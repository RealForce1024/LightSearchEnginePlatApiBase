package com.lseplat.apibase.util;


import com.lseplat.apibase.config.ConstantParams;

import java.io.*;
import java.util.*;

public class ExtractUtils {

	/**
	 * 读取评价对象抽取的规则
	 * @param inputPath 规则的完整路径
	 * @return
	 * xx/n
	 */
	public static List<String> getRules(String inputPath){
		List<String> result = new ArrayList<String>();
		File file = new File(inputPath);
		FileReader fr = null;
		BufferedReader br = null;
		String regex = "[\u4e00-\u9fa5a-zA-Z0-9]*";
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String temp = "";
			while((temp=br.readLine()) != null){
				String[] temps = temp.split(ConstantParams.SINGLE_BLANK);
				String str = "";
				for(String rule : temps){
					str += (regex+"/"+rule+"(.?)"+ConstantParams.SINGLE_BLANK);
				}
				result.add(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(br != null){
					br.close();
				}
				if(fr != null){
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 按照规则，抽取单个文件的候选集
	 * @param rulesPath 规则的路径
	 * @param sourcePath 分词之后的文件路径
	 * @param outputPath 抽取之后的保存路径
	 * @return
	 */
	public static void singleTextWordsSet(String rulesPath,String sourcePath,String outputPath){
		try {
			String result = "";
			List<String> rulesList = getRules(rulesPath);
			String sourceString = StringUtils.getContent(sourcePath);
			for(String rule : rulesList){
				result += StringUtils.getContentUseRegex(rule, sourceString, "");
			}
			StringUtils.string2File(result, outputPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 过滤特殊词（单子动词，特殊符号... ...）
	 * @param intputPath
	 * @param outputPath
	 */
	public static void filterWords(String inputPath,String outputPath){
		try {
			String result = "";
			File file = new File(inputPath);
			InputStream is = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(is,"gb2312");
			BufferedReader br = new BufferedReader(isr);
			String temp = "";
			while((temp=br.readLine()) != null){
				String[] temps = temp.split(ConstantParams.SINGLE_BLANK);
				if(temps.length > 1){
					result += temp+ConstantParams.CHENG_LINE;
				}else{
					String[] wordsTemps = temps[0].split("/");
					if(wordsTemps[0].length() != 1){
						result += temp+ConstantParams.CHENG_LINE;
					}
				}
			}
			br.close();
			StringUtils.string2File(result, outputPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 去除txt中重复的候选词
	 * @param inputPath
	 * @param outputPath
	 */
	public static void delRepWords(String inputPath,String outputPath){
		try {
			List<String> list = new ArrayList<String>();
			String result = "";
			File file = new File(inputPath);
			InputStream is = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(is,"gb2312");
			BufferedReader br = new BufferedReader(isr);
			String temp = "";
			while((temp=br.readLine()) != null){
				if(!list.contains(temp)){
					list.add(temp);
					result += temp+ConstantParams.CHENG_LINE;
				}
			}
			br.close();
			StringUtils.string2File(result, outputPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 非完整性过滤
	 * @param inputPath
	 * @param outputPath
	 */
	public static void filterInteWords(String inputPath,String sourcePath,String outputPath){
			try {
				String result = "";
				List<String> wordsList = StringUtils.getContentFromPath(inputPath);
				for(String word : wordsList){
					
					//读分词之后的源文件
					String sourceWord = StringUtils.getContent(sourcePath);
					String[] sourceWords = sourceWord.split(ConstantParams.SINGLE_BLANK);
					String[] words = word.split(ConstantParams.SINGLE_BLANK);
					Set<String> leftSet = new HashSet<String>();
					Set<String> rightSet = new HashSet<String>();
					
					//是0的时候，表示完整；1的时候表示不完整
					int left = 0;
					int right = 0;
					
					int leftFirst = 1;
					int rightLast = 1;
					if(words.length == 1){
						
						for(int i=0;i<sourceWords.length;i++){
							if(words[0].equals(sourceWords[i])){
								if(i == 0){
									leftFirst = 0;
									rightSet.add(sourceWords[i+1]);
								}else if(i == sourceWords.length-1){
									rightLast = 0;
									leftSet.add(sourceWords[i-1]);
								}else{
									leftSet.add(sourceWords[i-1]);
									rightSet.add(sourceWords[i+1]);
								}
								
							}
							if(leftFirst == 0){
								left = 0;
							}else{
								if(leftSet.size() == 1){
									left = 1;
								}else{
									left = 0;
								}
							}
							
							if(rightLast == 0){
								right = 0;
							}else{
								if(rightSet.size() == 1){
									right = 1;
								}else{
									right = 0;
								}
							}
						}
					}else if(words.length == 2){
						
						for(int i=0;i<sourceWords.length-1;i++){
							if(words[0].equals(sourceWords[i]) && words[1].equals(sourceWords[i+1])){
								if(i == 0){
									leftFirst = 0;
									rightSet.add(sourceWords[i+2]);
								}else if(i == sourceWords.length-1){
									rightLast = 0;
									leftSet.add(sourceWords[i-1]);
								}else{
									leftSet.add(sourceWords[i-1]);
									rightSet.add(sourceWords[i+2]);
								}
								
							}
							if(leftFirst == 0){
								left = 0;
							}else{
								if(leftSet.size() == 1){
									left = 1;
								}else{
									left = 0;
								}
							}
							
							if(rightLast == 0){
								right = 0;
							}else{
								if(rightSet.size() == 1){
									right = 1;
								}else{
									right = 0;
								}
							}
						}
					}else if(words.length == 2){
						for(int i=0;i<sourceWords.length-2;i++){
							if(words[0].equals(sourceWords[i]) && words[1].equals(sourceWords[i+1]) && words[2].equals(sourceWords[i+2])){
								if(i == 0){
									leftFirst = 0;
									rightSet.add(sourceWords[i+3]);
								}else if(i == sourceWords.length-1){
									rightLast = 0;
									leftSet.add(sourceWords[i-1]);
								}else{
									leftSet.add(sourceWords[i-1]);
									rightSet.add(sourceWords[i+3]);
								}
								
							}
							if(leftFirst == 0){
								left = 0;
							}else{
								if(leftSet.size() == 1){
									left = 1;
								}else{
									left = 0;
								}
							}
							
							if(rightLast == 0){
								right = 0;
							}else{
								if(rightSet.size() == 1){
									right = 1;
								}else{
									right = 0;
								}
							}
						}
					}
					result += word+ConstantParams.TABLE+left+ConstantParams.TABLE+right+ConstantParams.CHENG_LINE;
				}
				StringUtils.string2File(result, outputPath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	
	/**
	 * 计算候选词，在文章中的出现的次数
	 * @param inputPath
	 * @param outputPath
	 * @return 
	 */
	public static void getNumFromFile(String inputPath,String outputPath){
		try {
			String result = "";
			File file = new File(inputPath);
			InputStream is = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(is,"gb2312");
			BufferedReader br = new BufferedReader(isr);
			String temp = "";
			Map<String,Integer> map = new HashMap<String,Integer>();
			while((temp=br.readLine()) != null){
				if(map.containsKey(temp)){
					Integer value = map.get(temp);
					map.remove(temp);
					map.put(temp, value+1);
				}else{
					map.put(temp, 1);
				}
			}
			br.close();
			Set<String> set = map.keySet();
			Iterator<String> iter = set.iterator();
			while(iter.hasNext()){
				String key = iter.next();
				Integer value = map.get(key);
				result += key+ConstantParams.TABLE+value+ConstantParams.CHENG_LINE;
			}
			StringUtils.string2File(result, outputPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 给统计之后的文件，进行排序
	 * @param inputPath
	 * @param outputPath
	 */
	public static void getSortResultForStatistics(String inputPath,String outputPath){
		try {
			String result = "";
			File file = new File(inputPath);
			InputStream is = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(is,"gb2312");
			BufferedReader br = new BufferedReader(isr);
			String temp = "";
			Map<String,Integer> map = new HashMap<String,Integer>();
			while((temp=br.readLine()) != null){
				String[] temps = temp.split(ConstantParams.TABLE);
				map.put(temps[0], Integer.parseInt(temps[1]));
			}
			Map resultMap = SortUtils.sortByValue(map);
			br.close();
			
			Set<String> set = resultMap.keySet();
			Iterator<String> iter = set.iterator();
			while(iter.hasNext()){
				String key = iter.next();
				Integer value = (Integer)resultMap.get(key);
				result += key+ConstantParams.TABLE+value+ConstantParams.CHENG_LINE;
			}
			StringUtils.string2File(result, outputPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
