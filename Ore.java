import java.util.*;

import processing.core.*;
import static java.util.Arrays.asList;
import java.util.function.*;


public class Ore 
	extends Mineral
{
	public Ore (String name, List<PImage> imgs, Point position, int rate)
	{
		super(name, imgs, position, rate);
	}
	
	//method overloading
	public Ore(String name, List<PImage> imgs, Point position)
	{
		super(name, imgs, position, 5000);
	}
	
	public String entity_string()
	{
		List<String> strings = new ArrayList<String>(asList(
				"ore", this.get_name(), String.valueOf(this.get_position().x),
		         String.valueOf(this.get_position().y),
		         String.valueOf(this.get_rate())));
		String result = String.join(" ", strings);
		return result;
	}
	
	
	public void schedule_ore(WorldModel world, long ticks, HashMap<String, List<PImage>> i_store)
	{
		this.schedule_action(world, 
          					this.create_ore_transform_action(world, i_store),
          					ticks + this.get_rate());
	}
	
	public LongConsumer create_ore_transform_action(WorldModel world, HashMap<String, List<PImage>> i_store)
	{
		LongConsumer [] action = {null};
		action[0] = (long current_ticks) -> {
			this.remove_pending_action(action[0]);
          	OreBlob blob = Actions.create_blob(world, this.get_name() + " -- blob",
              							this.get_position(),
              							this.get_rate() / Actions.BLOB_RATE_SCALE,
              							current_ticks, i_store);

          	this.remove_entity(world);
          	world.add_entity(blob);
		};
       	return action[0];	
	}
}