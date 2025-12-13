package store.api.integracao.assas;

import com.squareup.okhttp.*;
import org.springframework.stereotype.Component;
import store.api.config.exceptions.StoreException;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class AssasApi {

    private static final String API_KEY = "$aact_prod_000MzkwODA2MWY2OGM3MWRlMDU2NWM3MzJlNzZmNGZhZGY6OjBkYjMxOThiLWYyNjYtNGMxZi05OTdjLTY5YTY1NmVjOTI2Nzo6JGFhY2hfNmE5NDE1MzAtNzI5ZS00MWRkLWJiNzYtYzc0NmQ2ZGEzYzEz";
    private static final String BASE_URL = "https://www.asaas.com/api/";
//    private static final String BASE_URL = "https://api-sandbox.asaas.com";

    public static void main(String[] args) {
        try {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");

            String json = """
                    {
                      "customer": "cus_000005123456",
                      "billingType": "CREDIT_CARD",
                      "value": 0.10,
                      "dueDate": "2026-05-10",
                      "description": "Mentoria Execução Trabalhista",
                      "creditCard": {
                        "holderName": "JOÃO DA SILVA",
                        "number": "4111111111111111",
                        "expiryMonth": "05",
                        "expiryYear": "2026",
                        "ccv": "123"
                      },
                      "creditCardHolderInfo": {
                        "name": "João da Silva",
                        "email": "joao@email.com",
                        "cpfCnpj": "12345678900",
                        "postalCode": "30140071",
                        "addressNumber": "100",
                        "phone": "31999999999"
                      }
                    }
                    """;

            RequestBody body = RequestBody.create(mediaType, json);

            Request request = new Request.Builder()
                    .url("https://sandbox.asaas.com/api/v3/payments")
                    .post(body)
                    .addHeader("accept", "application/json")
                    .addHeader("content-type", "application/json")
                    .addHeader("access_token", "SUA_API_KEY")
                    .build();

            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());


        } catch (Exception e) {
            e.printStackTrace();
        }

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
                throw new StoreException("Não foi possível gerar seu QrCode. Tente novamente em alguns instantes.");
            }
            String jsonResponse = response.body().string();
            return new ObjectMapper().readValue(jsonResponse, ConsultaPixResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
