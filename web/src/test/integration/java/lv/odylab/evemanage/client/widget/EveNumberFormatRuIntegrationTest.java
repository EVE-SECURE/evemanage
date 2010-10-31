package lv.odylab.evemanage.client.widget;

import com.google.gwt.junit.client.GWTTestCase;
import org.junit.Test;

public class EveNumberFormatRuIntegrationTest extends GWTTestCase {

    @Override
    public String getModuleName() {
        return "lv.odylab.evemanage.EveManageRu";
    }

    @Test
    public void test_PRICE_FORMAT() {
        assertEquals("123,456,789.00", EveNumberFormat.PRICE_FORMAT.format(123456789));
        assertEquals("123,456,789.12", EveNumberFormat.PRICE_FORMAT.format(123456789.12345));
    }

    @Test
    public void test_QUANTITY_FORMAT() {
        assertEquals("123,456,789", EveNumberFormat.QUANTITY_FORMAT.format(123456789));
    }

    @Test
    public void test_WASTE_FORMAT() {
        assertEquals("0.123", EveNumberFormat.WASTE_FORMAT.format(0.123));
        assertEquals("0.123457", EveNumberFormat.WASTE_FORMAT.format(0.123456789));
    }

    @Test
    public void test_DECIMAL_FORMAT() {
        assertEquals("123456789", EveNumberFormat.DECIMAL_FORMAT.format(123456789));
    }
}
