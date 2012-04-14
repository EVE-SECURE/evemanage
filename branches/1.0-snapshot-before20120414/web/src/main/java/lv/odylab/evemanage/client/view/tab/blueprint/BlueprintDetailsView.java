package lv.odylab.evemanage.client.view.tab.blueprint;

import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.CcpJsMessages;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.EveManageResources;
import lv.odylab.evemanage.client.EveManageUrlMessages;
import lv.odylab.evemanage.client.presenter.tab.blueprint.BlueprintDetailsPresenter;
import lv.odylab.evemanage.client.presenter.tab.blueprint.ComputableBlueprintDetails;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDetailsDto;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDto;
import lv.odylab.evemanage.client.rpc.dto.blueprint.MaterialDto;
import lv.odylab.evemanage.client.rpc.dto.blueprint.RequirementDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterInfoDto;
import lv.odylab.evemanage.client.util.EveImageUrlProvider;
import lv.odylab.evemanage.client.widget.AttachedCharacterLabel;
import lv.odylab.evemanage.client.widget.DamagePerJobLabel;
import lv.odylab.evemanage.client.widget.EveItemInfoLink;
import lv.odylab.evemanage.client.widget.EveItemMarketDetailsLink;
import lv.odylab.evemanage.client.widget.QuantityLabel;
import lv.odylab.evemanage.client.widget.SharingLevelLabel;
import lv.odylab.evemanage.client.widget.TimeLabel;
import lv.odylab.evemanage.client.widget.WasteLabel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlueprintDetailsView implements BlueprintDetailsPresenter.Display {
    private EveManageConstants constants;
    private EveManageResources resources;
    private EveManageMessages messages;
    private EveManageUrlMessages urlMessages;
    private CcpJsMessages ccpJsMessages;
    private EveImageUrlProvider imageUrlProvider;

    @Inject
    public BlueprintDetailsView(EveManageConstants constants, EveManageResources resources, EveManageMessages messages, EveManageUrlMessages urlMessages, CcpJsMessages ccpJsMessages, EveImageUrlProvider imageUrlProvider) {
        this.constants = constants;
        this.resources = resources;
        this.messages = messages;
        this.urlMessages = urlMessages;
        this.ccpJsMessages = ccpJsMessages;
        this.imageUrlProvider = imageUrlProvider;
    }

    @Override
    public ComputableBlueprintDetails attach(FlexTable detailsTable, BlueprintDetailsDto details, BlueprintDto blueprint) {
        return drawBlueprintDetailsTable(detailsTable, details, blueprint);
    }

    private ComputableBlueprintDetails drawBlueprintDetailsTable(FlexTable detailsTable, BlueprintDetailsDto details, BlueprintDto blueprint) {
        ComputableBlueprintDetails computableBlueprintDetails = new ComputableBlueprintDetails();
        computableBlueprintDetails.setBlueprintDetails(details);
        FlexTable materialsTable = new FlexTable();
        FlexTable requirementsTable = new FlexTable();
        List<MaterialDto> materials = details.getMaterials();
        List<RequirementDto> manufacturingRequirements = details.getManufacturingRequirements();
        List<RequirementDto> timeProductivityRequirements = details.getTimeProductivityRequirements();
        List<RequirementDto> materialProductivityRequirements = details.getMaterialProductivityRequirements();
        List<RequirementDto> copyingRequirements = details.getCopyingRequirements();
        List<RequirementDto> inventionRequirements = details.getInventionRequirements();
        Map<MaterialDto, QuantityLabel> materialToWidgetMap = new HashMap<MaterialDto, QuantityLabel>();
        Map<RequirementDto, QuantityLabel> requirementToWidgetMap = new HashMap<RequirementDto, QuantityLabel>();
        for (int i = 0; i < materials.size(); i++) {
            MaterialDto material = materials.get(i);
            String imageUrl = imageUrlProvider.getImage16Url(material.getMaterialTypeCategoryID(), material.getMaterialTypeID(), material.getMaterialTypeGraphicIcon());
            Image image = new Image(imageUrl);
            image.setTitle(material.getMaterialTypeName());
            image.addStyleName(resources.css().image16());
            EveItemInfoLink imageItemInfoLink = new EveItemInfoLink(ccpJsMessages, image, material.getMaterialTypeID());
            materialsTable.setWidget(i, 0, imageItemInfoLink);
            materialsTable.setWidget(i, 1, new EveItemMarketDetailsLink(constants, urlMessages, ccpJsMessages, material.getMaterialTypeName(), material.getMaterialTypeID()));
            materialsTable.setWidget(i, 2, new Label("x"));
            QuantityLabel quantityLabel = new QuantityLabel();
            materialsTable.setWidget(i, 3, quantityLabel);
            materialToWidgetMap.put(material, quantityLabel);
        }

        for (int i = 0; i < manufacturingRequirements.size(); i++) {
            RequirementDto requirement = manufacturingRequirements.get(i);
            String imageUrl = imageUrlProvider.getImage16Url(requirement.getRequiredTypeCategoryID(), requirement.getRequiredTypeID(), requirement.getRequiredTypeNameGraphicIcon());
            Image image = new Image(imageUrl);
            image.setTitle(requirement.getRequiredTypeName());
            image.addStyleName(resources.css().image16());
            EveItemInfoLink imageItemInfoLink = new EveItemInfoLink(ccpJsMessages, image, requirement.getRequiredTypeID());

            materialsTable.setWidget(i + materials.size(), 0, imageItemInfoLink);
            materialsTable.setWidget(i + materials.size(), 1, new EveItemMarketDetailsLink(constants, urlMessages, ccpJsMessages, requirement.getRequiredTypeName(), requirement.getRequiredTypeID()));
            materialsTable.setWidget(i + materials.size(), 2, new Label("x"));
            QuantityLabel quantityLabel = new QuantityLabel();
            HorizontalPanel quantityAndDamagePerJobPanel = new HorizontalPanel();
            quantityAndDamagePerJobPanel.add(quantityLabel);
            BigDecimal damagePerJob = requirement.getDamagePerJob();
            if (BigDecimal.ONE.compareTo(damagePerJob) == 1 && BigDecimal.ZERO.compareTo(damagePerJob) != 0) {
                DamagePerJobLabel damagePerJobLabel = new DamagePerJobLabel(damagePerJob);
                damagePerJobLabel.addStyleName(resources.css().damagePerJobLabel());
                quantityAndDamagePerJobPanel.add(damagePerJobLabel);
                quantityAndDamagePerJobPanel.setCellVerticalAlignment(damagePerJobLabel, HasVerticalAlignment.ALIGN_BOTTOM);
            }
            materialsTable.setWidget(i + materials.size(), 3, quantityAndDamagePerJobPanel);
            requirementToWidgetMap.put(requirement, quantityLabel);
        }

        Image stationImage = new Image(resources.station());
        stationImage.setTitle(messages.timeStation());
        stationImage.addStyleName(resources.css().image16());
        int index = materialsTable.getRowCount();
        materialsTable.setWidget(index, 0, stationImage);
        TimeLabel productionTimeStationLabel = new TimeLabel();
        materialsTable.setWidget(index, 1, productionTimeStationLabel);
        Image labImage = new Image(resources.lab());
        labImage.setTitle(messages.timePos());
        labImage.addStyleName(resources.css().image16());
        materialsTable.setWidget(index + 1, 0, labImage);
        TimeLabel productionTimePosLabel = new TimeLabel();
        materialsTable.setWidget(index + 1, 1, productionTimePosLabel);
        computableBlueprintDetails.setProductionTimeStationText(productionTimeStationLabel);
        computableBlueprintDetails.setProductionTimePosText(productionTimePosLabel);

        List<DisclosurePanel> disclosurePanels = new ArrayList<DisclosurePanel>();
        TimeLabel researchProductivityTimeStationLabel = new TimeLabel();
        TimeLabel researchProductivityTimePosLabel = new TimeLabel();
        disclosurePanels.add(createDisclosurePanel(messages.researchingTimeProductivity(), timeProductivityRequirements, researchProductivityTimeStationLabel, researchProductivityTimePosLabel));
        computableBlueprintDetails.setResearchProductivityTimeStationText(researchProductivityTimeStationLabel);
        computableBlueprintDetails.setResearchProductivityTimePosText(researchProductivityTimePosLabel);

        TimeLabel researchMaterialTimeStationLabel = new TimeLabel();
        TimeLabel researchMaterialTimePosLabel = new TimeLabel();
        disclosurePanels.add(createDisclosurePanel(messages.researchingMaterialProductivity(), materialProductivityRequirements, researchMaterialTimeStationLabel, researchMaterialTimePosLabel));
        computableBlueprintDetails.setResearchMaterialTimeStationText(researchMaterialTimeStationLabel);
        computableBlueprintDetails.setResearchMaterialTimePosText(researchMaterialTimePosLabel);

        TimeLabel researchCopyTimeStationLabel = new TimeLabel();
        TimeLabel researchCopyTimePosLabel = new TimeLabel();
        disclosurePanels.add(createDisclosurePanel(messages.copying(), copyingRequirements, researchCopyTimeStationLabel, researchCopyTimePosLabel));
        computableBlueprintDetails.setResearchCopyTimeStationText(researchCopyTimeStationLabel);
        computableBlueprintDetails.setResearchCopyTimePosText(researchCopyTimePosLabel);

        TimeLabel researchTechTimeStationLabel = new TimeLabel();
        TimeLabel researchTechTimePosLabel = new TimeLabel();
        computableBlueprintDetails.setResearchTechTimeStationText(researchTechTimeStationLabel);
        computableBlueprintDetails.setResearchTechTimePosText(researchTechTimePosLabel);
        if (inventionRequirements.size() > 0) {
            disclosurePanels.add(createDisclosurePanel(messages.invention(), inventionRequirements, researchTechTimeStationLabel, researchTechTimePosLabel));
        }
        AttachedCharacterLabel attachedCharacterNameLabel = new AttachedCharacterLabel(messages);
        SharingLevelLabel sharingLevelLabel = new SharingLevelLabel(messages);
        sharingLevelLabel.setSharingLevel(blueprint.getSharingLevel());
        disclosurePanels.add(createSharingDisclosurePanel(attachedCharacterNameLabel, sharingLevelLabel, blueprint.getAttachedCharacterInfo()));
        for (int i = 0; i < disclosurePanels.size(); i++) {
            DisclosurePanel disclosurePanel = disclosurePanels.get(i);
            requirementsTable.setWidget(i, 0, disclosurePanel);
        }
        computableBlueprintDetails.setAttachedCharacterNameLabel(attachedCharacterNameLabel);
        computableBlueprintDetails.setSharingLevelLabel(sharingLevelLabel);

        String imageUrl = imageUrlProvider.getImage64Url(blueprint.getProductCategoryID(), blueprint.getProductTypeID(), blueprint.getProductGraphicIcon());
        Image image = new Image(imageUrl);
        image.setTitle(blueprint.getProductTypeName());
        image.addStyleName(resources.css().image64());
        EveItemInfoLink imageItemInfoLink = new EveItemInfoLink(ccpJsMessages, image, blueprint.getProductTypeID());

        FlexTable imageTable = new FlexTable();
        imageTable.setWidget(0, 0, imageItemInfoLink);
        imageTable.setWidget(0, 1, new EveItemMarketDetailsLink(constants, urlMessages, ccpJsMessages, blueprint.getProductTypeName(), blueprint.getProductTypeID()));
        WasteLabel wasteLabel = new WasteLabel(messages);
        imageTable.setWidget(1, 0, wasteLabel);
        imageTable.setWidget(2, 0, new Label()); // TODO put something
        FlexTable.FlexCellFormatter imageTableCellFormatter = imageTable.getFlexCellFormatter();
        imageTableCellFormatter.setRowSpan(0, 0, 3);
        imageTableCellFormatter.setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
        imageTableCellFormatter.setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
        imageTableCellFormatter.setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
        imageTableCellFormatter.setVerticalAlignment(2, 0, HasVerticalAlignment.ALIGN_TOP);
        computableBlueprintDetails.setWasteText(wasteLabel);

        requirementsTable.insertRow(0);
        requirementsTable.setWidget(0, 0, imageTable);

        detailsTable.setWidget(0, 0, requirementsTable);
        detailsTable.setWidget(0, 1, materialsTable);
        HTMLTable.CellFormatter detailTableCellFromatter = detailsTable.getCellFormatter();
        detailTableCellFromatter.setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
        detailTableCellFromatter.setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);

        computableBlueprintDetails.setMaterialToWidgetMap(materialToWidgetMap);
        computableBlueprintDetails.setRequirementToWidgetMap(requirementToWidgetMap);
        return computableBlueprintDetails;
    }

    private DisclosurePanel createDisclosurePanel(String headerText, List<RequirementDto> requirements, TimeLabel stationTimeLabel, TimeLabel posTimeLabel) {
        DisclosurePanel disclosurePanel = new DisclosurePanel(headerText);
        FlexTable disclosurePanelTable = new FlexTable();
        for (int i = 0; i < requirements.size(); i++) {
            RequirementDto requirement = requirements.get(i);
            String imageUrl = imageUrlProvider.getImage16Url(requirement.getRequiredTypeCategoryID(), requirement.getRequiredTypeID(), requirement.getRequiredTypeNameGraphicIcon());
            Image image = new Image(imageUrl);
            image.setTitle(requirement.getRequiredTypeName());
            image.addStyleName(resources.css().image16());
            EveItemInfoLink imageItemInfoLink = new EveItemInfoLink(ccpJsMessages, image, requirement.getRequiredTypeID());
            disclosurePanelTable.setWidget(i, 0, imageItemInfoLink);
            disclosurePanelTable.setWidget(i, 1, new EveItemMarketDetailsLink(constants, urlMessages, ccpJsMessages, requirement.getRequiredTypeName(), requirement.getRequiredTypeID()));
            disclosurePanelTable.setWidget(i, 2, new Label("x"));
            QuantityLabel quantityLabel = new QuantityLabel(requirement.getQuantity());
            HorizontalPanel quantityAndDamagePerJobPanel = new HorizontalPanel();
            quantityAndDamagePerJobPanel.add(quantityLabel);
            BigDecimal damagePerJob = requirement.getDamagePerJob();
            if (BigDecimal.ONE.compareTo(damagePerJob) == 1 && BigDecimal.ZERO.compareTo(damagePerJob) != 0) {
                DamagePerJobLabel damagePerJobLabel = new DamagePerJobLabel(damagePerJob);
                damagePerJobLabel.addStyleName(resources.css().damagePerJobLabel());
                quantityAndDamagePerJobPanel.add(damagePerJobLabel);
                quantityAndDamagePerJobPanel.setCellVerticalAlignment(damagePerJobLabel, HasVerticalAlignment.ALIGN_BOTTOM);
            }
            disclosurePanelTable.setWidget(i, 3, quantityAndDamagePerJobPanel);
        }
        Image stationImage = new Image(resources.station());
        stationImage.setTitle(messages.timeStation());
        stationImage.addStyleName(resources.css().image16());
        int index = disclosurePanelTable.getRowCount();
        disclosurePanelTable.setWidget(index, 0, stationImage);
        disclosurePanelTable.setWidget(index, 1, stationTimeLabel);
        Image labImage = new Image(resources.lab());
        labImage.setTitle(messages.timePos());
        labImage.addStyleName(resources.css().image16());
        disclosurePanelTable.setWidget(index + 1, 0, labImage);
        disclosurePanelTable.setWidget(index + 1, 1, posTimeLabel);
        disclosurePanel.setContent(disclosurePanelTable);
        return disclosurePanel;
    }

    private DisclosurePanel createSharingDisclosurePanel(Label attachedCharacterNameLabel, Label sharingLevelLabel, CharacterInfoDto attachedCharacterInfo) {
        DisclosurePanel disclosurePanel = new DisclosurePanel(messages.sharing());
        FlexTable disclosurePanelTable = new FlexTable();
        Image characterImage = new Image(resources.character());
        characterImage.setTitle(messages.attachedCharacter());
        characterImage.addStyleName(resources.css().image16());
        disclosurePanelTable.setWidget(0, 0, characterImage);
        if (attachedCharacterInfo != null) {
            attachedCharacterNameLabel.setText(attachedCharacterInfo.getName());
        } else {
            attachedCharacterNameLabel.setText(messages.none());
        }
        disclosurePanelTable.setWidget(0, 1, attachedCharacterNameLabel);
        Image sharingImage = new Image(resources.sharing());
        sharingImage.setTitle(messages.sharingLevel());
        sharingImage.addStyleName(resources.css().image16());
        disclosurePanelTable.setWidget(1, 0, sharingImage);
        disclosurePanelTable.setWidget(1, 1, sharingLevelLabel);
        disclosurePanel.setContent(disclosurePanelTable);
        return disclosurePanel;
    }
}
