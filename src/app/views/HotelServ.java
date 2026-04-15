package app.views;
import app.models.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;
public class HotelServ extends JFrame{

    

    //description
    JLabel descrJLabel;
    JPanel descriptionPanel = new JPanel();
    //reservation
    JLabel chambresLabel;
    JPanel chambrePanel = new JPanel();


    public HotelServ(Hotel hotel){
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,1000);
        //------------------------
        //description
        descrJLabel = new JLabel("name: " + hotel.nom + " address: " + hotel.address + " notation: " + hotel.notation);
        descriptionPanel.add(descrJLabel);
        this.add(descriptionPanel,BorderLayout.NORTH);

        //chambres
        chambrePanel.setLayout(new GridLayout(8,1,5,5));
        chambresLabel = new JLabel("chambres: ");
        for(Chambre ch : hotel.listChambre){
            JButton chambre;
            if(ch.isfree()){
                chambre = new JButton("chambre: " + ch.num );
            }else{
                chambre = new JButton("chambre: " + ch.num);
            }
            

            chambrePanel.add(chambre);

        }
        chambrePanel.add(chambresLabel);
        this.add(chambrePanel,BorderLayout.WEST);


        this.setVisible(true);

        

    }

}
