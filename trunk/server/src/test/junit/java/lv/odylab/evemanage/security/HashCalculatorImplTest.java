package lv.odylab.evemanage.security;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

public class HashCalculatorImplTest {

    @Test
    public void testHashApiKey() {
        HashCalculator hashCalculator = new HashCalculatorImpl();
        String apiKeyHash1 = hashCalculator.hashApiKey(12345L, "apiKeyString");
        String apiKeyHash2 = hashCalculator.hashApiKey(123456L, "apiKeyString");
        String apiKeyHash3 = hashCalculator.hashApiKey(12345L, "apiKeyString");
        String apiKeyHash4 = hashCalculator.hashApiKey(123456L, "apiKeyString");
        assertFalse(apiKeyHash1.equals(apiKeyHash2));
        assertEquals(apiKeyHash1, apiKeyHash3);
        assertEquals(apiKeyHash2, apiKeyHash4);
    }
}
