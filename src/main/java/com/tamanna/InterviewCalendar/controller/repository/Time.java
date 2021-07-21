package com.tamanna.InterviewCalendar.controller.repository;

import com.tamanna.InterviewCalendar.controller.entity.*;
import lombok.*;

import java.util.*;

@Data
public class Time {

  private List<PeopleEntity> people = new ArrayList<>();
  private int time;



}
