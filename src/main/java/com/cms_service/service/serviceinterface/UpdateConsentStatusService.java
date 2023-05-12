package com.cms_service.service.serviceinterface;


import com.cms_service.bean.model.ActiveConsentRecords;
import com.cms_service.bean.model.ActiveConsents;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UpdateConsentStatusService {

    public void UpdateStatus();

    public int updateActiveConsentRecords(List<ActiveConsentRecords> activeConsentRecordsList);
    public int revokeConsent(ActiveConsents activeConsent);

}
