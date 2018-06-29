package org.activiti.runtime.api.model.impl;

import java.util.Objects;

import org.activiti.runtime.api.model.BPMNActivity;

public class BPMNActivityImpl implements BPMNActivity {

    private String activityDefinitionId;
    private String activityName;
    private String activityType;
    private String processInstanceId;
    private String processDefinitionId;

    public BPMNActivityImpl() {
    }

    public BPMNActivityImpl(String activityDefinitionId,
                            String activityName,
                            String activityType) {
        this.activityDefinitionId = activityDefinitionId;
        this.activityName = activityName;
        this.activityType = activityType;
    }

    public String getActivityDefinitionId() {
        return activityDefinitionId;
    }

    public void setActivityDefinitionId(String activityDefinitionId) {
        this.activityDefinitionId = activityDefinitionId;
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
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Override
    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
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
        return Objects.equals(activityDefinitionId,
                              that.activityDefinitionId) &&
                Objects.equals(activityName,
                               that.activityName) &&
                Objects.equals(activityType,
                               that.activityType) &&
                Objects.equals(processInstanceId,
                               that.processInstanceId) &&
                Objects.equals(processDefinitionId,
                               that.processDefinitionId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(activityDefinitionId,
                            activityName,
                            activityType,
                            processInstanceId,
                            processDefinitionId);
    }
}
