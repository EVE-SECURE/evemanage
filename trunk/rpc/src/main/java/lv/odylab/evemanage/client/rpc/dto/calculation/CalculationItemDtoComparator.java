package lv.odylab.evemanage.client.rpc.dto.calculation;

import java.util.Comparator;

public class CalculationItemDtoComparator implements Comparator<CalculationItemDto> {

    @Override
    public int compare(CalculationItemDto item1, CalculationItemDto item2) {
        Long[] pathNodes1 = item1.getPathExpression().getPathNodes();
        Long[] pathNodes2 = item2.getPathExpression().getPathNodes();
        Long[] shortestPathNodes = pathNodes1.length < pathNodes2.length ? pathNodes1 : pathNodes2;
        for (int i = 0; i < shortestPathNodes.length; i++) {
            Long node1 = pathNodes1[i];
            Long node2 = pathNodes2[i];
            if (!node1.equals(node2)) {
                return node1.compareTo(node2);
            } else if (pathNodes1.length == i + 1) {
                return -1;
            } else if (pathNodes2.length == i + 1) {
                return 1;
            }
        }
        throw new RuntimeException();
    }
}
