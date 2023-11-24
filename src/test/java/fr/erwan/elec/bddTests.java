package fr.erwan.elec;

import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.sql.SQLException;
import java.util.List;

import fr.erwan.elec.bdd.Connect;
import fr.erwan.elec.bdd.Requetes;
import fr.erwan.elec.config.Config;
import fr.erwan.elec.rest.Model;
import fr.erwan.elec.rest.ModelFront;


/**
 * classe de test de la classe bdd.Requetes
 */
@SpringBootTest
public class bddTests {
    
    private Requetes req;
    
    @BeforeEach
    public void setEnv() {
        Config.setEnv("dev");
    }

    @BeforeEach 
    private void detReq() {
        this.req = new Requetes();
    } 

    @Test
    public void testConnection() {
        Connect conn = new Connect();
        try {
            assertNotEquals(conn.getConnexion(), null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertDoesNotThrow(() -> conn.getConnexion(), "test de la classe de Connection");
    }

 
    @Test 
    public void testGetData() {
        List<Model> data = this.req.getData();
        assertNotEquals(0, data.size());
    }


    // @Test 
    // public void testUpdateData() {
    //     fail("not implemented");
    // }

    @Test 
    public void testInsertData() {
        ModelFront model = new ModelFront();
        model.setHp(1200d);
        model.setHc(1200d);
        model.setInsertedAt(null);
        boolean check = this.req.save(model);
        assertEquals(true, check);
    }


    @Test 
    public void testDeleteDatabyHpHc() {
        boolean check = this.req.delete(1200d, 1200d);
        assertEquals(true, check);
    }

    @Test 
    public void testdeleteById() {
        boolean check = this.req.delete(125420L);
        assertEquals(true, check);
    }
    
    @Test 
    public void testdeleteByDate() {
        boolean check = this.req.delete("2035-12-02T10:10:10");
        assertEquals(true, check);
    }

    @Test 
    public void testUpdate() {
        ModelFront model = new ModelFront();
        model.setId(123654789L);
        model.setHc(125478d);
        model.setHp(87987d);
        model.setInsertedAt("2035-12-02T10:10:10");
        boolean check = this.req.update(model);
        assertEquals(true, check);
    }

    @Test
    public void testInsertUpdateGet() {
        // insert
        ModelFront model = new ModelFront();
        model.setHp(1200d);
        model.setHc(1200d);
        model.setInsertedAt(null);

        // get last id
        Model m = this.req.getDataByLastId();
        
        // delete
        boolean check = this.req.delete(m.getId());
    }
}
