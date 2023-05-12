package com.cms_service.controller;

import com.cms_service.bean.model.AdminData;
import com.cms_service.bean.model.ConsentedRecordIDs;
import com.cms_service.bean.response.ConsentLogResponse;
import com.cms_service.bean.response.EntityResponse;
import com.cms_service.bean.response.MedicalRecordResponse;
import com.cms_service.bean.response.PendingRequestsResponse;
import com.cms_service.service.serviceinterface.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/v1/his")
public class HISController {

   GetMedicalRecordsService getMedicalRecordsService;
    GetConsentedRecordIDsService ConsentedRecordListSvc;
    ConsentedEntityConnectionService ConsentedConnectionsSvc;
    ConsentLogsService consentlogsvc;
    GetMedRecordsByRIDService getMedData;
    UpdateConsentStatusService updateconsentstatusservice;
    public HISController(GetMedicalRecordsService getMedicalRecordsService,
                         UpdateConsentStatusService updateconsentstatusservice,
                         GetConsentedRecordIDsService ConsentedRecordListSvc,
                         GetMedRecordsByRIDService getMedDatasvc,
                         ConsentedEntityConnectionService ConsentedConnectionsSvc,
                         ConsentLogsService consentlogsvc) {
        this.getMedicalRecordsService = getMedicalRecordsService;
        this.updateconsentstatusservice=updateconsentstatusservice;
        this.ConsentedRecordListSvc = ConsentedRecordListSvc;
        this.ConsentedConnectionsSvc= ConsentedConnectionsSvc;
        this.getMedData=getMedDatasvc;
        this.consentlogsvc=consentlogsvc;
    }

    @PostMapping("/get-medical-records/{pid}")
//    ResponseEntity<EntityResponse> getMedicalRecords(@PathVariable String pid) {
    ResponseEntity<String> getMedicalRecords(@RequestBody List<AdminData> adminData, @PathVariable  String pid) {

        String recordResponse = getMedicalRecordsService.getMedicalRecords(adminData,pid);
        System.out.println("\n Returned  records \n"+ recordResponse);


        return ResponseEntity.status(HttpStatus.OK).body(recordResponse);
    }

    @PostMapping ("/{eid}/{did}/get-consented-meddata/{pid}")
    ResponseEntity<MedicalRecordResponse>getConsentedMedRecords(@PathVariable String eid,
                                                                @PathVariable String did,
                                                                @PathVariable String pid)
        {
            updateconsentstatusservice.UpdateStatus();

        List<ConsentedRecordIDs>   RecordIds= null;
        //Consented Records ID with the provider details
        RecordIds= ConsentedRecordListSvc.getConsentedRecordIds(eid,did,pid) ;

        // provider details for Consented Records
        List<String> providerIds = new ArrayList<>();

        for (ConsentedRecordIDs record : RecordIds) {

            if(!providerIds.contains(record.getProvider_eid()))
            {
                providerIds.add(record.getProvider_eid());
            }
        }
//        HashSet<String> hset = new HashSet<String>(providerIds);
//        ArrayList<> providerIds = hset.toArray(new String[hset.size()]);
        System.out.println("[INFO] The provider Ids of the consented Records "+providerIds);

        /* -----------------------------------------------------------------------------------------------*/

        EntityResponse ConsentedEntityConnections=null;
        ConsentedEntityConnections=ConsentedConnectionsSvc.getConnectionDetails(providerIds);

        MedicalRecordResponse ConsentedMedicalRecords =getMedData.
                getMedData(ConsentedEntityConnections,pid,RecordIds,providerIds);


            return ResponseEntity.status(HttpStatus.OK).body(ConsentedMedicalRecords);
//        MedicalRecordResponse recordResponse = consentedMedDataService.getConsentedData(eid,did,pid);


        }

    @PostMapping ("/{eid}/get-pending-requests/{did}")
    ResponseEntity<PendingRequestsResponse>getPendingRequest(@PathVariable String eid,
                                                                  @PathVariable String did)
    {
        PendingRequestsResponse PendingRequests= new PendingRequestsResponse();
        //Consented Records ID with the provider details
        PendingRequests.setPendingRequests(consentlogsvc.getPendingRequests(eid, did));
        System.out.println("Returned Pending requests - "+PendingRequests.getPendingRequests() );
        return ResponseEntity.status(HttpStatus.OK).body(PendingRequests);


    }


    @PostMapping ("/{eid}/get-consent-logs/{did}")
    ResponseEntity<ConsentLogResponse>getConsentLogs(@PathVariable String eid,
                                                        @PathVariable String did)
    {
        updateconsentstatusservice.UpdateStatus();
        ConsentLogResponse consentlogs= new ConsentLogResponse();
        //Consented Records ID with the provider details
        consentlogs.setconsentLogs(consentlogsvc.getConsentLogs(eid, did));
        System.out.println("Returned Pending requests - "+consentlogs.getconsentLogs() );
        return ResponseEntity.status(HttpStatus.OK).body(consentlogs);


    }

    @GetMapping ("/get-active-consent-records/{pid}/{cid}")
    ResponseEntity<MedicalRecordResponse>getActiveConsentedMedRecords(@PathVariable String pid, @PathVariable String cid) {

        updateconsentstatusservice.UpdateStatus();

        List<ConsentedRecordIDs>   RecordIds= null;
        //Consented Records ID with the provider details
        RecordIds= ConsentedRecordListSvc.getActiveConsentedRecordIds(pid,cid) ;

        // provider details for Consented Records
        List<String> providerIds = new ArrayList<>();

        for (ConsentedRecordIDs record : RecordIds) {

            if(!providerIds.contains(record.getProvider_eid()))
            {
                providerIds.add(record.getProvider_eid());
            }
        }

        System.out.println("[INFO] The provider Ids of the consented Records "+providerIds);


        EntityResponse ConsentedEntityConnections=null;
        ConsentedEntityConnections=ConsentedConnectionsSvc.getConnectionDetails(providerIds);

        MedicalRecordResponse ConsentedMedicalRecords =getMedData.
                getMedData(ConsentedEntityConnections,pid,RecordIds,providerIds);

        return ResponseEntity.status(HttpStatus.OK).body(ConsentedMedicalRecords);

    }


}



