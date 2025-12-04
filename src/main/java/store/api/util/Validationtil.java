package store.api.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validationtil {

    private static String regex = "[0-9]{8}";

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

    public static boolean validarEmail(String email){
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isCepValido(String cep) {
        if (cep == null || cep.trim().isEmpty()) {
            return false;
        }
        return cep.matches("^[0-9]{5}-?[0-9]{3}$");
    }

    public  static String validarCEP(String cep) {
        if (cep == null) {
            return null;
        }
        cep = cep.replaceAll("[^0-9]", "");
        if (cep.length() != 8) {
            return null;
        }
        if (!cep.matches(regex)) {
            return null;
        }
        return cep;
    }
}
