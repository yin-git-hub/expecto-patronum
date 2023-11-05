package com.example.service.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 * 单向加密算法
 * 特点：加密速度快，不需要秘钥，但是安全性不高，需要搭配随机盐值使用
 *
 */
public class MD5Util {
	public static void main(String[] args) {
		String sign = sign("123", "123");
		System.out.println(sign);
		boolean verify = verify("123", "4297f44b13955235245b2497399d7a93", "123");
		System.out.println("verify = " + verify);
	}

	public static String sign(String content, String salt) {
		content = content + salt;
		return DigestUtils.md5Hex(getContentBytes(content, "UTF-8"));
	}

	public static boolean verify(String content, String sign, String salt) {
		content = content + salt;
		String mysign = DigestUtils.md5Hex(getContentBytes(content, "UTF-8"));
		return mysign.equals(sign);
	}

	private static byte[] getContentBytes(String content, String charset) {
		if (!"".equals(charset)) {
			try {
				return content.getBytes(charset);
			} catch (UnsupportedEncodingException var3) {
				throw new RuntimeException("MD5签名过程中出现错误,指定的编码集错误");
			}
		} else {
			return content.getBytes();
		}
	}

	//获取文件md5加密后的字符串
    public static String getFileMD5(MultipartFile file) throws Exception {
		InputStream fis = file.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int byteRead;
		while((byteRead = fis.read(buffer)) > 0){
			baos.write(buffer, 0, byteRead);
		}
		fis.close();
		return DigestUtils.md5Hex(baos.toByteArray());
    }

	//获取文件md5加密后的字符串
	public static String getFileMD5(InputStream fis) throws Exception {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int byteRead;
		while((byteRead = fis.read(buffer)) > 0){
			baos.write(buffer, 0, byteRead);
		}
		fis.close();
		return DigestUtils.md5Hex(baos.toByteArray());
	}


}
