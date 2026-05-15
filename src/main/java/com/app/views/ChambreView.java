package com.app.views;

import com.app.controllers.ReservationAdder;
import com.app.models.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.time.LocalDate;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.image.BufferedImage;

public class ChambreView extends JFrame{
    //description:
    JLabel titleLabel;
    JLabel detailsLabel;
    JLabel dateLabel;
    JLabel infoLabel;
    Chambre chambre;
    HotelView hotelView;
    JButton reservation;

    public ChambreView(Chambre chambre,HotelView hotelView){
        this.setTitle("Room Details");
        this.setSize(980, 680);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.chambre = chambre;
        this.hotelView=hotelView;

        JPanel root = new JPanel(new BorderLayout(16, 16));
        root.setBackground(new Color(242, 246, 252));
        root.setBorder(new EmptyBorder(18, 18, 18, 18));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(28, 45, 74));
        header.setBorder(new EmptyBorder(14, 18, 14, 18));

        titleLabel = new JLabel("Room #" + chambre.num + " - " + chambre.type);
        titleLabel.setForeground(new Color(246, 249, 255));
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));

        detailsLabel = new JLabel("Floor: " + chambre.etage + "   |   Price per night: " + chambre.prix);
        detailsLabel.setForeground(new Color(205, 218, 241));
        detailsLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JPanel titleBox = new JPanel(new BorderLayout(0, 4));
        titleBox.setOpaque(false);
        titleBox.add(titleLabel, BorderLayout.NORTH);
        titleBox.add(detailsLabel, BorderLayout.SOUTH);
        header.add(titleBox, BorderLayout.CENTER);

        JPanel content = new JPanel(new BorderLayout(14, 14));
        content.setOpaque(false);

        BufferedImage image = null;
        try {
            if (ChambreView.class.getResourceAsStream("/images/" + chambre.num + ".jpg") != null) {
                image = ImageIO.read(ChambreView.class.getResourceAsStream("/images/" + chambre.num + ".jpg"));
            }
        } catch(IOException e){
            e.printStackTrace();
        }

        final BufferedImage roomImage = image;
        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                if (roomImage != null) {
                    g2d.drawImage(roomImage, 0, 0, getWidth(), getHeight(), null);
                } else {
                    g2d.setColor(new Color(60, 70, 90));
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                    g2d.setColor(new Color(230, 236, 249));
                    g2d.setFont(new Font("SansSerif", Font.BOLD, 18));
                    g2d.drawString("No room image", 22, 36);
                }
                g2d.dispose();
            }
        }; 
        imagePanel.setPreferredSize(new Dimension(560, 420)); 
        imagePanel.setBorder(new LineBorder(new Color(212, 220, 232), 1, true));
        JPanel rightCard = new JPanel(new BorderLayout(12, 12)); rightCard.setBackground(Color.WHITE);
        rightCard.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(212, 220, 232), 1, true),
            new EmptyBorder(14, 14, 14, 14)
        ));
        dateLabel = new JLabel(buildDateText());
        dateLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        dateLabel.setForeground(new Color(75, 90, 118));

        infoLabel = new JLabel("Click reservation to confirm this room for selected dates.");
        infoLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        infoLabel.setForeground(new Color(95, 105, 124));

        reservation = new JButton("Reservation");
        reservation.setFocusPainted(false);
        reservation.setBackground(new Color(41, 124, 87));
        reservation.setForeground(Color.WHITE);
        reservation.setFont(new Font("SansSerif", Font.BOLD, 14));
        reservation.setPreferredSize(new Dimension(220, 42));

        ReservationAdder adder = new ReservationAdder(hotelView, chambre);
        reservation.addActionListener(e -> {
            adder.actionPerformed(e);
            infoLabel.setText("Reservation created. You can view it in My Reservations.");
            reservation.setEnabled(false);
        });
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        actionPanel.setOpaque(false);
        actionPanel.add(reservation);

        JPanel textPanel = new JPanel(new BorderLayout(0, 10));
        textPanel.setOpaque(false);
        textPanel.add(dateLabel, BorderLayout.NORTH);
        textPanel.add(infoLabel, BorderLayout.CENTER);
        textPanel.add(actionPanel, BorderLayout.SOUTH);

        rightCard.add(textPanel, BorderLayout.NORTH);

        content.add(imagePanel, BorderLayout.CENTER);
        content.add(rightCard, BorderLayout.EAST);

        root.add(header, BorderLayout.NORTH);
        root.add(content, BorderLayout.CENTER);

        this.setContentPane(root);
        this.setVisible(true);
    }

    private String buildDateText(){
        try {
            LocalDate deb = hotelView.getDeb();
            LocalDate fin = hotelView.getFin();
            return "Period: " + deb + " to " + fin;
        } catch (Exception e) {
            return "Period: not set (search dates first in hotel view)";
        }
    }
    
}   