package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.Label;

public class TimeLabel extends Label {
    public TimeLabel() {
    }

    public TimeLabel(Integer timeInSeconds) {
        super(formatTime(timeInSeconds));
    }

    public void setTime(Integer timeInSeconds) {
        super.setText(formatTime(timeInSeconds));
    }

    private static String formatTime(Integer timeInSeconds) {
        // TODO use localized constants
        Integer secondsReminder = timeInSeconds % 60;
        Integer minutes = timeInSeconds / 60;
        Integer minutesReminder = minutes % 60;
        Integer hours = minutes / 60;
        Integer hoursReminder = hours % 24;
        Integer days = hours / 24;
        if (days > 0) {
            return days + "d" + hoursReminder + "h" + minutesReminder + "m";
        } else if (hours > 0) {
            return hours + "h" + minutesReminder + "m";
        } else if (minutes > 0) {
            return minutes + "m" + secondsReminder + "s";
        } else {
            return timeInSeconds + "s";
        }
    }
}
