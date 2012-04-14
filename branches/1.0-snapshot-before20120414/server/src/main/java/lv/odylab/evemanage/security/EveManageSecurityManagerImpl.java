package lv.odylab.evemanage.security;

import com.google.inject.Inject;
import lv.odylab.appengine.aspect.Logging;
import lv.odylab.appengine.repackaged.Base64;
import lv.odylab.evemanage.application.exception.BadSecurityConfigurationException;
import lv.odylab.evemanage.application.exception.EveManageSecurityException;
import lv.odylab.evemanage.domain.system.SystemProperty;
import lv.odylab.evemanage.domain.system.SystemPropertyDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.PrivateKey;
import java.security.PublicKey;

@Logging(logArguments = false)
public class EveManageSecurityManagerImpl implements EveManageSecurityManager {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final SystemPropertyDao systemPropertyDao;
    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    @Inject
    public EveManageSecurityManagerImpl(SystemPropertyDao systemPropertyDao, PublicKey publicKey, PrivateKey privateKey) {
        this.systemPropertyDao = systemPropertyDao;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    @Override
    public void initializeSecuritySystem() {
        logger.info("Initializing security system");
        String systemPublicKeyBase64 = systemPropertyDao.getProperty(SystemProperty.SYSTEM_PUBLIC_KEY_BASE64);
        logger.info("systemPublicKeyBase64: {}", systemPublicKeyBase64);
        if (systemPublicKeyBase64 == null) {
            initializeSecuritySystemForFirstTime();
            systemPublicKeyBase64 = systemPropertyDao.getProperty(SystemProperty.SYSTEM_PUBLIC_KEY_BASE64);
        }
        checkInitializedSecuritySystem(systemPublicKeyBase64);
    }

    private void initializeSecuritySystemForFirstTime() {
        logger.info("Initializing security system for the first time");
        String systemPublicKeyBase64 = Base64.encodeBytes(publicKey.getEncoded());
        systemPropertyDao.put(SystemProperty.SYSTEM_PUBLIC_KEY_BASE64, systemPublicKeyBase64);
        logger.info("Setting system property systemPublicKeyBase64: {}", systemPublicKeyBase64);
    }

    private void checkInitializedSecuritySystem(String systemPublicKeyBase64) {
        logger.info("Checking security system configuration (ensuring that system is using correct key pair)");
        String currentPublicKeyBase64 = Base64.encodeBytes(publicKey.getEncoded());
        if (!systemPublicKeyBase64.equals(currentPublicKeyBase64)) {
            throw new BadSecurityConfigurationException("System property public key differs from currently used, halting");
        }
        try {
            decrypt(encrypt("testString".getBytes()));
        } catch (EveManageSecurityException e) {
            throw new BadSecurityConfigurationException("Unable to encrypt and decrypt test string, halting");
        }
        logger.info("Security system is configured correctly");
        checkIfUsingDefaultKeys(systemPublicKeyBase64);
    }

    private void checkIfUsingDefaultKeys(String systemPublicKeyBase64) {
        String defaultPublicKeyBase64 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgsbxBvb1/A8P2prbmRC8gZaloRF1a4bGWyb/CYS4LktnlNeBBQQkIiGvg8prXgNjoETnBGOOC6299uov/56NhGJbAwHrJKLj00F44pLSPsJfvJ5guKzTE/IoFUoUoh0LTHj7qxSIMPm55gD+C1faL8TqbXuNi10656qGUeMtXQnnjhIPFdA3Z2G39I2meJdIpvWFbCA+n8byCrbHM4a413mq+u9kFGCVDNNJhnQ0aJ6PxK+Eh9RycFNAe+/F4FLT/WU0bfU49O7f9U+0JS2Ki+vDJ9ORmhparruZJ0nNUldHlqCdmjv51oGQUozys97oxGM0JcgVR9xEtnw9/clqvwIDAQAB";
        if (defaultPublicKeyBase64.equals(systemPublicKeyBase64)) {
            logger.warn("!!! Using default key pair, THIS IS NOT SECURE !!!");
        } else {
            logger.warn("Using non-default key pair, this is secure. Keep your private key safe!");
        }
    }

    @Override
    public byte[] encrypt(byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new EveManageSecurityException(e);
        }
    }

    @Override
    public byte[] decrypt(byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new EveManageSecurityException(e);
        }
    }

    @Override
    public String decodeUrlString(String string) {
        if (string == null) {
            return null;
        }
        try {
            return URLDecoder.decode(string, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
