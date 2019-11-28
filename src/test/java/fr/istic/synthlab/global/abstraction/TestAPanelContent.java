package fr.istic.synthlab.global.abstraction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestAPanelContent {

	protected APanelContent abstractPanelContent;
	
	@Before
	public void setUp(){
		abstractPanelContent = new APanelContent();
	}
	
	@After
    public void tearDown() {
    }

	@Test
	public final void testInit() {
		assertEquals("Grid isn't null", null, abstractPanelContent.getGrille());
		abstractPanelContent.init();
		assertNotEquals("Grid is null", null, abstractPanelContent.getGrille());
		assertEquals("Grid is not empty", 0, abstractPanelContent.getGrille().size());
	}
}