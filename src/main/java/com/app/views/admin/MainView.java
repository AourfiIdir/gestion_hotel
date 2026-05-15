package com.app.views.admin;
import javax.swing.*;
import java.awt.*;
import com.app.models.*;
import java.util.Vector;
import com.app.utils.*;
import com.app.models.DAO.*;
import com.app.controllers.admin.*;
public class MainView extends JFrame{
    ClientDao clientDao;
    JPanel clientsPanel;
    Vector<Client> myClients;
    JLabel countLabel;

    public MainView(){
        this.setSize(1000,1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("ADMIN");
        this.setLayout(new BorderLayout(16, 16));
        this.getContentPane().setBackground(new Color(242, 246, 252));

        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        headerPanel.setBackground(new Color(28, 45, 74));

        JLabel titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));

        countLabel = new JLabel(" ");
        countLabel.setForeground(new Color(203, 218, 242));
        countLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(countLabel, BorderLayout.SOUTH);

        //initilialization
        clientDao = new ClientDao();
        myClients = getClients(clientDao);
        clientsPanel = new JPanel();
        clientsPanel.setLayout(new BoxLayout(clientsPanel, BoxLayout.Y_AXIS));
        clientsPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(clientsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);

        JButton refreshButton = new JButton("Refresh clients");
        refreshButton.setFocusPainted(false);
        refreshButton.setBackground(new Color(45, 115, 225));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        refreshButton.addActionListener(e -> refreshClients());

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        actionPanel.setOpaque(false);
        actionPanel.add(refreshButton);

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setOpaque(false);
        topPanel.add(headerPanel, BorderLayout.CENTER);
        topPanel.add(actionPanel, BorderLayout.EAST);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);

        refreshClients();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    public Vector<Client> getClients(ClientDao clientDao){
        Vector<Client> myClients;
        myClients = clientDao.getAll();
        return myClients;
    }

    private void refreshClients(){
        myClients = getClients(clientDao);
        clientsPanel.removeAll();

        countLabel.setText("Total clients: " + myClients.size());

        if (myClients.isEmpty()) {
            JLabel emptyLabel = new JLabel("No clients found.");
            emptyLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
            emptyLabel.setBorder(BorderFactory.createEmptyBorder(18, 16, 18, 16));
            emptyLabel.setForeground(new Color(95, 105, 124));
            clientsPanel.add(emptyLabel);
        } else {
            for(Client cl : myClients){
                clientsPanel.add(buildClientCard(cl));
                clientsPanel.add(Box.createVerticalStrut(10));
            }
        }

        clientsPanel.revalidate();
        clientsPanel.repaint();
    }

    private JPanel buildClientCard(Client client){
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(new Color(249, 251, 255));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(224, 231, 242), 1, true),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel nameLabel = new JLabel(client.nom + " " + client.prenom);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        nameLabel.setForeground(new Color(38, 52, 79));

        JLabel resLabel = new JLabel("Reservations: " + client.listRes.size());
        resLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        resLabel.setForeground(new Color(80, 92, 115));

        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        textPanel.setOpaque(false);
        textPanel.add(nameLabel);
        textPanel.add(resLabel);

        JButton clientBtn = new JButton("Open profile");
        clientBtn.setFocusPainted(false);
        clientBtn.setBackground(new Color(65, 100, 170));
        clientBtn.setForeground(Color.WHITE);
        clientBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        clientBtn.addActionListener(new ClientCtr(client));

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        actionPanel.setOpaque(false);
        actionPanel.add(clientBtn);

        card.add(textPanel, BorderLayout.CENTER);
        card.add(actionPanel, BorderLayout.EAST);

        return card;
    }
}
