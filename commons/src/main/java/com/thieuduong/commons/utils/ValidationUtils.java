package com.thieuduong.commons.utils;

public class ValidationUtils {
	public static boolean isNullOrEmpty(String input) {
		if (input == null || input.isBlank() || input.isEmpty()) {
			return true;
		}
		return false;
	}
}
