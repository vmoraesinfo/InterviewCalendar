package com.tamanna.InterviewCalendar.api.model;


import lombok.*;

import javax.validation.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class People {
    @NotNull(message = "Person name must not be blank!")
    private String name;
    @NotNull(message = "The availability must not be blank!")
    @Valid
    private List<Availability> availabilityEntityList = new ArrayList<Availability>();
    private int peopleType;
}
