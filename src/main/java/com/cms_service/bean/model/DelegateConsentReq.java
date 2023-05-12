package com.cms_service.bean.model;

import java.util.Objects;

public class DelegateConsentReq {
    private String new_accessor_id;
    private String new_accessor_name;
    private String old_accessor_id;
    private String cid;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getNew_accessor_id() {
        return new_accessor_id;
    }

    public void setNew_accessor_id(String new_accessor_id) {
        this.new_accessor_id = new_accessor_id;
    }

    public String getNew_accessor_name() {
        return new_accessor_name;
    }

    public void setNew_accessor_name(String new_accessor_name) {
        this.new_accessor_name = new_accessor_name;
    }

    public String getOld_accessor_id() {
        return old_accessor_id;
    }

    public void setOld_accessor_id(String old_accessor_id) {
        this.old_accessor_id = old_accessor_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DelegateConsentReq that = (DelegateConsentReq) o;
        return Objects.equals(new_accessor_id, that.new_accessor_id) && Objects.equals(new_accessor_name, that.new_accessor_name) && Objects.equals(old_accessor_id, that.old_accessor_id) && Objects.equals(cid, that.cid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(new_accessor_id, new_accessor_name, old_accessor_id, cid);
    }

    @Override
    public String toString() {
        return "DelegateConsentReq{" +
                "new_accessor_id='" + new_accessor_id + '\'' +
                ", new_accessor_name='" + new_accessor_name + '\'' +
                ", old_accessor_id='" + old_accessor_id + '\'' +
                ", cid='" + cid + '\'' +
                '}';
    }
}
