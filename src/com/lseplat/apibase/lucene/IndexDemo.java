package com.lseplat.apibase.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by fqc on 2015/6/22.
 */
public class IndexDemo {
    /**
     * 创建字符串索引
     *
     * @param indexPath
     */
    //Lucene的概述 http://blog.csdn.net/fanv2011/article/details/46592665
    public static void createIndex(String indexPath) {
        //两步核心 ：创建索引，添加文档。
        IndexWriter iw = null; // 索引书写器，也就是维护器，对索引进行读取和删除操作的类
        try {
//         Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_46);
            Analyzer analyzer = new IKAnalyzer();
            IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_46, analyzer);
            File file = new File(indexPath);
            Directory dic = FSDirectory.open(file);
            iw = new IndexWriter(dic, iwc); //core step to step

            Document doc = new Document(); //源数据的封装结构，我们需要把源数据分成不同的域，放入到documet里面，到时搜索时也可以指定搜索哪些域（Field）了
            IndexableField intField = new IntField("id", 1, Field.Store.YES);
            IndexableField stringField = new StringField("title", "key1 key2", Field.Store.YES);
            IndexableField contentField = new TextField("content", "key3 key4", Field.Store.YES);
            doc.add(intField);
            doc.add(stringField);
            doc.add(contentField);
            iw.addDocument(doc); //core step to step
            iw.forceMerge(10); //merge index files
            iw.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                iw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建txt文档索引
     *
     * @param indexPath
     */
    public static void createTextIndex(List<Map<String, Object>> list, String indexPath) {
        //两步核心 ：创建索引，添加文档。
        IndexWriter iw = null; // 索引书写器，也就是维护器，对索引进行读取和删除操作的类
        try {
//         Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_46);
            Analyzer analyzer = new IKAnalyzer(); //Ik分词器更加强劲，使用luke工具对比就知晓了。 智能分词 词频
            IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_46, analyzer);
            File file = new File(indexPath);
            Directory dic = FSDirectory.open(file);
            iw = new IndexWriter(dic, iwc); //core step to step

            //将list中map的k,v获取 添加到doc中
            for (Map<String, Object> map : list) {
                Document doc = new Document(); //源数据的封装结构，我们需要把源数据分成不同的域，放入到documet里面，到时搜索时也可以指定搜索哪些域（Field）了
                Set<String> set = map.keySet();
                Iterator<String> iterator = set.iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    String value = (String) map.get(key);
                    //这里就做简单处理了，目前不做各种Field判断，solr中已处理。
                    IndexableField field = new TextField(key, value, Field.Store.YES);
                    doc.add(field);
                }
                iw.addDocument(doc);
            }
            // iw.forceMerge(10); //merge index files
            iw.commit();
            iw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void indexDemo(List<Map<String, Object>> list, String indexPath) {
        try {
//			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_46);
            Analyzer analyzer = new IKAnalyzer();
            Directory dir = FSDirectory.open(new File(indexPath));
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_46, analyzer);
            IndexWriter iw = new IndexWriter(dir, config);

            for(Map<String,Object> map : list){
                Document doc = new Document();
                Set<String> set = map.keySet();
                Iterator<String> iter = set.iterator();
                while(iter.hasNext()){
                    String key = iter.next();
                    String value = (String)map.get(key);
                    IndexableField field = new TextField(key,value,Field.Store.YES);
                    doc.add(field);
                }
                iw.addDocument(doc);
            }
            iw.commit();
            iw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateIndex(String indexPath) {
        try {
            Analyzer analyzer = new IKAnalyzer();
            Directory dir = FSDirectory.open(new File(indexPath));
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_46, analyzer);
            IndexWriter iw = new IndexWriter(dir, config);

            Document doc = new Document();
            IndexableField field = new TextField("id", "100", Field.Store.YES);
            doc.add(field);
            iw.updateDocument(new Term("id", "2"), doc);

            iw.commit();
            iw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除索引
     * @param indexPath
     */
    public static void deleteIndex(String indexPath) {
        try {
            Analyzer analyzer = new IKAnalyzer();
            Directory dir = FSDirectory.open(new File(indexPath));
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_46, analyzer);
            IndexWriter iw = new IndexWriter(dir, config);

            iw.deleteAll();
//         iw.deleteDocuments(new Term("id","1"));
            iw.commit();
            iw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
