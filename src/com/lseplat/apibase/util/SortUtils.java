package com.lseplat.apibase.util;

import java.util.*;

public class SortUtils {

	/**
	 * map����
	 * @param map
	 * @param isDec trueΪ����falseΪ����
	 * @return
	 */
	public static Map sortByValue(Map map,final boolean isDec){
		Map result = new LinkedHashMap();
		List list = new LinkedList(map.entrySet());
		Collections.sort(list, new Comparator(){

			@Override
			public int compare(Object o1, Object o2) {
				if(isDec){
					return -((Comparable)((Map.Entry)o1).getValue()).compareTo(((Map.Entry)o2).getValue());
				}else{
					return ((Comparable)((Map.Entry)o1).getValue()).compareTo(((Map.Entry)o2).getValue());
				}
			}
			
		});
		
		for(Iterator iter = list.iterator();iter.hasNext();){
			Map.Entry entry = (Map.Entry)iter.next();
			result.put(entry.getKey(),entry.getValue());
		}
		return result;
	}
	
	/**
	 * map�ĵ�������
	 * @param map
	 * @return
	 */
	public static Map sortByValue(Map map){
		return sortByValue(map,true);
	}
}
