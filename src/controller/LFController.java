package controller;

import model.Campaign;
import users.Admin;
import users.Client;
import users.User;
import utils.Loader;
import utils.MainModel;
import utils.Verifier;

import java.util.*;

/**
 /**
 * Created by Ivo Mladenov
 * COMP2211 SEG Ad Auction Dashboard.
 */
public class LFController extends Controller {

    public LFController(User user) {
        super(user);
    }

    public Set<String> getCampaignNames() {
        if (this.user instanceof Client) {
            return ((Client) user).getCampaignNames();
        } else if (this.user instanceof Admin) {
            return ((Admin) user).getCampaignNames();
        }
        return new HashSet<String>();
    }

    public Set<String> getUsers(int level) {
        return Loader.getUsers(level);
    }

    public String getUsername() {
        if (user != null) return this.user.getUsername();

        return "User";
    }

    public int getNumberOfImpressions(String name) {
        Campaign c = ((Client) user).getCampaign(name);
        if (c != null) return c.getNumberOfImpressions();

        return 1;
    }

    public int getNumberOfClicks(String name) {
        Campaign c = ((Client) user).getCampaign(name);
        if (c != null) return c.getNumberOfClicks();

        return 2;
    }

    public int getNumberOfUniques(String name) {
        Campaign c = ((Client) user).getCampaign(name);
        if (c != null) return c.getNumberOfUniques();

        return 3;
    }

    public int getNumberOfBounces(String name) {
        Campaign c = ((Client) user).getCampaign(name);
        if (c != null) return c.getNumberOfBounces();

        return 4;
    }

    public int getNumberOfConversions(String name) {
        Campaign c = ((Client) user).getCampaign(name);
        if (c != null) return c.getNumberOfConversions();

        return 5;
    }

    public float getTotalCost(String name) {
        Campaign c = ((Client) user).getCampaign(name);
        if (c != null) return c.getTotalCost();

        return 0;
    }
    public float getCtr(String name) {
        Campaign c = ((Client) user).getCampaign(name);
        if (c != null) return c.getCtr();

        return 6;
    }
    public float getCpa(String name) {
        Campaign c = ((Client) user).getCampaign(name);
        if (c != null) return c.getCpa();

        return 7;
    }
    public float getCpc(String name) {
        Campaign c = ((Client) user).getCampaign(name);
        if (c != null) return c.getCpc();

        return 8;
    }
    public float getCpm(String name) {
        Campaign c = ((Client) user).getCampaign(name);
        if (c != null) return c.getCpm();

        return 9;
    }
    public float getBounceRate(String name) {
        Campaign c = ((Client) user).getCampaign(name);
        if (c != null) return c.getBounceRate();

        return 10;
    }

    public Campaign loadCampaign(String campaignName) {

        if (this.user instanceof Client) {
            ((Client) user).loadCampaign(campaignName);
        } else if (this.user instanceof Admin) {
            ((Admin) user).loadCampaign(campaignName);
        }

        return ((Client) user).getCampaign(campaignName);
    }

}
