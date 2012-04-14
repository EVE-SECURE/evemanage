package lv.odylab.evemanage.client.presenter.tab;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageErrorConstants;
import lv.odylab.evemanage.client.event.BlueprintsTabActionCallback;
import lv.odylab.evemanage.client.event.blueprints.*;
import lv.odylab.evemanage.client.event.error.BlueprintsTabErrorEvent;
import lv.odylab.evemanage.client.event.error.BlueprintsTabErrorEventHandler;
import lv.odylab.evemanage.client.event.preferences.*;
import lv.odylab.evemanage.client.presenter.AttachableDisplay;
import lv.odylab.evemanage.client.presenter.Presenter;
import lv.odylab.evemanage.client.presenter.tab.blueprint.BlueprintDetailsPresenter;
import lv.odylab.evemanage.client.presenter.tab.blueprint.ComputableBlueprintDetails;
import lv.odylab.evemanage.client.presenter.tab.blueprint.EditableBlueprintDetails;
import lv.odylab.evemanage.client.rpc.EveCalculator;
import lv.odylab.evemanage.client.rpc.EveManageRemoteServiceAsync;
import lv.odylab.evemanage.client.rpc.action.blueprints.*;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDetailsDto;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDto;
import lv.odylab.evemanage.client.rpc.dto.eve.ApiKeyDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;
import lv.odylab.evemanage.client.widget.AttachedCharacterListBox;
import lv.odylab.evemanage.client.widget.OnlyDigitsAndMinusChangeHandler;
import lv.odylab.evemanage.client.widget.OnlyDigitsChangeHandler;
import lv.odylab.evemanage.client.widget.SharingLevelListBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlueprintsTabPresenter implements Presenter, BlueprintsTabErrorEventHandler, BlueprintsTabFirstLoadEventHandler, BlueprintAddedEventHandler, BlueprintDeletedEventHandler, BlueprintSavedEventHandler, BlueprintGotBlueprintDetailsEventHandler, BlueprintsImportedEventHandler, BlueprintsReloadedEventHandler, BlueprintsReloadedForCorporationEventHandler, BlueprintsReloadedForAllianceEventHandler, BlueprintGotCorporationBlueprintDetailsEventHandler, BlueprintGotAllianceBlueprintDetailsEventHandler, PreferencesCharacterAddedEventHandler, PreferencesCharacterDeletedEventHandler, PreferencesApiKeyAddedEventHandler, PreferencesApiKeyDeletedEventHandler, PreferencesMainCharacterSetEventHandler {

    public interface Display extends AttachableDisplay {

        Widget getSpinnerImage();

        Widget getErrorContainer();

        HasText getErrorMessageLabel();

        SuggestBox getNewBlueprintTextBox();

        TextBox getNewBlueprintMeTextBox();

        TextBox getNewBlueprintPeTextBox();

        Button getAddNewBlueprintButton();

        TextArea getImportXmlTextArea();

        TextBox getOneTimeFullApiKeyTextBox();

        TextBox getOneTimeUserIdTextBox();

        TextBox getOneTimeCharacterIdTextBox();

        ListBox getOneTimeLevelListBox();

        TextArea getImportCsvTextArea();

        ListBox getFullApiKeyCharacterListBox();

        ListBox getFullApiKeyLevelListBox();

        void setAttachedCharacterNames(List<CharacterNameDto> attachedCharacterNames);

        AttachedCharacterListBox getAttachedCharacterNames();

        void setSharingLevels(List<String> sharingLevels);

        void setFullApiKeys(List<ApiKeyDto> fullApiKeys);

        SharingLevelListBox getSharingLevels();

        Button getImportButton();

        Map<Long, BlueprintDto> getBlueprintMap();

        Map<Long, BlueprintDto> getCorporationBlueprintMap();

        Map<Long, BlueprintDto> getAllianceBlueprintMap();

        Map<BlueprintDto, EditableBlueprintDetails> getBlueprintEditableDetailsMap();

        Map<BlueprintDto, EditableBlueprintDetails> getCorporationBlueprintEditableDetailsMap();

        Map<BlueprintDto, EditableBlueprintDetails> getAllianceBlueprintEditableDetailsMap();

        void setCurrentBlueprints(List<BlueprintDto> blueprints);

        void setCurrentCorporationBlueprints(List<BlueprintDto> blueprints);

        void setCurrentAllianceBlueprints(List<BlueprintDto> blueprints);

        void addBlueprint(BlueprintDto blueprint);

        void deleteBlueprint(Long blueprintID);

        Button getReloadButton();

        Button getHideButton();

        TextBox getFilterTextBox();

        Button getCorporationReloadButton();

        Button getCorporationHideButton();

        TextBox getCorporationFilterTextBox();

        Button getAllianceReloadButton();

        Button getAllianceHideButton();

        TextBox getAllianceFilterTextBox();

        List<HandlerRegistration> getHandlerRegistrations();

        List<HandlerRegistration> getHandlerRegistrationsForCorporation();

        List<HandlerRegistration> getHandlerRegistrationsForAlliance();

        void resetCorporationAndAllianceBlueprints();

    }

    private EventBus eventBus;
    private TrackingManager trackingManager;
    private EveManageRemoteServiceAsync rpcService;
    private EveManageConstants constants;
    private EveManageErrorConstants errorConstants;
    private EveCalculator calculator;
    private Display display;
    private BlueprintDetailsPresenter blueprintDetailsPresenter;

    private HasWidgets displayContainer;
    private List<HandlerRegistration> staticHandlerRegistrations;
    private List<HandlerRegistration> dynamicHandlerRegistrations;
    private List<HandlerRegistration> dynamicHandlerRegistrationsForCorporation;
    private List<HandlerRegistration> dynamicHandlerRegistrationsForAlliance;
    private Map<BlueprintDto, ComputableBlueprintDetails> blueprintToComputableDetailsMap;

    @Inject
    public BlueprintsTabPresenter(EventBus eventBus, TrackingManager trackingManager, EveManageRemoteServiceAsync rpcService, EveManageConstants constants, EveManageErrorConstants errorConstants, EveCalculator calculator, Display display, BlueprintDetailsPresenter blueprintDetailsPresenter) {
        this.eventBus = eventBus;
        this.trackingManager = trackingManager;
        this.rpcService = rpcService;
        this.constants = constants;
        this.errorConstants = errorConstants;
        this.calculator = calculator;
        this.display = display;
        this.blueprintDetailsPresenter = blueprintDetailsPresenter;

        this.staticHandlerRegistrations = new ArrayList<HandlerRegistration>();
        this.dynamicHandlerRegistrations = new ArrayList<HandlerRegistration>();
        this.dynamicHandlerRegistrationsForCorporation = new ArrayList<HandlerRegistration>();
        this.dynamicHandlerRegistrationsForAlliance = new ArrayList<HandlerRegistration>();
        this.blueprintToComputableDetailsMap = new HashMap<BlueprintDto, ComputableBlueprintDetails>();

        eventBus.addHandler(BlueprintsTabErrorEvent.TYPE, this);
        eventBus.addHandler(BlueprintsTabFirstLoadEvent.TYPE, this);
        eventBus.addHandler(BlueprintAddedEvent.TYPE, this);
        eventBus.addHandler(BlueprintDeletedEvent.TYPE, this);
        eventBus.addHandler(BlueprintSavedEvent.TYPE, this);
        eventBus.addHandler(BlueprintGotBlueprintDetailsEvent.TYPE, this);
        eventBus.addHandler(BlueprintsImportedEvent.TYPE, this);
        eventBus.addHandler(BlueprintsReloadedEvent.TYPE, this);
        eventBus.addHandler(BlueprintsReloadedForCorporationEvent.TYPE, this);
        eventBus.addHandler(BlueprintsReloadedForAllianceEvent.TYPE, this);
        eventBus.addHandler(BlueprintGotCorporationBlueprintDetailsEvent.TYPE, this);
        eventBus.addHandler(BlueprintGotAllianceBlueprintDetailsEvent.TYPE, this);
        eventBus.addHandler(BlueprintGotAllianceBlueprintDetailsEvent.TYPE, this);
        eventBus.addHandler(PreferencesCharacterAddedEvent.TYPE, this);
        eventBus.addHandler(PreferencesCharacterDeletedEvent.TYPE, this);
        eventBus.addHandler(PreferencesApiKeyAddedEvent.TYPE, this);
        eventBus.addHandler(PreferencesApiKeyDeletedEvent.TYPE, this);
        eventBus.addHandler(PreferencesMainCharacterSetEvent.TYPE, this);
    }

    @Override
    public void go(HasWidgets container) {
        if (displayContainer == null) {
            displayContainer = container;
            displayContainer.clear();
            display.attach(displayContainer);
            bindStatic();
            doBlueprintsTabFirstLoad();
        }
    }

    @Override
    public void onBlueprintsTabError(BlueprintsTabErrorEvent event) {
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
    public void onBlueprintsTabFirstLoad(BlueprintsTabFirstLoadEvent event) {
        display.setAttachedCharacterNames(event.getAttachedCharacterNames());
        display.getAttachedCharacterNames().setAttachedCharacterNames(event.getAttachedCharacterNames());
        display.setSharingLevels(event.getSharingLevels());
        display.setFullApiKeys(event.getFullApiKeys());
        display.setCurrentBlueprints(event.getBlueprints());
        bindDynamic();
        hideSpinner();
        enableButtons();
    }

    @Override
    public void onBlueprintAdded(BlueprintAddedEvent event) {
        display.addBlueprint(event.getBlueprint());
        bindBlueprint(event.getBlueprint());
        hideSpinner();
    }

    @Override
    public void onBlueprintDeleted(BlueprintDeletedEvent event) {
        display.deleteBlueprint(event.getBlueprintID());
        hideSpinner();
    }

    @Override
    public void onBlueprintSaved(BlueprintSavedEvent event) {
        BlueprintDto savedBlueprint = event.getBlueprint();
        BlueprintDto blueprint = display.getBlueprintMap().get(savedBlueprint.getId());
        blueprint.setMaterialLevel(savedBlueprint.getMaterialLevel());
        blueprint.setProductivityLevel(savedBlueprint.getProductivityLevel());
        blueprint.setAttachedCharacterInfo(savedBlueprint.getAttachedCharacterInfo());
        blueprint.setSharingLevel(savedBlueprint.getSharingLevel());
        EditableBlueprintDetails editableBlueprintDetails = display.getBlueprintEditableDetailsMap().get(blueprint);
        editableBlueprintDetails.getMeLevelLabel().setText(String.valueOf(savedBlueprint.getMaterialLevel()));
        editableBlueprintDetails.getPeLevelLabel().setText(String.valueOf(savedBlueprint.getProductivityLevel()));
        ComputableBlueprintDetails computableBlueprintDetails = blueprintToComputableDetailsMap.get(blueprint);
        if (computableBlueprintDetails != null) {
            computableBlueprintDetails.recalculate(blueprint, calculator);
        }
        hideSpinner();
    }

    @Override
    public void onGotBlueprintDetails(BlueprintGotBlueprintDetailsEvent event) {
        BlueprintDto blueprint = display.getBlueprintMap().get(event.getBlueprintID());
        EditableBlueprintDetails editableBlueprintDetails = display.getBlueprintEditableDetailsMap().get(blueprint);
        FlexTable detailsTable = editableBlueprintDetails.getDetailsTable();
        BlueprintDetailsDto details = event.getDetails();
        ComputableBlueprintDetails computableBlueprintDetails = blueprintDetailsPresenter.go(detailsTable, details, blueprint);
        computableBlueprintDetails.recalculate(blueprint, calculator);
        blueprintToComputableDetailsMap.put(blueprint, computableBlueprintDetails);
        editableBlueprintDetails.getSpinnerImage().setVisible(false);
    }

    @Override
    public void onBlueprintsImported(BlueprintsImportedEvent event) {
        Timer timer = new Timer() {
            @Override
            public void run() {
                display.getImportButton().setEnabled(true);
                display.getImportXmlTextArea().setText("");
                display.getImportCsvTextArea().setText("");
                display.getOneTimeFullApiKeyTextBox().setText("");
                display.getOneTimeUserIdTextBox().setText("");
                display.getOneTimeCharacterIdTextBox().setText("");
                display.getOneTimeLevelListBox().setSelectedIndex(0);
                display.getFullApiKeyCharacterListBox().setSelectedIndex(0);
                display.getFullApiKeyLevelListBox().setSelectedIndex(0);
                display.getReloadButton().click();
                enableButtons();
            }
        };
        timer.schedule(5000);
    }

    @Override
    public void onBlueprintsReloaded(BlueprintsReloadedEvent event) {
        unbindDynamic();
        display.setAttachedCharacterNames(event.getAttachedCharacterNames());
        display.setSharingLevels(event.getSharingLevels());
        display.setCurrentBlueprints(event.getBlueprints());
        bindDynamic();
        hideSpinner();
    }

    @Override
    public void onBlueprintsReloadedForCorporation(BlueprintsReloadedForCorporationEvent event) {
        unbindDynamicForCorporation();
        display.setCurrentCorporationBlueprints(event.getBlueprints());
        bindDynamicForCorporation();
        hideSpinner();
    }

    @Override
    public void onBlueprintsReloadedForAlliance(BlueprintsReloadedForAllianceEvent event) {
        unbindDynamicForAlliance();
        display.setCurrentAllianceBlueprints(event.getBlueprints());
        bindDynamicForAlliance();
        hideSpinner();
    }


    @Override
    public void onGotCorporationBlueprintDetails(BlueprintGotCorporationBlueprintDetailsEvent event) {
        BlueprintDto blueprint = display.getCorporationBlueprintMap().get(event.getBlueprintID());
        EditableBlueprintDetails editableBlueprintDetails = display.getCorporationBlueprintEditableDetailsMap().get(blueprint);
        FlexTable detailsTable = editableBlueprintDetails.getDetailsTable();
        BlueprintDetailsDto details = event.getDetails();
        ComputableBlueprintDetails computableBlueprintDetails = blueprintDetailsPresenter.go(detailsTable, details, blueprint);
        computableBlueprintDetails.recalculate(blueprint, calculator);
        editableBlueprintDetails.getSpinnerImage().setVisible(false);
    }

    @Override
    public void onGotAllianceBlueprintDetails(BlueprintGotAllianceBlueprintDetailsEvent event) {
        BlueprintDto blueprint = display.getAllianceBlueprintMap().get(event.getBlueprintID());
        EditableBlueprintDetails editableBlueprintDetails = display.getAllianceBlueprintEditableDetailsMap().get(blueprint);
        FlexTable detailsTable = editableBlueprintDetails.getDetailsTable();
        BlueprintDetailsDto details = event.getDetails();
        ComputableBlueprintDetails computableBlueprintDetails = blueprintDetailsPresenter.go(detailsTable, details, blueprint);
        computableBlueprintDetails.recalculate(blueprint, calculator);
        editableBlueprintDetails.getSpinnerImage().setVisible(false);
    }

    @Override
    public void onCharacterAdded(PreferencesCharacterAddedEvent event) {
        display.setAttachedCharacterNames(event.getCharacterNames());
        display.getAttachedCharacterNames().setAttachedCharacterNames(event.getCharacterNames());
        for (EditableBlueprintDetails editableBlueprintDetails : display.getBlueprintEditableDetailsMap().values()) {
            editableBlueprintDetails.getAttachedCharacterListBox().setAttachedCharacterNames(event.getCharacterNames());
        }
    }

    @Override
    public void onCharacterDeleted(PreferencesCharacterDeletedEvent event) {
        display.setAttachedCharacterNames(event.getCharacterNames());
        display.getAttachedCharacterNames().setAttachedCharacterNames(event.getCharacterNames());
        for (EditableBlueprintDetails editableBlueprintDetails : display.getBlueprintEditableDetailsMap().values()) {
            editableBlueprintDetails.getAttachedCharacterListBox().setAttachedCharacterNames(event.getCharacterNames());
        }
    }

    @Override
    public void onApiKeyAdded(PreferencesApiKeyAddedEvent event) {
        List<ApiKeyDto> fullApiKeys = new ArrayList<ApiKeyDto>();
        for (ApiKeyDto apiKeyDto : event.getApiKeys()) {
            if ("FULL".equals(apiKeyDto.getKeyType())) {
                fullApiKeys.add(apiKeyDto);
            }
        }
        display.setFullApiKeys(fullApiKeys);
    }

    @Override
    public void onApiKeyDeleted(PreferencesApiKeyDeletedEvent event) {
        List<ApiKeyDto> fullApiKeys = new ArrayList<ApiKeyDto>();
        for (ApiKeyDto apiKeyDto : event.getApiKeys()) {
            if ("FULL".equals(apiKeyDto.getKeyType())) {
                fullApiKeys.add(apiKeyDto);
            }
        }
        display.setFullApiKeys(fullApiKeys);
    }

    @Override
    public void onMainCharacterSet(PreferencesMainCharacterSetEvent event) {
        display.resetCorporationAndAllianceBlueprints();
    }

    private void doBlueprintsTabFirstLoad() {
        showSpinner();
        rpcService.execute(new BlueprintsTabFirstLoadAction(), new BlueprintsTabActionCallback<BlueprintsTabFirstLoadActionResponse>(eventBus, trackingManager, constants) {
            @Override
            public void onSuccess(BlueprintsTabFirstLoadActionResponse response) {
                eventBus.fireEvent(new BlueprintsTabFirstLoadEvent(trackingManager, constants, response, getExecutionDuration()));
            }
        });
    }

    private void bindStatic() {
        final Button addNewBlueprintButton = display.getAddNewBlueprintButton();
        staticHandlerRegistrations.add(addNewBlueprintButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                addNewBlueprintButton.setEnabled(false);
                BlueprintAddAction action = new BlueprintAddAction();
                action.setBlueprintTypeName(display.getNewBlueprintTextBox().getText());
                action.setMeLevel(Integer.valueOf(display.getNewBlueprintMeTextBox().getText()));
                action.setPeLevel(Integer.valueOf(display.getNewBlueprintPeTextBox().getText()));
                showSpinner();
                rpcService.execute(action, new BlueprintsTabActionCallback<BlueprintAddActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(BlueprintAddActionResponse response) {
                        addNewBlueprintButton.setEnabled(true);
                        eventBus.fireEvent(new BlueprintAddedEvent(trackingManager, constants, response, getExecutionDuration()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        addNewBlueprintButton.setEnabled(true);
                        super.onFailure(throwable);
                    }
                });
            }
        }));
        final TextArea importXmlTextArea = display.getImportXmlTextArea();
        final TextBox newBlueprintMeTextBox = display.getNewBlueprintMeTextBox();
        final TextBox newBlueprintPeTextBox = display.getNewBlueprintPeTextBox();
        staticHandlerRegistrations.add(newBlueprintMeTextBox.addChangeHandler(new OnlyDigitsAndMinusChangeHandler(newBlueprintMeTextBox, 3)));
        staticHandlerRegistrations.add(newBlueprintPeTextBox.addChangeHandler(new OnlyDigitsAndMinusChangeHandler(newBlueprintPeTextBox, 3)));
        final TextBox oneTimeFullApiKeyTextBox = display.getOneTimeFullApiKeyTextBox();
        final TextBox oneTimeUserIdTextBox = display.getOneTimeUserIdTextBox();
        final TextBox oneTimeCharacterIdTextBox = display.getOneTimeCharacterIdTextBox();
        final ListBox oneTimeLevelListBox = display.getOneTimeLevelListBox();
        final TextArea importCsvTextArea = display.getImportCsvTextArea();
        final ListBox fullApiKeyCharacterListBox = display.getFullApiKeyCharacterListBox();
        final ListBox fullApiKeyLevelListBox = display.getFullApiKeyLevelListBox();
        staticHandlerRegistrations.add(oneTimeUserIdTextBox.addChangeHandler(new OnlyDigitsChangeHandler(oneTimeUserIdTextBox, 15)));
        staticHandlerRegistrations.add(oneTimeCharacterIdTextBox.addChangeHandler(new OnlyDigitsChangeHandler(oneTimeCharacterIdTextBox, 15)));
        staticHandlerRegistrations.add(importXmlTextArea.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                if (importXmlTextArea.getText().length() > 0) {
                    importCsvTextArea.setEnabled(false);
                    oneTimeFullApiKeyTextBox.setEnabled(false);
                    oneTimeUserIdTextBox.setEnabled(false);
                    oneTimeCharacterIdTextBox.setEnabled(false);
                    oneTimeLevelListBox.setEnabled(false);
                    fullApiKeyCharacterListBox.setEnabled(false);
                    fullApiKeyLevelListBox.setEnabled(false);
                } else {
                    importCsvTextArea.setEnabled(true);
                    oneTimeFullApiKeyTextBox.setEnabled(true);
                    oneTimeUserIdTextBox.setEnabled(true);
                    oneTimeCharacterIdTextBox.setEnabled(true);
                    oneTimeLevelListBox.setEnabled(true);
                    fullApiKeyCharacterListBox.setEnabled(true);
                    fullApiKeyLevelListBox.setEnabled(true);
                }
            }
        }));
        ChangeHandler oneTimeFieldsChangeHandler = new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                if (oneTimeFullApiKeyTextBox.getText().length() > 0 ||
                        oneTimeUserIdTextBox.getText().length() > 0 ||
                        oneTimeCharacterIdTextBox.getText().length() > 0) {
                    importXmlTextArea.setEnabled(false);
                    importCsvTextArea.setEnabled(false);
                    fullApiKeyCharacterListBox.setEnabled(false);
                    fullApiKeyLevelListBox.setEnabled(false);
                } else {
                    importXmlTextArea.setEnabled(true);
                    importCsvTextArea.setEnabled(true);
                    fullApiKeyCharacterListBox.setEnabled(true);
                    fullApiKeyLevelListBox.setEnabled(true);
                }
            }
        };
        staticHandlerRegistrations.add(oneTimeFullApiKeyTextBox.addChangeHandler(oneTimeFieldsChangeHandler));
        staticHandlerRegistrations.add(oneTimeUserIdTextBox.addChangeHandler(oneTimeFieldsChangeHandler));
        staticHandlerRegistrations.add(oneTimeCharacterIdTextBox.addChangeHandler(oneTimeFieldsChangeHandler));
        staticHandlerRegistrations.add(importCsvTextArea.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                if (importCsvTextArea.getText().length() > 0) {
                    importXmlTextArea.setEnabled(false);
                    oneTimeFullApiKeyTextBox.setEnabled(false);
                    oneTimeUserIdTextBox.setEnabled(false);
                    oneTimeCharacterIdTextBox.setEnabled(false);
                    oneTimeLevelListBox.setEnabled(false);
                    fullApiKeyCharacterListBox.setEnabled(false);
                    fullApiKeyLevelListBox.setEnabled(false);
                } else {
                    importXmlTextArea.setEnabled(true);
                    oneTimeFullApiKeyTextBox.setEnabled(true);
                    oneTimeUserIdTextBox.setEnabled(true);
                    oneTimeCharacterIdTextBox.setEnabled(true);
                    oneTimeLevelListBox.setEnabled(true);
                    fullApiKeyCharacterListBox.setEnabled(true);
                    fullApiKeyLevelListBox.setEnabled(true);
                }
            }
        }));
        staticHandlerRegistrations.add(fullApiKeyCharacterListBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                if (fullApiKeyCharacterListBox.getSelectedIndex() != 0) {
                    importXmlTextArea.setEnabled(false);
                    oneTimeFullApiKeyTextBox.setEnabled(false);
                    oneTimeUserIdTextBox.setEnabled(false);
                    oneTimeCharacterIdTextBox.setEnabled(false);
                    oneTimeLevelListBox.setEnabled(false);
                    importCsvTextArea.setEnabled(false);
                } else {
                    importXmlTextArea.setEnabled(true);
                    oneTimeFullApiKeyTextBox.setEnabled(true);
                    oneTimeUserIdTextBox.setEnabled(true);
                    oneTimeCharacterIdTextBox.setEnabled(true);
                    oneTimeLevelListBox.setEnabled(true);
                    importCsvTextArea.setEnabled(true);
                }
            }
        }));
        final Button importButton = display.getImportButton();
        staticHandlerRegistrations.add(importButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                importButton.setEnabled(false);
                BlueprintsImportAction action = new BlueprintsImportAction();
                action.setAttachedCharacterID(display.getAttachedCharacterNames().getAttachedCharacterName().getId());
                action.setSharingLevel(display.getSharingLevels().getSharingLevel());
                action.setImportXml(importXmlTextArea.getText().isEmpty() ? null : importXmlTextArea.getText());
                action.setImportCsv(importCsvTextArea.getText().isEmpty() ? null : importCsvTextArea.getText());
                action.setOneTimeFullApiKey(oneTimeFullApiKeyTextBox.getText().isEmpty() ? null : oneTimeFullApiKeyTextBox.getText());
                action.setOneTimeUserID(oneTimeUserIdTextBox.getText().isEmpty() ? null : Long.valueOf(oneTimeUserIdTextBox.getText()));
                action.setOneTimeCharacterID(oneTimeCharacterIdTextBox.getText().isEmpty() ? null : Long.valueOf(oneTimeCharacterIdTextBox.getText()));
                action.setOneTimeLevel(oneTimeLevelListBox.getValue(oneTimeLevelListBox.getSelectedIndex()));
                action.setFullApiKeyCharacterID(Long.valueOf(fullApiKeyCharacterListBox.getValue(fullApiKeyCharacterListBox.getSelectedIndex())));
                action.setFullApiKeyLevel(fullApiKeyLevelListBox.getValue(fullApiKeyLevelListBox.getSelectedIndex()));
                showSpinner();
                rpcService.execute(action, new BlueprintsTabActionCallback<BlueprintsImportActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(BlueprintsImportActionResponse response) {
                        eventBus.fireEvent(new BlueprintsImportedEvent(trackingManager, constants, response, getExecutionDuration()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        importButton.setEnabled(true);
                        super.onFailure(throwable);
                    }
                });
            }
        }));
        final Button reloadButton = display.getReloadButton();
        staticHandlerRegistrations.add(reloadButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                reloadButton.setEnabled(false);
                showSpinner();
                rpcService.execute(new BlueprintsReloadAction(), new BlueprintsTabActionCallback<BlueprintsReloadActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(BlueprintsReloadActionResponse response) {
                        reloadButton.setEnabled(true);
                        eventBus.fireEvent(new BlueprintsReloadedEvent(trackingManager, constants, response, getExecutionDuration()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        reloadButton.setEnabled(true);
                        super.onFailure(throwable);
                    }
                });
            }
        }));
        final Button corporationReloadButton = display.getCorporationReloadButton();
        staticHandlerRegistrations.add(corporationReloadButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                corporationReloadButton.setEnabled(false);
                showSpinner();
                rpcService.execute(new BlueprintsReloadForCorporationAction(), new BlueprintsTabActionCallback<BlueprintsReloadForCorporationActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(BlueprintsReloadForCorporationActionResponse response) {
                        corporationReloadButton.setEnabled(true);
                        eventBus.fireEvent(new BlueprintsReloadedForCorporationEvent(trackingManager, constants, response, getExecutionDuration()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        corporationReloadButton.setEnabled(true);
                        super.onFailure(throwable);
                    }
                });
            }
        }));
        final Button allianceReloadButton = display.getAllianceReloadButton();
        staticHandlerRegistrations.add(allianceReloadButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                allianceReloadButton.setEnabled(false);
                showSpinner();
                rpcService.execute(new BlueprintsReloadForAllianceAction(), new BlueprintsTabActionCallback<BlueprintsReloadForAllianceActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(BlueprintsReloadForAllianceActionResponse response) {
                        allianceReloadButton.setEnabled(true);
                        eventBus.fireEvent(new BlueprintsReloadedForAllianceEvent(trackingManager, constants, response, getExecutionDuration()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        allianceReloadButton.setEnabled(true);
                        super.onFailure(throwable);
                    }
                });
            }
        }));
    }

    private void bindDynamic() {
        for (Map.Entry<BlueprintDto, EditableBlueprintDetails> mapEntry : display.getBlueprintEditableDetailsMap().entrySet()) {
            BlueprintDto blueprint = mapEntry.getKey();
            EditableBlueprintDetails editableBlueprintDetails = mapEntry.getValue();
            bindDetailsButton(blueprint, editableBlueprintDetails.getDetailsButton());
            bindDeleteButton(blueprint, editableBlueprintDetails.getDeleteButton());
            bindSaveButton(blueprint, editableBlueprintDetails);
        }
    }

    private void bindDynamicForCorporation() {
        for (Map.Entry<BlueprintDto, EditableBlueprintDetails> mapEntry : display.getCorporationBlueprintEditableDetailsMap().entrySet()) {
            BlueprintDto blueprint = mapEntry.getKey();
            EditableBlueprintDetails editableBlueprintDetails = mapEntry.getValue();
            bindDetailsForCorporationButton(blueprint, editableBlueprintDetails.getDetailsButton());
        }
    }

    private void bindDynamicForAlliance() {
        for (Map.Entry<BlueprintDto, EditableBlueprintDetails> mapEntry : display.getAllianceBlueprintEditableDetailsMap().entrySet()) {
            BlueprintDto blueprint = mapEntry.getKey();
            EditableBlueprintDetails editableBlueprintDetails = mapEntry.getValue();
            bindDetailsForAllianceButton(blueprint, editableBlueprintDetails.getDetailsButton());
        }
    }

    private void bindBlueprint(BlueprintDto blueprint) {
        EditableBlueprintDetails editableBlueprintDetails = display.getBlueprintEditableDetailsMap().get(blueprint);
        bindDetailsButton(blueprint, editableBlueprintDetails.getDetailsButton());
        bindDeleteButton(blueprint, editableBlueprintDetails.getDeleteButton());
        bindSaveButton(blueprint, editableBlueprintDetails);
    }

    private void bindDetailsButton(final BlueprintDto blueprint, Button detailButton) {
        dynamicHandlerRegistrations.add(detailButton.addClickHandler(new ClickHandler() {
            private boolean invoked = false;

            @Override
            public void onClick(ClickEvent event) {
                if (!invoked) {
                    BlueprintGetDetailsAction action = new BlueprintGetDetailsAction();
                    action.setBlueprintID(blueprint.getId());
                    action.setBlueprintTypeID(blueprint.getItemTypeID());
                    rpcService.execute(action, new BlueprintsTabActionCallback<BlueprintGetDetailsActionResponse>(eventBus, trackingManager, constants) {
                        @Override
                        public void onSuccess(BlueprintGetDetailsActionResponse response) {
                            eventBus.fireEvent(new BlueprintGotBlueprintDetailsEvent(trackingManager, constants, response, getExecutionDuration()));
                        }
                    });
                    invoked = true;
                }
            }
        }));
    }

    private void bindDetailsForCorporationButton(final BlueprintDto blueprint, Button detailButton) {
        dynamicHandlerRegistrationsForCorporation.add(detailButton.addClickHandler(new ClickHandler() {
            private boolean invoked = false;

            @Override
            public void onClick(ClickEvent event) {
                if (!invoked) {
                    BlueprintGetDetailsAction action = new BlueprintGetDetailsAction();
                    action.setBlueprintID(blueprint.getId());
                    action.setBlueprintTypeID(blueprint.getItemTypeID());
                    rpcService.execute(action, new BlueprintsTabActionCallback<BlueprintGetDetailsActionResponse>(eventBus, trackingManager, constants) {
                        @Override
                        public void onSuccess(BlueprintGetDetailsActionResponse response) {
                            eventBus.fireEvent(new BlueprintGotCorporationBlueprintDetailsEvent(trackingManager, constants, response, getExecutionDuration()));
                        }
                    });
                    invoked = true;
                }
            }
        }));
    }

    private void bindDetailsForAllianceButton(final BlueprintDto blueprint, Button detailButton) {
        dynamicHandlerRegistrationsForAlliance.add(detailButton.addClickHandler(new ClickHandler() {
            private boolean invoked = false;

            @Override
            public void onClick(ClickEvent event) {
                if (!invoked) {
                    BlueprintGetDetailsAction action = new BlueprintGetDetailsAction();
                    action.setBlueprintID(blueprint.getId());
                    action.setBlueprintTypeID(blueprint.getItemTypeID());
                    rpcService.execute(action, new BlueprintsTabActionCallback<BlueprintGetDetailsActionResponse>(eventBus, trackingManager, constants) {
                        @Override
                        public void onSuccess(BlueprintGetDetailsActionResponse response) {
                            eventBus.fireEvent(new BlueprintGotAllianceBlueprintDetailsEvent(trackingManager, constants, response, getExecutionDuration()));
                        }
                    });
                    invoked = true;
                }
            }
        }));
    }

    private void bindDeleteButton(final BlueprintDto blueprint, final Button deleteButton) {
        dynamicHandlerRegistrations.add(deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                deleteButton.setEnabled(false);
                BlueprintDeleteAction action = new BlueprintDeleteAction();
                action.setBlueprintID(blueprint.getId());
                showSpinner();
                rpcService.execute(action, new BlueprintsTabActionCallback<BlueprintDeleteActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(BlueprintDeleteActionResponse response) {
                        deleteButton.setEnabled(true);
                        eventBus.fireEvent(new BlueprintDeletedEvent(trackingManager, constants, response, getExecutionDuration()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        deleteButton.setEnabled(true);
                        super.onFailure(throwable);
                    }
                });
            }
        }));
    }

    private void bindSaveButton(final BlueprintDto blueprint, final EditableBlueprintDetails editableBlueprintDetails) {
        final Button saveButton = editableBlueprintDetails.getSaveButton();
        final TextBox meLevelTextBox = editableBlueprintDetails.getMeLevelTextBox();
        final TextBox peLevelTextBox = editableBlueprintDetails.getPeLevelTextBox();
        final TextBox itemIdTextBox = editableBlueprintDetails.getItemIdTextBox();
        dynamicHandlerRegistrations.add(saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                saveButton.setEnabled(false);
                BlueprintSaveAction action = new BlueprintSaveAction();
                action.setBlueprintID(blueprint.getId());
                action.setMeLevel(Integer.valueOf(meLevelTextBox.getText()));
                action.setPeLevel(Integer.valueOf(peLevelTextBox.getText()));
                action.setAttachedCharacterID(editableBlueprintDetails.getAttachedCharacterListBox().getAttachedCharacterName().getId());
                action.setSharingLevel(editableBlueprintDetails.getSharingLevelListBox().getSharingLevel());
                if (itemIdTextBox.getText().length() > 0) {
                    action.setItemID(Long.valueOf(itemIdTextBox.getText()));
                }
                showSpinner();
                rpcService.execute(action, new BlueprintsTabActionCallback<BlueprintSaveActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(BlueprintSaveActionResponse response) {
                        saveButton.setEnabled(true);
                        eventBus.fireEvent(new BlueprintSavedEvent(trackingManager, constants, response, getExecutionDuration()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        saveButton.setEnabled(true);
                        super.onFailure(throwable);
                    }
                });
            }
        }));
        dynamicHandlerRegistrations.add(itemIdTextBox.addChangeHandler(new OnlyDigitsChangeHandler(itemIdTextBox, 15)));
        dynamicHandlerRegistrations.add(meLevelTextBox.addChangeHandler(new OnlyDigitsAndMinusChangeHandler(meLevelTextBox, 3)));
        dynamicHandlerRegistrations.add(peLevelTextBox.addChangeHandler(new OnlyDigitsAndMinusChangeHandler(peLevelTextBox, 3)));
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

    private void unbindDynamicForCorporation() {
        for (HandlerRegistration handlerRegistration : dynamicHandlerRegistrationsForCorporation) {
            handlerRegistration.removeHandler();
        }
        dynamicHandlerRegistrationsForCorporation.clear();

        List<HandlerRegistration> displayHandlerRegistrations = display.getHandlerRegistrationsForCorporation();
        for (HandlerRegistration handlerRegistration : displayHandlerRegistrations) {
            handlerRegistration.removeHandler();
        }
        displayHandlerRegistrations.clear();
    }

    private void unbindDynamicForAlliance() {
        for (HandlerRegistration handlerRegistration : dynamicHandlerRegistrationsForAlliance) {
            handlerRegistration.removeHandler();
        }
        dynamicHandlerRegistrationsForAlliance.clear();

        List<HandlerRegistration> displayHandlerRegistrations = display.getHandlerRegistrationsForAlliance();
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
        display.getNewBlueprintTextBox().getTextBox().setEnabled(true);
        display.getNewBlueprintMeTextBox().setEnabled(true);
        display.getNewBlueprintPeTextBox().setEnabled(true);
        display.getAddNewBlueprintButton().setEnabled(true);
        display.getImportXmlTextArea().setEnabled(true);
        display.getOneTimeFullApiKeyTextBox().setEnabled(true);
        display.getOneTimeUserIdTextBox().setEnabled(true);
        display.getOneTimeCharacterIdTextBox().setEnabled(true);
        display.getOneTimeLevelListBox().setEnabled(true);
        display.getImportCsvTextArea().setEnabled(true);
        display.getFullApiKeyCharacterListBox().setEnabled(true);
        display.getFullApiKeyLevelListBox().setEnabled(true);
        display.getAttachedCharacterNames().setEnabled(true);
        display.getSharingLevels().setEnabled(true);
        display.getImportButton().setEnabled(true);
        display.getReloadButton().setEnabled(true);
        display.getHideButton().setEnabled(true);
        display.getFilterTextBox().setEnabled(true);
        display.getCorporationReloadButton().setEnabled(true);
        display.getCorporationHideButton().setEnabled(true);
        display.getCorporationFilterTextBox().setEnabled(true);
        display.getAllianceReloadButton().setEnabled(true);
        display.getAllianceHideButton().setEnabled(true);
        display.getAllianceFilterTextBox().setEnabled(true);
    }
}