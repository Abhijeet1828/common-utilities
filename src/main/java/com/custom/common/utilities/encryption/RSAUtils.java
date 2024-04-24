package com.custom.common.utilities.encryption;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.io.FileUtils;

/**
 * This utility class is used for RSA encryption and decryption functions. It
 * has multiple functions to encrypt, decrypt, generate key-pair, load public
 * and private keys.
 * 
 * @author Abhijeet
 *
 */
public final class RSAUtils {

	private static final String RSA_ALGORITHM = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
	private static final int KEY_SIZE = 2048;

	private RSAUtils() {
		throw new IllegalStateException("RSAUtils class cannot be instantiated");
	}

	/**
	 * This method is used to generate a KeyPair (Public - Private Key) for RSA
	 * encryption.
	 * 
	 * @return {@link KeyPair}
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(KEY_SIZE);
		return keyPairGenerator.generateKeyPair();
	}

	/**
	 * This method is used to encrypt the given string using the given Public key
	 * (String format).
	 * 
	 * @param toBeEncrypted - The String to be encrypted
	 * @param publicKey     - Base64 encoded Public Key in String format
	 * 
	 * @return Encrypted String
	 * 
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidKeySpecException
	 */
	public static String encrypt(String toBeEncrypted, String publicKey)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, InvalidKeySpecException {
		return encryptFinal(toBeEncrypted, loadPublicKey(publicKey));
	}

	/**
	 * This method is used to encrypt the given string using the given Public key
	 * (File format).
	 * 
	 * @param toBeEncrypted - The string to be encrypted
	 * @param publicKey     - Public Key File
	 * 
	 * @return Encrypted String
	 * 
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidKeySpecException
	 * @throws IOException
	 */
	public static String encrypt(String toBeEncrypted, File publicKey)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, InvalidKeySpecException, IOException {
		return encryptFinal(toBeEncrypted, loadPublicKey(publicKey));
	}

	/**
	 * This method is used to decrypt the given encrypted string using the given
	 * Private Key (String format)
	 * 
	 * @param encryptedString - The encrypted string
	 * @param privateKey      - Base64 encoded Private Key in String format
	 * 
	 * @return Decrypted String in Base64 encoded format
	 * 
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidKeySpecException
	 */
	public static String decrypt(String encryptedString, String privateKey)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, InvalidKeySpecException {
		return decryptFinal(encryptedString, loadPrivateKey(privateKey));
	}

	/**
	 * This method is used to decrypt the given encrypted string using the given
	 * Private Key (File format)
	 * 
	 * @param encryptedString - The encrypted string
	 * @param privateKey      - Private Key File
	 * 
	 * @return Decrypted String in Base64 encoded format
	 * 
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidKeySpecException
	 * @throws IOException
	 */
	public static String decrypt(String encryptedString, File privateKey)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, InvalidKeySpecException, IOException {
		return decryptFinal(encryptedString, loadPrivateKey(privateKey));
	}

	/**
	 * This method is used to convert given Base64 encoded public key string into
	 * {@link PublicKey}.
	 * 
	 * @param publicKey - Base64 encoded public key string
	 * 
	 * @return {@link PublicKey}
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PublicKey loadPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey.getBytes());
		return generatePublicKey(publicKeyBytes);
	}

	/**
	 * This method is used to convert given public key file into {@link PublicKey}.
	 * 
	 * @param publicKey - Public key file
	 * 
	 * @return {@link PublicKey}
	 * 
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PublicKey loadPublicKey(File publicKey)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] publicKeyBytes = FileUtils.readFileToByteArray(publicKey);
		return generatePublicKey(publicKeyBytes);
	}

	/**
	 * This method is used to convert given Base64 encoded private key string into
	 * {@link PrivateKey}.
	 * 
	 * @param privateKey - Base64 encoded private key string
	 * 
	 * @return {@link PrivateKey}
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PrivateKey loadPrivateKey(String privateKey)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] privateKeyBytes = Base64.getDecoder().decode(privateKey.getBytes());
		return generatePrivateKey(privateKeyBytes);
	}

	/**
	 * This method is used to convert given private key file into
	 * {@link PrivateKey}.
	 * 
	 * @param privateKey - Private key file
	 * 
	 * @return {@link PrivateKey}
	 * 
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PrivateKey loadPrivateKey(File privateKey)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] privateKeyBytes = FileUtils.readFileToByteArray(privateKey);
		return generatePrivateKey(privateKeyBytes);
	}

	////////////////// PRIVATE HELPER METHODS ////////////////////////////////
	/////////////////////////////////////////////////////////////////////////

	/**
	 * This method is used to convert given byte array of private key into
	 * {@link PrivateKey}.
	 * 
	 * @param privateKeyBytes - Byte array of private key
	 * 
	 * @return {@link PrivateKey}
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	private static PrivateKey generatePrivateKey(byte[] privateKeyBytes)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
		return keyFactory.generatePrivate(keySpec);
	}

	/**
	 * This method is used to convert byte array of public key into
	 * {@link PublicKey}.
	 * 
	 * @param publicKeyBytes - Byte array of public key
	 * 
	 * @return {@link PublicKey}
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	private static PublicKey generatePublicKey(byte[] publicKeyBytes)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
		return keyFactory.generatePublic(keySpec);
	}

	/**
	 * This method is used to encrypt the given string using the given public key.
	 * 
	 * @param toBeEncrypted - String to be encrypted
	 * @param publicKey     - {@link PublicKey}
	 * 
	 * @return Encrypted String
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	private static String encryptFinal(String toBeEncrypted, PublicKey publicKey) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		byte[] encryptedBytes = cipher.doFinal(toBeEncrypted.getBytes(StandardCharsets.UTF_8));
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	/**
	 * This method is used to decrypt the given string using the given private key.
	 * 
	 * @param encryptedString - Encrypted string
	 * @param privateKey      - {@link PrivateKey}
	 * 
	 * @return Decrypted string
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	private static String decryptFinal(String encryptedString, PrivateKey privateKey) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		byte[] encryptedBytes = Base64.getDecoder().decode(encryptedString);
		byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
		return new String(decryptedBytes, StandardCharsets.UTF_8);
	}

}
