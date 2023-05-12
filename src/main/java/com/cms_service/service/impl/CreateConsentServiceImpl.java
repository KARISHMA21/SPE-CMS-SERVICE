package com.cms_service.service.impl;

import com.cms_service.bean.entity.ConsentLogEntity;
import com.cms_service.bean.entity.ConsentMappingEntity;
import com.cms_service.bean.entity.Consent_Comp_key;
import com.cms_service.bean.entity.PendingRequestEntity;
import com.cms_service.bean.model.ConsentActionRequestBody;
import com.cms_service.bean.model.FinalRecords;
import com.cms_service.bean.response.MedicalRecordResponse;
import com.cms_service.repository.ConsentLogRepository;
import com.cms_service.repository.ConsentMappingRepository;
import com.cms_service.repository.PendingRequestRepository;
import com.cms_service.service.serviceinterface.CreateConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CreateConsentServiceImpl implements CreateConsentService {
    @Autowired
    private PendingRequestRepository pendingRequestRepository;
    @Autowired
    private ConsentMappingRepository consentMappingRepository;
    @Autowired
    private ConsentLogRepository consentLogRepository;

    public CreateConsentServiceImpl(PendingRequestRepository pendingRequestRepository, ConsentMappingRepository consentMappingRepository, ConsentLogRepository consentLogRepository) {
        this.pendingRequestRepository = pendingRequestRepository;
        this.consentMappingRepository = consentMappingRepository;
        this.consentLogRepository = consentLogRepository;
    }

    public Integer createPendingRequest(PendingRequestEntity pendingRequestEntity){
        try{
            pendingRequestRepository.save(pendingRequestEntity);
            return 1;
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    @Override
    public Integer createConsentMapping(ConsentMappingEntity consentMappingEntity) {
        try{
            consentMappingRepository.save(consentMappingEntity);
            return 1;
        }
        catch(Exception e) {
            return 0;
        }
    }

    @Override
    public Integer createConsentLog(ConsentActionRequestBody consentActionRequestBody, PendingRequestEntity pendingRequestEntity) {
        try{
            ConsentLogEntity consentLogEntity = new ConsentLogEntity();
            consentLogEntity.setCid("CID"+pendingRequestEntity.getPendingRequestId().substring(2));
            consentLogEntity.setAction_taken_by(consentActionRequestBody.getAction_taken_by());
            consentLogEntity.setReason(pendingRequestEntity.getReason());
            consentLogEntity.setPid(pendingRequestEntity.getPid());
            consentLogEntity.setCreate_date(new Date());
            consentLogEntity.setAccessing_eid(pendingRequestEntity.getRequestor_eid());
            consentLogEntity.setAccessor_id(pendingRequestEntity.getRequestor_id());
            consentLogEntity.setLast_update(new Date());
            consentLogEntity.setExpiry_date(consentActionRequestBody.getExpiry_date());
            consentLogEntity.setAccessor_name(pendingRequestEntity.getRequestor_name());
            consentLogEntity.setAccessing_ename(pendingRequestEntity.getRequestor_ename());
            consentLogEntity.setTag1(pendingRequestEntity.getTag1());
            consentLogEntity.setTag2(pendingRequestEntity.getTag2());
            consentLogEntity.setTag3(pendingRequestEntity.getTag3());
            if (consentActionRequestBody.getStatus().equals("Rejected")) {
                consentLogEntity.setStatus("Rejected");
                consentLogRepository.save(consentLogEntity);
            }
            else{
                if(consentActionRequestBody.getStatus().equals("Emergency")){
                    consentLogEntity.setStatus("Emergency");
                }
                else{
                    consentLogEntity.setStatus("Active");
                }
                consentLogRepository.save(consentLogEntity);
                ConsentMappingEntity consentMappingEntity = new ConsentMappingEntity();
                List<List<String>> records = consentActionRequestBody.getRid();
                for (List<String> record : records){
                    consentMappingEntity.setTag1(pendingRequestEntity.getTag1());
                    consentMappingEntity.setTag2(pendingRequestEntity.getTag2());
                    consentMappingEntity.setTag3(pendingRequestEntity.getTag3());
                    consentMappingEntity.setActive_flag(1);
                    consentMappingEntity.setRecord_creator_id(record.get(1));
                    // the conset_comp_key is (cid,rid) where the cid is currently the pending request id itself
                    consentMappingEntity.setCid_rid_provider_eid(new Consent_Comp_key("CID"+pendingRequestEntity.getPendingRequestId().substring(2),record.get(0),record.get(2)));
                    createConsentMapping(consentMappingEntity);
                }
            }
            return 1;
        }
        catch(Exception e) {
            return 0;
        }
    }

    @Override
    public Integer dropPendingRequest(String pendingrequestid) {
        pendingRequestRepository.deleteById(pendingrequestid);
        return 1;
    }

    @Override
    public List<ConsentLogEntity> getActiveConsents(String pid) {
        return consentLogRepository.findAllByPidAndStatus(pid, "Active").orElse(null);
    }

    @Override
    public Integer createEmergencyConsent(List<MedicalRecordResponse> medicalRecordResponseList, PendingRequestEntity pendingRequestEntity,String action){
        MedicalRecordResponse Y_Tags_Y_Date = medicalRecordResponseList.get(0), N_Tags_Y_Date = medicalRecordResponseList.get(1);
        List<FinalRecords> Y_Date = Y_Tags_Y_Date.getFinalRecordsList();
        Y_Date.addAll(N_Tags_Y_Date.getFinalRecordsList());
        List<List<String>> records= new ArrayList<>();
        for(FinalRecords f: Y_Date){
            List<String> record = new ArrayList<>();
            record.add(f.getRid());
            record.add(f.getDid());
            record.add(f.getEid());
            records.add(record);
        }
        ConsentActionRequestBody consentActionRequestBody = new ConsentActionRequestBody(
            records,
            pendingRequestEntity.getSuperid(),
            action,
            pendingRequestEntity.getPendingRequestId(),
            pendingRequestEntity.getExpiry_date()
        );
        return createConsentLog(consentActionRequestBody,pendingRequestEntity);
    }
}
