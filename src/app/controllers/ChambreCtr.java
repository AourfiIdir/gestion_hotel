package app.controllers;
import app.models.*;
import app.views.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.*;
import javax.swing.*;

public class ChambreCtr implements ActionListener{
    Chambre ch;
    public ChambreCtr(Chambre ch){
        this.ch = ch;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        ChambreView chView = new ChambreView(ch);
    }
}