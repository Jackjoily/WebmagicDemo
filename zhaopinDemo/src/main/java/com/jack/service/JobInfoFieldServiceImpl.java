package com.jack.service;

import com.jack.bean.JobInfo;
import com.jack.bean.JobInfoField;
import com.jack.dao.JobInfoFieldDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobInfoFieldServiceImpl implements JobInfoFieldService {
    @Autowired
    JobInfoFieldDao dao;

    @Override
    public void save(JobInfoField field) {
        this.dao.save(field);
    }

    @Override
    public void saveAll(List<JobInfoField> jobInfoFieldsList) {
        this.dao.saveAll(jobInfoFieldsList);
    }
}
