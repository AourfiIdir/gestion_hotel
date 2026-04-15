package app.models;
import java.util.*;
import java.time.LocalDate;

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
        //if the dbut is in an intervall dead
        for(Reservation r : listRes){

        }
        return true;
    }

}
