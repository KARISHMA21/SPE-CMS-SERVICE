package com.cms_service.security.controller;

import com.cms_service.security.model.AuthRequest;
import com.cms_service.security.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationContoller {

    @Value("${admin.client.id}")
    private String adminClientId;

    @Value("${admin.client.secret}")
    private String adminClientSecret;
    @Value("${patient.client.id}")
    private String patientClientId;

    @Value("${patient.client.secret}")
    private String patientClientSecret;

    @Value("${his.client.id}")
    private String hisClientId;

    @Value("${his.client.secret}")
    private String hisClientSecret;

    @Autowired
    private JwtService jwtService;
    @PostMapping(value = "/authenticate-admin")
    public ResponseEntity<?> adminAuthenticate(@RequestBody AuthRequest authRequest){

        if(adminClientId.equals(authRequest.getUsername()) && adminClientSecret.equals(authRequest.getPassword())){
            String token=jwtService.createToken(adminClientId);
            System.out.print(token);
            return ResponseEntity.ok(token);
        }
        ResponseEntity<String> resp=new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        return resp;
    }
    @PostMapping(value = "/authenticate-patient")
    public ResponseEntity<?> patientAuthenticate(@RequestBody AuthRequest authRequest){

        if(patientClientId.equals(authRequest.getUsername()) && patientClientSecret.equals(authRequest.getPassword())){
            String token=jwtService.createToken(patientClientId);
            System.out.print(token);
            return ResponseEntity.ok(token);
        }
        ResponseEntity<String> resp=new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        return resp;
    }

    @PostMapping(value = "/authenticate-his")
    public ResponseEntity<?> hisAuthenticate(@RequestBody AuthRequest authRequest){

        if(hisClientId.equals(authRequest.getUsername()) && hisClientSecret.equals(authRequest.getPassword())){
            String token=jwtService.createToken(hisClientId);
            System.out.print(token);
            return ResponseEntity.ok(token);
        }
        ResponseEntity<String> resp=new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        return resp;
    }
}
