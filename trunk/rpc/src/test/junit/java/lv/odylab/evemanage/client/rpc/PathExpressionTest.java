package lv.odylab.evemanage.client.rpc;

import org.junit.Test;

import static junit.framework.Assert.*;

public class PathExpressionTest {

    @Test
    public void testRoot_Material() {
        assertEquals("/123:4:5/678", new PathExpression(123L, 4, 5, 678L).getPath());
    }

    @Test
    public void testRoot_Material_parse() {
        assertEquals("/123:4:5/678", PathExpression.parsePath("/123:4:5/678").getPath());
    }

    @Test
    public void testRoot_Requirement() {
        assertEquals("/123::/678", new PathExpression(123L, 678L).getPath());
    }

    @Test
    public void testRoot_Requirement_parse() {
        assertEquals("/123::/678", PathExpression.parsePath("/123::/678").getPath());
    }

    @Test
    public void testChild_Material() {
        assertEquals("/123/456:7:8/9", new PathExpression(new Long[]{123L, 456L, 9L}, 7, 8).getPath());
    }

    @Test
    public void testChild_Material_parse() {
        assertEquals("/123/456:7:8/9", PathExpression.parsePath("/123/456:7:8/9").getPath());
    }

    @Test
    public void testChild_Requirement() {
        assertEquals("/123/456::/9", new PathExpression(new Long[]{123L, 456L, 9L}).getPath());
    }

    @Test
    public void testChild_Requirement_parse() {
        assertEquals("/123/456::/9", PathExpression.parsePath("/123/456::/9").getPath());
    }

    @Test
    public void testChildren_Material() {
        assertEquals("/1/2/3/456:7:8/9", new PathExpression(new Long[]{1L, 2L, 3L, 456L}, 7, 8, 9L).getPath());
    }

    @Test
    public void testChildren_Material_parse() {
        assertEquals("/1/2/3/456:7:8/9", PathExpression.parsePath("/1/2/3/456:7:8/9").getPath());
    }

    @Test
    public void testChildren_Requirement() {
        assertEquals("/1/2/3/456::/9", new PathExpression(new Long[]{1L, 2L, 3L, 456L}, 9L).getPath());
    }

    @Test
    public void testChildren_Requirement_parse() {
        assertEquals("/1/2/3/456::/9", PathExpression.parsePath("/1/2/3/456::/9").getPath());
    }

    @Test
    public void testParentPath() {
        assertEquals("/123", PathExpression.parsePath("/123:4:5/678").getParentPath());
        assertEquals("/123/456", PathExpression.parsePath("/123/456:7:8/9").getParentPath());
        assertEquals("/1/2/3/456", PathExpression.parsePath("/1/2/3/456:7:8/9").getParentPath());
    }

    @Test
    public void testIsRoot() {
        assertTrue(PathExpression.parsePath("/123:4:5/678").isRootNode());
        assertFalse(PathExpression.parsePath("/123/456:7:8/9").isRootNode());
        assertFalse(PathExpression.parsePath("/1/2/3/456:7:8/9").isRootNode());
    }

    @Test
    public void testGetLevel() {
        assertEquals(Integer.valueOf(1), PathExpression.parsePath("/123:4:5/678").getLevel());
        assertEquals(Integer.valueOf(2), PathExpression.parsePath("/123/456:7:8/9").getLevel());
        assertEquals(Integer.valueOf(4), PathExpression.parsePath("/1/2/3/456:7:8/9").getLevel());
    }

    @Test
    public void testCreatingForParentPath_Material() {
        PathExpression pathExpression = PathExpression.parsePath("/1/2/3/456:-1:-2/7");
        assertEquals("/1/2/3/456/7:-3:-4/10", new PathExpression(pathExpression, -3, -4, 10L).getPath());
    }

    @Test
    public void testCreatingForParentPath_Requirement() {
        PathExpression pathExpression = PathExpression.parsePath("/1/2/3/456::/7");
        assertEquals("/1/2/3/456/7::/10", new PathExpression(pathExpression, 10L).getPath());
    }

    @Test
    public void testGetPathNodes() {
        Long[] pathNodes = PathExpression.parsePath("/1/2/3/456:-1:-2/7").getPathNodes();
        assertEquals(Long.valueOf(1), pathNodes[0]);
        assertEquals(Long.valueOf(2), pathNodes[1]);
        assertEquals(Long.valueOf(3), pathNodes[2]);
        assertEquals(Long.valueOf(456), pathNodes[3]);
        assertEquals(Long.valueOf(7), pathNodes[4]);
    }

    @Test
    public void testGetPathNodesString() {
        assertEquals("/1/2/3/456/7", PathExpression.parsePath("/1/2/3/456:-1:-2/7").getPathNodesString());
    }
}
