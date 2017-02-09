package pt.ulisboa.tecnico.meic.cmu.Security;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.SignatureException;
import java.util.Base64;
import java.util.List;

public class Utils {
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    /**
     * Compare the request HMAC with computed by the server from the tuple in clean text
     * @param request - HMAC and tuple
     * @param key - Secret key of the user
     * @return true or false
     */
    public static boolean checkHash(Validate request, String key) {
        try {
            return (request.getHmac().equals(calculateRFC2104HMAC(request.getTuple(), key)));
        } catch (SignatureException e) {
            return false;
        }
    }

    /**
     * Compare the timestamp received by the request with the timestamp stored for each user.
     * If the timestamp (in the request message) exist in the list of the corresponding sender
     * the message will be rejected
     *
     * @param request - HMAC and tuple
     * @param senderTimeStamp - User timestamp stored in DB
     * @return true or false
     */
    public static boolean checkSenderTimestamp(Validate request, List<Integer> senderTimeStamp) {
        return (senderTimeStamp.contains(request.getTimestamp()));
    }

    /**
     * Computes RFC 2104-compliant HMAC signature.
     * * @param data
     * The data to be signed.
     *
     * @param key The signing key.
     * @return The Base64-encoded RFC 2104-compliant HMAC signature.
     * @throws java.security.SignatureException when signature generation fails
     */
    private static String calculateRFC2104HMAC(String data, String key) throws java.security.SignatureException {
        String result;
        try {
            // get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);

            // get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);

            // compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(data.getBytes());

            // base64-encode the hmac
            result = Base64.getEncoder().encodeToString(rawHmac);
        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }
}
