package store.api.util;

import org.apache.commons.lang3.StringUtils;
import store.api.config.exceptions.StoreException;
import store.api.domain.EnderecoDto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validationtil {

    private static String regex = "[0-9]{8}";

    public static void validarEndereco(EnderecoDto dto) throws StoreException {
        if(dto == null){
            throw new StoreException("Informe endereço informado está inválido.");
        }

        if(StringUtils.isEmpty(dto.getEndereco()) || dto.getEndereco().length() < 5){
            throw new StoreException("Informe o seu endereço completo.");
        }

        if(StringUtils.isEmpty(dto.getNumero())){
            throw new StoreException("Informe o número do seu endereço.");
        }

        if(StringUtils.isEmpty(dto.getBairro()) || dto.getBairro().length() < 5){
            throw new StoreException("Informe o nome do seu bairro.");
        }

        if(StringUtils.isEmpty(dto.getCidade()) ){
            throw new StoreException("Informe o nome da cidade.");
        }

        if(StringUtils.isEmpty(dto.getCep()) || !Validationtil.isCepValido(dto.getCep())){
            throw new StoreException("Informe o seu CEP completo.");
        }

        if(StringUtils.isEmpty(dto.getEstado()) ){
            throw new StoreException("Informe o seu estado.");
        }
    }
    public static boolean isValidCPF(String cpf) {
        if (cpf == null) return false;

        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("\\D", "");

        // CPF precisa ter 11 dígitos
        if (cpf.length() != 11) return false;

        // Rejeita CPFs com todos dígitos iguais (ex: 00000000000)
        if (cpf.matches("(\\d)\\1{10}")) return false;

        try {
            int sum = 0;

            // Cálculo do primeiro dígito verificador
            for (int i = 0; i < 9; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }

            int firstDigit = 11 - (sum % 11);
            firstDigit = (firstDigit > 9) ? 0 : firstDigit;

            // Cálculo do segundo dígito verificador
            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }

            int secondDigit = 11 - (sum % 11);
            secondDigit = (secondDigit > 9) ? 0 : secondDigit;

            // Valida com os dígitos informados
            return cpf.charAt(9) == Character.forDigit(firstDigit, 10)
                    && cpf.charAt(10) == Character.forDigit(secondDigit, 10);

        } catch (Exception e) {
            return false;
        }
    }

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
