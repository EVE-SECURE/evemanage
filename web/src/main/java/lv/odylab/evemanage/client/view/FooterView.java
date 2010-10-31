package lv.odylab.evemanage.client.view;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.CcpJsMessages;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.EveManageResources;
import lv.odylab.evemanage.client.presenter.FooterPresenter;
import lv.odylab.evemanage.client.util.IgbChecker;
import lv.odylab.evemanage.client.widget.EveCharacterInfoLink;

public class FooterView implements FooterPresenter.Display {
    private EveManageConstants constants;
    private EveManageResources resources;
    private EveManageMessages messages;
    private Panel footerPanel;

    @Inject
    public FooterView(EveManageConstants constants, EveManageResources resources, EveManageMessages messages, CcpJsMessages ccpJsMessages) {
        this.constants = constants;
        this.resources = resources;
        this.messages = messages;

        footerPanel = new VerticalPanel();
        footerPanel.addStyleName(resources.css().footerPanel());

        String characterString = "hcydo";
        if (IgbChecker.isInIgb()) {
            EveCharacterInfoLink characterLink = new EveCharacterInfoLink(ccpJsMessages, "hcydo", 499939401L);
            characterLink.setTarget("_blank");
            characterString = characterLink.toString();
        }
        Anchor eveOnlineAnchor = new Anchor(constants.eveOnline(), constants.eveOnlineUrl(), "_blank");
        eveOnlineAnchor.addStyleName(resources.css().footerAnchor());
        Anchor ccpGamesAnchor = new Anchor(constants.ccpGames(), constants.ccpGamesUrl(), "_blank");
        ccpGamesAnchor.addStyleName(resources.css().footerAnchor());
        Anchor appEngineAnchor = new Anchor(constants.googleAppEngine(), constants.googleAppEngineUrl(), "_blank");
        appEngineAnchor.addStyleName(resources.css().footerAnchor());

        InlineHTML footerInlineHtml = new InlineHTML(messages.footerText(characterString, eveOnlineAnchor.toString(), ccpGamesAnchor.toString(), appEngineAnchor.toString()));
        footerInlineHtml.addStyleName(resources.css().footerText());
        footerPanel.add(footerInlineHtml);
    }

    @Override
    public void attach(HasWidgets container) {
        container.add(footerPanel);
    }

    @Override
    public void setVersions(String eveManageVersion, String eveDbVersion) {
        Anchor eveManageVersionAnchor = new Anchor(eveManageVersion, constants.eveManageReleaseNotesUrl(), "_blank");
        eveManageVersionAnchor.addStyleName(resources.css().footerAnchor());
        Anchor eveDbVersionAnchor = new Anchor(eveDbVersion, constants.eveDbReleaseNotesUrl(), "_blank");
        eveDbVersionAnchor.addStyleName(resources.css().footerAnchor());
        InlineHTML footerVersionInlineHtml = new InlineHTML(messages.footerVersionText(eveManageVersionAnchor.toString(), eveDbVersionAnchor.toString()));
        footerVersionInlineHtml.addStyleName(resources.css().footerText());
        footerPanel.add(footerVersionInlineHtml);
    }
}
