package lv.odylab.evemanage.client.view.tab.quickcalculator;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.CcpJsMessages;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.EveManageResources;
import lv.odylab.evemanage.client.EveManageUrlMessages;
import lv.odylab.evemanage.client.oracle.BlueprintTypeSuggestOracle;
import lv.odylab.evemanage.client.presenter.tab.QuickCalculatorTabPresenter;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.ComputableBlueprintInformation;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.EditableBlueprintInformation;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;
import lv.odylab.evemanage.client.util.EveImageUrlProvider;
import lv.odylab.evemanage.client.widget.BatchQuantityLabel;
import lv.odylab.evemanage.client.widget.EveCentralQuicklookLink;
import lv.odylab.evemanage.client.widget.EveItemInfoLink;
import lv.odylab.evemanage.client.widget.EveItemMarketDetailsLink;
import lv.odylab.evemanage.client.widget.EveMetricsItemPriceLink;
import lv.odylab.evemanage.client.widget.OpaqueLoadableBlueprintImage;
import lv.odylab.evemanage.client.widget.PriceLabel;
import lv.odylab.evemanage.client.widget.QuantityLabel;
import lv.odylab.evemanage.client.widget.WasteLabel;

import java.util.ArrayList;
import java.util.List;

public class BlueprintInformationSection implements QuickCalculatorTabPresenter.BlueprintInformationSectionDisplay {
    private EveManageConstants constants;
    private EveManageResources resources;
    private EveManageMessages messages;
    private EveManageUrlMessages urlMessages;
    private CcpJsMessages ccpJsMessages;
    private EveImageUrlProvider imageUrlProvider;

    private FlexTable enterBlueprintTable;
    private Label enterBlueprintNameLabel;
    private SuggestBox enterBlueprintSuggestBox;
    private Button setButton;
    private FlexTable blueprintInfoTable;

    private EditableBlueprintInformation editableBlueprintInformation;
    private ComputableBlueprintInformation computableBlueprintInformation;
    private List<HandlerRegistration> blueprintInformationSectionRegistrationHandlers;

    @Inject
    public BlueprintInformationSection(EveManageConstants constants, EveManageResources resources, EveManageMessages messages, EveManageUrlMessages urlMessages, CcpJsMessages ccpJsMessages, EveImageUrlProvider imageUrlProvider, BlueprintTypeSuggestOracle blueprintTypeSuggestOracle) {
        this.constants = constants;
        this.resources = resources;
        this.messages = messages;
        this.urlMessages = urlMessages;
        this.ccpJsMessages = ccpJsMessages;
        this.imageUrlProvider = imageUrlProvider;

        enterBlueprintTable = new FlexTable();
        enterBlueprintSuggestBox = new SuggestBox(blueprintTypeSuggestOracle);
        enterBlueprintSuggestBox.addStyleName(resources.css().blueprintNameInput());
        enterBlueprintSuggestBox.getTextBox().setEnabled(false);
        enterBlueprintNameLabel = new Label(messages.enterBlueprintName() + ":");
        setButton = new Button(messages.set());
        setButton.setEnabled(false);
        blueprintInfoTable = new FlexTable();

        editableBlueprintInformation = new EditableBlueprintInformation();
        computableBlueprintInformation = new ComputableBlueprintInformation();
        blueprintInformationSectionRegistrationHandlers = new ArrayList<HandlerRegistration>();
    }

    @Override
    public void attach(HasWidgets container) {
        enterBlueprintTable.setWidget(0, 0, enterBlueprintNameLabel);
        enterBlueprintTable.setWidget(0, 1, enterBlueprintSuggestBox);
        enterBlueprintTable.setWidget(0, 2, setButton);
        container.add(enterBlueprintTable);
        container.add(blueprintInfoTable);
    }

    @Override
    public SuggestBox getBlueprintSuggestBox() {
        return enterBlueprintSuggestBox;
    }

    @Override
    public Button getSetButton() {
        return setButton;
    }

    @Override
    public void drawBlueprintInformation(CalculationDto calculation) {
        String blueprintImageUrl = imageUrlProvider.getBlueprintImageUrl(calculation.getBlueprintTypeID());
        Image blueprintImage = new Image(blueprintImageUrl);
        blueprintImage.addStyleName(resources.css().image64());
        EveItemInfoLink blueprintImageItemInfoLink = new EveItemInfoLink(ccpJsMessages, blueprintImage, calculation.getBlueprintTypeID());
        blueprintInfoTable.setWidget(0, 0, blueprintImageItemInfoLink);
        String productImageUrl = imageUrlProvider.getImage64Url(calculation.getProductTypeCategoryID(), calculation.getProductTypeID(), calculation.getProductGraphicIcon());
        Image productImage = new Image(productImageUrl);
        productImage.addStyleName(resources.css().image64());
        EveItemInfoLink productImageItemInfoLink = new EveItemInfoLink(ccpJsMessages, productImage, calculation.getProductTypeID());
        blueprintInfoTable.setWidget(0, 1, productImageItemInfoLink);

        FlexTable blueprintTable = new FlexTable();
        blueprintTable.setWidget(0, 0, new EveItemMarketDetailsLink(constants, urlMessages, ccpJsMessages, calculation.getBlueprintTypeName(), calculation.getBlueprintTypeID()));
        blueprintTable.setWidget(0, 1, new Label(messages.me() + ":"));
        Label meLabel = new Label(String.valueOf(calculation.getMaterialLevel()));
        blueprintTable.setWidget(0, 2, meLabel);
        blueprintTable.setWidget(0, 3, new Label(messages.pe() + ":"));
        Label peLabel = new Label(String.valueOf(calculation.getProductivityLevel()));
        blueprintTable.setWidget(0, 4, peLabel);
        WasteLabel wasteLabel = new WasteLabel(messages);
        blueprintTable.setWidget(0, 5, wasteLabel);
        Button editButton = new Button(messages.edit());
        blueprintTable.setWidget(0, 6, editButton);
        OpaqueLoadableBlueprintImage useAllBlueprintsImage = new OpaqueLoadableBlueprintImage(resources, messages, messages.useAllBlueprints(), messages.stopUsingAllBlueprints());
        useAllBlueprintsImage.addStyleName(resources.css().image16());
        useAllBlueprintsImage.addStyleName(resources.css().cursorHand());
        useAllBlueprintsImage.setOpacity();
        blueprintTable.setWidget(0, 7, useAllBlueprintsImage);
        blueprintInfoTable.setWidget(0, 2, blueprintTable);

        FlexTable productTable = new FlexTable();
        QuantityLabel quantityLabel = new BatchQuantityLabel(1L, calculation.getProductPortionSize());
        productTable.setWidget(0, 0, new EveItemMarketDetailsLink(constants, urlMessages, ccpJsMessages, calculation.getProductTypeName(), calculation.getProductTypeID()));
        productTable.setWidget(0, 1, new Label("x"));
        productTable.setWidget(0, 2, quantityLabel);
        productTable.setWidget(0, 3, new Label("="));
        PriceLabel totalPriceLabel = new PriceLabel(calculation.getPrice());
        totalPriceLabel.addStyleName(resources.css().totalPriceLabel());
        productTable.setWidget(0, 4, totalPriceLabel);
        Image eveCentralImage = new Image(resources.eveCentralIcon());
        eveCentralImage.setTitle(messages.eveCentralQuicklook());
        Image eveMetricsImage = new Image(resources.eveMetricsIcon());
        eveMetricsImage.setTitle(messages.eveMetricsItemPrice());
        productTable.setWidget(0, 5, new EveCentralQuicklookLink(constants, urlMessages, eveCentralImage, calculation.getProductTypeID()));
        productTable.setWidget(0, 6, new EveMetricsItemPriceLink(constants, urlMessages, eveMetricsImage, calculation.getProductTypeCategoryID(), calculation.getProductTypeID()));
        blueprintInfoTable.setWidget(1, 0, productTable);

        FlexTable.FlexCellFormatter imageTableCellFormatter = blueprintInfoTable.getFlexCellFormatter();
        imageTableCellFormatter.setRowSpan(0, 0, 3);
        imageTableCellFormatter.setRowSpan(0, 1, 3);
        imageTableCellFormatter.setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
        imageTableCellFormatter.setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
        imageTableCellFormatter.setVerticalAlignment(0, 2, HasVerticalAlignment.ALIGN_TOP);
        imageTableCellFormatter.setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);

        TextBox meTextBox = new TextBox();
        meTextBox.setValue(meLabel.getText());
        meTextBox.addStyleName(resources.css().mePeInput());
        TextBox peTextBox = new TextBox();
        peTextBox.setValue(peLabel.getText());
        peTextBox.addStyleName(resources.css().mePeInput());
        final DecoratedPopupPanel editBlueprintInfoPopup = new DecoratedPopupPanel(true);
        FlexTable editBlueprintInfoPopupFlexTable = new FlexTable();
        editBlueprintInfoPopupFlexTable.setWidget(0, 0, new Label(messages.materialLevel() + ":"));
        editBlueprintInfoPopupFlexTable.setWidget(0, 1, meTextBox);
        editBlueprintInfoPopupFlexTable.setWidget(1, 0, new Label(messages.productivityLevel() + ":"));
        editBlueprintInfoPopupFlexTable.setWidget(1, 1, peTextBox);
        TextBox quantityTextBox = new TextBox();
        quantityTextBox.setValue("1");
        quantityTextBox.addStyleName(resources.css().quantityInput());
        editBlueprintInfoPopupFlexTable.setWidget(2, 0, new Label(messages.quantity() + ":"));
        editBlueprintInfoPopupFlexTable.setWidget(2, 1, quantityTextBox);
        Button applyButton = new Button(messages.apply());
        editBlueprintInfoPopupFlexTable.setWidget(3, 0, applyButton);
        editBlueprintInfoPopupFlexTable.getFlexCellFormatter().setColSpan(3, 0, 2);
        editBlueprintInfoPopup.setWidget(editBlueprintInfoPopupFlexTable);

        blueprintInformationSectionRegistrationHandlers.add(editButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Widget source = (Widget) event.getSource();
                int left = source.getAbsoluteLeft() + 10;
                int top = source.getAbsoluteTop() + 10;
                editBlueprintInfoPopup.setPopupPosition(left, top);
                editBlueprintInfoPopup.show();
            }
        }));
        blueprintInformationSectionRegistrationHandlers.add(applyButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                editBlueprintInfoPopup.hide();
            }
        }));

        editableBlueprintInformation.setUseAllBlueprintsImage(useAllBlueprintsImage);
        editableBlueprintInformation.setMeLabel(meLabel);
        editableBlueprintInformation.setPeLabel(peLabel);
        editableBlueprintInformation.setQuantityLabel(quantityLabel);
        editableBlueprintInformation.setMeTextBox(meTextBox);
        editableBlueprintInformation.setPeTextBox(peTextBox);
        editableBlueprintInformation.setQuantityTextBox(quantityTextBox);
        editableBlueprintInformation.setApplyButton(applyButton);
        computableBlueprintInformation.setCalculation(calculation);
        computableBlueprintInformation.setWasteLabel(wasteLabel);
        computableBlueprintInformation.setTotalPriceLabel(totalPriceLabel);
    }

    @Override
    public void cleanBlueprintInformation() {
        blueprintInfoTable.removeAllRows();
    }

    @Override
    public EditableBlueprintInformation getEditableBlueprintInformation() {
        return editableBlueprintInformation;
    }

    @Override
    public ComputableBlueprintInformation getComputableBlueprintInformation() {
        return computableBlueprintInformation;
    }

    @Override
    public List<HandlerRegistration> getBlueprintInformationSectionRegistrationHandlers() {
        return blueprintInformationSectionRegistrationHandlers;
    }
}
