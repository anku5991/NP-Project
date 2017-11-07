package Final;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.io.UnsupportedEncodingException; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 


import javax.crypto.Cipher;

public class Trial {
    
    //public static void main(String [] args) throws Exception {
        // generate public and private keys
        /*KeyPair keyPair = buildKeyPair();
        PublicKey pubKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();*/
        
        // encrypt the message
        /*byte [] encrypted = encrypt(privateKey, "This is a secret message");     
        System.out.println(new String(encrypted)); */ // <<encrypted message>>
        
        // decrypt the message
        //byte[] secret = decrypt(pubKey, encrypted);                                 
        //System.out.println(new String(secret));     // This is a secret message
   // }
        public static byte[] hexStringToByteArray(String s) {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                             + Character.digit(s.charAt(i+1), 16));
    }
    return data;
}

    public static KeyPair buildKeyPair() throws NoSuchAlgorithmException {
        final int keySize = 2048;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);      
        return keyPairGenerator.genKeyPair();
    }

    public static byte[] encrypt(PublicKey publicKey, String message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);  

        return cipher.doFinal(message.getBytes());  
    }
    /*public static byte[] encrypt(PublicKey publicKey, int message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);  

        return cipher.doFinal(message.getBytes());  
    }*/
    
    public static byte[] decrypt(PrivateKey privateKey, byte [] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        
        return cipher.doFinal(encrypted);
    }


    public static String toString(int temp,String str) throws Exception {
        char ch;
        str="";
        while(temp!=0){
            ch=((char)(temp%10+97));
            if(ch=='a')str="a"+str;
            if(ch=='b')str="b"+str;
            if(ch=='c')str="c"+str;
            if(ch=='d')str="d"+str;
            if(ch=='e')str="e"+str;
            if(ch=='f')str="f"+str;
            if(ch=='g')str="g"+str;
            if(ch=='h')str="h"+str;
            if(ch=='i')str="i"+str;
            if(ch=='j')str="j"+str;

            temp=temp/10;
            //System.out.println(str);
        }
        return str;
    }
    /*public static String stringDigest(String data) {
        //System.out.println("SHAHashDemo.stringDigest");
        //String data = "This is just a simple data message for SHA digest demo.";
        String digest = new DigestUtils(SHA_224).digestAsHex(data);
       // String digest = new String(Hex.encodeHex(DigestUtils.sha(data)));
        //String  = DigestUtils.sha1Hex(data);
        System.out.println("Digest          = " + digest);
        System.out.println("Digest.length() = " + digest.length());
        return digest;
    }*/
    private static String convertToHex(byte[] data) { 
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) { 
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do { 
                if ((0 <= halfbyte) && (halfbyte <= 9)) 
                    buf.append((char) ('0' + halfbyte));
                else 
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        } 
        return buf.toString();
    } 
    public static String SHA1(String text) 
    throws NoSuchAlgorithmException, UnsupportedEncodingException  { 
    MessageDigest md;
    md = MessageDigest.getInstance("SHA-1");
    byte[] sha1hash = new byte[40];
    md.update(text.getBytes("iso-8859-1"), 0, text.length());
    sha1hash = md.digest();
    return convertToHex(sha1hash);
    }

}