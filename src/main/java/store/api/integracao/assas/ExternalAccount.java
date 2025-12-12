package store.api.integracao.assas;

import lombok.Data;

@Data
public class ExternalAccount {

    private Integer ispb;
    private String ispbName;
    private String name;
    private String agency;
    private String account;
    private String accountDigit;
    private String accountType;
    private String cpfCnpj;
    private String addressKey;
    private String addressKeyType;
}

