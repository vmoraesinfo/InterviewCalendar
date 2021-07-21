package com.tamanna.InterviewCalendar.api.model;


import lombok.*;

import javax.validation.constraints.*;

@NoArgsConstructor
@Data
public class Availability {

    @NotNull(message = "Day name must not be blank!")
    private String name;
    @NotNull(message = "Dates must not be blank!")
    private String ranges;
}
