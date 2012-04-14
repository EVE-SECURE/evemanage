package lv.odylab.evemanage.domain;

import com.googlecode.objectify.ObjectifyFactory;
import lv.odylab.evemanage.domain.blueprint.Blueprint;
import lv.odylab.evemanage.domain.eve.ApiKey;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.eve.CharacterNameID;
import lv.odylab.evemanage.domain.priceset.PriceSet;
import lv.odylab.evemanage.domain.system.SystemProperty;
import lv.odylab.evemanage.domain.user.User;

public class EveManageObjectifyFactory extends ObjectifyFactory {
    public EveManageObjectifyFactory() {
        register(SystemProperty.class);

        register(User.class);
        register(Character.class);
        register(ApiKey.class);
        register(CharacterNameID.class);

        register(Blueprint.class);
        register(PriceSet.class);
    }
}

