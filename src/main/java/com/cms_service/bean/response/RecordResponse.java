package com.cms_service.bean.response;

import java.util.List;

public class RecordResponse {




    public String getPid() {
        return pid;
    }

    @Override
    public String toString() {
        return "RecordResponse{" +
                "hospitalResponses=" + hospitalResponses +
                ", pid='" + pid + '\'' +
                '}';
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public List<HospitalResponse> getHospitalResponses() {
        return hospitalResponses;
    }

    public void setHospitalResponses(List<HospitalResponse> hospitalResponses) {
        this.hospitalResponses = hospitalResponses;
    }

    List<HospitalResponse> hospitalResponses;
    private String pid;
}
