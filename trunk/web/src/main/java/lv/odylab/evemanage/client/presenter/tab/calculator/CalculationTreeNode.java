package lv.odylab.evemanage.client.presenter.tab.calculator;

import lv.odylab.evemanage.client.rpc.PathExpression;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationItemDto;

import java.math.BigDecimal;
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

    public CalculationTreeNodeSummary getSummary() {
        CalculationTreeNodeSummary summary = new CalculationTreeNodeSummary();
        CalculationItemDto firstCalculationItemDto = calculationItems.get(0);
        PathExpression pathExpression = firstCalculationItemDto.getPathExpression();
        summary.setPathNodesString(pathExpression.getPathNodesString());
        summary.setPathNodes(pathExpression.getPathNodes());
        summary.setItemTypeID(firstCalculationItemDto.getItemTypeID());
        summary.setItemCategoryID(firstCalculationItemDto.getItemCategoryID());
        summary.setItemTypeName(firstCalculationItemDto.getItemTypeName());
        summary.setItemTypeIcon(firstCalculationItemDto.getItemTypeIcon());
        summary.setParentQuantity(firstCalculationItemDto.getParentQuantity());
        summary.setDamagePerJob(firstCalculationItemDto.getDamagePerJob());
        summary.setPrice(firstCalculationItemDto.getPrice());
        if (calculationItems.size() > 1) {
            Long quantity = 0L;
            BigDecimal totalPrice = BigDecimal.ZERO;
            BigDecimal totalPriceForParent = BigDecimal.ZERO;
            for (CalculationItemDto nodeCalculationItem : calculationItems) {
                quantity += nodeCalculationItem.getQuantity();
                totalPrice = totalPrice.add(nodeCalculationItem.getTotalPrice());
                totalPriceForParent = totalPriceForParent.add(nodeCalculationItem.getTotalPriceForParent());
            }
            summary.setQuantity(quantity);
            summary.setTotalPrice(totalPrice);
            summary.setTotalPriceForParent(totalPriceForParent);
        } else {
            summary.setQuantity(firstCalculationItemDto.getQuantity());
            summary.setTotalPrice(firstCalculationItemDto.getTotalPrice());
            summary.setTotalPriceForParent(firstCalculationItemDto.getTotalPriceForParent());
        }
        return summary;
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
