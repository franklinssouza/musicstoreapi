package store.api.integracao;

import org.springframework.stereotype.Component;

public class ZapMessageUtil {

    public static String reenvioSenha = "Olá *XXX*! \n " +
            "\nEssa é sua senha de acesso ao *Portais Music Store* \n \n" +
            "*YYY* \n \n " +
            "Acesse o *link* abaixo e confira nossas novidades! \n " +
            "https://www.portaismusic.com.br/loja \n ";

    public static String bemvindo = "Oi *XXX*! \n \nSeja muito bem-vindo(o) a *Portais Music Store* ! \n \n" +
            "Seu cadastro foi *confirmado* com sucesso e " +
            "você já pode realizar seus *pedidos*! \n \n " +
            "Acesse o *link* abaixo e confira nossas novidades! \n " +
            "https://www.portaismusic.com.br/loja \n ";


    public static String compraRealizada = "Seja muito bem-vindo(o) a *Portais Music Store* ! \n " +
            "\n Seu cadastro foi *confirmado* com sucesso e " +
            "você já pode realizar seus pedidos! \n \n " +
            "Acesse o link abaixo e confira nossas novidades! \n " +
            "https://www.portaismusic.com.br/loja \n ";
}
