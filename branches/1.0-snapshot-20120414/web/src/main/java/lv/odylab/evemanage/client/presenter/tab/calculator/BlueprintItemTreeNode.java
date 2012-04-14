package lv.odylab.evemanage.client.presenter.tab.calculator;

import lv.odylab.evemanage.client.rpc.dto.calculation.BlueprintItemDto;

import java.util.Map;
import java.util.TreeMap;

public class BlueprintItemTreeNode {
    private BlueprintItemDto blueprintItem;
    private Map<Long, BlueprintItemTreeNode> nodeMap = new TreeMap<Long, BlueprintItemTreeNode>();
    private Boolean excludeNodeFromCalculation = false;

    public BlueprintItemDto getBlueprintItem() {
        return blueprintItem;
    }

    public void setBlueprintItem(BlueprintItemDto blueprintItem) {
        this.blueprintItem = blueprintItem;
    }

    public Map<Long, BlueprintItemTreeNode> getNodeMap() {
        return nodeMap;
    }

    public Boolean isExcludeNodeCalculation() {
        return excludeNodeFromCalculation;
    }

    public void setExcludeNodeFromCalculation(Boolean excludeNodeFromCalculation) {
        this.excludeNodeFromCalculation = excludeNodeFromCalculation;
    }
}
