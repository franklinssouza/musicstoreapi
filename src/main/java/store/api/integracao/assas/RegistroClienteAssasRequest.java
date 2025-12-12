package store.api.integracao.assas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistroClienteAssasRequest {

    private String name;
    private String cpfCnpj;
    private String email;
    private String phone;
    private String mobilePhone;
    private String address;
    private String addressNumber;
    private String complement;
    private String province;
    private String postalCode;
    private String externalReference;
    private Boolean notificationDisabled;
    private String additionalEmails;
    private String municipalInscription;
    private String stateInscription;
    private String observations;
    private String groupName;   // pode ser null
    private String company;     // pode ser null
    private Boolean foreignCustomer;

}
