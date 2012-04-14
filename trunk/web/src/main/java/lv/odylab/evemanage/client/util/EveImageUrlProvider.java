package lv.odylab.evemanage.client.util;

import com.google.inject.Inject;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageUrlMessages;

public class EveImageUrlProvider {
    private EveManageConstants constants;
    private EveManageUrlMessages urlMessages;

    @Inject
    public EveImageUrlProvider(EveManageConstants constants, EveManageUrlMessages urlMessages) {
        this.constants = constants;
        this.urlMessages = urlMessages;
    }

    public String getImage16Url(Long itemCategoryID, Long itemTypeID, String graphicName) {
        if (itemCategoryID == 6) {
            return urlMessages.imgShip32Url(constants.eveImagesUrl(), itemTypeID);
        } else if (itemCategoryID == 9) {
            return urlMessages.imgBlueprint64Url(constants.eveImagesUrl(), itemTypeID);
        } else if (itemCategoryID == 18) {
            return urlMessages.imgDrone32Url(constants.eveImagesUrl(), itemTypeID);
        } else if (itemCategoryID == 23) {
            return urlMessages.imgStructures32Url(constants.eveImagesUrl(), itemTypeID);
        } else if (itemCategoryID == 40) {
            return urlMessages.imgSovereigntyStructures32Url(constants.eveImagesUrl(), itemTypeID);
        } else {
            return urlMessages.imgIcon32Url(constants.eveImagesUrl(), itemTypeID);
        }
    }

    public String getImage32Url(Long itemCategoryID, Long itemTypeID, String graphicName) {
        if (itemCategoryID == 6) {
            return urlMessages.imgShip32Url(constants.eveImagesUrl(), itemTypeID);
        } else if (itemCategoryID == 9) {
            return urlMessages.imgBlueprint64Url(constants.eveImagesUrl(), itemTypeID);
        } else if (itemCategoryID == 18) {
            return urlMessages.imgDrone32Url(constants.eveImagesUrl(), itemTypeID);
        } else if (itemCategoryID == 23) {
            return urlMessages.imgStructures32Url(constants.eveImagesUrl(), itemTypeID);
        } else if (itemCategoryID == 40) {
            return urlMessages.imgSovereigntyStructures32Url(constants.eveImagesUrl(), itemTypeID);
        } else {
            return urlMessages.imgIcon32Url(constants.eveImagesUrl(), itemTypeID);
        }
    }

    public String getImage64Url(Long itemCategoryID, Long itemTypeID, String graphicName) {
        if (itemCategoryID == 6) {
            return urlMessages.imgShip64Url(constants.eveImagesUrl(), itemTypeID);
        } else if (itemCategoryID == 9) {
            return urlMessages.imgBlueprint64Url(constants.eveImagesUrl(), itemTypeID);
        } else if (itemCategoryID == 18) {
            return urlMessages.imgDrone64Url(constants.eveImagesUrl(), itemTypeID);
        } else if (itemCategoryID == 23) {
            return urlMessages.imgStructures64Url(constants.eveImagesUrl(), itemTypeID);
        } else if (itemCategoryID == 40) {
            return urlMessages.imgSovereigntyStructures64Url(constants.eveImagesUrl(), itemTypeID);
        } else {
            return urlMessages.imgIcon64Url(constants.eveImagesUrl(), itemTypeID);
        }
    }

    public String getBlueprintImageUrl(Long itemTypeID) {
        return urlMessages.imgBlueprint64Url(constants.eveImagesUrl(), itemTypeID);
    }
}
