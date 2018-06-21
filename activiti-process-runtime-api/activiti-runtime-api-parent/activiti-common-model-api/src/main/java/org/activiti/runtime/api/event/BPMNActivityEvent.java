package org.activiti.runtime.api.event;

import org.activiti.runtime.api.model.BPMNActivity;
import org.activiti.runtime.api.model.VariableInstance;

public interface BPMNActivityEvent extends RuntimeEvent<BPMNActivity, BPMNActivityEvent.ActivityEvents> {

    enum ActivityEvents {

        ACTIVITY_STARTED,

        ACTIVITY_CANCELLED,

        ACTIVITY_COMPLETED

    }


}
