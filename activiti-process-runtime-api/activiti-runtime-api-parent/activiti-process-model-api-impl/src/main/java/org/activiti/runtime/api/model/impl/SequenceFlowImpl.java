/*
 * Copyright 2018 Alfresco, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.runtime.api.model.impl;

import org.activiti.runtime.api.model.SequenceFlow;

public class SequenceFlowImpl implements SequenceFlow {

    private String processInstanceId;
    private String processDefinitionId;
    private String sourceActivityDefinitionId;
    private String sourceActivityName;
    private String sourceActivityType;
    private String targetActivityDefinitionId;
    private String targetActivityName;
    private String targetActivityType;

    public SequenceFlowImpl() {
    }

    public SequenceFlowImpl(String sourceActivityDefinitionId,
                            String targetActivityDefinitionId) {
        this.sourceActivityDefinitionId = sourceActivityDefinitionId;
        this.targetActivityDefinitionId = targetActivityDefinitionId;
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
    public String getSourceActivityDefinitionId() {
        return sourceActivityDefinitionId;
    }

    @Override
    public String getSourceActivityName() {
        return sourceActivityName;
    }

    public void setSourceActivityName(String sourceActivityName) {
        this.sourceActivityName = sourceActivityName;
    }

    @Override
    public String getSourceActivityType() {
        return sourceActivityType;
    }

    public void setSourceActivityType(String sourceActivityType) {
        this.sourceActivityType = sourceActivityType;
    }

    @Override
    public String getTargetActivityDefinitionId() {
        return targetActivityDefinitionId;
    }

    @Override
    public String getTargetActivityName() {
        return targetActivityName;
    }

    public void setTargetActivityName(String targetActivityName) {
        this.targetActivityName = targetActivityName;
    }

    @Override
    public String getTargetActivityType() {
        return targetActivityType;
    }

    public void setTargetActivityType(String targetActivityType) {
        this.targetActivityType = targetActivityType;
    }

}
