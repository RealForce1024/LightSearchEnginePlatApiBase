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
    public static void searchIndex(String indexPath){
        // IndexSearcher  // 索引的搜索器，就是把用户输入拿到索引列表中搜索的一个类 需要注意的是，这个搜索出来的就是（TopDocs）索引号，还不是真正的文章。
        try {
            Directory dir  = FSDirectory.open(new File(indexPath));
            IndexReader ir = DirectoryReader.open(dir); //索引读取器，用于读取指定目录的索引。
            IndexSearcher is = new IndexSearcher(ir);  //core
            Query query = NumericRangeQuery.newIntRange("id", 1, 2, true, true);//查询语句，我们需要把我们的查询String封装成Query才可以交给Searcher来搜索 ,查询的最小单元是Term，Lucene的Query有很多种，根据不同的需求选用不同的Query就是了.

//            Query query = new TermQuery(new Term("content","key4"));
//            Query query = new TermQuery(new Term("title","key1 key2"));

//            Query query = new TermQuery(new Term("id","1")); //查不出，封装的原因
            TopDocs topDocs = is.search(query, 10);  //core 结果集，就是searcher搜索的结果，里面就是一些ScoreDoc，这个对象的doc成员就是这个Id了！

                                                    // 要想得到文章，那么就得需要用这个Id去取文章了，searcher提供了用id得到document的方法，于是就取到了数据了
            int hits = topDocs.totalHits;
            System.out.println("hits:"+hits);

            ScoreDoc[] scoreDocs = topDocs.scoreDocs; //core
            for (ScoreDoc sd : scoreDocs){
                int docID = sd.doc;
                Document doc = is.doc(docID);  //core
                System.out.println(doc.get("id")+":"+doc.get("title")+":"+doc.get("content"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
