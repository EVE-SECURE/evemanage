package lv.odylab.evemanage.client.presenter.tab;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageErrorConstants;
import lv.odylab.evemanage.client.event.PriceSetTabActionCallback;
import lv.odylab.evemanage.client.event.error.PriceSetTabErrorEvent;
import lv.odylab.evemanage.client.event.error.PriceSetTabErrorEventHandler;
import lv.odylab.evemanage.client.event.preferences.PreferencesCharacterAddedEvent;
import lv.odylab.evemanage.client.event.preferences.PreferencesCharacterAddedEventHandler;
import lv.odylab.evemanage.client.event.preferences.PreferencesCharacterDeletedEvent;
import lv.odylab.evemanage.client.event.preferences.PreferencesCharacterDeletedEventHandler;
import lv.odylab.evemanage.client.event.preferences.PreferencesMainCharacterSetEvent;
import lv.odylab.evemanage.client.event.preferences.PreferencesMainCharacterSetEventHandler;
import lv.odylab.evemanage.client.event.priceset.PriceSetCopiedEvent;
import lv.odylab.evemanage.client.event.priceset.PriceSetCopiedEventHandler;
import lv.odylab.evemanage.client.event.priceset.PriceSetCreatedEvent;
import lv.odylab.evemanage.client.event.priceset.PriceSetCreatedEventHandler;
import lv.odylab.evemanage.client.event.priceset.PriceSetDeletedEvent;
import lv.odylab.evemanage.client.event.priceset.PriceSetDeletedEventHandler;
import lv.odylab.evemanage.client.event.priceset.PriceSetFetchedPricesFromEveCentralEvent;
import lv.odylab.evemanage.client.event.priceset.PriceSetFetchedPricesFromEveCentralEventHandler;
import lv.odylab.evemanage.client.event.priceset.PriceSetFetchedPricesFromEveMetricsEvent;
import lv.odylab.evemanage.client.event.priceset.PriceSetFetchedPricesFromEveMetricsEventHandler;
import lv.odylab.evemanage.client.event.priceset.PriceSetItemAddedEvent;
import lv.odylab.evemanage.client.event.priceset.PriceSetItemAddedEventHandler;
import lv.odylab.evemanage.client.event.priceset.PriceSetLoadedEvent;
import lv.odylab.evemanage.client.event.priceset.PriceSetLoadedEventHandler;
import lv.odylab.evemanage.client.event.priceset.PriceSetLoadedForAllianceEvent;
import lv.odylab.evemanage.client.event.priceset.PriceSetLoadedForAllianceEventHandler;
import lv.odylab.evemanage.client.event.priceset.PriceSetLoadedForCorporationEvent;
import lv.odylab.evemanage.client.event.priceset.PriceSetLoadedForCorporationEventHandler;
import lv.odylab.evemanage.client.event.priceset.PriceSetLoadedNamesEvent;
import lv.odylab.evemanage.client.event.priceset.PriceSetLoadedNamesEventHandler;
import lv.odylab.evemanage.client.event.priceset.PriceSetLoadedNamesForAllianceEvent;
import lv.odylab.evemanage.client.event.priceset.PriceSetLoadedNamesForAllianceEventHandler;
import lv.odylab.evemanage.client.event.priceset.PriceSetLoadedNamesForCorporationEvent;
import lv.odylab.evemanage.client.event.priceset.PriceSetLoadedNamesForCorporationEventHandler;
import lv.odylab.evemanage.client.event.priceset.PriceSetManyItemsAddedEvent;
import lv.odylab.evemanage.client.event.priceset.PriceSetManyItemsAddedEventHandler;
import lv.odylab.evemanage.client.event.priceset.PriceSetRenamedEvent;
import lv.odylab.evemanage.client.event.priceset.PriceSetRenamedEventHandler;
import lv.odylab.evemanage.client.event.priceset.PriceSetSavedEvent;
import lv.odylab.evemanage.client.event.priceset.PriceSetSavedEventHandler;
import lv.odylab.evemanage.client.event.priceset.PriceSetTabFirstLoadEvent;
import lv.odylab.evemanage.client.event.priceset.PriceSetTabFirstLoadEventHandler;
import lv.odylab.evemanage.client.presenter.AttachableDisplay;
import lv.odylab.evemanage.client.presenter.Presenter;
import lv.odylab.evemanage.client.rpc.ErrorCode;
import lv.odylab.evemanage.client.rpc.EveManageRemoteServiceAsync;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceFetchFromEveCentralAction;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceFetchFromEveCentralActionResponse;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceFetchFromEveMetricsAction;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceFetchFromEveMetricsActionResponse;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetAddItemAction;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetAddItemActionResponse;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetCopyAction;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetCopyActionResponse;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetCreateAction;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetCreateActionResponse;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetDeleteAction;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetDeleteActionResponse;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetLoadAction;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetLoadActionResponse;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetLoadAllianceNamesAction;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetLoadAllianceNamesActionResponse;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetLoadCorporationNamesAction;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetLoadCorporationNamesActionResponse;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetLoadForAllianceAction;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetLoadForAllianceActionResponse;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetLoadForCorporationAction;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetLoadForCorporationActionResponse;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetRenameAction;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetRenameActionResponse;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetSaveAction;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetSaveActionResponse;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetTabFirstLoadAction;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetTabFirstLoadActionResponse;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetItemDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;
import lv.odylab.evemanage.client.widget.AttachedCharacterListBox;
import lv.odylab.evemanage.client.widget.PriceSetListBox;
import lv.odylab.evemanage.client.widget.PriceTextBox;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PriceSetTabPresenter implements Presenter, PriceSetTabErrorEventHandler, PriceSetTabFirstLoadEventHandler, PriceSetLoadedEventHandler, PriceSetLoadedNamesEventHandler, PriceSetCreatedEventHandler, PriceSetCopiedEventHandler, PriceSetRenamedEventHandler, PriceSetDeletedEventHandler, PriceSetSavedEventHandler, PriceSetFetchedPricesFromEveCentralEventHandler, PriceSetFetchedPricesFromEveMetricsEventHandler, PriceSetItemAddedEventHandler, PriceSetManyItemsAddedEventHandler, PreferencesCharacterAddedEventHandler, PreferencesMainCharacterSetEventHandler, PreferencesCharacterDeletedEventHandler, PriceSetLoadedNamesForCorporationEventHandler, PriceSetLoadedNamesForAllianceEventHandler, PriceSetLoadedForCorporationEventHandler, PriceSetLoadedForAllianceEventHandler {

    public interface Display extends AttachableDisplay {

        Widget getSpinnerImage();

        Widget getErrorContainer();

        HasText getErrorMessageLabel();

        PriceSetListBox getPriceSetNamesListBox();

        void setCurrentPriceSetNameIndex(Integer index);

        PriceSetDto getCurrentPriceSet();

        void setCurrentPriceSet(PriceSetDto priceSet);

        void clearPriceSetItemsTable();

        AttachedCharacterListBox getAttachedCharacterNameListBox();

        ListBox getSharingLevelListBox();

        void setSharingLevels(List<String> sharingLevels);

        void addPriceSetItem(PriceSetItemDto priceSetItem);

        Boolean priceSetItemExists(PriceSetItemDto priceSetItem);

        Map<PriceSetItemDto, PriceTextBox> getCurrentPriceSetItemValues();

        Map<PriceSetItemDto, Button> getCurrentPriceSetItemDeleteButtons();

        List<PriceSetItemDto> getCurrentPriceSetDeletedItems();

        PriceSetListBox getPriceSetListBox();

        TextBox getNewPriceSetName();

        Button getSavePriceSetButton();

        Button getFetchEveCentralPricesButton();

        Button getFetchEveMetricsPricesButton();

        Button getCreatePriceSetButton();

        Button getCopyPriceSetButton();

        Button getRenamePriceSetButton();

        Button getDeletePriceSetButton();

        Button getAddBasicMineralsButton();

        Button getAddAdvancedMoonMaterialButton();

        SuggestBox getAddNewItemName();

        Button getAddNewItemButton();

        void clearCorporationPriceSetItemsTable();

        Button getReloadCorporationPriceSetNames();

        PriceSetListBox getCorporationPriceSetNamesListBox();

        void setCurrentCorporationPriceSet(PriceSetDto priceSet);

        void clearAlliancePriceSetItemsTable();

        PriceSetListBox getAlliancePriceSetNamesListBox();

        void setCurrentAlliancePriceSet(PriceSetDto priceSet);

        Button getReloadAlliancePriceSetNames();

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
    public PriceSetTabPresenter(EventBus eventBus, TrackingManager trackingManager, EveManageRemoteServiceAsync rpcService, EveManageConstants constants, EveManageErrorConstants errorConstants, Display display) {
        this.eventBus = eventBus;
        this.trackingManager = trackingManager;
        this.rpcService = rpcService;
        this.constants = constants;
        this.errorConstants = errorConstants;
        this.display = display;

        this.staticHandlerRegistrations = new ArrayList<HandlerRegistration>();
        this.dynamicHandlerRegistrations = new ArrayList<HandlerRegistration>();

        eventBus.addHandler(PriceSetTabErrorEvent.TYPE, this);
        eventBus.addHandler(PriceSetTabFirstLoadEvent.TYPE, this);
        eventBus.addHandler(PriceSetLoadedEvent.TYPE, this);
        eventBus.addHandler(PriceSetLoadedNamesEvent.TYPE, this);
        eventBus.addHandler(PriceSetCreatedEvent.TYPE, this);
        eventBus.addHandler(PriceSetCopiedEvent.TYPE, this);
        eventBus.addHandler(PriceSetRenamedEvent.TYPE, this);
        eventBus.addHandler(PriceSetDeletedEvent.TYPE, this);
        eventBus.addHandler(PriceSetSavedEvent.TYPE, this);
        eventBus.addHandler(PriceSetFetchedPricesFromEveCentralEvent.TYPE, this);
        eventBus.addHandler(PriceSetFetchedPricesFromEveMetricsEvent.TYPE, this);
        eventBus.addHandler(PriceSetItemAddedEvent.TYPE, this);
        eventBus.addHandler(PriceSetManyItemsAddedEvent.TYPE, this);
        eventBus.addHandler(PreferencesCharacterAddedEvent.TYPE, this);
        eventBus.addHandler(PreferencesCharacterDeletedEvent.TYPE, this);
        eventBus.addHandler(PreferencesMainCharacterSetEvent.TYPE, this);
        eventBus.addHandler(PriceSetLoadedNamesForCorporationEvent.TYPE, this);
        eventBus.addHandler(PriceSetLoadedNamesForAllianceEvent.TYPE, this);
        eventBus.addHandler(PriceSetLoadedForCorporationEvent.TYPE, this);
        eventBus.addHandler(PriceSetLoadedForAllianceEvent.TYPE, this);
    }

    @Override
    public void go(HasWidgets container) {
        if (displayContainer == null) {
            displayContainer = container;
            displayContainer.clear();
            display.attach(displayContainer);
            bindStatic();
            doPriceSetTabFirstLoad();
        }
    }

    @Override
    public void onPriceSetTabError(PriceSetTabErrorEvent event) {
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
    public void onPriceSetTabFirstLoad(PriceSetTabFirstLoadEvent event) {
        if (event.getPriceSet() == null) {
            disableButtons();
        } else {
            enableButtons();
        }
        PriceSetDto priceSet = event.getPriceSet();
        display.getPriceSetNamesListBox().setPriceSetNames(event.getPriceSetNames());
        display.getAttachedCharacterNameListBox().setAttachedCharacterNames(event.getAttachedCharacterNames());
        display.setSharingLevels(event.getSharingLevels());
        display.setCurrentPriceSet(priceSet);
        display.setCurrentPriceSetNameIndex(event.getCurrentPriceSetNameIndex() + 1);
        display.getCorporationPriceSetNamesListBox().setPriceSetNames(event.getCorporationPriceSetNames());
        display.getAlliancePriceSetNamesListBox().setPriceSetNames(event.getAlliancePriceSetNames());
        display.getCorporationPriceSetNamesListBox().setEnabled(true);
        display.getAlliancePriceSetNamesListBox().setEnabled(true);
        display.getReloadCorporationPriceSetNames().setEnabled(true);
        display.getReloadAlliancePriceSetNames().setEnabled(true);
        bindDynamic();
        hideSpinner();
    }

    @Override
    public void onPriceSetLoaded(PriceSetLoadedEvent event) {
        PriceSetDto priceSet = event.getPriceSet();
        if (priceSet != null) {
            enableButtons();
        }
        display.setCurrentPriceSet(priceSet);
        rebindDynamic();
        hideSpinner();
    }

    @Override
    public void onPriceSetLoadedNames(PriceSetLoadedNamesEvent event) {
        display.getPriceSetNamesListBox().setPriceSetNames(event.getPriceSetNames());
        hideSpinner();
    }

    @Override
    public void onPriceSetCreated(PriceSetCreatedEvent event) {
        enableButtons();
        display.getNewPriceSetName().setText("");
        display.getPriceSetNamesListBox().setPriceSetNames(event.getPriceSetNames());
        display.setCurrentPriceSet(event.getPriceSet());
        display.setCurrentPriceSetNameIndex(event.getCurrentPriceSetNameIndex() + 1);
        rebindDynamic();
        hideSpinner();
    }

    @Override
    public void onPriceSetCopied(PriceSetCopiedEvent event) {
        display.getNewPriceSetName().setText("");
        display.getPriceSetNamesListBox().setPriceSetNames(event.getPriceSetNames());
        display.setCurrentPriceSet(event.getPriceSet());
        display.setCurrentPriceSetNameIndex(event.getCurrentPriceSetNameIndex() + 1);
        rebindDynamic();
        hideSpinner();
    }

    @Override
    public void onPriceSetRenamed(PriceSetRenamedEvent event) {
        display.getNewPriceSetName().setText("");
        display.getPriceSetNamesListBox().setPriceSetNames(event.getPriceSetNames());
        display.setCurrentPriceSet(event.getPriceSet());
        display.setCurrentPriceSetNameIndex(event.getCurrentPriceSetNameIndex() + 1);
        rebindDynamic();
        hideSpinner();
    }

    @Override
    public void onPriceSetDeleted(PriceSetDeletedEvent event) {
        if (event.getPriceSet() == null) {
            disableButtons();
        }
        display.getPriceSetNamesListBox().setPriceSetNames(event.getPriceSetNames());
        display.setCurrentPriceSet(event.getPriceSet());
        display.setCurrentPriceSetNameIndex(event.getCurrentPriceSetNameIndex() + 1);
        rebindDynamic();
        hideSpinner();
    }

    @Override
    public void onPriceSetSaved(PriceSetSavedEvent event) {
        display.setCurrentPriceSet(event.getPriceSet());
        rebindDynamic();
        hideSpinner();
    }

    @Override
    public void onFetchedPricesFromEveCentral(PriceSetFetchedPricesFromEveCentralEvent event) {
        PriceSetDto priceSet = display.getCurrentPriceSet();
        priceSet.setItems(event.getPriceSetItems());
        display.setCurrentPriceSet(priceSet);
        rebindDynamic();
        hideSpinner();
    }

    @Override
    public void onFetchedPricesFromEveMetrics(PriceSetFetchedPricesFromEveMetricsEvent event) {
        PriceSetDto priceSet = display.getCurrentPriceSet();
        priceSet.setItems(event.getPriceSetItems());
        display.setCurrentPriceSet(priceSet);
        rebindDynamic();
        hideSpinner();
    }

    @Override
    public void onPriceSetItemAdded(PriceSetItemAddedEvent event) {
        PriceSetItemDto priceSetItem = event.getPriceSetItem();
        if (!display.priceSetItemExists(priceSetItem)) {
            display.getAddNewItemName().setText("");
            display.addPriceSetItem(priceSetItem);
            bindRemoveButton(priceSetItem);
        } else {
            eventBus.fireEvent(new PriceSetTabErrorEvent(trackingManager, constants, ErrorCode.ITEM_MUST_BE_UNIQUE));
        }
        hideSpinner();
    }

    @Override
    public void onPriceSetManyItemsAdded(PriceSetManyItemsAddedEvent event) {
        Set<Long> existingItemTypes = new HashSet<Long>();
        PriceSetDto priceSet = display.getCurrentPriceSet();
        for (PriceSetItemDto priceSetItem : priceSet.getItems()) {
            existingItemTypes.add(priceSetItem.getItemTypeID());
        }
        for (PriceSetItemDto priceSetItem : event.getPriceSetItems()) {
            if (!existingItemTypes.contains(priceSetItem.getItemTypeID())) {
                display.addPriceSetItem(priceSetItem);
                bindRemoveButton(priceSetItem);
            }
        }
    }

    @Override
    public void onCharacterAdded(PreferencesCharacterAddedEvent event) {
        display.getAttachedCharacterNameListBox().setAttachedCharacterNames(event.getCharacterNames());
    }

    @Override
    public void onCharacterDeleted(PreferencesCharacterDeletedEvent event) {
        display.getAttachedCharacterNameListBox().setAttachedCharacterNames(event.getCharacterNames());
    }

    @Override
    public void onMainCharacterSet(PreferencesMainCharacterSetEvent event) {
        final PriceSetListBox corporationPriceSetNamesListBox = display.getCorporationPriceSetNamesListBox();
        final PriceSetListBox alliancePriceSetNamesListBox = display.getAlliancePriceSetNamesListBox();
        corporationPriceSetNamesListBox.setEnabled(false);
        alliancePriceSetNamesListBox.setEnabled(false);
        rpcService.execute(new PriceSetLoadCorporationNamesAction(), new PriceSetTabActionCallback<PriceSetLoadCorporationNamesActionResponse>(eventBus, trackingManager, constants) {
            @Override
            public void onSuccess(PriceSetLoadCorporationNamesActionResponse response) {
                corporationPriceSetNamesListBox.setEnabled(true);
                eventBus.fireEvent(new PriceSetLoadedNamesForCorporationEvent(trackingManager, constants, response, getExecutionDuration()));
            }

            @Override
            public void onFailure(Throwable throwable) {
                corporationPriceSetNamesListBox.setEnabled(true);
                super.onFailure(throwable);
            }
        });

        rpcService.execute(new PriceSetLoadAllianceNamesAction(), new PriceSetTabActionCallback<PriceSetLoadAllianceNamesActionResponse>(eventBus, trackingManager, constants) {
            @Override
            public void onSuccess(PriceSetLoadAllianceNamesActionResponse response) {
                alliancePriceSetNamesListBox.setEnabled(true);
                eventBus.fireEvent(new PriceSetLoadedNamesForAllianceEvent(trackingManager, constants, response, getExecutionDuration()));
            }

            @Override
            public void onFailure(Throwable throwable) {
                alliancePriceSetNamesListBox.setEnabled(true);
                super.onFailure(throwable);
            }
        });
    }

    @Override
    public void onPriceSetLoadedNamesForCorporation(PriceSetLoadedNamesForCorporationEvent event) {
        PriceSetListBox priceSetListBox = display.getCorporationPriceSetNamesListBox();
        priceSetListBox.setPriceSetNames(event.getPriceSetNames());
        if (priceSetListBox.getCurrentPriceSetID() == -1) {
            display.clearCorporationPriceSetItemsTable();
        }
        hideSpinner();
    }

    @Override
    public void onPriceSetLoadedNamesForAlliance(PriceSetLoadedNamesForAllianceEvent event) {
        PriceSetListBox priceSetListBox = display.getAlliancePriceSetNamesListBox();
        priceSetListBox.setPriceSetNames(event.getPriceSetNames());
        if (priceSetListBox.getCurrentPriceSetID() == -1) {
            display.clearAlliancePriceSetItemsTable();
        }
        hideSpinner();
    }

    @Override
    public void onPriceSetLoadedForCorporation(PriceSetLoadedForCorporationEvent event) {
        display.setCurrentCorporationPriceSet(event.getPriceSet());
        hideSpinner();
    }

    @Override
    public void onPriceSetLoadedForAlliance(PriceSetLoadedForAllianceEvent event) {
        display.setCurrentAlliancePriceSet(event.getPriceSet());
        hideSpinner();
    }

    private void doPriceSetTabFirstLoad() {
        showSpinner();
        rpcService.execute(new PriceSetTabFirstLoadAction(), new PriceSetTabActionCallback<PriceSetTabFirstLoadActionResponse>(eventBus, trackingManager, constants) {
            @Override
            public void onSuccess(PriceSetTabFirstLoadActionResponse response) {
                eventBus.fireEvent(new PriceSetTabFirstLoadEvent(trackingManager, constants, response, getExecutionDuration()));
            }
        });
    }

    private void bindStatic() {
        final PriceSetListBox priceSetListBox = display.getPriceSetListBox();
        staticHandlerRegistrations.add(priceSetListBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                Long currentPriceSetID = priceSetListBox.getCurrentPriceSetID();
                if (currentPriceSetID == -1) {
                    display.clearPriceSetItemsTable();
                    disableButtons();
                    display.getPriceSetNamesListBox().setEnabled(true);
                    return;
                }
                priceSetListBox.setEnabled(false);
                PriceSetLoadAction action = new PriceSetLoadAction();
                action.setPriceSetID(currentPriceSetID);
                showSpinner();
                rpcService.execute(action, new PriceSetTabActionCallback<PriceSetLoadActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(PriceSetLoadActionResponse response) {
                        priceSetListBox.setEnabled(true);
                        eventBus.fireEvent(new PriceSetLoadedEvent(trackingManager, constants, response, getExecutionDuration()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        priceSetListBox.setEnabled(true);
                        super.onFailure(throwable);
                    }
                });
            }
        }));
        final Button createPriceSetButton = display.getCreatePriceSetButton();
        staticHandlerRegistrations.add(createPriceSetButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                createPriceSetButton.setEnabled(false);
                String priceSetName = display.getNewPriceSetName().getText();
                PriceSetCreateAction action = new PriceSetCreateAction();
                action.setPriceSetName(priceSetName);
                showSpinner();
                rpcService.execute(action, new PriceSetTabActionCallback<PriceSetCreateActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(PriceSetCreateActionResponse response) {
                        createPriceSetButton.setEnabled(true);
                        eventBus.fireEvent(new PriceSetCreatedEvent(trackingManager, constants, response, getExecutionDuration()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        createPriceSetButton.setEnabled(true);
                        super.onFailure(throwable);
                    }
                });
            }
        }));
        final Button copyPriceSetButton = display.getCopyPriceSetButton();
        staticHandlerRegistrations.add(copyPriceSetButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                copyPriceSetButton.setEnabled(false);
                String priceSetName = display.getNewPriceSetName().getText();
                PriceSetCopyAction action = new PriceSetCopyAction();
                action.setPriceSetID(priceSetListBox.getCurrentPriceSetID());
                action.setPriceSetName(priceSetName);
                showSpinner();
                rpcService.execute(action, new PriceSetTabActionCallback<PriceSetCopyActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(PriceSetCopyActionResponse response) {
                        copyPriceSetButton.setEnabled(true);
                        eventBus.fireEvent(new PriceSetCopiedEvent(trackingManager, constants, response, getExecutionDuration()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        copyPriceSetButton.setEnabled(true);
                        super.onFailure(throwable);
                    }
                });
            }
        }));
        final Button renamePriceSetButton = display.getRenamePriceSetButton();
        staticHandlerRegistrations.add(renamePriceSetButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                renamePriceSetButton.setEnabled(false);
                String priceSetName = display.getNewPriceSetName().getText();
                PriceSetRenameAction action = new PriceSetRenameAction();
                Long priceSetID = priceSetListBox.getCurrentPriceSetID();
                action.setPriceSetID(priceSetID);
                action.setPriceSetName(priceSetName);
                showSpinner();
                rpcService.execute(action, new PriceSetTabActionCallback<PriceSetRenameActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(PriceSetRenameActionResponse response) {
                        renamePriceSetButton.setEnabled(true);
                        eventBus.fireEvent(new PriceSetRenamedEvent(trackingManager, constants, response, getExecutionDuration()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        renamePriceSetButton.setEnabled(true);
                        super.onFailure(throwable);
                    }
                });
            }
        }));
        final Button deletePriceSetButton = display.getDeletePriceSetButton();
        staticHandlerRegistrations.add(deletePriceSetButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                deletePriceSetButton.setEnabled(false);
                PriceSetDeleteAction action = new PriceSetDeleteAction();
                Long priceSetID = priceSetListBox.getCurrentPriceSetID();
                action.setPriceSetID(priceSetID);
                showSpinner();
                rpcService.execute(action, new PriceSetTabActionCallback<PriceSetDeleteActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(PriceSetDeleteActionResponse response) {
                        deletePriceSetButton.setEnabled(true);
                        eventBus.fireEvent(new PriceSetDeletedEvent(trackingManager, constants, response, getExecutionDuration()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        deletePriceSetButton.setEnabled(true);
                        super.onFailure(throwable);
                    }
                });
            }
        }));
        final Button savePriceSetButton = display.getSavePriceSetButton();
        staticHandlerRegistrations.add(savePriceSetButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                savePriceSetButton.setEnabled(false);
                PriceSetSaveAction action = new PriceSetSaveAction();
                PriceSetDto priceSet = display.getCurrentPriceSet();
                List<PriceSetItemDto> priceSetItems = priceSet.getItems();
                for (PriceSetItemDto deletedPriceSetItem : display.getCurrentPriceSetDeletedItems()) {
                    priceSetItems.remove(deletedPriceSetItem);
                }
                for (PriceSetItemDto currentPriceSetItem : priceSetItems) {
                    currentPriceSetItem.setPrice(display.getCurrentPriceSetItemValues().get(currentPriceSetItem).getPrice());
                }
                action.setPriceSet(priceSet);
                showSpinner();
                rpcService.execute(action, new PriceSetTabActionCallback<PriceSetSaveActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(PriceSetSaveActionResponse response) {
                        savePriceSetButton.setEnabled(true);
                        eventBus.fireEvent(new PriceSetSavedEvent(trackingManager, constants, response, getExecutionDuration()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        savePriceSetButton.setEnabled(true);
                        super.onFailure(throwable);
                    }
                });
            }
        }));
        final Button fetchEveCentralPricesButton = display.getFetchEveCentralPricesButton();
        staticHandlerRegistrations.add(fetchEveCentralPricesButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                fetchEveCentralPricesButton.setEnabled(false);
                PriceFetchFromEveCentralAction action = new PriceFetchFromEveCentralAction();
                PriceSetDto priceSet = display.getCurrentPriceSet();
                List<PriceSetItemDto> priceSetItems = priceSet.getItems();
                action.setPriceSetItems(priceSetItems);
                showSpinner();
                rpcService.execute(action, new PriceSetTabActionCallback<PriceFetchFromEveCentralActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(PriceFetchFromEveCentralActionResponse response) {
                        fetchEveCentralPricesButton.setEnabled(true);
                        eventBus.fireEvent(new PriceSetFetchedPricesFromEveCentralEvent(trackingManager, constants, response, getExecutionDuration()));
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
            public void onClick(ClickEvent clickEvent) {
                fetchEveMetricsPricesButton.setEnabled(false);
                PriceFetchFromEveMetricsAction action = new PriceFetchFromEveMetricsAction();
                PriceSetDto priceSet = display.getCurrentPriceSet();
                List<PriceSetItemDto> priceSetItems = priceSet.getItems();
                action.setPriceSetItems(priceSetItems);
                showSpinner();
                rpcService.execute(action, new PriceSetTabActionCallback<PriceFetchFromEveMetricsActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(PriceFetchFromEveMetricsActionResponse response) {
                        fetchEveMetricsPricesButton.setEnabled(true);
                        eventBus.fireEvent(new PriceSetFetchedPricesFromEveMetricsEvent(trackingManager, constants, response, getExecutionDuration()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        fetchEveMetricsPricesButton.setEnabled(true);
                        super.onFailure(throwable);
                    }
                });
            }
        }));
        staticHandlerRegistrations.add(display.getAddBasicMineralsButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                List<PriceSetItemDto> priceSetItems = new ArrayList<PriceSetItemDto>();
                priceSetItems.add(createPriceSetItem(34L, 4L, "06_14", "Tritanium", BigDecimal.ZERO));
                priceSetItems.add(createPriceSetItem(35L, 4L, "06_15", "Pyerite", BigDecimal.ZERO));
                priceSetItems.add(createPriceSetItem(36L, 4L, "06_12", "Mexallon", BigDecimal.ZERO));
                priceSetItems.add(createPriceSetItem(37L, 4L, "06_16", "Isogen", BigDecimal.ZERO));
                priceSetItems.add(createPriceSetItem(38L, 4L, "11_09", "Nocxium", BigDecimal.ZERO));
                priceSetItems.add(createPriceSetItem(39L, 4L, "11_11", "Zydrine", BigDecimal.ZERO));
                priceSetItems.add(createPriceSetItem(40L, 4L, "11_10", "Megacyte", BigDecimal.ZERO));
                priceSetItems.add(createPriceSetItem(11399L, 4L, "35_02", "Morphite", BigDecimal.ZERO));
                eventBus.fireEvent(new PriceSetManyItemsAddedEvent(trackingManager, constants, priceSetItems, 0L));
            }
        }));
        staticHandlerRegistrations.add(display.getAddAdvancedMoonMaterialButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                List<PriceSetItemDto> priceSetItems = new ArrayList<PriceSetItemDto>();
                priceSetItems.add(createPriceSetItem(16670L, 4L, "49_09", "Crystalline Carbonide", BigDecimal.ZERO));
                priceSetItems.add(createPriceSetItem(16671L, 4L, "49_11", "Titanium Carbide", BigDecimal.ZERO));
                priceSetItems.add(createPriceSetItem(16672L, 4L, "49_12", "Tungsten Carbide", BigDecimal.ZERO));
                priceSetItems.add(createPriceSetItem(16673L, 4L, "49_10", "Fernite Carbide", BigDecimal.ZERO));
                priceSetItems.add(createPriceSetItem(16678L, 4L, "50_02", "Sylramic Fibers", BigDecimal.ZERO));
                priceSetItems.add(createPriceSetItem(16679L, 4L, "49_14", "Fullerides", BigDecimal.ZERO));
                priceSetItems.add(createPriceSetItem(16680L, 4L, "50_01", "Phenolic Composites", BigDecimal.ZERO));
                priceSetItems.add(createPriceSetItem(16681L, 4L, "49_16", "Nanotransistors", BigDecimal.ZERO));
                priceSetItems.add(createPriceSetItem(16682L, 4L, "49_15", "Hypersynaptic Fibers", BigDecimal.ZERO));
                priceSetItems.add(createPriceSetItem(16683L, 4L, "49_08", "Ferrogel", BigDecimal.ZERO));
                priceSetItems.add(createPriceSetItem(17317L, 4L, "49_13", "Fermionic Condensates", BigDecimal.ZERO));
                eventBus.fireEvent(new PriceSetManyItemsAddedEvent(trackingManager, constants, priceSetItems, 0L));
            }
        }));
        final Button addNewItemButton = display.getAddNewItemButton();
        staticHandlerRegistrations.add(addNewItemButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                addNewItemButton.setEnabled(false);
                String itemName = display.getAddNewItemName().getText();
                PriceSetAddItemAction action = new PriceSetAddItemAction();
                action.setItemTypeName(itemName);
                showSpinner();
                rpcService.execute(action, new PriceSetTabActionCallback<PriceSetAddItemActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(PriceSetAddItemActionResponse response) {
                        addNewItemButton.setEnabled(true);
                        eventBus.fireEvent(new PriceSetItemAddedEvent(trackingManager, constants, response, getExecutionDuration()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        addNewItemButton.setEnabled(true);
                        super.onFailure(throwable);
                    }
                });
            }
        }));
        final PriceSetListBox corporationPriceSetListBox = display.getCorporationPriceSetNamesListBox();
        staticHandlerRegistrations.add(corporationPriceSetListBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                Long currentPriceSetID = corporationPriceSetListBox.getCurrentPriceSetID();
                if (currentPriceSetID == -1) {
                    display.clearCorporationPriceSetItemsTable();
                    return;
                }
                corporationPriceSetListBox.setEnabled(false);
                PriceSetLoadForCorporationAction action = new PriceSetLoadForCorporationAction();
                action.setPriceSetID(currentPriceSetID);
                showSpinner();
                rpcService.execute(action, new PriceSetTabActionCallback<PriceSetLoadForCorporationActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(PriceSetLoadForCorporationActionResponse response) {
                        corporationPriceSetListBox.setEnabled(true);
                        eventBus.fireEvent(new PriceSetLoadedForCorporationEvent(trackingManager, constants, response, getExecutionDuration()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        corporationPriceSetListBox.setEnabled(true);
                        super.onFailure(throwable);
                    }
                });
            }
        }));
        final PriceSetListBox alliancePriceSetListBox = display.getAlliancePriceSetNamesListBox();
        staticHandlerRegistrations.add(alliancePriceSetListBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                Long currentPriceSetID = alliancePriceSetListBox.getCurrentPriceSetID();
                if (currentPriceSetID == -1) {
                    display.clearAlliancePriceSetItemsTable();
                    return;
                }
                alliancePriceSetListBox.setEnabled(false);
                PriceSetLoadForAllianceAction action = new PriceSetLoadForAllianceAction();
                action.setPriceSetID(currentPriceSetID);
                showSpinner();
                rpcService.execute(action, new PriceSetTabActionCallback<PriceSetLoadForAllianceActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(PriceSetLoadForAllianceActionResponse response) {
                        alliancePriceSetListBox.setEnabled(true);
                        eventBus.fireEvent(new PriceSetLoadedForAllianceEvent(trackingManager, constants, response, getExecutionDuration()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        alliancePriceSetListBox.setEnabled(true);
                        super.onFailure(throwable);
                    }
                });
            }
        }));
        final Button reloadCorporationPriceSetNamesButton = display.getReloadCorporationPriceSetNames();
        staticHandlerRegistrations.add(reloadCorporationPriceSetNamesButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                reloadCorporationPriceSetNamesButton.setEnabled(false);
                PriceSetLoadCorporationNamesAction action = new PriceSetLoadCorporationNamesAction();
                showSpinner();
                rpcService.execute(action, new PriceSetTabActionCallback<PriceSetLoadCorporationNamesActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(PriceSetLoadCorporationNamesActionResponse response) {
                        reloadCorporationPriceSetNamesButton.setEnabled(true);
                        eventBus.fireEvent(new PriceSetLoadedNamesForCorporationEvent(trackingManager, constants, response, getExecutionDuration()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        reloadCorporationPriceSetNamesButton.setEnabled(true);
                        super.onFailure(throwable);
                    }
                });
            }
        }));
        final Button reloadAlliancePriceSetNamesButton = display.getReloadAlliancePriceSetNames();
        staticHandlerRegistrations.add(reloadAlliancePriceSetNamesButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                reloadAlliancePriceSetNamesButton.setEnabled(false);
                PriceSetLoadAllianceNamesAction action = new PriceSetLoadAllianceNamesAction();
                showSpinner();
                rpcService.execute(action, new PriceSetTabActionCallback<PriceSetLoadAllianceNamesActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(PriceSetLoadAllianceNamesActionResponse response) {
                        reloadAlliancePriceSetNamesButton.setEnabled(true);
                        eventBus.fireEvent(new PriceSetLoadedNamesForAllianceEvent(trackingManager, constants, response, getExecutionDuration()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        reloadAlliancePriceSetNamesButton.setEnabled(true);
                        super.onFailure(throwable);
                    }
                });
            }
        }));
    }

    private void bindDynamic() {
        for (final PriceSetItemDto priceSetItem : display.getCurrentPriceSetItemDeleteButtons().keySet()) {
            bindRemoveButton(priceSetItem);
        }
        for (PriceTextBox priceSetTextBox : display.getCurrentPriceSetItemValues().values()) {
            dynamicHandlerRegistrations.addAll(priceSetTextBox.getHandlerRegistrations());
        }
    }

    private void bindRemoveButton(final PriceSetItemDto priceSetItem) {
        final Button removeButton = display.getCurrentPriceSetItemDeleteButtons().get(priceSetItem);
        dynamicHandlerRegistrations.add(removeButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                display.getCurrentPriceSetDeletedItems().add(priceSetItem);
                removeButton.setEnabled(false);
            }
        }));
    }

    private void unbindDynamic() {
        for (HandlerRegistration handlerRegistration : dynamicHandlerRegistrations) {
            handlerRegistration.removeHandler();
        }
        dynamicHandlerRegistrations.clear();
    }

    private void rebindDynamic() {
        unbindDynamic();
        bindDynamic();
    }

    private PriceSetItemDto createPriceSetItem(Long itemTypeID, Long itemCategoryID, String itemTypeIcon, String itemTypeName, BigDecimal price) {
        PriceSetItemDto priceSetItem = new PriceSetItemDto();
        priceSetItem.setItemTypeID(itemTypeID);
        priceSetItem.setItemCategoryID(itemCategoryID);
        priceSetItem.setItemTypeIcon(itemTypeIcon);
        priceSetItem.setItemTypeName(itemTypeName);
        priceSetItem.setPrice(price);
        return priceSetItem;
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
        display.getPriceSetNamesListBox().setEnabled(true);
        display.getSavePriceSetButton().setEnabled(true);
        display.getFetchEveCentralPricesButton().setEnabled(true);
        display.getFetchEveMetricsPricesButton().setEnabled(true);
        display.getCopyPriceSetButton().setEnabled(true);
        display.getRenamePriceSetButton().setEnabled(true);
        display.getDeletePriceSetButton().setEnabled(true);
        display.getAddBasicMineralsButton().setEnabled(true);
        display.getAddAdvancedMoonMaterialButton().setEnabled(true);
        display.getAddNewItemButton().setEnabled(true);
        display.getAddNewItemName().getTextBox().setEnabled(true);
        display.getAttachedCharacterNameListBox().setEnabled(true);
        display.getSharingLevelListBox().setEnabled(true);
    }

    private void disableButtons() {
        display.getPriceSetNamesListBox().setEnabled(false);
        display.getSavePriceSetButton().setEnabled(false);
        display.getFetchEveCentralPricesButton().setEnabled(false);
        display.getFetchEveMetricsPricesButton().setEnabled(false);
        display.getCopyPriceSetButton().setEnabled(false);
        display.getRenamePriceSetButton().setEnabled(false);
        display.getDeletePriceSetButton().setEnabled(false);
        display.getAddBasicMineralsButton().setEnabled(false);
        display.getAddAdvancedMoonMaterialButton().setEnabled(false);
        display.getAddNewItemButton().setEnabled(false);
        display.getAddNewItemName().getTextBox().setEnabled(false);
        display.getAddNewItemName().setText("");
        display.getAttachedCharacterNameListBox().setEnabled(false);
        display.getSharingLevelListBox().setEnabled(false);
    }
}
