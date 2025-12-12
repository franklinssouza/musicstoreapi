package store.api.integracao.assas;

import lombok.Data;
import java.util.List;

@Data
public class ConsultaPixResponse {
    private String object;
    private boolean hasMore;
    private int totalCount;
    private int limit;
    private int offset;
    private List<PixData> data;
}

