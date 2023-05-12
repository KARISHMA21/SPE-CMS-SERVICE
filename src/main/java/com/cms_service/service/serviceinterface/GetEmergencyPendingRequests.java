package com.cms_service.service.serviceinterface;

import com.cms_service.bean.entity.PendingRequestEntity;

import java.util.List;

public interface GetEmergencyPendingRequests {
    List<PendingRequestEntity> getEmergencyPendingRequests(String super_id);
}
