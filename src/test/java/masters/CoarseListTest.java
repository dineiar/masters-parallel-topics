package masters;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import masters.listset.CoarseList;

/**
 * Unit test for simple App.
 */
public class CoarseListTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void mainTest()
    {
        CoarseList<Integer> coarseList = new CoarseList<>(Integer.MIN_VALUE, Integer.MAX_VALUE);

        assertTrue( coarseList.add(10) );
        
        assertTrue( coarseList.add(12) );

        assertTrue( coarseList.contains(10) );

        assertFalse( coarseList.contains(11) );

        assertTrue( coarseList.add(11) );
        
        assertTrue( coarseList.remove(10) );

        assertFalse( coarseList.add(11) );
    }
}
