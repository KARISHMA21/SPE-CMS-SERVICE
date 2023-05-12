package com.cms_service.service.serviceinterface;

import com.cms_service.bean.model.ConsentedRecordIDs;

import java.util.List;

public interface GetConsentedRecordIDsService {

//    public List<ConsentedRecordIDs>  getConsentedRecordIds(String eid, String did, String pid) ;
public  List<ConsentedRecordIDs>   getConsentedRecordIds(String eid, String did, String pid) ;
//    public MedicalRecordResponse getConsentedData(String eid, String did, String pid);
public List<ConsentedRecordIDs> getActiveConsentedRecordIds(String pid, String cid);
}
