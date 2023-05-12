package com.cms_service.repository;


import com.cms_service.bean.entity.PendingRequestEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

import java.util.Optional;

@Repository
public interface PendingRequestRepository extends CrudRepository<PendingRequestEntity,String> {


    @Query("Select t from PendingRequestEntity  t  where " +
            "t.requestor_eid=:eid AND t.requestor_id=:did")
    public List<PendingRequestEntity> getPendingRequests(@Param("eid") String eid, @Param("did") String did);


    @Query("select count(t.pendingRequestId)from PendingRequestEntity  t where t.pid =:pid")
    public BigInteger getPendingStats(@Param("pid") String pid );

    @Query("select count(t.pendingRequestId)from PendingRequestEntity  t where t.requestor_eid =:eid and t.requestor_id=:did")
    public BigInteger getDoctorPendingRequest(@Param("did") String did,@Param("eid") String eid );

    Optional<List<PendingRequestEntity>> findByPid(String pid);

    PendingRequestEntity findByPendingRequestId(String pendingRequestId);

    Optional<List<PendingRequestEntity>> findBySuperidAndReason(String superid,String reason);
}
