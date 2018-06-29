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

package org.conf.activiti.runtime;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.activiti.runtime.api.event.BPMNActivityEvent;
import org.activiti.runtime.api.event.ProcessRuntimeEvent;
import org.activiti.runtime.api.event.SequenceFlowEvent;
import org.activiti.runtime.api.event.impl.CloudBPMNActivityCancelledEventImpl;
import org.activiti.runtime.api.event.impl.CloudBPMNActivityCompletedEventImpl;
import org.activiti.runtime.api.event.impl.CloudBPMNActivityStartedEventImpl;
import org.activiti.runtime.api.event.impl.CloudProcessCancelledEventImpl;
import org.activiti.runtime.api.event.impl.CloudProcessCompletedEventImpl;
import org.activiti.runtime.api.event.impl.CloudProcessCreatedEventImpl;
import org.activiti.runtime.api.event.impl.CloudProcessResumedEventImpl;
import org.activiti.runtime.api.event.impl.CloudProcessStartedEventImpl;
import org.activiti.runtime.api.event.impl.CloudProcessSuspendedEventImpl;
import org.activiti.runtime.api.event.impl.CloudSequenceFlowTakenImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudProcessModelAutoConfiguration {

    //this bean will be automatically injected inside boot's ObjectMapper
    @Bean
    public Module customizeCloudProcessModelObjectMapper() {
        SimpleModule module = new SimpleModule("mapProcessRuntimeEvents",
                                               Version.unknownVersion());
        module.registerSubtypes(new NamedType(CloudBPMNActivityStartedEventImpl.class,
                                              BPMNActivityEvent.ActivityEvents.ACTIVITY_STARTED.name()));
        module.registerSubtypes(new NamedType(CloudBPMNActivityCompletedEventImpl.class,
                                              BPMNActivityEvent.ActivityEvents.ACTIVITY_COMPLETED.name()));
        module.registerSubtypes(new NamedType(CloudBPMNActivityCancelledEventImpl.class,
                                              BPMNActivityEvent.ActivityEvents.ACTIVITY_CANCELLED.name()));
        module.registerSubtypes(new NamedType(CloudProcessStartedEventImpl.class,
                                              ProcessRuntimeEvent.ProcessEvents.PROCESS_STARTED.name()));
        module.registerSubtypes(new NamedType(CloudProcessCreatedEventImpl.class,
                                              ProcessRuntimeEvent.ProcessEvents.PROCESS_CREATED.name()));
        module.registerSubtypes(new NamedType(CloudProcessCompletedEventImpl.class,
                                              ProcessRuntimeEvent.ProcessEvents.PROCESS_COMPLETED.name()));
        module.registerSubtypes(new NamedType(CloudProcessSuspendedEventImpl.class,
                                              ProcessRuntimeEvent.ProcessEvents.PROCESS_SUSPENDED.name()));
        module.registerSubtypes(new NamedType(CloudProcessResumedEventImpl.class,
                                              ProcessRuntimeEvent.ProcessEvents.PROCESS_RESUMED.name()));
        module.registerSubtypes(new NamedType(CloudProcessCancelledEventImpl.class,
                                              ProcessRuntimeEvent.ProcessEvents.PROCESS_CANCELLED.name()));
        module.registerSubtypes(new NamedType(CloudSequenceFlowTakenImpl.class,
                                              SequenceFlowEvent.SequenceFlowEvents.SEQUENCE_FLOW_TAKEN.name()));

        return module;
    }

}
