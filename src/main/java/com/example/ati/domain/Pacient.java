package com.example.ati.domain;

import javax.xml.transform.sax.TemplatesHandler;

public class Pacient extends Entity<Long>{

    private Integer age;
    private Boolean premature;
    private String diagnosis;
    private Integer gravity;

    private Boolean inBed;

    public Pacient(Integer age,Boolean premature, String diagnosis, Integer gravity, Boolean inBed) {
        this.age = age;
        this.premature = premature;
        this.diagnosis = diagnosis;
        this.gravity = gravity;
        this.inBed = inBed;
    }

    public Integer getAge() {
        return age;
    }

    public Boolean getPremature() {
        return premature;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public Integer getGravity() {
        return gravity;
    }

    public Boolean getInBed() {
        return inBed;
    }

    @Override
    public String toString() {
        return "Pacient{" +
                "id=" + getId() +
                ", age=" + age +
                ", premature=" + premature +
                ", diagnosis='" + diagnosis + '\'' +
                ", gravity=" + gravity +
                '}';
    }

    public void setInBed(boolean b) {
        this.inBed = b;
    }
}
