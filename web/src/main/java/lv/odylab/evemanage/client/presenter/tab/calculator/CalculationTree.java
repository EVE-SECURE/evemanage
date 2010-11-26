package lv.odylab.evemanage.client.presenter.tab.calculator;

import lv.odylab.evemanage.client.rpc.CalculationExpression;
import lv.odylab.evemanage.client.rpc.PathExpression;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationItemDto;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CalculationTree {
    private Map<Long, CalculationTreeNode> nodeMap = new TreeMap<Long, CalculationTreeNode>();

    public Map<Long, CalculationTreeNode> getNodeMap() {
        return nodeMap;
    }

    public void build(CalculationDto calculation) {
        for (CalculationItemDto calculationItem : calculation.getItems()) {
            createNode(calculationItem);
        }
    }

    public void createNode(CalculationItemDto calculationItem) {
        PathExpression pathExpression = calculationItem.getPathExpression();
        Long[] pathNodes = pathExpression.getPathNodes();
        Map<Long, CalculationTreeNode> currentNodes = nodeMap;
        for (int i = 1; i < pathNodes.length - 1; i++) {
            Long pathNode = pathNodes[i];
            CalculationTreeNode node = currentNodes.get(pathNode);
            if (node == null) {
                node = new CalculationTreeNode();
                currentNodes.put(pathNode, node);
            }
            currentNodes = node.getNodeMap();
        }
        Long lastPathNode = pathNodes[pathNodes.length - 1];
        CalculationTreeNode calculationTreeNode = currentNodes.get(lastPathNode);
        if (calculationTreeNode == null) {
            calculationTreeNode = new CalculationTreeNode();
            currentNodes.put(lastPathNode, calculationTreeNode);
        }
        calculationTreeNode.addCalculationItem(calculationItem);
    }

    public CalculationTreeNode getNodeByPathNodes(Long[] pathNodes) {
        CalculationTreeNode calculationTreeNode = null;
        Map<Long, CalculationTreeNode> currentNodeMap = nodeMap;
        for (int i = 1; i < pathNodes.length; i++) {
            calculationTreeNode = currentNodeMap.get(pathNodes[i]);
            currentNodeMap = calculationTreeNode.getNodeMap();
        }
        return calculationTreeNode;
    }

    public void changeRootNodesMePeQuantity(Integer meLevel, Integer peLevel, Long quantity) {
        for (Map.Entry<Long, CalculationTreeNode> mapEntry : nodeMap.entrySet()) {
            CalculationTreeNode node = mapEntry.getValue();
            List<CalculationItemDto> calculationItems = node.getCalculationItems();
            for (CalculationItemDto calculationItem : calculationItems) {
                PathExpression pathExpression = calculationItem.getPathExpression();
                if (pathExpression.isMaterial()) {
                    pathExpression.setMeLevel(meLevel);
                    pathExpression.setPeLevel(peLevel);
                }
                calculationItem.setParentQuantity(quantity);
            }
        }
    }

    public void setPrices(Map<Long, BigDecimal> typeIdToPriceMap) {
        for (CalculationTreeNode node : nodeMap.values()) {
            recursivelySetPrices(typeIdToPriceMap, node);
        }
    }

    private void recursivelySetPrices(Map<Long, BigDecimal> typeIdToPriceMap, CalculationTreeNode calculationTreeNode) {
        List<CalculationItemDto> calculationItems = calculationTreeNode.getCalculationItems();
        for (CalculationItemDto calculationItem : calculationItems) {
            if (typeIdToPriceMap.containsKey(calculationItem.getItemTypeID())) {
                calculationItem.setPrice(typeIdToPriceMap.get(calculationItem.getItemTypeID()));
            }
            for (CalculationTreeNode node : calculationTreeNode.getNodeMap().values()) {
                recursivelySetPrices(typeIdToPriceMap, node);
            }
        }
    }

    public void removeAllNodes() {
        nodeMap.clear();
    }

    public void populateCalculationExpressionWithBlueprintInformation(CalculationExpression calculationExpression) {
        Map<String, PathExpression> pathNodesToPathExpressionMap = new HashMap<String, PathExpression>();
        for (Map.Entry<Long, CalculationTreeNode> node : nodeMap.entrySet()) {
            String pathNodes = String.valueOf(node.getKey());
            CalculationTreeNode calculationTreeNode = node.getValue();
            Map<Long, CalculationTreeNode> calculationTreeNodeMap = calculationTreeNode.getNodeMap();
            if (calculationTreeNodeMap.size() > 0 && !calculationTreeNode.isExcludeChildNodesFromCalculation()) {
                pathNodesToPathExpressionMap.put(pathNodes, calculationTreeNodeMap.values().iterator().next().getCalculationItems().get(0).getPathExpression());
                recursivelyPopulateCalculationExpressionWithBlueprintInformation(pathNodes, calculationTreeNode, pathNodesToPathExpressionMap);
            }
        }
        for (Map.Entry<String, PathExpression> entry : pathNodesToPathExpressionMap.entrySet()) {
            String blueprintPath = entry.getKey();
            PathExpression pathExpression = entry.getValue();
            calculationExpression.getBlueprintPathToMeLevelMap().put(blueprintPath, pathExpression.getMeLevel());
            calculationExpression.getBlueprintPathToPeLevelMap().put(blueprintPath, pathExpression.getPeLevel());
        }
    }

    private void recursivelyPopulateCalculationExpressionWithBlueprintInformation(String parentPathNodes, CalculationTreeNode treeNode, Map<String, PathExpression> pathNodesToPathExpressionMap) {
        for (Map.Entry<Long, CalculationTreeNode> node : treeNode.getNodeMap().entrySet()) {
            String pathNodes = parentPathNodes + "/" + String.valueOf(node.getKey());
            CalculationTreeNode calculationTreeNode = node.getValue();
            Map<Long, CalculationTreeNode> calculationTreeNodeMap = calculationTreeNode.getNodeMap();
            if (calculationTreeNodeMap.size() > 0 && !calculationTreeNode.isExcludeChildNodesFromCalculation()) {
                pathNodesToPathExpressionMap.put(pathNodes, calculationTreeNodeMap.values().iterator().next().getCalculationItems().get(0).getPathExpression());
                recursivelyPopulateCalculationExpressionWithBlueprintInformation(pathNodes, calculationTreeNode, pathNodesToPathExpressionMap);
            }
        }
    }

    public void populateCalculationExpressionWithPriceInformation(CalculationExpression calculationExpression) {
        Map<Long, BigDecimal> typeIdToPriceMap = new HashMap<Long, BigDecimal>();
        for (Map.Entry<Long, CalculationTreeNode> node : nodeMap.entrySet()) {
            CalculationTreeNode calculationTreeNode = node.getValue();
            if (calculationTreeNode.getNodeMap().size() > 0 && !calculationTreeNode.isExcludeChildNodesFromCalculation()) {
                recursivelyPopulateCalculationExpressionWithPriceInformation(calculationTreeNode, typeIdToPriceMap);
            } else {
                CalculationItemDto calculationItem = calculationTreeNode.getCalculationItems().get(0);
                typeIdToPriceMap.put(calculationItem.getItemTypeID(), calculationItem.getPrice());
            }
        }
        for (Map.Entry<Long, BigDecimal> entry : typeIdToPriceMap.entrySet()) {
            Long typeID = entry.getKey();
            BigDecimal price = entry.getValue();
            if (price.compareTo(BigDecimal.ZERO) > 0) {
                calculationExpression.getPriceSetItemTypeIdToPriceMap().put(typeID, price.toPlainString());
            }
        }
    }

    private void recursivelyPopulateCalculationExpressionWithPriceInformation(CalculationTreeNode treeNode, Map<Long, BigDecimal> typeIdToPriceMap) {
        for (Map.Entry<Long, CalculationTreeNode> node : treeNode.getNodeMap().entrySet()) {
            CalculationTreeNode calculationTreeNode = node.getValue();
            if (calculationTreeNode.getNodeMap().size() > 0 && !calculationTreeNode.isExcludeChildNodesFromCalculation()) {
                recursivelyPopulateCalculationExpressionWithPriceInformation(calculationTreeNode, typeIdToPriceMap);
            } else {
                CalculationItemDto calculationItem = calculationTreeNode.getCalculationItems().get(0);
                typeIdToPriceMap.put(calculationItem.getItemTypeID(), calculationItem.getPrice());
            }
        }
    }
}
