package com.app.models;
import java.time.LocalDate;
import java.util.*;

/**
 * 
 */
public class Hotel {

    public String nom;

    public String address;

    public int notation;
    public Vector<Chambre> listChambre= new Vector<Chambre>();


    public Vector<Client> listClient = new Vector<Client>();


    public Vector<Produit> listProduit = new Vector<Produit>();
    /**
     * Default constructor
     */

    public Hotel(String nom,String address,int notation) {
    	this.nom=nom;
    	this.address=address;
    	this.notation=notation;
    }
    
    
    public Vector<Chambre> getFreeRooms(LocalDate deb, LocalDate fin    ){
        Vector<Chambre> freeRooms = new Vector<Chambre>();
        for(Chambre ch : listChambre){
            if(ch.isfree(deb,fin)){
                freeRooms.add(ch);
            }
        }
        return freeRooms;
    }
    
    public void ajouteChambre(Chambre c) {
    	this.listChambre.add(c);
    }
    public void ajouteClient(Client c) {
    	this.listClient.add(c);
    }
    public void ajouteProduit(Produit p) {
    	this.listProduit.add(p);
    }


}