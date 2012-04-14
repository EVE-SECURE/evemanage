package lv.odylab.evemanage.security;

import com.google.inject.Provider;
import lv.odylab.appengine.repackaged.Base64;
import lv.odylab.evemanage.application.exception.BadSecurityConfigurationException;
import lv.odylab.evemanage.domain.system.SystemProperty;
import lv.odylab.evemanage.domain.system.SystemPropertyDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import static junit.framework.Assert.assertEquals;
import static lv.odylab.evemanage.security.KeyGenerator.generateKeyPair;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class EveManageSecurityManagerImplTest {
    @Mock
    private SystemPropertyDao systemPropertyDao;

    @Test
    public void testInitializeSecuritySystem_EncryptDecrypt() {
        KeyPair keyPair = generateKeyPair("RSA", 2048);
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        when(systemPropertyDao.getProperty(SystemProperty.SYSTEM_PUBLIC_KEY_BASE64)).thenReturn(Base64.encodeBytes(publicKey.getEncoded()));
        EveManageSecurityManager eveManageSecurityManager = new EveManageSecurityManagerImpl(systemPropertyDao, publicKey, privateKey);
        eveManageSecurityManager.initializeSecuritySystem();

        String testString = "testString";
        byte[] encodedTest = eveManageSecurityManager.encrypt(testString.getBytes());
        byte[] decodedTest = eveManageSecurityManager.decrypt(encodedTest);
        assertEquals(testString, new String(decodedTest));
    }

    @Test
    public void testInitializeSecuritySystem_DefaultKeys() {
        Provider<PublicKey> publicKeyProvider = new PublicKeyProvider();
        Provider<PrivateKey> privateKeyProvider = new PrivateKeyProvider();

        when(systemPropertyDao.getProperty(SystemProperty.SYSTEM_PUBLIC_KEY_BASE64)).thenReturn(Base64.encodeBytes(publicKeyProvider.get().getEncoded()));
        EveManageSecurityManager eveManageSecurityManager = new EveManageSecurityManagerImpl(systemPropertyDao, publicKeyProvider.get(), privateKeyProvider.get());
        eveManageSecurityManager.initializeSecuritySystem();

        String testString = "testString";
        byte[] encodedTest = eveManageSecurityManager.encrypt(testString.getBytes());
        byte[] decodedTest = eveManageSecurityManager.decrypt(encodedTest);
        assertEquals(testString, new String(decodedTest));
    }

    @Test
    public void testInitializeSecuritySystem_initializeSecuritySystemForFirstTime() {
        Provider<PublicKey> publicKeyProvider = new PublicKeyProvider();
        Provider<PrivateKey> privateKeyProvider = new PrivateKeyProvider();

        String systemPublicKeyBase64 = Base64.encodeBytes(publicKeyProvider.get().getEncoded());
        when(systemPropertyDao.getProperty(SystemProperty.SYSTEM_PUBLIC_KEY_BASE64)).thenReturn(null, systemPublicKeyBase64);
        EveManageSecurityManager eveManageSecurityManager = new EveManageSecurityManagerImpl(systemPropertyDao, publicKeyProvider.get(), privateKeyProvider.get());
        eveManageSecurityManager.initializeSecuritySystem();
        verify(systemPropertyDao, times(1)).put(SystemProperty.SYSTEM_PUBLIC_KEY_BASE64, systemPublicKeyBase64);

        String testString = "testString";
        byte[] encodedTest = eveManageSecurityManager.encrypt(testString.getBytes());
        byte[] decodedTest = eveManageSecurityManager.decrypt(encodedTest);
        assertEquals(testString, new String(decodedTest));
    }

    @Test(expected = BadSecurityConfigurationException.class)
    public void testInitializeSecuritySystem_badConfiguration_wrongKey() {
        KeyPair keyPair = generateKeyPair("RSA", 2048);
        Provider<PublicKey> publicKeyProvider = new PublicKeyProvider();
        Provider<PrivateKey> privateKeyProvider = new PrivateKeyProvider();

        String systemPublicKeyBase64 = Base64.encodeBytes(keyPair.getPublic().getEncoded());
        when(systemPropertyDao.getProperty(SystemProperty.SYSTEM_PUBLIC_KEY_BASE64)).thenReturn(systemPublicKeyBase64);
        EveManageSecurityManager eveManageSecurityManager = new EveManageSecurityManagerImpl(systemPropertyDao, publicKeyProvider.get(), privateKeyProvider.get());
        eveManageSecurityManager.initializeSecuritySystem();
    }

    @Test(expected = BadSecurityConfigurationException.class)
    public void testInitializeSecuritySystem_badConfiguration_randomPublicKeyString() {
        Provider<PublicKey> publicKeyProvider = new PublicKeyProvider();
        Provider<PrivateKey> privateKeyProvider = new PrivateKeyProvider();

        when(systemPropertyDao.getProperty(SystemProperty.SYSTEM_PUBLIC_KEY_BASE64)).thenReturn("randomString");
        EveManageSecurityManager eveManageSecurityManager = new EveManageSecurityManagerImpl(systemPropertyDao, publicKeyProvider.get(), privateKeyProvider.get());
        eveManageSecurityManager.initializeSecuritySystem();
    }

    @Test(expected = BadSecurityConfigurationException.class)
    public void testInitializeSecuritySystem_badConfiguration_randomPrivateKeyString() {
        KeyPair keyPair = generateKeyPair("RSA", 2048);
        Provider<PublicKey> publicKeyProvider = new PublicKeyProvider();

        String systemPublicKeyBase64 = Base64.encodeBytes(publicKeyProvider.get().getEncoded());
        when(systemPropertyDao.getProperty(SystemProperty.SYSTEM_PUBLIC_KEY_BASE64)).thenReturn(systemPublicKeyBase64);
        EveManageSecurityManager eveManageSecurityManager = new EveManageSecurityManagerImpl(systemPropertyDao, publicKeyProvider.get(), keyPair.getPrivate());
        eveManageSecurityManager.initializeSecuritySystem();
    }

    @Test
    public void testDefaultKeyConsistency() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Provider<PublicKey> publicKeyProvider = new PublicKeyProvider();
        Provider<PrivateKey> privateKeyProvider = new PrivateKeyProvider();

        String testString = "testString";
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKeyProvider.get());
        byte[] encryptedString = cipher.doFinal(testString.getBytes());

        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKeyProvider.get());
        byte[] decryptedString = cipher.doFinal(encryptedString);

        assertEquals(testString, new String(decryptedString));
    }
}
