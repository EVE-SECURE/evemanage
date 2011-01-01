package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

import java.util.Arrays;
import java.util.Comparator;

public class SortableFlexTable extends FlexTable {
    private Comparator<Widget[]> currentComparator;

    public SortableFlexTable(Comparator<Widget[]> currentComparator) {
        this.currentComparator = currentComparator;
    }

    public Comparator<Widget[]> getCurrentComparator() {
        return currentComparator;
    }

    public void setCurrentComparator(Comparator<Widget[]> currentComparator) {
        this.currentComparator = currentComparator;
    }

    public void sort() {
        int rowCount = getRowCount();
        if (rowCount == 0) {
            return;
        }
        int columnCount = getCellCount(0);
        Widget[][] oldData = new Widget[rowCount][columnCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                oldData[i][j] = getWidget(i, j);
            }
        }
        Arrays.sort(oldData, currentComparator);
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                setWidget(i, j, oldData[i][j]);
            }
        }
    }
}