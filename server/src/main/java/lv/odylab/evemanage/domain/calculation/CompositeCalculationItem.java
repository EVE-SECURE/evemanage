package lv.odylab.evemanage.domain.calculation;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Unindexed;

import java.io.Serializable;

@Unindexed
public class CompositeCalculationItem implements Serializable {
    private Key<Calculation> calculation;
    private Long itemID;
    private Long itemTypeID;
    private String itemTypeName;
    private Long productTypeID;
    private String productTypeName;
    private Long productTypeCategoryID;
    private String productGraphicIcon;
    private Integer productivityLevel;
    private Integer materialLevel;
    private Long quantity;
    private String price;
    private String priceOverride;
    private String totalPrice;
    private String totalPriceOverride;
}
