package com.app.views;
import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import com.app.controllers.*;
import com.app.models.*;

public class LoginView extends JFrame{
    //Declaration
    JLabel name;
    JLabel prenom;
    JTextField nameField;
    JTextField prenomField;
    JButton login;
    JLabel errorLabel;
    public JPanel backgroundPanel;
    public LoginView(){
        // Frame
        this.setSize(1000,900);
        this.setTitle("LOGIN PAGE");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        BufferedImage image = null;
        try {
            if (LoginView.class.getResourceAsStream("/images/login.jpg") != null) {
                image = ImageIO.read(LoginView.class.getResourceAsStream("/images/login.jpg"));
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        
        final BufferedImage backgroundImage = image;
        backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                if (backgroundImage != null) {
                    g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
                } else {
                    g2d.setColor(new Color(24, 31, 44));
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }

                // Dark overlay so text remains readable.
                g2d.setColor(new Color(0, 0, 0, 110));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());

        // Initialization
        name = new JLabel("Enter your name");
        prenom = new JLabel("Enter your family name");
        nameField = new JTextField();
        prenomField = new JTextField();
        login = new JButton("LOGIN");
        errorLabel = new JLabel(" ");

        Font labelFont = new Font("SansSerif", Font.BOLD, 21);
        name.setFont(labelFont);
        prenom.setFont(labelFont);
        name.setForeground(new Color(248, 248, 248));
        prenom.setForeground(new Color(248, 248, 248));

        nameField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        prenomField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        nameField.setBackground(new Color(255, 255, 255));
        prenomField.setBackground(new Color(255, 255, 255));
        nameField.setForeground(new Color(20, 20, 20));
        prenomField.setForeground(new Color(20, 20, 20));
        nameField.setCaretColor(new Color(20, 20, 20));
        prenomField.setCaretColor(new Color(20, 20, 20));
        nameField.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(70, 110, 180), 2, true), new EmptyBorder(8, 12, 8, 12)));
        prenomField.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(70, 110, 180), 2, true), new EmptyBorder(8, 12, 8, 12)));

        login.setPreferredSize(new Dimension(220, 48));
        login.setFocusPainted(false);
        login.setBackground(new Color(55, 132, 255));
        login.setForeground(Color.WHITE);
        login.setFont(new Font("SansSerif", Font.BOLD, 16));
        login.setBorder(new LineBorder(new Color(35, 95, 185), 2, true));

        errorLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        errorLabel.setForeground(new Color(255, 120, 120));

        JPanel formPanel = new JPanel(null);
        formPanel.setOpaque(true);
        formPanel.setBackground(new Color(15, 20, 30, 205));
        formPanel.setBorder(BorderFactory.createEmptyBorder(28, 28, 28, 28));
        formPanel.setPreferredSize(new Dimension(640, 360));

        name.setBounds(60, 70, 220, 38);
        nameField.setBounds(290, 66, 280, 44);

        prenom.setBounds(60, 145, 240, 38);
        prenomField.setBounds(290, 141, 280, 44);

        login.setBounds(210, 235, 220, 48);
        errorLabel.setBounds(160, 300, 360, 28);

        formPanel.add(name);
        formPanel.add(nameField);
        formPanel.add(prenom);
        formPanel.add(prenomField);
        formPanel.add(login);
        formPanel.add(errorLabel);

        backgroundPanel.add(formPanel);
        this.setContentPane(backgroundPanel);

        // Actions
        LoginCtr loginCtr = new LoginCtr(this);
        login.addActionListener(loginCtr);

        this.setVisible(true);
    }
    public String getName(){
        return nameField.getText();
    }
    public String getPrenom(){
        return prenomField.getText();
    }

    public void showError(String message){
        errorLabel.setText(message);
    }

    public void clearError(){
        errorLabel.setText(" ");
    }

    public void showHotelView(Hotel hotel, Client client){
        this.setTitle("HOTEL PAGE");
        this.setContentPane(new HotelView(hotel, client));
        this.revalidate();
        this.repaint();
    }
    

}