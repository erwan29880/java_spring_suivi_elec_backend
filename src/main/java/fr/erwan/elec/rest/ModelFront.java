package fr.erwan.elec.rest;

public class ModelFront {
    private long id;
    private double hp;
    private double hc;
    private String insertedAt;    
    
    
    public ModelFront() {
    }

    public ModelFront(long id, double hp, double hc, String insertedAt) {
        this.id = id;
        this.hp = hp;
        this.hc = hc;
        this.insertedAt = insertedAt;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getHp() {
        return this.hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public double getHc() {
        return this.hc;
    }

    public void setHc(double hc) {
        this.hc = hc;
    }

    public String getInsertedAt() {
        return this.insertedAt;
    }

    public void setInsertedAt(String insertedAt) {
        this.insertedAt = insertedAt;
    }
}
