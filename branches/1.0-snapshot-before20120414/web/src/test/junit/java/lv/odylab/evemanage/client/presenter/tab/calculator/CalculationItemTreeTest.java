package lv.odylab.evemanage.client.presenter.tab.calculator;

import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationItemDto;
import lv.odylab.evemanage.shared.PathExpression;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

public class CalculationItemTreeTest {
    private CalculationItemTree calculationItemTree;

    @Before
    public void setUp() {
        calculationItemTree = new CalculationItemTree();
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
        calculation.setCalculationItems(calculationItems);
        calculationItemTree.build(calculation);

        assertNotNull(calculationItemTree.getNodeByPathNodes(new Long[]{1L, 2L, 3L}));
        assertNotNull(calculationItemTree.getNodeByPathNodes(new Long[]{1L, 2L, 4L}));
    }

    @Test
    public void testCreateNode_FirstNodeMustBeIgnored() {
        CalculationItemDto calculationItem = new CalculationItemDto();
        calculationItem.setPathExpression(new PathExpression(new Long[]{1L, 2L, 3L}));
        calculationItemTree.createNode(calculationItem);

        Map<Long, CalculationItemTreeNode> nodeMap = calculationItemTree.getNodeMap();
        assertNull(nodeMap.get(1L));
        CalculationItemTreeNode node2 = nodeMap.get(2L);
        assertNotNull(node2);
        nodeMap = node2.getNodeMap();
        CalculationItemTreeNode node3 = nodeMap.get(3L);
        assertNotNull(node3);
    }

    @Test
    public void testCreateNode_AddingChildren() {
        CalculationItemDto calculationItem1 = new CalculationItemDto();
        calculationItem1.setPathExpression(new PathExpression(new Long[]{1L, 2L, 3L}));
        calculationItemTree.createNode(calculationItem1);
        CalculationItemDto calculationItem2 = new CalculationItemDto();
        calculationItem2.setPathExpression(new PathExpression(new Long[]{1L, 2L, 4L}));
        calculationItemTree.createNode(calculationItem2);

        Map<Long, CalculationItemTreeNode> nodeMap = calculationItemTree.getNodeMap().get(2L).getNodeMap();
        CalculationItemTreeNode node3 = nodeMap.get(3L);
        CalculationItemTreeNode node4 = nodeMap.get(4L);
        assertNotNull(node3);
        assertNotNull(node4);
    }

    @Test
    public void testCreateNode_AddingManyChildren() {
        CalculationItemDto calculationItem1 = new CalculationItemDto();
        calculationItem1.setPathExpression(new PathExpression(new Long[]{1L, 2L, 3L}));
        calculationItemTree.createNode(calculationItem1);
        CalculationItemDto calculationItem2 = new CalculationItemDto();
        calculationItem2.setPathExpression(new PathExpression(new Long[]{1L, 2L, 4L}));
        calculationItemTree.createNode(calculationItem2);
        CalculationItemDto calculationItem3 = new CalculationItemDto();
        calculationItem3.setPathExpression(new PathExpression(new Long[]{1L, 5L, 6L}));
        calculationItemTree.createNode(calculationItem3);
        CalculationItemDto calculationItem4 = new CalculationItemDto();
        calculationItem4.setPathExpression(new PathExpression(new Long[]{1L, 5L, 7L}));
        calculationItemTree.createNode(calculationItem4);

        Map<Long, CalculationItemTreeNode> nodeMap = calculationItemTree.getNodeMap().get(2L).getNodeMap();
        CalculationItemTreeNode node3 = nodeMap.get(3L);
        CalculationItemTreeNode node4 = nodeMap.get(4L);
        nodeMap = calculationItemTree.getNodeMap().get(5L).getNodeMap();
        CalculationItemTreeNode node6 = nodeMap.get(6L);
        CalculationItemTreeNode node7 = nodeMap.get(7L);
        assertNotNull(node3);
        assertNotNull(node4);
        assertNotNull(node6);
        assertNotNull(node7);
    }

    @Test
    public void testGetNodeByPathNodes() {
        CalculationItemDto calculationItem = new CalculationItemDto();
        calculationItem.setPathExpression(new PathExpression(new Long[]{1L, 2L, 3L}));
        calculationItemTree.createNode(calculationItem);

        assertNotNull(calculationItemTree.getNodeByPathNodes(new Long[]{1L, 2L, 3L}));
    }

    @Test
    public void testGetNodeByPathNodes_Many() {
        CalculationItemDto calculationItem1 = new CalculationItemDto();
        calculationItem1.setPathExpression(new PathExpression(new Long[]{1L, 2L, 3L}));
        calculationItemTree.createNode(calculationItem1);
        CalculationItemDto calculationItem2 = new CalculationItemDto();
        calculationItem2.setPathExpression(new PathExpression(new Long[]{1L, 2L, 4L}));
        calculationItemTree.createNode(calculationItem2);
        CalculationItemDto calculationItem3 = new CalculationItemDto();
        calculationItem3.setPathExpression(new PathExpression(new Long[]{1L, 5L, 6L}));
        calculationItemTree.createNode(calculationItem3);
        CalculationItemDto calculationItem4 = new CalculationItemDto();
        calculationItem4.setPathExpression(new PathExpression(new Long[]{1L, 5L, 7L}));
        calculationItemTree.createNode(calculationItem4);

        assertNotNull(calculationItemTree.getNodeByPathNodes(new Long[]{1L, 2L, 3L}));
        assertNotNull(calculationItemTree.getNodeByPathNodes(new Long[]{1L, 2L, 4L}));
        assertNotNull(calculationItemTree.getNodeByPathNodes(new Long[]{1L, 5L, 6L}));
        assertNotNull(calculationItemTree.getNodeByPathNodes(new Long[]{1L, 5L, 7L}));
    }
}
