package com.lseplat.apibase_test.lucene;

import com.lseplat.apibase.lucene.IndexDemo;
import com.lseplat.apibase.lucene.SearchDemo;
import org.junit.Test;

/**
 * Created by fqc on 2015/6/22.
 */
public class LuceneDemoTest {
    @Test
    public void createIndex(){
        String indexPath = "F:\\github-fqc\\LightSearchEnginePlatApiBase_ToLearn\\data\\index";
        IndexDemo.createIndex(indexPath);
    }

    @Test
    public void searchIndex(){
        String indexPath = "F:\\github-fqc\\LightSearchEnginePlatApiBase_ToLearn\\data\\index";
        SearchDemo.searchIndex(indexPath);
    }
}
