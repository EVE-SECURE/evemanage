package lv.odylab.evemanage.client.view.tab.preferences;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.EveManageResources;
import lv.odylab.evemanage.client.presenter.tab.PreferencesTabPresenter;
import lv.odylab.evemanage.client.rpc.dto.eve.RegionDto;
import lv.odylab.evemanage.client.widget.PriceFetchOptionListBox;
import lv.odylab.evemanage.client.widget.RegionListBox;
import lv.odylab.evemanage.shared.eve.PriceFetchOption;

import java.util.List;

public class PriceFetchingSection implements PreferencesTabPresenter.PriceFetchingSectionDisplay {
    private Image spinnerImage;

    private Label priceFetchingSectionLabel;
    private FlexTable priceFetchingFlexTable;
    private Label preferredRegionLabel;
    private RegionListBox preferredRegionListBox;
    private Label preferredPriceFetchOptionLabel;
    private PriceFetchOptionListBox preferredPriceFetchOptionListBox;
    private FlexTable saveFlexTable;
    private Button savePriceFetchConfigurationButton;

    @Inject
    public PriceFetchingSection(EveManageResources resources, EveManageMessages messages) {
        spinnerImage = new Image(resources.spinnerIcon());
        spinnerImage.setTitle(messages.loading());
        spinnerImage.setVisible(false);

        priceFetchingSectionLabel = new Label(messages.priceFetchConfiguration());
        priceFetchingSectionLabel.addStyleName(resources.css().tabHeadingText());
        priceFetchingFlexTable = new FlexTable();
        preferredRegionLabel = new Label(messages.preferredRegion() + ":");
        preferredRegionListBox = new RegionListBox();
        preferredRegionListBox.setEnabled(false);
        preferredPriceFetchOptionLabel = new Label(messages.preferredPrice() + ":");
        preferredPriceFetchOptionListBox = new PriceFetchOptionListBox(messages);
        preferredPriceFetchOptionListBox.setEnabled(false);

        saveFlexTable = new FlexTable();
        savePriceFetchConfigurationButton = new Button(messages.save());
        savePriceFetchConfigurationButton.setEnabled(false);
    }

    @Override
    public void attach(HasWidgets container) {
        container.add(priceFetchingSectionLabel);
        priceFetchingFlexTable.setWidget(0, 0, preferredRegionLabel);
        priceFetchingFlexTable.setWidget(0, 1, preferredRegionListBox);
        priceFetchingFlexTable.setWidget(1, 0, preferredPriceFetchOptionLabel);
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
    public void setPriceFetchOptions(List<PriceFetchOption> priceFetchOptions) {
        preferredPriceFetchOptionListBox.setPriceFetchOptions(priceFetchOptions);
    }

    @Override
    public void setPreferredPriceFetchOption(PriceFetchOption preferredPriceFetchOption) {
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
