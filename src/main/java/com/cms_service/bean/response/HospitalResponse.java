package com.cms_service.bean.response;

import com.cms_service.bean.model.MedicalRecord;

import java.util.List;

public class HospitalResponse {





    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEtype() {
        return etype;
    }

    public void setEtype(String etype) {
        this.etype = etype;
    }
    List<MedicalRecord> medicalRecordEntities;
    private String eid;

    @Override
    public String toString() {
        return "HospitalResponse{" +
                "medicalRecordEntities=" + medicalRecordEntities +
                ", eid='" + eid + '\'' +
                ", ename='" + ename + '\'' +
                ", etype='" + etype + '\'' +
                '}';
    }

    private String ename;

    public List<MedicalRecord> getMedicalRecordEntities() {
        return medicalRecordEntities;
    }

    public void setMedicalRecordEntities(List<MedicalRecord> medicalRecordEntities) {
        this.medicalRecordEntities = medicalRecordEntities;
    }

    private  String etype;



}
