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

package org.conf.activiti.runtime.api;

import java.util.Collections;
import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.runtime.api.ProcessRuntime;
import org.activiti.runtime.api.conf.ProcessRuntimeConfiguration;
import org.activiti.runtime.api.conf.impl.ProcessRuntimeConfigurationImpl;
import org.activiti.runtime.api.event.impl.ToAPIProcessCreatedEventConverter;
import org.activiti.runtime.api.event.impl.ToAPIProcessStartedEventConverter;
import org.activiti.runtime.api.event.impl.ToProcessResumedConverter;
import org.activiti.runtime.api.event.impl.ToProcessSuspendedConverter;
import org.activiti.runtime.api.event.internal.ProcessCreatedEventListenerDelegate;
import org.activiti.runtime.api.event.internal.ProcessResumedEventListenerDelegate;
import org.activiti.runtime.api.event.internal.ProcessStartedEventListenerDelegate;
import org.activiti.runtime.api.event.internal.ProcessSuspendedEventListenerDelegate;
import org.activiti.runtime.api.event.listener.ProcessRuntimeEventListener;
import org.activiti.runtime.api.impl.ProcessRuntimeImpl;
import org.activiti.runtime.api.model.builder.impl.ProcessStarterFactory;
import org.activiti.runtime.api.model.impl.APIProcessDefinitionConverter;
import org.activiti.runtime.api.model.impl.APIProcessInstanceConverter;
import org.activiti.runtime.api.model.impl.APIVariableInstanceConverter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessRuntimeAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ProcessRuntime processRuntime(RepositoryService repositoryService,
                                         APIProcessDefinitionConverter processDefinitionConverter,
                                         RuntimeService runtimeService,
                                         APIProcessInstanceConverter processInstanceConverter,
                                         ProcessRuntimeConfiguration processRuntimeConfiguration) {
        return new ProcessRuntimeImpl(repositoryService,
                                      processDefinitionConverter,
                                      runtimeService,
                                      processInstanceConverter,
                                      processRuntimeConfiguration);
    }

    @Bean
    public APIProcessDefinitionConverter apiProcessDefinitionConverter(ProcessStarterFactory processStarterFactory,
                                                                       RuntimeService runtimeService,
                                                                       APIProcessInstanceConverter processInstanceConverter) {
        return new APIProcessDefinitionConverter(processStarterFactory,
                                                 runtimeService,
                                                 processInstanceConverter);
    }

    @Bean
    public ProcessStarterFactory processStarterFactory(RuntimeService runtimeService,
                                                       APIProcessInstanceConverter processInstanceConverter) {
        return new ProcessStarterFactory(runtimeService,
                                         processInstanceConverter);
    }

    @Bean
    public APIProcessInstanceConverter apiProcessInstanceConverter(RuntimeService runtimeService,
                                                                   APIVariableInstanceConverter variableInstanceConverter) {
        return new APIProcessInstanceConverter(runtimeService,
                                               variableInstanceConverter);
    }

    @Bean
    public ProcessRuntimeConfiguration processRuntimeConfiguration(@Autowired(required = false) List<ProcessRuntimeEventListener> eventListeners) {
        return new ProcessRuntimeConfigurationImpl(getInitializedListeners(eventListeners));
    }

    @Bean
    public ToAPIProcessStartedEventConverter apiProcessStartedEventConverter(APIProcessInstanceConverter processInstanceConverter) {
        return new ToAPIProcessStartedEventConverter(processInstanceConverter);
    }

    @Bean
    public ToAPIProcessCreatedEventConverter apiProcessCreatedEventConverter(APIProcessInstanceConverter processInstanceConverter) {
        return new ToAPIProcessCreatedEventConverter(processInstanceConverter);
    }

    @Bean
    public ToProcessResumedConverter processResumedConverter(APIProcessInstanceConverter processInstanceConverter) {
        return new ToProcessResumedConverter(processInstanceConverter);
    }

    @Bean
    public ToProcessSuspendedConverter processSuspendedConverter(APIProcessInstanceConverter processInstanceConverter) {
        return new ToProcessSuspendedConverter(processInstanceConverter);
    }

    private List<ProcessRuntimeEventListener> getInitializedListeners(List<ProcessRuntimeEventListener> eventListeners) {
        return eventListeners != null ? eventListeners : Collections.emptyList();
    }

    @Bean
    public InitializingBean registerProcessStartedEventListenerDelegate(RuntimeService runtimeService,
                                                                        @Autowired(required = false) List<ProcessRuntimeEventListener> listeners,
                                                                        ToAPIProcessStartedEventConverter processStartedEventConverter) {
        return () -> runtimeService.addEventListener(new ProcessStartedEventListenerDelegate(getInitializedListeners(listeners),
                                                                                             processStartedEventConverter),
                                                     ActivitiEventType.PROCESS_STARTED);
    }

    @Bean
    public InitializingBean registerProcessCreatedEventListenerDelegate(RuntimeService runtimeService,
                                                                        @Autowired(required = false) List<ProcessRuntimeEventListener> eventListeners,
                                                                        ToAPIProcessCreatedEventConverter converter) {
        return () -> runtimeService.addEventListener(new ProcessCreatedEventListenerDelegate(getInitializedListeners(eventListeners),
                                                                                             converter),
                                                     ActivitiEventType.ENTITY_CREATED);
    }

    @Bean
    public InitializingBean registerProcessSuspendedEventListenerDelegate(RuntimeService runtimeService,
                                                                          @Autowired(required = false) List<ProcessRuntimeEventListener> eventListeners,
                                                                          ToProcessSuspendedConverter converter) {
        return () -> runtimeService.addEventListener(new ProcessSuspendedEventListenerDelegate(getInitializedListeners(eventListeners),
                                                                                               converter),
                                                     ActivitiEventType.ENTITY_SUSPENDED);
    }

    @Bean
    public InitializingBean registerProcessResumedEventListenerDelegate(RuntimeService runtimeService,
                                                                        @Autowired(required = false) List<ProcessRuntimeEventListener> eventListeners,
                                                                        ToProcessResumedConverter converter) {
        return () -> runtimeService.addEventListener(new ProcessResumedEventListenerDelegate(getInitializedListeners(eventListeners),
                                                                                             converter),
                                                     ActivitiEventType.ENTITY_ACTIVATED);
    }
}
