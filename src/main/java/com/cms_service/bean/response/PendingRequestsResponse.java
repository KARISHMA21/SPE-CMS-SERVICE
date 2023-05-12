package com.cms_service.bean.response;

import com.cms_service.bean.entity.PendingRequestEntity;

import java.util.List;

public class PendingRequestsResponse {

    public List<PendingRequestEntity> getPendingRequests() {
        return pendingRequests;
    }

    public void setPendingRequests(List<PendingRequestEntity> pendingRequests) {
        this.pendingRequests = pendingRequests;
    }

    List<PendingRequestEntity> pendingRequests;

    @Override
    public String toString() {
        return "PendingReqResponse{" +
                "pendingRequests=" + pendingRequests +
//                ", pid='" + pid + '\'' +
                '}';
    }
}
