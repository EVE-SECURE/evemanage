package lv.odylab.evemanage.security;

import com.google.inject.Provider;
import lv.odylab.appengine.repackaged.Base64;
import lv.odylab.evemanage.application.exception.EveManageSecurityException;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public class PrivateKeyProvider implements Provider<PrivateKey> {
    private final PrivateKey privateKey;

    public PrivateKeyProvider() {
        try {
            privateKey = readPrivateKeyFromFile("/private.key");
        } catch (IOException e) {
            throw new EveManageSecurityException(e);
        }
    }

    @Override
    public PrivateKey get() {
        return privateKey;
    }

    private PrivateKey readPrivateKeyFromFile(String fileName) throws IOException {
        InputStream base64InputStream = new Base64.InputStream(getClass().getResourceAsStream(fileName));
        byte[] data = new byte[base64InputStream.available()];
        base64InputStream.read(data);
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(data);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(privateKeySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new EveManageSecurityException(e);
        } catch (InvalidKeySpecException e) {
            throw new EveManageSecurityException(e);
        }
    }
}
