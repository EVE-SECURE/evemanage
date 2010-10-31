package lv.odylab.evemanage.domain.system;

import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

import javax.persistence.Id;
import java.io.Serializable;

@Unindexed
public class SystemProperty implements Serializable {
    private static final long serialVersionUID = 8546390252914293375L;

    public static final String SYSTEM_PUBLIC_KEY_BASE64 = "systemPublicKeyBase64";
    public static final String SYSTEM_BANNER_MESSAGE = "systemBannerMessage";

    @Id
    private Long id;
    @Indexed
    private String propertyKey;
    private String propertyValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPropertyKey() {
        return propertyKey;
    }

    public void setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }
}
