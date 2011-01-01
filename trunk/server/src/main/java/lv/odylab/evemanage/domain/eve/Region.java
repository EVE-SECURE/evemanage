package lv.odylab.evemanage.domain.eve;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Region {
    DERELIK(10000001L, "Derelik"),
    THE_FORGE(10000002L, "The Forge"),
    VALE_OF_THE_SILENT(10000003L, "Vale of the Silent"),
    DETORID(10000005L, "Detorid"),
    WICKED_CREEK(10000006L, "Wicked Creek"),
    CACHE(10000007L, "Cache"),
    SCALDING_PASS(10000008L, "Scalding Pass"),
    INSMOTHER(10000009L, "Insmother"),
    TRIBUTE(10000010L, "Tribute"),
    GREAT_WILDLANDS(10000011L, "Great Wildlands"),
    CURSE(10000012L, "Curse"),
    MALPAIS(10000013L, "Malpais"),
    CATCH(10000014L, "Catch"),
    VENAL(10000015L, "Venal"),
    LONETREK(10000016L, "Lonetrek"),
    THE_SPIRE(10000018L, "The Spire"),
    TASH_MURKON(10000020L, "Tash-Murkon"),
    OUTER_PASSAGE(10000021L, "Outer Passage"),
    STAIN(10000022L, "Stain"),
    PURE_BLIND(10000023L, "Pure Blind"),
    IMMENSEA(10000025L, "Immensea"),
    ETHERIUM_REACH(10000027L, "Etherium Reach"),
    MOLDEN_HEATH(10000028L, "Molden Heath"),
    GEMINATE(10000029L, "Geminate"),
    HEIMATAR(10000030L, "Heimatar"),
    IMPASS(10000031L, "Impass"),
    SINQ_LAISON(10000032L, "Sinq Laison"),
    THE_CITADEL(10000033L, "The Citadel"),
    THE_KALEVALA_EXPANSE(10000034L, "The Kalevala Expanse"),
    DEKLEIN(10000035L, "Deklein"),
    DEVOID(10000036L, "Devoid"),
    EVERYSHORE(10000037L, "Everyshore"),
    THE_BLEAK_LANDS(10000038L, "The Bleak Lands"),
    ESOTERIA(10000039L, "Esoteria"),
    OASA(10000040L, "Oasa"),
    SYNDICATE(10000041L, "Syndicate"),
    METROPOLIS(10000042L, "Metropolis"),
    DOMAIN(10000043L, "Domain"),
    SOLITUDE(10000044L, "Solitude"),
    TENAL(10000045L, "Tenal"),
    FADE(10000046L, "Fade"),
    PROVIDENCE(10000047L, "Providence"),
    PLACID(10000048L, "Placid"),
    KHANID(10000049L, "Khanid"),
    QUERIOUS(10000050L, "Querious"),
    CLOUD_RING(10000051L, "Cloud Ring"),
    KADOR(10000052L, "Kador"),
    COBALT_EDGE(10000053L, "Cobalt Edge"),
    ARIDIA(10000054L, "Aridia"),
    BRANCH(10000055L, "Branch"),
    FEYTHABOLIS(10000056L, "Feythabolis"),
    OUTER_RING(10000057L, "Outer Ring"),
    FOUNTAIN(10000058L, "Fountain"),
    PARAGON_SOUL(10000059L, "Paragon Soul"),
    DELVE(10000060L, "Delve"),
    TENERIFIS(10000061L, "Tenerifis"),
    OMIST(10000062L, "Omist"),
    PERIOD_BASIS(10000063L, "Period Basis"),
    ESSENCE(10000064L, "Essence"),
    KOR_AZOR(10000065L, "Kor-Azor"),
    PERRIGEN_FALLS(10000066L, "Perrigen Falls"),
    GENESIS(10000067L, "Genesis"),
    VERGE_VENDOR(10000068L, "Verge Vendor"),
    BLACK_RISE(10000069L, "Black Rise");

    private final Long regionID;
    private final String name;

    private static Map<Long, Region> regionIdToRegionMap;

    static {
        Map<Long, Region> regionIdToRegionMap = new HashMap<Long, Region>();
        for (Region region : values()) {
            regionIdToRegionMap.put(region.getRegionID(), region);
        }
        Region.regionIdToRegionMap = Collections.unmodifiableMap(regionIdToRegionMap);
    }

    private Region(Long regionID, String name) {
        this.regionID = regionID;
        this.name = name;
    }

    public Long getRegionID() {
        return regionID;
    }

    public String getName() {
        return name;
    }

    public static Region getByRegionID(Long regionID) {
        return regionIdToRegionMap.get(regionID);
    }
}

/*
SELECT
  CONCAT(
    REPLACE(UPPER(regionName), ' ', '_'),
    '(',
    regionID, 'L, ',
    '"', regionName, '"', '),'
  )
FROM
  mapRegions
ORDER BY regionID
 */
