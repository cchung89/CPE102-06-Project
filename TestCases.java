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
		Point setPt = new Point(7,9);
		
		assertTrue(brian.get_name().equals("Brian"));
		assertEquals(brian.get_position().x ,1);
		assertEquals(brian.get_position().y , 2);
		brian.set_position(setPt);
		assertEquals(brian.get_position().x, 7);
		assertEquals(brian.get_position().y, 9);
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
		assertEquals(chin.get_rate() ,5000);
	}
	@Test
	public void testOreBlob()
	{
		Point pt = new Point(4,5);
		OreBlob bob = new OreBlob("blob", pt, 500);
		assertTrue(bob.get_name().equals("blob"));
		assertEquals(bob.get_position().x , 4);
		assertEquals(bob.get_position().y , 5);
		assertEquals(bob.get_rate(), 500);
	}
	@Test
	public void testVein()
	{
		Point pt = new Point(7,8);
		Point setPt = new Point(7,2);
		Vein edward = new Vein("eddy",25, pt , 500);
		assertTrue(edward.get_name().equals("eddy"));
		assertEquals(edward.get_rate() , 25);
		assertEquals(edward.get_position().x , 7);
		assertEquals(edward.get_position().y,8);
		assertEquals(edward.get_resource_distance(),500);
		edward.set_position(setPt);
		assertEquals(edward.get_position().x , 7);
		assertEquals(edward.get_position().y , 2);
		
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
	@Test
	public void testBlackSmith()
	{
		Point pt = new Point(8,8);
		Blacksmith chin = new Blacksmith("chin", pt, 15,20 ,30);
		assertTrue(chin.get_name().equals("chin"));
		assertEquals(chin.get_position().x , 8);
		assertEquals(chin.get_position().y , 8);
		assertEquals(chin.get_resource_limit() ,  15);
		assertEquals(chin.get_rate() , 20);
		assertEquals(chin.get_resource_distance(),30 );
	}
	
	@Test
	public void testMinerNotFull()
	{
		Point pt = new Point(3,4);
		MinerNotFull  bob = new MinerNotFull("bob", 5 , pt , 500);
		assertTrue(bob.get_name().equals("bob"));
		assertEquals(bob.get_resource_limit() , 5);
		assertEquals(bob.get_position().x , 3);
		assertEquals(bob.get_position().y , 4);
		assertEquals(bob.get_rate(), 500);
		assertEquals(bob.get_resource_count() , 0 );
		bob.set_resource_count(5);
		assertEquals(bob.get_resource_count(), 5);
	}
	
	@Test
	public void testMiner()
	{
		Point pt = new Point(8,9);
		MinerFull brian = new MinerFull("brian" , 5 , pt , 144);
		assertTrue(brian.get_name().equals("brian"));
		assertEquals(brian.get_resource_limit() , 5);
		assertEquals(brian.get_position().x , 8);
		assertEquals(brian.get_position().y , 9);
		assertEquals(brian.get_rate() , 144);
		assertEquals(brian.get_resource_count(), 5);
		brian.set_resource_count(2);
		assertEquals(brian.get_resource_count() ,  2);
	}
	
}