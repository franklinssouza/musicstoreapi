package store.api.integracao.melhorenvio;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import store.api.integracao.assas.RegistroClienteAssasResponse;
import store.api.util.FormatUtil;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@Component
public class MelhorEnvioApi {

//    URL PARA PEGAR O CODE EM PPRD
//    PRD
//    https://melhorenvio.com.br/oauth/authorize?response_type=code&client_id=21342&redirect_uri=https://portaismusic.com.br/loja/
//            &scope=cart-read cart-write companies-read companies-write coupons-read coupons-write notifications-read orders-read products-read products-write purchases-read shipping-calculate shipping-cancel shipping-checkout shipping-companies shipping-generate shipping-preview shipping-print shipping-share shipping-tracking ecommerce-shipping transactions-read users-read users-write

    private static final String BASE_URL = "https://sandbox.melhorenvio.com.br";

    private static final String CLIENT_ID = "7686";
    private static final String CLIENT_SECRET = "gpWPzBMSYazM799bpNvWqVIUKaD2U4rMB0RqYHvK";

    private static final String REDIRECT_URI = "http://localhost:8080/callback";


    @Value("${melhorenvio.ceporigem}")
    private String cepOrigem;
    @Value("${melhorenvio.token}")
    private String token;
    @Value("${melhorenvio.refreshtoken}")
    private String refreshToken;

    public static void main(String[] args) {

         new MelhorEnvioApi().calcularFrete(null);
    }

    public FreteEntregaDto calcularFrete(ConsultaFreteRequest consultaFreteRequest){
        FreteEntregaDto retorno = FreteEntregaDto.builder()
                .valor("15,00")
                .nome("Padrão")
                .dias(5).build();;

        try {

            OkHttpClient client = new OkHttpClient();
            String json =
                    "{"
                            + "\"from\":{\"postal_code\":\"" + cepOrigem + "\"},"
                            + "\"to\":{\"postal_code\":\"" + consultaFreteRequest.getCep() + "\"},"
                            + "\"products\":[{"
                            + "\"id\":\"1\","
                            + "\"width\":1,"
                            + "\"height\":1,"
                            + "\"length\":1,"
                            + "\"weight\":1,"
                            + "\"quantity\":\"" + consultaFreteRequest.getQuantity() + "\","
                            + "\"insurance_value\":1"
                            + "}]"
                            + "}";

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, json);

            Request request = new Request.Builder()
                    .url("https://melhorenvio.com.br/api/v2/me/shipment/calculate")
                    .post(body)
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + this.token)
                    .addHeader("User-Agent", "Portais Music (franklins.mariami@gmail.com)")
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {

                String jsonRetorno = response.body().string();
                Gson gson = new Gson();
                Type listType = new TypeToken<List<ConsultaFreteResponse>>() {}.getType();
                List<ConsultaFreteResponse> opcoes = gson.fromJson(jsonRetorno, listType);

                if(opcoes == null){
                    retorno = FreteEntregaDto.builder()
                            .valor("15,00")
                            .nome("Padrão")
                            .dias(5).build();
                }else{
                    for (ConsultaFreteResponse option : opcoes) {
                        if (StringUtils.isEmpty(option.getError())){
                            retorno = FreteEntregaDto.builder()
                                    .valor(FormatUtil.format(option.getPrice()))
                                    .nome(option.getName())
                                    .dias(option.getDeliveryTime()).build();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return retorno;
        }
        return retorno;
    }


    public static void gerarToken(){

        try {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\"grant_type\":\"authorization_code\",\"client_id\":21342,\"client_secret\":\"LUH6OXkFms03iH3fMB3x8r141zTgjwWCSfXy5uYb\",\"redirect_uri\":\"https://portaismusic.com.br/loja/\",\"code\":\"def502003cebec58a3a98ca047a7d0607410ada0c956c964c036cc620964c73bcf008b8857da18332a001047ec066a426b22fa85e45dc30748989a6ced82cfcc42310990ecc465cc7cf849526df25255b1c55866bafea9fc6d87cb0a6d8264eee217131c2a150c2fe022bebac37b71549dccb8c25d8d360a5081cad87d2aada0e0198edcc57b52c08ee0fb3d567c1362d93b26121d18af478844f9ecef66b95609520055c575ba10067cd4161e2c84281f234f2618fde863e020ea7953c674e2f98ae37ef12336d1f0185697f972c2e3155c0bccc5dd50418d71c78febba1d85eb798dc3b2cd69d6b901038aa9b65cef4aad8d2d4926d4aa3b8129174fbaf306dc728e2f42e8cc50702b81c75399c061f8852132054f42da95c16677b7256256a993e12982d500601893daf2b4b0e6b98b2c90bc246acb77e01c8fc4e277788991eced63d05b2961a395ecd8bd2fe7ffbd37b451a126bfb4d44c630afc70d0da93c7695bd4a4351b92ecd260adbd295ba2ac9369dc62e46734c9f765d0c3cae5e1db4957921fe9edde08271ed36b8614a7da50d17e3d833cf40e969ba49844693d6a031a4a861949256468cb680505d85e25ff08471f37a4b352e5783bdcf15e9326de4f623532003db699aced7f82000a3a62307a329a8595a70edf6fd2c63aec49b657422340201831d2074c797b1d25533a58113b9ba9de3faad6ff325c6a65fee6a85de17dc261e0b37ac9de6b7f739a20b957d2ff70b74fdbe92d9346a81e20145bb5c74504326dc3c96239492f76ce96bd3028b1a80891cd704fcf3c8b831eb61423eb4aad0ede0a1172871f7a4b64fc0ffab07461d9c5755a9ec6bb935a675ab9f2180bf3a1c770f68d1a2f212969891c8ceada3509cb8a4c471f3c91fd9400d3b12b589a31af629e7e2fc87eefd2afd80db71295b94e1dbfa31f36aad25a9a2fc58e0639aec25614f8a22859aeaaf2619a0e5db2094a3622368673a7501f3803e8db907ce6fd86ed5adc167534b859badada8b47d5e690a3814c3019110f5227bd3b68266ac20f3def3c7226aa46be16ed74bab78af4532bb3e7fd5c300332f92001291c49c8e2946d3a582252b61e0ad3785d68d9b5370e059046ae188046c8b6d393ee380b71\",\"refresh_token\":\"\"}");
            Request request = new Request.Builder()
                    .url("https://melhorenvio.com.br/oauth/token")
                    .post(body)
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("User-Agent", "Portais Music (franklins.mariami@gmail.com)")
                    .build();

            Response response = client.newCall(request).execute();

            System.out.println(response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
