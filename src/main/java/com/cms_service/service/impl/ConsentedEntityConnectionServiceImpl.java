package com.cms_service.service.impl;

import com.cms_service.bean.response.AdminResponse;
import com.cms_service.bean.response.EntityResponse;
import com.cms_service.security.model.AuthRequest;
import com.cms_service.service.serviceinterface.ConsentedEntityConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Transactional
@Service
public class ConsentedEntityConnectionServiceImpl implements ConsentedEntityConnectionService {

    @Value("${cms.client.id}")
    private String cmsClientId;

    @Value("${cms.client.secret}")
    private String cmsClientSecret;
    private RestTemplate restTemplate_admin;
    public ConsentedEntityConnectionServiceImpl(@Value("${admin_service.base.url}"+"/api/v1")String adminBaseURL, RestTemplateBuilder builder){
        this.restTemplate_admin= builder.rootUri(adminBaseURL).build();
    }
//    @Autowired
    public EntityResponse getConnectionDetails(List<String> Provider_eid){
        String idsParam = Provider_eid.stream()
                                                .distinct() // Remove duplicates
                                                .map(String::valueOf).collect(Collectors.joining(","));
        System.out.println("The list of Entity Ids for which records are consented "+idsParam);


        System.out.println("========================== CMS Authentication Request to admin ==========================");

        String admin="/auth/authenticate-cms";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        //get the token for communication
        AuthRequest entitycred=new AuthRequest();

        entitycred.setUsername(cmsClientId);
        entitycred.setPassword(cmsClientSecret);

        HttpEntity<?> request_token = new HttpEntity<>(entitycred, headers);
        System.out.println(request_token.getBody());

        String Token= restTemplate_admin.postForObject(admin,request_token, String.class,"");
        System.out.println(Token);
        //Getting the medical records
        headers.set("Authorization", "Bearer "+Token);

        EntityResponse  consentedEntityConnectionData=null;
        admin = "/cms/get-consented-hospital-detail?provider-ids=" + idsParam;

        HttpEntity<?> request_records = new HttpEntity<>("", headers);
//
        consentedEntityConnectionData= restTemplate_admin.postForObject(admin,request_records, EntityResponse.class);
//        List<AdminResponse> adminResponse =consentedEntityConnectionData.getEntityRegs();
        return consentedEntityConnectionData;



    }
}
