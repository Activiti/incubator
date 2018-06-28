package org.activiti.runtime.api.model;

public interface BPMNActivity {

    String getActivityDefinitionId();

    String getActivityName();

    String getActivityType();

    String getProcessInstanceId();

    String getProcessDefinitionId();
}
