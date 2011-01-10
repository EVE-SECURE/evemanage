package lv.odylab.evemanage.client.presenter.tab;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageErrorConstants;
import lv.odylab.evemanage.client.event.QuickCalculatorTabActionCallback;
import lv.odylab.evemanage.client.event.error.QuickCalculatorTabErrorEvent;
import lv.odylab.evemanage.client.event.error.QuickCalculatorTabErrorEventHandler;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorDirectSetEvent;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorDirectSetEventHandler;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorFetchedPricesFromEveCentralEvent;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorFetchedPricesFromEveCentralEventHandler;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorFetchedPricesFromEveMetricsEvent;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorFetchedPricesFromEveMetricsEventHandler;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorInventedBlueprintEvent;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorInventedBlueprintEventHandler;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorReusedAllBlueprintsEvent;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorReusedAllBlueprintsEventHandler;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorReusedBlueprintEvent;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorReusedBlueprintEventHandler;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorReusedSchematicEvent;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorReusedSchematicEventHandler;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorSetEvent;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorSetEventHandler;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorStoppedUsingAllBlueprintsEvent;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorStoppedUsingAllBlueprintsEventHandler;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorStoppedUsingBlueprintEvent;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorStoppedUsingBlueprintEventHandler;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorStoppedUsingSchematicEvent;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorStoppedUsingSchematicEventHandler;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorTabFirstLoadEvent;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorTabFirstLoadEventHandler;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorUsedAllBlueprintsEvent;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorUsedAllBlueprintsEventHandler;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorUsedBlueprintEvent;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorUsedBlueprintEventHandler;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorUsedSchematicEvent;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorUsedSchematicEventHandler;
import lv.odylab.evemanage.client.presenter.AttachableDisplay;
import lv.odylab.evemanage.client.presenter.Presenter;
import lv.odylab.evemanage.client.presenter.tab.calculator.BlueprintItemTree;
import lv.odylab.evemanage.client.presenter.tab.calculator.BlueprintItemTreeNode;
import lv.odylab.evemanage.client.presenter.tab.calculator.CalculationItemTree;
import lv.odylab.evemanage.client.presenter.tab.calculator.CalculationItemTreeNodeSummary;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.ComputableBlueprintInformation;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.ComputableBlueprintItem;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.ComputableCalculationItem;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.ComputableCalculationPriceSetItem;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.EditableBlueprintInformation;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.EditableBlueprintItem;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.EditableCalculationItem;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.EditableCalculationPriceSetItem;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.EditableCalculationSkillLevel;
import lv.odylab.evemanage.client.rpc.CalculationExpression;
import lv.odylab.evemanage.client.rpc.EveManageRemoteServiceAsync;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorFetchPricesFromEveCentralAction;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorFetchPricesFromEveCentralActionResponse;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorFetchPricesFromEveMetricsAction;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorFetchPricesFromEveMetricsActionResponse;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorInventBlueprintAction;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorInventBlueprintActionResponse;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorSetAction;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorSetActionResponse;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorTabFirstLoadAction;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorTabFirstLoadActionResponse;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorUseAllBlueprintsAction;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorUseAllBlueprintsActionResponse;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorUseBlueprintAction;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorUseBlueprintActionResponse;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorUseSchematicAction;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorUseSchematicActionResponse;
import lv.odylab.evemanage.client.rpc.dto.ItemTypeDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.BlueprintItemDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationPriceItemDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.DecryptorDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.InventedBlueprintDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.UsedBlueprintDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.UsedSchematicDto;
import lv.odylab.evemanage.client.rpc.dto.eve.RegionDto;
import lv.odylab.evemanage.client.rpc.dto.user.PriceFetchOptionDto;
import lv.odylab.evemanage.client.rpc.dto.user.SkillLevelDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;
import lv.odylab.evemanage.client.widget.BlueprintUseButton;
import lv.odylab.evemanage.client.widget.OnlyDigitsAndMinusChangeHandler;
import lv.odylab.evemanage.client.widget.OnlyDigitsChangeHandler;
import lv.odylab.evemanage.client.widget.OpaqueLoadableBlueprintImage;
import lv.odylab.evemanage.client.widget.OpaqueLoadableSchematicImage;
import lv.odylab.evemanage.client.widget.PriceFetchOptionListBox;
import lv.odylab.evemanage.client.widget.PriceTextBox;
import lv.odylab.evemanage.client.widget.RegionListBox;
import lv.odylab.evemanage.client.widget.SkillBookImage;
import lv.odylab.evemanage.client.widget.SkillLevelImage;
import lv.odylab.evemanage.client.widget.SkillLevelListBox;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuickCalculatorTabPresenter implements Presenter, ValueChangeHandler<String>, QuickCalculatorTabErrorEventHandler, QuickCalculatorTabFirstLoadEventHandler, QuickCalculatorSetEventHandler, QuickCalculatorDirectSetEventHandler, QuickCalculatorUsedBlueprintEventHandler, QuickCalculatorReusedBlueprintEventHandler, QuickCalculatorStoppedUsingBlueprintEventHandler, QuickCalculatorUsedAllBlueprintsEventHandler, QuickCalculatorStoppedUsingAllBlueprintsEventHandler, QuickCalculatorReusedAllBlueprintsEventHandler, QuickCalculatorInventedBlueprintEventHandler, QuickCalculatorUsedSchematicEventHandler, QuickCalculatorReusedSchematicEventHandler, QuickCalculatorStoppedUsingSchematicEventHandler, QuickCalculatorFetchedPricesFromEveCentralEventHandler, QuickCalculatorFetchedPricesFromEveMetricsEventHandler {

    public interface Display extends AttachableDisplay {

        Widget getSpinnerImage();

        Widget getErrorContainer();

        HasText getErrorMessageLabel();

        void setNewCalculation(CalculationDto calculation);

        List<String> useBlueprint(Long[] pathNodes, UsedBlueprintDto calculation);

        void stopUsingBlueprint(String pathNodesString);

        void reuseBlueprint(String pathNodesString);

        List<String> useAllBlueprints(Map<Long[], UsedBlueprintDto> pathNodesToUsedBlueprintMap);

        void stopUsingAllBlueprints();

        void reuseAllBlueprints();

        void inventBlueprint(Long[] pathNodes, InventedBlueprintDto inventedBlueprintDto);

        List<String> useSchematic(Long[] pathNodes, UsedSchematicDto schematic);

        void stopUsingSchematics(String pathNodesString);

        void reuseSchematics(String pathNodesString);

        void updatePrices();

        void updatePrices(Map<Long, BigDecimal> typeIdToPriceMap);

        void changeBlueprintMePeQuantity(Integer meLevel, Integer peLevel, Long quantity);

        void changeCalculationItemMePe(Long[] pathNodes, Integer meLevel, Integer peLevel);

        void recalculate();

        BlueprintInformationSectionDisplay getBlueprintInformationSectionDisplay();

        CalculationItemTreeSectionDisplay getCalculationTreeSectionDisplay();

        BlueprintItemTreeSectionDisplay getBlueprintUsageSectionDisplay();

        PricesSectionDisplay getPricesSectionDisplay();

        SkillsForCalculationSectionDisplay getSkillsForCalculationSectionDisplay();

        DirectLinkSectionDisplay getDirectLinkSectionDisplay();

    }

    public interface BlueprintInformationSectionDisplay extends AttachableDisplay {

        SuggestBox getBlueprintSuggestBox();

        Button getSetButton();

        void drawBlueprintInformation(CalculationDto calculation);

        void cleanBlueprintInformation();

        EditableBlueprintInformation getEditableBlueprintInformation();

        ComputableBlueprintInformation getComputableBlueprintInformation();

        List<HandlerRegistration> getBlueprintInformationSectionStaticRegistrationHandlers();

    }

    public interface CalculationItemTreeSectionDisplay extends AttachableDisplay {

        CalculationItemTree getCalculationItemTree();

        void drawCalculationItemTree(CalculationItemTree calculationItemTree);

        void cleanCalculationItemTree();

        void showCalculationItemDetails(EditableCalculationItem editableCalculationItem);

        void hideCalculationItemDetails(EditableCalculationItem editableCalculationItem);

        void hideDetailsTable(EditableCalculationItem editableCalculationItem);

        List<String> getPathNodeStringsWithUsedBlueprint();

        List<String> getPathNodeStringsWithUsedSchematic();

        List<String> addCalculationItemTreeNodes(Map<Long[], UsedBlueprintDto> pathNodesToUsedBlueprintMap);

        List<String> addCalculationItemTreeNodesForSchematic(Map<Long[], UsedSchematicDto> pathNodesToUsedSchematicMap);

        void excludeCalculationItemTreeNodesFromCalculation(List<Long[]> pathNodesList);

        void includeCalculationItemTreeNodesInCalculation(List<Long[]> pathNodesList);

        Map<String, EditableCalculationItem> getPathNodesStringToEditableCalculationItemMap();

        Map<String, ComputableCalculationItem> getPathNodesStringToComputableCalculationItemMap();

        List<HandlerRegistration> getCalculationItemTreeSectionRegistrationHandlers();

    }

    public interface BlueprintItemTreeSectionDisplay extends AttachableDisplay {

        BlueprintItemTree getBlueprintItemTree();

        void drawBlueprintItemTree(BlueprintItemTree blueprintItemTree);

        void cleanBlueprintItemTree();

        void showBlueprintItemDetails(EditableBlueprintItem editableBlueprintItem);

        void hideBlueprintItemDetails(EditableBlueprintItem editableBlueprintItem);

        void showBlueprintInventionItems(EditableBlueprintItem editableBlueprintItem);

        void hideBlueprintInventionItems(EditableBlueprintItem editableBlueprintItem);

        void addBlueprintItemTreeNodes(Map<Long[], UsedBlueprintDto> pathNodesToUsedBlueprintMap);

        void drawChildBlueprintItems(BlueprintItemTreeNode blueprintItemTreeNode);

        void drawDecryptors(FlexTable decryptorTable, List<DecryptorDto> decryptors);

        void drawBaseItems(FlexTable baseItemTable, List<ItemTypeDto> baseItems);

        void excludeBlueprintItemTreeNodesFromCalculation(List<Long[]> pathNodesList);

        void includeBlueprintItemTreeNodesInCalculation(List<Long[]> pathNodesList);

        Map<String, EditableBlueprintItem> getPathNodesStringToEditableBlueprintItemMap();

        Map<String, ComputableBlueprintItem> getPathNodesStringToComputableBlueprintItemMap();

    }

    public interface PricesSectionDisplay extends AttachableDisplay {

        Widget getPricesSectionSpinnerImage();

        Button getFetchEveCentralPricesButton();

        Button getFetchEveMetricsPricesButton();

        void drawCalculationPriceSetItems(Collection<CalculationPriceItemDto> calculationPriceItems);

        void cleanPricesSection();

        void setRegions(List<RegionDto> regions);

        void setPreferredRegion(RegionDto preferredRegion);

        RegionListBox getPreferredRegionListBox();

        void setPriceFetchOptions(List<PriceFetchOptionDto> priceFetchOptions);

        void setPreferredPriceFetchOption(PriceFetchOptionDto preferredPriceFetchOption);

        PriceFetchOptionListBox getPreferredPriceFetchOption();

        Map<Long, CalculationPriceItemDto> getTypeIdToCalculationPriceSetItemMap();

        Map<Long, EditableCalculationPriceSetItem> getTypeIdToEditableCalculationPriceSetItemMap();

        Map<Long, ComputableCalculationPriceSetItem> getTypeIdToComputableCalculationPriceSetItemMap();

        List<HandlerRegistration> getPricesSectionStaticRegistrationHandlers();

    }

    public interface SkillsForCalculationSectionDisplay extends AttachableDisplay {

        void drawSkillsForCalculation(List<SkillLevelDto> skillLevels);

        void cleanSkillsForCalculation();

        Map<Long, Integer> getTypeIdToSkillLevelMap();

        Map<Long, EditableCalculationSkillLevel> getTypeIdToEditableCalculationSkillLevelMap();

    }

    public interface DirectLinkSectionDisplay extends AttachableDisplay {

        Button getCreateDirectLinkButton();

        void createDirectLink();

    }

    private EventBus eventBus;
    private TrackingManager trackingManager;
    private EveManageRemoteServiceAsync rpcService;
    private EveManageConstants constants;
    private EveManageErrorConstants errorConstants;
    private Display display;
    private BlueprintInformationSectionDisplay blueprintInformationSectionDisplay;
    private CalculationItemTreeSectionDisplay calculationItemTreeSectionDisplay;
    private BlueprintItemTreeSectionDisplay blueprintItemTreeSectionDisplay;
    private PricesSectionDisplay pricesSectionDisplay;
    private SkillsForCalculationSectionDisplay skillsForCalculationSectionDisplay;
    private DirectLinkSectionDisplay directLinkSectionDisplay;

    private HasWidgets displayContainer;
    private List<HandlerRegistration> staticHandlerRegistrations;
    private List<HandlerRegistration> dynamicHandlerRegistrations;
    private List<HandlerRegistration> pricesSectionDynamicHandlerRegistrations;

    @Inject
    public QuickCalculatorTabPresenter(EventBus eventBus, TrackingManager trackingManager, EveManageRemoteServiceAsync rpcService, EveManageConstants constants, EveManageErrorConstants errorConstants, Display display) {
        this.eventBus = eventBus;
        this.trackingManager = trackingManager;
        this.rpcService = rpcService;
        this.constants = constants;
        this.errorConstants = errorConstants;
        this.display = display;
        this.blueprintInformationSectionDisplay = display.getBlueprintInformationSectionDisplay();
        this.calculationItemTreeSectionDisplay = display.getCalculationTreeSectionDisplay();
        this.blueprintItemTreeSectionDisplay = display.getBlueprintUsageSectionDisplay();
        this.pricesSectionDisplay = display.getPricesSectionDisplay();
        this.skillsForCalculationSectionDisplay = display.getSkillsForCalculationSectionDisplay();
        this.directLinkSectionDisplay = display.getDirectLinkSectionDisplay();

        this.staticHandlerRegistrations = new ArrayList<HandlerRegistration>();
        this.dynamicHandlerRegistrations = new ArrayList<HandlerRegistration>();
        this.pricesSectionDynamicHandlerRegistrations = new ArrayList<HandlerRegistration>();

        History.addValueChangeHandler(this);
        eventBus.addHandler(QuickCalculatorTabErrorEvent.TYPE, this);
        eventBus.addHandler(QuickCalculatorTabFirstLoadEvent.TYPE, this);
        eventBus.addHandler(QuickCalculatorSetEvent.TYPE, this);
        eventBus.addHandler(QuickCalculatorDirectSetEvent.TYPE, this);
        eventBus.addHandler(QuickCalculatorUsedBlueprintEvent.TYPE, this);
        eventBus.addHandler(QuickCalculatorReusedBlueprintEvent.TYPE, this);
        eventBus.addHandler(QuickCalculatorStoppedUsingBlueprintEvent.TYPE, this);
        eventBus.addHandler(QuickCalculatorUsedAllBlueprintsEvent.TYPE, this);
        eventBus.addHandler(QuickCalculatorStoppedUsingAllBlueprintsEvent.TYPE, this);
        eventBus.addHandler(QuickCalculatorReusedAllBlueprintsEvent.TYPE, this);
        eventBus.addHandler(QuickCalculatorInventedBlueprintEvent.TYPE, this);
        eventBus.addHandler(QuickCalculatorUsedSchematicEvent.TYPE, this);
        eventBus.addHandler(QuickCalculatorReusedSchematicEvent.TYPE, this);
        eventBus.addHandler(QuickCalculatorStoppedUsingSchematicEvent.TYPE, this);
        eventBus.addHandler(QuickCalculatorFetchedPricesFromEveCentralEvent.TYPE, this);
        eventBus.addHandler(QuickCalculatorFetchedPricesFromEveMetricsEvent.TYPE, this);
    }

    @Override
    public void go(HasWidgets container) {
        if (displayContainer == null) {
            displayContainer = container;
            displayContainer.clear();
            display.attach(displayContainer);
            bindStatic();
            doQuickCalculatorTabTabFirstLoad();
        }
    }

    @Override
    public void onValueChange(ValueChangeEvent<String> stringValueChangeEvent) {
        processHistoryToken();
    }

    @Override
    public void onQuickCalculatorTabError(QuickCalculatorTabErrorEvent event) {
        display.getErrorContainer().setVisible(true);
        String errorText;
        try {
            errorText = errorConstants.getString(event.getErrorMessage());
        } catch (Exception e) {
            errorText = errorConstants.errorOnServer();
        }
        display.getErrorMessageLabel().setText(errorText);
        hideSpinner();
        hidePricesSectionSpinner();
    }

    @Override
    public void onQuickCalculatorTabFirstLoad(QuickCalculatorTabFirstLoadEvent event) {
        pricesSectionDisplay.setRegions(event.getRegions());
        pricesSectionDisplay.setPreferredRegion(event.getPreferredRegion());
        pricesSectionDisplay.setPriceFetchOptions(event.getPriceFetchOptions());
        pricesSectionDisplay.setPreferredPriceFetchOption(event.getPreferredPriceFetchOption());
        hideSpinner();
        enableButtons();

        processHistoryToken();
    }

    // TODO this needs revamp
    private void processHistoryToken() {
        String historyToken = History.getToken();
        CalculationExpression calculationExpression = CalculationExpression.parseExpression(historyToken);
        if (historyToken.startsWith(constants.quickCalculatorToken()) && calculationExpression.isValid()) {
            eventBus.fireEvent(new QuickCalculatorTabErrorEvent(trackingManager, constants, "directLinksTemporaryDisabled"));
            /*SuggestBox blueprintSuggestBox = blueprintInformationSectionDisplay.getBlueprintSuggestBox();
            final Button setButton = blueprintInformationSectionDisplay.getSetButton();
            blueprintSuggestBox.getTextBox().setText(calculationExpression.getBlueprintTypeName().replace('+', ' '));
            setButton.setEnabled(false);
            QuickCalculatorDirectSetAction action = new QuickCalculatorDirectSetAction();
            action.setCalculationExpression(calculationExpression);
            action.setHistoryToken(historyToken);
            showSpinner();
            rpcService.execute(action, new QuickCalculatorTabActionCallback<QuickCalculatorDirectSetActionResponse>(eventBus, trackingManager, constants) {
                @Override
                public void onSuccess(QuickCalculatorDirectSetActionResponse response) {
                    setButton.setEnabled(true);
                    eventBus.fireEvent(new QuickCalculatorDirectSetEvent(trackingManager, constants, response, getExecutionDuration()));
                }

                @Override
                public void onFailure(Throwable throwable) {
                    setButton.setEnabled(true);
                    super.onFailure(throwable);
                }
            });*/
        }
    }

    @Override
    public void onQuickCalculatorSet(QuickCalculatorSetEvent event) {
        unbindDynamic();
        display.setNewCalculation(event.getCalculation());
        pricesSectionDisplay.getFetchEveCentralPricesButton().setEnabled(true);
        pricesSectionDisplay.getFetchEveMetricsPricesButton().setEnabled(true);
        pricesSectionDisplay.getPreferredRegionListBox().setEnabled(true);
        pricesSectionDisplay.getPreferredPriceFetchOption().setEnabled(true);
        bindDynamic();
        hideSpinner();
    }

    // TODO this needs revamp
    @Override
    public void onQuickCalculatorDirectSet(QuickCalculatorDirectSetEvent event) {
        /*unbindDynamic();
        CalculationDto calculation = event.useBlueprint();
        CalculationExpression calculationExpression = event.getCalculationExpression();
        display.getExistingTypeIdToCalculationPriceSetItemMap().clear();
        display.getTypeIdToEditableCalculationPriceSetItemMap().clear();
        display.setNewCalculation(calculation);

        List<String> pathNodeStringsWithBlueprint = display.addCalculationItemTreeNodes(event.getPathNodesToUsedBlueprintMap());
        for (String pathNodesString : pathNodeStringsWithBlueprint) {
            EditableCalculationItem editableCalculationItem = display.getPathNodesStringToEditableCalculationItemMap().get(pathNodesString);
            ComputableCalculationItem computableCalculationItem = display.getPathNodesStringToComputableCalculationItemMap().get(pathNodesString);
            bindUseBlueprintImage(computableCalculationItem.getCalculationItemTreeNodeSummary(), editableCalculationItem.getBlueprintImage());
            bindApplyButton(editableCalculationItem, computableCalculationItem);
        }
        for (Map.Entry<String, EditableCalculationItem> mapEntry : display.getPathNodesStringToEditableCalculationItemMap().entrySet()) {
            EditableCalculationItem editableCalculationItem = mapEntry.getValue();
            OpaqueLoadableBlueprintImage blueprintImage = editableCalculationItem.getBlueprintImage();
            if (blueprintImage != null && display.getPathNodeStringsWithUsedBlueprint().containsKey(mapEntry.getKey())) {
                blueprintImage.removeOpacity();
            }
        }

        display.changeBlueprintMePeQuantity(calculation.getMaterialLevel(), calculation.getProductivityLevel(), calculationExpression.getQuantity());
        Map<Long, String> priceSetItemTypeIdToPriceMap = calculationExpression.getPriceSetItemTypeIdToPriceMap();
        for (Map.Entry<Long, EditableCalculationPriceSetItem> entry : display.getTypeIdToEditableCalculationPriceSetItemMap().entrySet()) {
            Long typeID = entry.getKey();
            EditableCalculationPriceSetItem calculationPriceSetItem = entry.getValue();
            String price = priceSetItemTypeIdToPriceMap.get(typeID);
            if (price != null) {
                calculationPriceSetItem.getPriceTextBox().setPrice(price);
            }
        }

        display.updatePrices();
        display.getApplyButton().setEnabled(true);
        display.getFetchEveCentralPricesButton().setEnabled(true);
        display.getFetchEveMetricsPricesButton().setEnabled(true);
        display.getCreateDirectLinkButton().setEnabled(true);
        bindDynamic();
        hideSpinner();*/
    }

    @Override
    public void onUsedBlueprint(QuickCalculatorUsedBlueprintEvent event) {
        List<String> pathNodesStringsWithBlueprintOrSchematic = display.useBlueprint(event.getPathNodes(), event.getUsedBlueprint());
        for (String pathNodesString : pathNodesStringsWithBlueprintOrSchematic) {
            EditableCalculationItem editableCalculationItem = calculationItemTreeSectionDisplay.getPathNodesStringToEditableCalculationItemMap().get(pathNodesString);
            ComputableCalculationItem computableCalculationItem = calculationItemTreeSectionDisplay.getPathNodesStringToComputableCalculationItemMap().get(pathNodesString);
            if (editableCalculationItem.getBlueprintImage() != null) {
                bindUseBlueprintImage(computableCalculationItem.getCalculationItemTreeNodeSummary(), editableCalculationItem.getBlueprintImage());
                bindApplyButton(editableCalculationItem, computableCalculationItem);
            } else if (editableCalculationItem.getSchematicImage() != null) {
                bindUseSchematicImage(computableCalculationItem.getCalculationItemTreeNodeSummary(), editableCalculationItem.getSchematicImage());
            }
        }
        //EditableBlueprintItem editableBlueprintItem = blueprintItemTreeSectionDisplay.getPathNodesStringToEditableBlueprintItemMap().get(event.getPathNodesString());
        //bindCopyPriceTextBox(editableBlueprintItem);
        rebindPricesSectionDynamicHandlers();
    }

    @Override
    public void onStoppedUsingBlueprint(QuickCalculatorStoppedUsingBlueprintEvent event) {
        display.stopUsingBlueprint(event.getPathNodesString());
        rebindPricesSectionDynamicHandlers();
    }

    @Override
    public void onReusedBlueprint(QuickCalculatorReusedBlueprintEvent event) {
        display.reuseBlueprint(event.getPathNodesString());
        rebindPricesSectionDynamicHandlers();
    }

    @Override
    public void onUsedAllBlueprints(QuickCalculatorUsedAllBlueprintsEvent event) {
        List<String> pathNodesStringsWithBlueprintOrSchematic = display.useAllBlueprints(event.getPathNodesToUsedBlueprintMap());
        for (String pathNodesString : pathNodesStringsWithBlueprintOrSchematic) {
            EditableCalculationItem editableCalculationItem = calculationItemTreeSectionDisplay.getPathNodesStringToEditableCalculationItemMap().get(pathNodesString);
            ComputableCalculationItem computableCalculationItem = calculationItemTreeSectionDisplay.getPathNodesStringToComputableCalculationItemMap().get(pathNodesString);
            if (editableCalculationItem.getBlueprintImage() != null) {
                bindUseBlueprintImage(computableCalculationItem.getCalculationItemTreeNodeSummary(), editableCalculationItem.getBlueprintImage());
                bindApplyButton(editableCalculationItem, computableCalculationItem);
            } else if (editableCalculationItem.getSchematicImage() != null) {
                bindUseSchematicImage(computableCalculationItem.getCalculationItemTreeNodeSummary(), editableCalculationItem.getSchematicImage());
            }
        }
        rebindPricesSectionDynamicHandlers();
    }

    @Override
    public void onStoppedUsingAllBlueprints(QuickCalculatorStoppedUsingAllBlueprintsEvent event) {
        display.stopUsingAllBlueprints();
        rebindPricesSectionDynamicHandlers();
    }

    @Override
    public void onReusedAllBlueprints(QuickCalculatorReusedAllBlueprintsEvent event) {
        display.reuseAllBlueprints();
        rebindPricesSectionDynamicHandlers();
    }

    @Override
    public void onInventedBlueprint(QuickCalculatorInventedBlueprintEvent event) {
        display.inventBlueprint(event.getPathNodes(), event.getInventedBlueprint());
        rebindPricesSectionDynamicHandlers();
    }

    @Override
    public void onUsedSchematic(QuickCalculatorUsedSchematicEvent event) {
        List<String> pathNodesStringsWithSchematic = display.useSchematic(event.getPathNodes(), event.getUsedSchematic());
        for (String pathNodesString : pathNodesStringsWithSchematic) {
            EditableCalculationItem editableCalculationItem = calculationItemTreeSectionDisplay.getPathNodesStringToEditableCalculationItemMap().get(pathNodesString);
            ComputableCalculationItem computableCalculationItem = calculationItemTreeSectionDisplay.getPathNodesStringToComputableCalculationItemMap().get(pathNodesString);
            bindUseSchematicImage(computableCalculationItem.getCalculationItemTreeNodeSummary(), editableCalculationItem.getSchematicImage());
        }
        rebindPricesSectionDynamicHandlers();
    }

    @Override
    public void onReusedSchematic(QuickCalculatorReusedSchematicEvent event) {
        display.reuseSchematics(event.getPathNodesString());
        rebindPricesSectionDynamicHandlers();
    }

    @Override
    public void onStoppedUsingSchematic(QuickCalculatorStoppedUsingSchematicEvent event) {
        display.stopUsingSchematics(event.getPathNodesString());
        rebindPricesSectionDynamicHandlers();
    }

    @Override
    public void onFetchedPricesFromEveCentral(QuickCalculatorFetchedPricesFromEveCentralEvent event) {
        display.updatePrices(event.getTypeIdToPriceMap());
        hidePricesSectionSpinner();
    }

    @Override
    public void onFetchedPricesFromEveMetrics(QuickCalculatorFetchedPricesFromEveMetricsEvent event) {
        display.updatePrices(event.getTypeIdToPriceMap());
        hidePricesSectionSpinner();
    }

    private void doQuickCalculatorTabTabFirstLoad() {
        showSpinner();
        rpcService.execute(new QuickCalculatorTabFirstLoadAction(), new QuickCalculatorTabActionCallback<QuickCalculatorTabFirstLoadActionResponse>(eventBus, trackingManager, constants) {
            @Override
            public void onSuccess(QuickCalculatorTabFirstLoadActionResponse response) {
                eventBus.fireEvent(new QuickCalculatorTabFirstLoadEvent(trackingManager, constants, response, getExecutionDuration()));
            }
        });
    }

    private void bindStatic() {
        final Button setButton = blueprintInformationSectionDisplay.getSetButton();
        staticHandlerRegistrations.add(setButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                setButton.setEnabled(false);
                QuickCalculatorSetAction action = new QuickCalculatorSetAction();
                action.setBlueprintName(blueprintInformationSectionDisplay.getBlueprintSuggestBox().getText());
                showSpinner();
                rpcService.execute(action, new QuickCalculatorTabActionCallback<QuickCalculatorSetActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(QuickCalculatorSetActionResponse response) {
                        setButton.setEnabled(true);
                        eventBus.fireEvent(new QuickCalculatorSetEvent(trackingManager, constants, response, getExecutionDuration()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        setButton.setEnabled(true);
                        super.onFailure(throwable);
                    }
                });
            }
        }));
        final Button fetchEveCentralPricesButton = pricesSectionDisplay.getFetchEveCentralPricesButton();
        staticHandlerRegistrations.add(fetchEveCentralPricesButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                fetchEveCentralPricesButton.setEnabled(false);
                QuickCalculatorFetchPricesFromEveCentralAction action = new QuickCalculatorFetchPricesFromEveCentralAction();
                action.setTypeIDs(new ArrayList<Long>(pricesSectionDisplay.getTypeIdToCalculationPriceSetItemMap().keySet()));
                action.setPreferredRegionID(pricesSectionDisplay.getPreferredRegionListBox().getRegionID());
                action.setPreferredPriceFetchOption(pricesSectionDisplay.getPreferredPriceFetchOption().getPriceFetchOption());
                showPricesSectionSpinner();
                rpcService.execute(action, new QuickCalculatorTabActionCallback<QuickCalculatorFetchPricesFromEveCentralActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(QuickCalculatorFetchPricesFromEveCentralActionResponse response) {
                        fetchEveCentralPricesButton.setEnabled(true);
                        eventBus.fireEvent(new QuickCalculatorFetchedPricesFromEveCentralEvent(trackingManager, constants, response, getExecutionDuration()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        fetchEveCentralPricesButton.setEnabled(true);
                        super.onFailure(throwable);
                    }
                });
            }
        }));
        final Button fetchEveMetricsPricesButton = pricesSectionDisplay.getFetchEveMetricsPricesButton();
        staticHandlerRegistrations.add(fetchEveMetricsPricesButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                fetchEveMetricsPricesButton.setEnabled(false);
                QuickCalculatorFetchPricesFromEveMetricsAction action = new QuickCalculatorFetchPricesFromEveMetricsAction();
                action.setTypeIDs(new ArrayList<Long>(pricesSectionDisplay.getTypeIdToCalculationPriceSetItemMap().keySet()));
                action.setPreferredRegionID(pricesSectionDisplay.getPreferredRegionListBox().getRegionID());
                action.setPreferredPriceFetchOption(pricesSectionDisplay.getPreferredPriceFetchOption().getPriceFetchOption());
                showPricesSectionSpinner();
                rpcService.execute(action, new QuickCalculatorTabActionCallback<QuickCalculatorFetchPricesFromEveMetricsActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(QuickCalculatorFetchPricesFromEveMetricsActionResponse response) {
                        fetchEveMetricsPricesButton.setEnabled(true);
                        eventBus.fireEvent(new QuickCalculatorFetchedPricesFromEveMetricsEvent(trackingManager, constants, response, getExecutionDuration()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        fetchEveMetricsPricesButton.setEnabled(true);
                        super.onFailure(throwable);
                    }
                });
            }
        }));
        staticHandlerRegistrations.add(directLinkSectionDisplay.getCreateDirectLinkButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                directLinkSectionDisplay.createDirectLink();
            }
        }));
        staticHandlerRegistrations.addAll(blueprintInformationSectionDisplay.getBlueprintInformationSectionStaticRegistrationHandlers());
        staticHandlerRegistrations.addAll(pricesSectionDisplay.getPricesSectionStaticRegistrationHandlers());
    }

    private void bindDynamic() {
        bindApplyButton(blueprintInformationSectionDisplay.getEditableBlueprintInformation());
        bindUseAllBlueprintsImage(blueprintInformationSectionDisplay.getEditableBlueprintInformation().getUseAllBlueprintsImage());
        for (Map.Entry<String, EditableCalculationItem> mapEntry : calculationItemTreeSectionDisplay.getPathNodesStringToEditableCalculationItemMap().entrySet()) {
            String pathNodesString = mapEntry.getKey();
            EditableCalculationItem editableCalculationItem = mapEntry.getValue();
            ComputableCalculationItem computableCalculationItem = calculationItemTreeSectionDisplay.getPathNodesStringToComputableCalculationItemMap().get(pathNodesString);
            if (editableCalculationItem.getBlueprintImage() != null) {
                bindUseBlueprintImage(computableCalculationItem.getCalculationItemTreeNodeSummary(), editableCalculationItem.getBlueprintImage());
                bindApplyButton(editableCalculationItem, computableCalculationItem);
            } else if (editableCalculationItem.getSchematicImage() != null) {
                bindUseSchematicImage(computableCalculationItem.getCalculationItemTreeNodeSummary(), editableCalculationItem.getSchematicImage());
            }
        }
        for (Map.Entry<String, EditableBlueprintItem> mapEntry : blueprintItemTreeSectionDisplay.getPathNodesStringToEditableBlueprintItemMap().entrySet()) {
            String pathNodesString = mapEntry.getKey();
            EditableBlueprintItem editableBlueprintItem = mapEntry.getValue();
            ComputableBlueprintItem computableBlueprintItem = blueprintItemTreeSectionDisplay.getPathNodesStringToComputableBlueprintItemMap().get(pathNodesString);
            FlexTable inventionTable = editableBlueprintItem.getInventionTable();
            BlueprintItemDto blueprintItem = computableBlueprintItem.getBlueprintItem();
            if (inventionTable != null) {
                bindInventionAnchor(blueprintItem, editableBlueprintItem);
            }
            bindUseOriginalAnchor(blueprintItem, editableBlueprintItem);
            bindUseCopyAnchor(blueprintItem, editableBlueprintItem);
            final PriceTextBox priceTextBox = editableBlueprintItem.getCopyPriceTextBox();
            dynamicHandlerRegistrations.add(priceTextBox.addChangeHandler(new ChangeHandler() {
                @Override
                public void onChange(ChangeEvent event) {
                    priceTextBox.setPrice(priceTextBox.getText());
                    display.updatePrices();
                }
            }));
        }
        for (EditableCalculationSkillLevel editableCalculationSkillLevel : skillsForCalculationSectionDisplay.getTypeIdToEditableCalculationSkillLevelMap().values()) {
            final SkillLevelListBox skillLevelListBox = editableCalculationSkillLevel.getSkillLevelListBox();
            final SkillBookImage skillBookImage = editableCalculationSkillLevel.getSkillBookImage();
            final SkillLevelImage skillLevelImage = editableCalculationSkillLevel.getSkillLevelImage();
            staticHandlerRegistrations.add(skillLevelListBox.addChangeHandler(new ChangeHandler() {
                @Override
                public void onChange(ChangeEvent event) {
                    skillBookImage.setLevel(skillLevelListBox.getSelectedLevel());
                    skillLevelImage.setLevel(skillLevelListBox.getSelectedLevel());
                    display.recalculate();
                }
            }));
        }
        bindPricesSectionDynamicHandlers();
    }

    private void bindApplyButton(final EditableBlueprintInformation editableBlueprintInformation) {
        final TextBox meTextBox = editableBlueprintInformation.getMeTextBox();
        final TextBox peTextBox = editableBlueprintInformation.getPeTextBox();
        final TextBox quantityTextBox = editableBlueprintInformation.getQuantityTextBox();
        dynamicHandlerRegistrations.add(editableBlueprintInformation.getApplyButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                display.changeBlueprintMePeQuantity(Integer.valueOf(meTextBox.getText()), Integer.valueOf(peTextBox.getText()), Long.valueOf(quantityTextBox.getText()));
            }
        }));
        dynamicHandlerRegistrations.add(meTextBox.addChangeHandler(new OnlyDigitsAndMinusChangeHandler(meTextBox, 3)));
        dynamicHandlerRegistrations.add(peTextBox.addChangeHandler(new OnlyDigitsAndMinusChangeHandler(peTextBox, 3)));
        dynamicHandlerRegistrations.add(quantityTextBox.addChangeHandler(new OnlyDigitsChangeHandler(quantityTextBox, 9)));
    }

    private void bindApplyButton(final EditableCalculationItem editableCalculationItem, final ComputableCalculationItem computableCalculationItem) {
        final TextBox meTextBox = editableCalculationItem.getMeTextBox();
        final TextBox peTextBox = editableCalculationItem.getPeTextBox();
        dynamicHandlerRegistrations.add(editableCalculationItem.getApplyButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                editableCalculationItem.getMeLabel().setText(meTextBox.getText());
                editableCalculationItem.getPeLabel().setText(peTextBox.getText());
                display.changeCalculationItemMePe(computableCalculationItem.getCalculationItemTreeNodeSummary().getPathNodes(), Integer.valueOf(meTextBox.getText()), Integer.valueOf(peTextBox.getText()));
            }
        }));
        dynamicHandlerRegistrations.add(meTextBox.addChangeHandler(new OnlyDigitsAndMinusChangeHandler(meTextBox, 3)));
        dynamicHandlerRegistrations.add(peTextBox.addChangeHandler(new OnlyDigitsAndMinusChangeHandler(peTextBox, 3)));
    }

    private void bindUseAllBlueprintsImage(final OpaqueLoadableBlueprintImage useAllBlueprintsImage) {
        dynamicHandlerRegistrations.add(useAllBlueprintsImage.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (useAllBlueprintsImage.hasOpacity()) {
                    Map<Long[], String> pathNodesToBlueprintNameMap = new HashMap<Long[], String>();
                    for (Map.Entry<String, ComputableCalculationItem> mapEntry : calculationItemTreeSectionDisplay.getPathNodesStringToComputableCalculationItemMap().entrySet()) {
                        String pathNodesString = mapEntry.getKey();
                        EditableCalculationItem editableCalculationItem = calculationItemTreeSectionDisplay.getPathNodesStringToEditableCalculationItemMap().get(pathNodesString);
                        OpaqueLoadableBlueprintImage blueprintImage = editableCalculationItem.getBlueprintImage();
                        if (blueprintImage != null && !calculationItemTreeSectionDisplay.getPathNodeStringsWithUsedBlueprint().contains(pathNodesString)) {
                            CalculationItemTreeNodeSummary calculationItemTreeNodeSummary = mapEntry.getValue().getCalculationItemTreeNodeSummary();
                            pathNodesToBlueprintNameMap.put(calculationItemTreeNodeSummary.getPathNodes(), calculationItemTreeNodeSummary.getItemTypeName() + " Blueprint");
                            blueprintImage.removeOpacity();
                            blueprintImage.startLoading();
                        }
                    }
                    if (pathNodesToBlueprintNameMap.isEmpty()) {
                        reuseAllBlueprints();
                    } else {
                        useAllBlueprintsFirstTime(pathNodesToBlueprintNameMap);
                    }
                } else {
                    stopUsingAllBlueprints();
                }
            }

            private void useAllBlueprintsFirstTime(Map<Long[], String> pathNodesToBlueprintNameMap) {
                QuickCalculatorUseAllBlueprintsAction action = new QuickCalculatorUseAllBlueprintsAction();
                useAllBlueprintsImage.removeOpacity();
                useAllBlueprintsImage.startLoading();
                action.setPathNodesToBlueprintNameMap(pathNodesToBlueprintNameMap);
                rpcService.execute(action, new QuickCalculatorTabActionCallback<QuickCalculatorUseAllBlueprintsActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(QuickCalculatorUseAllBlueprintsActionResponse response) {
                        useAllBlueprintsImage.stopLoading();
                        eventBus.fireEvent(new QuickCalculatorUsedAllBlueprintsEvent(trackingManager, constants, response, getExecutionDuration()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        useAllBlueprintsImage.stopLoading();
                        super.onFailure(throwable);
                    }
                });
            }

            private void stopUsingAllBlueprints() {
                useAllBlueprintsImage.setOpacity();
                eventBus.fireEvent(new QuickCalculatorStoppedUsingAllBlueprintsEvent(trackingManager, constants));
            }

            private void reuseAllBlueprints() {
                useAllBlueprintsImage.removeOpacity();
                eventBus.fireEvent(new QuickCalculatorReusedAllBlueprintsEvent(trackingManager, constants));
            }
        }));
    }

    private void bindUseBlueprintImage(final CalculationItemTreeNodeSummary calculationItemTreeNodeSummary, final OpaqueLoadableBlueprintImage useBlueprintImage) {
        dynamicHandlerRegistrations.add(useBlueprintImage.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (useBlueprintImage.hasOpacity()) {
                    String pathNodesString = calculationItemTreeNodeSummary.getPathNodesString();
                    if (calculationItemTreeSectionDisplay.getPathNodeStringsWithUsedBlueprint().contains(pathNodesString)) {
                        reuseBlueprint();
                    } else {
                        useBlueprintFirstTime();
                    }
                } else {
                    stopUsingBlueprint();
                }
            }

            private void useBlueprintFirstTime() {
                QuickCalculatorUseBlueprintAction action = new QuickCalculatorUseBlueprintAction();
                action.setPathNodes(calculationItemTreeNodeSummary.getPathNodes());
                action.setBlueprintName(calculationItemTreeNodeSummary.getItemTypeName() + " Blueprint");
                useBlueprintImage.removeOpacity();
                useBlueprintImage.startLoading();
                rpcService.execute(action, new QuickCalculatorTabActionCallback<QuickCalculatorUseBlueprintActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(QuickCalculatorUseBlueprintActionResponse response) {
                        useBlueprintImage.stopLoading();
                        eventBus.fireEvent(new QuickCalculatorUsedBlueprintEvent(trackingManager, constants, response, getExecutionDuration()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        useBlueprintImage.stopLoading();
                        super.onFailure(throwable);
                    }
                });
            }

            private void stopUsingBlueprint() {
                useBlueprintImage.setOpacity();
                eventBus.fireEvent(new QuickCalculatorStoppedUsingBlueprintEvent(trackingManager, constants, calculationItemTreeNodeSummary.getPathNodes(), calculationItemTreeNodeSummary.getPathNodesString()));
            }

            private void reuseBlueprint() {
                useBlueprintImage.removeOpacity();
                eventBus.fireEvent(new QuickCalculatorReusedBlueprintEvent(trackingManager, constants, calculationItemTreeNodeSummary.getPathNodes(), calculationItemTreeNodeSummary.getPathNodesString()));
            }
        }));
    }

    private void bindCopyPriceTextBox(EditableBlueprintItem editableBlueprintItem) {
        final PriceTextBox copyPriceTextBox = editableBlueprintItem.getCopyPriceTextBox();
        dynamicHandlerRegistrations.add(copyPriceTextBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                copyPriceTextBox.setPrice(copyPriceTextBox.getText());
                display.updatePrices();
            }
        }));
    }

    private void bindInventionAnchor(final BlueprintItemDto blueprintItem, final EditableBlueprintItem editableBlueprintItem) {
        final BlueprintUseButton blueprintUseButton = editableBlueprintItem.getBlueprintUseButton();
        dynamicHandlerRegistrations.add(editableBlueprintItem.getInventAnchor().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (editableBlueprintItem.getInventionBlueprintItemTable().getRowCount() == 0) {
                    blueprintUseButton.setEnabled(false);
                    QuickCalculatorInventBlueprintAction action = new QuickCalculatorInventBlueprintAction();
                    action.setPathNodes(blueprintItem.getPathExpression().getPathNodes());
                    action.setBlueprintName(blueprintItem.getParentBlueprintTypeName());
                    rpcService.execute(action, new QuickCalculatorTabActionCallback<QuickCalculatorInventBlueprintActionResponse>(eventBus, trackingManager, constants) {
                        @Override
                        public void onSuccess(QuickCalculatorInventBlueprintActionResponse response) {
                            blueprintUseButton.setEnabled(true);
                            eventBus.fireEvent(new lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorInventedBlueprintEvent(trackingManager, constants, response, getExecutionDuration()));
                        }
                    });
                } else {
                    blueprintItemTreeSectionDisplay.showBlueprintInventionItems(editableBlueprintItem);
                }
            }
        }));
    }

    private void bindUseOriginalAnchor(final BlueprintItemDto blueprintItem, final EditableBlueprintItem editableBlueprintItem) {
        dynamicHandlerRegistrations.add(editableBlueprintItem.getUseOriginalAnchor().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (editableBlueprintItem.getInventionBlueprintItemTable() != null) {
                    blueprintItemTreeSectionDisplay.hideBlueprintInventionItems(editableBlueprintItem);
                }
                blueprintItem.setBlueprintUse("ORIGINAL"); // TODO remove string constant usage
                display.recalculate();
            }
        }));
    }

    private void bindUseCopyAnchor(final BlueprintItemDto blueprintItem, final EditableBlueprintItem editableBlueprintItem) {
        dynamicHandlerRegistrations.add(editableBlueprintItem.getUseCopyAnchor().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (editableBlueprintItem.getInventionBlueprintItemTable() != null) {
                    blueprintItemTreeSectionDisplay.hideBlueprintInventionItems(editableBlueprintItem);
                }
                blueprintItem.setBlueprintUse("COPY"); // TODO remove string constant usage
                display.recalculate();
            }
        }));
    }

    private void bindUseSchematicImage(final CalculationItemTreeNodeSummary calculationItemTreeNodeSummary, final OpaqueLoadableSchematicImage useSchematicImage) {
        dynamicHandlerRegistrations.add(useSchematicImage.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (useSchematicImage.hasOpacity()) {
                    String pathNodesString = calculationItemTreeNodeSummary.getPathNodesString();
                    if (calculationItemTreeSectionDisplay.getPathNodeStringsWithUsedSchematic().contains(pathNodesString)) {
                        reuseSchematic();
                    } else {
                        useSchematicFirstTime();
                    }
                } else {
                    stopUsingSchematic();
                }
            }

            private void useSchematicFirstTime() {
                QuickCalculatorUseSchematicAction action = new QuickCalculatorUseSchematicAction();
                action.setPathNodes(calculationItemTreeNodeSummary.getPathNodes());
                action.setSchematicName(calculationItemTreeNodeSummary.getItemTypeName());
                useSchematicImage.removeOpacity();
                useSchematicImage.startLoading();
                rpcService.execute(action, new QuickCalculatorTabActionCallback<QuickCalculatorUseSchematicActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(QuickCalculatorUseSchematicActionResponse response) {
                        useSchematicImage.stopLoading();
                        eventBus.fireEvent(new QuickCalculatorUsedSchematicEvent(trackingManager, constants, response, getExecutionDuration()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        useSchematicImage.stopLoading();
                        super.onFailure(throwable);
                    }
                });
            }

            private void stopUsingSchematic() {
                useSchematicImage.setOpacity();
                eventBus.fireEvent(new QuickCalculatorStoppedUsingSchematicEvent(trackingManager, constants, calculationItemTreeNodeSummary.getPathNodes(), calculationItemTreeNodeSummary.getPathNodesString()));
            }

            private void reuseSchematic() {
                useSchematicImage.removeOpacity();
                eventBus.fireEvent(new QuickCalculatorReusedSchematicEvent(trackingManager, constants, calculationItemTreeNodeSummary.getPathNodes(), calculationItemTreeNodeSummary.getPathNodesString()));
            }
        }));
    }

    private void bindPricesSectionDynamicHandlers() {
        for (EditableCalculationPriceSetItem editableCalculationPriceSetItem : pricesSectionDisplay.getTypeIdToEditableCalculationPriceSetItemMap().values()) {
            final PriceTextBox priceTextBox = editableCalculationPriceSetItem.getPriceTextBox();
            pricesSectionDynamicHandlerRegistrations.add(priceTextBox.addChangeHandler(new ChangeHandler() {
                @Override
                public void onChange(ChangeEvent event) {
                    priceTextBox.setPrice(priceTextBox.getText());
                    display.updatePrices();
                }
            }));
            pricesSectionDynamicHandlerRegistrations.addAll(priceTextBox.getHandlerRegistrations());
        }
    }

    private void unbindPricesSectionDynamicHandlers() {
        for (HandlerRegistration handlerRegistration : pricesSectionDynamicHandlerRegistrations) {
            handlerRegistration.removeHandler();
        }
        pricesSectionDynamicHandlerRegistrations.clear();
    }

    private void rebindPricesSectionDynamicHandlers() {
        unbindPricesSectionDynamicHandlers();
        bindPricesSectionDynamicHandlers();
    }

    private void unbindDynamic() {
        for (HandlerRegistration handlerRegistration : dynamicHandlerRegistrations) {
            handlerRegistration.removeHandler();
        }
        dynamicHandlerRegistrations.clear();

        List<HandlerRegistration> calculationItemTreeSectionRegistrationHandlers = calculationItemTreeSectionDisplay.getCalculationItemTreeSectionRegistrationHandlers();
        for (HandlerRegistration handlerRegistration : calculationItemTreeSectionRegistrationHandlers) {
            handlerRegistration.removeHandler();
        }
        calculationItemTreeSectionRegistrationHandlers.clear();
        unbindPricesSectionDynamicHandlers();
    }

    private void showSpinner() {
        display.getErrorContainer().setVisible(false);
        display.getErrorMessageLabel().setText("");
        display.getSpinnerImage().setVisible(true);
    }

    private void hideSpinner() {
        display.getSpinnerImage().setVisible(false);
    }

    private void showPricesSectionSpinner() {
        display.getErrorContainer().setVisible(false);
        display.getErrorMessageLabel().setText("");
        pricesSectionDisplay.getPricesSectionSpinnerImage().setVisible(true);
    }

    private void hidePricesSectionSpinner() {
        pricesSectionDisplay.getPricesSectionSpinnerImage().setVisible(false);
    }

    private void enableButtons() {
        blueprintInformationSectionDisplay.getSetButton().setEnabled(true);
        blueprintInformationSectionDisplay.getBlueprintSuggestBox().getTextBox().setEnabled(true);
    }
}
