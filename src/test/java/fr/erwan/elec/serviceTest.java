package fr.erwan.elec;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import fr.erwan.elec.rest.Services;
import fr.erwan.elec.rest.Model;
import fr.erwan.elec.rest.ModelFront;

/**
 * classe de tests de la classe rest.Services
 */
@SpringBootTest
public class serviceTest {

    @Autowired
    private Services serv; 
    
    @Test 
    public void testGetData() {
        List<Model> data = this.serv.getData();
        assertNotEquals(0, data.size());
    }

    @Test 
    public void testGetDataById() {
        Model data = this.serv.getDataById(12589785423L);
        assertEquals(data.getId(), 0L);
    }

    @Test 
    public void testGetDataByDate() {
        Model data = this.serv.getDataByDate("2078-11-23T10:10:10");
        assertEquals(data.getId(), 0L);
    }

    @Test 
    public void testGetDataByDateTestPattern() {
        Model data = this.serv.getDataByDate("2078-11-T10:10:10");
        assertEquals(data.getId(), 0L);
    }

    @Test 
    public void testSave() {
        ModelFront model = new ModelFront();
        model.setHp(1200d);
        model.setHc(1200d);
        model.setInsertedAt(null);
        boolean check = this.serv.save(model);
        assertEquals(true, check);

        this.serv.delete(1200d, 1200d);
    }

    @Test 
    public void testdeleteByDate() {
        boolean check = this.serv.delete("2035-12-02T10:10:10");
        assertEquals(false, check);
    }

    @Test 
    public void testdeleteById() {
        boolean check = this.serv.delete(156987465632L);
        assertEquals(true, check);
    }

    @Test 
    public void testUpdate() {
        ModelFront model = new ModelFront();
        model.setHp(1200d);
        model.setHc(1200d);
        model.setInsertedAt("blabla");
        boolean check = this.serv.update(model);
        assertEquals(false, check);
    }

}
