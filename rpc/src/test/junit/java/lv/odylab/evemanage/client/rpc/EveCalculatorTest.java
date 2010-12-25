package lv.odylab.evemanage.client.rpc;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class EveCalculatorTest {
    private EveCalculator eveCalculator;

    @Before
    public void setUp() {
        eveCalculator = new EveCalculator();
    }

    @Test
    public void testCalculateWaste() {
        assertThat(eveCalculator.calculateWaste(0, 10), equalTo(0.1));
        assertThat(eveCalculator.calculateWaste(9, 10), equalTo(0.01));
        assertThat(eveCalculator.calculateWaste(-4, 10), equalTo(0.5));
    }

    @Test
    public void testCalculateMaterialAmount() {
        assertThat(eveCalculator.calculateMaterialAmount(1000L, 100, 10), equalTo(1001L));
        assertThat(eveCalculator.calculateMaterialAmount(1000L, 10, 10), equalTo(1009L));
        assertThat(eveCalculator.calculateMaterialAmount(1000L, 5, 10), equalTo(1017L));
        assertThat(eveCalculator.calculateMaterialAmount(1000L, 4, 10), equalTo(1020L));
        assertThat(eveCalculator.calculateMaterialAmount(1000L, 3, 10), equalTo(1025L));
        assertThat(eveCalculator.calculateMaterialAmount(1000L, 2, 10), equalTo(1033L));
        assertThat(eveCalculator.calculateMaterialAmount(1000L, 1, 10), equalTo(1050L));
        assertThat(eveCalculator.calculateMaterialAmount(1000L, 0, 10), equalTo(1100L));
        assertThat(eveCalculator.calculateMaterialAmount(1000L, -1, 10), equalTo(1200L));
        assertThat(eveCalculator.calculateMaterialAmount(1000L, -2, 10), equalTo(1300L));
        assertThat(eveCalculator.calculateMaterialAmount(1000L, -3, 10), equalTo(1400L));
        assertThat(eveCalculator.calculateMaterialAmount(1000L, -4, 10), equalTo(1500L));
    }

    @Test
    public void testCalculateMeResearchTime() {
        assertThat(eveCalculator.calculateMeResearchTime(1000, 1.0), equalTo(750));
        assertThat(eveCalculator.calculateMeResearchTime(1000, 0.75), equalTo(563));
    }
}
