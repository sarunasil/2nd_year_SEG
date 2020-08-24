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
// It will handle all other controllers
public class MainController extends Controller {
    private Map<String, Controller> controllers; // map with all controllers

    /**
     * Main controller that will handle all other controllers
     * @param user
     */
    public MainController(User user) {
        super(user);  // Stoyan Botev : I had to make it public in order to be visible in MainGUI.java
        this.controllers = new HashMap<String, Controller>();
        this.addControllers();
    }

    /**
     * Attach all controllers
     * @return
     */
    private boolean addControllers() {
        // If-statement necessary to ensure that a model has been set
        if (this.user != null) {
            controllers.put("ChartsController", new ChartsController(this.user)); // add this.model later
            controllers.put("LFController", new LFController(this.user)); // add this.model later
            return true;
        }
        return false;
    }

    // Get a specific controller
    public Controller getController(String name) {
        return controllers.get(name);
    }

    /**
     * Method for adding new controllers when different frames are opened
     * @param controller
     * @param name
     */
    public void setController(Controller controller, String name) {
        this.controllers.put(name, controller);
    }
}
