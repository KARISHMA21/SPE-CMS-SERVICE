package com.cms_service.bean.model;

import com.cms_service.bean.entity.Consent_Comp_key;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConsentedRecordIDs {
//    private Consent_Comp_key cid_rid;
    private String record_id;
    private String provider_eid;

    public String getRecord_id() {
        return record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }

    public String getProvider_eid() {
        return provider_eid;
    }

    public void setProvider_eid(String provider_eid) {
        this.provider_eid = provider_eid;
    }

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

    private String cid;

    public ConsentedRecordIDs(String record_id, String provider_eid, String cid, String pid) {
        this.record_id = record_id;
        this.provider_eid = provider_eid;
        this.cid = cid;
        this.pid = pid;
    }

    private String pid;

//    @Override
//    public String toString() {
//        return "ConsentedRecordIDs{" +
//                "rid='" + record_id + '\'' +
//                ", provider_eid='" + provider_eid + '\'' +
//                ", cid='" + cid + '\'' +
//                ", pid='" + pid + '\'' +
//
//                '}';
//    }
}
