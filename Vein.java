import java.util.*;
import processing.core.*;
import static java.util.Arrays.asList;
import java.util.function.*;

public class Vein
	extends Mineral
{
	private int resource_distance;

	public Vein(String name, List<PImage> imgs, int rate, Point position, int resource_distance)
	{
		super(name, imgs, position, rate);
		this.resource_distance = resource_distance;	
	}
	
	//method overloading 
	public Vein(String name, List<PImage> imgs, int rate, Point position)
	{
		super(name, imgs, position, rate);
		this.resource_distance = 1 ;
	}

	public int get_resource_distance()
	{
		return resource_distance;
	}

	public String entity_string()
	{
		List<String> strings = new ArrayList<String>(asList(
				"vein", this.get_name(), String.valueOf(this.get_position().x),
		         String.valueOf(this.get_position().y),
		         String.valueOf(this.get_rate()), String.valueOf(get_resource_distance())));
		String result = String.join(" ", strings);
		return result;
	}
	
	public void schedule_vein(WorldModel world, long ticks, HashMap<String, List<PImage>> i_store)
	{
		this.schedule_action(world, this.create_vein_action(world, i_store),
          					ticks + this.get_rate());
	}
	
	public LongConsumer create_vein_action(WorldModel world, HashMap<String, List<PImage>> i_store)
	{
		LongConsumer [] action = {null};
		action[0] = (long current_ticks) -> {
			this.remove_pending_action(action[0]);

          	Point open_pt = Actions.find_open_around(world, this.get_position(),
            				this.get_resource_distance());
            if (open_pt != null)
            {
            	Ore ore = Actions.create_ore(world,
                "ore - " + this.get_name() + " - " + String.valueOf(current_ticks),
                open_pt, current_ticks, i_store);
            	world.add_entity(ore);
        	}
        	this.schedule_action(world,
             				this.create_vein_action(world, i_store),
             				current_ticks + this.get_rate());
		};
       	return action[0];	
	}
}