package io.bootify.health_hive.model;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;


public class DailyTipsDTO {

    private Long id;

    @NotNull
    private String tip;

    private String heading;

    private String type;

    private LocalDateTime date;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(final String tip) {
        this.tip = tip;
    }

}
