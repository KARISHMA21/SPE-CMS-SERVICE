package com.cms_service.bean.response;

import java.math.BigInteger;

public class DoctorConsentStats {
    public BigInteger getTotalpatients() {
        return totalpatients;
    }

    public void setTotalpatients(BigInteger totalpatients) {
        this.totalpatients = totalpatients;
    }

    public BigInteger getActiveconsent() {
        return activeconsent;
    }

    public void setActiveconsent(BigInteger activeconsent) {
        this.activeconsent = activeconsent;
    }

    public BigInteger getRejectedconsent() {
        return rejectedconsent;
    }

    public void setRejectedconsent(BigInteger rejectedconsent) {
        this.rejectedconsent = rejectedconsent;
    }

    public BigInteger getPendingrequests() {
        return pendingrequests;
    }

    public void setPendingrequests(BigInteger pendingrequests) {
        this.pendingrequests = pendingrequests;
    }

    private BigInteger totalpatients;
    private BigInteger activeconsent;
    private BigInteger rejectedconsent;
    private BigInteger pendingrequests;
}
