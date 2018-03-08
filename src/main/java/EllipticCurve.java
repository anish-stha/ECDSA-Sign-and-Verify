import java.io.*;
import java.math.BigInteger;
import java.util.Scanner;

public class EllipticCurve {

    private BigInteger p;
    private BigInteger n;
    private BigInteger seed;
    private BigInteger a = BigInteger.valueOf(-3);
    private BigInteger c;
    private BigInteger b;
    private BigInteger[] xyG;
    private BigInteger xG;
    private BigInteger yG;

    /*Constructor of EC which reads the EC parameters and sets it to its fields*/
    public EllipticCurve() {
        System.out.println("Initializing Elliptic Curve .......");
        //Get file from resources folder
        //File f = new File("/data.txt");

        //Scanner s = new Scanner(f);

        // For p192
        p = new BigInteger("6277101735386680763835789423207666416083908700390324961279");
        n = new BigInteger("6277101735386680763835789423176059013767194773182842284081");
        seed = new BigInteger("3045ae6fc8422f64ed579528d38120eae12196d5", 16);
        c = new BigInteger("3099d2bbbfcb2538542dcd5fb078b6ef5f3d6fe2c745de65", 16);
        b = new BigInteger("64210519e59c80e70fa7e9ab72243049feb8deecc146b9b1", 16);
        xG = new BigInteger("188da80eb03090f67cbf20eb43a18800f4ff0afd82ff1012", 16);
        yG = new BigInteger("07192b95ffc8da78631011ed6b24cdd573f977a11e794811", 16);
        xyG = new BigInteger[2];
        xyG[0] = xG;
        xyG[1] = yG;
        // s.close();
        System.out.println("Elliptic Curve INITIALIZED");
    }

    /* Getters*/
    public BigInteger getP() {return p;}
    public BigInteger getN() {return n;}
    public BigInteger getSeed() {return seed;}
    public BigInteger getA() {return a;}
    public BigInteger getB() {return b;}
    public BigInteger getC() {return c;}
    public BigInteger[] getXyG() {return xyG;}
    public BigInteger getXG() {return xG;}
    public BigInteger getyG() {return yG;}
}