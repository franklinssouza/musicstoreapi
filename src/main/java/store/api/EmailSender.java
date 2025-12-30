package store.api;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import store.api.config.exceptions.StoreException;
import store.api.integracao.zapi.ZapApi;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class EmailSender {

    private static final String hostName = "mail.portaismusic.com.br";
    private static final String porta = "465";
    private static final String login = "loja@portaismusic.com.br";
    private static final String senha = "Yeshua29@123";
    private final EmailResources emailResources;

    private final Logger logger = LoggerFactory.getLogger( EmailSender.class);

    public EmailSender(EmailResources emailResources) {
        this.emailResources = emailResources;
    }

    @Async
    public  void reenviarSenha(String usuario, String to, String senha) throws StoreException {
        try {
            Path caminho = Paths.get(emailResources.recuperacaoSenha);
            String conteudo = null;

            conteudo = Files.readString(caminho);
            conteudo = conteudo.replace("{usuario}", usuario);
            conteudo = conteudo.replace("{senha}", senha);

            this.send(to,"Reenvio de senha",conteudo, getMimeBodyParts());

            logger.info("Email de recuperacao de senha enviado com sucesso");

        } catch (Exception e) {
            throw new StoreException("Erro ao enviar recuperação de senha.", e);
        }
    }

    @Async
    public void enviarEmailCompra(String to, String usuario, String compras) throws StoreException {
        try {

            Path caminho = Paths.get(emailResources .compraRealizada);
            String conteudo = Files.readString(caminho);
            conteudo = conteudo.replace("{usuario}", usuario);

            compras = compras.replaceAll("\n", "<br>");
            compras = compras.replaceAll("\\*", "");

            conteudo = conteudo.replace("{compras}", compras);

            this.send(to,"Compra realizada!",conteudo, getMimeBodyParts());

            logger.info("Email de compra realizada enviado com sucesso");

        } catch (Exception e) {
            throw new StoreException("Erro ao enviar email", e);
        }
    }

    @Async
    public  void enviarEmailBemVindo(String to, String usuario) throws StoreException {
        try {

            Path caminho = Paths.get(emailResources .htmlBemVindo);
            String conteudo = Files.readString(caminho);
            conteudo = conteudo.replace("{usuario}", usuario);

            this.send(to,"Cadastro realizado",conteudo, getMimeBodyParts());

            logger.info("Email de bem-indo enviado com sucesso");

        } catch (Exception e) {
            throw new StoreException("Erro ao enviar email", e);
        }
    }

    private List<MimeBodyPart> getMimeBodyParts() throws MessagingException {
        List<MimeBodyPart> bodyParts = new ArrayList<>();

        MimeBodyPart imagemEmbebida = new MimeBodyPart();
        DataSource fds = new FileDataSource(emailResources.iconYoutube); // Substitua pelo caminho do seu arquivo de imagem
        imagemEmbebida.setDataHandler(new DataHandler(fds));
        imagemEmbebida.setHeader("Content-ID", "<youtube>");
        bodyParts.add(imagemEmbebida);

        imagemEmbebida = new MimeBodyPart();
        fds = new FileDataSource(emailResources.iconInstagram); // Substitua pelo caminho do seu arquivo de imagem
        imagemEmbebida.setDataHandler(new DataHandler(fds));
        imagemEmbebida.setHeader("Content-ID", "<instagram>");
        bodyParts.add(imagemEmbebida);

        imagemEmbebida = new MimeBodyPart();
        fds = new FileDataSource(emailResources.logo); // Substitua pelo caminho do seu arquivo de imagem
        imagemEmbebida.setDataHandler(new DataHandler(fds));
        imagemEmbebida.setHeader("Content-ID", "<logo>");
        bodyParts.add(imagemEmbebida);
        return bodyParts;
    }

    public  void send(String to, String assunto, String content, List<MimeBodyPart> dados){
        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.host", hostName);
            properties.put("mail.smtp.port", porta);
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.starttls.enable", "false");

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(login, senha);
                }
            });

            MimeMessage mensagem = new MimeMessage(session);
            mensagem.setFrom(new InternetAddress("loja@portaismusic.com.br","Portais Music Store"));
            mensagem.setRecipients(Message.RecipientType.TO, to);
            mensagem.setSubject(assunto, String.valueOf(StandardCharsets.UTF_8));

            Multipart multipart = new MimeMultipart();

            MimeBodyPart bodyPartTexto = new MimeBodyPart();
            bodyPartTexto.setContent(content, "text/html");

            if(dados != null){
                for (MimeBodyPart item : dados) {
                    multipart.addBodyPart(item);
                }
            }
            multipart.addBodyPart(bodyPartTexto);

            mensagem.setContent(multipart);
            Transport.send(mensagem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
