package app.models;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Sejour {

    private double facturation;

    private Reservation reservation;

    public Vector<Consomation> listConsom=new Vector<Consomation>();
    public Sejour(Reservation reservation) {
        this.reservation = reservation;
        
        reservation.setSejour(this);
    }

    
    public void ajouteConsom(Consomation consom){
        this.listConsom.add(consom);
    }
    double prixSejour(){
        double somme=0;
        for(Consomation c:listConsom){
            somme +=c.prixConsommation();
        }
        return facturation = somme + reservation.chambre.prix * (reservation.fin.toEpochDay() - reservation.debut.toEpochDay());
        
    }

}