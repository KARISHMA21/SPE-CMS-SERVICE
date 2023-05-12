package com.cms_service.bean.response;

import java.io.Serializable;

public class AdminResponse implements Serializable {
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

    public String getEip() {
        return eip;
    }

    public void setEip(String eip) {
        this.eip = eip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

//    public List<Patients> getPatientIds() {

    @Override
    public String toString() {
        return "AdminResponse{" +
                "eid='" + eid + '\'' +
                ", ename='" + ename + '\'' +
                ", etype='" + etype + '\'' +
                ", eip='" + eip + '\'' +
                ", port=" + port +
                '}';
    }
//        return patientIds;
//    }
//
//    public void setPatientIds(List<Patients> patientIds) {
//        this.patientIds = patientIds;
//    }

    private String eid;
    private  String ename;
    private  String etype;
    private String eip;
    private int port;

}
