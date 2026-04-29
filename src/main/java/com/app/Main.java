package com.app;
import com.app.models.*;
import com.app.views.*;
import com.app.utils.InitDb;
import java.time.LocalDate;
public class Main{
    

    public static void main(String[]args){
        InitDb.initDb();

        LocalDate date1_d = LocalDate.parse("2024-06-01");
        LocalDate date1_f = LocalDate.parse("2024-06-10");

        LocalDate date2_d = LocalDate.parse("2024-06-01");
        LocalDate date2_f = LocalDate.parse("2024-06-10");

        LocalDate date3_d = LocalDate.parse("2024-06-01");
        LocalDate date3_f = LocalDate.parse("2024-06-10");

        Hotel hotel1 = InitDb.getHotel();


        Chambre chambre1 = new Chambre(1,"simple",150,1,hotel1);
        Chambre chambre2 = new Chambre(2,"premium",200,2,hotel1);
        Chambre chambre3 = new Chambre(3,"simple",200,3,hotel1);

        Client zizou = new Client("zizou","lmistrale",hotel1);
        Client idir = new Client("idir","aourfi",hotel1);
        Client mourad = new Client("mourad","nassiri",hotel1);

        Reservation ch1_zizou = new Reservation(date1_d,date1_f,chambre1,zizou);
        Reservation ch2_idir = new Reservation(date2_d,date2_f,chambre2,idir);
        Reservation ch3_mourad = new Reservation(date3_d,date3_f,chambre3,mourad);

        Sejour zizouSej = new Sejour(ch1_zizou);
        Sejour idirSej = new Sejour(ch2_idir);

        //products
        Produit product1 = new Produit("boisson",8,hotel1);
        Produit product2 = new Produit("pizza",8,hotel1);
        Produit product3 = new Produit("supplements",8,hotel1);

        //consommation
        Consomation consoma1_idir = new Consomation(5,idirSej);
        
        consoma1_idir.ajouteProduit(product3);
        
        consoma1_idir.ajouteProduit(product2);
        //HotelView hotelView = new HotelView(hotel1);
        LoginView loginView = new LoginView();       
    }
}
