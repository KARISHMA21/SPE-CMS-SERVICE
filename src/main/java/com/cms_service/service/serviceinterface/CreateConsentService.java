package com.cms_service.service.serviceinterface;

import com.cms_service.bean.entity.ConsentLogEntity;
import com.cms_service.bean.entity.ConsentMappingEntity;
import com.cms_service.bean.entity.PendingRequestEntity;
import com.cms_service.bean.model.ConsentActionRequestBody;
import com.cms_service.bean.response.MedicalRecordResponse;

import java.util.List;

public interface CreateConsentService {
    Integer createPendingRequest(PendingRequestEntity pendingRequestEntity);

    Integer createConsentMapping(ConsentMappingEntity consentMappingEntity);

    Integer createConsentLog(ConsentActionRequestBody consentActionRequestBody, PendingRequestEntity pendingRequestEntity);

    Integer dropPendingRequest(String pendingrequestid);

    List<ConsentLogEntity> getActiveConsents(String pid);

    Integer createEmergencyConsent(List<MedicalRecordResponse> medicalRecordResponseList, PendingRequestEntity pendingRequestEntity, String action);
}
