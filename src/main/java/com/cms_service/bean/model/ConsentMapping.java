package com.cms_service.bean.model;

import com.cms_service.bean.entity.Consent_Comp_key;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsentMapping {
    private Consent_Comp_key cid_owning_eid;
    private String rid;
    private int active_flag;

}
