package com.cms_service.repository;

import com.cms_service.bean.entity.ConsentMappingEntity;
import com.cms_service.bean.entity.Consent_Comp_key;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsentMappingRepository extends CrudRepository<ConsentMappingEntity, Consent_Comp_key> {
    @Modifying
    @Query("UPDATE ConsentMappingEntity cm SET cm.active_flag=0  WHERE " +
            "cm.cid_rid_provider_eid.cid=:cid AND cm.cid_rid_provider_eid.record_id=:rid AND cm.cid_rid_provider_eid.provider_eid=:provider_id")
    public void UpdateActiveConsentRecords(@Param("cid") String cid, @Param("rid") String rid, @Param("provider_id") String provider_id );

    @Modifying
    @Query("UPDATE ConsentMappingEntity cm SET cm.active_flag=0  WHERE " + "cm.cid_rid_provider_eid.cid=:cid")
    public void UpdateConsentRecords(@Param("cid") String cid );
    @Query("SELECT m.cid_rid_provider_eid.cid FROM ConsentMappingEntity m group by m.cid_rid_provider_eid.cid having sum(m.active_flag)=0")
    public List<String> getConsentedCidsRevoked();


}
