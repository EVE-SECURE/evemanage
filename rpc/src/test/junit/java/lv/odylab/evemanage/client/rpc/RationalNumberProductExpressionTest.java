package lv.odylab.evemanage.client.rpc;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class RationalNumberProductExpressionTest {

    @Test
    public void testExpression() {
        assertThat(RationalNumberProductExpression.parseExpression("10/3").getExpression(), equalTo("10/3"));
        assertThat(RationalNumberProductExpression.parseExpression("10/3*40/5").getExpression(), equalTo("10/3*40/5"));
        assertThat(RationalNumberProductExpression.parseExpression("10/3*40/5*1000/20").getExpression(), equalTo("10/3*40/5*1000/20"));
    }

    @Test
    public void testEvaluate() {
        assertThat(RationalNumberProductExpression.parseExpression("10/3").evaluate(), equalTo(new BigDecimal("3.33")));
        assertThat(RationalNumberProductExpression.parseExpression("40/5").evaluate(), equalTo(new BigDecimal("8.00")));
        assertThat(RationalNumberProductExpression.parseExpression("1000/20").evaluate(), equalTo(new BigDecimal("50.00")));
        assertThat(RationalNumberProductExpression.parseExpression("10/3*40/5").evaluate(), equalTo(new BigDecimal("26.67")));
        assertThat(RationalNumberProductExpression.parseExpression("10/3*40/5*1000/20").evaluate(), equalTo(new BigDecimal("1333.33")));
    }
}
