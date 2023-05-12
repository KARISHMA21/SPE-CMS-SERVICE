package com.cms_service.bean.model;

import java.util.Objects;

public class EmergencyAction {
    String action;

    @Override
    public String toString() {
        return "EmergencyAction{" +
                "action='" + action + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmergencyAction that = (EmergencyAction) o;
        return Objects.equals(action, that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(action);
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
