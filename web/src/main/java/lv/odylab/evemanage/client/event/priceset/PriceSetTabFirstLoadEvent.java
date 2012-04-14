package lv.odylab.evemanage.client.event.priceset;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetTabFirstLoadActionResponse;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetNameDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

import java.util.List;

public class PriceSetTabFirstLoadEvent extends PriceSetTabEvent<PriceSetTabFirstLoadEventHandler> {
    public static final Type<PriceSetTabFirstLoadEventHandler> TYPE = new Type<PriceSetTabFirstLoadEventHandler>();

    private List<PriceSetNameDto> priceSetNames;
    private List<PriceSetNameDto> corporationPriceSetNames;
    private List<PriceSetNameDto> alliancePriceSetNames;
    private PriceSetDto priceSet;
    private Integer currentPriceSetNameIndex;
    private List<CharacterNameDto> attachedCharacterNames;
    private List<String> sharingLevels;

    public PriceSetTabFirstLoadEvent(TrackingManager trackingManager, EveManageConstants constants, PriceSetTabFirstLoadActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.priceSetNames = response.getPriceSetNames();
        this.corporationPriceSetNames = response.getCorporationPriceSetNames();
        this.alliancePriceSetNames = response.getAlliancePriceSetNames();
        this.priceSet = response.getPriceSet();
        this.currentPriceSetNameIndex = response.getCurrentPriceSetNameIndex();
        this.attachedCharacterNames = response.getAttachedCharacterNames();
        this.sharingLevels = response.getSharingLevels();
    }

    public List<PriceSetNameDto> getPriceSetNames() {
        return priceSetNames;
    }

    @Override
    public Type<PriceSetTabFirstLoadEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PriceSetTabFirstLoadEventHandler handler) {
        handler.onPriceSetTabFirstLoad(this);

        int priceSetLength = priceSet != null ? priceSet.getItems().size() : 0;
        trackEvent(String.valueOf(priceSetLength));
    }

    public PriceSetDto getPriceSet() {
        return priceSet;
    }

    public List<PriceSetNameDto> getCorporationPriceSetNames() {
        return corporationPriceSetNames;
    }

    public List<PriceSetNameDto> getAlliancePriceSetNames() {
        return alliancePriceSetNames;
    }

    public Integer getCurrentPriceSetNameIndex() {
        return currentPriceSetNameIndex;
    }

    public List<CharacterNameDto> getAttachedCharacterNames() {
        return attachedCharacterNames;
    }

    public List<String> getSharingLevels() {
        return sharingLevels;
    }
}
