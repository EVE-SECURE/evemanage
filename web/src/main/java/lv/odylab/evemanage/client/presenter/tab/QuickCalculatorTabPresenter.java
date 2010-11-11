package lv.odylab.evemanage.client.presenter.tab;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
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
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorFetchedPricesFromEveCentralEvent;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorFetchedPricesFromEveCentralEventHandler;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorFetchedPricesFromEveMetricsEvent;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorFetchedPricesFromEveMetricsEventHandler;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorReusedAllBlueprintsEvent;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorReusedAllBlueprintsEventHandler;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorReusedBlueprintEvent;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorReusedBlueprintEventHandler;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorSetEvent;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorSetEventHandler;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorStoppedUsingAllBlueprintsEvent;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorStoppedUsingAllBlueprintsEventHandler;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorStoppedUsingBlueprintEvent;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorStoppedUsingBlueprintEventHandler;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorTabFirstLoadEvent;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorTabFirstLoadEventHandler;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorUsedAllBlueprintsEvent;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorUsedAllBlueprintsEventHandler;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorUsedBlueprintEvent;
import lv.odylab.evemanage.client.event.quickcalculator.QuickCalculatorUsedBlueprintEventHandler;
import lv.odylab.evemanage.client.presenter.AttachableDisplay;
import lv.odylab.evemanage.client.presenter.Presenter;
import lv.odylab.evemanage.client.presenter.tab.calculator.CalculationTreeNodeSummary;
import lv.odylab.evemanage.client.presenter.tab.calculator.ComputableCalculationItem;
import lv.odylab.evemanage.client.presenter.tab.calculator.EditableCalculation;
import lv.odylab.evemanage.client.presenter.tab.calculator.EditableCalculationItem;
import lv.odylab.evemanage.client.presenter.tab.calculator.EditableCalculationPriceSetItem;
import lv.odylab.evemanage.client.rpc.EveManageRemoteServiceAsync;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorFetchPricesFromEveCentralAction;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorFetchPricesFromEveCentralActionResponse;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorFetchPricesFromEveMetricsAction;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorFetchPricesFromEveMetricsActionResponse;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorSetAction;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorSetActionResponse;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorTabFirstLoadAction;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorTabFirstLoadActionResponse;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorUseAllBlueprintsAction;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorUseAllBlueprintsActionResponse;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorUseBlueprintAction;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorUseBlueprintActionResponse;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;
import lv.odylab.evemanage.client.widget.OnlyDigitsAndMinusChangeHandler;
import lv.odylab.evemanage.client.widget.OnlyDigitsChangeHandler;
import lv.odylab.evemanage.client.widget.OpaqueLoadableBlueprintImage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuickCalculatorTabPresenter implements Presenter, QuickCalculatorTabErrorEventHandler, QuickCalculatorTabFirstLoadEventHandler, QuickCalculatorSetEventHandler, QuickCalculatorUsedBlueprintEventHandler, QuickCalculatorReusedBlueprintEventHandler, QuickCalculatorStoppedUsingBlueprintEventHandler, QuickCalculatorUsedAllBlueprintsEventHandler, QuickCalculatorStoppedUsingAllBlueprintsEventHandler, QuickCalculatorReusedAllBlueprintsEventHandler, QuickCalculatorFetchedPricesFromEveCentralEventHandler, QuickCalculatorFetchedPricesFromEveMetricsEventHandler {

    public interface Display extends AttachableDisplay {

        Widget getSpinnerImage();

        Widget getErrorContainer();

        HasText getErrorMessageLabel();

        SuggestBox getBlueprintSuggestBox();

        Button getSetButton();

        Button getApplyButton();

        Button getFetchEveCentralPricesButton();

        Button getFetchEveMetricsPricesButton();

        void showBlueprintDetails(EditableCalculationItem editableCalculationItem);

        void hideBlueprintDetails(EditableCalculationItem editableCalculationItem);

        void hideDetailsTable(EditableCalculationItem editableCalculationItem);

        EditableCalculation getEditableCalculation();

        Map<String, EditableCalculationItem> getPathNodesStringToEditableCalculationItemMap();

        Map<String, ComputableCalculationItem> getPathNodesStringToComputableCalculationItemMap();

        Map<String, CalculationDto> getPathNodesStringToUsedCalculationMap();

        Map<Long, EditableCalculationPriceSetItem> getTypeIdToEditableCalculationPriceSetItemMap();

        void setNewCalculation(CalculationDto calculation);

        List<String> addCalculationTreeNode(Long[] pathNodes, CalculationDto calculation);

        List<String> addCalculationTreeNodes(Map<Long[], CalculationDto> pathNodesToCalculationMap);

        void excludeCalculationTreeNodeFromCalculation(Long[] pathNodes);

        void excludeCalculationTreeNodesFromCalculation(List<Long[]> pathNodesList);

        void includeCalculationTreeNodeInCalculation(Long[] pathNodes);

        void includeCalculationTreeNodesInCalculation(List<Long[]> pathNodesList);

        void changeMePeQuantity(Integer meLevel, Integer peLevel, Long quantity);

        void changeMePe(Long[] pathNodes, Integer meLevel, Integer peLevel);

        void updatePrices();

        void updatePrices(Map<Long, BigDecimal> typeIdToPriceMap);

        List<HandlerRegistration> getHandlerRegistrations();
    }

    private EventBus eventBus;
    private TrackingManager trackingManager;
    private EveManageRemoteServiceAsync rpcService;
    private EveManageConstants constants;
    private EveManageErrorConstants errorConstants;
    private Display display;

    private HasWidgets displayContainer;
    private List<HandlerRegistration> staticHandlerRegistrations;
    private List<HandlerRegistration> dynamicHandlerRegistrations;

    @Inject
    public QuickCalculatorTabPresenter(EventBus eventBus, TrackingManager trackingManager, EveManageRemoteServiceAsync rpcService, EveManageConstants constants, EveManageErrorConstants errorConstants, Display display) {
        this.eventBus = eventBus;
        this.trackingManager = trackingManager;
        this.rpcService = rpcService;
        this.constants = constants;
        this.errorConstants = errorConstants;
        this.display = display;

        this.staticHandlerRegistrations = new ArrayList<HandlerRegistration>();
        this.dynamicHandlerRegistrations = new ArrayList<HandlerRegistration>();

        eventBus.addHandler(QuickCalculatorTabErrorEvent.TYPE, this);
        eventBus.addHandler(QuickCalculatorTabFirstLoadEvent.TYPE, this);
        eventBus.addHandler(QuickCalculatorSetEvent.TYPE, this);
        eventBus.addHandler(QuickCalculatorUsedBlueprintEvent.TYPE, this);
        eventBus.addHandler(QuickCalculatorReusedBlueprintEvent.TYPE, this);
        eventBus.addHandler(QuickCalculatorStoppedUsingBlueprintEvent.TYPE, this);
        eventBus.addHandler(QuickCalculatorUsedAllBlueprintsEvent.TYPE, this);
        eventBus.addHandler(QuickCalculatorStoppedUsingAllBlueprintsEvent.TYPE, this);
        eventBus.addHandler(QuickCalculatorReusedAllBlueprintsEvent.TYPE, this);
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
    }

    @Override
    public void onQuickCalculatorTabFirstLoad(QuickCalculatorTabFirstLoadEvent event) {
        hideSpinner();
        enableButtons();
    }

    @Override
    public void onQuickCalculatorSet(QuickCalculatorSetEvent event) {
        unbindDynamic();
        display.setNewCalculation(event.getCalculation());
        display.getApplyButton().setEnabled(true);
        display.getFetchEveCentralPricesButton().setEnabled(true);
        display.getFetchEveMetricsPricesButton().setEnabled(true);
        bindDynamic();
        hideSpinner();
    }

    @Override
    public void onUsedBlueprint(QuickCalculatorUsedBlueprintEvent event) {
        List<String> pathNodeStringsWithBlueprint = display.addCalculationTreeNode(event.getPathNodes(), event.getCalculation());
        for (String pathNodesString : pathNodeStringsWithBlueprint) {
            EditableCalculationItem editableCalculationItem = display.getPathNodesStringToEditableCalculationItemMap().get(pathNodesString);
            ComputableCalculationItem computableCalculationItem = display.getPathNodesStringToComputableCalculationItemMap().get(pathNodesString);
            bindUseBlueprintImage(computableCalculationItem.getCalculationTreeNodeSummary(), editableCalculationItem.getBlueprintImage());
            bindApplyButton(editableCalculationItem, computableCalculationItem);
        }
    }

    @Override
    public void onStoppedUsingBlueprint(QuickCalculatorStoppedUsingBlueprintEvent event) {
        EditableCalculationItem editableCalculationItem = display.getPathNodesStringToEditableCalculationItemMap().get(event.getPathNodesString());
        display.hideBlueprintDetails(editableCalculationItem);
        display.hideDetailsTable(editableCalculationItem);
        display.excludeCalculationTreeNodeFromCalculation(event.getPathNodes());
    }

    @Override
    public void onReusedBlueprint(QuickCalculatorReusedBlueprintEvent event) {
        String pathNodesString = event.getPathNodesString();
        EditableCalculationItem editableCalculationItem = display.getPathNodesStringToEditableCalculationItemMap().get(pathNodesString);
        display.showBlueprintDetails(editableCalculationItem);
        display.includeCalculationTreeNodeInCalculation(event.getPathNodes());
    }

    @Override
    public void onUsedAllBlueprints(QuickCalculatorUsedAllBlueprintsEvent event) {
        for (Map.Entry<String, EditableCalculationItem> mapEntry : display.getPathNodesStringToEditableCalculationItemMap().entrySet()) {
            OpaqueLoadableBlueprintImage blueprintImage = mapEntry.getValue().getBlueprintImage();
            if (blueprintImage != null && !display.getPathNodesStringToUsedCalculationMap().containsKey(mapEntry.getKey())) {
                blueprintImage.stopLoading();
                blueprintImage.removeOpacity();
            }
        }
        List<String> pathNodeStringsWithBlueprint = display.addCalculationTreeNodes(event.getPathNodesToCalculationMap());
        for (String pathNodesString : pathNodeStringsWithBlueprint) {
            EditableCalculationItem editableCalculationItem = display.getPathNodesStringToEditableCalculationItemMap().get(pathNodesString);
            ComputableCalculationItem computableCalculationItem = display.getPathNodesStringToComputableCalculationItemMap().get(pathNodesString);
            bindUseBlueprintImage(computableCalculationItem.getCalculationTreeNodeSummary(), editableCalculationItem.getBlueprintImage());
            bindApplyButton(editableCalculationItem, computableCalculationItem);
        }
    }

    @Override
    public void onStoppedUsingAllBlueprints(QuickCalculatorStoppedUsingAllBlueprintsEvent event) {
        List<Long[]> pathNodesList = new ArrayList<Long[]>();
        for (Map.Entry<String, EditableCalculationItem> mapEntry : display.getPathNodesStringToEditableCalculationItemMap().entrySet()) {
            EditableCalculationItem editableCalculationItem = mapEntry.getValue();
            ComputableCalculationItem computableCalculationItem = display.getPathNodesStringToComputableCalculationItemMap().get(mapEntry.getKey());
            OpaqueLoadableBlueprintImage blueprintImage = editableCalculationItem.getBlueprintImage();
            if (blueprintImage != null && !blueprintImage.hasOpacity()) {
                blueprintImage.setOpacity();
                display.hideBlueprintDetails(editableCalculationItem);
                display.hideDetailsTable(editableCalculationItem);
                pathNodesList.add(computableCalculationItem.getCalculationTreeNodeSummary().getPathNodes());
            }
        }
        display.excludeCalculationTreeNodesFromCalculation(pathNodesList);
    }

    @Override
    public void onReusedAllBlueprints(QuickCalculatorReusedAllBlueprintsEvent event) {
        List<Long[]> pathNodesList = new ArrayList<Long[]>();
        for (Map.Entry<String, EditableCalculationItem> mapEntry : display.getPathNodesStringToEditableCalculationItemMap().entrySet()) {
            EditableCalculationItem editableCalculationItem = mapEntry.getValue();
            ComputableCalculationItem computableCalculationItem = display.getPathNodesStringToComputableCalculationItemMap().get(mapEntry.getKey());
            OpaqueLoadableBlueprintImage blueprintImage = editableCalculationItem.getBlueprintImage();
            if (blueprintImage != null && blueprintImage.hasOpacity()) {
                blueprintImage.removeOpacity();
                display.showBlueprintDetails(editableCalculationItem);
                pathNodesList.add(computableCalculationItem.getCalculationTreeNodeSummary().getPathNodes());
            }
        }
        display.includeCalculationTreeNodesInCalculation(pathNodesList);
    }

    @Override
    public void onFetchedPricesFromEveCentral(QuickCalculatorFetchedPricesFromEveCentralEvent event) {
        display.updatePrices(event.getTypeIdToPriceMap());
        hideSpinner();
    }

    @Override
    public void onFetchedPricesFromEveMetrics(QuickCalculatorFetchedPricesFromEveMetricsEvent event) {
        display.updatePrices(event.getTypeIdToPriceMap());
        hideSpinner();
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
        final Button setButton = display.getSetButton();
        staticHandlerRegistrations.add(setButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                setButton.setEnabled(false);
                QuickCalculatorSetAction action = new QuickCalculatorSetAction();
                action.setBlueprintName(display.getBlueprintSuggestBox().getText());
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
        staticHandlerRegistrations.add(display.getApplyButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                display.updatePrices();
            }
        }));
        final Button fetchEveCentralPricesButton = display.getFetchEveCentralPricesButton();
        staticHandlerRegistrations.add(fetchEveCentralPricesButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                fetchEveCentralPricesButton.setEnabled(false);
                QuickCalculatorFetchPricesFromEveCentralAction action = new QuickCalculatorFetchPricesFromEveCentralAction();
                action.setTypeIDs(new ArrayList<Long>(display.getTypeIdToEditableCalculationPriceSetItemMap().keySet()));
                showSpinner();
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
        final Button fetchEveMetricsPricesButton = display.getFetchEveMetricsPricesButton();
        staticHandlerRegistrations.add(fetchEveMetricsPricesButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                fetchEveMetricsPricesButton.setEnabled(false);
                QuickCalculatorFetchPricesFromEveMetricsAction action = new QuickCalculatorFetchPricesFromEveMetricsAction();
                action.setTypeIDs(new ArrayList<Long>(display.getTypeIdToEditableCalculationPriceSetItemMap().keySet()));
                showSpinner();
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
    }

    private void bindDynamic() {
        bindApplyButton(display.getEditableCalculation());
        bindUseAllBlueprintsImage(display.getEditableCalculation().getUseAllBlueprintsImage());
        for (Map.Entry<String, EditableCalculationItem> mapEntry : display.getPathNodesStringToEditableCalculationItemMap().entrySet()) {
            String pathNodesString = mapEntry.getKey();
            EditableCalculationItem editableCalculationItem = mapEntry.getValue();
            if (editableCalculationItem.getBlueprintImage() != null) {
                ComputableCalculationItem computableCalculationItem = display.getPathNodesStringToComputableCalculationItemMap().get(pathNodesString);
                bindUseBlueprintImage(computableCalculationItem.getCalculationTreeNodeSummary(), editableCalculationItem.getBlueprintImage());
                bindApplyButton(editableCalculationItem, computableCalculationItem);
            }
        }
        for (EditableCalculationPriceSetItem editableCalculationPriceSetItem : display.getTypeIdToEditableCalculationPriceSetItemMap().values()) {
            dynamicHandlerRegistrations.addAll(editableCalculationPriceSetItem.getPriceTextBox().getHandlerRegistrations());
        }
    }

    private void bindApplyButton(final EditableCalculation editableCalculation) {
        final TextBox meTextBox = editableCalculation.getMeTextBox();
        final TextBox peTextBox = editableCalculation.getPeTextBox();
        final TextBox quantityTextBox = editableCalculation.getQuantityTextBox();
        dynamicHandlerRegistrations.add(editableCalculation.getApplyButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                editableCalculation.getMeLabel().setText(meTextBox.getText());
                editableCalculation.getPeLabel().setText(peTextBox.getText());
                editableCalculation.getQuantityLabel().setText(quantityTextBox.getText());
                display.changeMePeQuantity(Integer.valueOf(meTextBox.getText()), Integer.valueOf(peTextBox.getText()), Long.valueOf(quantityTextBox.getText()));
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
                display.changeMePe(computableCalculationItem.getCalculationTreeNodeSummary().getPathNodes(), Integer.valueOf(meTextBox.getText()), Integer.valueOf(peTextBox.getText()));
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
                    for (Map.Entry<String, ComputableCalculationItem> mapEntry : display.getPathNodesStringToComputableCalculationItemMap().entrySet()) {
                        String pathNodesString = mapEntry.getKey();
                        EditableCalculationItem editableCalculationItem = display.getPathNodesStringToEditableCalculationItemMap().get(pathNodesString);
                        OpaqueLoadableBlueprintImage blueprintImage = editableCalculationItem.getBlueprintImage();
                        if (blueprintImage != null && !display.getPathNodesStringToUsedCalculationMap().containsKey(pathNodesString)) {
                            CalculationTreeNodeSummary calculationTreeNodeSummary = mapEntry.getValue().getCalculationTreeNodeSummary();
                            pathNodesToBlueprintNameMap.put(calculationTreeNodeSummary.getPathNodes(), calculationTreeNodeSummary.getItemTypeName() + " Blueprint");
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

    private void bindUseBlueprintImage(final CalculationTreeNodeSummary calculationTreeNodeSummary, final OpaqueLoadableBlueprintImage useBlueprintImage) {
        dynamicHandlerRegistrations.add(useBlueprintImage.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (useBlueprintImage.hasOpacity()) {
                    String pathNodesString = calculationTreeNodeSummary.getPathNodesString();
                    if (display.getPathNodesStringToUsedCalculationMap().containsKey(pathNodesString)) {
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
                action.setPathNodes(calculationTreeNodeSummary.getPathNodes());
                action.setBlueprintName(calculationTreeNodeSummary.getItemTypeName() + " Blueprint");
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
                eventBus.fireEvent(new QuickCalculatorStoppedUsingBlueprintEvent(trackingManager, constants, calculationTreeNodeSummary.getPathNodes(), calculationTreeNodeSummary.getPathNodesString()));
            }

            private void reuseBlueprint() {
                useBlueprintImage.removeOpacity();
                eventBus.fireEvent(new QuickCalculatorReusedBlueprintEvent(trackingManager, constants, calculationTreeNodeSummary.getPathNodes(), calculationTreeNodeSummary.getPathNodesString()));
            }
        }));
    }

    private void unbindDynamic() {
        for (HandlerRegistration handlerRegistration : dynamicHandlerRegistrations) {
            handlerRegistration.removeHandler();
        }
        dynamicHandlerRegistrations.clear();

        List<HandlerRegistration> displayHandlerRegistrations = display.getHandlerRegistrations();
        for (HandlerRegistration handlerRegistration : displayHandlerRegistrations) {
            handlerRegistration.removeHandler();
        }
        displayHandlerRegistrations.clear();
    }

    private void showSpinner() {
        display.getErrorContainer().setVisible(false);
        display.getErrorMessageLabel().setText("");
        display.getSpinnerImage().setVisible(true);
    }

    private void hideSpinner() {
        display.getSpinnerImage().setVisible(false);
    }

    private void enableButtons() {
        display.getSetButton().setEnabled(true);
        display.getBlueprintSuggestBox().getTextBox().setEnabled(true);
    }
}
