package lv.odylab.evemanage.client.view.tab;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.EveManageResources;
import lv.odylab.evemanage.client.presenter.tab.QuickCalculatorTabPresenter;
import lv.odylab.evemanage.client.presenter.tab.calculator.BlueprintItemTree;
import lv.odylab.evemanage.client.presenter.tab.calculator.CalculationItemTree;
import lv.odylab.evemanage.client.presenter.tab.calculator.CalculationItemTreeNode;
import lv.odylab.evemanage.client.presenter.tab.calculator.CalculationProcessor;
import lv.odylab.evemanage.client.presenter.tab.calculator.CalculationProcessorResult;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.ComputableBlueprintInformation;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.ComputableCalculationItem;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.ComputableCalculationPriceSetItem;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.EditableBlueprintInformation;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.EditableCalculationItem;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.EditableCalculationPriceSetItem;
import lv.odylab.evemanage.client.rpc.EveCalculator;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationPriceItemDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.UsedBlueprintDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.UsedSchematicDto;
import lv.odylab.evemanage.client.view.tab.quickcalculator.BlueprintInformationSection;
import lv.odylab.evemanage.client.view.tab.quickcalculator.BlueprintItemTreeSection;
import lv.odylab.evemanage.client.view.tab.quickcalculator.CalculationItemTreeSection;
import lv.odylab.evemanage.client.view.tab.quickcalculator.DirectLinkSection;
import lv.odylab.evemanage.client.view.tab.quickcalculator.PricesSection;
import lv.odylab.evemanage.client.view.tab.quickcalculator.SkillsForCalculationSection;
import lv.odylab.evemanage.client.widget.OpaqueLoadableBlueprintImage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuickCalculatorTabView implements QuickCalculatorTabPresenter.Display {
    private CalculationProcessor calculationProcessor;
    private EveCalculator calculator;

    private HorizontalPanel headerPanel;
    private Label headerLabel;
    private Image spinnerImage;

    private FlexTable errorMessageTable;
    private Label errorMessageLabel;
    private Image errorImage;

    private BlueprintInformationSection blueprintInformationSection;
    private CalculationItemTreeSection calculationItemTreeSection;
    private BlueprintItemTreeSection blueprintItemTreeSection;
    private PricesSection pricesSection;
    private SkillsForCalculationSection skillsForCalculationSection;
    private DirectLinkSection directLinkSection;

    @Inject
    public QuickCalculatorTabView(CalculationProcessor calculationProcessor, EveCalculator calculator, EveManageResources resources, EveManageMessages messages, BlueprintInformationSection blueprintInformationSection, CalculationItemTreeSection calculationItemTreeSection, BlueprintItemTreeSection blueprintItemTreeSection, PricesSection pricesSection, SkillsForCalculationSection skillsForCalculationSection, DirectLinkSection directLinkSection) {
        this.calculationProcessor = calculationProcessor;
        this.calculator = calculator;
        this.blueprintInformationSection = blueprintInformationSection;
        this.calculationItemTreeSection = calculationItemTreeSection;
        this.blueprintItemTreeSection = blueprintItemTreeSection;
        this.pricesSection = pricesSection;
        this.skillsForCalculationSection = skillsForCalculationSection;
        this.directLinkSection = directLinkSection;

        headerPanel = new HorizontalPanel();
        headerPanel.addStyleName(resources.css().tabHeaderPanel());
        headerLabel = new Label(messages.quickCalculator());
        headerLabel.addStyleName(resources.css().tabHeaderText());
        spinnerImage = new Image(resources.spinnerIcon());
        spinnerImage.setTitle(messages.loading());
        spinnerImage.addStyleName(resources.css().tabHeaderSpinner());

        errorMessageTable = new FlexTable();
        errorMessageLabel = new Label();
        errorMessageLabel.addStyleName(resources.css().errorLabel());
        errorImage = new Image(resources.errorIcon());
        errorImage.setTitle(messages.error());
    }

    @Override
    public void attach(HasWidgets container) {
        headerPanel.add(headerLabel);
        headerPanel.add(spinnerImage);
        headerPanel.setCellVerticalAlignment(spinnerImage, HasVerticalAlignment.ALIGN_MIDDLE);
        headerPanel.setCellVerticalAlignment(headerLabel, HasVerticalAlignment.ALIGN_MIDDLE);
        container.add(headerPanel);

        errorMessageTable.setVisible(false);
        errorMessageTable.setWidget(0, 0, errorImage);
        errorMessageTable.setWidget(0, 1, errorMessageLabel);
        container.add(errorMessageTable);

        blueprintInformationSection.attach(container);
        calculationItemTreeSection.attach(container);
        blueprintItemTreeSection.attach(container);
        pricesSection.attach(container);
        skillsForCalculationSection.attach(container);
        directLinkSection.attach(container);
    }

    @Override
    public Widget getSpinnerImage() {
        return spinnerImage;
    }

    @Override
    public Widget getErrorContainer() {
        return errorMessageTable;
    }

    @Override
    public HasText getErrorMessageLabel() {
        return errorMessageLabel;
    }

    @Override
    public void setNewCalculation(CalculationDto calculation) {
        blueprintInformationSection.cleanBlueprintInformation();
        calculationItemTreeSection.cleanCalculationItemTree();
        blueprintItemTreeSection.cleanBlueprintItemTree();
        pricesSection.cleanPrices();

        CalculationItemTree calculationItemTree = calculationItemTreeSection.getCalculationItemTree();
        BlueprintItemTree blueprintItemTree = blueprintItemTreeSection.getBlueprintItemTree();
        calculationItemTree.build(calculation);
        blueprintItemTree.build(calculation);

        CalculationProcessorResult calculationProcessorResult = calculationProcessor.process(calculationItemTree, blueprintItemTree);
        blueprintInformationSection.drawBlueprintInformation(calculation);
        calculationItemTreeSection.drawCalculationItemTree(calculationItemTree);
        blueprintItemTreeSection.drawBlueprintItemTree(blueprintItemTree);
        pricesSection.drawCalculationPriceSetItems(calculationProcessorResult.getTypeIdToCalculationPriceSetItemMap().values());
    }

    @Override
    public List<String> useBlueprint(Long[] pathNodes, UsedBlueprintDto usedBlueprint) {
        Map<Long[], UsedBlueprintDto> pathNodesToUsedBlueprintMap = new HashMap<Long[], UsedBlueprintDto>();
        pathNodesToUsedBlueprintMap.put(pathNodes, usedBlueprint);
        List<String> pathNodesStringsWithBlueprintOrSchematic = calculationItemTreeSection.addCalculationItemTreeNodes(pathNodesToUsedBlueprintMap);

        CalculationItemTree calculationItemTree = calculationItemTreeSection.getCalculationItemTree();
        BlueprintItemTree blueprintItemTree = blueprintItemTreeSection.getBlueprintItemTree();
        CalculationProcessorResult calculationProcessorResult = calculationProcessor.process(calculationItemTree, blueprintItemTree);
        Map<Long, CalculationPriceItemDto> typeIdToCalculationPriceSetItemMap = calculationProcessorResult.getTypeIdToCalculationPriceSetItemMap();
        pricesSection.cleanPrices();
        pricesSection.drawCalculationPriceSetItems(new ArrayList<CalculationPriceItemDto>(typeIdToCalculationPriceSetItemMap.values()));
        recalculate();

        return pathNodesStringsWithBlueprintOrSchematic;
    }

    @Override
    public void stopUsingBlueprint(String pathNodesString) {
        EditableCalculationItem editableCalculationItem = calculationItemTreeSection.getPathNodesStringToEditableCalculationItemMap().get(pathNodesString);
        calculationItemTreeSection.hideCalculationItemDetails(editableCalculationItem);
        calculationItemTreeSection.hideDetailsTable(editableCalculationItem);

        CalculationItemTree calculationItemTree = calculationItemTreeSection.getCalculationItemTree();
        BlueprintItemTree blueprintItemTree = blueprintItemTreeSection.getBlueprintItemTree();
        CalculationProcessorResult calculationProcessorResult = calculationProcessor.process(calculationItemTree, blueprintItemTree);
        Map<Long, CalculationPriceItemDto> typeIdToCalculationPriceSetItemMap = calculationProcessorResult.getTypeIdToCalculationPriceSetItemMap();
        pricesSection.cleanPrices();
        pricesSection.drawCalculationPriceSetItems(new ArrayList<CalculationPriceItemDto>(typeIdToCalculationPriceSetItemMap.values()));
        recalculate();
    }

    @Override
    public void reuseBlueprint(String pathNodesString) {
        EditableCalculationItem editableCalculationItem = calculationItemTreeSection.getPathNodesStringToEditableCalculationItemMap().get(pathNodesString);
        calculationItemTreeSection.showCalculationItemDetails(editableCalculationItem);

        CalculationItemTree calculationItemTree = calculationItemTreeSection.getCalculationItemTree();
        BlueprintItemTree blueprintItemTree = blueprintItemTreeSection.getBlueprintItemTree();
        CalculationProcessorResult calculationProcessorResult = calculationProcessor.process(calculationItemTree, blueprintItemTree);
        Map<Long, CalculationPriceItemDto> typeIdToCalculationPriceSetItemMap = calculationProcessorResult.getTypeIdToCalculationPriceSetItemMap();
        pricesSection.cleanPrices();
        pricesSection.drawCalculationPriceSetItems(new ArrayList<CalculationPriceItemDto>(typeIdToCalculationPriceSetItemMap.values()));
        recalculate();
    }

    @Override
    public List<String> useAllBlueprints(Map<Long[], UsedBlueprintDto> pathNodesToUsedBlueprintMap) {
        for (Map.Entry<String, EditableCalculationItem> mapEntry : calculationItemTreeSection.getPathNodesStringToEditableCalculationItemMap().entrySet()) {
            OpaqueLoadableBlueprintImage blueprintImage = mapEntry.getValue().getBlueprintImage();
            if (blueprintImage != null && !calculationItemTreeSection.getPathNodeStringsWithUsedBlueprint().contains(mapEntry.getKey())) {
                blueprintImage.stopLoading();
                blueprintImage.removeOpacity();
            }
        }
        List<String> pathNodesStringsWithBlueprintOrSchematic = calculationItemTreeSection.addCalculationItemTreeNodes(pathNodesToUsedBlueprintMap);

        CalculationItemTree calculationItemTree = calculationItemTreeSection.getCalculationItemTree();
        BlueprintItemTree blueprintItemTree = blueprintItemTreeSection.getBlueprintItemTree();
        CalculationProcessorResult calculationProcessorResult = calculationProcessor.process(calculationItemTree, blueprintItemTree);
        Map<Long, CalculationPriceItemDto> typeIdToCalculationPriceSetItemMap = calculationProcessorResult.getTypeIdToCalculationPriceSetItemMap();
        pricesSection.cleanPrices();
        pricesSection.drawCalculationPriceSetItems(new ArrayList<CalculationPriceItemDto>(typeIdToCalculationPriceSetItemMap.values()));
        recalculate();

        return pathNodesStringsWithBlueprintOrSchematic;
    }

    @Override
    public void stopUsingAllBlueprints() {
        List<Long[]> pathNodesList = new ArrayList<Long[]>();
        for (Map.Entry<String, EditableCalculationItem> mapEntry : calculationItemTreeSection.getPathNodesStringToEditableCalculationItemMap().entrySet()) {
            EditableCalculationItem editableCalculationItem = mapEntry.getValue();
            ComputableCalculationItem computableCalculationItem = calculationItemTreeSection.getPathNodesStringToComputableCalculationItemMap().get(mapEntry.getKey());
            OpaqueLoadableBlueprintImage blueprintImage = editableCalculationItem.getBlueprintImage();
            if (blueprintImage != null && !blueprintImage.hasOpacity()) {
                blueprintImage.setOpacity();
                calculationItemTreeSection.hideCalculationItemDetails(editableCalculationItem);
                calculationItemTreeSection.hideDetailsTable(editableCalculationItem);
                pathNodesList.add(computableCalculationItem.getCalculationItemTreeNodeSummary().getPathNodes());
            }
        }
        calculationItemTreeSection.excludeCalculationItemTreeNodesFromCalculation(pathNodesList);

        CalculationProcessorResult calculationProcessorResult = recalculate(pricesSection.createTypeIdToPriceMap());
        pricesSection.cleanPrices();
        pricesSection.drawCalculationPriceSetItems(new ArrayList<CalculationPriceItemDto>(calculationProcessorResult.getTypeIdToCalculationPriceSetItemMap().values()));
    }

    @Override
    public void reuseAllBlueprints() {
        List<Long[]> pathNodesList = new ArrayList<Long[]>();
        for (Map.Entry<String, EditableCalculationItem> mapEntry : calculationItemTreeSection.getPathNodesStringToEditableCalculationItemMap().entrySet()) {
            EditableCalculationItem editableCalculationItem = mapEntry.getValue();
            ComputableCalculationItem computableCalculationItem = calculationItemTreeSection.getPathNodesStringToComputableCalculationItemMap().get(mapEntry.getKey());
            OpaqueLoadableBlueprintImage blueprintImage = editableCalculationItem.getBlueprintImage();
            if (blueprintImage != null && blueprintImage.hasOpacity()) {
                blueprintImage.removeOpacity();
                calculationItemTreeSection.showCalculationItemDetails(editableCalculationItem);
                pathNodesList.add(computableCalculationItem.getCalculationItemTreeNodeSummary().getPathNodes());
            }
        }
        calculationItemTreeSection.includeCalculationItemTreeNodesInCalculation(pathNodesList);
        CalculationProcessorResult calculationProcessorResult = recalculate(pricesSection.createTypeIdToPriceMap());
        pricesSection.cleanPrices();
        pricesSection.drawCalculationPriceSetItems(new ArrayList<CalculationPriceItemDto>(calculationProcessorResult.getTypeIdToCalculationPriceSetItemMap().values()));
    }

    @Override
    public List<String> useSchematic(Long[] pathNodes, UsedSchematicDto schematic) {
        Map<Long[], UsedSchematicDto> pathNodesToSchematicMap = new HashMap<Long[], UsedSchematicDto>();
        pathNodesToSchematicMap.put(pathNodes, schematic);
        List<String> pathNodesStringsWithSchematic = calculationItemTreeSection.addCalculationItemTreeNodesForSchematic(pathNodesToSchematicMap);

        CalculationItemTree calculationItemTree = calculationItemTreeSection.getCalculationItemTree();
        BlueprintItemTree blueprintItemTree = blueprintItemTreeSection.getBlueprintItemTree();
        CalculationProcessorResult calculationProcessorResult = calculationProcessor.process(calculationItemTree, blueprintItemTree);
        Map<Long, CalculationPriceItemDto> typeIdToCalculationPriceSetItemMap = calculationProcessorResult.getTypeIdToCalculationPriceSetItemMap();
        pricesSection.cleanPrices();
        pricesSection.drawCalculationPriceSetItems(new ArrayList<CalculationPriceItemDto>(typeIdToCalculationPriceSetItemMap.values()));
        recalculate();

        return pathNodesStringsWithSchematic;
    }

    @Override
    public void stopUsingSchematics(String pathNodesString) {
        EditableCalculationItem editableCalculationItem = calculationItemTreeSection.getPathNodesStringToEditableCalculationItemMap().get(pathNodesString);
        calculationItemTreeSection.hideCalculationItemDetails(editableCalculationItem);
        calculationItemTreeSection.hideDetailsTable(editableCalculationItem);

        CalculationItemTree calculationItemTree = calculationItemTreeSection.getCalculationItemTree();
        BlueprintItemTree blueprintItemTree = blueprintItemTreeSection.getBlueprintItemTree();
        CalculationProcessorResult calculationProcessorResult = calculationProcessor.process(calculationItemTree, blueprintItemTree);
        Map<Long, CalculationPriceItemDto> typeIdToCalculationPriceSetItemMap = calculationProcessorResult.getTypeIdToCalculationPriceSetItemMap();
        pricesSection.cleanPrices();
        pricesSection.drawCalculationPriceSetItems(new ArrayList<CalculationPriceItemDto>(typeIdToCalculationPriceSetItemMap.values()));
        recalculate();
    }

    @Override
    public void reuseSchematics(String pathNodesString) {
        EditableCalculationItem editableCalculationItem = calculationItemTreeSection.getPathNodesStringToEditableCalculationItemMap().get(pathNodesString);
        calculationItemTreeSection.showCalculationItemDetails(editableCalculationItem);

        CalculationItemTree calculationItemTree = calculationItemTreeSection.getCalculationItemTree();
        BlueprintItemTree blueprintItemTree = blueprintItemTreeSection.getBlueprintItemTree();
        CalculationProcessorResult calculationProcessorResult = calculationProcessor.process(calculationItemTree, blueprintItemTree);
        Map<Long, CalculationPriceItemDto> typeIdToCalculationPriceSetItemMap = calculationProcessorResult.getTypeIdToCalculationPriceSetItemMap();
        pricesSection.cleanPrices();
        pricesSection.drawCalculationPriceSetItems(new ArrayList<CalculationPriceItemDto>(typeIdToCalculationPriceSetItemMap.values()));
        recalculate();
    }

    @Override
    public void updatePrices() {
        Map<Long, BigDecimal> typeIdToPriceMap = new HashMap<Long, BigDecimal>();
        for (Map.Entry<Long, EditableCalculationPriceSetItem> mapEntry : pricesSection.getTypeIdToEditableCalculationPriceSetItemMap().entrySet()) {
            Long typeID = mapEntry.getKey();
            BigDecimal price = mapEntry.getValue().getPriceTextBox().getPrice();
            ComputableCalculationPriceSetItem computableCalculationPriceSetItem = pricesSection.getTypeIdToComputableCalculationPriceSetItemMap().get(typeID);
            computableCalculationPriceSetItem.getCalculationPriceItem().setPrice(price);
            typeIdToPriceMap.put(typeID, price);
        }
        calculationItemTreeSection.getCalculationItemTree().setPrices(typeIdToPriceMap);
        recalculate();
    }

    @Override
    public void updatePrices(Map<Long, BigDecimal> typeIdToPriceMap) {
        for (Map.Entry<Long, BigDecimal> mapEntry : typeIdToPriceMap.entrySet()) {
            Long typeID = mapEntry.getKey();
            BigDecimal price = mapEntry.getValue();
            pricesSection.getTypeIdToEditableCalculationPriceSetItemMap().get(typeID).getPriceTextBox().setPrice(price);
            pricesSection.getTypeIdToComputableCalculationPriceSetItemMap().get(typeID).getCalculationPriceItem().setPrice(price);
        }
        calculationItemTreeSection.getCalculationItemTree().setPrices(typeIdToPriceMap);
        recalculate();
    }

    @Override
    public void changeBlueprintMePeQuantity(Integer meLevel, Integer peLevel, Long quantity) {
        EditableBlueprintInformation editableBlueprintInformation = blueprintInformationSection.getEditableBlueprintInformation();
        ComputableBlueprintInformation computableBlueprintInformation = blueprintInformationSection.getComputableBlueprintInformation();
        editableBlueprintInformation.getMeLabel().setText(String.valueOf(meLevel));
        editableBlueprintInformation.getPeLabel().setText(String.valueOf(peLevel));
        editableBlueprintInformation.getQuantityLabel().setQuantity(quantity);
        editableBlueprintInformation.getMeTextBox().setText(String.valueOf(meLevel));
        editableBlueprintInformation.getPeTextBox().setText(String.valueOf(peLevel));
        editableBlueprintInformation.getQuantityTextBox().setText(String.valueOf(quantity));
        computableBlueprintInformation.getCalculation().setMaterialLevel(meLevel);
        computableBlueprintInformation.getCalculation().setProductivityLevel(peLevel);
        calculationItemTreeSection.getCalculationItemTree().changeRootNodesMePeQuantity(meLevel, peLevel, quantity);
        recalculate();
    }

    @Override
    public void changeCalculationItemMePe(Long[] pathNodes, Integer meLevel, Integer peLevel) {
        CalculationItemTreeNode calculationItemTreeNode = calculationItemTreeSection.getCalculationItemTree().getNodeByPathNodes(pathNodes);
        for (CalculationItemTreeNode node : calculationItemTreeNode.getNodeMap().values()) {
            node.changeMePe(meLevel, peLevel);
        }
        recalculate();
    }

    @Override
    public QuickCalculatorTabPresenter.BlueprintInformationSectionDisplay getBlueprintInformationSectionDisplay() {
        return blueprintInformationSection;
    }

    @Override
    public QuickCalculatorTabPresenter.CalculationItemTreeSectionDisplay getCalculationTreeSectionDisplay() {
        return calculationItemTreeSection;
    }

    @Override
    public QuickCalculatorTabPresenter.BlueprintItemTreeSectionDisplay getBlueprintUsageSectionDisplay() {
        return blueprintItemTreeSection;
    }

    @Override
    public QuickCalculatorTabPresenter.PricesSectionDisplay getPricesSectionDisplay() {
        return pricesSection;
    }

    @Override
    public QuickCalculatorTabPresenter.SkillsForCalculationSectionDisplay getSkillsForCalculationSectionDisplay() {
        return skillsForCalculationSection;
    }

    @Override
    public QuickCalculatorTabPresenter.DirectLinkSectionDisplay getDirectLinkSectionDisplay() {
        return directLinkSection;
    }

    private void redrawPrices() {

    }

    private CalculationProcessorResult recalculate() {
        return recalculate(null);
    }

    private CalculationProcessorResult recalculate(Map<Long, BigDecimal> typeIdToPriceMap) {
        CalculationItemTree calculationItemTree = calculationItemTreeSection.getCalculationItemTree();
        BlueprintItemTree blueprintItemTree = blueprintItemTreeSection.getBlueprintItemTree();
        Long quantity = Long.valueOf(blueprintInformationSection.getEditableBlueprintInformation().getQuantityTextBox().getText());
        CalculationProcessorResult pricingProcessorResult = calculationProcessor.process(calculationItemTree, blueprintItemTree);
        for (Map.Entry<String, ComputableCalculationItem> mapEntry : calculationItemTreeSection.getPathNodesStringToComputableCalculationItemMap().entrySet()) {
            ComputableCalculationItem computableCalculationItem = mapEntry.getValue();
            computableCalculationItem.recalculate();
        }
        for (Map.Entry<Long, CalculationPriceItemDto> mapEntry : pricingProcessorResult.getTypeIdToCalculationPriceSetItemMap().entrySet()) {
            CalculationPriceItemDto calculationPriceSetItemDto = mapEntry.getValue();
            ComputableCalculationPriceSetItem computableCalculationPriceSetItem = pricesSection.getTypeIdToComputableCalculationPriceSetItemMap().get(mapEntry.getKey());
            computableCalculationPriceSetItem.setCalculationPriceItem(calculationPriceSetItemDto);
            computableCalculationPriceSetItem.recalculate();
        }
        blueprintInformationSection.getComputableBlueprintInformation().getCalculation().setPrice(pricingProcessorResult.getTotalPrice());
        blueprintInformationSection.getComputableBlueprintInformation().recalculate(calculator);
        return pricingProcessorResult;
    }
}
