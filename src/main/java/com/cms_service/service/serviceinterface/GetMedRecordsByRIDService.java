package com.cms_service.service.serviceinterface;

import com.cms_service.bean.model.ConsentedRecordIDs;
import com.cms_service.bean.response.EntityResponse;
import com.cms_service.bean.response.MedicalRecordResponse;

import java.util.List;

public interface GetMedRecordsByRIDService {

    public MedicalRecordResponse getMedData(EntityResponse entityResponse, String pid, List<ConsentedRecordIDs>   RecordIds, List<String> Provider_ids);
}