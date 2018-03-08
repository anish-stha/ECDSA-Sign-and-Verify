import  java.io.*;
import java.util.Scanner;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;


public class Signature {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        EllipticCurve ellipticCurve = new EllipticCurve();


        BigInteger n = ellipticCurve.getN();
        BigInteger a = ellipticCurve.getA();
        BigInteger[] G = ellipticCurve.getXyG();
        KeyPair kp = new KeyPair(G, n, a);

        Scanner s = new Scanner(System.in);
        System.out.println("Enter your message on one line:");
        String msg = s.nextLine();

        BigInteger[] signature = ECDSAEngine.messageSign(msg, n, G, a, kp.getPrivateKey());

        ECDSAEngine.messageVerify(msg, signature, n, G, a, kp.getPublicKey());
    }

}