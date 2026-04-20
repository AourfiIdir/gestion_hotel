package app.views;
import app.controllers.*;
import app.models.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import javax.swing.*;

public class HotelView extends JFrame{

    
    LocalDate deb;
    LocalDate fin;

    //description
    JLabel descrJLabel;
    JPanel descriptionPanel = new JPanel();
    //reservation
    //reservation - search for a room
    JLabel chambresLabel;
    public JTextField dateDebut;
    public JTextField dateFin;
    public JPanel chambrePanel = new JPanel();
    JButton search;


    public HotelView(Hotel hotel){
        /*

         */
        // JFrame
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,1000);
        //------------------------
        //description
        descrJLabel = new JLabel("name: " + hotel.nom + " address: " + hotel.address + " notation: " + hotel.notation);
        descriptionPanel.add(descrJLabel);
        this.add(descriptionPanel,BorderLayout.NORTH);

        //------------------------
        //reservation
        //reservation - Panel
        chambrePanel.setLayout(new GridLayout(8,1,5,5));
        //reservation - descriptipn
        chambresLabel = new JLabel("chambres: ");
        //reservation - search
        dateDebut = new JTextField("enter date deb");
        dateFin = new JTextField("entre date fin");
        search = new JButton("Search");
        
        
        //actions listeners
        HotelSearchCtr getFreeRooms = new HotelSearchCtr(hotel,this);
        //settings
        chambrePanel.add(chambresLabel);
        chambrePanel.add(dateDebut);
        chambrePanel.add(dateFin);
        chambrePanel.add(search);

        search.addActionListener(getFreeRooms);
        
        
        this.add(chambrePanel,BorderLayout.WEST);

        //JFrame
        this.setVisible(true);

        

    }

}
