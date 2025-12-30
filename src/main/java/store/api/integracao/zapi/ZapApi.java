package store.api.integracao.zapi;

import com.squareup.okhttp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ZapApi {

    private final Logger logger = LoggerFactory.getLogger( ZapApi.class);

    @Value("${zapi.instancia}")
    private String instancia;

    @Value("${zapi.token}")
    private String token;

    @Value("${zapi.security}")
    private String security;

    public void enviarTexto(String mensagem, String para) {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");

        String jsonBody = String.format("{\"phone\": \"%s\",  \"message\": \"%s\",  \"caption\": \"%s\" , \"image\": \"%s\"}", "55" + para, mensagem, mensagem, "https://www.portaismusic.com.br/assets/img/logo-loja1.png");
        RequestBody body = RequestBody.create(mediaType, jsonBody);

        Request request = new Request.Builder()
                .url("https://api.z-api.io/instances/"+ this.instancia+"/token/"+ this.token+"/send-text")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("client-Token",  security )
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                logger.info("Mensagem WahtsApp enviada com sucesso!");
            } else {
                logger.info("Erro ao enviar a mensagem WahtsApp: " + response.message());
            }
            response.body().close();
        } catch (Exception e) {
            logger.error("Erro ao enviar mensagem whatsapp", e);
        }
    }
}
