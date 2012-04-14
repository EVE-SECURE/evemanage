package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import java.util.Comparator;

@Deprecated
// TODO remove this class
public class LongArrayComparator implements Comparator<Long[]> {

    @Override
    public int compare(Long[] arr1, Long[] arr2) {
        if (arr1.length > arr2.length) {
            return 1;
        } else if (arr2.length > arr1.length) {
            return -1;
        } else {
            for (int i = 0; i < arr1.length; i++) {
                if (arr1[i] > arr2[i]) {
                    return 1;
                } else if (arr2[i] > arr1[i]) {
                    return -1;
                }
            }
            return 0;
        }
    }
}

