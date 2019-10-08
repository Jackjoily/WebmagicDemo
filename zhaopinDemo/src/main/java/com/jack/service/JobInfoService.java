package com.jack.service;

import com.jack.bean.JobInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface JobInfoService {
    public void save(JobInfo info);
    public List<JobInfo> findJobinfo(JobInfo info);

    Page<JobInfo> findJobinfoByPage(int i, int i1);
}
