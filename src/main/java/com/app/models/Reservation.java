package com.app.models;
import java.time.LocalDate;


public class Reservation {

    public LocalDate debut;

    public LocalDate fin;

    public Client client;

    public Chambre chambre;

    public String reservé;

    public Reservation(LocalDate debut,LocalDate fin,Chambre chambre,Client client) {
        this.debut = debut;
        this.fin = fin;
        this.chambre = chambre;
        this.client=client;
        chambre.ajouterRes(this);
        client.ajouterRes(this);
    }
    
    public Sejour sejour;
    public void setSejour(Sejour sejour){
        this.sejour = sejour;
    }

}