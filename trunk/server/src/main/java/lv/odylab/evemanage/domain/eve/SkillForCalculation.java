package lv.odylab.evemanage.domain.eve;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public enum SkillForCalculation {
    INDUSTRY(3380L, 268L, "Industry"),
    PRODUCTION_EFFICIENCY(3388L, 268L, "Production Efficiency"),
    SCIENCE(3402L, 270L, "Science"),
    RESEARCH(3403L, 270L, "Research"),
    REVERSE_ENGINEERING(3408L, 270L, "Reverse Engineering"),
    METALLURGY(3409L, 270L, "Metallurgy"),
    HIGH_ENERGY_PHYSICS(11433L, 270L, "High Energy Physics"),
    PLASMA_PHYSICS(11441L, 270L, "Plasma Physics"),
    NANITE_ENGINEERING(11442L, 270L, "Nanite Engineering"),
    HYDROMAGNETIC_PHYSICS(11443L, 270L, "Hydromagnetic Physics"),
    AMARRIAN_STARSHIP_ENGINEERING(11444L, 270L, "Amarrian Starship Engineering"),
    MINMATAR_STARSHIP_ENGINEERING(11445L, 270L, "Minmatar Starship Engineering"),
    GRAVITON_PHYSICS(11446L, 270L, "Graviton Physics"),
    LASER_PHYSICS(11447L, 270L, "Laser Physics"),
    ELECTROMAGNETIC_PHYSICS(11448L, 270L, "Electromagnetic Physics"),
    ROCKET_SCIENCE(11449L, 270L, "Rocket Science"),
    GALLENTEAN_STARSHIP_ENGINEERING(11450L, 270L, "Gallentean Starship Engineering"),
    NUCLEAR_PHYSICS(11451L, 270L, "Nuclear Physics"),
    MECHANICAL_ENGINEERING(11452L, 270L, "Mechanical Engineering"),
    ELECTRONIC_ENGINEERING(11453L, 270L, "Electronic Engineering"),
    CALDARI_STARSHIP_ENGINEERING(11454L, 270L, "Caldari Starship Engineering"),
    QUANTUM_PHYSICS(11455L, 270L, "Quantum Physics"),
    ASTRONAUTIC_ENGINEERING(11487L, 270L, "Astronautic Engineering"),
    MOLECULAR_ENGINEERING(11529L, 270L, "Molecular Engineering"),
    HACKING(21718L, 270L, "Hacking"),
    CALDARI_ENCRYPTION_METHODS(21790L, 270L, "Caldari Encryption Methods"),
    MINMATAR_ENCRYPTION_METHODS(21791L, 270L, "Minmatar Encryption Methods"),
    AMARR_ENCRYPTION_METHODS(23087L, 270L, "Amarr Encryption Methods"),
    GALLENTE_ENCRYPTION_METHODS(23121L, 270L, "Gallente Encryption Methods"),
    DEFENSIVE_SUBSYSTEM_TECHNOLOGY(30324L, 270L, "Defensive Subsystem Technology"),
    ENGINEERING_SUBSYSTEM_TECHNOLOGY(30325L, 270L, "Engineering Subsystem Technology"),
    ELECTRONIC_SUBSYSTEM_TECHNOLOGY(30326L, 270L, "Electronic Subsystem Technology"),
    OFFENSIVE_SUBSYSTEM_TECHNOLOGY(30327L, 270L, "Offensive Subsystem Technology"),
    PROPULSION_SUBSYSTEM_TECHNOLOGY(30788L, 270L, "Propulsion Subsystem Technology");

    private Long typeID;
    private Long groupID;
    private String name;

    private static Map<Long, SkillForCalculation> typeIdToSkillMap = new HashMap<Long, SkillForCalculation>();
    private static Set<Long> typeIdSet = new HashSet<Long>();

    static {
        for (SkillForCalculation skillForCalculation : values()) {
            typeIdToSkillMap.put(skillForCalculation.getTypeID(), skillForCalculation);
            typeIdSet.add(skillForCalculation.getTypeID());
        }
    }

    private SkillForCalculation(Long typeID, Long groupID, String name) {
        this.typeID = typeID;
        this.groupID = groupID;
        this.name = name;
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

    public static SkillForCalculation getByTypeID(Long typeID) {
        return typeIdToSkillMap.get(typeID);
    }

    public static Boolean exists(Long typeID) {
        return typeIdSet.contains(typeID);
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
  it.groupID IN (268, 270)
ORDER BY it.typeID
*/