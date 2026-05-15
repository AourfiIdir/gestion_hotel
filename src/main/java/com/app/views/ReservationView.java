package com.app.views;
import javax.swing.*;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.format.DateTimeFormatter;
import java.util.Vector;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import com.app.models.*;

public class ReservationView extends JFrame{
    Client client;
    JPanel listPanel;
    JLabel titleLabel;
    JLabel countLabel;

    public ReservationView(Client client){
        this.client = client;
        this.setTitle("My Reservations");
        this.setSize(920, 640);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(16, 16));
        root.setBackground(new Color(244, 247, 252));
        root.setBorder(new EmptyBorder(18, 18, 18, 18));

        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(new EmptyBorder(12, 14, 12, 14));
        header.setBackground(new Color(28, 45, 74));

        titleLabel = new JLabel("Reservations - " + client.nom + " " + client.prenom);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(new Color(245, 248, 255));

        countLabel = new JLabel();
        countLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        countLabel.setForeground(new Color(203, 218, 242));

        header.add(titleLabel, BorderLayout.NORTH);
        header.add(countLabel, BorderLayout.SOUTH);

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);
        listPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(new LineBorder(new Color(212, 220, 232), 1, true));
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.getVerticalScrollBar().setUnitIncrement(14);

        root.add(header, BorderLayout.NORTH);
        root.add(scroll, BorderLayout.CENTER);

        this.setContentPane(root);
        refreshReservations();
        this.setVisible(true);
    }

    private void refreshReservations(){
        listPanel.removeAll();
        Vector<Reservation> reservations = client.listRes;
        
        countLabel.setText("Total reservations: " + reservations.size());

        if (reservations.isEmpty()) {
            JLabel empty = new JLabel("No reservations yet.");
            empty.setFont(new Font("SansSerif", Font.PLAIN, 16));
            empty.setForeground(new Color(95, 105, 124));
            empty.setBorder(new EmptyBorder(20, 8, 8, 8));
            listPanel.add(empty);
        } else {
            for (Reservation reservation : reservations) {
                listPanel.add(buildReservationCard(reservation));
                listPanel.add(Box.createVerticalStrut(10));
            }
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel buildReservationCard(Reservation reservation){
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(new Color(249, 251, 255));
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(224, 231, 242), 1, true),
            new EmptyBorder(12, 12, 12, 12)
        ));

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String chambreInfo = "Room #" + reservation.chambre.num + "  |  Type: " + reservation.chambre.type +
            "  |  Price/night: " + reservation.chambre.prix;
        String periodInfo = "From " + reservation.debut.format(fmt) + " to " + reservation.fin.format(fmt);

        JLabel chambreLabel = new JLabel(chambreInfo);
        chambreLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        chambreLabel.setForeground(new Color(38, 52, 79));

        JLabel dateLabel = new JLabel(periodInfo);
        dateLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        dateLabel.setForeground(new Color(80, 92, 115));

        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        textPanel.setOpaque(false);
        textPanel.add(chambreLabel);
        textPanel.add(dateLabel);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        actionPanel.setOpaque(false);

        JButton confirmButton = new JButton();
        confirmButton.setFocusPainted(false);
        confirmButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        confirmButton.setPreferredSize(new java.awt.Dimension(190, 36));
        JButton cancelButton = new JButton();
        cancelButton.setFocusPainted(false);
        cancelButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        cancelButton.setPreferredSize(new java.awt.Dimension(190, 36));
 
        if (reservation.sejour == null) {
            confirmButton.setText("Confirm (Create Sejour)");
            confirmButton.setBackground(new Color(41, 124, 87));
            confirmButton.setForeground(Color.WHITE);
            confirmButton.addActionListener(e -> {
                new Sejour(reservation);
                refreshReservations();
            });
            cancelButton.setText("Cancel Reservation");
            cancelButton.setBackground(new Color(220, 60, 60));
            cancelButton.setForeground(Color.WHITE);
            cancelButton.addActionListener(e -> {
               // remove reservation from client and chambre, then refresh view
               client.listRes.remove(reservation);
               if (reservation.chambre != null) {
                   reservation.chambre.listRes.remove(reservation);
               }
               refreshReservations();
            });
        } else {
            confirmButton.setText("Sejour already created");
            confirmButton.setEnabled(false);
            confirmButton.setBackground(new Color(210, 216, 229));
            confirmButton.setForeground(new Color(75, 82, 98));
        }

        actionPanel.add(confirmButton);
        actionPanel.add(cancelButton);

        card.add(textPanel, BorderLayout.CENTER);
        card.add(actionPanel, BorderLayout.EAST);
        return card;
    }
    
}
