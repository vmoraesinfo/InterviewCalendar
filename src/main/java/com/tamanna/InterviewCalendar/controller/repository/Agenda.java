package com.tamanna.InterviewCalendar.controller.repository;

import lombok.*;

import java.util.*;


public class Agenda {

  private static Agenda instance;
  @Getter
  @Setter
  private Map<String, Day> days = new HashMap<String, Day>();


  private Agenda(){}

  public static Agenda getInstance(){
    if(instance == null){
       instance = new Agenda();
    }
    return  instance;
  }

  public void clear(){
    days.clear();
  }








}
