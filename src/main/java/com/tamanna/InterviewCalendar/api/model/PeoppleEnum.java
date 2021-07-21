package com.tamanna.InterviewCalendar.api.model;

public enum PeoppleEnum {

    INTERVIEWER(1, "Interviewer"),
    CANDIDATE(2,"Candidate");

    private int idPeopleType;
    private String peopleName;
    PeoppleEnum(int idPeopleType, String peopleName){
        this.idPeopleType = idPeopleType;
        this.peopleName = peopleName;
    }


    public static int getIndPeopleType(String type){
        for(PeoppleEnum peoppleEnum: PeoppleEnum.values()){
            if(peoppleEnum.peopleName.equalsIgnoreCase(type)){
                return peoppleEnum.idPeopleType;
            }
        }
        return -1;
    }
}
