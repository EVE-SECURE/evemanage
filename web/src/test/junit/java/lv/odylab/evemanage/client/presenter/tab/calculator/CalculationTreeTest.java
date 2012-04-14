package lv.odylab.evemanage.client.presenter.tab.calculator;

import lv.odylab.evemanage.client.rpc.PathExpression;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationItemDto;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

public class CalculationTreeTest {
    private CalculationTree calculationTree;

    @Before
    public void setUp() {
        calculationTree = new CalculationTree();
    }

    @Test
    public void testBuild() {
        CalculationDto calculation = new CalculationDto();
        CalculationItemDto calculationItem1 = new CalculationItemDto();
        calculationItem1.setPathExpression(new PathExpression(new Long[]{1L, 2L, 3L}));
        CalculationItemDto calculationItem2 = new CalculationItemDto();
        calculationItem2.setPathExpression(new PathExpression(new Long[]{1L, 2L, 4L}));
        List<CalculationItemDto> calculationItems = new ArrayList<CalculationItemDto>();
        calculationItems.add(calculationItem1);
        calculationItems.add(calculationItem2);
        calculation.setItems(calculationItems);
        calculationTree.build(calculation);

        assertNotNull(calculationTree.getNodeByPathNodes(new Long[]{1L, 2L, 3L}));
        assertNotNull(calculationTree.getNodeByPathNodes(new Long[]{1L, 2L, 4L}));
    }

    @Test
    public void testCreateNode_FirstNodeMustBeIgnored() {
        CalculationItemDto calculationItem = new CalculationItemDto();
        calculationItem.setPathExpression(new PathExpression(new Long[]{1L, 2L, 3L}));
        calculationTree.createNode(calculationItem);

        Map<Long, CalculationTreeNode> nodeMap = calculationTree.getNodeMap();
        assertNull(nodeMap.get(1L));
        CalculationTreeNode node2 = nodeMap.get(2L);
        assertNotNull(node2);
        nodeMap = node2.getNodeMap();
        CalculationTreeNode node3 = nodeMap.get(3L);
        assertNotNull(node3);
    }

    @Test
    public void testCreateNode_AddingChildren() {
        CalculationItemDto calculationItem1 = new CalculationItemDto();
        calculationItem1.setPathExpression(new PathExpression(new Long[]{1L, 2L, 3L}));
        calculationTree.createNode(calculationItem1);
        CalculationItemDto calculationItem2 = new CalculationItemDto();
        calculationItem2.setPathExpression(new PathExpression(new Long[]{1L, 2L, 4L}));
        calculationTree.createNode(calculationItem2);

        Map<Long, CalculationTreeNode> nodeMap = calculationTree.getNodeMap().get(2L).getNodeMap();
        CalculationTreeNode node3 = nodeMap.get(3L);
        CalculationTreeNode node4 = nodeMap.get(4L);
        assertNotNull(node3);
        assertNotNull(node4);
    }

    @Test
    public void testCreateNode_AddingManyChildren() {
        CalculationItemDto calculationItem1 = new CalculationItemDto();
        calculationItem1.setPathExpression(new PathExpression(new Long[]{1L, 2L, 3L}));
        calculationTree.createNode(calculationItem1);
        CalculationItemDto calculationItem2 = new CalculationItemDto();
        calculationItem2.setPathExpression(new PathExpression(new Long[]{1L, 2L, 4L}));
        calculationTree.createNode(calculationItem2);
        CalculationItemDto calculationItem3 = new CalculationItemDto();
        calculationItem3.setPathExpression(new PathExpression(new Long[]{1L, 5L, 6L}));
        calculationTree.createNode(calculationItem3);
        CalculationItemDto calculationItem4 = new CalculationItemDto();
        calculationItem4.setPathExpression(new PathExpression(new Long[]{1L, 5L, 7L}));
        calculationTree.createNode(calculationItem4);

        Map<Long, CalculationTreeNode> nodeMap = calculationTree.getNodeMap().get(2L).getNodeMap();
        CalculationTreeNode node3 = nodeMap.get(3L);
        CalculationTreeNode node4 = nodeMap.get(4L);
        nodeMap = calculationTree.getNodeMap().get(5L).getNodeMap();
        CalculationTreeNode node6 = nodeMap.get(6L);
        CalculationTreeNode node7 = nodeMap.get(7L);
        assertNotNull(node3);
        assertNotNull(node4);
        assertNotNull(node6);
        assertNotNull(node7);
    }

    @Test
    public void testGetNodeByPathNodes() {
        CalculationItemDto calculationItem = new CalculationItemDto();
        calculationItem.setPathExpression(new PathExpression(new Long[]{1L, 2L, 3L}));
        calculationTree.createNode(calculationItem);

        assertNotNull(calculationTree.getNodeByPathNodes(new Long[]{1L, 2L, 3L}));
    }

    @Test
    public void testGetNodeByPathNodes_Many() {
        CalculationItemDto calculationItem1 = new CalculationItemDto();
        calculationItem1.setPathExpression(new PathExpression(new Long[]{1L, 2L, 3L}));
        calculationTree.createNode(calculationItem1);
        CalculationItemDto calculationItem2 = new CalculationItemDto();
        calculationItem2.setPathExpression(new PathExpression(new Long[]{1L, 2L, 4L}));
        calculationTree.createNode(calculationItem2);
        CalculationItemDto calculationItem3 = new CalculationItemDto();
        calculationItem3.setPathExpression(new PathExpression(new Long[]{1L, 5L, 6L}));
        calculationTree.createNode(calculationItem3);
        CalculationItemDto calculationItem4 = new CalculationItemDto();
        calculationItem4.setPathExpression(new PathExpression(new Long[]{1L, 5L, 7L}));
        calculationTree.createNode(calculationItem4);

        assertNotNull(calculationTree.getNodeByPathNodes(new Long[]{1L, 2L, 3L}));
        assertNotNull(calculationTree.getNodeByPathNodes(new Long[]{1L, 2L, 4L}));
        assertNotNull(calculationTree.getNodeByPathNodes(new Long[]{1L, 5L, 6L}));
        assertNotNull(calculationTree.getNodeByPathNodes(new Long[]{1L, 5L, 7L}));
    }
}
