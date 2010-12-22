package lv.odylab.evemanage.client.presenter.tab;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.presenter.AttachableDisplay;
import lv.odylab.evemanage.client.presenter.Presenter;

public class DashboardTabPresenter implements Presenter {

    public interface Display extends AttachableDisplay {
    }

    private Display display;
    private HasWidgets displayContainer;

    @Inject
    public DashboardTabPresenter(Display display) {
        this.display = display;
    }

    @Override
    public void go(HasWidgets container) {
        if (displayContainer == null) {
            displayContainer = container;
            displayContainer.clear();
            display.attach(displayContainer);
        }
    }
}
