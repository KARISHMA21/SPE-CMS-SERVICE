package com.cms_service.service.impl;

import com.cms_service.bean.model.AdminData;
import com.cms_service.bean.model.ConsentedRecordIDs;
import com.cms_service.bean.model.FinalRecords;
import com.cms_service.bean.model.MedicalRecord;
import com.cms_service.bean.response.*;
import com.cms_service.security.model.AuthRequest;
import com.cms_service.service.serviceinterface.GetMedRecordsByRIDService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class GetMedRecordsByRIDServiceImpl implements GetMedRecordsByRIDService {

    @Value("${cms.client.id}")
    private String cmsClientId;

    @Value("${cms.client.secret}")
    private String cmsClientSecret;
    private String hisBaseURL;
    private String URL;
    private RestTemplate restTemplate_his;

    public GetMedRecordsByRIDServiceImpl(RestTemplateBuilder builder) {
        this.restTemplate_his = builder.build();
    }

    @Override
    public MedicalRecordResponse getMedData(EntityResponse entityResponse,
                                            String pid,
                                            List<ConsentedRecordIDs> RecordIds,
                                            List<String> Provider_ids) {


//        List<MedicalRecordResponse> finalMeddata= new ArrayList<>();

        List<String> ConsentedRecIds = new ArrayList<>();
        List<FinalRecords> finalRecordsList = new ArrayList<>();
        MedicalRecordResponse medicalRecordResponse = new MedicalRecordResponse();
        int i = 0;
        //For each provider entity do this
        for (String Provider : Provider_ids) {
            System.out.println("Provide Id - "+Provider);
            ConsentedRecIds.clear();
            for (ConsentedRecordIDs record1 : RecordIds) {
//                if(record.getProvider_eid()==record1.getRecord_id())
                if (Provider.equals(record1.getProvider_eid())
//                        && (record.getRecord_id().equals(record1.getRecord_id()))
                ) {
                    i++;
                    System.out.println(" loop --" + i);
                    ConsentedRecIds.add(record1.getRecord_id());
//                    System.out.println("Record Id belonging to provider id "+record1.getProvider_eid()+" - " +record1.getRecord_id()+"all "+ConsentedRecIds );

                }


            }
           System.out.println("The consented Record Ids belonging to provider id " + Provider + " are " + ConsentedRecIds);
//            MedicalRecordResponse ConsentedMedicalRecords =getMedData.getMedData(ConsentedEntityConnections,pid,ConsentedRecIds,record.getProvider_eid());
//            System.out.println("The following records are being sent ... ");

            String RidsParam = ConsentedRecIds.stream().map(String::valueOf).collect(Collectors.joining(","));
//        System.out.println("The list of Consented Records Ids "+RidsParam+"from "+Provider_id);


            List<AdminData> adminResponse = entityResponse.getEntityRegs();
            HospitalResponse hospitalResponse = new HospitalResponse();
            List<HospitalResponse> hospitalResponseList = new ArrayList<>();

            RecordResponse recordResponse = new RecordResponse();
//            MedicalRecordResponse medicalRecordResponse =new MedicalRecordResponse();

            for (final AdminData d : adminResponse) {
                if (d.getEid().equals(Provider)) {
                    hisBaseURL = "http://" + d.getEip() + ":" + d.getPort() + "/his_end/api/v1";
                    System.out.println("========================== CMS Authentication Request to HIS :"+d.getEid()+" ==========================");
//                    http://localhost:8080/his_end/api/v1/auth/authenticate-cms
                    String hisurl=hisBaseURL+"/auth/authenticate-cms";
                    System.out.println("\n"+hisurl+"\n");
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                    //get the token for communication
                    AuthRequest entitycred=new AuthRequest();

                    entitycred.setUsername(cmsClientId);
                    entitycred.setPassword(cmsClientSecret);

                    HttpEntity<?> request_token = new HttpEntity<>(entitycred, headers);
                    System.out.println(request_token.getBody());
                    try {
                    String Token= restTemplate_his.postForObject(hisurl,request_token, String.class,"");
                    System.out.println(Token);
                    //Getting the medical records
                    headers.set("Authorization", "Bearer "+Token);


//                    hisBaseURL = "https://" + d.getEip() + "/his_end/api";
                    hisurl = hisBaseURL + "/his/get-requested-records/{pid}?record-ids=" + RidsParam;
//                    hisurl = "/get-requested-records/{pid}?record-ids=" + RidsParam;
                    System.out.println("\n Fetching consented records from... " + hisurl);

                    HttpEntity<?> request_records = new HttpEntity<>("", headers);
//            hospitalResponse= restTemplate_his.getForObject(hisBaseURL+"/getRecords/{pid}", HospitalResponse.class ,pid);
////                    System.out.println(hospitalResponse.toString());
//                    if(Provider.equals("E02"))
//                        System.out.println("Printing "+(restTemplate_his.getForObject(URL, HospitalResponse.class, pid)).toString());
                    try {
//                        System.out.println("Printing "+restTemplate_his.getForObject(URL, HospitalResponse.class, pid));
                        hospitalResponse = restTemplate_his.postForObject(hisurl,request_records, HospitalResponse.class, pid);

                        hospitalResponse.setEid(d.getEid());
                        hospitalResponse.setEname(d.getEname());
                        hospitalResponse.setEtype(d.getEtype());
//                    System.out.println(hospitalResponse.toString());

                        hospitalResponseList.add((HospitalResponse) hospitalResponse);
                    } catch (Exception e) {
                        System.out.println("Error while Connecting to Hospital:  " + e.getMessage());
                        System.out.println("Warn -- Skipped Records from Provide Id: " + Provider + " due to connection error");
                    }
                    } catch (Exception e) {
                        System.out.println("Error while Authenticating Hospital "+Provider+" : " + e.getMessage());
                        System.out.println("Warn -- Skipped Records from Provide Id: " + Provider + " due to connection error");
                    }
                }
            }
            recordResponse.setHospitalResponses(hospitalResponseList);
            recordResponse.setPid(pid);

            List<HospitalResponse> hospitalResponses = hospitalResponseList;
            for (final HospitalResponse d : hospitalResponses) {
                for (final MedicalRecord m : d.getMedicalRecordEntities()) {
                    FinalRecords finalRecords = new FinalRecords();
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
                    finalRecords.setGen_date(m.getGen_date());
                    finalRecords.setRec_type(m.getType());
//                System.out.println(finalRecords.toString());
                    finalRecordsList.add((FinalRecords) finalRecords);
                }


            }

//            if(ConsentedMedicalRecords !=null)
//            {
//                System.out.println(ConsentedMedicalRecords.toString());
//                finalMeddata.add(ConsentedMedicalRecords);
//            }
//            else{
//                System.out.println("Warn -- Skipped Records from Provide Id: "+record.getProvider_eid()+" due to connection error");
//            }

        }

        medicalRecordResponse.setFinalRecordsList(finalRecordsList);
        System.out.println(medicalRecordResponse.toString());
        return medicalRecordResponse;
    }
}


/*===========================================================================================================*/

//        String RidsParam = RecIds.stream().map(String::valueOf).collect(Collectors.joining(","));
////        System.out.println("The list of Consented Records Ids "+RidsParam+"from "+Provider_id);
//
//        List<AdminResponse> adminResponse =entityResponse.getEntityRegs();
//        HospitalResponse hospitalResponse =new HospitalResponse();
//        List<HospitalResponse> hospitalResponseList=new ArrayList<>();
//
//        //////////////////
//        List<FinalRecords> finalRecordsList=new ArrayList<>();
//
//
//        RecordResponse recordResponse =new RecordResponse();
//        MedicalRecordResponse medicalRecordResponse =new MedicalRecordResponse();
//
//        for (final AdminResponse  d : adminResponse) {
//            if(d.getEid().equals(Provider_id))
//            {
//                hisBaseURL="http://"+d.getEip()+":"+d.getPort()+"/his_end/api";
//                URL=hisBaseURL+"/{pid}/getRecords?record-ids="+RidsParam;
//                    System.out.println("Fetching consented records from... "+ URL);
////            hospitalResponse= restTemplate_his.getForObject(hisBaseURL+"/getRecords/{pid}", HospitalResponse.class ,pid);
//////                    System.out.println(hospitalResponse.toString());
//                try {
//                    hospitalResponse = restTemplate_his.getForObject(URL, HospitalResponse.class, pid);
//
//                    hospitalResponse.setEid(d.getEid());
//                    hospitalResponse.setEname(d.getEname());
//                    hospitalResponse.setEtype(d.getEtype());
////                    System.out.println(hospitalResponse.toString());
//
//                    hospitalResponseList.add((HospitalResponse) hospitalResponse);
//                }catch (Exception e){
//                    System.out.println("Error while Connecting to Hospital:  " + e.getMessage());
//                    return null;
//                }
//            }
//        }
//        recordResponse.setHospitalResponses(hospitalResponseList);
//        recordResponse.setPid(pid);
////                System.out.println(recordResponse.toString());
//
//        List<HospitalResponse> hospitalResponses =hospitalResponseList;
//        for (final HospitalResponse  d : hospitalResponses) {
//            for(final MedicalRecord m : d.getMedicalRecordEntities()) {
//                FinalRecords finalRecords=new FinalRecords();
//                finalRecords.setDid(m.getDoctorEntity().getDid());
//                finalRecords.setDname(m.getDoctorEntity().getDname());
////                        System.out.println(finalRecords.getDname());
//                finalRecords.setEid(d.getEid());
////                        System.out.println(finalRecords.getEid());
//                finalRecords.setEtype(d.getEtype());
////                        System.out.println(finalRecords.getEtype());
//                finalRecords.setEname(d.getEname());
////                        System.out.println(finalRecords.getEname());
//                finalRecords.setRid(m.getRid());
////                        System.out.println(finalRecords.getRid());
//
//                finalRecords.setDesc(m.getDescription());
////                        System.out.println(finalRecords.getDesc());
//                finalRecords.setTags(m.getTags());
////                        System.out.println(finalRecords.getTags());
//                finalRecords.setGen_date(m.getGen_date());
//                finalRecords.setRec_type(m.getType());
////                System.out.println(finalRecords.toString());
//                finalRecordsList.add((FinalRecords) finalRecords);
//            }
//
//
//
//        }
//
//        medicalRecordResponse.setFinalRecordsList(finalRecordsList);
////                    System.out.println(medicalRecordResponse.toString());
//        return  medicalRecordResponse;
//    }
//}
