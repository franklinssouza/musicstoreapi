package store.api.integracao.assas;

import lombok.Data;

@Data
public class QrCodePixResponse {

    private String id;
    private String encodedImage;
    private String payload;
    private Boolean allowsMultiplePayments;
    private String expirationDate;
    private String externalReference;
    private String description;

    @Override
    public String toString() {
        return """
                QrCodePixResponse {
                    id='%s',
                    encodedImage='%s',
                    payload='%s'
                }
                """.formatted(id, encodedImage,payload);
    }
}
