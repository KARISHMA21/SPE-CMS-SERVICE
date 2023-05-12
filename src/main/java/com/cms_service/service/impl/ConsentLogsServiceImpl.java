package com.cms_service.service.impl;


import com.cms_service.bean.entity.ConsentLogEntity;
import com.cms_service.bean.entity.PendingRequestEntity;
import com.cms_service.bean.model.ConsentLog;
import com.cms_service.repository.ConsentLogRepository;
import com.cms_service.repository.PendingRequestRepository;
import com.cms_service.service.serviceinterface.ConsentLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ConsentLogsServiceImpl implements ConsentLogsService {

    @Autowired
    private PendingRequestRepository pendingreqrepository;

    @Autowired
    private ConsentLogRepository consentlogrepository;

    public List<PendingRequestEntity> getPendingRequests(String eid, String did){
        List<PendingRequestEntity> PendingRequests= null;
        System.out.println("Fetching the pending requests for Requestor: "+did+ " of Entity: "+eid+" \n");
        PendingRequests=pendingreqrepository.getPendingRequests(eid,did);
        System.out.println("Pending requests are ----> \n "+PendingRequests);
        return PendingRequests;



    }

    public List<ConsentLog> getConsentLogs(String eid, String did){
        List<ConsentLog> ConsentLogs= null;
        System.out.println("Fetching the pending requests for Requestor: "+did+ " of Entity: "+eid+" \n");
        ConsentLogs=consentlogrepository.getConsentLogs(eid,did);
        System.out.println("Consent history of "+did+" ----> \n "+ConsentLogs);
        return ConsentLogs;

    }
    public List<ConsentLog> getPatientConsentLogs(String pid){
        List<ConsentLog> ConsentLogs= null;
        System.out.println("Fetching the pending requests for patient: "+pid+" \n");
        ConsentLogs=consentlogrepository.getPatientConsentLogs(pid);
        System.out.println("Consent history of "+pid+" ----> \n "+ConsentLogs);
        return ConsentLogs;

    }

    @Override
    public ConsentLogEntity getConsentLogEntity(String cid) {
        return consentlogrepository.findByCid(cid);
    }

}
