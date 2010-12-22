package lv.odylab.evemanage.client.view.tab.preferences;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.CcpJsMessages;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.EveManageResources;
import lv.odylab.evemanage.client.EveManageUrlMessages;
import lv.odylab.evemanage.client.presenter.tab.PreferencesTabPresenter;
import lv.odylab.evemanage.client.rpc.dto.eve.RegionDto;
import lv.odylab.evemanage.client.rpc.dto.user.PriceFetchOptionDto;
import lv.odylab.evemanage.client.widget.PriceFetchOptionListBox;
import lv.odylab.evemanage.client.widget.RegionListBox;

import java.util.List;

public class PriceFetchingSection implements PreferencesTabPresenter.PriceFetchingSectionDisplay {
    private EveManageConstants constants;
    private EveManageResources resources;
    private EveManageMessages messages;
    private EveManageUrlMessages urlMessages;
    private CcpJsMessages ccpJsMessages;

    private Image spinnerImage;

    private Label priceFetchingSectionlabel;
    private FlexTable priceFetchingFlexTable;
    private Label preferredRegionLabel;
    private RegionListBox preferredRegionListBox;
    private Label preferredPriceLabel;
    private PriceFetchOptionListBox preferredPriceFetchOptionListBox;

    private FlexTable saveFlexTable;
    private Button savePriceFetchConfigurationButton;

    @Inject
    public PriceFetchingSection(EveManageConstants constants, EveManageResources resources, EveManageMessages messages, EveManageUrlMessages urlMessages, CcpJsMessages ccpJsMessages) {
        this.constants = constants;
        this.resources = resources;
        this.messages = messages;
        this.urlMessages = urlMessages;
        this.ccpJsMessages = ccpJsMessages;

        spinnerImage = new Image(resources.spinnerIcon());
        spinnerImage.setTitle(messages.loading());
        spinnerImage.setVisible(false);

        priceFetchingSectionlabel = new Label(messages.priceFetchConfiguration());
        priceFetchingSectionlabel.addStyleName(resources.css().tabHeadingText());
        priceFetchingFlexTable = new FlexTable();
        preferredRegionLabel = new Label(messages.preferredRegion() + ":");
        preferredRegionListBox = new RegionListBox();
        preferredRegionListBox.setEnabled(false);
        preferredPriceLabel = new Label(messages.preferredPrice() + ":");
        preferredPriceFetchOptionListBox = new PriceFetchOptionListBox(messages);
        preferredPriceFetchOptionListBox.setEnabled(false);

        saveFlexTable = new FlexTable();
        savePriceFetchConfigurationButton = new Button(messages.save());
        savePriceFetchConfigurationButton.setEnabled(false);
    }

    @Override
    public void attach(HasWidgets container) {
        container.add(priceFetchingSectionlabel);

        priceFetchingFlexTable.setWidget(0, 0, preferredRegionLabel);
        priceFetchingFlexTable.setWidget(0, 1, preferredRegionListBox);
        priceFetchingFlexTable.setWidget(1, 0, preferredPriceLabel);
        priceFetchingFlexTable.setWidget(1, 1, preferredPriceFetchOptionListBox);
        container.add(priceFetchingFlexTable);

        saveFlexTable.setWidget(0, 0, savePriceFetchConfigurationButton);
        saveFlexTable.setWidget(0, 1, spinnerImage);
        container.add(saveFlexTable);
    }

    @Override
    public Widget getPriceFetchingSectionSpinnerImage() {
        return spinnerImage;
    }

    @Override
    public void setRegions(List<RegionDto> regions) {
        preferredRegionListBox.setRegions(regions);
    }

    @Override
    public void setPreferredRegion(RegionDto preferredRegion) {
        preferredRegionListBox.selectRegion(preferredRegion);
    }

    @Override
    public RegionListBox getPreferredRegionListBox() {
        return preferredRegionListBox;
    }

    @Override
    public void setPriceFetchOptions(List<PriceFetchOptionDto> priceFetchOptions) {
        preferredPriceFetchOptionListBox.setPriceFetchOptions(priceFetchOptions);
    }

    @Override
    public void setPreferredPriceFetchOption(PriceFetchOptionDto preferredPriceFetchOption) {
        preferredPriceFetchOptionListBox.selectFetchOptions(preferredPriceFetchOption);
    }

    @Override
    public PriceFetchOptionListBox getPreferredPriceFetchOption() {
        return preferredPriceFetchOptionListBox;
    }

    @Override
    public Button getSavePriceFetchConfigurationButton() {
        return savePriceFetchConfigurationButton;
    }
}
