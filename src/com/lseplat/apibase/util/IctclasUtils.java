package com.lseplat.apibase.util;

import ICTCLAS.I3S.AC.ICTCLAS50;

public class IctclasUtils {
	
	/**
	 * ictclas分词
	 * @param str
	 * @return
	 */
	public static String ictclasSplit(String str){
		String result = "";
		try{
			ICTCLAS50 testICTCLAS50 = new ICTCLAS50();
			String argu = ".";
			//初始化
			if (testICTCLAS50.ICTCLAS_Init(argu.getBytes("GB2312")) == false){
				System.out.println("Init Fail!");
				return "";
			}


			//设置词性标注集(0 计算所二级标注集，1 计算所一级标注集，2 北大二级标注集，3 北大一级标注集)
			testICTCLAS50.ICTCLAS_SetPOSmap(2);


			//导入用户词典前分词
			byte nativeBytes[] = testICTCLAS50.ICTCLAS_ParagraphProcess(str.getBytes("GB2312"), 0, 1);//分词处理
			result = new String(nativeBytes, 0, nativeBytes.length, "GB2312");

			//释放分词组件资源
			testICTCLAS50.ICTCLAS_Exit();
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
}
