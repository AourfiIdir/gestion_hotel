package com.app.models;
import java.io.*;
import java.time.chrono.ThaiBuddhistChronology;
import java.util.*;

/**
 * 
 */
public class Client {

    /**
     * Default constructor
     */
    public Client(String nom,String prenom,Hotel hotel) {
        this(nom, prenom, hotel, true);
    }

    public Client(String nom, String prenom, Hotel hotel, boolean registerInHotel) {
        this.nom = nom;
        this.prenom = prenom;
        this.hotel = hotel;
        if (registerInHotel) {
            hotel.ajouteClient(this);
        }
    }

    /**
     * 
     */
    public String nom;

    /**
     * 
     */
    public String prenom;

    /**
     * 
     */
    public Vector<Reservation> listRes = new Vector<Reservation>();

    /**
     * 
     */
    public Hotel hotel;

    public void ajouterRes(Reservation r) {
    	this.listRes.add(r);
    }

}