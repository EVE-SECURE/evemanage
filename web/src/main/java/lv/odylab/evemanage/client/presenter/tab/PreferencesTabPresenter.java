package lv.odylab.evemanage.client.presenter.tab;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageErrorConstants;
import lv.odylab.evemanage.client.event.PreferencesTabActionCallback;
import lv.odylab.evemanage.client.event.error.PreferencesTabErrorEvent;
import lv.odylab.evemanage.client.event.error.PreferencesTabErrorEventHandler;
import lv.odylab.evemanage.client.event.preferences.*;
import lv.odylab.evemanage.client.presenter.AttachableDisplay;
import lv.odylab.evemanage.client.presenter.Presenter;
import lv.odylab.evemanage.client.rpc.EveManageRemoteServiceAsync;
import lv.odylab.evemanage.client.rpc.action.preferences.*;
import lv.odylab.evemanage.client.rpc.dto.eve.ApiKeyDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;
import lv.odylab.evemanage.client.widget.OnlyDigitsChangeHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PreferencesTabPresenter implements Presenter, PreferencesTabErrorEventHandler, PreferencesTabFirstLoadEventHandler, PreferencesCharacterAddedEventHandler, PreferencesCharacterDeletedEventHandler, PreferencesMainCharacterSetEventHandler, PreferencesApiKeyAddedEventHandler, PreferencesApiKeyDeletedEventHandler {

    public interface Display extends AttachableDisplay {

        Widget getSpinnerImage();

        Widget getErrorContainer();

        HasText getErrorMessageLabel();

        void setCharacters(List<CharacterDto> characters);

        void setMainCharacter(CharacterDto mainCharacter);

        Map<String, Image> getCharacterImageMap();

        Map<CharacterDto, Button> getCharacterDeleteButtonMap();

        Long getSelectedNewCharacterID();

        void setNewCharacterNames(List<CharacterNameDto> characterNames);

        ListBox getNewCharacterNamesListBox();

        Button getAddNewCharacterButton();

        void setApiKeys(List<ApiKeyDto> apiKeys);

        Map<ApiKeyDto, Button> getApiKeyDeleteButtonMap();

        TextBox getNewApiKeyUserIdTextBox();

        TextBox getNewApiKeyStringTextBox();

        Button getAddNewApiKeyButton();

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
        bindDynamic();
        hideSpinner();
    }

    @Override
    public void onCharacterAdded(PreferencesCharacterAddedEvent event) {
        display.setCharacters(event.getCharacters());
        display.setMainCharacter(event.getMainCharacter());
        setNewCharacterNames(event.getNewCharacterNames());
        rebindDynamic();
        hideSpinner();
    }

    @Override
    public void onCharacterDeleted(PreferencesCharacterDeletedEvent event) {
        display.setCharacters(event.getCharacters());
        display.setMainCharacter(event.getMainCharacter());
        setNewCharacterNames(event.getNewCharacterNames());
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
    }

    private void bindDynamic() {
        Map<String, Image> characterImageMap = display.getCharacterImageMap();
        for (final String characterName : characterImageMap.keySet()) {
            HasClickHandlers characterImage = characterImageMap.get(characterName);
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
        }
        Map<ApiKeyDto, Button> apiKeyRemoveButtonMap = display.getApiKeyDeleteButtonMap();
        for (final ApiKeyDto apiKey : apiKeyRemoveButtonMap.keySet()) {
            final Button deleteButton = apiKeyRemoveButtonMap.get(apiKey);
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
        Map<CharacterDto, Button> characterDeleteButtonMap = display.getCharacterDeleteButtonMap();
        for (final CharacterDto character : characterDeleteButtonMap.keySet()) {
            final Button deleteButton = characterDeleteButtonMap.get(character);
            dynamicHandlerRegistrations.add(deleteButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent clickEvent) {
                    deleteButton.setEnabled(false);
                    PreferencesDeleteCharacterAction action = new PreferencesDeleteCharacterAction();
                    action.setCharacterID(character.getId());
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

    private void enableButtons() {
        display.getNewApiKeyUserIdTextBox().setEnabled(true);
        display.getNewApiKeyStringTextBox().setEnabled(true);
        display.getAddNewApiKeyButton().setEnabled(true);
    }
}