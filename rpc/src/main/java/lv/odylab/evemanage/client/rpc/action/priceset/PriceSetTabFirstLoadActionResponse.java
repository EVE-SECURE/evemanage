package lv.odylab.evemanage.client.rpc.action.priceset;

import lv.odylab.evemanage.client.rpc.action.Response;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetNameDto;

import java.util.List;

public class PriceSetTabFirstLoadActionResponse implements Response {
    private List<PriceSetNameDto> priceSetNames;
    private List<PriceSetNameDto> corporationPriceSetNames;
    private List<PriceSetNameDto> alliancePriceSetNames;
    private PriceSetDto priceSet;
    private Integer currentPriceSetNameIndex;
    private List<CharacterNameDto> attachedCharacterNames;
    private List<String> sharingLevels;

    public List<PriceSetNameDto> getPriceSetNames() {
        return priceSetNames;
    }

    public void setPriceSetNames(List<PriceSetNameDto> priceSetNames) {
        this.priceSetNames = priceSetNames;
    }

    public List<PriceSetNameDto> getCorporationPriceSetNames() {
        return corporationPriceSetNames;
    }

    public void setCorporationPriceSetNames(List<PriceSetNameDto> corporationPriceSetNames) {
        this.corporationPriceSetNames = corporationPriceSetNames;
    }

    public List<PriceSetNameDto> getAlliancePriceSetNames() {
        return alliancePriceSetNames;
    }

    public void setAlliancePriceSetNames(List<PriceSetNameDto> alliancePriceSetNames) {
        this.alliancePriceSetNames = alliancePriceSetNames;
    }

    public PriceSetDto getPriceSet() {
        return priceSet;
    }

    public void setPriceSet(PriceSetDto priceSet) {
        this.priceSet = priceSet;
    }

    public Integer getCurrentPriceSetNameIndex() {
        return currentPriceSetNameIndex;
    }

    public void setCurrentPriceSetNameIndex(Integer currentPriceSetNameIndex) {
        this.currentPriceSetNameIndex = currentPriceSetNameIndex;
    }

    public List<CharacterNameDto> getAttachedCharacterNames() {
        return attachedCharacterNames;
    }

    public void setAttachedCharacterNames(List<CharacterNameDto> attachedCharacterNames) {
        this.attachedCharacterNames = attachedCharacterNames;
    }

    public List<String> getSharingLevels() {
        return sharingLevels;
    }

    public void setSharingLevels(List<String> sharingLevels) {
        this.sharingLevels = sharingLevels;
    }
}
