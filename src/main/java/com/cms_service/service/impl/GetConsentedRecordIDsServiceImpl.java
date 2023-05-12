package com.cms_service.service.impl;

import com.cms_service.bean.model.ConsentedRecordIDs;
import com.cms_service.repository.ConsentLogRepository;
import com.cms_service.service.serviceinterface.GetConsentedRecordIDsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class GetConsentedRecordIDsServiceImpl implements GetConsentedRecordIDsService {
    @Autowired
    private ConsentLogRepository consentLogRepository;


    public List<ConsentedRecordIDs> getConsentedRecordIds(String eid, String did, String pid) {

        System.out.println("Entity Id: " + eid + " Requestor Did: " + did + " Patient ID: " + pid);
        List<ConsentedRecordIDs> recordIDs= null;
        recordIDs = consentLogRepository.getConsentedRecordList(eid, did, pid);
//        for (final ConsentedRecordIDs cs : consentrecords) {
//            System.out.println("Consented Records for CID: " + cs.() + " is " + cs.getStatus() + " Expiry data: " + cs.getExpiry());
//        }
        return recordIDs;

    }
    public List<ConsentedRecordIDs> getActiveConsentedRecordIds(String pid, String cid) {

//        System.out.println("Entity Id: " + eid + " Requestor Did: " + did + " Patient ID: " + pid);
        List<ConsentedRecordIDs> recordIDs= null;
        recordIDs = consentLogRepository.getActiveConsentedRecordList(pid,cid);
//        for (final ConsentedRecordIDs cs : consentrecords) {
//            System.out.println("Consented Records for CID: " + cs.() + " is " + cs.getStatus() + " Expiry data: " + cs.getExpiry());
//        }
        return recordIDs;

    }


}
