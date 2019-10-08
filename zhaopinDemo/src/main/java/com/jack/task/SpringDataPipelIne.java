package com.jack.task;

import com.jack.bean.JobInfo;
import com.jack.service.JobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
public class SpringDataPipelIne implements Pipeline {

    @Autowired
    private JobInfoService infoService;

    /**
     * @param resultItems 保存的结果集
     * @param task
     */
    @Override
    public void process(ResultItems resultItems, Task task) {
        //获取封装好的详情对象
        JobInfo jobInfo = resultItems.get("jobInfo");
        //如果数据不为空
        if(jobInfo!=null){
            infoService.save(jobInfo);
        }
    }
}
