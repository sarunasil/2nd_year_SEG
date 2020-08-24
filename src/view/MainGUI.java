package view;

import controller.LoginController;
import users.Admin;
import utils.DBInitializer;
import utils.Loader;
import utils.MainModel;

import javax.swing.*;

public class MainGUI {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

//            MainModel model = new MainModel();
            DBInitializer.initilizeDatabase();


            //the campaigns will be loaded from the database OR if there are no campaigns at all, an new Map object will be created
            Admin admin = new Admin("SEG16", "SEG16");
            if ( !Loader.createAdmin("SEG16", "SEG16") ) {
                System.out.println("User "+admin.getUsername()+" was not created");
            }

            View view = new View();
            view.initialize();
            view.setController(new LoginController());

        });

    }
}
