package com.jack.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "jobinfo", type = "jobinfoField")
public class JobInfoField {
    @Id
    @Field(index = true, store = true, type = FieldType.Long)
    private Long id;
    @Field(index = true, store = true,analyzer = "ik_smart",searchAnalyzer = "ik_smart",type = FieldType.text)
    private String cname;
    @Field(index = true, store = true,analyzer = "ik_smart",searchAnalyzer = "ik_smart",type = FieldType.text)
    private String cinfo;
    @Field(index = true, store = true,analyzer = "ik_smart",searchAnalyzer = "ik_smart",type = FieldType.text)
    private String jname;
    @Field(index = true, store = true,analyzer = "ik_smart",searchAnalyzer = "ik_smart",type = FieldType.text)
    private String jaddr;
    @Field(index = true, store = true,analyzer = "ik_smart",searchAnalyzer = "ik_smart",type = FieldType.text)
    private String jinfo;
    @Field(index = true, store = true,analyzer = "ik_smart",searchAnalyzer = "ik_smart",type = FieldType.text)
    private String salary;
    @Field(index = true, store = true,analyzer = "ik_smart",searchAnalyzer = "ik_smart",type = FieldType.text)
    private String url;
    @Field(index = true, store = true,analyzer = "ik_smart",searchAnalyzer = "ik_smart",type = FieldType.text)
    private String time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCinfo() {
        return cinfo;
    }

    public void setCinfo(String cinfo) {
        this.cinfo = cinfo;
    }

    public String getJname() {
        return jname;
    }

    public void setJname(String jname) {
        this.jname = jname;
    }

    public String getJaddr() {
        return jaddr;
    }

    public void setJaddr(String jaddr) {
        this.jaddr = jaddr;
    }

    public String getJinfo() {
        return jinfo;
    }

    public void setJinfo(String jinfo) {
        this.jinfo = jinfo;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

