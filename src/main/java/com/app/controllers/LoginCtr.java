package com.app.controllers;
import java.awt.event.*;
import com.app.views.*;
import com.app.models.*;
import com.app.models.DAO.*;
import com.app.utils.InitDb;

import java.util.Optional;
import java.util.Optional.*;
public class LoginCtr implements ActionListener{
    LoginView lv;
    ClientDao clientService;
    public LoginCtr(LoginView lv){
        this.lv=lv;
        clientService = new ClientDao();
    }
    private boolean clientExists(){
        Optional<Client> client = Optional.empty();
        client=clientService.get(lv.getName().trim());
        return !client.isEmpty();
        
    }
    @Override
    public void actionPerformed(ActionEvent e) {
                String nom = lv.getName().trim();
                String prenom = lv.getPrenom().trim();
           if(!nom.isEmpty() && !prenom.isEmpty()){
                lv.clearError();
                Client client = new Client(nom,prenom,InitDb.getHotel());
                if(!clientExists()){  
                    clientService = new ClientDao();
                    clientService.insert(client);
                }
                lv.showHotelView(InitDb.getHotel(), client);
        }else{
              lv.showError("Nom and Prenom are mandatory");
        }
    }
    
}
