package lv.odylab.evemanage.client.presenter.tab.calculator;

import lv.odylab.evemanage.client.rpc.EveCalculator;
import lv.odylab.evemanage.client.rpc.PathExpression;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationItemDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CalculationTreeNode {
    private List<CalculationItemDto> calculationItems = new ArrayList<CalculationItemDto>();
    private Map<Long, CalculationTreeNode> nodeMap = new TreeMap<Long, CalculationTreeNode>();
    private Boolean excludeChildNodesFromCalculation = false;

    public CalculationTreeNode() {
    }

    public List<CalculationItemDto> getCalculationItems() {
        return calculationItems;
    }

    public void addCalculationItem(CalculationItemDto calculationItem) {
        calculationItems.add(calculationItem);
    }

    public CalculationItemDto getMergedCalculationItem(EveCalculator calculator) {
        if (calculationItems.size() > 1) {
            CalculationItemDto calculationItem = new CalculationItemDto();
            CalculationItemDto firstCalculationItemDto = calculationItems.get(0);
            calculationItem.setPathExpression(firstCalculationItemDto.getPathExpression());
            calculationItem.setItemTypeID(firstCalculationItemDto.getItemTypeID());
            calculationItem.setItemCategoryID(firstCalculationItemDto.getItemCategoryID());
            calculationItem.setItemTypeName(firstCalculationItemDto.getItemTypeName());
            calculationItem.setItemTypeIcon(firstCalculationItemDto.getItemTypeIcon());
            calculationItem.setParentQuantity(firstCalculationItemDto.getParentQuantity());
            calculationItem.setPrice(firstCalculationItemDto.getPrice());
            List<Long> sumQuantity = new ArrayList<Long>();
            List<Long> sumPerfectQuantity = new ArrayList<Long>();
            List<String> sumTotalPrice = new ArrayList<String>();
            List<String> sumTotalPriceForParent = new ArrayList<String>();
            for (CalculationItemDto nodeCalculationItem : calculationItems) {
                sumQuantity.add(nodeCalculationItem.getQuantity());
                sumPerfectQuantity.add(nodeCalculationItem.getPerfectQuantity());
                sumTotalPrice.add(nodeCalculationItem.getTotalPrice());
                sumTotalPriceForParent.add(nodeCalculationItem.getTotalPriceForParent());
            }
            calculationItem.setQuantity(calculator.sum(sumQuantity));
            calculationItem.setPerfectQuantity(calculator.sum(sumPerfectQuantity));
            calculationItem.setTotalPrice(calculator.sum(sumTotalPrice));
            calculationItem.setTotalPriceForParent(calculator.sum(sumTotalPriceForParent));
            return calculationItem;
        } else {
            return calculationItems.get(0);
        }
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
        for (CalculationItemDto calculationItem : calculationItems) {
            PathExpression pathExpression = calculationItem.getPathExpression();
            if (pathExpression.isMaterial()) {
                pathExpression.setMeLevel(meLevel);
                pathExpression.setPeLevel(peLevel);
            }
            for (Map.Entry<Long, CalculationTreeNode> mapEntry : nodeMap.entrySet()) {
                CalculationTreeNode node = mapEntry.getValue();
                List<CalculationItemDto> nodeCalculationItems = node.getCalculationItems();
                for (CalculationItemDto nodeCalculationItem : nodeCalculationItems) {
                    PathExpression nodePathExpression = nodeCalculationItem.getPathExpression();
                    if (nodePathExpression.isMaterial()) {
                        nodePathExpression.setMeLevel(meLevel);
                        nodePathExpression.setPeLevel(peLevel);
                    }
                }
            }
        }
    }
}
