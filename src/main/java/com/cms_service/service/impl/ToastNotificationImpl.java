package com.cms_service.service.impl;

import com.cms_service.bean.model.ToastRequestBody;
import com.cms_service.security.model.AuthRequest;
import com.cms_service.service.serviceinterface.ToastNotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
@Service
public class ToastNotificationImpl implements ToastNotificationService {
    @Value("${cms.client.id}")
    private String cmsClientId;
    @Value("${cms.client.secret}")
    private String cmsClientSecret;
    @Value("${admin_service.base.url}/api/v1")
    private String adminBaseURL;
    private RestTemplate restTemplate_admin;
    public ToastNotificationImpl(RestTemplateBuilder builder) {
        this.restTemplate_admin = builder.build();
    }

    @Override
    public Integer requestToast(String pid, String message) {
        //authentication begins
        String admin=adminBaseURL + "/auth/authenticate-cms";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        AuthRequest entitycred=new AuthRequest();
        entitycred.setUsername(cmsClientId);
        entitycred.setPassword(cmsClientSecret);
        HttpEntity<?> request_token = new HttpEntity<>(entitycred, headers);
        //System.out.println(request_token.getBody());
        String Token= restTemplate_admin.postForObject(admin,request_token, String.class,"");
        headers.set("Authorization", "Bearer "+Token);
        //authentication ends
        ToastRequestBody toastRequestBody = new ToastRequestBody();
        toastRequestBody.setMessage(message);
        toastRequestBody.setPid(pid);
        HttpEntity<?> request = new HttpEntity<>(toastRequestBody, headers);
        String url=adminBaseURL + "/cms/notify";
        ResponseEntity response= restTemplate_admin.postForEntity(url,request, ResponseEntity.class,"");
        if(response.getStatusCode().isSameCodeAs(HttpStatus.OK)){
            return 1;
        }
        else{
            System.out.println("Notification sending failed");
            return 0;
        }
    }
}
