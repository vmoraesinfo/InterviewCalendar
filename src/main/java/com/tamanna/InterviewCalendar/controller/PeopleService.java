package com.tamanna.InterviewCalendar.controller;


import com.tamanna.InterviewCalendar.api.model.Availability;
import com.tamanna.InterviewCalendar.api.model.People;
import com.tamanna.InterviewCalendar.controller.entity.PeopleEntity;
import com.tamanna.InterviewCalendar.controller.repository.*;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class PeopleService {

  private Agenda agenda = Agenda.getInstance();

  public void addOnAgenda( People people ) {

    List<Availability> availabilityEntityList = people.getAvailabilityEntityList();
    PeopleEntity entity = getPeopleEntity( people );
    availabilityEntityList.forEach( availability -> {
      String[] ranges = availability.getRanges().split( ";" );
      Stream.of( ranges ).forEach( range -> {
        Day day = getDayPopulated( entity, availability, range );
        agenda.getDays().put( availability.getName(), day );
      } );
    } );
  }

  private Day getDayPopulated( PeopleEntity entity, Availability availability, String range ) {
    String[] values = range.split( "-" );
    int init = Integer.parseInt( values[ 0 ] );
    int end = Integer.parseInt( values[ 1 ] );
    Day day = new Day();
    while ( init < end ) {
      day = getDayFromAgenda( availability, day );
      Time time = getTimePopulated( entity, init, day );
      day.getHr().add( time );
      init++;
    }
    return day;
  }

  private PeopleEntity getPeopleEntity( People people ) {
    PeopleEntity entity = new PeopleEntity();
    entity.setName( people.getName() );
    entity.setPeopleType( people.getPeopleType() );
    return entity;
  }

  private Day getDayFromAgenda( Availability availability, Day day ) {
    if ( agenda.getDays().containsKey( availability.getName() ) ) {
      day = agenda.getDays().get( availability.getName() );
      agenda.getDays().remove( availability.getName() );
    }
    day.setDay( availability.getName() );
    return day;
  }

  private Time getTimePopulated( PeopleEntity entity, int init, Day day ) {
    Time time = null;
    Optional op = day.getHr().stream().filter( time1 -> time1.getTime() == init ).findFirst();
    if(op.isPresent()){
      time = (Time) op.get();
      day.getHr().remove( time );
    }

    if(time == null){
      time = new Time();
      time.setTime( init );
    }

    time.getPeople().add( entity );
    return time;
  }

  public boolean hasCandidate(String candidateName){
    return agenda.getDays().entrySet().stream().parallel()
      .filter(e -> e.getValue().getHr().stream().parallel()
        .anyMatch( element -> element.getPeople().stream().parallel()
          .anyMatch( el -> el.getName().equals( candidateName ) ) ))
      .findFirst()
      .orElse(null) != null;
  }

  public  Map<String, List<Slots>> findSlots(String candidateName){
    Map<String, List<Slots>> list = new HashMap<>();
      for(String key: agenda.getDays().keySet()){
        Day day = agenda.getDays().get( key );
        day.getHr().forEach( time -> {
          List<Slots> slots = new ArrayList<>();
          Slots slot = new Slots();
          slot.setCandidate( candidateName );
          slot.setHr( time.getTime() );
          time.getPeople().forEach( person->{
            if(person.getName().equals( candidateName ) && person.getPeopleType() == 2){
              slots.add( slot );
            }else{
              slot.getInterviewers().add( person.getName() );
            }
          } );
          if(slots.size() > 0){
            list.put( day.getDay(), slots );
          }
        } );
      }
    return list;
  }


  public void cleanAgenda(){
    agenda.clear();
  }

  public  Agenda getAllAgenda(){

    return agenda;
  }


}
