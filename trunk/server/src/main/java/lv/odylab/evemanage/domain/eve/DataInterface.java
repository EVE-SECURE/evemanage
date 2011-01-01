package lv.odylab.evemanage.domain.eve;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum DataInterface {
    CRYPTIC_DATA_INTERFACE(25553L, 716L, "Cryptic Data Interface", RacialDecryptors.MINMATAR, SkillForCalculation.MINMATAR_ENCRYPTION_METHODS),
    OCCULT_DATA_INTERFACE(25554L, 716L, "Occult Data Interface", RacialDecryptors.AMARR, SkillForCalculation.AMARR_ENCRYPTION_METHODS),
    ESOTERIC_DATA_INTERFACE(25555L, 716L, "Esoteric Data Interface", RacialDecryptors.CALDARI, SkillForCalculation.CALDARI_ENCRYPTION_METHODS),
    INCOGNITO_DATA_INTERFACE(25556L, 716L, "Incognito Data Interface", RacialDecryptors.GALLENTE, SkillForCalculation.GALLENTE_ENCRYPTION_METHODS),
    OCCULT_SHIP_DATA_INTERFACE(25851L, 716L, "Occult Ship Data Interface", RacialDecryptors.AMARR, SkillForCalculation.AMARR_ENCRYPTION_METHODS),
    ESOTERIC_SHIP_DATA_INTERFACE(25853L, 716L, "Esoteric Ship Data Interface", RacialDecryptors.CALDARI, SkillForCalculation.CALDARI_ENCRYPTION_METHODS),
    INCOGNITO_SHIP_DATA_INTERFACE(25855L, 716L, "Incognito Ship Data Interface", RacialDecryptors.GALLENTE, SkillForCalculation.GALLENTE_ENCRYPTION_METHODS),
    CRYPTIC_SHIP_DATA_INTERFACE(25857L, 716L, "Cryptic Ship Data Interface", RacialDecryptors.MINMATAR, SkillForCalculation.MINMATAR_ENCRYPTION_METHODS),
    CRYPTIC_TUNER_DATA_INTERFACE(26597L, 716L, "Cryptic Tuner Data Interface", RacialDecryptors.MINMATAR, SkillForCalculation.MINMATAR_ENCRYPTION_METHODS),
    ESOTERIC_TUNER_DATA_INTERFACE(26599L, 716L, "Esoteric Tuner Data Interface", RacialDecryptors.CALDARI, SkillForCalculation.CALDARI_ENCRYPTION_METHODS),
    INCOGNITO_TUNER_DATA_INTERFACE(26601L, 716L, "Incognito Tuner Data Interface", RacialDecryptors.GALLENTE, SkillForCalculation.GALLENTE_ENCRYPTION_METHODS),
    OCCULT_TUNER_DATA_INTERFACE(26603L, 716L, "Occult Tuner Data Interface", RacialDecryptors.AMARR, SkillForCalculation.AMARR_ENCRYPTION_METHODS);

    private final Long typeID;
    private final Long groupID;
    private final String name;
    private final List<Decryptor> decryptors;
    private final SkillForCalculation encryptionSkill;

    private static Map<Long, DataInterface> typeIdToDataInterfaceMap;

    static {
        Map<Long, DataInterface> typeIdToDataInterfaceMap = new HashMap<Long, DataInterface>();
        for (DataInterface dataInterface : values()) {
            typeIdToDataInterfaceMap.put(dataInterface.getTypeID(), dataInterface);
        }
        DataInterface.typeIdToDataInterfaceMap = Collections.unmodifiableMap(typeIdToDataInterfaceMap);
    }

    private DataInterface(Long typeID, Long groupID, String name, RacialDecryptors racialDecryptors, SkillForCalculation encryptionSkill) {
        this.typeID = typeID;
        this.groupID = groupID;
        this.name = name;
        this.decryptors = racialDecryptors.getDecryptors();
        this.encryptionSkill = encryptionSkill;
    }

    public Long getTypeID() {
        return typeID;
    }

    public Long getGroupID() {
        return groupID;
    }

    public String getName() {
        return name;
    }

    public List<Decryptor> getDecryptors() {
        return decryptors;
    }

    public SkillForCalculation getEncryptionSkill() {
        return encryptionSkill;
    }

    public static DataInterface getByTypeID(Long typeID) {
        return typeIdToDataInterfaceMap.get(typeID);
    }
}

/*
SELECT
  CONCAT(
    REPLACE(UPPER(it.typeName), ' ', '_'),
    '(',
    it.typeID, 'L, ',
    it.groupID, 'L, ',
    '"', it.typeName, '"', '),'
  )
FROM
  invTypes it
WHERE
  it.groupID = 716 -- Data Interfaces
ORDER BY it.typeID
 */