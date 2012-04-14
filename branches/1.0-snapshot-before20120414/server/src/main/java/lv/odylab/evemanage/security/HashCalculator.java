package lv.odylab.evemanage.security;

public interface HashCalculator {

    String hashApiKey(Long apiKeyUserID, String apiKeyString);

}
