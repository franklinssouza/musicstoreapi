package store.api.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class FormatUtil {

    public static String format(String valor){
        if(valor == null){
            return "0,0";
        }
        if(valor.contains(".") && valor.contains(",")){
            valor=valor.replace(".","").replace(",",".");;
        }
        if(valor.contains(",") && !valor.contains(".")){
            valor=valor.replace(",",".");;
        }
        double valor1 = 0;
        try {
            valor1 = Double.parseDouble(valor);
        } catch (NumberFormatException e) {
            return null;
        }
        return format(valor1);
    }

    public static void main(String[] args) {

        System.out.println(format(100.144));
    }
    public static String format(double valor){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat formatoMoeda = new DecimalFormat("#,##0.00", symbols);
        return formatoMoeda.format(valor);
    }

    public static Double converttoDouble(String valorTotal) {
        Double valor = 0.0;
        if(valorTotal != null){
            if(!valorTotal.contains(",")){
                valor = Double.parseDouble(valorTotal);
            }
            if(valorTotal.contains(".") && valorTotal.contains(",")){
                valorTotal = valorTotal.replace(".","").replace(",",".");
                valor = Double.parseDouble(valorTotal);
            }
            if(valorTotal.contains(",") && !valorTotal.contains(".")){
                valorTotal = valorTotal.replace(",",".");
                valor = Double.parseDouble(valorTotal);
            }
        }
        return valor;
    }

    public static double toPercentage(double v) {
        String format = String.format("%.2f", v).replace(",00","");
        return Double.parseDouble(format.replace(",","."));
    }

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
