package com.app.controllers;

import com.app.models.*;
import com.app.views.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.*;
import javax.swing.*;

public class ReservationAdder implements ActionListener{
    /*
        create a reservation:
            create the class with client and chambre
        add it to the client reservations list
        add it to the chambres reservation list
    */
    Chambre chambre;
    HotelView hotelView;
    public ReservationAdder(HotelView hotelView,Chambre chambre){
        this.hotelView = hotelView;
        this.chambre = chambre;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        Reservation reservation = new Reservation(hotelView.getDeb(), hotelView.getFin(), chambre, hotelView.client);
    }
}
