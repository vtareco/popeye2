package net.dms.fsync.synchronizer.fenix.entities;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FenixAccTest {

    private FenixAccTest underTest;

    @Test
    public void calculateTotalEsfuerzo(){

        assertEquals(15, FenixAcc.calculateTotalEsfuerzo("5-10"));
        assertEquals(25, FenixAcc.calculateTotalEsfuerzo("15-10"));
        assertEquals(10, FenixAcc.calculateTotalEsfuerzo("1-9"));
    }
}
