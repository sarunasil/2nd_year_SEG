package controller;

import users.Admin;
import users.User;
import utils.Loader;

/**
 * Created by Ivo Mladenov
 * COMP1206 Coursework Digital Doily.
 */
public class    AddClientFrameController extends Controller {
    public AddClientFrameController(Admin admin) {
        super(admin);
    }

    public boolean submit(String name, char[] password) {
        return ((Admin) user).addUser(name, password);
    }

}
