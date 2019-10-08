import com.jack.Application;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.net.InetAddress;
import org.junit.After;
import org.junit.Before;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class SpringTest {
        private static String host="127.0.0.1"; // 服务器地址
        private static int port=9200; // 端口

        public static final String CLUSTER_NAME = "my-application"; //集群名称

        private TransportClient client=null;

        private static Settings settings= Settings.builder()
                .put("cluster.name",CLUSTER_NAME)
                .put("client.transport.sniff", true)
                .build();

        //获取客户端
        @SuppressWarnings({ "resource", "unchecked" })
        @Before
        public void getClient() throws Exception {
            try {
                client = new PreBuiltTransportClient(settings)
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host),port));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        //关闭客户端
        @After
        public void close() {
            if(client!=null) {
                client.close();
            }
        }

        /**
         * 查询所有
         */
        @Test
        public void searchAll() {
            SearchRequestBuilder srb=client.prepareSearch("java").setTypes("jobinfoField");
            SearchResponse sr=srb.setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();//查询所有
            SearchHits hits=sr.getHits();
            for(SearchHit hit:hits) {
                System.out.println(hit.getSourceAsString());
            }
        }
        /**
         * 分页查询
         */
        @Test
        public void searchPaging() {
            SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
            SearchResponse sr=srb.setQuery(QueryBuilders.matchAllQuery()).setFrom(0).setSize(2).execute().actionGet();
            SearchHits hits=sr.getHits();
            for (SearchHit hit : hits) {
                System.out.println(hit.getSourceAsString());
            }
        }
        /**
         * 排序查询
         */
        @Test
        public void searchOrderBy() {
            SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
            SearchResponse sr=srb.setQuery(QueryBuilders.matchAllQuery())
                    .addSort("publishDate", SortOrder.DESC).execute().actionGet();
            SearchHits hits=sr.getHits();
            for (SearchHit hit : hits) {
                System.out.println(hit.getSourceAsString());
            }
        }
        /**
         * 数据列过滤查询
         */
        @Test
        public void searchInclude() {
            SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
            SearchResponse sr=srb.setQuery(QueryBuilders.matchAllQuery())
                    .setFetchSource(new String[] {"title","price"},null)
                    .execute()
                    .actionGet();
            SearchHits hits=sr.getHits();
            for (SearchHit hit : hits) {
                System.out.println(hit.getSourceAsString());
            }
        }
        /***
         * 简单条件查询
         */
        @Test
        public void searchByCondition() {
            SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
            SearchResponse sr=srb.setQuery(QueryBuilders.matchQuery("title","铁"))
                    .setFetchSource(new String[] {"title","price"},null)
                    .execute()
                    .actionGet();
            SearchHits hits=sr.getHits();
            for (SearchHit hit : hits) {
                System.out.println(hit.getSourceAsString());
            }
        }
        /**
         * 条件查询高亮实现
         */
        @Test
        public void searchHighlight() {
            SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
            HighlightBuilder highlightBuilder=new HighlightBuilder();
            highlightBuilder.preTags("<h2>");
            highlightBuilder.postTags("</h2>");
            highlightBuilder.field("title");
            SearchResponse sr=srb.setQuery(QueryBuilders.matchQuery("title","战"))
                    .highlighter(highlightBuilder)
                    .setFetchSource(new String[] {"title","price"},null)
                    .execute()
                    .actionGet();
            SearchHits hits=sr.getHits();
            for (SearchHit hit : hits) {
                System.out.println(hit.getSourceAsString());
                System.out.println(hit.getHighlightFields());
            }
        }
        /**
         * 多条件查询  must
         */
        @Test
        public void searchMutil() {
            SearchRequestBuilder srb =client.prepareSearch("film").setTypes("dongzuo");
            QueryBuilder queryBuilder=QueryBuilders.matchPhraseQuery("title", "战");
            QueryBuilder queryBuilder2=QueryBuilders.matchPhraseQuery("content", "星球");
            SearchResponse sr=srb.setQuery(QueryBuilders.boolQuery()
                    .must(queryBuilder)
                    .must(queryBuilder2))
                    .execute()
                    .actionGet();
            SearchHits hits=sr.getHits();
            for (SearchHit hit : hits) {
                System.out.println(hit.getSourceAsString());
            }
        }

        /**
         * 多条件查询  mustNot
         */
        @Test
        public void searchMutil2() {
            SearchRequestBuilder srb =client.prepareSearch("film").setTypes("dongzuo");
            QueryBuilder queryBuilder=QueryBuilders.matchPhraseQuery("title", "战");
            QueryBuilder queryBuilder2=QueryBuilders.matchPhraseQuery("content", "武士");
            SearchResponse sr=srb.setQuery(QueryBuilders.boolQuery()
                    .must(queryBuilder)
                    .mustNot(queryBuilder2))
                    .execute()
                    .actionGet();
            SearchHits hits=sr.getHits();
            for (SearchHit hit : hits) {
                System.out.println(hit.getSourceAsString());
            }
        }

        /**
         * 多条件查询  should提高得分
         * @throws Exception
         */
        @Test
        public void searchMutil3()throws Exception{
            SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
            QueryBuilder queryBuilder=QueryBuilders.matchPhraseQuery("title", "战");
            QueryBuilder queryBuilder2=QueryBuilders.matchPhraseQuery("content", "星球");
            QueryBuilder queryBuilder3=QueryBuilders.rangeQuery("publishDate").gt("2018-01-01");
            SearchResponse sr=srb.setQuery(QueryBuilders.boolQuery()
                    .must(queryBuilder)
                    .should(queryBuilder2)
                    .should(queryBuilder3))
                    .execute()
                    .actionGet();
            SearchHits hits=sr.getHits();
            for(SearchHit hit:hits){
                System.out.println(hit.getScore()+":"+hit.getSourceAsString());
            }
        }
        /***
         * 多条件查询 range限制范围
         */
        @Test
        public void searchMutil4() {
            SearchRequestBuilder srb = client.prepareSearch("film").setTypes("dongzuo");
            QueryBuilder queryBuilder=QueryBuilders.matchPhraseQuery("title", "战");
            QueryBuilder queryBuilder2=QueryBuilders.rangeQuery("price").lte(40);
            SearchResponse sr=srb.setQuery(QueryBuilders.boolQuery()
                    .must(queryBuilder)
                    .filter(queryBuilder2))
                    .execute()
                    .actionGet();
            SearchHits hits=sr.getHits();
            for (SearchHit hit : hits) {
                System.out.println(hit.getSourceAsString());
            }
        }
    }
