package com.cms_service.service.serviceinterface;

import com.cms_service.bean.response.EntityResponse;

import java.util.List;

public interface ConsentedEntityConnectionService {
    public EntityResponse getConnectionDetails(List<String> Provider_eid);
}
