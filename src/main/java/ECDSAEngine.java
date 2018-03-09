import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Formatter;

public class ECDSAEngine {

    /*
     * function: messageSign - sign the message using the private key
     * returns: Array kG containing (r,s) - signature
     */
    public static BigInteger[] messageSign(String msg, BigInteger n, BigInteger[] G, BigInteger a, BigInteger pvkd) throws NoSuchAlgorithmException {
        System.out.println("Signing the message ....");
        BigInteger k, kInv, r, e, s, z;
        BigInteger[] kG;

        do {
            do {
                k = BigInteger.valueOf(1);
                kG = ECOperations.pointMultiply(G, n, a, k);
                r = kG[0].mod(n);
            } while (r.compareTo(BigInteger.ZERO) == 0);

            kInv = k.modInverse(n);
            e = new BigInteger(SHAsum(msg.getBytes()), 16);
            z = e.shiftRight(e.bitLength() - n.bitLength());
            s = (kInv.multiply(z.add(pvkd.multiply(r)))).mod(n);
        } while (s.compareTo(BigInteger.ZERO) == 0);

        kG[0] = r;
        kG[1] = s;
        System.out.println("Message SIGNED with Signature:");
        System.out.println(kG);
        return kG;
    }

    /*
     * function: messageVerify - sign the message using the private key
     * returns: boolean  - verification Status
     */

    public static boolean messageVerify(String msg, BigInteger[] sign, BigInteger n, BigInteger[] G, BigInteger a, BigInteger[] pbkQ) throws NoSuchAlgorithmException {
        System.out.println("Verifying Message ......");
        BigInteger r = sign[0], s = sign[1], z;

        if (r.compareTo(n) >= 0) {
            System.out.println(" r : Message NOT VERIFIED");
            return false;
        }
        if (s.compareTo(n) >= 0) {
            System.out.println(" s :Message NOT VERIFIED");
            return false;
        }

        BigInteger e = new BigInteger(SHAsum(msg.getBytes()), 16);
        BigInteger w = s.modInverse(n);
        z = e.shiftRight(e.bitLength() - n.bitLength());
        BigInteger u1 = (z.multiply(w)).mod(n);
        BigInteger u2 = (r.multiply(w)).mod(n);
        BigInteger[] X = ECOperations.pointAddition(ECOperations.pointMultiply(G, n, a, u1), ECOperations.pointMultiply(pbkQ, n, a, u2), n);

        BigInteger v = X[0].mod(n);

        if (v.compareTo(r) == 0) {
            System.out.println("Message VERIFIED");
            return true;
        }

        System.out.println("Message NOT VERIFIED");
        return false;
    }

    public static String SHAsum(byte[] convertme) throws NoSuchAlgorithmException {
        Security.addProvider(new BouncyCastleProvider());
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hashed = messageDigest.digest(convertme);
        String hashedString = null;
        try {
            hashedString = getHexString(hashed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(hashedString);
        return hashedString;
    }

    // Convert byte arrary to hex string
    private static String byteArray2Hex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    private static byte[] getByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    private static String getHexString(byte[] b) throws Exception {
        String result = "";
        for (int i=0; i < b.length; i++) {
            result +=
                    Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
    }
}