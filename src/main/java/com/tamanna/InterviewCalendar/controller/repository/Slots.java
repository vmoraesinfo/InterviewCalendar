package com.tamanna.InterviewCalendar.controller.repository;


import lombok.*;

import java.util.*;

@Data
public class Slots {
  private int hr;
  private List<String> interviewers = new ArrayList<>();
  private String candidate;
}
