package lv.odylab.evemanage.client.view;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.EveManageResources;
import lv.odylab.evemanage.client.presenter.LoginPresenter;

public class LoginView implements LoginPresenter.Display {
    private HorizontalPanel loginPanel;
    private HorizontalPanel bannerPanel;
    private Image spinnerImage;
    private Label userLoginLabel;
    private Anchor signInSignOutAnchor;
    private Anchor helpAnchor;
    private Anchor russianAnchor;
    private Anchor englishAnchor;
    private Label separatorLabel1;
    private Label separatorLabel2;
    private Label separatorLabel3;
    private Label bannerLabel;

    @Inject
    public LoginView(EveManageConstants constants, EveManageResources resources, EveManageMessages messages) {
        resources.css().ensureInjected();
        loginPanel = new HorizontalPanel();
        loginPanel.addStyleName(resources.css().loginPanel());

        spinnerImage = new Image(resources.spinnerIcon());
        spinnerImage.setVisible(false);
        spinnerImage.setTitle(messages.loading());

        Image userImage = new Image(resources.userIcon());
        userImage.setTitle(messages.user());
        userLoginLabel = new Label();
        userLoginLabel.setVisible(false);
        userLoginLabel.addStyleName(resources.css().userLoginLabel());
        separatorLabel1 = new Label("|");
        separatorLabel1.setVisible(false);
        separatorLabel1.addStyleName(resources.css().separatorLabel());
        separatorLabel2 = new Label("|");
        separatorLabel2.setVisible(false);
        separatorLabel2.addStyleName(resources.css().separatorLabel());
        separatorLabel3 = new Label("|");
        separatorLabel3.setVisible(false);
        separatorLabel3.addStyleName(resources.css().separatorLabel());
        signInSignOutAnchor = new Anchor();
        signInSignOutAnchor.setVisible(false);
        helpAnchor = new Anchor(messages.help(), constants.helpUrl(), "_blank");
        helpAnchor.setVisible(false);
        russianAnchor = new Anchor(messages.russian());
        russianAnchor.setVisible(false);
        englishAnchor = new Anchor(messages.english());
        englishAnchor.setVisible(false);
        loginPanel.add(userImage);
        loginPanel.add(spinnerImage);
        loginPanel.add(userLoginLabel);
        loginPanel.add(separatorLabel1);
        loginPanel.add(signInSignOutAnchor);
        loginPanel.add(separatorLabel2);
        loginPanel.add(helpAnchor);
        loginPanel.add(separatorLabel3);
        loginPanel.add(russianAnchor);
        loginPanel.add(englishAnchor);

        loginPanel.setCellVerticalAlignment(userImage, HasVerticalAlignment.ALIGN_MIDDLE);
        loginPanel.setCellVerticalAlignment(spinnerImage, HasVerticalAlignment.ALIGN_MIDDLE);
        loginPanel.setCellVerticalAlignment(userLoginLabel, HasVerticalAlignment.ALIGN_MIDDLE);
        loginPanel.setCellVerticalAlignment(separatorLabel1, HasVerticalAlignment.ALIGN_MIDDLE);
        loginPanel.setCellVerticalAlignment(signInSignOutAnchor, HasVerticalAlignment.ALIGN_MIDDLE);
        loginPanel.setCellVerticalAlignment(separatorLabel2, HasVerticalAlignment.ALIGN_MIDDLE);
        loginPanel.setCellVerticalAlignment(helpAnchor, HasVerticalAlignment.ALIGN_MIDDLE);
        loginPanel.setCellVerticalAlignment(separatorLabel3, HasVerticalAlignment.ALIGN_MIDDLE);
        loginPanel.setCellVerticalAlignment(russianAnchor, HasVerticalAlignment.ALIGN_MIDDLE);
        loginPanel.setCellVerticalAlignment(englishAnchor, HasVerticalAlignment.ALIGN_MIDDLE);

        bannerPanel = new HorizontalPanel();
        bannerPanel.setVisible(false);
        bannerLabel = new Label();
        bannerLabel.setStyleName(resources.css().bannerLabel());
        bannerPanel.add(bannerLabel);
        bannerPanel.setCellVerticalAlignment(bannerLabel, HasVerticalAlignment.ALIGN_MIDDLE);
    }

    @Override
    public void attach(HasWidgets container) {
        container.add(loginPanel);
        container.add(bannerPanel);
    }

    @Override
    public Widget getSpinnerImage() {
        return spinnerImage;
    }

    @Override
    public HasText getUserLoginLabel() {
        return userLoginLabel;
    }

    @Override
    public HasText getBannerLabel() {
        return bannerLabel;
    }

    @Override
    public Anchor getSignInSignOutAnchor() {
        return signInSignOutAnchor;
    }

    @Override
    public Anchor getRussianAnchor() {
        return russianAnchor;
    }

    @Override
    public Anchor getEnglishAnchor() {
        return englishAnchor;
    }

    @Override
    public void displayMenu() {
        userLoginLabel.setVisible(true);
        signInSignOutAnchor.setVisible(true);
        helpAnchor.setVisible(true);
        separatorLabel1.setVisible(true);
        separatorLabel2.setVisible(true);
        separatorLabel3.setVisible(true);
    }

    @Override
    public void displayBanner() {
        bannerPanel.setVisible(true);
    }
}
