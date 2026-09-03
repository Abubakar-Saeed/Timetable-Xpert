package com.timetablexpert;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Single source of truth for password hashing.
 *
 * <p>History / bug fix: the old inline implementations in {@code LoginController} and
 * {@code RegisterController} did {@code new BigInteger(1, digest).toString(16)}, which
 * drops leading zero nibbles. Roughly 1 in 256 hashes came out shorter than 64 hex
 * chars, so the value stored at registration never matched the value computed at
 * login and the account was silently unusable. {@code %064x} zero-pads to a fixed
 * 64-character width, making the encoding stable.
 */
public final class PasswordUtil {

    private PasswordUtil() {
    }

    /** SHA-256 of the input, rendered as a fixed 64-character lowercase hex string. */
    public static String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return String.format("%064x", new BigInteger(1, digest));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available on this JVM", e);
        }
    }
}
