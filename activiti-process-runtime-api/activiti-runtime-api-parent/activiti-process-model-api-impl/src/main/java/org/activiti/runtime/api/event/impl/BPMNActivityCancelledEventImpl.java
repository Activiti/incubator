package org.activiti.runtime.api.event.impl;

import org.activiti.runtime.api.event.BPMNActivityCancelledEvent;

public class BPMNActivityCancelledEventImpl extends BPMNActivityEventImpl implements BPMNActivityCancelledEvent {

    public BPMNActivityCancelledEventImpl() {
    }

    @Override
    public ActivityEvents getEventType() {
        return ActivityEvents.ACTIVITY_CANCELLED;
    }
}
