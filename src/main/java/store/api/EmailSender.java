package store.api;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class EmailSender {

    private static final String hostName = "mail.portaismusic.com.br";
    private static final String porta = "465";
    private static final String login = "loja@portaismusic.com.br";
    private static final String senha = "Yeshua29@123";


    public static void main(String[] args) {
        enviarEmail("franklins.mariami@gmail.com","bem vindo","conteudo");
    }
    public static void enviarEmail(String to, String assunto, String content){
        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.host", hostName);
            properties.put("mail.smtp.port", porta);
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.ssl.enable", "true");  // IMPORTANTE
            properties.put("mail.smtp.starttls.enable", "false"); // DESATIVE


            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(login, senha);
                }
            });

            MimeMessage mensagem = new MimeMessage(session);
            mensagem.setFrom(new InternetAddress("loja@portaismusic.com.br"));
            mensagem.setRecipients(Message.RecipientType.TO, to);
            mensagem.setSubject(assunto, String.valueOf(StandardCharsets.UTF_8));

            Multipart multipart = new MimeMultipart();

            MimeBodyPart bodyPartTexto = new MimeBodyPart();
            bodyPartTexto.setContent(content, "text/html");

            multipart.addBodyPart(bodyPartTexto);

            mensagem.setContent(multipart);
            System.out.println("ENVIANDO");
            Transport.send(mensagem);
            System.out.println("ENVIADO");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
