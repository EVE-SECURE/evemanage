package lv.odylab.evemanage.client.presenter.tab;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.presenter.AttachableDisplay;
import lv.odylab.evemanage.client.presenter.Presenter;

public class AboutTabPresenter implements Presenter {

    public interface Display extends AttachableDisplay {
    }

    private Display display;

    @Inject
    public AboutTabPresenter(Display display) {
        this.display = display;
    }

    @Override
    public void go(HasWidgets container) {
        if (!container.iterator().hasNext()) {
            display.attach(container);
        }
    }
}
