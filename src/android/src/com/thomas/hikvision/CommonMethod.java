package com.thomas.hikvision;

public class CommonMethod {

	public static byte[] string2ASCII(String s, int length) {// 字符串转换为ASCII码
		if (s == null || "".equals(s)) {
			return null;
		}

		char[] chars = s.toCharArray();
		byte[] asciiArray = new byte[length];

		for (int i = 0; i < length; i++) {
			if (i < chars.length) {
				asciiArray[i] = char2ASCII(chars[i]);
			} else {
				asciiArray[i] = char2ASCII('\0');
			}
		}
		return asciiArray;
	}

	public static byte char2ASCII(char c){
    	return (byte)c;
    }
	public static String toValidString(String s){
        String[] sIP = new String[2];
        sIP = s.split("\0", 2);
        return sIP[0];
    }
}
