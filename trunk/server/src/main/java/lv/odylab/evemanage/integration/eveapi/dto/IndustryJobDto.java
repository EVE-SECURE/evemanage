package lv.odylab.evemanage.integration.eveapi.dto;

import java.io.Serializable;

public class IndustryJobDto implements Serializable {
    private Long installedItemID;
    private Integer installedItemProductivityLevel;
    private Integer installedItemMaterialLevel;
    private Integer installedItemLicensedProductionRunsRemaining;
    private Long installedItemTypeID;
    private Long outputTypeID;
    private Long activityID;

    public Long getInstalledItemID() {
        return installedItemID;
    }

    public void setInstalledItemID(Long installedItemID) {
        this.installedItemID = installedItemID;
    }

    public Integer getInstalledItemProductivityLevel() {
        return installedItemProductivityLevel;
    }

    public void setInstalledItemProductivityLevel(Integer installedItemProductivityLevel) {
        this.installedItemProductivityLevel = installedItemProductivityLevel;
    }

    public Integer getInstalledItemMaterialLevel() {
        return installedItemMaterialLevel;
    }

    public void setInstalledItemMaterialLevel(Integer installedItemMaterialLevel) {
        this.installedItemMaterialLevel = installedItemMaterialLevel;
    }

    public Integer getInstalledItemLicensedProductionRunsRemaining() {
        return installedItemLicensedProductionRunsRemaining;
    }

    public void setInstalledItemLicensedProductionRunsRemaining(Integer installedItemLicensedProductionRunsRemaining) {
        this.installedItemLicensedProductionRunsRemaining = installedItemLicensedProductionRunsRemaining;
    }

    public Long getInstalledItemTypeID() {
        return installedItemTypeID;
    }

    public void setInstalledItemTypeID(Long installedItemTypeID) {
        this.installedItemTypeID = installedItemTypeID;
    }

    public Long getOutputTypeID() {
        return outputTypeID;
    }

    public void setOutputTypeID(Long outputTypeID) {
        this.outputTypeID = outputTypeID;
    }

    public Long getActivityID() {
        return activityID;
    }

    public void setActivityID(Long activityID) {
        this.activityID = activityID;
    }
}
