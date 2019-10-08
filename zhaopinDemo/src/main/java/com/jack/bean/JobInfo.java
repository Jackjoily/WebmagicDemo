package com.jack.bean;

import javax.persistence.*;

@Entity
@Table(name = "job_info")
public class JobInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="cname")
    private String cname;
    @Column(name="cinfo")
    private String cinfo;
    @Column(name="jname")
    private String jname;
    @Column(name="jaddr")
    private String jaddr;
    @Column(name="jinfo")
    private String jinfo;
    @Column(name="minsalary")
    private Integer minsalary;
    @Column(name="maxsalary")
    private Integer maxsalary;
    @Column(name="url")
    private String url;
    @Column(name="time")
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


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public Integer getMinsalary() {
        return minsalary;
    }

    public void setMinsalary(Integer minsalary) {
        this.minsalary = minsalary;
    }

    public Integer getMaxsalary() {
        return maxsalary;
    }

    public void setMaxsalary(Integer maxsalary) {
        this.maxsalary = maxsalary;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

