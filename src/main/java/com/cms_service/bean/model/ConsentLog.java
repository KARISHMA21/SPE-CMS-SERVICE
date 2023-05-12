package com.cms_service.bean.model;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class ConsentLog {

    private String cid;
    private String pid;
    private String accessor_id;
    //    private String accessor_name;
    private String accessing_eid;
    //    private String accessing_ename;
    private Date last_update;

    private String status;
    private Date create_date;
    private Date expiry_date;
    private String action_taken_by;

    private String reason;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getAccessor_id() {
        return accessor_id;
    }

    public void setAccessor_id(String accessor_id) {
        this.accessor_id = accessor_id;
    }

    public String getAccessing_eid() {
        return accessing_eid;
    }

    public void setAccessing_eid(String accessing_eid) {
        this.accessing_eid = accessing_eid;
    }

    public Date getLast_update() {
        return last_update;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Date getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(Date expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getAction_taken_by() {
        return action_taken_by;
    }

    public void setAction_taken_by(String action_taken_by) {
        this.action_taken_by = action_taken_by;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ConsentLog(String cid, String pid, String accessor_id, String accessing_eid, Date last_update, String status, Date create_date, Date expiry_date, String action_taken_by, String reason) {
        this.cid = cid;
        this.pid = pid;
        this.accessor_id = accessor_id;
        this.accessing_eid = accessing_eid;
        this.last_update = last_update;
        this.status = status;
        this.create_date = create_date;
        this.expiry_date = expiry_date;
        this.action_taken_by = action_taken_by;
        this.reason = reason;
    }
//    @Override
//    public String toString() {
//        return "ConsentLog{" +
//                "cid=" + cid +
//                ", pid='" + pid + '\'' +
//                ", accessor_id='" + accessor_id + '\'' +
//                ", accessing_eid='" + accessing_eid + '\'' +
//                ", last_update='" + last_update + '\'' +
//                ", status='" + status + '\'' +
//                ", create_date='" + create_date + '\'' +
//                ", expiry_date='" + expiry_date + '\'' +
//                ", action_taken_by='" + action_taken_by + '\'' +
//                '}';
//    }
    @Override
    public String toString() {
        return "ConsentLog{" +
                "cid=" + cid +
                ", pid='" + pid + '\'' +
                ", accessor_id='" + accessor_id + '\'' +
                ", accessing_eid='" + accessing_eid + '\'' +
                ", last_update='" + last_update + '\'' +
                ", status='" + status + '\'' +
                ", create_date='" + create_date + '\'' +
                ", expiry_date='" + expiry_date + '\'' +
                ", action_taken_by='" + action_taken_by + '\'' +
                '}';
    }


}
