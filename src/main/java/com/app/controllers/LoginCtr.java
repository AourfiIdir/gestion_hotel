package com.app.controllers;
import java.awt.event.*;
import com.app.views.*;
import com.app.models.*;
import com.app.models.DAO.*;
import com.app.utils.InitDb;
public class LoginCtr implements ActionListener{
    LoginView lv;
    public LoginCtr(LoginView lv){
        this.lv=lv;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
                String nom = lv.getName().trim();
                String prenom = lv.getPrenom().trim();
           if(!nom.isEmpty() && !prenom.isEmpty()){
              lv.clearError();
            Client client = new Client(nom,prenom,InitDb.getHotel());
            ClientDao clientService = new ClientDao();
            clientService.insert(client);
              lv.showHotelView(InitDb.getHotel(), client);
        }else{
              lv.showError("Nom and Prenom are mandatory");
        }
        

    }
    
}
