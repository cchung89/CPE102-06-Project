import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import java.util.*;
public class TestCases
{   
	//Testing entity objects in a self-defined world
	Point pt1 = new Point(2,0);
	Location blob = new OreBlob("blob", pt1, 500); //OreBlob object
	
	Point pt2 = new Point(0,2);
	Location obstacle = new Obstacle("obstacle",pt2); //Obstacle object
	
	Point pt3 = new Point(1,2);
	Location smith = new Blacksmith("smith", pt3, 15, 20 ,30); //Blacksmith object
	
	Point pt4 = new Point(2,3);
	Location miner = new MinerNotFull("miner", 5 , pt4 , 500); //MinerNotFull Object
	
	Point pt5 = new Point(3,3);
	Location obstacle2 = new Obstacle("obstacle2",pt5); //Another Obstacle object
	
	WorldModel world = new WorldModel(4, 4); // 4 by 4 grid world
	
	@Test
	public void testQuake()
	{   
		Point pt = new Point(1,2);
		Quake brian = new Quake("Brian",pt);
		Point setPt = new Point(7,9);
		
		assertTrue(brian.get_name().equals("Brian"));
		assertEquals(brian.get_position().x , 1);
		assertEquals(brian.get_position().y , 2);
		
		//object position changed
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
		
		//object position changed
		edward.set_position(setPt);
		assertEquals(edward.get_position().x , 7);
		assertEquals(edward.get_position().y , 2);
	}
	
	@Test
	public void testVeinDefault()
	{   
		Point pt = new Point(1,2);
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
		Point setPt = new Point(4,3);
		MinerNotFull  bob = new MinerNotFull("bob", 5 , pt , 500);
		assertTrue(bob.get_name().equals("bob"));
		assertEquals(bob.get_resource_limit() , 5);
		assertEquals(bob.get_position().x , 3);
		assertEquals(bob.get_position().y , 4);
		assertEquals(bob.get_rate(), 500);
		assertEquals(bob.get_resource_count() , 0 );
		
		//resource count changed
		bob.set_resource_count(5);
		assertEquals(bob.get_resource_count(), 5);
		
		//object position changed
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
		
		//resource count changed
		brian.set_resource_count(2);
		assertEquals(brian.get_resource_count() ,  2);
		
		//object position changed
		brian.set_position(setPt);
		assertEquals(brian.get_position().x , 1);
		assertEquals(brian.get_position().y , 0);
	}
	
	@Test
	public void testBackground()
	{ 
		Background img = new Background("miner1");
		assertTrue(img.get_name().equals("miner1"));
	}
	
	//Within the bounds
	@Test
	public void test_is_occupied_1()
	{
		world.add_entity(blob);
		Point pt1 = blob.get_position();
		Point pt2 = new Point(1,3);
		
		//occupied by a Oreblob
		boolean result1 = world.is_occupied(pt1);
		assertTrue(result1);
		
		//Not occupied by any entity
		boolean result2 = world.is_occupied(pt2);
		assertFalse(result2);
	}
	
	//Outside the bounds
	@Test
	public void test_is_occupied_2()
	{
		Point pt = new Point (2, 5);
		boolean result = world.is_occupied(pt);
		assertFalse(result);
	}
	
	//Within the bounds
	@Test
	public void test_add_entity_1()
	{	
		world.add_entity(blob);
		world.add_entity(obstacle);
		world.add_entity(smith);
		world.add_entity(miner);
		world.add_entity(obstacle2);
		List<Location> entities = world.get_entities();
		Point pt = new Point(0, 0);
		
		//Before adding an entity
		boolean result1 = world.is_occupied(pt);
		assertEquals(entities.size(), 5);
		assertFalse(result1);
		
		//After adding an entity
		Ore ore = new Ore("ore" , pt);
		world.add_entity(ore);
		boolean result2 = world.is_occupied(pt);
		assertEquals(entities.size(), 6);
		assertTrue(entities.contains(ore));
		assertTrue(result2);
	}
	
	//Outside the bounds
	@Test
	public void test_add_entity_2()
	{
		Point pt = new Point(-1, 0);
		MinerFull miner2 = new MinerFull("miner2", 6, pt, 500);
		List<Location> entities = world.get_entities();
		
		world.add_entity(miner2);
		boolean result = world.is_occupied(pt);
		assertFalse(result);
		assertFalse(entities.contains(miner2));
	}
	
	//Within the bounds
	@Test
	public void test_move_entity_1()
	{
		world.add_entity(miner);
		Point miner_pt = miner.get_position();
		Point moved_pt = new Point(1, 3);
		
		//Before moving the entity
		boolean before_result_1 = world.is_occupied(miner_pt);
		boolean before_result_2 = world.is_occupied(moved_pt);
		assertTrue(before_result_1);
		assertFalse(before_result_2);
		
		//After moving the entity
		List<Point> tiles = world.move_entity(miner, moved_pt);
		boolean after_result_1 = world.is_occupied(miner_pt);
		boolean after_result_2 = world.is_occupied(moved_pt);
		assertFalse(after_result_1);
		assertTrue(after_result_2);
		assertEquals(tiles.get(0), miner_pt);
		assertEquals(tiles.get(1), moved_pt);
	}
	
	//Outside the bounds
	@Test
	public void test_move_entity_2()
	{
		Point miner_pt = miner.get_position();
		Point moved_pt = new Point(4, 4);
		
		List<Point> tiles = world.move_entity(miner, moved_pt);
		boolean result = world.is_occupied(moved_pt);
		assertFalse(result);
		assertFalse(tiles.contains(miner_pt));
		assertFalse(tiles.contains(moved_pt));
	}
	
	@Test
	public void test_remove_entity_1()
	{
		world.add_entity(blob);
		world.add_entity(obstacle);
		world.add_entity(smith);
		world.add_entity(miner);
		world.add_entity(obstacle2);
		List<Location> entities = world.get_entities();
		Point pt = obstacle.get_position();
		
		//Before removing the entity obstacle;
		boolean result1 = world.is_occupied(pt);
		assertEquals(entities.size(), 5);
		assertTrue(entities.contains(obstacle));
		assertTrue(result1);
		
		//After removing the entity obstacle
		world.remove_entity(obstacle);
		boolean result2 = world.is_occupied(pt);
		assertEquals(entities.size(), 4);
		assertFalse(entities.contains(obstacle));
		assertFalse(result2);
	}
	
	@Test
	public void test_remove_entity_2()
	{
		world.add_entity(blob);
		world.add_entity(obstacle);
		world.add_entity(smith);
		world.add_entity(miner);
		world.add_entity(obstacle2);
		List<Location> entities = world.get_entities();
		Point pt = new Point(3, 4);
		Quake quake = new Quake("quake", pt);
		
		/*check if "removing" an object that doesn't exist in the world affect anything,
		  it shouldn't remove anything if it works properly*/
		world.remove_entity(quake);
		boolean result = world.is_occupied(pt); 
		assertFalse(result);
		assertEquals(entities.size(), 5); //make sure it doesn't remove any object when it is not suppose to
		//remove_entity implicitly already check whether an object existed in a position
	}
	
	//Within the bounds
	@Test
	public void test_get_tile_occupant_1()
	{
		world.add_entity(smith);
		Point pt = smith.get_position();
		Location occupant = world.get_tile_occupant(pt);
		assertEquals(occupant, smith);
	}
	
	@Test
	public void test_tile_occupant_2()
	{
		Point pt = new Point(3, 4);
		Location occupant = world.get_tile_occupant(pt);
		assertEquals(occupant, null);
	}
	
	@Test
	public void test_get_entities()
	{
		world.add_entity(blob);
		world.add_entity(obstacle);
		List<Location> entities = world.get_entities();
		assertEquals(entities.size(), 2);
		assertEquals(entities.get(0), blob);
		assertEquals(entities.get(1), obstacle);
	}
	
	//When there are entities in the world
	@Test
	public void test_find_nearest_1()
	{
		world.add_entity(blob);
		world.add_entity(obstacle);
		world.add_entity(smith);
		world.add_entity(miner);
		world.add_entity(obstacle2);
		Point pt = new Point(2, 2);
		Location closest_entity = world.find_nearest(pt, Obstacle.class);
		assertEquals(closest_entity, obstacle2);
	}
	
	//When there are no entities in th world
	@Test
	public void test_find_nearest_2()
	{
		Point pt = new Point(3, 1);
		Location closest_entity = world.find_nearest(pt,  Obstacle.class);
		assertEquals(closest_entity, null);
	}
	
}