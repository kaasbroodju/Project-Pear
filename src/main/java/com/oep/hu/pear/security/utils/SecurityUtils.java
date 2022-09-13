package com.oep.hu.pear.security.utils;

import com.auth0.jwt.algorithms.Algorithm;

public class SecurityUtils {

	public static Algorithm getAlgorithm() {
		return Algorithm.HMAC256(System.getenv("secret_key"));
	}
}
