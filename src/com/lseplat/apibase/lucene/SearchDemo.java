package com.lseplat.apibase.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;

/**
 * Created by fqc on 2015/6/22.
 */
public class SearchDemo {
    public static void searchIndex(String indexPath) {
        // IndexSearcher  // �����������������ǰ��û������õ������б���������һ���� ��Ҫע����ǣ�������������ľ��ǣ�TopDocs�������ţ����������������¡�
        try {
            Directory dir = FSDirectory.open(new File(indexPath));
            IndexReader ir = DirectoryReader.open(dir); //������ȡ�������ڶ�ȡָ��Ŀ¼��������
            IndexSearcher is = new IndexSearcher(ir);  //core
//          Query query = NumericRangeQuery.newIntRange("id", 1, 2, true, true);//��ѯ��䣬������Ҫ�����ǵĲ�ѯString��װ��Query�ſ��Խ���Searcher������ ,��ѯ����С��Ԫ��Term��Lucene��Query�кܶ��֣����ݲ�ͬ������ѡ�ò�ͬ��Query������.
            Query query = new TermQuery(new Term("content", "����"));
//          Query query = new TermQuery(new Term("title","key1 key2"));
//          Query query = new TermQuery(new Term("id","1")); //�鲻������װ��ԭ�� StringFiled textfield
            TopDocs topDocs = is.search(query, 10);  //core �����������searcher�����Ľ�����������һЩScoreDoc����������doc��Ա�������Id�ˣ�
            // Ҫ��õ����£���ô�͵���Ҫ�����Idȥȡ�����ˣ�searcher�ṩ����id�õ�document�ķ��������Ǿ�ȡ����������
            int hits = topDocs.totalHits;
            System.out.println("hits:" + hits);

            ScoreDoc[] scoreDocs = topDocs.scoreDocs; //core
            for (ScoreDoc sd : scoreDocs) {
                int docID = sd.doc;
                Document doc = is.doc(docID);  //core
                System.out.println(doc.get("id") + ":" + doc.get("name") + ":" + doc.get("content"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void searcherDemo(String indexPath){
        try {
            Directory dir = FSDirectory.open(new File(indexPath));
            IndexReader reader = DirectoryReader.open(dir);
            IndexSearcher is = new IndexSearcher(reader);

//			Query query = new TermQuery(new Term("id","2"));
            Query query = new TermQuery(new Term("content", "����"));
//			Query query = NumericRangeQuery.newIntRange("id", 2, 4, true, true);
            TopDocs topDocs = is.search(query, 10);

            int hits = topDocs.totalHits;
            System.out.println("hits:"+hits);
            ScoreDoc[] scoreDoc = topDocs.scoreDocs;
            for(ScoreDoc sd : scoreDoc){
                int docID = sd.doc;
                Document doc = is.doc(docID);
                System.out.println(doc.get("id")+":"+doc.get("name")+":"+doc.get("content"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
