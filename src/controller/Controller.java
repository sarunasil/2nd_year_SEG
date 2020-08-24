package controller;

import users.Client;
import users.User;
import utils.MainModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ivo Mladenov
 * COMP2211 SEG Ad Auction Dashboard.
 */
// Generic controller.Controller class that will have a reference to the model
public class Controller {
    protected User user;

    protected Controller(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

}
