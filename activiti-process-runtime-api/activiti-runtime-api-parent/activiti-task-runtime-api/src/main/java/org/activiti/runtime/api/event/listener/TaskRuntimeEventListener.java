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

package org.activiti.runtime.api.event.listener;

import org.activiti.runtime.api.event.TaskAssignedEvent;
import org.activiti.runtime.api.event.TaskCancelled;
import org.activiti.runtime.api.event.TaskCandidateGroupAddedEvent;
import org.activiti.runtime.api.event.TaskCandidateUserAddedEvent;
import org.activiti.runtime.api.event.TaskCompletedEvent;
import org.activiti.runtime.api.event.TaskCreatedEvent;
import org.activiti.runtime.api.event.TaskSuspendedEvent;

public interface TaskRuntimeEventListener {

    void onTaskCreated(TaskCreatedEvent event);

    void onTaskAssigned(TaskAssignedEvent event);

    void onTaskSuspended(TaskSuspendedEvent event);

    void onTaskCompleted(TaskCompletedEvent event);

    void onTaskCancelled(TaskCancelled event);

    void onTaskCandidateUserAdded(TaskCandidateUserAddedEvent event);

    void onTaskCandidateGroupAdded(TaskCandidateGroupAddedEvent event);

}
