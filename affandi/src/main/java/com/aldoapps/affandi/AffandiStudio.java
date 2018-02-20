package com.aldoapps.affandi;

public class AffandiStudio {

    // TODO: Add more configuration here

    String imageUrl;

    public Affandi paint(String imageUrl) {
        this.imageUrl = imageUrl;
        Affandi affandi = Affandi.getInstance();
        affandi.setStudio(this);
        return affandi;
    }
}
