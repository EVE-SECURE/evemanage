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
import lv.odylab.evemanage.client.presenter.tab.calculator.BlueprintItemTreeNode;
import lv.odylab.evemanage.client.presenter.tab.calculator.CalculationItemTree;
import lv.odylab.evemanage.client.presenter.tab.calculator.CalculationItemTreeNode;
import lv.odylab.evemanage.client.presenter.tab.calculator.CalculationProcessor;
import lv.odylab.evemanage.client.presenter.tab.calculator.CalculationProcessorResult;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.ComputableBlueprintInformation;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.ComputableBlueprintItem;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.ComputableCalculationItem;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.ComputableCalculationPriceSetItem;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.EditableBlueprintInformation;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.EditableBlueprintItem;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.EditableCalculationItem;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.EditableCalculationPriceSetItem;
import lv.odylab.evemanage.client.rpc.dto.ItemTypeDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationPriceItemDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.InventedBlueprintDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.UsedBlueprintDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.UsedSchematicDto;
import lv.odylab.evemanage.client.rpc.dto.user.SkillLevelDto;
import lv.odylab.evemanage.client.view.tab.quickcalculator.BlueprintInformationSection;
import lv.odylab.evemanage.client.view.tab.quickcalculator.BlueprintItemTreeSection;
import lv.odylab.evemanage.client.view.tab.quickcalculator.CalculationItemTreeSection;
import lv.odylab.evemanage.client.view.tab.quickcalculator.DirectLinkSection;
import lv.odylab.evemanage.client.view.tab.quickcalculator.PricesSection;
import lv.odylab.evemanage.client.view.tab.quickcalculator.SkillsForCalculationSection;
import lv.odylab.evemanage.client.widget.OpaqueLoadableBlueprintImage;
import lv.odylab.evemanage.client.widget.PriceTextBox;
import lv.odylab.evemanage.shared.EveCalculator;

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

    private QuickCalculatorTabPresenter.BlueprintInformationSectionDisplay blueprintInformationSection;
    private QuickCalculatorTabPresenter.CalculationItemTreeSectionDisplay calculationItemTreeSection;
    private QuickCalculatorTabPresenter.BlueprintItemTreeSectionDisplay blueprintItemTreeSection;
    private QuickCalculatorTabPresenter.PricesSectionDisplay pricesSection;
    private QuickCalculatorTabPresenter.SkillsForCalculationSectionDisplay skillsForCalculationSection;
    private QuickCalculatorTabPresenter.DirectLinkSectionDisplay directLinkSection;

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
        Map<Long, CalculationPriceItemDto> existingTypeIdToCalculationPriceSetItemMap = pricesSection.getTypeIdToCalculationPriceSetItemMap();
        pricesSection.cleanPricesSection();
        skillsForCalculationSection.cleanSkillsForCalculation();

        CalculationItemTree calculationItemTree = calculationItemTreeSection.getCalculationItemTree();
        BlueprintItemTree blueprintItemTree = blueprintItemTreeSection.getBlueprintItemTree();
        calculationItemTree.build(calculation);
        blueprintItemTree.build(calculation);

        CalculationProcessorResult calculationProcessorResult = calculationProcessor.process(calculation.getQuantity(), calculationItemTree, blueprintItemTree, existingTypeIdToCalculationPriceSetItemMap, createTypeIdToSkillLevelMap(calculation.getSkillLevels()));
        blueprintInformationSection.drawBlueprintInformation(calculation);
        calculationItemTreeSection.drawCalculationItemTree(calculationItemTree);
        blueprintItemTreeSection.drawBlueprintItemTree(blueprintItemTree);
        pricesSection.drawCalculationPriceSetItems(calculationProcessorResult.getTypeIdToCalculationPriceSetItemMap().values());
        skillsForCalculationSection.drawSkillsForCalculation(calculation.getSkillLevels());
        recalculate(existingTypeIdToCalculationPriceSetItemMap);
    }

    @Override
    public List<String> useBlueprint(Long[] pathNodes, UsedBlueprintDto usedBlueprint) {
        Map<Long[], UsedBlueprintDto> pathNodesToUsedBlueprintMap = new HashMap<Long[], UsedBlueprintDto>();
        pathNodesToUsedBlueprintMap.put(pathNodes, usedBlueprint);
        List<String> pathNodesStringsWithBlueprintOrSchematic = calculationItemTreeSection.addCalculationItemTreeNodes(pathNodesToUsedBlueprintMap);
        blueprintItemTreeSection.addBlueprintItemTreeNodes(pathNodesToUsedBlueprintMap);
        redrawPricesAndRecalculate();
        return pathNodesStringsWithBlueprintOrSchematic;
    }

    @Override
    public void stopUsingBlueprint(String pathNodesString) {
        ComputableCalculationItem computableCalculationItem = calculationItemTreeSection.getPathNodesStringToComputableCalculationItemMap().get(pathNodesString);
        List<Long[]> pathNodesList = new ArrayList<Long[]>();
        pathNodesList.add(computableCalculationItem.getCalculationItemTreeNodeSummary().getPathNodes());
        calculationItemTreeSection.excludeCalculationItemTreeNodesFromCalculation(pathNodesList);
        blueprintItemTreeSection.excludeBlueprintItemTreeNodesFromCalculation(pathNodesList);
        EditableCalculationItem editableCalculationItem = calculationItemTreeSection.getPathNodesStringToEditableCalculationItemMap().get(pathNodesString);
        calculationItemTreeSection.hideCalculationItemDetails(editableCalculationItem);
        calculationItemTreeSection.hideDetailsTable(editableCalculationItem);
        EditableBlueprintItem editableBlueprintItem = blueprintItemTreeSection.getPathNodesStringToEditableBlueprintItemMap().get(pathNodesString);
        blueprintItemTreeSection.hideBlueprintItemDetails(editableBlueprintItem);
        redrawPricesAndRecalculate();
    }

    @Override
    public void reuseBlueprint(String pathNodesString) {
        ComputableCalculationItem computableCalculationItem = calculationItemTreeSection.getPathNodesStringToComputableCalculationItemMap().get(pathNodesString);
        List<Long[]> pathNodesList = new ArrayList<Long[]>();
        pathNodesList.add(computableCalculationItem.getCalculationItemTreeNodeSummary().getPathNodes());
        calculationItemTreeSection.includeCalculationItemTreeNodesInCalculation(pathNodesList);
        blueprintItemTreeSection.includeBlueprintItemTreeNodesInCalculation(pathNodesList);
        EditableCalculationItem editableCalculationItem = calculationItemTreeSection.getPathNodesStringToEditableCalculationItemMap().get(pathNodesString);
        calculationItemTreeSection.showCalculationItemDetails(editableCalculationItem);
        EditableBlueprintItem editableBlueprintItem = blueprintItemTreeSection.getPathNodesStringToEditableBlueprintItemMap().get(pathNodesString);
        blueprintItemTreeSection.showBlueprintItemDetails(editableBlueprintItem);
        redrawPricesAndRecalculate();
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
        blueprintItemTreeSection.addBlueprintItemTreeNodes(pathNodesToUsedBlueprintMap);
        redrawPricesAndRecalculate();
        return pathNodesStringsWithBlueprintOrSchematic;
    }

    @Override
    public void stopUsingAllBlueprints() {
        List<Long[]> pathNodesList = new ArrayList<Long[]>();
        for (Map.Entry<String, EditableCalculationItem> mapEntry : calculationItemTreeSection.getPathNodesStringToEditableCalculationItemMap().entrySet()) {
            String pathNodesString = mapEntry.getKey();
            EditableCalculationItem editableCalculationItem = mapEntry.getValue();
            EditableBlueprintItem editableBlueprintItem = blueprintItemTreeSection.getPathNodesStringToEditableBlueprintItemMap().get(pathNodesString);
            ComputableCalculationItem computableCalculationItem = calculationItemTreeSection.getPathNodesStringToComputableCalculationItemMap().get(pathNodesString);
            OpaqueLoadableBlueprintImage blueprintImage = editableCalculationItem.getBlueprintImage();
            if (blueprintImage != null && !blueprintImage.hasOpacity()) {
                blueprintImage.setOpacity();
                calculationItemTreeSection.hideCalculationItemDetails(editableCalculationItem);
                calculationItemTreeSection.hideDetailsTable(editableCalculationItem);
                blueprintItemTreeSection.hideBlueprintItemDetails(editableBlueprintItem);
                pathNodesList.add(computableCalculationItem.getCalculationItemTreeNodeSummary().getPathNodes());
            }
        }
        calculationItemTreeSection.excludeCalculationItemTreeNodesFromCalculation(pathNodesList);
        blueprintItemTreeSection.excludeBlueprintItemTreeNodesFromCalculation(pathNodesList);
        redrawPricesAndRecalculate();
    }

    @Override
    public void reuseAllBlueprints() {
        List<Long[]> pathNodesList = new ArrayList<Long[]>();
        for (Map.Entry<String, EditableCalculationItem> mapEntry : calculationItemTreeSection.getPathNodesStringToEditableCalculationItemMap().entrySet()) {
            String pathNodesString = mapEntry.getKey();
            EditableCalculationItem editableCalculationItem = mapEntry.getValue();
            EditableBlueprintItem editableBlueprintItem = blueprintItemTreeSection.getPathNodesStringToEditableBlueprintItemMap().get(pathNodesString);
            ComputableCalculationItem computableCalculationItem = calculationItemTreeSection.getPathNodesStringToComputableCalculationItemMap().get(pathNodesString);
            OpaqueLoadableBlueprintImage blueprintImage = editableCalculationItem.getBlueprintImage();
            if (blueprintImage != null && blueprintImage.hasOpacity()) {
                blueprintImage.removeOpacity();
                calculationItemTreeSection.showCalculationItemDetails(editableCalculationItem);
                blueprintItemTreeSection.showBlueprintItemDetails(editableBlueprintItem);
                pathNodesList.add(computableCalculationItem.getCalculationItemTreeNodeSummary().getPathNodes());
            }
        }
        calculationItemTreeSection.includeCalculationItemTreeNodesInCalculation(pathNodesList);
        blueprintItemTreeSection.includeBlueprintItemTreeNodesInCalculation(pathNodesList);
        redrawPricesAndRecalculate();
    }

    @Override
    public void inventBlueprint(Long[] pathNodes, InventedBlueprintDto inventedBlueprint) {
        BlueprintItemTree blueprintItemTree = blueprintItemTreeSection.getBlueprintItemTree();
        blueprintItemTree.addInventedBlueprintNodes(inventedBlueprint.getBlueprintItems());
        BlueprintItemTreeNode blueprintItemTreeNode = blueprintItemTree.getNodeByPathNodes(pathNodes);
        String pathNodesString = blueprintItemTreeNode.getBlueprintItem().getPathExpression().getPathNodesString();
        EditableBlueprintItem editableBlueprintItem = blueprintItemTreeSection.getPathNodesStringToEditableBlueprintItemMap().get(pathNodesString);
        editableBlueprintItem.getInventionTable().setWidget(0, 0, editableBlueprintItem.getUseDecryptorButton());
        editableBlueprintItem.getBlueprintUseButton().setEnabled(true);
        blueprintItemTreeSection.drawDecryptors(editableBlueprintItem.getDecryptorTable(), inventedBlueprint.getDecryptors());
        List<ItemTypeDto> baseItems = inventedBlueprint.getBaseItems();
        if (baseItems.size() > 0) {
            editableBlueprintItem.getInventionTable().setWidget(0, 1, editableBlueprintItem.getUseBaseItemButton());
            blueprintItemTreeSection.drawBaseItems(editableBlueprintItem.getBaseItemTable(), baseItems);
        }
        blueprintItemTreeSection.drawChildBlueprintItems(blueprintItemTreeNode);
        skillsForCalculationSection.drawSkillsForCalculation(inventedBlueprint.getSkillLevels());
        redrawPricesAndRecalculate(pricesSection.getTypeIdToCalculationPriceSetItemMap());
    }

    @Override
    public List<String> useSchematic(Long[] pathNodes, UsedSchematicDto schematic) {
        Map<Long[], UsedSchematicDto> pathNodesToSchematicMap = new HashMap<Long[], UsedSchematicDto>();
        pathNodesToSchematicMap.put(pathNodes, schematic);
        List<String> pathNodesStringsWithSchematic = calculationItemTreeSection.addCalculationItemTreeNodesForSchematic(pathNodesToSchematicMap);
        redrawPricesAndRecalculate();
        return pathNodesStringsWithSchematic;
    }

    @Override
    public void stopUsingSchematics(String pathNodesString) {
        ComputableCalculationItem computableCalculationItem = calculationItemTreeSection.getPathNodesStringToComputableCalculationItemMap().get(pathNodesString);
        List<Long[]> pathNodesList = new ArrayList<Long[]>();
        pathNodesList.add(computableCalculationItem.getCalculationItemTreeNodeSummary().getPathNodes());
        calculationItemTreeSection.excludeCalculationItemTreeNodesFromCalculation(pathNodesList);
        EditableCalculationItem editableCalculationItem = calculationItemTreeSection.getPathNodesStringToEditableCalculationItemMap().get(pathNodesString);
        calculationItemTreeSection.hideCalculationItemDetails(editableCalculationItem);
        calculationItemTreeSection.hideDetailsTable(editableCalculationItem);
        redrawPricesAndRecalculate();
    }

    @Override
    public void reuseSchematics(String pathNodesString) {
        ComputableCalculationItem computableCalculationItem = calculationItemTreeSection.getPathNodesStringToComputableCalculationItemMap().get(pathNodesString);
        List<Long[]> pathNodesList = new ArrayList<Long[]>();
        pathNodesList.add(computableCalculationItem.getCalculationItemTreeNodeSummary().getPathNodes());
        calculationItemTreeSection.includeCalculationItemTreeNodesInCalculation(pathNodesList);
        EditableCalculationItem editableCalculationItem = calculationItemTreeSection.getPathNodesStringToEditableCalculationItemMap().get(pathNodesString);
        calculationItemTreeSection.showCalculationItemDetails(editableCalculationItem);
        redrawPricesAndRecalculate();
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
        for (Map.Entry<String, EditableBlueprintItem> mapEntry : blueprintItemTreeSection.getPathNodesStringToEditableBlueprintItemMap().entrySet()) {
            String pathNodesString = mapEntry.getKey();
            PriceTextBox copyPriceTextBox = mapEntry.getValue().getCopyPriceTextBox();
            if (copyPriceTextBox != null) {
                BigDecimal price = copyPriceTextBox.getPrice();
                ComputableBlueprintItem computableBlueprintItem = blueprintItemTreeSection.getPathNodesStringToComputableBlueprintItemMap().get(pathNodesString);
                computableBlueprintItem.getBlueprintItem().setPrice(price);
            }
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
    public void recalculate() {
        recalculate(pricesSection.getTypeIdToCalculationPriceSetItemMap());
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

    private CalculationProcessorResult recalculate(Map<Long, CalculationPriceItemDto> existingTypeIdToCalculationPriceSetItemMap) {
        return recalculate(processAndGetCalculationProcessorResult(existingTypeIdToCalculationPriceSetItemMap));
    }

    private CalculationProcessorResult recalculate(CalculationProcessorResult calculationProcessorResult) {
        for (ComputableCalculationItem computableCalculationItem : calculationItemTreeSection.getPathNodesStringToComputableCalculationItemMap().values()) {
            computableCalculationItem.recalculate();
        }
        for (ComputableBlueprintItem computableBlueprintItem : blueprintItemTreeSection.getPathNodesStringToComputableBlueprintItemMap().values()) {
            computableBlueprintItem.recalculate();
        }
        for (Map.Entry<Long, CalculationPriceItemDto> mapEntry : calculationProcessorResult.getTypeIdToCalculationPriceSetItemMap().entrySet()) {
            CalculationPriceItemDto calculationPriceSetItemDto = mapEntry.getValue();
            ComputableCalculationPriceSetItem computableCalculationPriceSetItem = pricesSection.getTypeIdToComputableCalculationPriceSetItemMap().get(mapEntry.getKey());
            computableCalculationPriceSetItem.setCalculationPriceItem(calculationPriceSetItemDto);
            computableCalculationPriceSetItem.recalculate();
        }
        ComputableBlueprintInformation computableBlueprintInformation = blueprintInformationSection.getComputableBlueprintInformation();
        computableBlueprintInformation.getCalculation().setPrice(calculationProcessorResult.getTotalPrice());
        computableBlueprintInformation.recalculate(calculator);
        return calculationProcessorResult;
    }

    private CalculationProcessorResult processAndGetCalculationProcessorResult(Map<Long, CalculationPriceItemDto> existingTypeIdToCalculationPriceSetItemMap) {
        Long quantity = Long.valueOf(blueprintInformationSection.getEditableBlueprintInformation().getQuantityTextBox().getText());
        CalculationItemTree calculationItemTree = calculationItemTreeSection.getCalculationItemTree();
        BlueprintItemTree blueprintItemTree = blueprintItemTreeSection.getBlueprintItemTree();
        Map<Long, Integer> typeIdToSkillLevelMap = skillsForCalculationSection.getTypeIdToSkillLevelMap();
        return calculationProcessor.process(quantity, calculationItemTree, blueprintItemTree, existingTypeIdToCalculationPriceSetItemMap, typeIdToSkillLevelMap);
    }

    private void redrawPricesAndRecalculate() {
        redrawPricesAndRecalculate(pricesSection.getTypeIdToCalculationPriceSetItemMap());
    }

    private void redrawPricesAndRecalculate(Map<Long, CalculationPriceItemDto> existingTypeIdToCalculationPriceSetItemMap) {
        CalculationProcessorResult calculationProcessorResult = processAndGetCalculationProcessorResult(existingTypeIdToCalculationPriceSetItemMap);
        pricesSection.cleanPricesSection();
        pricesSection.drawCalculationPriceSetItems(calculationProcessorResult.getTypeIdToCalculationPriceSetItemMap().values());
        recalculate(calculationProcessorResult);
    }

    private Map<Long, Integer> createTypeIdToSkillLevelMap(List<SkillLevelDto> skillLevels) {
        Map<Long, Integer> typeIdToSkillLevelMap = new HashMap<Long, Integer>();
        for (SkillLevelDto skillLevel : skillLevels) {
            typeIdToSkillLevelMap.put(skillLevel.getTypeID(), skillLevel.getLevel());
        }
        return typeIdToSkillLevelMap;
    }
}
