package lv.odylab.evemanage.client.rpc.dto.calculation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CalculationDto implements Serializable {
    private Long id;
    private String name;
    private BigDecimal price;
    private Long blueprintTypeID;
    private String blueprintTypeName;
    private Long productTypeID;
    private String productTypeName;
    private Long productTypeCategoryID;
    private String productGraphicIcon;
    private Integer productivityLevel;
    private Integer materialLevel;
    private Integer wasteFactor;
    private Integer maxProductionLimit;
    private BigDecimal productVolume;
    private Integer productPortionSize;
    private List<CalculationItemDto> calculationItems;
    private List<BlueprintItemDto> blueprintItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getBlueprintTypeID() {
        return blueprintTypeID;
    }

    public void setBlueprintTypeID(Long blueprintTypeID) {
        this.blueprintTypeID = blueprintTypeID;
    }

    public String getBlueprintTypeName() {
        return blueprintTypeName;
    }

    public void setBlueprintTypeName(String blueprintTypeName) {
        this.blueprintTypeName = blueprintTypeName;
    }

    public Long getProductTypeID() {
        return productTypeID;
    }

    public void setProductTypeID(Long productTypeID) {
        this.productTypeID = productTypeID;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public Long getProductTypeCategoryID() {
        return productTypeCategoryID;
    }

    public void setProductTypeCategoryID(Long productTypeCategoryID) {
        this.productTypeCategoryID = productTypeCategoryID;
    }

    public String getProductGraphicIcon() {
        return productGraphicIcon;
    }

    public void setProductGraphicIcon(String productGraphicIcon) {
        this.productGraphicIcon = productGraphicIcon;
    }

    public Integer getProductivityLevel() {
        return productivityLevel;
    }

    public void setProductivityLevel(Integer productivityLevel) {
        this.productivityLevel = productivityLevel;
    }

    public Integer getMaterialLevel() {
        return materialLevel;
    }

    public void setMaterialLevel(Integer materialLevel) {
        this.materialLevel = materialLevel;
    }

    public Integer getWasteFactor() {
        return wasteFactor;
    }

    public void setWasteFactor(Integer wasteFactor) {
        this.wasteFactor = wasteFactor;
    }

    public Integer getMaxProductionLimit() {
        return maxProductionLimit;
    }

    public void setMaxProductionLimit(Integer maxProductionLimit) {
        this.maxProductionLimit = maxProductionLimit;
    }

    public BigDecimal getProductVolume() {
        return productVolume;
    }

    public void setProductVolume(BigDecimal productVolume) {
        this.productVolume = productVolume;
    }

    public Integer getProductPortionSize() {
        return productPortionSize;
    }

    public void setProductPortionSize(Integer productPortionSize) {
        this.productPortionSize = productPortionSize;
    }

    public List<CalculationItemDto> getCalculationItems() {
        return calculationItems;
    }

    public void setCalculationItems(List<CalculationItemDto> calculationItems) {
        this.calculationItems = calculationItems;
    }

    public List<BlueprintItemDto> getBlueprintItems() {
        return blueprintItems;
    }

    public void setBlueprintItems(List<BlueprintItemDto> blueprintItems) {
        this.blueprintItems = blueprintItems;
    }
}
