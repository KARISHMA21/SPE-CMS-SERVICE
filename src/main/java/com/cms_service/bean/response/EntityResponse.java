package com.cms_service.bean.response;

import com.cms_service.bean.model.AdminData;

import java.util.List;

public class EntityResponse {

    public List<AdminData> getEntityRegs() {
        return entityRegs;
    }

    public void setEntityRegs(List<AdminData> entityRegs) {
        this.entityRegs = entityRegs;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    List<AdminData> entityRegs;
    private String pid;
}
