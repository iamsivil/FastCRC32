package com.github.igp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.CRC32;

public class FastCRC32 {
	public static long getLong(File file, int bufferSize) throws IOException
	{
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);

		long len = file.length();
		if (bufferSize > len)
			bufferSize = (int)len;
		byte[] bytes = new byte[bufferSize];

		double r = len / bufferSize;
		CRC32 crc32 = new CRC32();
		for (int i = 0; i < r; i++)
		{
			bis.read(bytes);
			crc32.update(bytes);
		}
		if ((len - (r * bufferSize)) > 0)
		{
			bytes = new byte[(int)(len - (r * bufferSize))];
			bis.read(bytes);
			crc32.update(bytes);
		}

		return crc32.getValue();
	}

	public static long getLong(String path, int bufferSize) throws IOException
	{
		return getLong(new File(path), bufferSize);
	}

	public static String getHexString(String path, int bufferSize) throws IOException
	{
		return Long.toHexString(getLong(path, bufferSize));
	}

	public static String getHexString(File file, int bufferSize) throws IOException
	{
		return Long.toHexString(getLong(file, bufferSize));
	}
}