package com.petadopt.util;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public final class PasswordUtil {

    private PasswordUtil() {
    }

    public static String md5(String rawPassword) {
        return DigestUtils.md5DigestAsHex(rawPassword.getBytes(StandardCharsets.UTF_8));
    }
}
