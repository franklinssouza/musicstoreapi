package store.api.util;

public class TextoUtil {

    public static String capitalizar(String frase) {
        if (frase == null || frase.isEmpty()) return frase;

        StringBuilder sb = new StringBuilder();
        boolean maiuscula = true;

        for (char c : frase.toCharArray()) {
            if (Character.isWhitespace(c)) {
                maiuscula = true;
                sb.append(c);
            } else {
                sb.append(maiuscula ? Character.toUpperCase(c) : Character.toLowerCase(c));
                maiuscula = false;
            }
        }

        return sb.toString();
    }
    public static String formatarComZero(int numero) {
        return numero < 10 ? "0" + numero : String.valueOf(numero);
    }

}
