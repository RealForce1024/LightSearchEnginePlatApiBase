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
     * �����ַ�������
     *
     * @param indexPath
     */
    //Lucene�ĸ��� http://blog.csdn.net/fanv2011/article/details/46592665
    public static void createIndex(String indexPath) {
        //�������� ����������������ĵ���
        IndexWriter iw = null; // ������д����Ҳ����ά���������������ж�ȡ��ɾ����������
        try {
//         Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_46);
            Analyzer analyzer = new IKAnalyzer();
            IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_46, analyzer);
            File file = new File(indexPath);
            Directory dic = FSDirectory.open(file);
            iw = new IndexWriter(dic, iwc); //core step to step

            Document doc = new Document(); //Դ���ݵķ�װ�ṹ��������Ҫ��Դ���ݷֳɲ�ͬ���򣬷��뵽documet���棬��ʱ����ʱҲ����ָ��������Щ��Field����
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
     * ����txt�ĵ�����
     *
     * @param indexPath
     */
    public static void createTextIndex(List<Map<String, Object>> list, String indexPath) {
        //�������� ����������������ĵ���
        IndexWriter iw = null; // ������д����Ҳ����ά���������������ж�ȡ��ɾ����������
        try {
//         Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_46);
            Analyzer analyzer = new IKAnalyzer(); //Ik�ִ�������ǿ����ʹ��luke���߶ԱȾ�֪���ˡ� ���ִܷ� ��Ƶ
            IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_46, analyzer);
            File file = new File(indexPath);
            Directory dic = FSDirectory.open(file);
            iw = new IndexWriter(dic, iwc); //core step to step

            //��list��map��k,v��ȡ ��ӵ�doc��
            for (Map<String, Object> map : list) {
                Document doc = new Document(); //Դ���ݵķ�װ�ṹ��������Ҫ��Դ���ݷֳɲ�ͬ���򣬷��뵽documet���棬��ʱ����ʱҲ����ָ��������Щ��Field����
                Set<String> set = map.keySet();
                Iterator<String> iterator = set.iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    String value = (String) map.get(key);
                    //��������򵥴����ˣ�Ŀǰ��������Field�жϣ�solr���Ѵ���
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
     * ɾ������
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
