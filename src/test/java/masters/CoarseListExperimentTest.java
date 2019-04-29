package masters;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import masters.experiment.CoarseListExperiment;

/**
 * Unit test for simple App.
 */
public class CoarseListExperimentTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void mainTest()
    {
        CoarseListExperiment experiment = new CoarseListExperiment();

        assertTrue( experiment.getList().add(10) );
        
        assertTrue( experiment.getList().add(12) );

        assertTrue( experiment.getList().contains(10) );

        assertFalse( experiment.getList().contains(11) );

        assertTrue( experiment.getList().add(11) );
        
        assertTrue( experiment.getList().remove(10) );

        assertFalse( experiment.getList().add(11) );
    }
}
