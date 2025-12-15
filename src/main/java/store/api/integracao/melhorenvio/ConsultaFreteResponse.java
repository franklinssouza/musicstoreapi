package store.api.integracao.melhorenvio;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class ConsultaFreteResponse {

    private Integer id;
    private String name;
    private String error;

    private String price;
    private String customPrice;
    private String discount;
    private String currency;

    @SerializedName("delivery_time")
    private Integer deliveryTime;
    private DeliveryRange deliveryRange;
    private Integer customDeliveryTime;
    private DeliveryRange customDeliveryRange;

    private List<Package> packages;
    private AdditionalServices additionalServices;

    private Company company;

    @Data
    public static class Company {
        private Integer id;
        private String name;
        private String picture;
    }

    @Data
    public static class DeliveryRange {
        private Integer min;
        private Integer max;
    }

    @Data
    public static class Package {
        private String price;
        private String discount;
        private String format;
        private Dimensions dimensions;
        private String weight;
        private String insuranceValue;
        private List<Product> products;
    }

    @Data
    public static class Dimensions {
        private Integer height;
        private Integer width;
        private Integer length;
    }

    @Data
    public static class Product {
        private String id;
        private Integer quantity;
    }

    @Data
    public static class AdditionalServices {
        private Boolean receipt;
        private Boolean ownHand;
        private Boolean collect;
    }
}

