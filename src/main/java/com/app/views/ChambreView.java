package com.app.views;

import com.app.models.*;
import java.awt.BorderLayout;
import javax.swing.*;


public class ChambreView extends JFrame{
    //description:
    JLabel description;

    //
    JButton reservation;
    JButton confirmation;
    public ChambreView(Chambre chambre){
        this.setSize(1000,1000);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        //initialization
        description = new JLabel("Numero: "+chambre.num+",etage: "+chambre.etage+", type: "+chambre.type);
        reservation = new JButton("Reservation");
        confirmation = new JButton("Confirmation");

        this.setVisible(true);
    }
    
}   