package com.chrajeshkumar.nearby.Pojo;

import java.util.List;

/**
 * Created by ChRajeshKumar on 26-Jan-17.
 */

public class OpeningHours {

    private Boolean openNow;

    private List<Object> weekdayText = null;

    public Boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }

    public List<Object> getWeekdayText() {
        return weekdayText;
    }

    public void setWeekdayText(List<Object> weekdayText) {
        this.weekdayText = weekdayText;
    }
}
