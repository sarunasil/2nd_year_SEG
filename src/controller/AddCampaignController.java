package controller;

import model.Campaign;
import users.Admin;
import users.User;
import utils.Parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Ivo Mladenov
 * COMP2211 SEG Ad Auction Dashboard.
 */
public class AddCampaignController extends Controller {
    File server;
    File click;
    File impression;

    public AddCampaignController(User user) {
        super(user);
    }

    public boolean provideLogFiles(String campaignName) throws FileNotFoundException, IOException {
        if (server != null && click != null && impression != null) {
            ((Admin) user).provideLogFiles(user.getUsername(), campaignName, server, click, impression);
            server = null;
            click = null;
            impression = null;
            return true;
        }
        return false;
    }

    public boolean setServerFile(File server) {
        this.server = server;
        return true;
    }

    public boolean setClickFile(File click) {
        this.click = click;
        return true;
    }

    public boolean setImpressionFile(File impression) {
        this.impression = impression;
        return true;
    }
}
