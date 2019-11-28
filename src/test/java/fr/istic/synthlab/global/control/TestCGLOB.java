package fr.istic.synthlab.global.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class TestCGLOB {

	public static class Inner1{

		protected ICGLOB controleGlob;
		
		@Test
		public final void testGetInstance() {
			controleGlob = CGLOB.getInstance();
			assertNotEquals("Control not instanciate", null, controleGlob);
		}
	}
	
	public static class Inner2{
		
		protected ICGLOB controleGlob;
		
		@Before
		public void setUp(){
			controleGlob = CGLOB.getInstance();
		}
		
		@After
	    public void tearDown() {
	    }
		
	
		@Test
		public final void testGetInstance() {
			ICGLOB glob2 = CGLOB.getInstance();
			assertEquals("return different instance of CGLOB", true, glob2.equals(controleGlob));
		}
	
		@Test
		public final void testGetPresentation() {
			assertNotEquals("Presentation not instanciate", null, controleGlob.getPresentation());
		}
	
		@Test
		public final void testRemoveCable() {
			//fail("Not yet implemented"); // TODO
		}
		
		@Test
		public final void testEnregistrer() {
		}
	
		@Test
		public final void testInitPanelContent() {
		}
	
		@Test
		public final void testMyResize() {
		}
	}

}
