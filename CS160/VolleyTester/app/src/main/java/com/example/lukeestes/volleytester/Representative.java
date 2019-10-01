package com.example.lukeestes.volleytester;

public class Representative {
    public String type; // Type of representative (senator or representative)
    public String contactURL; // Contact url of representative
    public String contactForm; // Contact form of representative
    public String districtNumber; // Representing district number
    public String name; // Name of the representative
    public String party; // Party affiliation of representative

    public Representative (String t, String ctU, String ctF, String disNum, String n, String p) {
        type = t;
        contactURL = ctU;
        contactForm = ctF;
        districtNumber = disNum;
        name = n;
        party = p;
    }

    // Returns true if this rep has same contact URL as input
    // (comparing potential new reps to current ones)
    public boolean sameURL (String currRepURL) {
        return (contactURL.equals(currRepURL));
    }

    @Override
    // Personal rep toString() method
    public String toString() {
        if (type.equals("representative")) {
            return "House Rep: " + name;
        } else {
            return "Senator: " + name;
        }
    }
}
