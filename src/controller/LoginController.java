package controller;

import users.Client;
import utils.MainModel;
import utils.Verifier;

/**
 * Created by Ivo Mladenov
 * COMP2211 SEG Ad Auction Dashboard.
 */
// controller.Controller for dealing with login modal
public class LoginController extends Controller {

    /**
     * Attach the model directly when initialising the Controller
     */
    public LoginController() {
        super(null);
    }

    /**
     *
     * @param username
     * @param password
     * @return -1 if no such user, otherwise the access level of a user
     */
    public int verify(String username, char[] password) {
        int lvl;
        StringBuilder sb = new StringBuilder();
        sb.append(password);
        System.out.println(sb.toString());

        lvl = Verifier.verify(username, sb.toString());
        return lvl;
    }

}
