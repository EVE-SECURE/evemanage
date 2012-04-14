package lv.odylab.evemanage.client.presenter.tab.blueprint;

import com.google.gwt.user.client.ui.FlexTable;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDetailsDto;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDto;

public interface BlueprintDetailsDisplay {

    ComputableBlueprintDetails attach(FlexTable detailsTable, BlueprintDetailsDto details, BlueprintDto blueprint);

}
