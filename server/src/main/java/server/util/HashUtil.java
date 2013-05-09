package server.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

public class HashUtil {
	public static String hash(String value) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		digest.reset();
		try {
			byte[] hash = digest.digest(value.getBytes("UTF-8"));
			return new String(new Hex().encode(hash));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
