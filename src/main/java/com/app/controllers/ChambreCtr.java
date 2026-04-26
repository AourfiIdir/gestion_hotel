package com.app.controllers;
import com.app.models.*;
import com.app.views.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.*;
import javax.swing.*;

public class ChambreCtr implements ActionListener{
    Chambre ch;
    HotelView  hotelView;
    public ChambreCtr(Chambre ch,HotelView hotelView){
        this.ch = ch;
        this.hotelView=hotelView;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        ChambreView chView = new ChambreView(ch,hotelView);
    }
}