package com.jack.task;

import com.jack.bean.JobInfo;
import com.jack.utils.MathSalary;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

@Component
public class JobProcessor implements PageProcessor {

    @Autowired
    SpringDataPipelIne pipelIne;
    private String url = "https://search.51job.com/list/080200,000000,0000,00,9,99,java,2,1.html?lang=c&stype=&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&providesalary=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=";

    @Override
    public void process(Page page) {
        //解析页面，获取招聘详情的url地址
        List<Selectable> list = page.getHtml().css("div#resultList div.el").nodes();
        if (list.size() == 0) {
//如果为空的话，表示这是招聘详情页，获取招聘详细信息
            this.save(page);
        } else {
//如果不是，这是列表页，解析如详情页面的url，放到任务队列中
            for (Selectable selectable : list) {
                String jobInfoUrl = selectable.links().toString();
                //将获取到url地址放到任务队列中
                page.addTargetRequest(jobInfoUrl);
            }
            //获取下一页的url
            String bkurl = page.getHtml().css("div.p_in li.bk").nodes().get(1).links().toString();
            //把url放到任务队列中
            page.addTargetRequest(bkurl);

        }
    }

    //解析页面，获取招聘的详情页面
    private void save(Page page) {
        Html html = page.getHtml();
        //创建招聘详情对象
        JobInfo jobInfo = new JobInfo();
        jobInfo.setCname(html.css("div.cn p.cname a", "title").toString());
        jobInfo.setJaddr(Jsoup.parse(html.css("div.bmsg").nodes().get(1).css("p.fp").toString()).text());
        jobInfo.setCinfo(Jsoup.parse(html.css("div.tmsg").toString()).text());
        jobInfo.setJname(html.css("div.cn h1", "title").toString());
        float salary[]=MathSalary.getSalary(Jsoup.parse(html.css("div.cn strong").toString()).text());
        jobInfo.setMinsalary((int)salary[0]);
        jobInfo.setMaxsalary((int)salary[1]);
        jobInfo.setJinfo(Jsoup.parse(html.css("div.bmsg.job_msg.inbox").toString()).text());
        jobInfo.setUrl(page.getUrl().toString());
        page.putField("jobInfo", jobInfo);
    }
    private Site site = Site.me()
            .setCharset("gbk")
            .setTimeOut(10000)
            .setRetryTimes(3000)
            .setSleepTime(3);

    @Override
    public Site getSite() {
        return site;
    }

    /**
     * initialDelay启动后，等待多久执行方法
     * fixedDelay每隔多久执行方法
     */
    @Scheduled(initialDelay = 1000, fixedDelay = 100000)
    public void process() {
        Spider.create(new JobProcessor())
                .addUrl(url)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .thread(10)
                .addPipeline(pipelIne)
                .run();

    }

}
