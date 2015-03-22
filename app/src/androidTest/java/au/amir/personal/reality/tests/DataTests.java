package au.amir.personal.reality.tests;

import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;

import java.util.List;

import au.amir.personal.reality.model.FactsSheet;
import au.amir.personal.reality.service.MyService;


public class DataTests extends TestCase {

    FactsSheet factsSheet;
    boolean    dataFetched;
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        dataFetched = MyService.getInstance().prepareData("https://dl.dropboxusercontent.com/u/746330/facts.json");
        if (!dataFetched) return;

        factsSheet = MyService.getInstance().getFactsSheet();
    }

    @SmallTest
    public void testIsDataFetchedSuccessfully()
    {
        assertTrue(dataFetched); // if preparedata in MyService returns true then test passed
    }

    @SmallTest
    public void testRealityTitle()
    {
        assertEquals("About Canada",factsSheet.getTitle()); // if we found title as "About Canada" then this test passed
    }

    @SmallTest
    public void testRealityContents()
    {
        List<FactsSheet.Row> rows = factsSheet.getRows();

        assertNotNull(rows.get(0));  // there must be rows in data sets
    }

    @SmallTest
    public void testRealityContentsLength()
    {
        List<FactsSheet.Row> rows = factsSheet.getRows();

        assertEquals(14,rows.size());  // there must be 14 rows returned
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
