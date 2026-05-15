package com.app.views.admin;
import java.awt.*;
import javax.swing.*;
import com.app.models.*;
import java.time.format.DateTimeFormatter;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
public class ClientView extends JFrame {
    Client client;
    JPanel reservationsPanel;
    JLabel countLabel;
    public ClientView(Client client ) {
        this.client = client;
        this.setTitle("Client Profile");
        this.setSize(920, 640);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(16, 16));
        root.setBackground(new Color(244, 247, 252));
        root.setBorder(new EmptyBorder(18, 18, 18, 18));

        JPanel header = new JPanel(new BorderLayout(10, 10));
        header.setBackground(new Color(28, 45, 74));
        header.setBorder(new EmptyBorder(12, 14, 12, 14));

        JLabel titleLabel = new JLabel(client.nom + " " + client.prenom);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(new Color(245, 248, 255));

        countLabel = new JLabel();
        countLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        countLabel.setForeground(new Color(203, 218, 242));

        header.add(titleLabel, BorderLayout.NORTH);
        header.add(countLabel, BorderLayout.SOUTH);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 0, 8));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(212, 220, 232), 1, true),
            new EmptyBorder(14, 14, 14, 14)
        ));

        JLabel hotelLabel = new JLabel("Hotel: " + client.hotel.nom);
        hotelLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        hotelLabel.setForeground(new Color(38, 52, 79));

        JLabel descriptionLabel = new JLabel("Client profile and reservation history");
        descriptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        descriptionLabel.setForeground(new Color(80, 92, 115));

        infoPanel.add(hotelLabel);
        infoPanel.add(descriptionLabel);

        reservationsPanel = new JPanel();
        reservationsPanel.setLayout(new BoxLayout(reservationsPanel, BoxLayout.Y_AXIS));
        reservationsPanel.setBackground(Color.WHITE);
        reservationsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(reservationsPanel);
        scrollPane.setBorder(new LineBorder(new Color(212, 220, 232), 1, true));
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);

        JPanel topSection = new JPanel(new BorderLayout(10, 10));
        topSection.setOpaque(false);
        topSection.add(header, BorderLayout.NORTH);
        topSection.add(infoPanel, BorderLayout.CENTER);

        root.add(topSection, BorderLayout.NORTH);
        root.add(scrollPane, BorderLayout.CENTER);

        this.setContentPane(root);
        refreshReservations();
        this.setVisible(true);
    }

    private void refreshReservations(){
        reservationsPanel.removeAll();
        countLabel.setText("Total reservations: " + client.listRes.size());

        if (client.listRes.isEmpty()) {
            JLabel emptyLabel = new JLabel("No reservations found for this client.");
            emptyLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
            emptyLabel.setForeground(new Color(95, 105, 124));
            emptyLabel.setBorder(new EmptyBorder(8, 8, 8, 8));
            reservationsPanel.add(emptyLabel);
        } else {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (Reservation reservation : client.listRes) {
                reservationsPanel.add(buildReservationCard(reservation, fmt));
                reservationsPanel.add(Box.createVerticalStrut(10));
            }
        }

        reservationsPanel.revalidate();
        reservationsPanel.repaint();
    }

    private JPanel buildReservationCard(Reservation reservation, DateTimeFormatter fmt){
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(new Color(249, 251, 255));
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(224, 231, 242), 1, true),
            new EmptyBorder(12, 12, 12, 12)
        ));

        JLabel roomLabel = new JLabel("Room #" + reservation.chambre.num + "  |  Type: " + reservation.chambre.type);
        roomLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        roomLabel.setForeground(new Color(38, 52, 79));

        JLabel periodLabel = new JLabel("From " + reservation.debut.format(fmt) + " to " + reservation.fin.format(fmt));
        periodLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        periodLabel.setForeground(new Color(80, 92, 115));

        JLabel priceLabel = new JLabel("Price/night: " + reservation.chambre.prix);
        priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        priceLabel.setForeground(new Color(80, 92, 115));

        JPanel textPanel = new JPanel(new GridLayout(3, 1, 0, 5));
        textPanel.setOpaque(false);
        textPanel.add(roomLabel);
        textPanel.add(periodLabel);
        textPanel.add(priceLabel);

        card.add(textPanel, BorderLayout.CENTER);
        return card;
    }

}
