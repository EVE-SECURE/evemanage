package lv.odylab.evemanage.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import lv.odylab.evemanage.client.gin.EveManageGinjector;
import lv.odylab.evemanage.client.presenter.ContentPresenter;
import lv.odylab.evemanage.client.presenter.FooterPresenter;
import lv.odylab.evemanage.client.presenter.LoginPresenter;

public class EveManageEntryPoint implements EntryPoint {
    private final EveManageGinjector injector = GWT.create(EveManageGinjector.class);

    @Override
    public void onModuleLoad() {
        LoginPresenter loginPresenter = injector.getLoginPresenter();
        loginPresenter.go(RootPanel.get("login"));

        ContentPresenter contentPresenter = injector.getContentPresenter();
        contentPresenter.go(RootPanel.get("content"));

        FooterPresenter footerPresenter = injector.getFooterPresenter();
        footerPresenter.go(RootPanel.get("footer"));
    }
}
