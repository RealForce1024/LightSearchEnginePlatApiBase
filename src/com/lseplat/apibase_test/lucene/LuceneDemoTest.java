package com.lseplat.apibase_test.lucene;

import com.lseplat.apibase.lucene.IndexDemo;
import com.lseplat.apibase.lucene.SearchDemo;
import com.lseplat.apibase.util.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fqc on 2015/6/22.
 */
public class LuceneDemoTest {
    @Test
    public void createIndex(){
        String indexPath = "F:\\github\\LightSearchEnginePlatApiBase\\outputindex";
        IndexDemo.createIndex(indexPath);
    }

    @Test
    public void searchIndex(){
        String indexPath = "F:\\github\\LightSearchEnginePlatApiBase\\outputindex";
        SearchDemo.searchIndex(indexPath);
    }
    @Test
    public void createTextIndex(){
        String indexPath = "F:\\github\\LightSearchEnginePlatApiBase\\outputindex";//
        String txtPath = "F:\\github\\LightSearchEnginePlatApiBase\\parser";
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = null;
        StringUtils su = new StringUtils(txtPath);
        List<String> filesName = su.allPathResult;
        int id = 0;
        for(String fileName : filesName){
            map = new HashMap<String,Object>();
            String name = StringUtils.getFileNameFromPath(fileName);
            String content = StringUtils.getContent(fileName);
            map.put("id", ++id+"");
            map.put("name", name);
            map.put("content", content);
            list.add(map);
        }
        IndexDemo.createTextIndex(list,indexPath);
    }
    @Test
    public void deleteIndex() {
        String indexPath = "F:\\github\\LightSearchEnginePlatApiBase\\outputindex";
        IndexDemo.deleteIndex(indexPath);
    }

    @Test
    public void updateIndex() {
        IndexDemo.updateIndex("F:\\github\\LightSearchEnginePlatApiBase\\outputindex");

    }

    /**
     * ²âÊÔluceneËÑË÷
     */
    @Test
    public void test2() {
        String indexPath = "F:\\github\\LightSearchEnginePlatApiBase\\outputindex";//
        SearchDemo.searcherDemo(indexPath);
    }

    /**
     * ²âÊÔlucene txtÎÄ¼þË÷Òý
     */
    @Test
    public void test3() {
        String indexPath = "F:\\github\\LightSearchEnginePlatApiBase\\outputindex";//
        String txtPath = "F:\\github\\LightSearchEnginePlatApiBase\\parser";
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        StringUtils su = new StringUtils(txtPath);
        List<String> filesName = su.allPathResult;
        int id = 0;
        for (String fileName : filesName) {
            map = new HashMap<String, Object>();
            String name = StringUtils.getFileNameFromPath(fileName);
            String content = StringUtils.getContent(fileName);
            map.put("id", ++id + "");
            map.put("name", name);
            map.put("content", content);
            list.add(map);
        }
        IndexDemo.indexDemo(list, indexPath);
    }




}
