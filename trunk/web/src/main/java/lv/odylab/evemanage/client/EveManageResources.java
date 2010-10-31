package lv.odylab.evemanage.client;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface EveManageResources extends ClientBundle {

    @Source("evemanage.css")
    EveManageCssResource css();

    @Source("images/spinner.gif")
    ImageResource spinnerIcon();

    @Source("images/error.png")
    ImageResource errorIcon();

    @Source("images/user.png")
    ImageResource userIcon();

    @Source("images/dashboard.png")
    ImageResource dashboardTabIcon();

    @Source("images/blueprints.png")
    ImageResource blueprintsTabIcon();

    @Source("images/pricesets.png")
    ImageResource priceSetTabIcon();

    @Source("images/quickcalculator.png")
    ImageResource quickCalculatorTabIcon();

    @Source("images/preferences.png")
    ImageResource preferencesTabIcon();

    @Source("images/about.png")
    ImageResource aboutTabIcon();

    @Source("images/limitedkey.png")
    ImageResource limitedKeyIcon();

    @Source("images/fullkey.png")
    ImageResource fullKeyIcon();

    @Source("images/ok.png")
    ImageResource okIcon();

    @Source("images/nok.png")
    ImageResource nokIcon();

    @Source("images/lab.png")
    ImageResource lab();

    @Source("images/station.png")
    ImageResource station();

    @Source("images/character.png")
    ImageResource character();

    @Source("images/sharing.png")
    ImageResource sharing();

    @Source("images/evecentral.png")
    ImageResource eveCentralIcon16();

    @Source("images/evemetrics.png")
    ImageResource eveMetricsIcon16();

    @Source("images/blueprint.png")
    ImageResource blueprintIcon16();

    @Source("images/dashboard/crystallineCarbonideArmorPlate.png")
    ImageResource dashboardCrystallineCarbonideArmorPlateImage();

    @Source("images/dashboard/crystallineCarbonide.png")
    ImageResource dashboardCrystallineCarbonideImage();

    @Source("images/dashboard/tritanium.png")
    ImageResource dashboardTritaniumImage();

    @Source("images/dashboard/pyerite.png")
    ImageResource dashboardPyeriteImage();

    @Source("images/dashboard/crystallineCarbonideArmorPlateBlueprint.png")
    ImageResource dashboardCrystallineCarbonideArmorPlateBlueprintImage();

    @Source("images/dashboard/damageControlBlueprint.png")
    ImageResource dashboardDamageControlBlueprintImage();

    @Source("images/dashboard/corporation.png")
    ImageResource dashboardCorporationImage();

    @Source("images/dashboard/character.jpg")
    ImageResource dashboardCharacterImage();

}
