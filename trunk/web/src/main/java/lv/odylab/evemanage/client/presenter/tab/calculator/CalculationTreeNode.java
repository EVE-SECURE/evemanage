package lv.odylab.evemanage.client.presenter.tab.calculator;

import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationItemDto;

import java.util.Map;
import java.util.TreeMap;

public class CalculationTreeNode {
    private CalculationItemDto calculationItem;
    private Map<Long, CalculationTreeNode> nodeMap = new TreeMap<Long, CalculationTreeNode>();
    private Boolean excludeChildNodesFromCalculation = false;

    public CalculationTreeNode() {
    }

    public CalculationItemDto getCalculationItem() {
        return calculationItem;
    }

    public void setCalculationItem(CalculationItemDto calculationItem) {
        this.calculationItem = calculationItem;
    }

    public Map<Long, CalculationTreeNode> getNodeMap() {
        return nodeMap;
    }

    public Boolean isExcludeChildNodesFromCalculation() {
        return excludeChildNodesFromCalculation;
    }

    public void setExcludeChildNodesFromCalculation(Boolean excludeChildNodesFromCalculation) {
        this.excludeChildNodesFromCalculation = excludeChildNodesFromCalculation;
    }

    public void changeMePe(Integer meLevel, Integer peLevel) {
        calculationItem.getPathExpression().setMeLevel(meLevel);
        calculationItem.getPathExpression().setPeLevel(peLevel);
        for (Map.Entry<Long, CalculationTreeNode> mapEntry : nodeMap.entrySet()) {
            CalculationTreeNode node = mapEntry.getValue();
            CalculationItemDto calculationItem = node.getCalculationItem();
            calculationItem.getPathExpression().setMeLevel(meLevel);
            calculationItem.getPathExpression().setPeLevel(peLevel);
        }
    }
}
