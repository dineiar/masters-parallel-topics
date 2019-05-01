package masters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import masters.experiment.HoHListExperiment;

/**
 * Unit test for simple App.
 */
public class HoHListExperimentTest
{

    public static void main(String[] args) {
        new HoHListExperimentTest().mainTest();
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void mainTest()
    {
        HoHListExperiment experiment = new HoHListExperiment();

        assertTrue( experiment.getList().add(10) );
        
        assertEquals( 1, experiment.getList().count() );

        assertTrue( experiment.getList().add(12) );

        assertTrue( experiment.getList().contains(10) );

        assertFalse( experiment.getList().contains(11) );

        assertTrue( experiment.getList().add(11) );
        
        assertEquals( 3, experiment.getList().count() );

        assertTrue( experiment.getList().remove(10) );

        assertFalse( experiment.getList().add(11) );

        assertEquals( 2, experiment.getList().count() );

        System.out.println(experiment.getList().printToString());
    }
}
