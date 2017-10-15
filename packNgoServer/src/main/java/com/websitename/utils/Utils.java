package com.websitename.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;

public class Utils {

	private final static double PI = 3.14159;
	private static final String ALGORITHM = "AES";
	private static final byte[] keyValue = new byte[] { 'S', 'w', 'i', 'n','g', '.', '5', ':', 'a', 'r', 'V', 'U', '<', '>', 'q', 't' };
	
	
	public static String getDecrytedPassword(String encryptedString, Map<String, String> headerMap) {
		AesUtil aes = new AesUtil(Integer.parseInt(headerMap.get("size")), Integer.parseInt(headerMap.get("iteration")));
		String decrypt = aes.decrypt(headerMap.get("token2"), headerMap.get("token1"), headerMap.get("token3"), encryptedString);
		return decrypt;

	}
	/**
	 * Returns GUID
	 * @return GUID
	 */
	public static String generateGUID() {
		UUID idOne = UUID.randomUUID();
		String uuid = idOne.toString();
		uuid = uuid.replaceFirst(".{3}", "PNG");
		return uuid;
	}


	public static int daysDifference(String fromDay,String toDay){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date;
		int difference = 0;
		try {
			date = sdf.parse(toDay);
//			String currDateString = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
			Date currDate = sdf.parse(fromDay);	
			long diff = date.getTime() - currDate.getTime();
			difference = (int) (diff / (24 * 60 * 60 * 1000));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return difference;
	}

	/**
	 * Return if string value is null or empty
	 * @param data
	 * @return
	 */
	public static boolean isNullOrBlank(String data) {
		boolean status = false;
		if (data == null || data.isEmpty()) {
			status = true;
		}
		return status;
	}

	/**
	 * Converts string to Byte , required while storing in BLOB
	 * @param contentString
	 * @return
	 */
	public static Byte[] convertStringToByte(String contentString) {
		byte[] contentbytes = contentString.getBytes(Charset.forName("UTF-8"));
		Byte[] contentObject = new Byte[contentbytes.length];
		int i = 0;
		for (byte b : contentbytes)
			contentObject[i++] = b; // Autoboxing.

		return contentObject;
	}

	/**
	 * Converts Byte to String
	 * @param byteObjects
	 * @return
	 */
	public static String convertByteToString(Byte[] byteObjects) {
		String string = null;
		if (byteObjects != null) {
			int j = 0;
			byte[] bytes = new byte[byteObjects.length];
			// Unboxing byte values. (Byte[] to byte[])
			for (Byte b : byteObjects)
				bytes[j++] = b.byteValue();

			string = new String(bytes);
		}

		return string;
	}



	private static Key generateKey() throws Exception {
		Key key = new SecretKeySpec(keyValue, ALGORITHM);
		return key;
	}

	public static String encrypt(String valueToEnc) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGORITHM);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encValue = c.doFinal(valueToEnc.getBytes());
		String encryptedValue = new BASE64Encoder().encode(encValue);
		return encryptedValue;
	}




	public static void deleteImage(String path) throws IOException,IllegalArgumentException{

		String systemPath = System.getProperty("jboss.server.data.dir");
		String jBossServer = systemPath.substring(0,systemPath.length()-4);

		String filePath=path;
		int indexOfOFF = filePath.lastIndexOf("/");
		filePath=filePath.substring(indexOfOFF+1);

		String dir=filePath.substring(3,8);
		char[] dir1=dir.toCharArray();	
		dir="";	
		for(int j=0;j<dir1.length;j++)
		{
			dir+= dir1[j]+"//";		
		}		

		String serverPath = jBossServer +"//deployments//vv-assets.war//images//"+ dir + filePath;	

		File f = new File(serverPath);
		f.delete();

	}

	public static void deleteLogo(String path) throws IOException,IllegalArgumentException{

		String systemPath = System.getProperty("jboss.server.data.dir");
		String jBossServer = systemPath.substring(0,systemPath.length()-4);

		String filePath=path;
		int indexOfOFF = filePath.lastIndexOf("/");
		filePath=filePath.substring(indexOfOFF+1);

		String dir=filePath.substring(3,8);
		char[] dir1=dir.toCharArray();	
		dir="";	
		for(int j=0;j<dir1.length;j++)
		{
			dir+= dir1[j]+"//";		
		}		

		String serverPath = jBossServer +"//deployments//vv-assets.war//businessLogo//"+ dir + filePath;	

		File f = new File(serverPath);
		f.delete();

	}


	/**
	 * @param str
	 * @param list
	 * @return
	 */
	public static boolean containsCaseInsensitive(String str, List<String> list){
		for (String string : list){
			if (string.equalsIgnoreCase(str)){
				return true;
			}
		}
		return false;
	}


	public static String getNumberValue(String a) {

		if(a != null || a!=""){
			a = a.replaceAll(",", "");
			Pattern p = Pattern.compile("-?\\d+");
			Matcher m = p.matcher(a);
			if(m.find()) {
				return m.group();

			}  
		}
		return null;
	}


	public static String formatDate(String dateStr){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		try{
			Date date = formatter.parse(dateStr.replaceAll(".000", ""));
			formatter = new SimpleDateFormat("MM/dd/yyyy");
			return formatter.format(date);
		}catch (Exception e) {
			return null;
		}
	}
	
	public static String formatDateV3(String dateStr){
		SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
		try{
			Date date = formatter.parse(dateStr.replaceAll(".000", ""));
			formatter = new SimpleDateFormat("MM/dd/yyyy");
			return formatter.format(date);
		}catch (Exception e) {
			return null;
		}
	}
	
	public static String formatDateV2(String dateStr){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		try{
			Date date = formatter.parse(dateStr.replaceAll(".000", ""));
			formatter = new SimpleDateFormat("HH:mm:ss");
			return formatter.format(date);
		}catch (Exception e) {
			return null;
		}
	}
	
	public static Date string2Date(String dateStr){
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		try{
			return formatter.parse(dateStr);
		}catch (Exception e) {
			return null;
		}
	}

	public static String date2String(Date dateStr){
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		try{
			return formatter.format(dateStr);
		}catch (Exception e) {
			return null;
		}
	}
	
	public static Double timeDifference(String dateStart, String dateStop){
		//HH converts hour in 24 hours format (0-23), day calculation
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);

			//in milliseconds
			double diff = d2.getTime() - d1.getTime();

			//long diffSeconds = diff / 1000 % 60;
			//long diffMinutes = diff / (60 * 1000) % 60;
			return (double) (diff / (60 * 60 * 1000) % 24);
			//long diffDays = diff / (24 * 60 * 60 * 1000);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
