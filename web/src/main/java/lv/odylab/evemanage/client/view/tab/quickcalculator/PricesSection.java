package lv.odylab.evemanage.client.view.tab.quickcalculator;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.CcpJsMessages;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.EveManageResources;
import lv.odylab.evemanage.client.EveManageUrlMessages;
import lv.odylab.evemanage.client.presenter.tab.QuickCalculatorTabPresenter;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.ComputableCalculationPriceSetItem;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.EditableCalculationPriceSetItem;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationPriceItemDto;
import lv.odylab.evemanage.client.rpc.dto.eve.RegionDto;
import lv.odylab.evemanage.client.rpc.dto.user.PriceFetchOptionDto;
import lv.odylab.evemanage.client.util.EveImageUrlProvider;
import lv.odylab.evemanage.client.widget.DamagePerJobLabel;
import lv.odylab.evemanage.client.widget.EveCentralQuicklookLink;
import lv.odylab.evemanage.client.widget.EveItemInfoLink;
import lv.odylab.evemanage.client.widget.EveItemMarketDetailsLink;
import lv.odylab.evemanage.client.widget.EveMetricsItemPriceLink;
import lv.odylab.evemanage.client.widget.PriceFetchOptionListBox;
import lv.odylab.evemanage.client.widget.PriceLabel;
import lv.odylab.evemanage.client.widget.PriceTextBox;
import lv.odylab.evemanage.client.widget.QuantityLabel;
import lv.odylab.evemanage.client.widget.RegionListBox;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PricesSection implements QuickCalculatorTabPresenter.PricesSectionDisplay {
    private EveManageConstants constants;
    private EveManageResources resources;
    private EveManageMessages messages;
    private EveManageUrlMessages urlMessages;
    private CcpJsMessages ccpJsMessages;
    private EveImageUrlProvider imageUrlProvider;

    private Image spinnerImage;
    private Label priceSetLabel;
    private FlexTable priceSetItemTable;
    private FlexTable applyAndFetchPricesTable;
    private Button fetchEveCentralPricesButton;
    private Button fetchEveMetricsPricesButton;
    private FlexTable preferredRegionAndPriceFetchTable;
    private RegionListBox preferredRegionListBox;
    private PriceFetchOptionListBox preferredPriceFetchOptionListBox;

    private Map<Long, EditableCalculationPriceSetItem> typeIdToEditableCalculationPriceSetItemMap;
    private Map<Long, ComputableCalculationPriceSetItem> typeIdToComputableCalculationPriceSetItemMap;

    @Inject
    public PricesSection(EveManageConstants constants, EveManageResources resources, EveManageMessages messages, EveManageUrlMessages urlMessages, CcpJsMessages ccpJsMessages, EveImageUrlProvider imageUrlProvider) {
        this.constants = constants;
        this.resources = resources;
        this.messages = messages;
        this.urlMessages = urlMessages;
        this.ccpJsMessages = ccpJsMessages;
        this.imageUrlProvider = imageUrlProvider;

        spinnerImage = new Image(resources.spinnerIcon());
        spinnerImage.setTitle(messages.loading());
        spinnerImage.setVisible(false);

        priceSetLabel = new Label(messages.prices());
        priceSetLabel.addStyleName(resources.css().tabHeadingText());
        priceSetItemTable = new FlexTable();

        applyAndFetchPricesTable = new FlexTable();
        fetchEveCentralPricesButton = new Button(messages.fetchPricesFromEveCentral());
        fetchEveCentralPricesButton.setEnabled(false);
        fetchEveMetricsPricesButton = new Button(messages.orEveMetrics());
        fetchEveMetricsPricesButton.setEnabled(false);
        preferredRegionAndPriceFetchTable = new FlexTable();
        preferredRegionListBox = new RegionListBox();
        preferredRegionListBox.setEnabled(false);
        preferredPriceFetchOptionListBox = new PriceFetchOptionListBox(messages);
        preferredPriceFetchOptionListBox.setEnabled(false);

        typeIdToEditableCalculationPriceSetItemMap = new HashMap<Long, EditableCalculationPriceSetItem>();
        typeIdToComputableCalculationPriceSetItemMap = new HashMap<Long, ComputableCalculationPriceSetItem>();
    }

    @Override
    public void attach(HasWidgets container) {
        container.add(priceSetLabel);
        container.add(priceSetItemTable);
        applyAndFetchPricesTable.setWidget(0, 0, fetchEveCentralPricesButton);
        applyAndFetchPricesTable.setWidget(0, 1, fetchEveMetricsPricesButton);
        applyAndFetchPricesTable.setWidget(0, 2, spinnerImage);
        container.add(applyAndFetchPricesTable);
        preferredRegionAndPriceFetchTable.setWidget(0, 0, preferredRegionListBox);
        preferredRegionAndPriceFetchTable.setWidget(0, 1, preferredPriceFetchOptionListBox);
        container.add(preferredRegionAndPriceFetchTable);
    }

    @Override
    public Widget getPricesSectionSpinnerImage() {
        return spinnerImage;
    }

    @Override
    public Button getFetchEveCentralPricesButton() {
        return fetchEveCentralPricesButton;
    }

    @Override
    public Button getFetchEveMetricsPricesButton() {
        return fetchEveMetricsPricesButton;
    }

    public void drawCalculationPriceSetItems(Collection<CalculationPriceItemDto> calculationPriceItems) {
        for (CalculationPriceItemDto calculationPriceItemDto : calculationPriceItems) {
            drawCalculationPriceSetItem(calculationPriceItemDto);
        }
    }

    @Override
    public void cleanPrices() {
        priceSetItemTable.removeAllRows();
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

    private void drawCalculationPriceSetItem(CalculationPriceItemDto calculationPriceItem) {
        EditableCalculationPriceSetItem editableCalculationPriceSetItem = new EditableCalculationPriceSetItem();
        Long typeID = calculationPriceItem.getItemTypeID();
        typeIdToEditableCalculationPriceSetItemMap.put(typeID, editableCalculationPriceSetItem);
        ComputableCalculationPriceSetItem computableCalculationPriceSetItem = new ComputableCalculationPriceSetItem();
        computableCalculationPriceSetItem.setCalculationPriceItem(calculationPriceItem);
        typeIdToComputableCalculationPriceSetItemMap.put(typeID, computableCalculationPriceSetItem);

        int index = priceSetItemTable.getRowCount();
        String imageUrl = imageUrlProvider.getImage32Url(calculationPriceItem.getItemCategoryID(), typeID, calculationPriceItem.getItemTypeIcon());
        Image image = new Image(imageUrl);
        image.setTitle(calculationPriceItem.getItemTypeName());
        image.addStyleName(resources.css().image32());
        EveItemInfoLink imageItemInfoLink = new EveItemInfoLink(ccpJsMessages, image, typeID);
        priceSetItemTable.setWidget(index, 0, imageItemInfoLink);
        priceSetItemTable.setWidget(index, 1, new EveItemMarketDetailsLink(constants, urlMessages, ccpJsMessages, calculationPriceItem.getItemTypeName(), typeID));
        PriceTextBox priceTextBox = new PriceTextBox();
        priceTextBox.setPrice(calculationPriceItem.getPrice());
        priceTextBox.addStyleName(resources.css().priceInput());
        priceSetItemTable.setWidget(index, 2, priceTextBox);
        Image eveCentralImage = new Image(resources.eveCentralIcon());
        eveCentralImage.setTitle(messages.eveCentralQuicklook());
        priceSetItemTable.setWidget(index, 3, new EveCentralQuicklookLink(constants, urlMessages, eveCentralImage, typeID));
        Image eveMetricsImage = new Image(resources.eveMetricsIcon());
        eveMetricsImage.setTitle(messages.eveMetricsItemPrice());
        priceSetItemTable.setWidget(index, 4, new EveMetricsItemPriceLink(constants, urlMessages, eveMetricsImage, calculationPriceItem.getItemCategoryID(), typeID));
        priceSetItemTable.setWidget(index, 5, new Label("x"));
        QuantityLabel quantityLabel = new QuantityLabel(calculationPriceItem.getQuantity());
        HorizontalPanel quantityAndDamagePerJobPanel = new HorizontalPanel();
        quantityAndDamagePerJobPanel.add(quantityLabel);
        BigDecimal damagePerJob = calculationPriceItem.getDamagePerJob();
        if (BigDecimal.ONE.compareTo(damagePerJob) == 1) {
            DamagePerJobLabel damagePerJobLabel = new DamagePerJobLabel(damagePerJob);
            damagePerJobLabel.addStyleName(resources.css().damagePerJob());
            quantityAndDamagePerJobPanel.add(damagePerJobLabel);
            quantityAndDamagePerJobPanel.setCellVerticalAlignment(damagePerJobLabel, HasVerticalAlignment.ALIGN_BOTTOM);
        }
        priceSetItemTable.setWidget(index, 6, quantityAndDamagePerJobPanel);
        priceSetItemTable.setWidget(index, 7, new Label("="));
        PriceLabel totalPriceLabel = new PriceLabel(calculationPriceItem.getTotalPrice());
        priceSetItemTable.setWidget(index, 8, totalPriceLabel);

        editableCalculationPriceSetItem.setPriceTextBox(priceTextBox);
        computableCalculationPriceSetItem.setQuantityLabel(quantityLabel);
        computableCalculationPriceSetItem.setTotalPriceLabel(totalPriceLabel);
    }

    @Override
    public Map<Long, BigDecimal> createTypeIdToPriceMap() {
        Map<Long, BigDecimal> typeIdToPriceMap = new HashMap<Long, BigDecimal>();
        for (Map.Entry<Long, EditableCalculationPriceSetItem> mapEntry : typeIdToEditableCalculationPriceSetItemMap.entrySet()) {
            ComputableCalculationPriceSetItem computableCalculationPriceSetItem = typeIdToComputableCalculationPriceSetItemMap.get(mapEntry.getKey());
            computableCalculationPriceSetItem.getCalculationPriceItem();
            typeIdToPriceMap.put(mapEntry.getKey(), computableCalculationPriceSetItem.getCalculationPriceItem().getPrice());
        }
        return typeIdToPriceMap;
    }

    @Override
    public Map<Long, EditableCalculationPriceSetItem> getTypeIdToEditableCalculationPriceSetItemMap() {
        return typeIdToEditableCalculationPriceSetItemMap;
    }

    @Override
    public Map<Long, ComputableCalculationPriceSetItem> getTypeIdToComputableCalculationPriceSetItemMap() {
        return typeIdToComputableCalculationPriceSetItemMap;
    }
}
