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

package org.activiti.runtime.api;

import java.util.List;

import org.activiti.runtime.api.model.ProcessDefinition;
import org.activiti.runtime.api.model.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessRuntimeIT {

    @Autowired
    private ProcessRuntime processRuntime;

    @Test
    public void shouldStartProcessInstance() {
        //when
        ProcessInstance processInstance = processRuntime
                .processDefinitionWithKey("SimpleProcess")
                .start();

        //then
        assertThat(processInstance).isNotNull();
        assertThat(processInstance.getId()).isNotEmpty();
        assertThat(processInstance.getBusinessKey()).isNull();
        assertThat(processInstance.getProcessDefinitionId()).isNotEmpty();
        assertThat(processInstance.getProcessDefinitionKey()).isEqualTo("SimpleProcess");
    }

    @Test
    public void shouldStartProcessInstanceWithExtraInformation() {
        //when
        ProcessInstance processInstance = processRuntime
                .processDefinitionWithKey("SimpleProcess")
                .startProcessWith()
                .businessKey("myBusinessKey")
                .variable("firstName",
                          "John")
                .variable("lastName",
                          "Doe")
                .doIt();

        //then
        assertThat(processInstance).isNotNull();
        assertThat(processInstance.getId()).isNotEmpty();
        assertThat(processInstance.getBusinessKey()).isEqualTo("myBusinessKey");
        assertThat(processInstance.getProcessDefinitionId()).isNotEmpty();
        assertThat(processInstance.getProcessDefinitionKey()).isEqualTo("SimpleProcess");
    }

    @Test
    public void shouldGetProcessInstanceById() {
        //given
        ProcessInstance processInstance = aProcessInstance();

        //when
        ProcessInstance retrievedProcessInstance = processRuntime.processInstance(processInstance.getId());

        //then
        assertThat(retrievedProcessInstance).isEqualTo(processInstance);
    }

    private ProcessInstance aProcessInstance() {
        return processRuntime
                    .processDefinitionWithKey("SimpleProcess")
                    .start();
    }

    @Test
    public void shouldSuspendRunningProcessInstance() {
        //given
        ProcessInstance processInstance = aProcessInstance();

        //when
        processInstance.suspend();

        //then
        ProcessInstance retrievedProcessInstance = processRuntime.processInstance(processInstance.getId());
        assertThat(retrievedProcessInstance).isNotNull();
        assertThat(retrievedProcessInstance.getStatus()).isEqualTo(ProcessInstance.ProcessInstanceStatus.SUSPENDED);
    }

    @Test
    public void shouldResumeSuspendedProcessInstance() {
        //given
        ProcessInstance processInstance = aProcessInstance();
        processInstance.suspend();

        //when
        processInstance.resume();

        //then
        ProcessInstance retrievedProcessInstance = processRuntime.processInstance(processInstance.getId());
        assertThat(retrievedProcessInstance).isNotNull();
        assertThat(retrievedProcessInstance.getStatus()).isEqualTo(ProcessInstance.ProcessInstanceStatus.RUNNING);
    }

    @Test
    public void shouldGetDeployedProcessDefinitions() {
        //when
        List<ProcessDefinition> processDefinitions = processRuntime.processDefinitions();

        //then
        assertThat(processDefinitions).isNotNull();
        assertThat(processDefinitions).extracting(ProcessDefinition::getName).contains("SimpleProcess");
    }

    @Test
    public void shouldGetProcessInstancesFromProcessDefinition() {
        //given
        ProcessInstance firstSimpleProcess = processRuntime
                .processDefinitionWithKey("SimpleProcess")
                .start();
        ProcessInstance processWithVariables = processRuntime
                .processDefinitionWithKey("ProcessWithVariables")
                .start();
        ProcessInstance secondSimpleProcess = processRuntime
                .processDefinitionWithKey("SimpleProcess")
                .start();

        //when
        List<ProcessInstance> processInstances = processRuntime
                .processDefinitionWithKey("SimpleProcess")
                .processInstances(0,
                                  500);
        //then
        assertThat(processInstances)
                .contains(firstSimpleProcess,
                          secondSimpleProcess)
                .doesNotContain(processWithVariables);

        //when
        processInstances = processRuntime
                .processDefinitionWithKey("ProcessWithVariables")
                .processInstances(0,
                                  500);

        //then
        assertThat(processInstances)
                .contains(processWithVariables)
                .doesNotContain(firstSimpleProcess,
                                secondSimpleProcess);
    }
}
