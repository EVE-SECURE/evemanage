package lv.odylab.evemanage.client.rpc;

import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class CalculationExpressionTest {

    @Test
    public void testExpression() {
        CalculationExpression calculationExpression = CalculationExpression.parseExpression("QuickCalculation|Test Blueprint|ME:-1|PE:-2|Q:3|B:1234:10:20|B:1234/56:-2:-3|B:5678:5:10|P:11:123.45|P:22:67.89");
        assertThat(calculationExpression.getBlueprintTypeName(), equalTo("Test Blueprint"));
        assertThat(calculationExpression.getMeLevel(), equalTo(-1));
        assertThat(calculationExpression.getPeLevel(), equalTo(-2));
        assertThat(calculationExpression.getQuantity(), equalTo(3L));
        Map<String, Integer> blueprintPathToMeLevelMap = calculationExpression.getBlueprintPathToMeLevelMap();
        Map<String, Integer> blueprintPathToPeLevelMap = calculationExpression.getBlueprintPathToPeLevelMap();
        Map<Long, String> priceSetItemTypeIdToPriceMap = calculationExpression.getPriceSetItemTypeIdToPriceMap();
        assertThat(blueprintPathToMeLevelMap.size(), equalTo(3));
        assertThat(blueprintPathToMeLevelMap.get("1234"), equalTo(10));
        assertThat(blueprintPathToMeLevelMap.get("1234/56"), equalTo(-2));
        assertThat(blueprintPathToMeLevelMap.get("5678"), equalTo(5));
        assertThat(blueprintPathToPeLevelMap.size(), equalTo(3));
        assertThat(blueprintPathToPeLevelMap.get("1234"), equalTo(20));
        assertThat(blueprintPathToPeLevelMap.get("1234/56"), equalTo(-3));
        assertThat(blueprintPathToPeLevelMap.get("5678"), equalTo(10));
        assertThat(priceSetItemTypeIdToPriceMap.size(), equalTo(2));
        assertThat(priceSetItemTypeIdToPriceMap.get(11L), equalTo("123.45"));
        assertThat(priceSetItemTypeIdToPriceMap.get(22L), equalTo("67.89"));
    }

}
