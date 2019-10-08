package com.jack.service;

import com.jack.bean.JobInfoField;

import java.util.List;

public interface JobInfoFieldService {
    /**
     * 保存一条数据
     * @param field
     */
    void save(JobInfoField field) ;
    /**
     * 批量保存数据
     * @param field
     */
    void saveAll(List<JobInfoField> jobInfoFieldsList);
}
