package com.cms_service.service.serviceinterface;


import com.cms_service.bean.entity.ConsentLogEntity;
import com.cms_service.bean.entity.PendingRequestEntity;
import com.cms_service.bean.model.ConsentLog;


import java.util.List;

public interface ConsentLogsService {

    public List<PendingRequestEntity> getPendingRequests(String eid, String did);

    public List<ConsentLog> getConsentLogs(String eid, String did);


    public List<ConsentLog> getPatientConsentLogs(String pid);

    public ConsentLogEntity getConsentLogEntity(String cid);

}