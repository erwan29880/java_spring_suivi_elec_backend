package fr.erwan.elec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import fr.erwan.elec.utils.DateManip;

@SpringBootTest
public class utilsTest {
    
    @Test 
    public void testToSqlDate() {
        java.sql.Date dat = DateManip.toSqlDate("2012-11-01T10:10:10");
        java.sql.Date dat2 = DateManip.toSqlDate("2012-11-01T10:10:11");
        assertNotEquals(dat, dat2);
    }

    @Test
    public void testToSqlDateFromToday() {
        java.sql.Date dat = DateManip.toSqlDate("2012-11-01T10:10:10");
        assertNotEquals(dat, DateManip.toSqlDateFromToday());
    }

    @Test
    public void testGetMinMaxDate() {
        java.sql.Date[] dates = DateManip.getMinMaxDate("2012-11-01T10:10:10");
        assertNotEquals(dates[0], DateManip.toSqlDate("2000-01-01T01:01:01"));
    }
}
