package com.cheng.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 流的工具类
 */
public class StreamTools {
	/**
	 * 读取一个流 把流的内容转换成字符串
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String readStream(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = is.read(buffer)) != -1) {
			baos.write(buffer, 0, len);
		}
		is.close();
		return baos.toString();

	}
}
