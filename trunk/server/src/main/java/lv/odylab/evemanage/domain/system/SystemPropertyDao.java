package lv.odylab.evemanage.domain.system;

import com.google.inject.Inject;
import com.googlecode.objectify.ObjectifyFactory;

public class SystemPropertyDao {
    private final ObjectifyFactory objectifyFactory;

    @Inject
    public SystemPropertyDao(ObjectifyFactory objectifyFactory) {
        this.objectifyFactory = objectifyFactory;
    }

    public String getProperty(String propertyKey) {
        SystemProperty systemProperty = objectifyFactory.begin().query(SystemProperty.class)
                .filter("propertyKey", propertyKey).get();
        return systemProperty == null ? null : systemProperty.getPropertyValue();
    }

    public void put(String propertyKey, String propertyValue) {
        SystemProperty systemProperty = new SystemProperty();
        systemProperty.setPropertyKey(propertyKey);
        systemProperty.setPropertyValue(propertyValue);
        objectifyFactory.begin().put(systemProperty);
    }
}
