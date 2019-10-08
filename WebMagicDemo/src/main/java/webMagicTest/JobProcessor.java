package webMagicTest;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

public class JobProcessor implements PageProcessor {


    @Override
    public void process(Page page) {
        //解析page返回的数据page，并把解析的结果放到ResultItems中
        //使用css选择器
        //使用链式
//        page.putField("div", page.getHtml().css("div.co_content8>ul>table").regex(".*韩语.*").all());
        //使用xpath
        //获取一条数据（默认是第一条）
//        page.putField("div2", page.getHtml().xpath("div[@class='co_content8']/ul/table").get());
        //获取链接

//        page.addTargetRequests(page.getHtml().css("div.co_content8 > ul > table")
//                .links()
//                .all());
//        page.putField("url", page.getHtml().css("#Zoom > span > table > tbody > tr > td > a").all());
        page.addTargetRequest(new Request("https://www.ygdy8.net/html/gndy/dyzz/list_23_1.html"));
//        page.addTargetRequest(new Request("https://www.ygdy8.net/html/gndy/dyzz/list_23_1.html"));
//        page.addTargetRequest(new Request("https://www.ygdy8.net/html/gndy/dyzz/list_23_1.html"));
    }

    private Site site = Site.me()
            .setCharset("gb2312")
            .setTimeOut(10000)
            //设置重试间隔时间
            .setRetryTimes(10000)
            //设置重试次数
            .setSleepTime(3);

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new JobProcessor())
                .addUrl("https://www.ygdy8.net/html/gndy/dyzz/list_23_1.html")
//                .addPipeline(new FilePipeline("E:\\images\\"))
                //设置多线程
                .thread(5)
                //对多少条数据进行去重
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000)))
                .run();
    }
}
