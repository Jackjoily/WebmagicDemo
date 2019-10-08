package com.jack.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

@Component
public class SpiderTest implements PageProcessor {
    private Site site = Site.me();

    @Override
    public void process(Page page) {

        System.out.println(page.getHtml().toString());
    }

    @Override
    public Site getSite() {
        return site;
    }

//    @Scheduled(initialDelay = 1000, fixedDelay = 1000)
    public void Task() {
        HttpClientDownloader httpClientDownloader=new HttpClientDownloader();
        httpClientDownloader.setProxyProvider( SimpleProxyProvider.from(new Proxy("180.118.134.60",9000)));
        //给下载器设置代理
        Spider.create(new SpiderTest())
                .addUrl("http://45.32.164.128/ip.php")
                .setDownloader(httpClientDownloader)
                .run();
    }
}
