package com.cms_service.bean.model;

public class AdminData {
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

    private String eid;

    public AdminData(String eid, String ename, String etype, String eip, int port) {
        this.eid = eid;
        this.ename = ename;
        this.etype = etype;
        this.eip = eip;
        this.port = port;
    }

    private  String ename;
    private  String etype;
    private String eip;
    private int port;
}
