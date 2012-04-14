package lv.odylab.evemanage.shared.eve;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Datacore {
    DATACORE_DEFENSIVE_SUBSYSTEMS_ENGINEERING(11496L, 333L, "Datacore - Defensive Subsystems Engineering", SkillForCalculation.DEFENSIVE_SUBSYSTEM_TECHNOLOGY),
    DATACORE_PROPULSION_SUBSYSTEMS_ENGINEERING(20114L, 333L, "Datacore - Propulsion Subsystems Engineering", SkillForCalculation.PROPULSION_SUBSYSTEM_TECHNOLOGY),
    DATACORE_ENGINEERING_SUBSYSTEMS_ENGINEERING(20115L, 333L, "Datacore - Engineering Subsystems Engineering", SkillForCalculation.ENGINEERING_SUBSYSTEM_TECHNOLOGY),
    DATACORE_ELECTRONIC_SUBSYSTEMS_ENGINEERING(20116L, 333L, "Datacore - Electronic Subsystems Engineering", SkillForCalculation.ELECTRONIC_SUBSYSTEM_TECHNOLOGY),
    DATACORE_HYDROMAGNETIC_PHYSICS(20171L, 333L, "Datacore - Hydromagnetic Physics", SkillForCalculation.HYDROMAGNETIC_PHYSICS),
    DATACORE_MINMATAR_STARSHIP_ENGINEERING(20172L, 333L, "Datacore - Minmatar Starship Engineering", SkillForCalculation.MINMATAR_STARSHIP_ENGINEERING),
    DATACORE_GALLENTEAN_STARSHIP_ENGINEERING(20410L, 333L, "Datacore - Gallentean Starship Engineering", SkillForCalculation.GALLENTEAN_STARSHIP_ENGINEERING),
    DATACORE_HIGH_ENERGY_PHYSICS(20411L, 333L, "Datacore - High Energy Physics", SkillForCalculation.HIGH_ENERGY_PHYSICS),
    DATACORE_PLASMA_PHYSICS(20412L, 333L, "Datacore - Plasma Physics", SkillForCalculation.PLASMA_PHYSICS),
    DATACORE_LASER_PHYSICS(20413L, 333L, "Datacore - Laser Physics", SkillForCalculation.LASER_PHYSICS),
    DATACORE_QUANTUM_PHYSICS(20414L, 333L, "Datacore - Quantum Physics", SkillForCalculation.QUANTUM_PHYSICS),
    DATACORE_MOLECULAR_ENGINEERING(20415L, 333L, "Datacore - Molecular Engineering", SkillForCalculation.MOLECULAR_ENGINEERING),
    DATACORE_NANITE_ENGINEERING(20416L, 333L, "Datacore - Nanite Engineering", SkillForCalculation.NANITE_ENGINEERING),
    DATACORE_ELECTROMAGNETIC_PHYSICS(20417L, 333L, "Datacore - Electromagnetic Physics", SkillForCalculation.ELECTROMAGNETIC_PHYSICS),
    DATACORE_ELECTRONIC_ENGINEERING(20418L, 333L, "Datacore - Electronic Engineering", SkillForCalculation.ELECTRONIC_ENGINEERING),
    DATACORE_GRAVITON_PHYSICS(20419L, 333L, "Datacore - Graviton Physics", SkillForCalculation.GRAVITON_PHYSICS),
    DATACORE_ROCKET_SCIENCE(20420L, 333L, "Datacore - Rocket Science", SkillForCalculation.ROCKET_SCIENCE),
    DATACORE_AMARRIAN_STARSHIP_ENGINEERING(20421L, 333L, "Datacore - Amarrian Starship Engineering", SkillForCalculation.AMARRIAN_STARSHIP_ENGINEERING),
    DATACORE_NUCLEAR_PHYSICS(20423L, 333L, "Datacore - Nuclear Physics", SkillForCalculation.NUCLEAR_PHYSICS),
    DATACORE_MECHANICAL_ENGINEERING(20424L, 333L, "Datacore - Mechanical Engineering", SkillForCalculation.MECHANICAL_ENGINEERING),
    DATACORE_OFFENSIVE_SUBSYSTEMS_ENGINEERING(20425L, 333L, "Datacore - Offensive Subsystems Engineering", SkillForCalculation.OFFENSIVE_SUBSYSTEM_TECHNOLOGY),
    DATACORE_CALDARI_STARSHIP_ENGINEERING(25887L, 333L, "Datacore - Caldari Starship Engineering", SkillForCalculation.CALDARI_STARSHIP_ENGINEERING);

    private final Long typeID;
    private final Long groupID;
    private final String typeName;
    private final SkillForCalculation skillForCalculation;

    private static Map<Long, Datacore> typeIdToDatacoreMap;

    static {
        Map<Long, Datacore> typeIdToDatacoreMap = new HashMap<Long, Datacore>();
        for (Datacore datacore : values()) {
            typeIdToDatacoreMap.put(datacore.getTypeID(), datacore);
        }
        Datacore.typeIdToDatacoreMap = Collections.unmodifiableMap(typeIdToDatacoreMap);
    }

    private Datacore(Long typeID, Long groupID, String typeName, SkillForCalculation skillForCalculation) {
        this.typeID = typeID;
        this.groupID = groupID;
        this.typeName = typeName;
        this.skillForCalculation = skillForCalculation;
    }

    public Long getTypeID() {
        return typeID;
    }

    public Long getGroupID() {
        return groupID;
    }

    public String getTypeName() {
        return typeName;
    }

    public SkillForCalculation getSkillForCalculation() {
        return skillForCalculation;
    }

    public static Datacore getByTypeID(Long typeID) {
        return typeIdToDatacoreMap.get(typeID);
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
  it.groupID = 333 and -- Datacores
  it.published = 1
ORDER BY it.typeID
 */
