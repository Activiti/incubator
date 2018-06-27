package org.activiti.runtime.api.model.impl;

import java.util.Objects;

import org.activiti.runtime.api.model.BPMNActivity;

public class BPMNActivityImpl implements BPMNActivity {
    private String activityId;
    private String activityName;
    private String activityType;

    public BPMNActivityImpl() {
    }

    public BPMNActivityImpl(String activityId,
                            String activityName,
                            String activityType) {
        this.activityId = activityId;
        this.activityName = activityName;
        this.activityType = activityType;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BPMNActivityImpl that = (BPMNActivityImpl) o;
        return Objects.equals(activityId,
                              that.activityId) &&
                Objects.equals(activityName,
                               that.activityName) &&
                Objects.equals(activityType,
                               that.activityType);
    }

    @Override
    public int hashCode() {

        return Objects.hash(activityId,
                            activityName,
                            activityType);
    }
}