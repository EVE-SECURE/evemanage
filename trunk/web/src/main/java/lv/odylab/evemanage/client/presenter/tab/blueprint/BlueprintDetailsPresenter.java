package lv.odylab.evemanage.client.presenter.tab.blueprint;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDetailsDto;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDto;

public class BlueprintDetailsPresenter {

    public interface Display extends BlueprintDetailsDisplay {

    }

    private Display display;

    @Inject
    public BlueprintDetailsPresenter(Display display) {
        this.display = display;
    }

    public ComputableBlueprintDetails go(FlexTable detailsTable, BlueprintDetailsDto details, BlueprintDto blueprint) {
        return display.attach(detailsTable, details, blueprint);
    }
}
