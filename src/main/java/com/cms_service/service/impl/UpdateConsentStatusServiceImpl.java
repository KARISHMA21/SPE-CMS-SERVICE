package com.cms_service.service.impl;
//import java.text.SimpleDateFormat;
//import java.util.Date;

import com.cms_service.repository.ConsentLogRepository;
import com.cms_service.bean.model.ActiveConsentRecords;
import com.cms_service.bean.model.ActiveConsents;
import com.cms_service.repository.ConsentLogRepository;
import com.cms_service.repository.ConsentMappingRepository;
import com.cms_service.service.serviceinterface.UpdateConsentStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class UpdateConsentStatusServiceImpl implements UpdateConsentStatusService {

//    @Autowired
//    private ConsentedRecordsRepository updatestatus;
    @Autowired
    private ConsentMappingRepository consentMappingRepository;
    @Autowired
    private ConsentLogRepository consentLogRepository;
    public void UpdateStatus() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//    LocalDate today = LocalDate.now();
//    String todayStr = formatter.format(today);
        Date today = new Date();

        consentLogRepository.UpdateConsentStatus(today);
    }

    @Override
    public int updateActiveConsentRecords(List<ActiveConsentRecords> activeConsentRecordsList){
    try{

        String pid=activeConsentRecordsList.get(0).getPid();
        Date today = new Date();
        //update records active flags
        for(ActiveConsentRecords a: activeConsentRecordsList){
            consentMappingRepository.UpdateActiveConsentRecords(a.getCid(),a.getRid(),a.getProvider_eid());
        }
        for(ActiveConsentRecords a: activeConsentRecordsList){
            consentLogRepository.UpdateLastUpdateDate(a.getCid(),today,pid);
        }

        //update status and update date
        List<String> cids=consentMappingRepository.getConsentedCidsRevoked();
        System.out.println(cids.toString());
        for(String c: cids){
            consentLogRepository.UpdateConsents(c,pid);
        }





        return 0;
   }
   catch(Exception e){
        System.out.println(e);
    }
        return 1;
    }
    @Override
    public int revokeConsent(ActiveConsents activeConsent){
        try{

            String pid=activeConsent.getPid();
            Date today = new Date();
            //update consents in log
//            for(ActiveConsents a: activeConsentRecordsList){
                consentLogRepository.UpdateConsents(activeConsent.getCid(),pid);
//            }
//            for(ActiveConsents a: activeConsentRecordsList){
                consentLogRepository.UpdateLastUpdateDate(activeConsent.getCid(),today,pid);
//            }

            //update active flags in mapping

            consentMappingRepository.UpdateConsentRecords(activeConsent.getCid());





            return 0;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return 1;
    }


}
