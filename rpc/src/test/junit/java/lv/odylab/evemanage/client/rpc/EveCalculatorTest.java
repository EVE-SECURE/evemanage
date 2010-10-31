package lv.odylab.evemanage.client.rpc;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class EveCalculatorTest {
    private EveCalculator eveCalculator;

    @Before
    public void setUp() {
        eveCalculator = new EveCalculator();
    }

    @Test
    public void testCalculateWaste() {
        assertEquals(0.1, eveCalculator.calculateWaste(0, 10));
        assertEquals(0.01, eveCalculator.calculateWaste(9, 10));
        assertEquals(0.5, eveCalculator.calculateWaste(-4, 10));
    }

    @Test
    public void testCalculateMaterialAmount() {
        assertEquals(Long.valueOf(1001), eveCalculator.calculateMaterialAmount(1000L, 100, 10));
        assertEquals(Long.valueOf(1009), eveCalculator.calculateMaterialAmount(1000L, 10, 10));
        assertEquals(Long.valueOf(1017), eveCalculator.calculateMaterialAmount(1000L, 5, 10));
        assertEquals(Long.valueOf(1020), eveCalculator.calculateMaterialAmount(1000L, 4, 10));
        assertEquals(Long.valueOf(1025), eveCalculator.calculateMaterialAmount(1000L, 3, 10));
        assertEquals(Long.valueOf(1033), eveCalculator.calculateMaterialAmount(1000L, 2, 10));
        assertEquals(Long.valueOf(1050), eveCalculator.calculateMaterialAmount(1000L, 1, 10));
        assertEquals(Long.valueOf(1100), eveCalculator.calculateMaterialAmount(1000L, 0, 10));
        assertEquals(Long.valueOf(1200), eveCalculator.calculateMaterialAmount(1000L, -1, 10));
        assertEquals(Long.valueOf(1300), eveCalculator.calculateMaterialAmount(1000L, -2, 10));
        assertEquals(Long.valueOf(1400), eveCalculator.calculateMaterialAmount(1000L, -3, 10));
        assertEquals(Long.valueOf(1500), eveCalculator.calculateMaterialAmount(1000L, -4, 10));
    }

    @Test
    public void testCalculateMeResearchTime() {
        assertEquals(Integer.valueOf(750), eveCalculator.calculateMeResearchTime(1000, 1.0));
        assertEquals(Integer.valueOf(563), eveCalculator.calculateMeResearchTime(1000, 0.75));
    }

    @Test
    public void testMultiply() {
        assertEquals("1230.0", eveCalculator.multiply(1000L, "1.23"));
    }

    @Test
    public void testSum() {
        List<String> priceList = new ArrayList<String>();
        priceList.add("1.11");
        priceList.add("2.22");
        priceList.add("3.33");
        priceList.add("4.44");
        priceList.add("5.55");
        assertEquals("16.65", eveCalculator.sum(priceList));
    }
}
