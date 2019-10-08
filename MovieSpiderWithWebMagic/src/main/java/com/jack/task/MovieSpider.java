package com.jack.task;


import org.jsoup.Jsoup;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Date;
import java.util.List;

@Component
public class MovieSpider implements  PageProcessor{

    private Site site=Site.me();

    @Override
    public void process(Page page) {
    List<String> movieUrlList=page.getHtml().css("div.co_content8 table  b a ").links().all();
     if(movieUrlList.size()==0){
         System.out.println(page.getUrl()+ Jsoup.parse(page.getHtml().css("div#Zoom table a").toString()).text());
     }else{
         System.out.println("==========================================================添加任务==============================================================");
         page.addTargetRequests(movieUrlList);
         System.out.println("========================================================解析到页面的下一页========================================================");
         String url=page.getHtml().css("div.x a").nodes().get(6).links().toString();
         page.addTargetRequest(url);
     }

    }

    @Override
    public Site getSite() {
        return site;
    }

    @Scheduled(initialDelay = 1000,fixedDelay = 1000)
    public void htmlParse(){
        Spider.create(new MovieSpider())
                .addUrl("https://www.ygdy8.com/html/gndy/dyzz/list_23_1.html")
                .thread(10)
                .run();
    }

}
