package app.models;
import java.io.*;
import java.util.*;

public class Consomation {


    public Consomation(int quantite,Sejour sejour) {
        this.quantite = quantite;
        this.sejour = sejour;
        sejour.ajouteConsom(this);
    }

    public int quantite;

    public Sejour sejour;

    public Vector<Produit> listProduits = new Vector<Produit>();
    public void ajouteProduit(Produit prod){
        this.listProduits.add(prod);
        prod.ajouteConsom(this);
    }
    public double prixConsommation(){
        double somme=0;
        for(Produit p:listProduits){
            somme +=p.prix;
        }
        return somme;
    }
}