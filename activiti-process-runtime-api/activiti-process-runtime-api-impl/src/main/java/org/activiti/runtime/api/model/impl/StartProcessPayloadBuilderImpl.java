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

import java.util.Map;

import org.activiti.runtime.api.model.StartProcessPayload;
import org.activiti.runtime.api.model.StartProcessPayloadBuilder;
import org.springframework.stereotype.Component;

@Component
public class StartProcessPayloadBuilderImpl implements StartProcessPayloadBuilder {

    private final StartProcessPayloadImpl command;

    public StartProcessPayloadBuilderImpl() {
        command = new StartProcessPayloadImpl();
    }

    public StartProcessPayloadBuilderImpl withProcessDefinitionKey(String processDefinitionKey){
        command.setProcessDefinitionKey(processDefinitionKey);
        return this;
    }

    public StartProcessPayloadBuilderImpl withProcessDefinitionId(String processDefinitionId){
        command.setProcessDefinitionId(processDefinitionId);
        return this;
    }

    public StartProcessPayloadBuilderImpl withVariables(Map<String, Object> variables){
        command.setVariables(variables);
        return this;
    }

    public StartProcessPayloadBuilderImpl withBusinessKey(String businessKey){
        command.setBusinessKey(businessKey);
        return this;
    }

    public StartProcessPayload build() {
        return command;
    }
}