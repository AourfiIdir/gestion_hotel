package com.app.views;
import com.app.controllers.*;
import com.app.models.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class HotelView extends JPanel{

    public Client client;
    LocalDate deb;
    LocalDate fin;

    //description
    JLabel descrJLabel;
    JPanel descriptionPanel = new JPanel();
    JButton myReservationsBtn;
    JButton mySejoursBtn;
    //reservation
    //reservation - search for a room
    JLabel chambresLabel;
    public JTextField dateDebut;
    public JTextField dateFin;
    public JPanel chambrePanel = new JPanel();
    JButton search;


    public HotelView(Hotel hotel,Client client){
        // Main panel
        this.client = client;
        this.setLayout(new BorderLayout(18, 18));
        this.setBackground(new Color(242, 246, 252));
        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        //------------------------
        //description
        descriptionPanel.setLayout(new BorderLayout());
        descriptionPanel.setBackground(new Color(28, 45, 74));
        descriptionPanel.setBorder(new EmptyBorder(16, 20, 16, 20));

        descrJLabel = new JLabel(
            "<html><span style='font-size:20px; color:#F8FAFF;'><b>" + hotel.nom +
            "</b></span><br><span style='font-size:13px; color:#C9D7F0;'>Address: " + hotel.address +
            "  |  Rating: " + hotel.notation +
            "  |  Guest: " + client.nom + " " + client.prenom + "</span></html>"
        );
        myReservationsBtn = new JButton("My Reservations");
        myReservationsBtn.setFocusPainted(false);
        myReservationsBtn.setBackground(new Color(65, 100, 170));
        myReservationsBtn.setForeground(Color.WHITE);
        myReservationsBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        myReservationsBtn.addActionListener(e -> new ReservationView(this.client));

        mySejoursBtn = new JButton("My Sejours");
        mySejoursBtn.setFocusPainted(false);
        mySejoursBtn.setBackground(new Color(65, 100, 170));
        mySejoursBtn.setForeground(Color.WHITE);
        mySejoursBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        mySejoursBtn.addActionListener(e -> new SejourView(this.client));

        JPanel topRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        topRight.setOpaque(false);
        topRight.add(myReservationsBtn);
        topRight.add(mySejoursBtn);

        descriptionPanel.add(descrJLabel);
        descriptionPanel.add(topRight, BorderLayout.EAST);
        this.add(descriptionPanel,BorderLayout.NORTH);

        //------------------------
        //reservation
        JPanel contentPanel = new JPanel(new BorderLayout(12, 12));
        contentPanel.setOpaque(false);

        JPanel searchCard = new JPanel(new GridLayout(2, 3, 10, 10));
        searchCard.setBackground(Color.WHITE);
        searchCard.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(212, 220, 232), 1, true),
            new EmptyBorder(14, 14, 14, 14)
        ));

        //reservation - descriptipn
        JLabel dateDebutLabel = new JLabel("Check-in date (YYYY-MM-DD)");
        JLabel dateFinLabel = new JLabel("Check-out date (YYYY-MM-DD)");
        JLabel actionLabel = new JLabel("Action");

        Font subtitleFont = new Font("SansSerif", Font.BOLD, 13);
        dateDebutLabel.setFont(subtitleFont);
        dateFinLabel.setFont(subtitleFont);
        actionLabel.setFont(subtitleFont);
        dateDebutLabel.setForeground(new Color(70, 80, 100));
        dateFinLabel.setForeground(new Color(70, 80, 100));
        actionLabel.setForeground(new Color(70, 80, 100));

        //reservation - search
        dateDebut = new JTextField("");
        dateFin = new JTextField("");
        search = new JButton("Search");
        dateDebut.setPreferredSize(new Dimension(220, 40));
        dateFin.setPreferredSize(new Dimension(220, 40));
        search.setPreferredSize(new Dimension(140, 40));
        search.setFocusPainted(false);
        search.setBackground(new Color(45, 115, 225));
        search.setForeground(Color.WHITE);
        search.setFont(new Font("SansSerif", Font.BOLD, 14));

        searchCard.add(dateDebutLabel);
        searchCard.add(dateFinLabel);
        searchCard.add(actionLabel);
        searchCard.add(dateDebut);
        searchCard.add(dateFin);
        searchCard.add(search);

        JPanel roomCard = new JPanel(new BorderLayout(10, 10));
        roomCard.setBackground(Color.WHITE);
        roomCard.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(212, 220, 232), 1, true),
            new EmptyBorder(14, 14, 14, 14)
        ));

        chambresLabel = new JLabel("Available rooms");
        chambresLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        chambresLabel.setForeground(new Color(40, 50, 72));

        JPanel roomTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        roomTitlePanel.setOpaque(false);
        roomTitlePanel.add(chambresLabel);

        chambrePanel.setLayout(new BoxLayout(chambrePanel, BoxLayout.Y_AXIS));
        chambrePanel.setOpaque(false);
        
        //actions listeners
        HotelSearchCtr getFreeRooms = new HotelSearchCtr(hotel,this);
        search.addActionListener(getFreeRooms);

        JScrollPane roomScroll = new JScrollPane(chambrePanel);
        roomScroll.setBorder(null);
        roomScroll.getVerticalScrollBar().setUnitIncrement(14);
        roomScroll.getViewport().setBackground(Color.WHITE);

        roomCard.add(roomTitlePanel, BorderLayout.NORTH);
        roomCard.add(roomScroll, BorderLayout.CENTER);

        contentPanel.add(searchCard, BorderLayout.NORTH);
        contentPanel.add(roomCard, BorderLayout.CENTER);

        this.add(contentPanel,BorderLayout.CENTER);

    }
    public LocalDate getDeb() throws DateTimeParseException{
        return LocalDate.parse(dateDebut.getText());
    }
    public LocalDate getFin() throws DateTimeParseException{
        return LocalDate.parse(dateFin.getText());
    }

}
