package lv.odylab.evemanage.client.presenter.tab.calculator;

import lv.odylab.evemanage.client.rpc.PathExpression;
import lv.odylab.evemanage.client.rpc.dto.calculation.BlueprintItemDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BlueprintItemTree {
    private Map<Long, BlueprintItemTreeNode> nodeMap = new TreeMap<Long, BlueprintItemTreeNode>();

    public Map<Long, BlueprintItemTreeNode> getNodeMap() {
        return nodeMap;
    }

    public void build(CalculationDto calculation) {
        nodeMap.clear();
        for (BlueprintItemDto calculationItem : calculation.getBlueprintItems()) {
            createNode(calculationItem);
        }
    }

    public void createNode(BlueprintItemDto blueprintItem) {
        PathExpression pathExpression = blueprintItem.getPathExpression();
        Long[] pathNodes = pathExpression.getPathNodes();
        Map<Long, BlueprintItemTreeNode> currentNodes = nodeMap;
        for (int i = 1; i < pathNodes.length - 1; i++) {
            Long pathNode = pathNodes[i];
            BlueprintItemTreeNode node = currentNodes.get(pathNode);
            if (node == null) {
                node = new BlueprintItemTreeNode();
                currentNodes.put(pathNode, node);
            }
            currentNodes = node.getNodeMap();
        }
        Long lastPathNode = pathNodes[pathNodes.length - 1];
        BlueprintItemTreeNode blueprintItemTreeNode = currentNodes.get(lastPathNode);
        if (blueprintItemTreeNode == null) {
            blueprintItemTreeNode = new BlueprintItemTreeNode();
            currentNodes.put(lastPathNode, blueprintItemTreeNode);
        }
        blueprintItemTreeNode.setBlueprintItem(blueprintItem);
    }

    public BlueprintItemTreeNode getNodeByPathNodes(Long[] pathNodes) {
        BlueprintItemTreeNode blueprintItemTreeNode = null;
        Map<Long, BlueprintItemTreeNode> currentNodeMap = nodeMap;
        for (int i = 1; i < pathNodes.length; i++) {
            blueprintItemTreeNode = currentNodeMap.get(pathNodes[i]);
            currentNodeMap = blueprintItemTreeNode.getNodeMap();
        }
        return blueprintItemTreeNode;
    }

    public void addInventedBlueprintNodes(List<BlueprintItemDto> blueprintItems) {
        for (BlueprintItemDto blueprintItem : blueprintItems) {
            createNode(blueprintItem);
        }
    }
}
