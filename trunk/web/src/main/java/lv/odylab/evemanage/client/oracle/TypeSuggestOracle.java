package lv.odylab.evemanage.client.oracle;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.EveManageResources;
import lv.odylab.evemanage.client.event.PriceSetTabActionCallback;
import lv.odylab.evemanage.client.rpc.EveManageRemoteServiceAsync;
import lv.odylab.evemanage.client.rpc.action.suggest.SuggestTypeAction;
import lv.odylab.evemanage.client.rpc.action.suggest.SuggestTypeActionResponse;
import lv.odylab.evemanage.client.rpc.dto.ItemTypeDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;
import lv.odylab.evemanage.client.util.EveImageUrlProvider;

import java.util.ArrayList;
import java.util.Collection;

public class TypeSuggestOracle extends SuggestOracle {
    private final Collection<Suggestion> EMPTY_SUGGESTIONS = new ArrayList<Suggestion>();
    private final Collection<Suggestion> SUGGESTING = new ArrayList<Suggestion>();
    private final Collection<Suggestion> NO_SUGGESTIONS = new ArrayList<Suggestion>();

    private EventBus eventBus;
    private TrackingManager trackingManager;
    private EveManageRemoteServiceAsync rpcService;
    private EveManageResources resources;
    private EveManageConstants constants;
    private EveImageUrlProvider imageUrlProvider;
    private Timer timer;

    @Inject
    public TypeSuggestOracle(EventBus eventBus, TrackingManager trackingManager, EveManageRemoteServiceAsync rpcService, final EveManageResources resources, EveManageConstants constants, final EveManageMessages messages, EveImageUrlProvider imageUrlProvider) {
        this.eventBus = eventBus;
        this.trackingManager = trackingManager;
        this.rpcService = rpcService;
        this.resources = resources;
        this.constants = constants;
        this.imageUrlProvider = imageUrlProvider;

        SUGGESTING.add(new Suggestion() {
            @Override
            public String getDisplayString() {
                return "<img src=\"" + resources.spinnerIcon().getURL() + "\" class=\"spinnerImage\">" + messages.suggesting();
            }

            @Override
            public String getReplacementString() {
                return "";
            }
        });

        NO_SUGGESTIONS.add(new Suggestion() {
            @Override
            public String getDisplayString() {
                return messages.noResults();
            }

            @Override
            public String getReplacementString() {
                return "";
            }
        });
    }

    @Override
    public void requestSuggestions(final Request oracleRequest, final Callback oracleCallback) {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer() {
            @Override
            public void run() {
                timer = null;
                String query = oracleRequest.getQuery();
                final Response oracleResponse = new Response();
                if (query.length() < 3) {
                    oracleResponse.setSuggestions(EMPTY_SUGGESTIONS);
                    oracleCallback.onSuggestionsReady(oracleRequest, oracleResponse);
                    return;
                }

                oracleResponse.setSuggestions(SUGGESTING);
                oracleCallback.onSuggestionsReady(oracleRequest, oracleResponse);

                AsyncCallback<SuggestTypeActionResponse> callback = new PriceSetTabActionCallback<SuggestTypeActionResponse>(eventBus, trackingManager, constants) {
                    @Override
                    public void onSuccess(SuggestTypeActionResponse response) {
                        Collection<Suggestion> suggestions = new ArrayList<Suggestion>();
                        for (ItemTypeDto itemType : response.getQueryResult()) {
                            suggestions.add(new TypeSuggestion(resources, imageUrlProvider, itemType));
                        }
                        if (suggestions.isEmpty()) {
                            oracleResponse.setSuggestions(NO_SUGGESTIONS);
                        } else {
                            oracleResponse.setSuggestions(suggestions);
                        }
                        oracleCallback.onSuggestionsReady(oracleRequest, oracleResponse);
                    }
                };
                SuggestTypeAction action = new SuggestTypeAction();
                action.setQuery(query);
                rpcService.execute(action, callback);
            }
        };
        timer.schedule(500);
    }

    @Override
    public boolean isDisplayStringHTML() {
        return true;
    }
}
