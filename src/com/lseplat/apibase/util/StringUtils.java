package com.lseplat.apibase.util;


import com.lseplat.apibase.config.ConstantParams;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	public List<String> allPathResult = new ArrayList<String>();
	
	public StringUtils(String inputPath){
		getAllPath(inputPath);
	}

	/**
	 * 判断字符为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		boolean b = false;
		if(null == str || "".equals(str)){
			b = true;
		}
		return b;
	}
	
	/**
	 * 判断字符不为空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		boolean b = false;
		if(null != str && !"".equals(str)){
			b = true;
		}
		return b;
	}
	
	/**
	 * 利用正则表达式，获取可能匹配的单个内容
	 * @param regexString
	 * @param sourceString
	 * @return
	 */
	public static String getContentUseRegex(String regexString,String sourceString){
		String result = "";
		if(isEmpty(regexString) || isEmpty(sourceString)){
			return result;
		}
		try {
			Pattern pattern = Pattern.compile(regexString);
			Matcher matcher = pattern.matcher(sourceString);
			if(matcher.find()){
				result = matcher.group(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 利用正则表达式，获取可能匹配的多个内容
	 * @param regexString
	 * @param sourceString
	 * @param splitMark 分隔符
	 * @return
	 */
	public static String getContentUseRegex(String regexString,String sourceString,String splitMark){
		String result = "";
		if(isEmpty(regexString) || isEmpty(sourceString)){
			return result;
		}
		if(isEmpty(splitMark)){
			splitMark = ConstantParams.CHENG_LINE;
		}
		try {
			Pattern pattern = Pattern.compile(regexString);
			Matcher matcher = pattern.matcher(sourceString);
			while(matcher.find()){
				result += matcher.group()+splitMark;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 递归获取文件夹下所有的文件绝对路径
	 * @param inputPath
	 * @return
	 */
	public  void getAllPath(String inputPath){

		try {
			File file = new File(inputPath);
			File[] files = file.listFiles();
			for(File f : files){
				if(f.isDirectory()){
					getAllPath(f.getAbsolutePath());//递归
				}else{
//					System.out.println(f.getAbsolutePath());
					allPathResult.add(f.getAbsolutePath());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取文件内容（读到内存里面）
	 * @param inputPath
	 * @return
	 */
	public static String getContent(String inputPath){
		String result = "";
		if(isEmpty(inputPath)){
			return result;
		}
		try {
			File file = new File(inputPath);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String temp = "";
			while((temp=br.readLine()) != null){
				result += (temp+ConstantParams.CHENG_LINE);
			}
			br.close();
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 将内存中的字符串写入到磁盘文件中
	 * @param str 待写入的字符串
	 * @param outputPath 写入路径
	 * @return
	 */
	public static boolean string2File(String str,String outputPath){
		boolean b = false;
		if(isEmpty(outputPath)){
			return b;
		}
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			File file = new File(outputPath);
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			bw.write(str);
			b = true;
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		}finally{
			try {
				if(bw != null){
					bw.close();
				}
				if(fw != null){
					fw.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return b;
	}
	
	/**
	 * 从文件的完整路径中，抽取其文件名称
	 * @param path
	 * @return
	 */
	public static String getFileNameFromPath(String path){
		String result = "";
		if(isEmpty(path)){
			return result;
		}
		if(path.contains("/")){
			result = path.substring(path.lastIndexOf("/")+1,path.lastIndexOf("."));
		}else if(path.contains("\\")){
			result = path.substring(path.lastIndexOf("\\")+1,path.lastIndexOf("."));
		}else{
			result = path;
		}
		return result;
	}
	
	/**
	 * 将txt中的内容，每一行作为一个元素，读入到list中。
	 * @param inputPath
	 * @return
	 */
	public static List<String> getContentFromPath(String inputPath){
		List<String> result = new ArrayList<String>();
		try {
			File file = new File(inputPath);
			InputStream is = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(is,"gb2312");
			BufferedReader br = new BufferedReader(isr);
			String temp = "";
			while((temp=br.readLine()) != null){
				result.add(temp);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
