package store.api.integracao.assas;

import com.squareup.okhttp.*;
import org.springframework.stereotype.Component;
import store.api.integracao.QrCodePixRequest;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class AssasApi {

    private static final String API_KEY = "$aact_prod_000MzkwODA2MWY2OGM3MWRlMDU2NWM3MzJlNzZmNGZhZGY6OjJlN2NhNDNiLTlhOWQtNDc5MS05NDM2LTA3ODcxODFhN2Y0Yzo6JGFhY2hfNzRlOTY4ZTYtYWVlNS00OWU2LWJiMDAtYmE2YTI0OTZkZGMx";
    private static final String BASE_URL = "https://www.asaas.com/api/";
//    private static final String BASE_URL = "https://api-sandbox.asaas.com";

    public static void main(String[] args) {

//        RegistroClienteAssasRequest registro = new RegistroClienteAssasRequest();
//        registro.setName("FRANKLIN SOUZA");
//        registro.setCpfCnpj("05953667671");
//        registro.setEmail("john.doe2@asaas.com.br");
//        registro.setPhone("4738010919");
//        registro.setMobilePhone("4799376637");
//        registro.setAddress("Av. Paulista");
//        registro.setAddressNumber("150");
//        registro.setComplement("Sala 201");
//        registro.setProvince("Centro");
//        registro.setPostalCode("01310-000");
//        registro.setExternalReference("12987382");
//        registro.setNotificationDisabled(false);
//        registro.setAdditionalEmails("john.doe@asaas.com,john.doe.silva@asaas.com.br");
//        registro.setMunicipalInscription("46683695908");
//        registro.setStateInscription("646681195275");
//        registro.setObservations("ótimo pagador, nenhum problema até o momento");
//        registro.setGroupName(null);
//        registro.setCompany(null);
//        registro.setForeignCustomer(false);
//
//        RegistroClienteAssasResponse registroResponse = new AssasApi().criarCliente(registro);
//        System.out.println(registroResponse);

        QrCodePixRequest pixRequest = new QrCodePixRequest();
        pixRequest.setAddressKey("louvorportaiseternos@gmail.com");
        pixRequest.setDescription("teste");
        pixRequest.setValue(1.0);
        pixRequest.setFormat("ALL");
        pixRequest.setExpirationDate("2026-05-05 14:20:50");
        pixRequest.setExpirationSeconds(null);
        pixRequest.setAllowsMultiplePayments(true);
        pixRequest.setExternalReference("TESTE 123");

        QrCodePixResponse qrCode = new AssasApi().gerarQrCodePix(pixRequest);
        System.out.println(qrCode);
    }

    public QrCodePixResponse gerarQrCodePix(QrCodePixRequest pixRequest) {
        try {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            String jsonRequest = new ObjectMapper().writeValueAsString(pixRequest);

            RequestBody body = RequestBody.create(mediaType,jsonRequest);Request request = new Request.Builder()
                    .url(BASE_URL+"/v3/pix/qrCodes/static")
                    .post(body)
                    .addHeader("accept", "application/json")
                    .addHeader("content-type", "application/json")
                    .addHeader("access_token", API_KEY)
                    .build();

            Response response = client.newCall(request).execute();
            String json = response.body().string();
            return new ObjectMapper().readValue(json, QrCodePixResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    public RegistroClienteAssasResponse criarCliente(RegistroClienteAssasRequest registro) {
        try {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            String jsonRequest = new ObjectMapper().writeValueAsString(registro);
            RequestBody body = RequestBody.create(mediaType,jsonRequest);

            Request request = new Request.Builder()
                    .url(BASE_URL + "/v3/customers")
                    .post(body)
                    .addHeader("accept", "application/json")
                    .addHeader("content-type", "application/json")
                    .addHeader("access_token", API_KEY)
                    .build();

            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new RuntimeException("Erro ao criar cliente: " + response.code());
            }

            String jsonResponse = response.body().string();
            return new ObjectMapper().readValue(jsonResponse, RegistroClienteAssasResponse.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
