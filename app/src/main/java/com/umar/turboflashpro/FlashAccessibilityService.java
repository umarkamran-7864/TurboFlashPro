package com.umar.turboflashpro;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

public class FlashAccessibilityService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getPackageName() != null &&
            event.getPackageName().toString().contains("com.whatsapp") &&
            event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            FlashOverlayButton.show(this);
        }
    }

    @Override
    public void onInterrupt() {}
}
