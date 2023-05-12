package com.cms_service.bean.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ToastRequestBody {
    private String pid;
    private String message;
}
