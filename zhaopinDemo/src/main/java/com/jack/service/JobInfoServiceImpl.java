package com.jack.service;

import com.jack.bean.JobInfo;
import com.jack.dao.JobInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JobInfoServiceImpl implements JobInfoService {

    @Autowired
    private JobInfoDao dao;

    @Override
    public void save(JobInfo info) {
        //根据Url和发布时间查询数据
        JobInfo param=new JobInfo();
        param.setUrl(info.getUrl());
        param.setTime(info.getTime());
        List<JobInfo> list=this.findJobinfo(param);
        if(list.size()==0){
            this.dao.save(info);
        }
        //判断数据库中是否有已经存在的数据
    }

    @Override
    public List<JobInfo> findJobinfo(JobInfo info) {
        Example example = Example.of(info);
        List<JobInfo> list = this.dao.findAll(example);
        return list;
    }

    @Override
    public Page<JobInfo> findJobinfoByPage(int i, int i1) {
        Page<JobInfo> pages= this.dao.findAll(PageRequest.of(i-1, i1));
         return pages;
    }
}
