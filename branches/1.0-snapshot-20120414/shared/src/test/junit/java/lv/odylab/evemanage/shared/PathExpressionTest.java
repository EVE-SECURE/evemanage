package lv.odylab.evemanage.shared;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class PathExpressionTest {

    @Test
    public void testRoot_Material() {
        assertThat(new PathExpression(123L, 4, 5, 678L).getExpression(), equalTo("/123:4:5/678"));
    }

    @Test
    public void testRoot_Material_parse() {
        assertThat(PathExpression.parseExpression("/123:4:5/678").getExpression(), equalTo("/123:4:5/678"));
    }

    @Test
    public void testRoot_Requirement() {
        assertThat(new PathExpression(123L, 678L).getExpression(), equalTo("/123/678"));
    }

    @Test
    public void testRoot_Requirement_parse() {
        assertThat(PathExpression.parseExpression("/123/678").getExpression(), equalTo("/123/678"));
    }

    @Test
    public void testChild_Material() {
        assertThat(new PathExpression(new Long[]{123L, 456L, 9L}, 7, 8).getExpression(), equalTo("/123/456:7:8/9"));
    }

    @Test
    public void testChild_Material_parse() {
        assertThat(PathExpression.parseExpression("/123/456:7:8/9").getExpression(), equalTo("/123/456:7:8/9"));
    }

    @Test
    public void testChild_Requirement() {
        assertThat(new PathExpression(new Long[]{123L, 456L, 9L}).getExpression(), equalTo("/123/456/9"));
    }

    @Test
    public void testChild_Requirement_parse() {
        assertThat(PathExpression.parseExpression("/123/456/9").getExpression(), equalTo("/123/456/9"));
    }

    @Test
    public void testChildren_Material() {
        assertThat(new PathExpression(new Long[]{1L, 2L, 3L, 456L}, 7, 8, 9L).getExpression(), equalTo("/1/2/3/456:7:8/9"));
    }

    @Test
    public void testChildren_Material_parse() {
        assertThat(PathExpression.parseExpression("/1/2/3/456:7:8/9").getExpression(), equalTo("/1/2/3/456:7:8/9"));
    }

    @Test
    public void testChildren_Requirement() {
        assertThat(new PathExpression(new Long[]{1L, 2L, 3L, 456L}, 9L).getExpression(), equalTo("/1/2/3/456/9"));
    }

    @Test
    public void testChildren_Requirement_parse() {
        assertThat(PathExpression.parseExpression("/1/2/3/456/9").getExpression(), equalTo("/1/2/3/456/9"));
    }

    @Test
    public void testParentPath() {
        assertThat(PathExpression.parseExpression("/123:4:5/678").getParentPath(), equalTo("/123"));
        assertThat(PathExpression.parseExpression("/123/456:7:8/9").getParentPath(), equalTo("/123/456"));
        assertThat(PathExpression.parseExpression("/1/2/3/456:7:8/9").getParentPath(), equalTo("/1/2/3/456"));
    }

    @Test
    public void testIsRoot() {
        assertTrue(PathExpression.parseExpression("/123:4:5/678").isRootNode());
        assertFalse(PathExpression.parseExpression("/123/456:7:8/9").isRootNode());
        assertFalse(PathExpression.parseExpression("/1/2/3/456:7:8/9").isRootNode());
    }

    @Test
    public void testGetLevel() {
        assertThat(PathExpression.parseExpression("/123:4:5/678").getLevel(), equalTo(1));
        assertThat(PathExpression.parseExpression("/123/456:7:8/9").getLevel(), equalTo(2));
        assertThat(PathExpression.parseExpression("/1/2/3/456:7:8/9").getLevel(), equalTo(4));
    }

    @Test
    public void testCreatingForParentPath_Material() {
        PathExpression pathExpression = PathExpression.parseExpression("/1/2/3/456:-1:-2/7");
        assertThat(new PathExpression(pathExpression, -3, -4, 10L).getExpression(), equalTo("/1/2/3/456/7:-3:-4/10"));
    }

    @Test
    public void testCreatingForParentPath_Requirement() {
        PathExpression pathExpression = PathExpression.parseExpression("/1/2/3/456/7");
        assertThat(new PathExpression(pathExpression, 10L).getExpression(), equalTo("/1/2/3/456/7/10"));
    }

    @Test
    public void testGetPathNodes() {
        Long[] pathNodes = PathExpression.parseExpression("/1/2/3/456:-1:-2/7").getPathNodes();
        assertThat(pathNodes[0], equalTo(1L));
        assertThat(pathNodes[1], equalTo(2L));
        assertThat(pathNodes[2], equalTo(3L));
        assertThat(pathNodes[3], equalTo(456L));
        assertThat(pathNodes[4], equalTo(7L));
    }

    @Test
    public void testGetPathNodesString() {
        assertThat(PathExpression.parseExpression("/1/2/3/456:-1:-2/7").getPathNodesString(), equalTo("/1/2/3/456/7"));
    }

    @Test
    public void testCreatePathNodesStringFromPathNodes() {
        assertThat(PathExpression.createPathNodesStringFromPathNodes(new Long[]{1L, 2L, 3L}), equalTo("/1/2/3"));
    }
}
