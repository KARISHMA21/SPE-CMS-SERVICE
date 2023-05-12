package com.cms_service.service.impl;//package com.example.backend.service;
//
import com.cms_service.bean.model.AdminData;
import com.cms_service.bean.response.*;
import com.cms_service.bean.model.FinalRecords;
import com.cms_service.bean.model.MedicalRecord;
//import com.example.cms_service.response.*;
import com.cms_service.security.model.AuthRequest;
import com.cms_service.security.service.EncryptDecrypt;
import com.cms_service.service.serviceinterface.GetMedicalRecordsService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class GetMedicalRecordsImpl implements GetMedicalRecordsService {
//  @Autowired
    private RestTemplate restTemplate_his;
    private RestTemplate restTemplate_admin;
    @Value("${cms.client.id}")
    private String cmsClientId;
    @Value("${cms.client.secret}")
    private String cmsClientSecret;
    private final EncryptDecrypt encryptDecrypt;

    private String hisBaseURL;
    @Value("${admin_service.base.url}/api/v1")
    private String adminBaseURL;
    public GetMedicalRecordsImpl(RestTemplateBuilder builder,EncryptDecrypt encryptDecrypt){
        this.restTemplate_his=builder.build();
        this.restTemplate_admin=builder.build();
        this.encryptDecrypt=encryptDecrypt;
    }


    public String getMedicalRecords(List<AdminData> adminResponse,String pid) {

                EntityResponse entityResponse =new EntityResponse();

                String medrecords =null;
                MedicalRecordResponse medicalRecordResponse =new MedicalRecordResponse();
                List<FinalRecords> finalRecordsList=new ArrayList<>();
                List<HospitalResponse> hospitalResponseList=new ArrayList<>();
                List<MedicalRecord> response=new ArrayList<>();
                String url="";
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                //get the token for communication
                AuthRequest entitycred=new AuthRequest();

                entitycred.setUsername(cmsClientId);
                entitycred.setPassword(cmsClientSecret);


                HttpEntity<?> request_token = new HttpEntity<>(entitycred, headers);
                System.out.println(adminResponse.toString());
                 for (final AdminData  d : adminResponse) {
                     System.out.println("inside get data");
                     HospitalResponse hospitalResponse =new HospitalResponse();
                    hisBaseURL="http://"+d.getEip()+":"+d.getPort()+"/his_end/api/v1";
                    System.out.println(hisBaseURL);
                     try {
                         String Token = restTemplate_his.postForObject(hisBaseURL + "/auth/authenticate-cms", request_token, String.class, "");

                         url = hisBaseURL + "/his/get-records/{pid}";
                         headers.set("Authorization", "Bearer " + Token);

                         HttpEntity<?> request_records = new HttpEntity<>("", headers);
                         ResponseEntity<String> res =null;
                         res = restTemplate_his.exchange(url, HttpMethod.GET, request_records, new ParameterizedTypeReference<String>() {
                         }, pid);
                         String decryptedData=encryptDecrypt.decryptData(res.getBody()) ;
                         Gson gson = new Gson();

                         Type type = new TypeToken<List<MedicalRecord>>(){}.getType();
                          response = gson.fromJson(decryptedData, type);



                         hospitalResponse.setMedicalRecordEntities(response);
                         System.out.println("\n Recieved medical records from "+d.getEid()+" \n");
                        hospitalResponse.setEid(d.getEid());
                        hospitalResponse.setEname(d.getEname());
                        hospitalResponse.setEtype(d.getEtype());

                    hospitalResponseList.add((HospitalResponse) hospitalResponse);
                     } catch (Exception e) {
                         System.out.println("Error while Connecting to Hospital:  " + e.getMessage());
                         System.out.println("Warn -- Skipped Records from Provide Id: " + d.getEid() + " due to connection error");
                     }
                 }
//                 recordResponse.setHospitalResponses(hospitalResponseList);
//                 recordResponse.setPid(pid);
        System.out.println("after  get data");
                 List<HospitalResponse> hospitalResponses =hospitalResponseList;
                for (final HospitalResponse  d : hospitalResponses) {
                    for(final MedicalRecord m : d.getMedicalRecordEntities()) {
                        FinalRecords finalRecords=new FinalRecords();
                        finalRecords.setDid(m.getDoctorEntity().getDid());
                        finalRecords.setDname(m.getDoctorEntity().getDname());
//                        System.out.println(finalRecords.getDname());
                        finalRecords.setEid(d.getEid());
//                        System.out.println(finalRecords.getEid());
                        finalRecords.setEtype(d.getEtype());
//                        System.out.println(finalRecords.getEtype());
                        finalRecords.setEname(d.getEname());
//                        System.out.println(finalRecords.getEname());
                        finalRecords.setRid(m.getRid());
//                        System.out.println(finalRecords.getRid());

                        finalRecords.setDesc(m.getDescription());
//                        System.out.println(finalRecords.getDesc());
                        finalRecords.setTag1(m.getTag1());
                        finalRecords.setTag2(m.getTag2());
                        finalRecords.setTag3(m.getTag3());



                        //                        System.out.println(finalRecords.getTags());
                        finalRecords.setGen_date(m.getGen_date());
                        finalRecords.setRec_type(m.getType());
                        //System.out.println(finalRecords.toString());
                        finalRecordsList.add((FinalRecords) finalRecords);
                      }



                }

                 medicalRecordResponse.setFinalRecordsList(finalRecordsList);
                    //System.out.println(medicalRecordResponse.toString());
        System.out.println("\n <---------------Returning from getMedicalRecords--------------->\n");
        Gson gson = new Gson();

         medrecords =encryptDecrypt.encrytData(gson.toJson(medicalRecordResponse));
        return  medrecords;


 }

    public List<MedicalRecordResponse> getMedicalRecordsForPendingReq(String pid, String tag1, String tag2, String tag3, Date from, Date to){
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
        //asking admin for the mapping
        HttpEntity<?> request = new HttpEntity<>("", headers);
        String url=adminBaseURL + "/cms/get-patient-mapping/"+pid;
        ResponseEntity<List<AdminData>> response;
        response= restTemplate_admin.exchange(url, HttpMethod.GET, request, new ParameterizedTypeReference<List<AdminData>>(){},"");
        //calling getMedicalRecords() with the mapping and pid
        String medicalRecordResponse = getMedicalRecords(response.getBody(), pid);
        String decryptedData=encryptDecrypt.decryptData(medicalRecordResponse) ;
        Gson gson = new Gson();

        Type type = new TypeToken<MedicalRecordResponse>(){}.getType();


        MedicalRecordResponse medicalRecordResponse_pre=null;
        medicalRecordResponse_pre= gson.fromJson(decryptedData, type);
        //writing the code to filter the medical data into 3 lists based on tags and date
        List<FinalRecords> finalRecordsList = medicalRecordResponse_pre.getFinalRecordsList();
        List<FinalRecords> Y_Tag_Y_Date = new ArrayList<>();
        List<FinalRecords> N_Tag_Y_Date = new ArrayList<>();
        List<FinalRecords> N_Tag_N_Date = new ArrayList<>();
        List<String> request_tags=new ArrayList<>();
        request_tags.add(tag1);
        request_tags.add(tag2);
        request_tags.add(tag3);
        for(FinalRecords f: finalRecordsList){
            if(from.before(f.getGen_date()) && to.after(f.getGen_date())){
                //dates are matching
                if(request_tags.contains(f.getTag1()) || request_tags.contains(f.getTag2()) || request_tags.contains(f.getTag3())){
                    //tags are matching
                    Y_Tag_Y_Date.add(f);
                }
                else{
                    N_Tag_Y_Date.add(f);
                }
            }
            else{
                // not matching dates and tags
                N_Tag_N_Date.add(f);

            }
        }
        //returning the list of MedicalRecordResponse
        List<MedicalRecordResponse> medicalRecordResponseList = new ArrayList<>();
        MedicalRecordResponse t1 = new MedicalRecordResponse(),t2 = new MedicalRecordResponse(),t3 = new MedicalRecordResponse();
        t1.setFinalRecordsList(Y_Tag_Y_Date);
        medicalRecordResponseList.add(t1);
        t2.setFinalRecordsList(N_Tag_Y_Date);
        medicalRecordResponseList.add(t2);
        System.out.println("Medical Record Response Content:\n"+medicalRecordResponseList);
        t3.setFinalRecordsList(N_Tag_N_Date);
        medicalRecordResponseList.add(t3);
        //System.out.println("Medical Record Response Content:\n"+medicalRecordResponseList);
        return medicalRecordResponseList;
    }
}
