package lv.odylab.evemanage.client.rpc.dto.user;

import java.io.Serializable;

public class PriceFetchOptionDto implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
