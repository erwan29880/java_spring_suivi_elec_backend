package fr.erwan.elec.rest;
// import java.sql.Date;


public class Model {
    private long id;
    private double hp;
    private double hc;
    private java.sql.Date insertedAt;    
    
    
    public Model() {
    }

    public Model(long id, double hp, double hc, java.sql.Date insertedAt) {
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

    public java.sql.Date getInsertedAt() {
        return this.insertedAt;
    }

    public void setInsertedAt(java.sql.Date insertedAt) {
        this.insertedAt = insertedAt;
    }
}
