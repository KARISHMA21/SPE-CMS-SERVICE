package com.cms_service.bean.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecord {
    @Override
    public String toString() {
        return "MedicalRecord{" +
                "rid='" + rid + '\'' +
                ", pid='" + pid + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", gen_date='" + gen_date + '\'' +
                ", tag1='" + tag1 + '\'' +
                ", tag2='" + tag2 + '\'' +
                ", tag3='" + tag3 + '\'' +
                ", doctorEntity=" + doctorEntity +
                '}';
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTag1() {
        return tag1;
    }

    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }

    public String getTag2() {
        return tag2;
    }

    public void setTag2(String tag2) {
        this.tag2 = tag2;
    }

    public String getTag3() {
        return tag3;
    }

    public void setTag3(String tag3) {
        this.tag3 = tag3;
    }

    public DoctorEntity getDoctorEntity() {
        return doctorEntity;
    }

    public void setDoctorEntity(DoctorEntity doctorEntity) {
        this.doctorEntity = doctorEntity;
    }
    public Date getGen_date() {
        return gen_date;
    }
    public void setGen_date(Date gen_date) {
        this.gen_date = gen_date;
    }
    private String rid;
    private String pid;
    private String description;
    private String type;
    private Date gen_date;
    private String tag1;
    private String tag2;
    private String tag3;

    private DoctorEntity doctorEntity;
}
