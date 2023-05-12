package com.cms_service.service.impl;

import com.cms_service.bean.entity.PendingRequestEntity;
import com.cms_service.repository.PendingRequestRepository;
import com.cms_service.service.serviceinterface.GetPendingRequests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetPendingRequestsImpl implements GetPendingRequests {
    @Autowired
    PendingRequestRepository pendingRequestRepository;

    public GetPendingRequestsImpl(PendingRequestRepository pendingRequestRepository) {
        this.pendingRequestRepository = pendingRequestRepository;
    }

    @Override
    public List<PendingRequestEntity> getPendingRequests(String pid) {
        return pendingRequestRepository.findByPid(pid).orElse(null);
    }
}
