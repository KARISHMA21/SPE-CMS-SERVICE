package com.cms_service.service.impl;

import com.cms_service.bean.entity.ConsentLogEntity;
import com.cms_service.bean.model.ActiveConsents;
import com.cms_service.bean.response.ConsentStats;
import com.cms_service.bean.response.DoctorConsentStats;
import com.cms_service.bean.response.ListActiveConsents;
import com.cms_service.repository.ConsentLogRepository;
import com.cms_service.repository.PendingRequestRepository;
import com.cms_service.service.serviceinterface.ActiveConsentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class ActiveConsentsServiceImpl implements ActiveConsentService {

    @Autowired
    private ConsentLogRepository consentLogRepository;
    @Autowired
    private PendingRequestRepository pendingRequestRepository;

    @Override
    public ListActiveConsents getActiveConsents(String pid){

        ListActiveConsents listActiveConsents=new ListActiveConsents();
        List<ActiveConsents> activeConsentsList=new ArrayList<>();
        List<ConsentLogEntity> consentLogEntities=consentLogRepository.getConsentLogEntitiesByPidAndStatus(pid,"Active");
        for(final ConsentLogEntity c: consentLogEntities){
            ActiveConsents activeConsents=new ActiveConsents();
            activeConsents.setCid(c.getCid());
            activeConsents.setPid(pid);
            activeConsents.setAccessor_id(c.getAccessor_id());
            activeConsents.setAccessor_name(c.getAccessor_name());
            activeConsents.setAccessing_eid(c.getAccessing_eid());
            activeConsents.setAccessing_ename(c.getAccessing_ename());
            activeConsents.setReason(c.getReason());
            activeConsents.setCreate_date(c.getCreate_date());
            activeConsents.setExpiry_date(c.getExpiry_date());
            activeConsents.setTag1(c.getTag1());
            activeConsents.setTag2(c.getTag2());
            activeConsents.setTag3(c.getTag3());
            activeConsents.setStatus(c.getStatus());
            System.out.println(activeConsents.getCid());

            activeConsentsList.add((ActiveConsents)activeConsents);
        }

        listActiveConsents.setActiveConsentsList(activeConsentsList);
        System.out.println(listActiveConsents.toString());
        return listActiveConsents;
    }

    public ConsentStats getStats(String pid){
        ConsentStats consentStats=new ConsentStats();

        BigInteger active=consentLogRepository.getActiveStats(pid);
        BigInteger revoked=consentLogRepository.getRevokedStats(pid);
        BigInteger totalStats=consentLogRepository.getTotalStats(pid);
        BigInteger pending=pendingRequestRepository.getPendingStats(pid);

        consentStats.setActiveconsent(active);
        consentStats.setTotalconsent(totalStats);
        consentStats.setRevokedconsent(revoked);
        consentStats.setPendingrequests(pending);
        System.out.println("Inside getStats"+consentStats.getTotalconsent());

        return consentStats;
    }

    public DoctorConsentStats getDoctorConsents(String did, String eid){
        DoctorConsentStats doctorConsentStats=new DoctorConsentStats();
        BigInteger totalPatient=new BigInteger("0");
        BigInteger pending =pendingRequestRepository.getDoctorPendingRequest(did,eid);
        BigInteger active  =consentLogRepository.getDoctorActiveCount(did,eid);
        BigInteger rejected  =consentLogRepository.getDoctorRejectedCount(did,eid);
        doctorConsentStats.setActiveconsent(active);
        doctorConsentStats.setPendingrequests(pending);
        doctorConsentStats.setRejectedconsent(rejected);
        doctorConsentStats.setTotalpatients(totalPatient);

        return doctorConsentStats;
    }
}
