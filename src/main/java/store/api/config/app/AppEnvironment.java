package store.api.config.app;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Configuration
@ConfigurationProperties
@Component
public class AppEnvironment {
//    @Value( "${config.unleash.appname}" )
//    private String unleashAppName;
//
//    @Value( "${config.unleash.instanceid}" )
//    private String unleashInstanceId;
//
//    @Value( "${config.unleash.api}" )
//    private String unleashApi;
//
//    @Value( "${config.unleash.token}" )
//    private String unleashToken;
//
//    @Value( "${cloud.aws.region.static}" )
//    private String awsRegion;
//
//    @Value( "${cloud.aws.secretAccessKey}" )
//    private String secretAccessKey;
//
//    @Value( "${cloud.aws.accessKeyId}" )
//    private String accessKeyId;
//
//    @Value("${ms.imagem.endpoint}")
//    private String urlMsImagem;
//
//    @Value("${ms.monolito.endpoint}")
//    private String urlMonolito;
//
//    @Value("${ms.catalogo.endpoint}")
//    private String urlCatalogo;
//
//    @Value("${crm.notificacao.email}")
//    private String notificacaoEmailsFalha;
//
//    @Value("${crm.notificacao.templateCrm}")
//    private String crmTemplateFalhaMsAnuncio;
//
//    @Value("${cloud.aws.sns.arns.estado-anuncio}")
//    private String arnSnsEstadoAnuncio;
//
//    @Value("${cloud.aws.sqs.imagem.consumer}")
//    private String imagemSqsConsumer;
//
//    @Value("${cloud.aws.sqs.anuncio.consumer}")
//    private String sqsAnuncioConsumer;
//
//    @Value("${cloud.aws.sqs.anuncio.estado.bff}")
//    private String sqsAnuncioEstadoBff;
//
//    @Value("${cloud.aws.sqs.anuncio.estado.jornada}")
//    private String sqsAnuncioEstadoJornada;
//
//
//    @Value("${ms.fraude.endpoint}")
//    private String fraudeUrl;
//
//    @Value("${cloud.aws.sqs.fraude.consumer}")
//    private String fraudeSqsConsumer;
//
//    @Value("${cloud.aws.sqs.fraude.producer}")
//    private String fraudeSqsProducer;
//
//    @Value("${cloud.aws.sqs.fraude.accesskey}")
//    private String fraudeAccessKey;
//
//    @Value("${cloud.aws.sqs.fraude.secretkey}")
//    private String fraudeSecretKey;
//
//    @Value("${cloud.aws.sqs.fraude.region}")
//    private String fraudeRegion;
}