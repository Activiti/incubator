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

package org.activiti.runtime.api.events.impl;

import java.util.List;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.runtime.api.events.TaskRuntimeEvent;
import org.activiti.runtime.api.events.TaskRuntimeEventListener;
import org.springframework.stereotype.Component;

@Component
public class TaskRuntimeEventListenerDelegate implements ActivitiEventListener {

    private final List<TaskRuntimeEventListener> taskRuntimeEventListeners;

    private final TaskRuntimeEventsConverter taskRuntimeEventsConverter;

    public TaskRuntimeEventListenerDelegate(List<TaskRuntimeEventListener> taskRuntimeEventListeners,
                                            TaskRuntimeEventsConverter taskRuntimeEventsConverter) {
        this.taskRuntimeEventListeners = taskRuntimeEventListeners;
        this.taskRuntimeEventsConverter = taskRuntimeEventsConverter;
    }

    @Override
    public void onEvent(ActivitiEvent event) {
        TaskRuntimeEvent runtimeEvent = taskRuntimeEventsConverter.from(event);
        if (runtimeEvent != null) {
            for (TaskRuntimeEventListener listener : taskRuntimeEventListeners) {
                switch (event.getType()) {
                    case TASK_CREATED:
                        listener.onTaskCreated(runtimeEvent);
                        break;
                    case TASK_ASSIGNED:
                        break;
                    case TASK_COMPLETED:
                        break;
                }
            }
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
