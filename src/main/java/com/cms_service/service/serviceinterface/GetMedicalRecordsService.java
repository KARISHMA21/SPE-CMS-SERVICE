package com.cms_service.service.serviceinterface;
import com.cms_service.bean.model.AdminData;
import com.cms_service.bean.response.MedicalRecordResponse;

import java.util.Date;
import java.util.List;

public interface GetMedicalRecordsService {
   String getMedicalRecords(List<AdminData> adminData,String pid);

   List<MedicalRecordResponse> getMedicalRecordsForPendingReq(String pid, String tag1, String tag2, String tag3, Date from, Date to);
}
