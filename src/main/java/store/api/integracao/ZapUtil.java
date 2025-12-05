package store.api.integracao;

import com.squareup.okhttp.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ZapUtil {

    @Value("${zapi.instancia}")
    public String instancia;

    @Value("${zapi.token}")
    public String token;

    public void enviarTexto(String mensagem, String para) {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");

        String jsonBody = String.format("{\"phone\": \"%s\", \"message\": \"%s\"}", para, mensagem);
        RequestBody body = RequestBody.create(mediaType, jsonBody);

        Request request = new Request.Builder()
                .url("https://api.z-api.io/instances/SUA_INSTANCIA/token/SEU_TOKEN/send-text")
                .post(body)
                .addHeader("client-token", "{{security-token}}") // Substitua pelo seu token de segurança
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.println("Mensagem enviada com sucesso!");
            } else {
                System.out.println("Erro ao enviar a mensagem: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
