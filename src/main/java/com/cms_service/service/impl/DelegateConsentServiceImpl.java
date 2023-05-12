package com.cms_service.service.impl;

import com.cms_service.bean.model.DelegateConsentReq;
import com.cms_service.repository.ConsentLogRepository;
import com.cms_service.service.serviceinterface.DelegateConsentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
@Service
@Transactional
public class DelegateConsentServiceImpl implements DelegateConsentService {
    private ConsentLogRepository consentLogRepository;

    public DelegateConsentServiceImpl(ConsentLogRepository consentLogRepository) {
        this.consentLogRepository = consentLogRepository;
    }

    @Override
    public Integer delegateConsent(DelegateConsentReq delegateConsentReq) {
        try{
            consentLogRepository.delegateConsent(delegateConsentReq.getNew_accessor_id(),
                    delegateConsentReq.getNew_accessor_name(),
                    new Date(),
                    delegateConsentReq.getOld_accessor_id(),
                    delegateConsentReq.getCid());
        }
        catch(Exception e){
            return 0;
        }
        return 1;
    }
}
