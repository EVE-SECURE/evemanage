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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageErrorConstants;
import lv.odylab.evemanage.client.event.PreferencesTabActionCallback;
import lv.odylab.evemanage.client.event.error.PreferencesTabErrorEvent;
import lv.odylab.evemanage.client.event.error.PreferencesTabErrorEventHandler;
import lv.odylab.evemanage.client.event.preferences.PreferencesApiKeyAddedEvent;
import lv.odylab.evemanage.client.event.preferences.PreferencesApiKeyAddedEventHandler;
import lv.odylab.evemanage.client.event.preferences.PreferencesApiKeyDeletedEvent;
import lv.odylab.evemanage.client.event.preferences.PreferencesApiKeyDeletedEventHandler;
import lv.odylab.evemanage.client.event.preferences.PreferencesCharacterAddedEvent;
import lv.odylab.evemanage.client.event.preferences.PreferencesCharacterAddedEventHandler;
import lv.odylab.evemanage.client.event.preferences.PreferencesCharacterDeletedEvent;
import lv.odylab.evemanage.client.event.preferences.PreferencesCharacterDeletedEventHandler;
import lv.odylab.evemanage.client.event.preferences.PreferencesFetchedCalculationSkillLevelsEvent;
import lv.odylab.evemanage.client.event.preferences.PreferencesFetchedCalculationSkillLevelsEventHandler;
import lv.odylab.evemanage.client.event.preferences.PreferencesMainCharacterSetEvent;
import lv.odylab.evemanage.client.event.preferences.PreferencesMainCharacterSetEventHandler;
import lv.odylab.evemanage.client.event.preferences.PreferencesSavedPriceFetchConfigurationEvent;
import lv.odylab.evemanage.client.event.preferences.PreferencesSavedPriceFetchConfigurationEventHandler;
import lv.odylab.evemanage.client.event.preferences.PreferencesSavedSkillLevelsForCalculationEvent;
import lv.odylab.evemanage.client.event.preferences.PreferencesSavedSkillLevelsForCalculationEventHandler;
import lv.odylab.evemanage.client.event.preferences.PreferencesTabFirstLoadEvent;
import lv.odylab.evemanage.client.event.preferences.PreferencesTabFirstLoadEventHandler;
import lv.odylab.evemanage.client.presenter.AttachableDisplay;
import lv.odylab.evemanage.client.presenter.Presenter;
import lv.odylab.evemanage.client.presenter.tab.preferences.EditablePreferenceSkillLevel;
import lv.odylab.evemanage.client.presenter.tab.preferences.EditablePreferencesApiKey;
import lv.odylab.evemanage.client.presenter.tab.preferences.EditablePreferencesCharacter;
import lv.odylab.evemanage.client.rpc.EveManageRemoteServiceAsync;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesAddApiKeyAction;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesAddApiKeyActionResponse;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesAddCharacterAction;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesAddCharacterActionResponse;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesDeleteApiKeyAction;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesDeleteApiKeyActionResponse;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesDeleteCharacterAction;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesDeleteCharacterActionResponse;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesFetchCalculationSkillLevelsAction;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesFetchCalculationSkillLevelsActionResponse;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesSavePriceFetchConfigurationAction;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesSavePriceFetchConfigurationActionResponse;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesSaveSkillLevelsForCalculationAction;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesSaveSkillLevelsForCalculationActionResponse;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesSetMainCharacterAction;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesSetMainCharacterActionResponse;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesTabFirstLoadAction;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesTabFirstLoadActionResponse;
import lv.odylab.evemanage.client.rpc.dto.eve.ApiKeyDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.rpc.dto.eve.RegionDto;
import lv.odylab.evemanage.client.rpc.dto.user.PriceFetchOptionDto;
import lv.odylab.evemanage.client.rpc.dto.user.SkillLevelDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;
import lv.odylab.evemanage.client.widget.OnlyDigitsChangeHandler;
import lv.odylab.evemanage.client.widget.PriceFetchOptionListBox;
import lv.odylab.evemanage.client.widget.RegionListBox;
import lv.odylab.evemanage.client.widget.SkillBookImage;
import lv.odylab.evemanage.client.widget.SkillLevelImage;
import lv.odylab.evemanage.client.widget.SkillLevelListBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PreferencesTabPresenter implements Presenter, PreferencesTabErrorEventHandler, PreferencesTabFirstLoadEventHandler, PreferencesCharacterAddedEventHandler, PreferencesCharacterDeletedEventHandler, PreferencesMainCharacterSetEventHandler, PreferencesApiKeyAddedEventHandler, PreferencesApiKeyDeletedEventHandler, PreferencesSavedSkillLevelsForCalculationEventHandler, PreferencesFetchedCalculationSkillLevelsEventHandler, PreferencesSavedPriceFetchConfigurationEventHandler {

    public interface Display extends AttachableDisplay, CharactersSectionDisplay, ApiKeysSectionDisplay, SkillsForCalculationSectionDisplay, PriceFetchingSectionDisplay {

        Widget getSpinnerImage();

        Widget getErrorContainer();

        HasText getErrorMessageLabel();

    }

    public interface CharactersSectionDisplay extends AttachableDisplay {

        void setCharacters(List<CharacterDto> characters);

        void setMainCharacter(CharacterDto mainCharacter);

        Map<CharacterDto, EditablePreferencesCharacter> getCharacterToEditablePreferencesCharacterMap();

        Long getSelectedNewCharacterID();

        void setNewCharacterNames(List<CharacterNameDto> characterNames);

        ListBox getNewCharacterNamesListBox();

        Button getAddNewCharacterButton();

    }

    public interface ApiKeysSectionDisplay extends AttachableDisplay {

        void setApiKeys(List<ApiKeyDto> apiKeys);

        Map<ApiKeyDto, EditablePreferencesApiKey> getApiKeyToEditablePreferencesApiKeyMap();

        TextBox getNewApiKeyUserIdTextBox();

        TextBox getNewApiKeyStringTextBox();

        Button getAddNewApiKeyButton();

    }

    public interface SkillsForCalculationSectionDisplay extends AttachableDisplay {

        Widget getSkillsForCalculationSectionSpinnerImage();

        void setSkillLevelsForCalculation(List<SkillLevelDto> skillLevelsForCalculation);

        void updateSkillLevelsForCalculation(List<SkillLevelDto> skillLevelsForCalculation);

        List<SkillLevelDto> getSkillLevelsForCalculationThatNotEqualTo5();

        Button getSaveSkillLevelsButton();

        Button getFetchSkillLevelsForCurrentCharacterButton();

        Map<Long, EditablePreferenceSkillLevel> getTypeIdToEditablePreferenceSkillLevelMap();

    }

    public interface PriceFetchingSectionDisplay extends AttachableDisplay {

        Widget getPriceFetchingSectionSpinnerImage();

        void setRegions(List<RegionDto> regions);

        void setPreferredRegion(RegionDto preferredRegion);

        RegionListBox getPreferredRegionListBox();

        void setPriceFetchOptions(List<PriceFetchOptionDto> priceFetchOptions);

        void setPreferredPriceFetchOption(PriceFetchOptionDto preferredPriceFetchOption);

        PriceFetchOptionListBox getPreferredPriceFetchOption();

        Button getSavePriceFetchConfigurationButton();

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
    public PreferencesTabPresenter(EventBus eventBus, TrackingManager trackingManager, EveManageRemoteServiceAsync rpcService, EveManageConstants constants, EveManageErrorConstants errorConstants, Display display) {
        this.eventBus = eventBus;
        this.trackingManager = trackingManager;
        this.rpcService = rpcService;
        this.constants = constants;
        this.errorConstants = errorConstants;
        this.display = display;

        this.staticHandlerRegistrations = new ArrayList<HandlerRegistration>();
        this.dynamicHandlerRegistrations = new ArrayList<HandlerRegistration>();

        eventBus.addHandler(PreferencesTabErrorEvent.TYPE, this);
        eventBus.addHandler(PreferencesTabFirstLoadEvent.TYPE, this);
        eventBus.addHandler(PreferencesCharacterAddedEvent.TYPE, this);
        eventBus.addHandler(PreferencesCharacterDeletedEvent.TYPE, this);
        eventBus.addHandler(PreferencesMainCharacterSetEvent.TYPE, this);
        eventBus.addHandler(PreferencesApiKeyAddedEvent.TYPE, this);
        eventBus.addHandler(PreferencesApiKeyDeletedEvent.TYPE, this);
        eventBus.addHandler(PreferencesSavedSkillLevelsForCalculationEvent.TYPE, this);
        eventBus.addHandler(PreferencesFetchedCalculationSkillLevelsEvent.TYPE, this);
        eventBus.addHandler(PreferencesSavedPriceFetchConfigurationEvent.TYPE, this);
    }

    @Override
    public void go(HasWidgets container) {
        if (displayContainer == null) {
            displayContainer = container;
            displayContainer.clear();
            display.attach(displayContainer);
            bindStatic();
            doPreferencesTabFirstLoad();
        }
    }

    @Override
    public void onPreferencesTabError(PreferencesTabErrorEvent event) {
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
    public void onPreferencesTabFirstLoad(PreferencesTabFirstLoadEvent event) {
        enableButtons();
        display.setCharacters(event.getCharacters());
        display.setMainCharacter(event.getMainCharacter());
        setNewCharacterNames(event.getNewCharacterNames());
        display.setApiKeys(event.getApiKeys());
        display.setSkillLevelsForCalculation(event.getSkillLevelsForCalculation());
        display.setRegions(event.getRegions());
        display.setPreferredRegion(event.getPreferredRegion());
        display.setPriceFetchOptions(event.getPriceFetchOptions());
        display.setPreferredPriceFetchOption(event.getPreferredPriceFetchOption());
        display.getFetchSkillLevelsForCurrentCharacterButton().setEnabled(event.getCharacters().size() > 0);
        bindDynamic();
        hideSpinner();
    }

    @Override
    public void onCharacterAdded(PreferencesCharacterAddedEvent event) {
        display.setCharacters(event.getCharacters());
        display.setMainCharacter(event.getMainCharacter());
        setNewCharacterNames(event.getNewCharacterNames());
        display.getFetchSkillLevelsForCurrentCharacterButton().setEnabled(event.getCharacters().size() > 0);
        rebindDynamic();
        hideSpinner();
    }

    @Override
    public void onCharacterDeleted(PreferencesCharacterDeletedEvent event) {
        display.setCharacters(event.getCharacters());
        display.setMainCharacter(event.getMainCharacter());
        setNewCharacterNames(event.getNewCharacterNames());
        display.getFetchSkillLevelsForCurrentCharacterButton().setEnabled(event.getCharacters().size() > 0);
        rebindDynamic();
        hideSpinner();
    }

    @Override
    public void onMainCharacterSet(PreferencesMainCharacterSetEvent event) {
        display.setMainCharacter(event.getMainCharacter());
        hideSpinner();
    }

    @Override
    public void onApiKeyAdded(PreferencesApiKeyAddedEvent event) {
        display.getNewApiKeyUserIdTextBox().setText("");
        display.getNewApiKeyStringTextBox().setText("");
        display.setApiKeys(event.getApiKeys());
        display.setCharacters(event.getCharacters());
        display.setMainCharacter(event.getMainCharacter());
        setNewCharacterNames(event.getNewCharacterNames());
        rebindDynamic();
        hideSpinner();
    }

    @Override
    public void onApiKeyDeleted(PreferencesApiKeyDeletedEvent event) {
        display.setApiKeys(event.getApiKeys());
        display.setCharacters(event.getCharacters());
        display.setMainCharacter(event.getMainCharacter());
        setNewCharacterNames(event.getNewCharacterNames());
        rebindDynamic();
        hideSpinner();
    }

    @Override
    public void onFetchedCalculationSkillLevels(PreferencesFetchedCalculationSkillLevelsEvent event) {
        display.updateSkillLevelsForCalculation(event.getSkillLevelsForCalculation());
        hideSkillsForCalculationSectionSpinner();
    }

    @Override
    public void onSavedSkillLevelsForCalculation(PreferencesSavedSkillLevelsForCalculationEvent event) {
        hideSkillsForCalculationSectionSpinner();
    }

    @Override
    public void onSavedPriceFetchConfiguration(PreferencesSavedPriceFetchConfigurationEvent event) {
        hidePriceFetchingSectionSpinner();
    }

    private void doPreferencesTabFirstLoad() {
        showSpinner();
        rpcService.execute(new PreferencesTabFirstLoadAction(), new PreferencesTabActionCallback<PreferencesTabFirstLoadActionResponse>(eventBus, trackingManager, constants) {
            @Override
            public void onSuccess(PreferencesTabFirstLoadActionResponse response) {
                eventBus.fireEvent(new PreferencesTabFirstLoadEvent(trackingManager, constants, response, getExecutionDuration()));
            }
        });
    }

    private void setNewCharacterNames(List<CharacterNameDto> newCharacterNames) {
        display.setNewCharacterNames(newCharacterNames);
        if (newCharacterNames.size() > 0) {
            display.getNewCharacterNamesListBox().setEnabled(true);
            display.getAddNewCharacterButton().setEnabled(true);
        } else {
            display.getNewCharacterNamesListBox().setEnabled(false);
            display.getAddNewCharacterButton().setEnabled(false);
        }
    }

    private void bindStatic() {
        final Button addNewCharacterButton = display.getAddNewCharacterButton();
        staticHandlerRegistrations.add(addNewCharacterButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                addNewCharacterButton.setEnabled(false);
                PreferencesAddCharacterAction action = new PreferencesAddCharacterAction();
                action.setCharacterID(display.getSelectedNewCharacterID());
                showSpinner();
                rpcService.execute(action, new PreferencesTabActionCallback<PreferencesAddCharacterActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(PreferencesAddCharacterActionResponse response) {
                        addNewCharacterButton.setEnabled(true);
                        eventBus.fireEvent(new PreferencesCharacterAddedEvent(trackingManager, constants, response, getExecutionDuration()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        addNewCharacterButton.setEnabled(true);
                        super.onFailure(throwable);
                    }
                });
            }
        }));
        final Button newApiKeyButton = display.getAddNewApiKeyButton();
        staticHandlerRegistrations.add(newApiKeyButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                newApiKeyButton.setEnabled(false);
                PreferencesAddApiKeyAction action = new PreferencesAddApiKeyAction();
                action.setApiKeyUserID(display.getNewApiKeyUserIdTextBox().getText());
                action.setApiKeyString(display.getNewApiKeyStringTextBox().getText());
                showSpinner();
                rpcService.execute(action, new PreferencesTabActionCallback<PreferencesAddApiKeyActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(PreferencesAddApiKeyActionResponse response) {
                        eventBus.fireEvent(new PreferencesApiKeyAddedEvent(trackingManager, constants, response, getExecutionDuration()));
                        newApiKeyButton.setEnabled(true);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        newApiKeyButton.setEnabled(true);
                        super.onFailure(throwable);
                    }
                });
            }
        }));
        final TextBox newApiKeyUserIdTextBox = display.getNewApiKeyUserIdTextBox();
        staticHandlerRegistrations.add(newApiKeyUserIdTextBox.addChangeHandler(new OnlyDigitsChangeHandler(newApiKeyUserIdTextBox, 15)));
        final Button saveSkillsButton = display.getSaveSkillLevelsButton();
        staticHandlerRegistrations.add(saveSkillsButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                saveSkillsButton.setEnabled(false);
                PreferencesSaveSkillLevelsForCalculationAction action = new PreferencesSaveSkillLevelsForCalculationAction();
                action.setSkillLevelsForCalculation(display.getSkillLevelsForCalculationThatNotEqualTo5());
                showSkillsForCalculationSectionSpinner();
                rpcService.execute(action, new PreferencesTabActionCallback<PreferencesSaveSkillLevelsForCalculationActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(PreferencesSaveSkillLevelsForCalculationActionResponse response) {
                        eventBus.fireEvent(new PreferencesSavedSkillLevelsForCalculationEvent(trackingManager, constants, response, getExecutionDuration()));
                        saveSkillsButton.setEnabled(true);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        saveSkillsButton.setEnabled(true);
                        super.onFailure(throwable);
                    }
                });
            }
        }));
        final Button fetchSkillsForCurrentCharacterButton = display.getFetchSkillLevelsForCurrentCharacterButton();
        staticHandlerRegistrations.add(fetchSkillsForCurrentCharacterButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                fetchSkillsForCurrentCharacterButton.setEnabled(false);
                PreferencesFetchCalculationSkillLevelsAction action = new PreferencesFetchCalculationSkillLevelsAction();
                showSkillsForCalculationSectionSpinner();
                rpcService.execute(action, new PreferencesTabActionCallback<PreferencesFetchCalculationSkillLevelsActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(PreferencesFetchCalculationSkillLevelsActionResponse response) {
                        eventBus.fireEvent(new PreferencesFetchedCalculationSkillLevelsEvent(trackingManager, constants, response, getExecutionDuration()));
                        fetchSkillsForCurrentCharacterButton.setEnabled(true);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        fetchSkillsForCurrentCharacterButton.setEnabled(true);
                        super.onFailure(throwable);
                    }
                });
            }
        }));
        final Button savePriceFetchConfigurationButton = display.getSavePriceFetchConfigurationButton();
        staticHandlerRegistrations.add(savePriceFetchConfigurationButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                savePriceFetchConfigurationButton.setEnabled(false);
                PreferencesSavePriceFetchConfigurationAction action = new PreferencesSavePriceFetchConfigurationAction();
                action.setPreferredRegionID(display.getPreferredRegionListBox().getRegionID());
                action.setPreferredPriceFetchOption(display.getPreferredPriceFetchOption().getPriceFetchOption());
                showPriceFetchingSectionSpinner();
                rpcService.execute(action, new PreferencesTabActionCallback<PreferencesSavePriceFetchConfigurationActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(PreferencesSavePriceFetchConfigurationActionResponse response) {
                        eventBus.fireEvent(new PreferencesSavedPriceFetchConfigurationEvent(trackingManager, constants, response, getExecutionDuration()));
                        savePriceFetchConfigurationButton.setEnabled(true);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        savePriceFetchConfigurationButton.setEnabled(true);
                        super.onFailure(throwable);
                    }
                });
            }
        }));
    }

    private void bindDynamic() {
        Map<CharacterDto, EditablePreferencesCharacter> characterToEditablePreferencesCharacterMap = display.getCharacterToEditablePreferencesCharacterMap();
        for (Map.Entry<CharacterDto, EditablePreferencesCharacter> mapEntry : characterToEditablePreferencesCharacterMap.entrySet()) {
            CharacterDto character = mapEntry.getKey();
            EditablePreferencesCharacter editablePreferencesCharacter = mapEntry.getValue();
            final String characterName = character.getName();
            Image characterImage = editablePreferencesCharacter.getCharacterImage();
            dynamicHandlerRegistrations.add(characterImage.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent clickEvent) {
                    PreferencesSetMainCharacterAction action = new PreferencesSetMainCharacterAction();
                    action.setCharacterName(characterName);
                    showSpinner();
                    rpcService.execute(action, new PreferencesTabActionCallback<PreferencesSetMainCharacterActionResponse>(eventBus, trackingManager, constants) {
                        @Override
                        public void onSuccess(PreferencesSetMainCharacterActionResponse response) {
                            eventBus.fireEvent(new PreferencesMainCharacterSetEvent(trackingManager, constants, response, getExecutionDuration()));
                        }
                    });
                }
            }));
            final Long characterID = character.getId();
            final Button deleteButton = editablePreferencesCharacter.getDeleteButton();
            dynamicHandlerRegistrations.add(deleteButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent clickEvent) {
                    deleteButton.setEnabled(false);
                    PreferencesDeleteCharacterAction action = new PreferencesDeleteCharacterAction();
                    action.setCharacterID(characterID);
                    showSpinner();
                    rpcService.execute(action, new PreferencesTabActionCallback<PreferencesDeleteCharacterActionResponse>(eventBus, trackingManager, constants) {
                        @Override
                        public void onSuccess(PreferencesDeleteCharacterActionResponse response) {
                            eventBus.fireEvent(new PreferencesCharacterDeletedEvent(trackingManager, constants, response, getExecutionDuration()));
                        }
                    });
                }
            }));
        }
        Map<ApiKeyDto, EditablePreferencesApiKey> apiKeyDtoEditablePreferencesApiKeyMap = display.getApiKeyToEditablePreferencesApiKeyMap();
        for (Map.Entry<ApiKeyDto, EditablePreferencesApiKey> mapEntry : apiKeyDtoEditablePreferencesApiKeyMap.entrySet()) {
            final ApiKeyDto apiKey = mapEntry.getKey();
            EditablePreferencesApiKey editablePreferencesApiKey = mapEntry.getValue();
            final Button deleteButton = editablePreferencesApiKey.getDeleteButton();
            dynamicHandlerRegistrations.add(deleteButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent clickEvent) {
                    deleteButton.setEnabled(false);
                    PreferencesDeleteApiKeyAction action = new PreferencesDeleteApiKeyAction();
                    action.setApiKeyID(apiKey.getId());
                    showSpinner();
                    rpcService.execute(action, new PreferencesTabActionCallback<PreferencesDeleteApiKeyActionResponse>(eventBus, trackingManager, constants) {
                        @Override
                        public void onSuccess(PreferencesDeleteApiKeyActionResponse response) {
                            eventBus.fireEvent(new PreferencesApiKeyDeletedEvent(trackingManager, constants, response, getExecutionDuration()));
                        }
                    });
                }
            }));
        }
        bindSkillLevelListBoxes();
    }

    private void bindSkillLevelListBoxes() {
        for (EditablePreferenceSkillLevel editablePreferenceSkillLevel : display.getTypeIdToEditablePreferenceSkillLevelMap().values()) {
            final SkillLevelListBox skillLevelListBox = editablePreferenceSkillLevel.getSkillLevelListBox();
            final SkillBookImage skillBookImage = editablePreferenceSkillLevel.getSkillBookImage();
            final SkillLevelImage skillLevelImage = editablePreferenceSkillLevel.getSkillLevelImage();
            staticHandlerRegistrations.add(skillLevelListBox.addChangeHandler(new ChangeHandler() {
                @Override
                public void onChange(ChangeEvent event) {
                    skillBookImage.setLevel(skillLevelListBox.getSelectedLevel());
                    skillLevelImage.setLevel(skillLevelListBox.getSelectedLevel());
                }
            }));
        }
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

    private void showSpinner() {
        display.getErrorContainer().setVisible(false);
        display.getErrorMessageLabel().setText("");
        display.getSpinnerImage().setVisible(true);
    }

    private void hideSpinner() {
        display.getSpinnerImage().setVisible(false);
    }

    private void showSkillsForCalculationSectionSpinner() {
        display.getErrorContainer().setVisible(false);
        display.getErrorMessageLabel().setText("");
        display.getSkillsForCalculationSectionSpinnerImage().setVisible(true);
    }

    private void hideSkillsForCalculationSectionSpinner() {
        display.getSkillsForCalculationSectionSpinnerImage().setVisible(false);
    }

    private void showPriceFetchingSectionSpinner() {
        display.getErrorContainer().setVisible(false);
        display.getErrorMessageLabel().setText("");
        display.getPriceFetchingSectionSpinnerImage().setVisible(true);
    }

    private void hidePriceFetchingSectionSpinner() {
        display.getPriceFetchingSectionSpinnerImage().setVisible(false);
    }

    private void enableButtons() {
        display.getNewApiKeyUserIdTextBox().setEnabled(true);
        display.getNewApiKeyStringTextBox().setEnabled(true);
        display.getAddNewApiKeyButton().setEnabled(true);
        display.getSaveSkillLevelsButton().setEnabled(true);
        display.getPreferredRegionListBox().setEnabled(true);
        display.getPreferredPriceFetchOption().setEnabled(true);
        display.getSavePriceFetchConfigurationButton().setEnabled(true);
    }
}