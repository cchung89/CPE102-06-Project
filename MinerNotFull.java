import java.util.*;
import processing.core.*;
import java.util.AbstractMap.SimpleEntry;
import static java.util.Arrays.asList;
import java.util.function.*;

public class MinerNotFull 
	extends Miner
{
	public MinerNotFull(String name, List<PImage> imgs, int resource_limit, Point position, int rate, int animation_rate)
	{
		super(name, imgs, position, rate, resource_limit, 0, animation_rate);
	}

	public String entity_string()
	{
		List<String> strings = new ArrayList<String>(asList(
				"miner", this.get_name(), String.valueOf(this.get_position().x),
		         String.valueOf(this.get_position().y), String.valueOf(this.get_resource_limit()),
		         String.valueOf(this.get_rate()), String.valueOf(get_animation_rate())));
		String result = String.join(" ", strings);
		return result;
	}
	
	public LongConsumer create_miner_not_full_action(WorldModel world, HashMap<String, List<PImage>> i_store)
	{
		LongConsumer[] action = {null};
		action[0] = (long current_ticks) -> {
			this.remove_pending_action(action[0]);

          	Point entity_pt = this.get_position();
          	Ore ore = (Ore) world.find_nearest(entity_pt, Ore.class);
          	SimpleEntry<List<Point>, Boolean> tiles_found = this.miner_to_ore(world, ore);
          	List<Point> tiles = tiles_found.getKey();
          	Miner new_entity = this;
          	if (tiles_found.getValue())
          	{
          		new_entity = this.try_transform_miner(world, 
             				try_transform_miner_not_full);
          	}
          	new_entity.schedule_action(world,
             				new_entity.create_miner_action(world, i_store),
             				current_ticks + this.get_rate());
		};
       	return action[0];
	}
	
	public Miner try_transform_miner_not_full(WorldModel world)
	{
		if (this.get_resource_count() < this.get_resource_limit())
		{
			return this;
		}
		else
		{
			Miner new_entity = new MinerFull(this.get_name(), this.get_images(),this.get_resource_limit(),
					this.get_resource_count(),
					this.get_position(), this.get_rate(), 
					this.get_animation_rate());
			return new_entity;
		}
	}
	
	protected LongConsumer create_miner_action(WorldModel world, HashMap<String, List<PImage>> image_store)
	{
		return this.create_miner_not_full_action(world, image_store);
	}
	
	public SimpleEntry<List<Point>, Boolean> miner_to_ore(WorldModel world, Ore ore)
	{
		Point entity_pt = this.get_position();
		SimpleEntry<List<Point>, Boolean> tiles_boolean;
       	if (!(ore != null))
       	{
       		List<Point> not_ore = new ArrayList<Point>();
       		not_ore.add(entity_pt);
       		tiles_boolean = new SimpleEntry<List<Point>, Boolean>(not_ore, false);
          	return tiles_boolean;
        }
       	Point ore_pt = ore.get_position();
       	if (Actions.adjacent(entity_pt, ore_pt))
       	{
          	this.set_resource_count(
            			1 + this.get_resource_count());
          	ore.remove_entity(world);
          	List<Point> adjacent_ore = new ArrayList<Point>();
          	adjacent_ore.add(ore_pt);
          	tiles_boolean = new SimpleEntry<List<Point>, Boolean>(adjacent_ore, true);
          	return tiles_boolean;
        }
       	else
        {
        	Point new_pt = this.next_position(world, ore_pt);
        	tiles_boolean = new SimpleEntry<List<Point>, Boolean>(world.move_entity(this, new_pt), false);
          	return tiles_boolean;
        }
	}
	
	public void schedule_miner(WorldModel world, int ticks, HashMap<String, List<PImage>> i_store)	
	{
		this.schedule_action(world, this.create_miner_action(world, i_store),
				ticks + this.get_rate());
		this.schedule_animation(world);
	}
}
