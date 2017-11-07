package Final;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import Final.Frame;
import Final.Trial;
import java.security.KeyFactory;
//import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.io.*;
import java.net.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random; 
//import RSA.java;
public class server {


	public static void main(String[] args)  {
	try
	{
	
	ServerSocket ss=new ServerSocket(3090);
	System.out.println("\n\n\nWaiting for Connection");
	// waiting for client Request
	Socket s=ss.accept();
	// accepting client Request
	System.out.println("Connected to Client\n\n");
	




	ObjectInputStream in=new ObjectInputStream(s.getInputStream());
	ObjectOutputStream out=new ObjectOutputStream(s.getOutputStream());
	




	System.out.println("Exchanging Public KEys\n");
	
	// generate public and private keys
    
    KeyPair keyPair = Trial.buildKeyPair();
    PublicKey pubKey = keyPair.getPublic();
    PrivateKey privateKey = keyPair.getPrivate();
    PublicKey clientPubKey ;

	
	System.out.println("Server is Sending Public KEY ");
	Frame frame = new Frame();
	frame.data = pubKey.getEncoded();
	out.writeObject(frame);
	System.out.println("Server has Sent its Public KEY\n\n ");
	

	System.out.println("Server is Recieving Public KEY of Client ");
	frame = (Frame)in.readObject();
	byte[] cpubKey = frame.data;                 
	X509EncodedKeySpec ks = new X509EncodedKeySpec(cpubKey);
	KeyFactory kf= KeyFactory.getInstance("RSA");
	clientPubKey = kf.generatePublic(ks);
	System.out.println("Server has recieved Public KEY of  Client\n\n\n");
	


	String str=null;
	byte [] encrypted = Trial.encrypt(clientPubKey, "This is a secret message");
    frame.data = encrypted;
    out.writeObject(frame);
    frame=(Frame)in.readObject();
    
    encrypted=Trial.decrypt(privateKey, frame.data);
    encrypted = Trial.encrypt(clientPubKey, new String(encrypted));
    frame = new Frame();
    frame.data = encrypted;
    out.writeObject(frame);
    DataInputStream d1= new DataInputStream(System.in);
    while(true){
    		Frame frameHash = new Frame();
    		frameHash=(Frame)in.readObject();
    		String hashCode=new String(frameHash.data);
    		String toBeSent="";
    		byte[] toBeSent1 = Trial.hexStringToByteArray("e04fd020ea3a6910a2d808002b30309d");
    		frame = new Frame();
        	frame.data=toBeSent1;
        	out.writeObject(frame);
        	frame=(Frame)in.readObject();
        	byte[] hashToBeComp=Trial.hexStringToByteArray(Trial.SHA1(new String(Trial.decrypt(privateKey, frame.data))));
        	if(new String(hashToBeComp).equals(hashCode)){
        		if((new String(Trial.decrypt(privateKey, frame.data))).equalsIgnoreCase("bye")){System.out.println("Message from Client : bye. ");break;};
        		System.out.println("Message from Client : "+new String(Trial.decrypt(privateKey, frame.data))+"\n"+"Enter Your Message.");
        	}
        	else{System.out.println("Interruption in the Network ");break;}
        	toBeSent=d1.readLine();
        	
        	frame.data=Trial.encrypt(clientPubKey, toBeSent);
        	out.writeObject(frame);
        	
    }
}
catch(Exception e){System.out.println(e);}

	}}