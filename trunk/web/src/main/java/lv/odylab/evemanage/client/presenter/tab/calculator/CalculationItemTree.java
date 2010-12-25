package lv.odylab.evemanage.client.presenter.tab.calculator;

import lv.odylab.evemanage.client.rpc.PathExpression;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationItemDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CalculationItemTree {
    private Map<Long, CalculationItemTreeNode> nodeMap = new TreeMap<Long, CalculationItemTreeNode>();

    public Map<Long, CalculationItemTreeNode> getNodeMap() {
        return nodeMap;
    }

    public void build(CalculationDto calculation) {
        nodeMap.clear();
        for (CalculationItemDto calculationItem : calculation.getCalculationItems()) {
            createNode(calculationItem);
        }
    }

    public void createNode(CalculationItemDto calculationItem) {
        PathExpression pathExpression = calculationItem.getPathExpression();
        Long[] pathNodes = pathExpression.getPathNodes();
        Map<Long, CalculationItemTreeNode> currentNodes = nodeMap;
        for (int i = 1; i < pathNodes.length - 1; i++) {
            Long pathNode = pathNodes[i];
            CalculationItemTreeNode node = currentNodes.get(pathNode);
            if (node == null) {
                node = new CalculationItemTreeNode();
                currentNodes.put(pathNode, node);
            }
            currentNodes = node.getNodeMap();
        }
        Long lastPathNode = pathNodes[pathNodes.length - 1];
        CalculationItemTreeNode calculationItemTreeNode = currentNodes.get(lastPathNode);
        if (calculationItemTreeNode == null) {
            calculationItemTreeNode = new CalculationItemTreeNode();
            currentNodes.put(lastPathNode, calculationItemTreeNode);
        }
        calculationItemTreeNode.addCalculationItem(calculationItem);
    }

    public CalculationItemTreeNode getNodeByPathNodes(Long[] pathNodes) {
        CalculationItemTreeNode calculationItemTreeNode = null;
        Map<Long, CalculationItemTreeNode> currentNodeMap = nodeMap;
        for (int i = 1; i < pathNodes.length; i++) {
            calculationItemTreeNode = currentNodeMap.get(pathNodes[i]);
            currentNodeMap = calculationItemTreeNode.getNodeMap();
        }
        return calculationItemTreeNode;
    }

    public void changeRootNodesMePeQuantity(Integer meLevel, Integer peLevel, Long quantity) {
        for (Map.Entry<Long, CalculationItemTreeNode> mapEntry : nodeMap.entrySet()) {
            CalculationItemTreeNode node = mapEntry.getValue();
            List<CalculationItemDto> calculationItems = node.getCalculationItems();
            for (CalculationItemDto calculationItem : calculationItems) {
                PathExpression pathExpression = calculationItem.getPathExpression();
                if (pathExpression.hasMeFactoring()) {
                    pathExpression.setMeLevel(meLevel);
                    pathExpression.setPeLevel(peLevel);
                }
                calculationItem.setParentQuantity(quantity);
            }
        }
    }

    public void setPrices(Map<Long, BigDecimal> typeIdToPriceMap) {
        for (CalculationItemTreeNode node : nodeMap.values()) {
            recursivelySetPrices(typeIdToPriceMap, node);
        }
    }

    private void recursivelySetPrices(Map<Long, BigDecimal> typeIdToPriceMap, CalculationItemTreeNode calculationItemTreeNode) {
        List<CalculationItemDto> calculationItems = calculationItemTreeNode.getCalculationItems();
        for (CalculationItemDto calculationItem : calculationItems) {
            if (typeIdToPriceMap.containsKey(calculationItem.getItemTypeID())) {
                calculationItem.setPrice(typeIdToPriceMap.get(calculationItem.getItemTypeID()));
            }
            for (CalculationItemTreeNode node : calculationItemTreeNode.getNodeMap().values()) {
                recursivelySetPrices(typeIdToPriceMap, node);
            }
        }
    }

    /*public void populateCalculationExpressionWithBlueprintInformation(CalculationExpression calculationExpression) {
        Map<String, PathExpression> pathNodesToPathExpressionMap = new HashMap<String, PathExpression>();
        for (Map.Entry<Long, CalculationItemTreeNode> node : nodeMap.entrySet()) {
            String pathNodes = String.valueOf(node.getKey());
            CalculationItemTreeNode calculationItemTreeNode = node.getValue();
            Map<Long, CalculationItemTreeNode> calculationTreeNodeMap = calculationItemTreeNode.getNodeMap();
            if (calculationTreeNodeMap.size() > 0 && !calculationItemTreeNode.isExcludeChildNodesFromCalculation()) {
                pathNodesToPathExpressionMap.put(pathNodes, calculationTreeNodeMap.values().iterator().next().getCalculationItems().get(0).getPathExpression());
                recursivelyPopulateCalculationExpressionWithBlueprintInformation(pathNodes, calculationItemTreeNode, pathNodesToPathExpressionMap);
            }
        }
        for (Map.Entry<String, PathExpression> entry : pathNodesToPathExpressionMap.entrySet()) {
            String blueprintPath = entry.getKey();
            PathExpression pathExpression = entry.getValue();
            calculationExpression.getBlueprintPathToMeLevelMap().put(blueprintPath, pathExpression.getMeLevel());
            calculationExpression.getBlueprintPathToPeLevelMap().put(blueprintPath, pathExpression.getPeLevel());
        }
    }

    private void recursivelyPopulateCalculationExpressionWithBlueprintInformation(String parentPathNodes, CalculationItemTreeNode treeNode, Map<String, PathExpression> pathNodesToPathExpressionMap) {
        for (Map.Entry<Long, CalculationItemTreeNode> node : treeNode.getNodeMap().entrySet()) {
            String pathNodes = parentPathNodes + "/" + String.valueOf(node.getKey());
            CalculationItemTreeNode calculationItemTreeNode = node.getValue();
            Map<Long, CalculationItemTreeNode> calculationTreeNodeMap = calculationItemTreeNode.getNodeMap();
            if (calculationTreeNodeMap.size() > 0 && !calculationItemTreeNode.isExcludeChildNodesFromCalculation()) {
                pathNodesToPathExpressionMap.put(pathNodes, calculationTreeNodeMap.values().iterator().next().getCalculationItems().get(0).getPathExpression());
                recursivelyPopulateCalculationExpressionWithBlueprintInformation(pathNodes, calculationItemTreeNode, pathNodesToPathExpressionMap);
            }
        }
    }*/

    /*public void populateCalculationExpressionWithPriceInformation(CalculationExpression calculationExpression) {
        Map<Long, BigDecimal> typeIdToPriceMap = new HashMap<Long, BigDecimal>();
        for (Map.Entry<Long, CalculationItemTreeNode> node : nodeMap.entrySet()) {
            CalculationItemTreeNode calculationItemTreeNode = node.getValue();
            if (calculationItemTreeNode.getNodeMap().size() > 0 && !calculationItemTreeNode.isExcludeChildNodesFromCalculation()) {
                recursivelyPopulateCalculationExpressionWithPriceInformation(calculationItemTreeNode, typeIdToPriceMap);
            } else {
                CalculationItemDto calculationItem = calculationItemTreeNode.getCalculationItems().get(0);
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

    private void recursivelyPopulateCalculationExpressionWithPriceInformation(CalculationItemTreeNode treeNode, Map<Long, BigDecimal> typeIdToPriceMap) {
        for (Map.Entry<Long, CalculationItemTreeNode> node : treeNode.getNodeMap().entrySet()) {
            CalculationItemTreeNode calculationItemTreeNode = node.getValue();
            if (calculationItemTreeNode.getNodeMap().size() > 0 && !calculationItemTreeNode.isExcludeChildNodesFromCalculation()) {
                recursivelyPopulateCalculationExpressionWithPriceInformation(calculationItemTreeNode, typeIdToPriceMap);
            } else {
                CalculationItemDto calculationItem = calculationItemTreeNode.getCalculationItems().get(0);
                typeIdToPriceMap.put(calculationItem.getItemTypeID(), calculationItem.getPrice());
            }
        }
    }*/
}
