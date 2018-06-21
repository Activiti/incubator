package org.activiti.runtime.api.event.impl;

import org.activiti.runtime.api.event.BPMNActivityCompletedEvent;

public class BPMNActivityCompletedEventImpl extends BPMNActivityEventImpl implements BPMNActivityCompletedEvent {

    public BPMNActivityCompletedEventImpl() {
    }

    @Override
    public ActivityEvents getEventType() {
        return ActivityEvents.ACTIVITY_COMPLETED;
    }
}
