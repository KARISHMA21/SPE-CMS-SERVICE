package com.cms_service.service.impl;

import com.cms_service.bean.entity.PendingRequestEntity;
import com.cms_service.repository.PendingRequestRepository;
import com.cms_service.service.serviceinterface.PendingReqInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PendingReqInstanceImpl implements PendingReqInstance {
    @Autowired
    PendingRequestRepository pendingRequestRepository;

    public PendingReqInstanceImpl(PendingRequestRepository pendingRequestRepository) {
        this.pendingRequestRepository = pendingRequestRepository;
    }

    @Override
    public PendingRequestEntity getPendingRequestInstance(String pendingrequestid) {
        return pendingRequestRepository.findByPendingRequestId(pendingrequestid);
    }

    @Override
    public List<PendingRequestEntity> getPendingRequests(String pid) {
        return null;
    }
}
