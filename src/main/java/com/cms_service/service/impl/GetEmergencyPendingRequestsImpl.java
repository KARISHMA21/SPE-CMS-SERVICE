package com.cms_service.service.impl;

import com.cms_service.bean.entity.PendingRequestEntity;
import com.cms_service.repository.PendingRequestRepository;
import com.cms_service.service.serviceinterface.GetEmergencyPendingRequests;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GetEmergencyPendingRequestsImpl implements GetEmergencyPendingRequests {
    PendingRequestRepository pendingRequestRepository;

    public GetEmergencyPendingRequestsImpl(PendingRequestRepository pendingRequestRepository) {
        this.pendingRequestRepository = pendingRequestRepository;
    }

    @Override
    public List<PendingRequestEntity> getEmergencyPendingRequests(String super_id) {
        return pendingRequestRepository.findBySuperidAndReason(super_id, "Emergency").orElse(null);
    }
}
