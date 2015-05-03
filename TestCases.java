import org.junit.Test;
 import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
public class TestCases
{   
	
	@Test
	public void testQuake()
	{
		Point pt = new Point(1,2);
		Quake brian = new Quake("Brian",pt);
		assertTrue(brian.get_name().equals("Brian"));
		assertEquals(brian.get_position().x ,1);
		assertEquals(brian.get_position().y , 2);
	}
	@Test
	public void testOre()
	{
		Point pt = new Point(5,7);
		Ore chin = new Ore("Chin", pt , 10);
		assertTrue(chin.get_name().equals("Chin"));
		assertEquals(chin.get_position().x , 5);
		assertEquals(chin.get_position().y , 7);
		assertEquals(chin.get_rate(), 10);
	}
	
	@Test
	public void testOreDefault()
	{
		Point pt = new Point(6,7);
		Ore chin = new Ore("chin" , pt );
		assertTrue(chin.get_rate() == 5000);
	}
	
	@Test
	public void testVein()
	{
		Point pt = new Point(7,8);
		Vein edward = new Vein("eddy",25, pt , 500);
		assertTrue(edward.get_name().equals("eddy"));
		assertEquals(edward.get_rate() , 25);
		assertEquals(edward.get_position().x , 7);
		assertEquals(edward.get_position().y,8);
		assertEquals(edward.get_resource_distance(),500);
		
	}
	
	@Test
	public void testVeinDefault()
	{   Point pt = new Point(1,2);
		Vein edward = new Vein("brian",25, pt);
		assertEquals(edward.get_resource_distance(),1);
		
		
	}
	
	@Test
	public void testObstacle()
	{
		Point pt = new Point(5,5);
		Obstacle mayweather = new Obstacle("mayweather",pt);
		assertTrue(mayweather.get_name().equals("mayweather"));
		assertEquals(mayweather.get_position().x , 5);
		assertEquals(mayweather.get_position().y,5);
	}
}