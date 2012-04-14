package lv.odylab.evemanage.shared.eve;

import java.util.Arrays;
import java.util.List;

public enum RacialDecryptors {
    AMARR(Decryptor.CIRCULAR_LOGIC, Decryptor.SACRED_MANIFESTO, Decryptor.FORMATION_LAYOUT, Decryptor.CLASSIC_DOCTRINE, Decryptor.WAR_STRATEGON),
    CALDARI(Decryptor.INTERFACE_ALIGNMENT_CHART, Decryptor.USER_MANUAL, Decryptor.TUNING_INSTRUCTIONS, Decryptor.PROTOTYPE_DIAGRAM, Decryptor.INSTALLATION_GUIDE),
    GALLENTE(Decryptor.SYMBIOTIC_FIGURES, Decryptor.ENGAGEMENT_PLAN, Decryptor.COLLISION_MEASUREMENTS, Decryptor.TEST_REPORTS, Decryptor.STOLEN_FORMULAS),
    MINMATAR(Decryptor.CIRCUITRY_SCHEMATICS, Decryptor.OPERATION_HANDBOOK, Decryptor.CALIBRATION_DATA, Decryptor.ADVANCED_THEORIES, Decryptor.ASSEMBLY_INSTRUCTIONS);

    private final List<Decryptor> decryptors;

    private RacialDecryptors(Decryptor... decryptors) {
        this.decryptors = Arrays.asList(decryptors);
    }

    public List<Decryptor> getDecryptors() {
        return decryptors;
    }
}
