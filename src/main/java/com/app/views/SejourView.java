package com.app.views;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.format.DateTimeFormatter;
import java.util.Vector;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import com.app.models.*;

public class SejourView extends JFrame {
    Client client;
    JPanel listPanel;
    JLabel titleLabel;
    JLabel countLabel;

    public SejourView(Client client) {
        this.client = client;
        this.setTitle("My Sejours");
        this.setSize(920, 640);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(16, 16));
        root.setBackground(new Color(244, 247, 252));
        root.setBorder(new EmptyBorder(18, 18, 18, 18));

        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(new EmptyBorder(12, 14, 12, 14));
        header.setBackground(new Color(28, 45, 74));

        titleLabel = new JLabel("Sejours - " + client.nom + " " + client.prenom);
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
        refreshCards();
        this.setVisible(true);
    }

    private void refreshCards() {
        listPanel.removeAll();
        Vector<Sejour> mySejours = new Vector<Sejour>();
        client.listRes.stream()
            .filter(r -> r.sejour != null)
            .forEach(r -> mySejours.add(r.sejour));

        countLabel.setText("Total sejours: " + mySejours.size());

        if (mySejours.isEmpty()) {
            JLabel empty = new JLabel("No sejours yet. Confirm a reservation first.");
            empty.setFont(new Font("SansSerif", Font.PLAIN, 16));
            empty.setForeground(new Color(95, 105, 124));
            empty.setBorder(new EmptyBorder(20, 8, 8, 8));
            listPanel.add(empty);
        } else {
            for (Sejour sejour : mySejours) {
                listPanel.add(buildCard(sejour));
                listPanel.add(Box.createVerticalStrut(10));
            }
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel buildCard(Sejour sejour) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(new Color(249, 251, 255));
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(224, 231, 242), 1, true),
            new EmptyBorder(12, 12, 12, 12)
        ));

        Reservation reservation = sejour.getReservation();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String chambreInfo = "Room #" + reservation.chambre.num + "  |  Type: " + reservation.chambre.type
            + "  |  Price/night: " + reservation.chambre.prix;
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

        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        infoPanel.setOpaque(false);

        JLabel consomationLabel = new JLabel("Consumptions: " + sejour.listConsom.size());
        consomationLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        consomationLabel.setForeground(new Color(80, 92, 115));

        JLabel totalLabel = new JLabel("Total bill: " + sejour.getPrixSejour());
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        totalLabel.setForeground(new Color(41, 124, 87));

        infoPanel.add(consomationLabel);
        infoPanel.add(totalLabel);

        JButton addProductsButton = new JButton("Add products");
        addProductsButton.setFocusPainted(false);
        addProductsButton.setBackground(new Color(45, 115, 225));
        addProductsButton.setForeground(Color.WHITE);
        addProductsButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        addProductsButton.addActionListener(e -> new ProductView(sejour, this::refreshCards));

        JButton consumationButton = new JButton("Consumation details");
        consumationButton.setFocusPainted(false);
        consumationButton.setBackground(new Color(65, 100, 170));
        consumationButton.setForeground(Color.WHITE);
        consumationButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        consumationButton.addActionListener(e -> new ConsomationView(sejour));

        JPanel rightPanel = new JPanel(new BorderLayout(0, 10));
        rightPanel.setOpaque(false);
        rightPanel.add(infoPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(consumationButton);
        buttonPanel.add(Box.createHorizontalStrut(8));
        buttonPanel.add(addProductsButton);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        card.add(textPanel, BorderLayout.CENTER);
        card.add(rightPanel, BorderLayout.EAST);
        return card;
    }
}
