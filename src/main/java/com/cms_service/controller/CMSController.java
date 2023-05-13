package com.cms_service.controller;

import com.cms_service.bean.entity.ConsentLogEntity;
import com.cms_service.bean.entity.PendingRequestEntity;
import com.cms_service.bean.model.*;
import com.cms_service.bean.response.*;
import com.cms_service.service.serviceinterface.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/api/v1/cms")
public class CMSController {
    private final VoluntaryConsentService voluntaryConsentService;
    private final CreateConsentService createConsentService;
    private final GetPendingRequests getPendingRequests;
    private final PendingReqInstance pendingReqInstance;
    private final GetMedicalRecordsService getMedicalRecordsService;
    private final ActiveConsentService activeConsentService;
    private final ConsentLogsService consentLogsService;
    private final UpdateConsentStatusService updateConsentStatusService;
    private final GetEmergencyPendingRequests getEmergencyPendingRequests;
    private final DelegateConsentService delegateConsentService;
    private final ToastNotificationService toastNotificationService;

    public CMSController(VoluntaryConsentService voluntaryConsentService,
                         CreateConsentService createConsentService,
                         GetPendingRequests getPendingRequests,
                         PendingReqInstance pendingReqInstance,
                         GetMedicalRecordsService getMedicalRecordsService,
                         ActiveConsentService activeConsentService,
                         GetEmergencyPendingRequests getEmergencyPendingRequests,
                         DelegateConsentService delegateConsentService,
                         ConsentLogsService consentLogsService,
                         UpdateConsentStatusService updateConsentStatusService,
                         ToastNotificationService toastNotificationService
                         ) {
        this.voluntaryConsentService = voluntaryConsentService;
        this.createConsentService = createConsentService;
        this.getPendingRequests = getPendingRequests;
        this.pendingReqInstance = pendingReqInstance;
        this.getMedicalRecordsService = getMedicalRecordsService;
        this.activeConsentService = activeConsentService;
        this.updateConsentStatusService=updateConsentStatusService;
        this.consentLogsService=consentLogsService;
        this.getEmergencyPendingRequests = getEmergencyPendingRequests;
        this.delegateConsentService = delegateConsentService;
        this.toastNotificationService = toastNotificationService;
    }

    @PostMapping("/save-voluntary-consent")
    public ResponseEntity saveVoluntaryConsent(@RequestBody List<VoluntaryConsent> voluntaryConsentList) {
        try {
            //System.out.println(voluntaryConsentList.toString());
//            ToastRequestBody toastRequestBody = new ToastRequestBody();
//            toastRequestBody.setPid(voluntaryConsentList.get(0).getPid());
//            toastRequestBody.setMessage("A Voluntary Consent has been created with Consent ID: "
//                    + voluntaryConsentList.get(0).getCid()
//                    + ". If you did not create the voluntary consent, please login and reject the consent.\n\n" +
//                    "Regards,\n" +
//                    "Consent Management System");
//            toastNotificationService.requestToast(toastRequestBody.getPid(),toastRequestBody.getMessage());
            voluntaryConsentService.saveVoluntaryConsent(voluntaryConsentList);

            return ResponseEntity.status(HttpStatusCode.valueOf(200)).build();
        } catch (Exception e)
        {
            System.out.println(e);

        }
        return ResponseEntity.status(HttpStatusCode.valueOf(500)).build();
    }
    @GetMapping("/get-active-consent/{pid}")
    public ResponseEntity<ListActiveConsents> getActiveConsent(@PathVariable String pid) {
        try {
            ListActiveConsents response=activeConsentService.getActiveConsents(pid);

            return ResponseEntity.ok(response);
        } catch (Exception e)
        {
            System.out.println(e);

        }
        return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
    }

    @GetMapping("/get-doctor-stats/{did}/{eid}")
    public ResponseEntity<DoctorConsentStats> getDoctorConsents(@PathVariable String did,@PathVariable String eid) {
        try {


            DoctorConsentStats response=activeConsentService.getDoctorConsents(did,eid);
System.out.println(response.getActiveconsent()+","+response.getPendingrequests()+","+response.getRejectedconsent());
            return ResponseEntity.ok().body(response);
        } catch (Exception e)
        {
            System.out.println(e);

        }
        return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
    }

    @PutMapping("/update-active-consent-records")
    public ResponseEntity updateActiveConsentRecords(@RequestBody List<ActiveConsentRecords> activeConsentRecordsList) {
        try {
            updateConsentStatusService.UpdateStatus();
            updateConsentStatusService.updateActiveConsentRecords(activeConsentRecordsList);
//            ToastRequestBody toastRequestBody = new ToastRequestBody();
//            toastRequestBody.setPid(activeConsentRecordsList.get(0).getPid());
//            toastRequestBody.setMessage("An Active Consent has been modified with Consent ID: "
//                    + activeConsentRecordsList.get(0).getCid()
//                    + ". If you did not perform the update to the consent, please login and reject the consent.\n\n" +
//                    "Regards,\n" +
//                    "Consent Management System");
//            toastNotificationService.requestToast(toastRequestBody.getPid(),toastRequestBody.getMessage());
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).build();
        } catch (Exception e)
        {
            System.out.println(e);

        }
        return ResponseEntity.status(HttpStatusCode.valueOf(500)).build();
    }


    @PutMapping("/revoke-active-consent/")
    public ResponseEntity updateAllActiveConsentRecords(@RequestBody ActiveConsents activeConsent) {
        try {
            updateConsentStatusService.UpdateStatus();
            updateConsentStatusService.revokeConsent(activeConsent);
//            ToastRequestBody toastRequestBody = new ToastRequestBody();
//            toastRequestBody.setPid(activeConsent.getPid());
//            toastRequestBody.setMessage("An Active Consent has been revoked with Consent ID: "
//                    + activeConsent.
//                    getCid()
//                    + ". The concerned entity's will no longer have access to your data.\n\n" +
//                    "Regards,\n" +
//                    "Consent Management System");
//            toastNotificationService.requestToast(toastRequestBody.getPid(),toastRequestBody.getMessage());
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).build();
        } catch (Exception e)
        {
            System.out.println(e);

        }
        return ResponseEntity.status(HttpStatusCode.valueOf(500)).build();
    }


    @GetMapping("/get-stats/{pid}")
    public ResponseEntity<ConsentStats> getStats(@PathVariable String pid) {
        try {
            ConsentStats response=activeConsentService.getStats(pid);
            System.out.println("Inside getStats"+response.toString());
            return ResponseEntity.ok(response);
        } catch (Exception e)
        {
            System.out.println(e);

        }
        return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
    }


    @GetMapping("/get-patient-consent-logs/{pid}")
    public ResponseEntity<ConsentLogResponse> getPatientConsentLogs(@PathVariable String pid) {
        updateConsentStatusService.UpdateStatus();
        ConsentLogResponse consentlogs= new ConsentLogResponse();
        //Consented Records ID with the provider details
        consentlogs.setconsentLogs(consentLogsService.getPatientConsentLogs(pid));
        System.out.println("Returned Pending requests - "+consentlogs.getconsentLogs() );
        return ResponseEntity.status(HttpStatus.OK).body(consentlogs);

    }

    @PostMapping("/new-consent-request")
    public ResponseEntity newConsentRequest(@RequestBody PendingRequestEntity pendingRequestEntity){
        //System.out.println(pendingRequestEntity.toString());
//        ToastRequestBody toastRequestBody = new ToastRequestBody();
//        toastRequestBody.setPid(pendingRequestEntity.getPid());
//        toastRequestBody.setMessage("A Pending Request has been created with Pending Consent ID: "
//                + pendingRequestEntity.getPendingRequestId()
//                + ". To take action on it, please login.\n\n" +
//                "Regards,\n" +
//                "Consent Management System");
//        toastNotificationService.requestToast(toastRequestBody.getPid(),toastRequestBody.getMessage());
        Integer status = createConsentService.createPendingRequest(pendingRequestEntity);
        if(status ==1){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/get-pending-request-list/{pid}")
    public ResponseEntity<List<PendingRequestEntity>> getPendingRequests(@PathVariable String pid){
        List<PendingRequestEntity> pendingRequestsResponse = getPendingRequests.getPendingRequests(pid);
        if(Objects.isNull(pendingRequestsResponse)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(pendingRequestsResponse);
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(pendingRequestsResponse);
        }
    }

    @GetMapping("/get-pending-request-details/{pid}/{pendingrequestid}")
    public ResponseEntity<List<MedicalRecordResponse>> getPendingRequestDetail(@PathVariable String pid,@PathVariable String pendingrequestid){
        // First we need to get the pending request id so that we can get the tags and the date that is needed
        PendingRequestEntity pendingRequestEntity = pendingReqInstance.getPendingRequestInstance(pendingrequestid);
        // next we are going to fetch the medical records and then filter them at the service level
        List<MedicalRecordResponse> pendingRequestDetailResponse = getMedicalRecordsService.getMedicalRecordsForPendingReq(pid,pendingRequestEntity.getTag1(),pendingRequestEntity.getTag2(),pendingRequestEntity.getTag3(),pendingRequestEntity.getFrom_date(),pendingRequestEntity.getTo_date());
        //List<MedicalRecordResponse> pendingRequestDetailResponse = visitedHospitalDetailsService.getPendingRequestDetail(pid,"tags",new Date(123456789));
        //return ResponseEntity.status(HttpStatus.OK).body(pendingRequestDetailResponse);
        return ResponseEntity.ok().body(pendingRequestDetailResponse);
    }

    @PostMapping("/action-on-pending-request/{pendingrequestid}")
    public ResponseEntity actionOnPendingRequest(@PathVariable String pendingrequestid, @RequestBody ConsentActionRequestBody consentActionRequestBody){
        PendingRequestEntity pendingRequestEntity = pendingReqInstance.getPendingRequestInstance(pendingrequestid);
        if(isNull(pendingRequestEntity)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
//        ToastRequestBody toastRequestBody = new ToastRequestBody();
//        toastRequestBody.setPid(pendingRequestEntity.getPid());
//        toastRequestBody.setMessage("An action has been performed on a Pending Request with Pending Consent ID: "
//                + pendingRequestEntity.getPendingRequestId()
//                + ". If you didn't take this action, please login and revoke if active.\n\n" +
//                "Regards,\n" +
//                "Consent Management System");
//        toastNotificationService.requestToast(toastRequestBody.getPid(),toastRequestBody.getMessage());
        Integer status = createConsentService.createConsentLog(consentActionRequestBody, pendingRequestEntity);
        createConsentService.dropPendingRequest(pendingrequestid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/view-active-consents/{pid}")
    public ResponseEntity<List<ConsentLogEntity>> viewActiveConsents(@PathVariable String pid){
        List<ConsentLogEntity> consentLogEntities = createConsentService.getActiveConsents(pid);
        // we will be returning an empty list to front end if the pid has no active requests
        // so check the length of response to figure out if you have active consent or not
        if(isNull(consentLogEntities)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(consentLogEntities);
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(consentLogEntities);
        }
    }

    @PostMapping("/create-emergency-consent")
    public ResponseEntity createEmergencyConsent(@RequestBody PendingRequestEntity pendingRequestEntity){
//        ToastRequestBody toastRequestBody = new ToastRequestBody();
//        toastRequestBody.setPid(pendingRequestEntity.getPid());
//        toastRequestBody.setMessage("Emergency Consent has been raised with Pending Request ID: "
//                + pendingRequestEntity.getPendingRequestId()
//                + ". If you do not consent to this, please login and reject it.\n\n" +
//                "Regards,\n" +
//                "Consent Management System");
        ResponseEntity response = newConsentRequest(pendingRequestEntity);
        if(response.getStatusCode().isSameCodeAs(HttpStatus.OK)){
//            toastNotificationService.requestToast(toastRequestBody.getPid(),toastRequestBody.getMessage());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/action-on-emergency-consent/{pendingrequestid}")
    public ResponseEntity actionOnEmergencyConsent(@PathVariable String pendingrequestid, @RequestBody EmergencyAction action){
        PendingRequestEntity pendingRequestEntity = pendingReqInstance.getPendingRequestInstance(pendingrequestid);
        List<MedicalRecordResponse> medicalRecordResponseList = getMedicalRecordsService.getMedicalRecordsForPendingReq(pendingRequestEntity.getPid(),pendingRequestEntity.getTag1(),pendingRequestEntity.getTag2(),pendingRequestEntity.getTag3(),pendingRequestEntity.getFrom_date(),pendingRequestEntity.getTo_date());
        Integer status = createConsentService.createEmergencyConsent(medicalRecordResponseList, pendingRequestEntity, action.getAction());
//        ToastRequestBody toastRequestBody = new ToastRequestBody();
//        toastRequestBody.setPid(pendingRequestEntity.getPid());
//        toastRequestBody.setMessage("An action has been performed on a Pending Emergency Request with Pending Consent ID: "
//                + pendingRequestEntity.getPendingRequestId()
//                + ". If you want to modify the action, please login and verify.\n\n" +
//                "Regards,\n" +
//                "Consent Management System");
//        toastNotificationService.requestToast(toastRequestBody.getPid(),toastRequestBody.getMessage());
        if(status==1){
            createConsentService.dropPendingRequest(pendingrequestid);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/get-emergency-pending-request-list/{super_id}")
    public ResponseEntity<List<PendingRequestEntity>> getEmergencyPendingRequstsList(@PathVariable String super_id){
        List<PendingRequestEntity> pendingRequestsResponse = getEmergencyPendingRequests.getEmergencyPendingRequests(super_id);
        if(Objects.isNull(pendingRequestsResponse)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(pendingRequestsResponse);
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(pendingRequestsResponse);
        }
    }

    @PostMapping("/delegate-consent")
    public ResponseEntity delegateConsent(@RequestBody DelegateConsentReq delegateConsentReq){
        Integer status = delegateConsentService.delegateConsent(delegateConsentReq);
        ConsentLogEntity consentLogEntity = consentLogsService.getConsentLogEntity(delegateConsentReq.getCid());
//        ToastRequestBody toastRequestBody = new ToastRequestBody();
//        toastRequestBody.setPid(consentLogEntity.getPid());
//        toastRequestBody.setMessage("An active consent of your with Consent ID: "
//                + delegateConsentReq.getCid()
//                + " has been delegated to a new doctor, "+ delegateConsentReq.getNew_accessor_name() + ". If you want to update your consent, please login and modify consent.\n\n" +
//                "Regards,\n" +
//                "Consent Management System");
//        toastNotificationService.requestToast(toastRequestBody.getPid(),toastRequestBody.getMessage());
        if(status==1){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}




