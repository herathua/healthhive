package io.bootify.health_hive.model;

import jakarta.validation.constraints.NotNull;


public class DailyTipsDTO {

    private Long id;

    @NotNull
    private String tip;

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

    private String heading;

    private String type;

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
