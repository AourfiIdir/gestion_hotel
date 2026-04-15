package app.models;
import java.io.*;
import java.util.*;

/**
 * 
 */
public class Produit {

    /**
     * Default constructor
     */
    public Produit(String nom,double prix,Hotel hotel) {
        this.nom = nom;
        this.prix = prix;
        this.hotel = hotel;
        hotel.ajouteProduit(this);
    }

    /**
     * 
     */
    public String nom;

    
    public double prix;

    
    public Hotel hotel;

    
    public Vector<Consomation> listConsom  = new Vector<Consomation>();
    public void ajouteConsom(Consomation consom){
        this.listConsom.add(consom);
    }

}