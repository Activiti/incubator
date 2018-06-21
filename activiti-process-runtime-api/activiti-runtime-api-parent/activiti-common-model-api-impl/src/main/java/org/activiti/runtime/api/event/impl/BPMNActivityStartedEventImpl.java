package org.activiti.runtime.api.event.impl;

import org.activiti.runtime.api.event.BPMNActivityStartedEvent;

public class BPMNActivityStartedEventImpl extends BPMNActivityEventImpl implements BPMNActivityStartedEvent {

    public BPMNActivityStartedEventImpl() {
    }

    @Override
    public ActivityEvents getEventType() {
        return ActivityEvents.ACTIVITY_STARTED;
    }


}
