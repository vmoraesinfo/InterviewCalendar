package com.tamanna.InterviewCalendar.controller.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class PeopleEntity {
    private String name;
    private int peopleType;
}
