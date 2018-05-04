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

package org.activiti.runtime.api.conf.impl;

import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.runtime.api.conf.ProcessRuntimeConfiguration;
import org.activiti.runtime.api.event.internal.ProcessStartedEventListenerDelegate;
import org.activiti.runtime.api.event.listener.ProcessRuntimeEventListener;
import org.springframework.stereotype.Component;

@Component
public class RuntimeConfigurationImpl implements ProcessRuntimeConfiguration {

    private final RuntimeService runtimeService;
    private final ProcessStartedEventListenerDelegate processStartedEventListenerDelegate;
    private final List<ProcessRuntimeEventListener> eventListeners;

    public RuntimeConfigurationImpl(RuntimeService runtimeService,
                                    ProcessStartedEventListenerDelegate processStartedEventListenerDelegate,
                                    List<ProcessRuntimeEventListener> eventListeners) {
        this.runtimeService = runtimeService;
        this.processStartedEventListenerDelegate = processStartedEventListenerDelegate;
        this.eventListeners = eventListeners;
    }

    @PostConstruct
    private void registerEventListeners() {
        runtimeService.addEventListener(processStartedEventListenerDelegate, ActivitiEventType.PROCESS_STARTED);
    }

    @Override
    public List<ProcessRuntimeEventListener> eventListeners() {
        return Collections.unmodifiableList(eventListeners);
    }
}
