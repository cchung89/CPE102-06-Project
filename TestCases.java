import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import java.util.*;
public class TestCases
{   
	//Testing entity objects
	
	Point pt1 = new Point(2,0);
	Location blob = new OreBlob("blob", pt1, 500); //OreBlob object
	
	Point pt2 = new Point(0,2);
	Location obstacle = new Obstacle("obstacle",pt2); //obstacle object
	
	Point pt3 = new Point(1,2);
	Location smith = new Blacksmith("smith", pt3, 15, 20 ,30); //Blacksmith object
	
	Point pt4 = new Point(2,3);
	Location miner = new MinerNotFull("miner", 5 , pt4 , 500); //MinerNotFull Object
	
	Background[][] background = new Background[4][4];
	List<Location> entities = new ArrayList<Location>(Arrays.asList(blob, obstacle, smith, miner));
	Location[][] occupancy = new Location[][] {
		{null, null, blob, null},
		{null, null, null, null},
		{obstacle, smith, null, null},
		{null, null, miner, null}
	};
	WorldModel world = new WorldModel(4, 4, occupancy, background, entities);
	
	
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
		Point setPt = new Point(1,2);
		Ore chin = new Ore("Chin", pt , 10);
		assertTrue(chin.get_name().equals("Chin"));
		assertEquals(chin.get_position().x , 5);
		assertEquals(chin.get_position().y , 7);
		assertEquals(chin.get_rate(), 10);
		chin.set_position(setPt);
		assertEquals(chin.get_position().x , 1);
		assertEquals(chin.get_position().y , 2);
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
		Point setPt = new Point(1,2);
		OreBlob bob = new OreBlob("blob", pt, 500);
		assertTrue(bob.get_name().equals("blob"));
		assertEquals(bob.get_position().x , 4);
		assertEquals(bob.get_position().y , 5);
		assertEquals(bob.get_rate(), 500);
		
		bob.set_position(setPt);
		assertEquals(bob.get_position().x , 1);
		assertEquals(bob.get_position().y , 2);
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
		Point setPt = new Point(1,2);
		Obstacle mayweather = new Obstacle("mayweather",pt);
		assertTrue(mayweather.get_name().equals("mayweather"));
		assertEquals(mayweather.get_position().x , 5);
		assertEquals(mayweather.get_position().y,5);
		mayweather.set_position(setPt);
		assertEquals(mayweather.get_position().x , 1);
		assertEquals(mayweather.get_position().y , 2);
	}
	@Test
	public void testBlackSmith()
	{
		Point pt = new Point(8,8);
		Point setPt = new Point(4,5);
		Blacksmith chin = new Blacksmith("chin", pt, 15,20 ,30);
		assertTrue(chin.get_name().equals("chin"));
		assertEquals(chin.get_position().x , 8);
		assertEquals(chin.get_position().y , 8);
		assertEquals(chin.get_resource_limit() ,  15);
		assertEquals(chin.get_rate() , 20);
		assertEquals(chin.get_resource_distance(),30 );
		
		chin.set_position(setPt);
		assertEquals(chin.get_position().x ,4);
		assertEquals(chin.get_position().y, 5);

	}
	
	@Test
	public void testMinerNotFull()
	{
		Point pt = new Point(3,4);
		Point setPt = new Point(4,3);
		MinerNotFull  bob = new MinerNotFull("bob", 5 , pt , 500);
		assertTrue(bob.get_name().equals("bob"));
		assertEquals(bob.get_resource_limit() , 5);
		assertEquals(bob.get_position().x , 3);
		assertEquals(bob.get_position().y , 4);
		assertEquals(bob.get_rate(), 500);
		assertEquals(bob.get_resource_count() , 0 );
		bob.set_resource_count(5);
		assertEquals(bob.get_resource_count(), 5);
		bob.set_position(setPt);
		assertEquals(bob.get_position().x , 4);
		assertEquals(bob.get_position().y, 3);
	}
	
	@Test
	public void testMinerFull()
	{
		Point pt = new Point(8,9);
		Point setPt = new Point (1,0);
		MinerFull brian = new MinerFull("brian" , 5 , pt , 144);
		assertTrue(brian.get_name().equals("brian"));
		assertEquals(brian.get_resource_limit() , 5);
		assertEquals(brian.get_position().x , 8);
		assertEquals(brian.get_position().y , 9);
		assertEquals(brian.get_rate() , 144);
		assertEquals(brian.get_resource_count(), 5);
		brian.set_resource_count(2);
		assertEquals(brian.get_resource_count() ,  2);
		brian.set_position(setPt);
		assertEquals(brian.get_position().x , 1);
		assertEquals(brian.get_position().y , 0);
	}
	
	@Test
	public void testBackground()
	{ Background img = new Background("miner1");
	  assertTrue(img.get_name().equals("miner1"));
	}
	
	@Test
	public void test_is_occupied()
	{
		Point pt1 = blob.get_position();
		Point pt2 = new Point(1,3);
		boolean result1 = world.is_occupied(pt1);
		boolean result2 = world.is_occupied(pt2);
		assertTrue(result1);
		assertFalse(result2);
	}
	
	@Test
	public void test_add_entity()
	{	
		Point pt = new Point (0, 0);
		
		//Before adding an entity
		boolean result1 = world.is_occupied(pt);
		assertEquals(entities.size(), 4);
		assertFalse(result1);
		
		//After adding an entity
		Ore ore = new Ore("ore" , pt);
		world.add_entity(ore);
		boolean result2 = world.is_occupied(pt);
		assertEquals(entities.size(), 5);
		assertEquals(entities.get(4), ore);
		assertTrue(result2);
	}
	
	@Test
	public void test_move_entity()
	{
		Point pt1 = miner.get_position();
		Point pt2 = new Point(1, 3);
		
		//Before moving the entity
		boolean result1 = world.is_occupied(pt2);
		assertFalse(result1);
		
		//After moving the entity
		List<Point> tiles = world.move_entity(miner, pt2);
		boolean result2 = world.is_occupied(pt2);
		assertTrue(result2);
		assertEquals(tiles.get(0), pt1);
		assertEquals(tiles.get(1), pt2);
	}
	
	@Test
	public void test_remove_entity()
	{
		Point pt = obstacle.get_position();
		
		//Before removing the entity;
		boolean result1 = world.is_occupied(pt);
		assertEquals(entities.size(), 4);
		assertTrue(result1);
		
		//After removing the entity
		world.remove_entity(obstacle);
		boolean result2 = world.is_occupied(pt);
		assertEquals(entities.size(), 3);
		assertFalse(result2);
	}
	
	@Test
	public void test_get_tile_occupant()
	{
		Point pt = smith.get_position();
		Location occupant = world.get_tile_occupant(pt);
		assertEquals(occupant, smith);
	}
	
	@Test
	public void test_get_entities()
	{
		List<Location> worldObj = world.get_entities();
		assertEquals(entities, worldObj);
	}
	
}