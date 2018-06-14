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

package org.activiti.runtime.api.conf;

import java.util.Collections;
import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.runtime.api.TaskRuntime;
import org.activiti.runtime.api.conf.impl.TaskRuntimeConfigurationImpl;
import org.activiti.runtime.api.event.impl.ToAPITaskAssignedEventConverter;
import org.activiti.runtime.api.event.impl.ToAPITaskCandidateGroupAddedEventConverter;
import org.activiti.runtime.api.event.impl.ToAPITaskCandidateUserAddedEventConverter;
import org.activiti.runtime.api.event.impl.ToAPITaskCreatedEventConverter;
import org.activiti.runtime.api.event.internal.TaskAssignedEventListenerDelegate;
import org.activiti.runtime.api.event.internal.TaskCandidateGroupAddedEventListenerDelegate;
import org.activiti.runtime.api.event.internal.TaskCandidateUserAddedEventListenerDelegate;
import org.activiti.runtime.api.event.internal.TaskCreatedEventListenerDelegate;
import org.activiti.runtime.api.event.listener.TaskRuntimeEventListener;
import org.activiti.runtime.api.impl.TaskRuntimeImpl;
import org.activiti.runtime.api.model.impl.APITaskCandidateGroupConverter;
import org.activiti.runtime.api.model.impl.APITaskCandidateUserConverter;
import org.activiti.runtime.api.model.impl.APITaskConverter;
import org.activiti.runtime.api.model.impl.APIVariableInstanceConverter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskRuntimeAutoConfiguration {

    @Bean
    public TaskRuntime taskRuntime(TaskService taskService,
                                   APITaskConverter taskConverter,
                                   TaskRuntimeConfiguration configuration) {
        return new TaskRuntimeImpl(taskService,
                                   taskConverter,
                                   configuration);
    }

    @Bean
    public APITaskConverter apiTaskConverter(TaskService taskService,
                                             APIVariableInstanceConverter variableInstanceConverter) {
        return new APITaskConverter(taskService,
                                    variableInstanceConverter);
    }

    @Bean
    public TaskRuntimeConfiguration taskRuntimeConfiguration(RuntimeService runtimeService,
                                                             @Autowired(required = false) List<TaskRuntimeEventListener> eventListeners,
                                                             TaskCreatedEventListenerDelegate taskCreatedEventListenerDelegate,
                                                             TaskAssignedEventListenerDelegate taskAssignedEventListenerDelegate) {
        return new TaskRuntimeConfigurationImpl(runtimeService,
                                                getInitializedTaskRuntimeEventListeners(eventListeners),
                                                taskCreatedEventListenerDelegate,
                                                taskAssignedEventListenerDelegate);
    }

    @Bean
    public TaskCreatedEventListenerDelegate taskCreatedEventListenerDelegate(@Autowired(required = false) List<TaskRuntimeEventListener> taskRuntimeEventListeners,
                                                                             ToAPITaskCreatedEventConverter taskCreatedEventConverter) {
        return new TaskCreatedEventListenerDelegate(getInitializedTaskRuntimeEventListeners(taskRuntimeEventListeners),
                                                    taskCreatedEventConverter);
    }

    @Bean
    public InitializingBean registerTaskCreatedEventListener(RuntimeService runtimeService,
                                                             TaskCreatedEventListenerDelegate listenerDelegate) {
        return () -> runtimeService.addEventListener(listenerDelegate,
                                                     ActivitiEventType.TASK_CREATED);
    }

    private List<TaskRuntimeEventListener> getInitializedTaskRuntimeEventListeners(List<TaskRuntimeEventListener> taskRuntimeEventListeners) {
        return taskRuntimeEventListeners != null ? taskRuntimeEventListeners : Collections.emptyList();
    }

    @Bean
    public TaskAssignedEventListenerDelegate taskAssignedEventListenerDelegate(@Autowired(required = false) List<TaskRuntimeEventListener> taskRuntimeEventListeners,
                                                                               ToAPITaskAssignedEventConverter taskAssignedEventConverter) {
        return new TaskAssignedEventListenerDelegate(getInitializedTaskRuntimeEventListeners(taskRuntimeEventListeners),
                                                     taskAssignedEventConverter);
    }

    @Bean
    public InitializingBean registerTaskAssignedEventListener(RuntimeService runtimeService,
                                                              TaskAssignedEventListenerDelegate listenerDelegate) {
        return () -> runtimeService.addEventListener(listenerDelegate,
                                                     ActivitiEventType.TASK_ASSIGNED);
    }

    @Bean
    public ToAPITaskCreatedEventConverter apiTaskCreatedEventConverter(APITaskConverter taskConverter) {
        return new ToAPITaskCreatedEventConverter(taskConverter);
    }

    @Bean
    public ToAPITaskAssignedEventConverter apiTaskAssignedEventConverter(APITaskConverter taskConverter) {
        return new ToAPITaskAssignedEventConverter(taskConverter);
    }

    @Bean
    public APITaskCandidateUserConverter apiTaskCandidateUserConverter() {
        return new APITaskCandidateUserConverter();
    }

    @Bean
    public ToAPITaskCandidateUserAddedEventConverter toAPITaskCandidateUserAddedEventConverter(APITaskCandidateUserConverter taskCandidateUserConverter) {
        return new ToAPITaskCandidateUserAddedEventConverter(taskCandidateUserConverter);
    }

    @Bean
    public TaskCandidateUserAddedEventListenerDelegate taskCandidateUserAddedEventListenerDelegate(@Autowired(required = false) List<TaskRuntimeEventListener> taskRuntimeEventListeners,
                                                                                                   ToAPITaskCandidateUserAddedEventConverter taskCandidateUserAddedEventConverter) {
        return new TaskCandidateUserAddedEventListenerDelegate(getInitializedTaskRuntimeEventListeners(taskRuntimeEventListeners),
                                                               taskCandidateUserAddedEventConverter);
    }

    @Bean
    public InitializingBean registerTaskCandidateUserAddedEventListener(RuntimeService runtimeService,
                                                                        TaskCandidateUserAddedEventListenerDelegate listenerDelegate) {
        return () -> runtimeService.addEventListener(listenerDelegate,
                                                     ActivitiEventType.ENTITY_CREATED);
    }

    @Bean
    public APITaskCandidateGroupConverter apiTaskCandidateGroupConverter() {
        return new APITaskCandidateGroupConverter();
    }

    @Bean
    public ToAPITaskCandidateGroupAddedEventConverter toAPITaskCandidateGroupAddedEventConverter(APITaskCandidateGroupConverter taskCandidateGroupConverter) {
        return new ToAPITaskCandidateGroupAddedEventConverter(taskCandidateGroupConverter);
    }

    @Bean
    public TaskCandidateGroupAddedEventListenerDelegate taskCandidateGroupAddedEventListenerDelegate(@Autowired(required = false) List<TaskRuntimeEventListener> taskRuntimeEventListeners,
                                                                                                     ToAPITaskCandidateGroupAddedEventConverter taskCandidateGroupAddedEventConverter) {
        return new TaskCandidateGroupAddedEventListenerDelegate(getInitializedTaskRuntimeEventListeners(taskRuntimeEventListeners),
                                                                taskCandidateGroupAddedEventConverter);
    }

    @Bean
    public InitializingBean registerTaskCandidateGroupAddedEventListener(RuntimeService runtimeService,
                                                                         TaskCandidateGroupAddedEventListenerDelegate listenerDelegate) {
        return () -> runtimeService.addEventListener(listenerDelegate,
                                                     ActivitiEventType.ENTITY_CREATED);
    }
}
