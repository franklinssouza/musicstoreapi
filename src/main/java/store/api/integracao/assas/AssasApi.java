package store.api.integracao.assas;

import com.squareup.okhttp.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import store.api.config.exceptions.StoreException;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class AssasApi {

    private static final String API_KEY = "$aact_prod_000MzkwODA2MWY2OGM3MWRlMDU2NWM3MzJlNzZmNGZhZGY6OmYyN2ZkZjk5LWUxYmYtNGZjNy05YTFmLTI0ODhmYzQwODg2MTo6JGFhY2hfNDhlOGY3NmEtZDQ2ZC00YjAxLWE3ZjQtMTU3ZTc2ZTVjZTc5";
    private static final String BASE_URL = "https://www.asaas.com/api/";
//    private static final String BASE_URL = "https://api-sandbox.asaas.com";

    @Value("${assas.chavepix}")
    private String chavePixAssas;


    public PaymentResponse getPayment(String key)  {

        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        Response response = null;
        try {
            MediaType mediaType = MediaType.parse("application/json");
            Request request = new Request.Builder()
                    .url(BASE_URL + "/v3/payments/" + key)
                    .get()
                    .addHeader("accept", "application/json")
                    .addHeader("access_token", API_KEY)
                    .build();
            response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new StoreException("Não foi possível gerar seu pagamento. Tente novamente em alguns instantes.");
            }
            String json = response.body().string();
            response.body().close();
            return mapper.readValue(json, PaymentResponse.class);

        } catch (Exception e) {
            return null;
        }
    }


    public AsaasPaymentLinkResponse gerarLinkPagamento(AssasPaymentLinkRequest req) throws StoreException {

        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        Response response = null;
        try {
            MediaType mediaType = MediaType.parse("application/json");
            String jsonRequest = mapper.writeValueAsString(req);

            RequestBody body = RequestBody.create(mediaType, jsonRequest);

            Request request = new Request.Builder()
                    .url(BASE_URL + "v3/paymentLinks")
                    .post(body)
                    .addHeader("accept", "application/json")
                    .addHeader("content-type", "application/json")
                    .addHeader("access_token", API_KEY)
                    .build();

            response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new StoreException("Não foi possível gerar seu pagamento. Tente novamente em alguns instantes.");
            }
            String json = response.body().string();
            response.body().close();
            return mapper.readValue(json, AsaasPaymentLinkResponse.class);

        } catch (StoreException e) {
            throw e;
        } catch (Exception e) {
            throw new StoreException("Não foi possível gerar seu QrCode. Tente novamente em alguns instantes.");
        }
    }

    public QrCodePixResponse gerarQrCodePix(Double valor, String externalId) throws StoreException {

        QrCodePixRequest pixRequest = new QrCodePixRequest();
        pixRequest.setAddressKey(chavePixAssas);
        pixRequest.setDescription("PMS");
        pixRequest.setValue(valor);
        pixRequest.setFormat("ALL");
        pixRequest.setExpirationDate("2045-05-05 14:20:50");
        pixRequest.setExpirationSeconds(null);
        pixRequest.setAllowsMultiplePayments(false);
        pixRequest.setExternalReference(externalId);
        return this.gerarQrCodePix(pixRequest);
    }


    public QrCodePixResponse gerarQrCodePix(QrCodePixRequest pixRequest) throws StoreException {
        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        Response response = null;
        try {
            MediaType mediaType = MediaType.parse("application/json");
            String jsonRequest = mapper.writeValueAsString(pixRequest);

            RequestBody body = RequestBody.create(mediaType, jsonRequest);

            Request request = new Request.Builder()
                    .url(BASE_URL + "/v3/pix/qrCodes/static")
                    .post(body)
                    .addHeader("accept", "application/json")
                    .addHeader("content-type", "application/json")
                    .addHeader("access_token", API_KEY)
                    .build();

            response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new StoreException("Não foi possível gerar seu QrCode. Tente novamente em alguns instantes.");
            }
            String json = response.body().string();
            response.body().close();
            return mapper.readValue(json, QrCodePixResponse.class);

        } catch (StoreException e) {
            throw e;
        } catch (Exception e) {
            throw new StoreException("Não foi possível gerar seu QrCode. Tente novamente em alguns instantes.");
        }
    }

    public RegistroClienteAssasResponse isClienteExistente(String idCliente) {
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(BASE_URL + "/v3/customers/cus_0000073052772")
                    .get()
                    .addHeader("accept", "application/json")
                    .addHeader("access_token", API_KEY)
                    .build();


            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new RuntimeException("Erro ao criar cliente: " + response.code());
            }

            String json = response.body().string();
            response.body().close();
            return new ObjectMapper().readValue(json, RegistroClienteAssasResponse.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public RegistroClienteAssasResponse criarCliente(RegistroClienteAssasRequest registro) throws StoreException {
        try {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            String jsonRequest = new ObjectMapper().writeValueAsString(registro);
            RequestBody body = RequestBody.create(mediaType, jsonRequest);

            Request request = new Request.Builder()
                    .url(BASE_URL + "/v3/customers")
                    .post(body)
                    .addHeader("accept", "application/json")
                    .addHeader("content-type", "application/json")
                    .addHeader("access_token", API_KEY)
                    .build();

            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new StoreException("Não foi possível gerar seu QrCode. Tente novamente em alguns instantes.");
            }
            String jsonResponse = response.body().string();
            response.body().close();
            return new ObjectMapper().readValue(jsonResponse, RegistroClienteAssasResponse.class);

        } catch (StoreException e) {
            throw e;
        } catch (Exception e) {
            throw new StoreException("Não foi possível gerar seu QrCode. Tente novamente em alguns instantes.", e);
        }
    }

    public ConsultaPixResponse processarPagamentoPix(Integer offset, Integer limit) {
        try {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(BASE_URL + "/v3/pix/transactions?status=DONE&limit=" + limit + "&offset=" + offset)
                    .get()
                    .addHeader("accept", "application/json")
                    .addHeader("access_token", API_KEY)
                    .build();

            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new StoreException("Não foi possível processar o pagamento.");
            }
            String jsonResponse = response.body().string();
            response.body().close();
            return new ObjectMapper().readValue(jsonResponse, ConsultaPixResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
