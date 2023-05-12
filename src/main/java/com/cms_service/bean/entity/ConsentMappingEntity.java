package com.cms_service.bean.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Entity
@Data //for getters and setters
@Table(name="consentmapping")
public class ConsentMappingEntity {

    public String getRecord_creator_id() {
        return record_creator_id;
    }

    public void setRecord_creator_id(String record_creator_id) {
        this.record_creator_id = record_creator_id;
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

    public int getActive_flag() {
        return active_flag;
    }

    public Consent_Comp_key getCid_rid_provider_eid() {
        return cid_rid_provider_eid;
    }

    public void setCid_rid_provider_eid(Consent_Comp_key cid_rid_provider_eid) {
        this.cid_rid_provider_eid = cid_rid_provider_eid;
    }

    public void setActive_flag(int active_flag) {
        this.active_flag = active_flag;
    }

    @EmbeddedId
    private Consent_Comp_key cid_rid_provider_eid;
    private String record_creator_id;
    private String tag1;
    private String tag2;
    private String tag3;
    private int active_flag;

    public ConsentLogEntity getConsentlog() {
        return Consentlog;
    }

    public void setConsentlog(ConsentLogEntity consentlog) {
        Consentlog = consentlog;

    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumns({
            @JoinColumn(name = "cid", referencedColumnName = "cid", insertable = false, updatable = false)
    })
    private ConsentLogEntity Consentlog;

}
