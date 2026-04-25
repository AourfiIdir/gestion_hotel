package com.app.controllers;
import com.app.models.*;
import com.app.views.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.*;
import javax.swing.*;
public class HotelSearchCtr implements  ActionListener{
    Vector<Chambre> chambres;
    Hotel hotel;
    LocalDate deb;
    LocalDate fin;
    HotelView hotelView;
    public HotelSearchCtr(Hotel hotel,HotelView hotelView){
        this.hotel = hotel;
        this.hotelView = hotelView;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        chambres = new Vector<Chambre>();
        this.deb = LocalDate.parse(hotelView.dateDebut.getText());
        this.fin = LocalDate.parse(hotelView.dateFin.getText());
        for(Chambre ch : hotel.listChambre){
            JButton chambre;
            if(ch.isfree(deb,fin)){
                chambre = new JButton("chambre: " + ch.num );
            }else{
                chambre = new JButton("chambre: " + ch.num);
            }
            chambres.add(ch);
        }
        if(chambres.isEmpty()){
            hotelView.chambrePanel.add(new JLabel("no room found"));
        }else{
            for(Chambre r:chambres){
            JButton ch = new JButton(Integer.toString(r.num));
            ChambreCtr openRoom = new ChambreCtr(r);
            ch.addActionListener(openRoom);
            //Add an action listener to open a Chambre JFrame for each one
            hotelView.chambrePanel.add(ch);
            }
        }
        hotelView.chambrePanel.revalidate();
        hotelView.chambrePanel.repaint();
        
    }

}