package com.app.views;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Vector;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import com.app.models.*;

public class ConsomationView extends JFrame {
    private final Sejour sejour;
    private final JPanel listPanel;
    private final JLabel countLabel;

    public ConsomationView(Sejour sejour) {
        this.sejour = sejour;

        this.setTitle("Consumation Details");
        this.setSize(760, 560);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(16, 16));
        root.setBackground(new Color(244, 247, 252));
        root.setBorder(new EmptyBorder(18, 18, 18, 18));

        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(new EmptyBorder(12, 14, 12, 14));
        header.setBackground(new Color(28, 45, 74));

        JLabel titleLabel = new JLabel("Products Consumed");
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
        refreshConsumations();
        this.setVisible(true);
    }

    private void refreshConsumations() {
        listPanel.removeAll();

        int totalProducts = 0;
        for (Consomation consomation : sejour.listConsom) {
            totalProducts += consomation.listProduits.size();
        }

        countLabel.setText("Consumation entries: " + sejour.listConsom.size() + "  |  Products: " + totalProducts);

        if (totalProducts == 0) {
            JLabel empty = new JLabel("No consumed products yet.");
            empty.setFont(new Font("SansSerif", Font.PLAIN, 16));
            empty.setForeground(new Color(95, 105, 124));
            empty.setBorder(new EmptyBorder(20, 8, 8, 8));
            listPanel.add(empty);
        } else {
            int index = 1;
            for (Consomation consomation : sejour.listConsom) {
                Vector<Produit> products = consomation.listProduits;
                for (Produit product : products) {
                    listPanel.add(buildProductCard(product, index));
                    listPanel.add(Box.createVerticalStrut(10));
                    index++;
                }
            }
        }

        JLabel totalLabel = new JLabel("Total consumed amount: " + computeConsumptionTotal());
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        totalLabel.setForeground(new Color(41, 124, 87));
        totalLabel.setBorder(new EmptyBorder(12, 8, 2, 8));
        listPanel.add(totalLabel);

        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel buildProductCard(Produit product, int index) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(new Color(249, 251, 255));
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(224, 231, 242), 1, true),
            new EmptyBorder(12, 12, 12, 12)
        ));

        JLabel productNameLabel = new JLabel(index + ". " + product.nom);
        productNameLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        productNameLabel.setForeground(new Color(38, 52, 79));

        JLabel productPriceLabel = new JLabel("Price: " + product.prix);
        productPriceLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        productPriceLabel.setForeground(new Color(80, 92, 115));

        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        textPanel.setOpaque(false);
        textPanel.add(productNameLabel);
        textPanel.add(productPriceLabel);

        card.add(textPanel, BorderLayout.CENTER);

        return card;
    }

    private double computeConsumptionTotal() {
        double total = 0;
        for (Consomation consomation : sejour.listConsom) {
            total += consomation.prixConsommation();
        }
        return total;
    }
}
