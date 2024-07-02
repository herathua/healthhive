package io.bootify.health_hive.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class FaqDTO {

    private Long id;

    @NotNull
    private String question;
    private String answer;


    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(final String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }
    public void setAnswer(final String answer) {
        this.answer = answer;
    }

}

