package com.custom.common.utilities.encryption;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to encrypt or decrypt strings using AES.
 * 
 * @implNote Intialization Vector can be changed according to different
 *           organizational settings.
 * 
 * @author Abhijeet
 *
 */
public final class AESUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(AESUtils.class);

	public static final int AES_KEY_SIZE = 128;
	public static final String INITIALIZATION_VECTOR = "encryptionIntVec";

	private AESUtils() {
		throw new IllegalStateException("AESUtils class cannot be instantiated");
	}

	/**
	 * This method generates a {@link SecretKey} based on the password and IV.
	 * 
	 * @param password
	 * @param iv
	 * 
	 * @return {@link SecretKey}
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static SecretKey generateSecretKey(String password, byte[] iv)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeySpec spec = new PBEKeySpec(password.toCharArray(), iv, 65536, AES_KEY_SIZE); // AES-128
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		byte[] key = secretKeyFactory.generateSecret(spec).getEncoded();
		return new SecretKeySpec(key, "AES");
	}

	/**
	 * This method is used to encrypt the given string with the given secret key and
	 * initialization vector using AES encryption algorithm.
	 * 
	 * @param toBeEncrypted
	 * @param secretKey
	 * 
	 * @return Encrypted String
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static String encrypt(String toBeEncrypted, String secretKey)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		IvParameterSpec ivParameterSpec = new IvParameterSpec(INITIALIZATION_VECTOR.getBytes(StandardCharsets.UTF_8));
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

		byte[] encrypted = cipher.doFinal(toBeEncrypted.getBytes());
		return Base64.encodeBase64String(encrypted);
	}

	/**
	 * This method is used to decrypt the given encrypted string with the given
	 * secret key and initialization vector using AES encryption algorithm.
	 * 
	 * @param encryptedString
	 * @param secretKey
	 * 
	 * @return Decrypted String
	 * 
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 */
	public static String decrypt(String encryptedString, String secretKey)
			throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		LOGGER.info("Encrypted string: {}", encryptedString);

		IvParameterSpec ivParameterSpec = new IvParameterSpec(INITIALIZATION_VECTOR.getBytes(StandardCharsets.UTF_8));
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

		byte[] decryptedBytes = cipher.doFinal(Base64.decodeBase64(encryptedString));
		return new String(decryptedBytes);
	}

}
