package com.lseplat.apibase.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

/**
 * Created by fqc on 2015/6/22.
 */
public class IndexDemo {

   //Lucene的概述 http://blog.csdn.net/fanv2011/article/details/46592665
   public static void createIndex(String indexPath){
        //两步核心 ：创建索引，添加文档。
      IndexWriter iw = null; // 索引书写器，也就是维护器，对索引进行读取和删除操作的类
      try {
         Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_46);
         IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_46,analyzer);
         File file = new File(indexPath);
         Directory dic = FSDirectory.open(file);
         iw = new IndexWriter(dic,iwc); //core step to step

         Document doc = new Document(); //源数据的封装结构，我们需要把源数据分成不同的域，放入到documet里面，到时搜索时也可以指定搜索哪些域（Field）了
         IndexableField intField = new IntField("id",1, Field.Store.YES);
         IndexableField stringField = new StringField("title","key1 key2", Field.Store.YES);
         IndexableField contentField = new TextField("content","key3 key4", Field.Store.YES);
         doc.add(intField);
         doc.add(stringField);
         doc.add(contentField);
         iw.addDocument(doc); //core step to step
         iw.commit();
      }catch (IOException e){
         e.printStackTrace();
      }finally {
         try {
            iw.close();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }





   }
}
