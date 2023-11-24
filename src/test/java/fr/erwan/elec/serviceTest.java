package fr.erwan.elec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Optional;

import fr.erwan.elec.rest.Services;
import fr.erwan.elec.config.Config;
import fr.erwan.elec.rest.Model;
import fr.erwan.elec.rest.ModelFront;


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
        assertEquals(true, check);
    }

    @Test 
    public void testdeleteById() {
        boolean check = this.serv.delete(156987465632L);
        assertEquals(true, check);
    }


    // @Test 
    // public void testdeleteById() {
    //     boolean check = this.req.delete(125420L);
    //     assertEquals(true, check);
    // }
       

    // @Test 
    // public void testUpdateData() {
    //     fail("not implemented");
    // }

}
