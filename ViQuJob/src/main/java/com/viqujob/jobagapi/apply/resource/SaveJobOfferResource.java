package com.viqujob.jobagapi.apply.resource;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.time.LocalDate;

public class SaveJobOfferResource {
    @Size(max = 200)
    private String description;
    @DateTimeFormat
    private LocalDate begin_date_offer;
    @DateTimeFormat
    private LocalDate final_date_offer;
    private Long salary;
    @Size(max = 60)
    private String direction;
    private String type;
    private String title;

    public String getDescription() {
        return description;
    }

    public SaveJobOfferResource setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDate getBegin_date_offer() {
        return begin_date_offer;
    }

    public SaveJobOfferResource setBegin_date_offer(LocalDate begin_date_offer) {
        this.begin_date_offer = begin_date_offer;
        return this;
    }

    public LocalDate getFinal_date_offer() {
        return final_date_offer;
    }

    public SaveJobOfferResource setFinal_date_offer(LocalDate final_date_offer) {
        this.final_date_offer = final_date_offer;
        return this;
    }

    public Long getSalary() {
        return salary;
    }

    public SaveJobOfferResource setSalary(Long salary) {
        this.salary = salary;
        return this;
    }

    public String getDirection() {
        return direction;
    }

    public SaveJobOfferResource setDirection(String direction) {
        this.direction = direction;
        return this;
    }

    public String getType() {
        return type;
    }

    public SaveJobOfferResource setType(String type) {
        this.type = type;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public SaveJobOfferResource setTitle(String title) {
        this.title = title;
        return this;
    }
}
