package lv.odylab.evemanage.client.widget;

import com.google.gwt.i18n.client.NumberFormat;

public class EveNumberFormat {
    public static final NumberFormat PRICE_FORMAT = NumberFormat.getFormat("###,###,###,###,###,###,##0.00");
    public static final NumberFormat QUANTITY_FORMAT = NumberFormat.getFormat("###,###,###,###,###,###,##0");
    public static final NumberFormat DECIMAL_FORMAT = NumberFormat.getFormat("####################0.##");
    public static final NumberFormat WASTE_FORMAT = NumberFormat.getFormat("##0.######");
    public static final NumberFormat DAMAGE_FORMAT = NumberFormat.getFormat("0.00##");
}
