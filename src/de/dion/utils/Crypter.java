package de.dion.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class Crypter {
	
	private static SecretKey newKey;
	private static SecretKey oldKey;
	private static String pw = null;
	private static final Charset UTF8 = Charset.forName("UTF8");
	

	public static byte[] encrypt(byte[] data) throws Exception {
		Cipher c = Cipher.getInstance("AES");
		c.init(Cipher.ENCRYPT_MODE, newKey);
		byte[] encVal = c.doFinal(data);
		return encVal;
	}

	
	public static byte[] decrypt(byte[] encryptedData) throws Exception {
		Cipher c = Cipher.getInstance("AES");
		c.init(Cipher.DECRYPT_MODE, newKey);
		byte[] decValue = c.doFinal(encryptedData);
		return decValue;
	}
	
	/**
	 * Erstellt einen Sha256 gehashten <b>AES256</b> Schlüssel mit der neuen Methode.
	 * */
	public static void genKey(String pw) {
		if(Crypter.pw == null || !Crypter.pw.equals(pw)) {
			Crypter.pw = pw;
			
			try {
				byte[] hashedPassword = hashPassword(pw);
				pw = new String(hashedPassword, UTF8);
				
				SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
				PBEKeySpec spec = new PBEKeySpec(pw.toCharArray(), hashedPassword, 197212, 256);
				SecretKey tmp = factory.generateSecret(spec);
				newKey = new SecretKeySpec(tmp.getEncoded(), "AES");
				
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static byte[] hashPassword(String pw) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(pw.getBytes(UTF8));
		return (md.digest());
	}
	
	public static class OldCrypter {
		
		/**
		 * Erstelle einen Schlüssel mit der alten Methode (Buggy)
		 * Macht probleme wenn verschlüssselte Files über verschiedene Betriebssysteme geteilt werden.
		 * */
		public static void genKey(String pw) {
			try {
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update(pw.getBytes());
				pw = new String(md.digest());
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			}
			
			try {
				SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
				PBEKeySpec spec = new PBEKeySpec(pw.toCharArray(), pw.getBytes(), 65536, 256);
				SecretKey tmp = factory.generateSecret(spec);
				oldKey = new SecretKeySpec(tmp.getEncoded(), "AES");
				
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				e.printStackTrace();
			}
		}
		
		public static byte[] decrypt(byte[] encryptedData) throws Exception {
			Cipher c = Cipher.getInstance("AES");
			c.init(Cipher.DECRYPT_MODE, oldKey);
			byte[] decValue = c.doFinal(encryptedData);
			return decValue;
		}
	}
	
}
