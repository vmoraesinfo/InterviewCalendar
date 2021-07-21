package com.tamanna.InterviewCalendar.controller.repository;

import lombok.*;

import java.util.*;


@Data
public class Day {

  private List<Time> hr = new ArrayList<>();
  private String day;


}
