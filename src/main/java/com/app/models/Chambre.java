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
    	this.etage=etage;
    	this.type=type;
    	this.prix=prix;
    	this.num=num;
    	this.hotel=hotel;
        hotel.ajouteChambre(this);
    }
    

    public void ajouterRes(Reservation r) {
    	this.listRes.add(r);
    }

    //services
    //get if this room is free
    public boolean isfree(LocalDate deb, LocalDate fin){
        //Check the deb of the reservation
        for(Reservation r : listRes){
            if(deb.isAfter(r.debut) && deb.isBefore(r.fin)){
                return false;
            }
        }
        //Check the fin of the reservation
        for(Reservation r : listRes){
            if(fin.isAfter(r.debut) && fin.isBefore(r.fin)){
                return false;
            }
        }
        //check if there's a reservation in the middle only the deb
        for(Reservation r : listRes){
            if(r.debut.isAfter(deb) && r.fin.isBefore(fin)){
                return false;
            }
        }
        return true;
    }

}
