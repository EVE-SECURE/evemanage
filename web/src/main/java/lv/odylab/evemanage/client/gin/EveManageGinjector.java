package lv.odylab.evemanage.client.gin;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import lv.odylab.evemanage.client.presenter.ContentPresenter;
import lv.odylab.evemanage.client.presenter.FooterPresenter;
import lv.odylab.evemanage.client.presenter.LoginPresenter;

@GinModules(EveManageModule.class)
public interface EveManageGinjector extends Ginjector {

    LoginPresenter getLoginPresenter();

    ContentPresenter getContentPresenter();

    FooterPresenter getFooterPresenter();

}
