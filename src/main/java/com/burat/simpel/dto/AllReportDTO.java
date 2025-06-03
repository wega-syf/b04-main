package com.burat.simpel.dto;

import com.burat.simpel.model.EventPeriodModel;

public class AllReportDTO {
    private EventPeriodModel eventPeriod;
    private Double rerataGap;
    private Long participants;

    public AllReportDTO(EventPeriodModel eventPeriod, Double rerataGap, Long participants) {
        this.eventPeriod = eventPeriod;
        this.rerataGap = (rerataGap < 0.0 ? rerataGap * -1 : rerataGap);
        this.participants = participants;
    }

    public Long getParticipants() {
        return participants;
    }

    public void setParticipants(Long participants) {
        this.participants = participants;
    }

    public EventPeriodModel getEventPeriod() {
        return eventPeriod;
    }

    public void setEventPeriod(EventPeriodModel eventPeriod) {
        this.eventPeriod = eventPeriod;
    }

    public Double getRerataGap() {
        return rerataGap;
    }

    public void setRerataGap(Double rerataGap) {
        this.rerataGap = rerataGap;
    }
}
