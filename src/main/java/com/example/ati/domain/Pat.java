package com.example.ati.domain;

public class Pat extends Entity<Integer>{

    private Type tip;

    private boolean ventilation;

    private Pacient pacient;

    public Pat (Type tip, boolean ventilation, Pacient pacient) {
        this.tip = tip;
        this.ventilation = ventilation;
        this.pacient = pacient;
    }

    public Type getTip() {
        return tip;
    }

    public boolean isVentilation() {
        return ventilation;
    }

    public Pacient getPacient() {
        return pacient;
    }

    public void setPacient(Pacient pacient) {
        this.pacient = pacient;
    }

    @Override
    public String toString() {
        return "Pat{" +
                "id=" + getId() +
                ", tip=" + tip +
                ", ventilation=" + ventilation +
                ", pacient=" + pacient +
                '}';
    }
}
