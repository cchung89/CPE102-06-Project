import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.LongConsumer;

import processing.core.PImage;


public class Tent
	extends Mineral 
{
	public static final int SUPER_MINER_RATE = 500;
	public static final int KNIGHT_RATE = 8000;
	
	private int resource_distance;
	
	public Tent(String name, int rate, Point position, List<PImage> imgs, int resource_distance)
	{
		super(name, imgs, position, rate);
		this.resource_distance = resource_distance;
	}
	
	// method overloading
	public Tent(String name, int rate, Point position, List<PImage> imgs)
	{
		super(name, imgs, position, rate);
		this.resource_distance = 2;
	}

	public int get_resource_distance()
	{
		return resource_distance;
	}

	public String entity_string()
	{
		List<String> strings = new ArrayList<String>(asList(
				"tent", this.get_name(), String.valueOf(this.get_position().x),
		         String.valueOf(this.get_position().y),
		         String.valueOf(this.get_rate()), String.valueOf(get_resource_distance())));
		String result = String.join(" ", strings);
		return result;
	}
	
	public void schedule_tent(WorldModel world, long ticks, HashMap<String, List<PImage>> i_store)
	{
		this.schedule_action(world, this.create_tent_action(world, i_store),
          					ticks + this.get_rate());
	}
	
	public LongConsumer create_tent_action(WorldModel world, HashMap<String, List<PImage>> i_store)
	{
		LongConsumer [] action = {null};
		action[0] = (long current_ticks) -> {
			this.remove_pending_action(action[0]);

          	Point open_pt = Actions.find_open_around(world, this.get_position(),
            				this.get_resource_distance());
            if (open_pt != null)
            {
            	if (Math.random() < 0.75)
            	{
            		Purifier knight = Actions.create_purifier(world,
            				"knight - " + this.get_name() + " - " + String.valueOf(current_ticks),
            				open_pt, KNIGHT_RATE, current_ticks, i_store);
            		world.add_entity(knight);
            	}
            	else
            	{
            		Miner miner = Actions.create_miner(world,
            				"miner - " + this.get_name() + " - " + String.valueOf(current_ticks),
            				open_pt, SUPER_MINER_RATE, current_ticks, i_store);
            		world.add_entity(miner);
            	}
        	}
        	this.schedule_action(world,
             				this.create_tent_action(world, i_store),
             				current_ticks + this.get_rate());
		};
       	return action[0];	
	}
}

