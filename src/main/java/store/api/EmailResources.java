package store.api;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.annotation.Resource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

@Component
public class EmailResources {

    @Value("${resources.logo}")
    public String logo;
    @Value("${resources.youtube}")
    public  String iconYoutube;
    @Value("${resources.instagram}")
    public  String iconInstagram;

    @Value("${resources.htmls.bemvindo}")
    public  String htmlBemVindo;

}
