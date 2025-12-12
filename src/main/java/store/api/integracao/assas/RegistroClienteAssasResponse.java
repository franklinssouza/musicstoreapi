package store.api.integracao.assas;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroClienteAssasResponse {

    private String object;
    private String id;
    private String dateCreated;
    private String name;
    private String email;
    private String company;
    private String phone;
    private String mobilePhone;
    private String address;
    private String addressNumber;
    private String complement;
    private String province;
    private String postalCode;
    private String cpfCnpj;
    private String personType;
    private Boolean deleted;
    private String additionalEmails;
    private String externalReference;
    private Boolean notificationDisabled;
    private String observations;
    private String municipalInscription;
    private String stateInscription;
    private Boolean canDelete;
    private String cannotBeDeletedReason;
    private Boolean canEdit;
    private String cannotEditReason;
    private Integer city;
    private String cityName;
    private String state;
    private String country;



    @Override
    public String toString() {
        return """
                RegistroClienteAssas {
                    id='%s',
                    name='%s'
                }
                """.formatted(id, name);
    }
}

