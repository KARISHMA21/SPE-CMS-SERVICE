package com.cms_service.bean.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class Consent_Comp_key implements Serializable {
    public Consent_Comp_key(String cid, String record_id,String provider_eid) {
        this.cid = cid;
        this.record_id = record_id;
        this.provider_eid=provider_eid;
    }
//    SongId songId = new SongId("test_name", "test_album", "test_artist");
//    Song song = new Song(songId, 23, null, null, 4, "http://download.this.song");
    public Consent_Comp_key(){
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }



    public String getRecord_id() {
        return record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }
    private String cid;

    private String record_id;

    public String getProvider_eid() {
        return provider_eid;
    }

    public void setProvider_eid(String provider_eid) {
        this.provider_eid = provider_eid;
    }

    private String provider_eid;

}

