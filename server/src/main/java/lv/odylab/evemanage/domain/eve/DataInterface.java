package lv.odylab.evemanage.domain.eve;

import java.util.List;

public enum DataInterface {
    CRYPTIC_DATA_INTERFACE(25553L, 716L, "Cryptic Data Interface", RacialDecryptors.MINMATAR),
    OCCULT_DATA_INTERFACE(25554L, 716L, "Occult Data Interface", RacialDecryptors.AMARR),
    ESOTERIC_DATA_INTERFACE(25555L, 716L, "Esoteric Data Interface", RacialDecryptors.CALDARI),
    INCOGNITO_DATA_INTERFACE(25556L, 716L, "Incognito Data Interface", RacialDecryptors.GALLENTE),
    OCCULT_SHIP_DATA_INTERFACE(25851L, 716L, "Occult Ship Data Interface", RacialDecryptors.AMARR),
    ESOTERIC_SHIP_DATA_INTERFACE(25853L, 716L, "Esoteric Ship Data Interface", RacialDecryptors.CALDARI),
    INCOGNITO_SHIP_DATA_INTERFACE(25855L, 716L, "Incognito Ship Data Interface", RacialDecryptors.GALLENTE),
    CRYPTIC_SHIP_DATA_INTERFACE(25857L, 716L, "Cryptic Ship Data Interface", RacialDecryptors.MINMATAR),
    CRYPTIC_TUNER_DATA_INTERFACE(26597L, 716L, "Cryptic Tuner Data Interface", RacialDecryptors.MINMATAR),
    ESOTERIC_TUNER_DATA_INTERFACE(26599L, 716L, "Esoteric Tuner Data Interface", RacialDecryptors.CALDARI),
    INCOGNITO_TUNER_DATA_INTERFACE(26601L, 716L, "Incognito Tuner Data Interface", RacialDecryptors.GALLENTE),
    OCCULT_TUNER_DATA_INTERFACE(26603L, 716L, "Occult Tuner Data Interface", RacialDecryptors.AMARR);

    private Long typeID;
    private Long groupID;
    private String name;
    private List<Decryptor> decryptors;

    private DataInterface(Long typeID, Long groupID, String name, RacialDecryptors racialDecryptors) {
        this.typeID = typeID;
        this.groupID = groupID;
        this.name = name;
        this.decryptors = racialDecryptors.getDecryptors();
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
  it.groupID = 716
ORDER BY it.typeID
 */