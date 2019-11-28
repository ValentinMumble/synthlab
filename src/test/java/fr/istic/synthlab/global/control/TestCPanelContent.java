package fr.istic.synthlab.global.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class TestCPanelContent {
	
	public static class Inner1{
		protected CPanelContent controlPanelContent;

		@Before
		public void setUp(){
			controlPanelContent = new CPanelContent();
		}
		
		@After
	    public void tearDown() {
	    }
		
		@Test
		public final void testGetPresentation() {
			assertNotEquals("Presentation of CPanelContent is NULL", null, controlPanelContent.getPresentation());
		}
		
		@Test
		public final void testGetGrille1() {
			assertEquals("Grid empty when we construct the panel", null, controlPanelContent.getGrille());
		}
		
		@Test
		public final void testInitPanel1() {
			controlPanelContent.initPanel();
			assertNotEquals("Grid not initializes", null, controlPanelContent.getGrille());
			assertEquals("Grid has too many lines", 2, controlPanelContent.getGrille().size());
		}
	}
	
	public static class Inner2{
		protected CPanelContent controlPanelContent;
		
		@Before
		public void setUp(){
			controlPanelContent = new CPanelContent();
			controlPanelContent.initPanel();
		}
		
		@After
	    public void tearDown() {
	    }
		
	
		@Test
		public final void testGetPresentation() {
			assertNotEquals("Presentation of CPanelContent is NULL", null, controlPanelContent.getPresentation());
		}
		
		@Test
		public final void testGetGrille2() {
			assertNotEquals("Grid not initializes", null, controlPanelContent.getGrille());
			assertEquals("Grid has too many lines", 2, controlPanelContent.getGrille().size());
		}
		
		@Test
		public final void testAjouterLigne() {
			controlPanelContent.initPanel();
			controlPanelContent.addLine();
			assertEquals("Grid has too many lines", 3, controlPanelContent.getGrille().size());
			controlPanelContent.addLine();
			assertEquals("Grid has too many lines", 4, controlPanelContent.getGrille().size());
		}
		
		@Test
		public final void testInitPanel2() {
			assertNotEquals("Grid not initializes", null, controlPanelContent.getGrille());
			assertEquals("Grid has too many lines", 2, controlPanelContent.getGrille().size());
			controlPanelContent.addLine();
			assertEquals("Grid has too many lines", 3, controlPanelContent.getGrille().size());
			controlPanelContent.initPanel();
			assertNotEquals("Grid not initializes", null, controlPanelContent.getGrille());
			assertEquals("Grid has too many lines", 2, controlPanelContent.getGrille().size());
		}
		
		@Test
		public final void testMyResize() {
			controlPanelContent.initPanel();
			ArrayList<CSlot[]> etatGrille = new ArrayList<CSlot[]>(controlPanelContent.getGrille());
			controlPanelContent.myResize();
			assertEquals("Grid has been modified", true, etatGrille.equals(controlPanelContent.getGrille()));
		}
	}
}