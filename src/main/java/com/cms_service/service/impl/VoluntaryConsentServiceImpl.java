package com.cms_service.service.impl;

import com.cms_service.bean.entity.ConsentLogEntity;
import com.cms_service.bean.entity.ConsentMappingEntity;
import com.cms_service.bean.entity.Consent_Comp_key;
import com.cms_service.bean.model.VoluntaryConsent;
import com.cms_service.repository.ConsentLogRepository;
import com.cms_service.repository.ConsentMappingRepository;
import com.cms_service.service.serviceinterface.VoluntaryConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
@Service
public class VoluntaryConsentServiceImpl implements VoluntaryConsentService {
    @Autowired
    private ConsentLogRepository consentLogRepository;
    @Autowired
    private ConsentMappingRepository consentMappingRepository;
    public void saveVoluntaryConsent(List<VoluntaryConsent> voluntaryConsent) {

//        for (final VoluntaryConsent v : voluntaryConsent) {
//            System.out.println(voluntaryConsent.get(0).getAccessing_eid());
//        }
        System.out.println("Last update"+voluntaryConsent.get(0).getLast_update());
        System.out.println("Create Date"+voluntaryConsent.get(0).getCreate_date());
        System.out.println("Expiry Date"+voluntaryConsent.get(0).getExpiry_date());
        DateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        try {

            ConsentLogEntity consentLogEntity = new ConsentLogEntity();
            consentLogEntity.setAccessing_eid(voluntaryConsent.get(0).getAccessing_eid());
            consentLogEntity.setCid(voluntaryConsent.get(0).getCid());
            consentLogEntity.setAccessor_id(voluntaryConsent.get(0).getAccessor_id());
            consentLogEntity.setPid(voluntaryConsent.get(0).getPid());
            consentLogEntity.setCreate_date(simpleDateFormat.parse(voluntaryConsent.get(0).getCreate_date()));
            consentLogEntity.setStatus(voluntaryConsent.get(0).getStatus());
            consentLogEntity.setReason(voluntaryConsent.get(0).getReason());
            consentLogEntity.setLast_update(simpleDateFormat.parse(voluntaryConsent.get(0).getLast_update()));
            consentLogEntity.setExpiry_date(simpleDateFormat.parse(voluntaryConsent.get(0).getExpiry_date()));
            consentLogEntity.setAction_taken_by(voluntaryConsent.get(0).getAction_taken_by());
            consentLogEntity.setTag1(voluntaryConsent.get(0).getConsent_tag1());
            consentLogEntity.setTag2(voluntaryConsent.get(0).getConsent_tag2());
            consentLogEntity.setTag3(voluntaryConsent.get(0).getConsent_tag3());
            consentLogEntity.setAccessor_name(voluntaryConsent.get(0).getAccessor_name());
            consentLogEntity.setAccessing_ename(voluntaryConsent.get(0).getAccessing_ename());
            System.out.println(consentLogEntity);
            consentLogRepository.save(consentLogEntity);
            for (final VoluntaryConsent v : voluntaryConsent) {
                ConsentMappingEntity consentMappingEntity = new ConsentMappingEntity();
                Consent_Comp_key consentCompKey = new Consent_Comp_key();
                consentCompKey.setCid(v.getCid());
                consentCompKey.setRecord_id(v.getRid());
                consentCompKey.setProvider_eid(v.getProvider_eid());
                consentMappingEntity.setCid_rid_provider_eid(consentCompKey);
                consentMappingEntity.setTag1(v.getTag1());
                consentMappingEntity.setTag2(v.getTag2());
                consentMappingEntity.setTag3(v.getTag3());
                consentMappingEntity.setRecord_creator_id(v.getRecord_creator_id());
//                consentMappingEntity.setProvider_eid(v.getProvider_eid());
                consentMappingEntity.setActive_flag(v.getActive_flag());
                consentMappingRepository.save(consentMappingEntity);
//            System.out.println("hi");
            }
        }
        catch(Exception e){
            System.out.println(e);
            return;
        }
    }
}
