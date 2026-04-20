package app.views;
import javax.swing.*;


public class LoginView extends JFrame{
    JLabel name;
    JLabel password;
    JTextField nameField;
    JTextField passwordField;
    public LoginView(){
        this.setSize(1000,1000);
        this.setTitle("LOGIN PAGE");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        name = new JLabel("Enter your name");
        password = new JLabel("Enter your password");
        nameField = new JTextField();
        passwordField = new JTextField();


        this.setVisible(true);
    }

}