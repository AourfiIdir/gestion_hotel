package com.app.controllers.admin;
import java.awt.event.*;
import com.app.models.*;
import com.app.views.admin.*;

public class ClientCtr implements ActionListener{
    Client client;
    public ClientCtr(Client client){
        this.client = client;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        new ClientView(client);
    }
}
