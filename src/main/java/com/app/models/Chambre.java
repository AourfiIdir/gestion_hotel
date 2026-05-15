package com.app.models;
import java.time.LocalDate;
import java.util.*;

/**
 * 
 */
public class Chambre {

    public Hotel hotel;

    public int etage;


    public String type;


    public double prix;


    public int num;


    public Vector<Reservation> listRes= new Vector<Reservation>();
    /**
     * Default constructor
     */
    public Chambre(int etage,String type,double prix,int num,Hotel hotel) {
        this(etage, type, prix, num, hotel, true);
    }

    public Chambre(int etage, String type, double prix, int num, Hotel hotel, boolean registerInHotel) {
    	this.etage=etage;
    	this.type=type;
    	this.prix=prix;
    	this.num=num;
    	this.hotel=hotel;
        if (registerInHotel) {
            hotel.ajouteChambre(this);
        }
    }
    

    public void ajouterRes(Reservation r) {
    	this.listRes.add(r);
    }

    //services
    //get if this room is free
    public boolean isfree(LocalDate deb, LocalDate fin){
        for(Reservation r : listRes){
            // two intervals [r.debut, r.fin] and [deb, fin] overlap unless
            // r.fin < deb OR r.debut > fin
            if(!(r.fin.isBefore(deb) || r.debut.isAfter(fin))){
                return false;
            }
        }
        return true;
    
    }
}
