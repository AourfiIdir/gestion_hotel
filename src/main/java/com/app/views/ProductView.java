package com.app.views;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Vector;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import com.app.models.*;

public class ProductView extends JFrame {
    private final Sejour sejour;
    private final Runnable onProductsAdded;
    private final JPanel listPanel;
    private final JLabel countLabel;

    public ProductView(Sejour sejour, Runnable onProductsAdded) {
        this.sejour = sejour;
        this.onProductsAdded = onProductsAdded;

        this.setTitle("Products");
        this.setSize(760, 560);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(16, 16));
        root.setBackground(new Color(244, 247, 252));
        root.setBorder(new EmptyBorder(18, 18, 18, 18));

        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(new EmptyBorder(12, 14, 12, 14));
        header.setBackground(new Color(28, 45, 74));

        JLabel titleLabel = new JLabel("Select Products To Consume");
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
        refreshProducts();
        this.setVisible(true);
    }

    private void refreshProducts() {
        listPanel.removeAll();
        Vector<Produit> products = sejour.getReservation().client.hotel.listProduit;
        countLabel.setText("Available products: " + products.size());

        if (products.isEmpty()) {
            JLabel empty = new JLabel("No products configured for this hotel.");
            empty.setFont(new Font("SansSerif", Font.PLAIN, 16));
            empty.setForeground(new Color(95, 105, 124));
            empty.setBorder(new EmptyBorder(20, 8, 8, 8));
            listPanel.add(empty);
        } else {
            for (Produit product : products) {
                listPanel.add(buildProductCard(product));
                listPanel.add(Box.createVerticalStrut(10));
            }
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel buildProductCard(Produit product) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(new Color(249, 251, 255));
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(224, 231, 242), 1, true),
            new EmptyBorder(12, 12, 12, 12)
        ));

        JLabel productNameLabel = new JLabel(product.nom);
        productNameLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        productNameLabel.setForeground(new Color(38, 52, 79));

        JLabel productPriceLabel = new JLabel("Price: " + product.prix);
        productPriceLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        productPriceLabel.setForeground(new Color(80, 92, 115));

        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        textPanel.setOpaque(false);
        textPanel.add(productNameLabel);
        textPanel.add(productPriceLabel);

        JButton addButton = new JButton("Add to consume");
        addButton.setFocusPainted(false);
        addButton.setBackground(new Color(41, 124, 87));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        addButton.addActionListener(e -> {
            Consomation consomation = new Consomation(1, sejour);
            consomation.ajouteProduit(product);
            if (onProductsAdded != null) {
                onProductsAdded.run();
            }
            JOptionPane.showMessageDialog(this, "Product added to sejour.");
        });

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        actionPanel.setOpaque(false);
        actionPanel.add(addButton);

        card.add(textPanel, BorderLayout.CENTER);
        card.add(actionPanel, BorderLayout.EAST);

        return card;
    }
}
