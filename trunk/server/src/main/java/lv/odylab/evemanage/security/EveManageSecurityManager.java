package lv.odylab.evemanage.security;

public interface EveManageSecurityManager {

    void initializeSecuritySystem();

    byte[] encrypt(byte[] data);

    byte[] decrypt(byte[] data);

}
