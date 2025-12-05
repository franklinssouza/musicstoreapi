package store.api.util;

public class TelefoneUtil {

    private final static String REGEX_TELEFONE_DDD_CELULAR ="^(1[1-9]|2[12478]|3[1-8]|4[1-9]|5[1-5]|6[1-9]|7[134579]|8[1-9]|9[1-9])9\\d{8}$";
    private final static String REGEX_TELEFONE_FIXO_DDD_CELULAR ="(\\(?\\d{2}\\)?\\s)?(\\d{4,5}\\-\\d{4})";

    public static void main(String[] args) {

        System.out.println(TelefoneUtil.isTelefoneCelularValido("(31) 95959-5995"));
//        System.out.println(TelefoneUtil.validate("031-2571-2841"));
//        System.out.println(TelefoneUtil.validate("(31) 9104.0977"));
//        System.out.println(TelefoneUtil.validate("031-3511-1283"));
//        System.out.println(TelefoneUtil.validate("(31)3352-9858"));
//        System.out.println(TelefoneUtil.validate("31-3535-6575"));
//        System.out.println(TelefoneUtil.validate("31993693202"));
//        System.out.println(TelefoneUtil.validate("975477698"));
    }

    public static boolean isTelefoneFixoValido(String telefone){
        return telefone.matches(REGEX_TELEFONE_FIXO_DDD_CELULAR);
    }
    public static Boolean isTelefoneCelularValido(String telefone){
        if (telefone == null){
            return false;
        }
        return telefone.matches(REGEX_TELEFONE_DDD_CELULAR);
    }

    public static String validate(String telefone){
        if(telefone == null || telefone.equals("") || telefone.endsWith("informado")){
            return null;
        }else{

            telefone = telefone.replace("\r", "").replace("  ", "").replace(" ", "").replace("-", "").replace(".", "").replace("(", "").replace(")", "").replace("/", "").trim();
            if(telefone.startsWith("55")){
                telefone = telefone.substring(2,telefone.length());
            }

            if(telefone.startsWith("031")){
                telefone = telefone.substring(1,telefone.length());
            }

            if(telefone.equals("000000000")){
                return null;
            }
            if(telefone.equals("9999999")){
                return null;
            }
            if(telefone.equals("999999999")){
                return null;
            }
            if(telefone.length() < 11 || telefone.length() > 11 ){
                return null;
            }
            try {
                Long.parseLong(telefone);
            } catch (Exception e) {
                return null;
            }
        }
        if(!isTelefoneCelularValido(telefone)){
            return null;
        }
        return telefone;
    }

    public static String toNumber(String telefone) {
        if(!telefone.startsWith("55")){
            telefone = "55" + telefone;
        }
        return telefone.replaceAll("[^0-9]", "");
    }
}