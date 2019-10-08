package com.jack.dao;

import com.jack.bean.JobInfoField;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface JobInfoFieldDao extends ElasticsearchRepository<JobInfoField,Long> {
}
