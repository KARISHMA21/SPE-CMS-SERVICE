package com.cms_service.repository;

import com.cms_service.bean.entity.ConsentLogEntity;
import com.cms_service.bean.entity.PendingRequestEntity;
import com.cms_service.bean.model.ConsentLog;
import com.cms_service.bean.model.ConsentedRecordIDs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConsentLogRepository extends CrudRepository<ConsentLogEntity,String> {
    @Modifying
    @Query("UPDATE ConsentLogEntity cl SET cl.last_update=:last_updated WHERE " +
            "cl.cid=:cid AND cl.pid=:pid")
    public void UpdateLastUpdateDate(@Param("cid") String cid, @Param("last_updated") Date last_updated,@Param("pid") String pid);
    @Modifying
    @Query("UPDATE ConsentLogEntity cl SET cl.status='Expired' WHERE " +
            "cl.cid=:cid AND cl.pid=:pid")
    public void UpdateConsents(@Param("cid") String cid,@Param("pid") String pid);

    @Query("select t from ConsentLogEntity  t where t.pid =:pid and (t.status=:status or t.status='Emergency' )")
    public List<ConsentLogEntity> getConsentLogEntitiesByPidAndStatus(@Param("pid") String pid, @Param("status") String status );



//    @Query("SELECT cl.cid,cl.expiry_date,cl.status FROM  ConsentLogEntity cl WHERE " +
//            "cl.accessing_eid=:eid AND" +
//            " cl.accessor_id=:did AND cl.pid=:pid AND " +
//            "cl.status='Active'  ")
//    public List<ConsentStatus> getConsentStatus(@Param("eid") String eid, @Param("did") String did, @Param("pid") String pid );

    @Query("SELECT new com.cms_service.bean.model.ConsentedRecordIDs(cm.cid_rid_provider_eid.record_id,cm.cid_rid_provider_eid.provider_eid,cl.cid,cl.pid) " +
            "FROM ConsentMappingEntity cm  " +
            "JOIN ConsentLogEntity cl on cm.cid_rid_provider_eid.cid=cl.cid" +
            " WHERE " +
            "cl.accessing_eid=:eid AND" +
            " cl.accessor_id=:did AND cl.pid=:pid AND " +
            "(cl.status='Active' OR cl.status='Emergency')" +
            "AND cm.active_flag=1    ")
    public List<ConsentedRecordIDs> getConsentedRecordList(@Param("eid") String eid, @Param("did") String did, @Param("pid") String pid);

    @Query("SELECT  new com.cms_service.bean.model.ConsentLog(t.cid,t.pid,t.accessor_id,t.accessing_eid,t.last_update,t.status,t.create_date,t.expiry_date,t.action_taken_by,t.reason) " +
            "FROM ConsentLogEntity  t  where " +
            "t.accessing_eid=:eid AND t.accessor_id=:did")
    public List<ConsentLog> getConsentLogs(@Param("eid") String eid, @Param("did") String did);



    @Query("select count(t.cid)from ConsentLogEntity  t where t.pid =:pid and  (t.status='Active' or t.status='Emergency' )")
    public BigInteger getActiveStats(@Param("pid") String pid );

    @Query("select count(t.cid)from ConsentLogEntity  t where t.pid =:pid")
    public BigInteger getTotalStats(@Param("pid") String pid );

    @Query("select count(t.cid)from ConsentLogEntity  t where t.pid =:pid and (t.status='Expired' or t.status='Rejected')")
    public BigInteger getRevokedStats(@Param("pid") String pid );

    @Query("select count(t.cid)from ConsentLogEntity  t where t.accessor_id =:did and (t.status='Active' or t.status='Emergency')")
    public BigInteger getDoctorActiveCount(@Param("did") String did ,@Param("did") String eid );

    @Query("select count(t.cid)from ConsentLogEntity  t where t.accessor_id =:did and (t.status='Rejected')")
    public BigInteger getDoctorRejectedCount(@Param("did") String did ,@Param("did") String eid );



    @Query("SELECT  new com.cms_service.bean.model.ConsentLog(t.cid,t.pid,t.accessor_id,t.accessing_eid,t.last_update,t.status,t.create_date,t.expiry_date,t.action_taken_by,t.reason) " +
            "FROM ConsentLogEntity  t  where " +
            "t.pid=:pid")
    public List<ConsentLog> getPatientConsentLogs(@Param("pid") String pid);
    @Modifying
    @Query("UPDATE ConsentLogEntity cl SET cl.status='Expired'  WHERE " +
            "(cl.status='Active' OR cl.status='Emergency') AND cl.expiry_date<=:today")
    public void UpdateConsentStatus(@Param("today") Date today );



    @Query("SELECT new com.cms_service.bean.model.ConsentedRecordIDs(cm.cid_rid_provider_eid.record_id,cm.cid_rid_provider_eid.provider_eid,cl.cid,cl.pid) FROM ConsentMappingEntity cm  JOIN ConsentLogEntity cl on cm.cid_rid_provider_eid.cid=cl.cid WHERE " +

            " cl.cid=:cid AND cl.pid=:pid AND " +
            "(cl.status='Active' OR cl.status='Emergency') AND cm.active_flag=1    ")
    public List<ConsentedRecordIDs> getActiveConsentedRecordList(@Param("pid") String pid, @Param("cid") String cid);


    Optional<List<ConsentLogEntity>> findAllByPidAndStatus(String pid, String status);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update ConsentLogEntity c set c.accessor_id = :new_accessor_id, c.accessor_name = :new_accessor_name, c.last_update = :date where c.accessor_id = :old_accessor_id and c.cid = :cid")
    void delegateConsent(@Param("new_accessor_id")String new_accessor_id,
                         @Param("new_accessor_name")String new_accessor_name,
                         @Param("date") Date date,
                         @Param("old_accessor_id")String old_accessor_id,
                         @Param("cid") String cid);

    ConsentLogEntity findByCid(String cid);
}
