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

   //Lucene�ĸ��� http://blog.csdn.net/fanv2011/article/details/46592665
   public static void createIndex(String indexPath){
        //�������� ����������������ĵ���
      IndexWriter iw = null; // ������д����Ҳ����ά���������������ж�ȡ��ɾ����������
      try {
         Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_46);
         IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_46,analyzer);
         File file = new File(indexPath);
         Directory dic = FSDirectory.open(file);
         iw = new IndexWriter(dic,iwc); //core step to step

         Document doc = new Document(); //Դ���ݵķ�װ�ṹ��������Ҫ��Դ���ݷֳɲ�ͬ���򣬷��뵽documet���棬��ʱ����ʱҲ����ָ��������Щ��Field����
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
