package com.cms_service.service.serviceinterface;

import com.cms_service.bean.response.ConsentStats;
import com.cms_service.bean.response.DoctorConsentStats;
import com.cms_service.bean.response.ListActiveConsents;
import org.springframework.http.ResponseEntity;

public interface ActiveConsentService {

    public ListActiveConsents getActiveConsents(String pid);
    public ConsentStats getStats(String pid);
    public DoctorConsentStats getDoctorConsents(String did,String eid);
}
