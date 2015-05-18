import java.util.*;
import processing.core.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.function.*;

public class MinerFull 
	extends Miner 
{
	public MinerFull(String name, int resource_limit, Point position, int rate, List<PImage> imgs, int animation_rate)
	{
		super(name, imgs, position, rate, resource_limit, resource_limit, animation_rate);
	}
	
	public LongConsumer create_miner_full_action(WorldModel world, Image_store i_store)
	{
		LongConsumer [] action = {null};
		action[0] = (long current_ticks) -> {
            this.remove_pending_action(action[0]);

          	Point entity_pt = this.get_position();
          	Blacksmith smith = (Blacksmith) world.find_nearest(entity_pt, Blacksmith.class);
          	SimpleEntry<List<Point>, Boolean> tiles_found = this.miner_to_smith(world, smith);
          	Miner new_entity = this;
          	if (tiles_found.getValue())
          	{
          		new_entity = this.try_transform_miner(world, 
             				this::try_transform_miner_full);
          	}
          	new_entity.schedule_action(world,
             				new_entity.create_miner_action(world, i_store),
             				current_ticks + this.get_rate());
		};
       	return action[0];
	}
	
	public LongConsumer create_miner_action(WorldModel world, Image_store image_store)
	{
		return this.create_miner_full_action(world, image_store);
	}

	public SimpleEntry<List<Point>, Boolean> miner_to_smith(WorldModel world, Blacksmith smith)
	{
		Point entity_pt = this.get_position();
		SimpleEntry<List<Point>, Boolean> tiles_boolean;
       	if (!(smith != null))
       	{
       		List<Point> not_smith = new ArrayList<Point>();
       		not_smith.add(entity_pt);
       		tiles_boolean = new SimpleEntry<List<Point>, Boolean>(not_smith, false);
          	return tiles_boolean;
        }
       	Point smith_pt = smith.get_position();
       	if (Actions.adjacent(entity_pt, smith_pt))
       	{
          	smith.set_resource_count(
            			smith.get_resource_count() + this.get_resource_count());
          	this.set_resource_count(0);
          	List<Point> empty_list = new ArrayList<Point>();
          	tiles_boolean = new SimpleEntry<List<Point>, Boolean>(empty_list, true);
          	return tiles_boolean;
        }
       	else
        {
        	Point new_pt = this.next_position(world, smith_pt);
        	tiles_boolean = new SimpleEntry<List<Point>, Boolean>(world.move_entity(this, new_pt), false);
          	return tiles_boolean;
        }
	}

	public Miner try_transform_miner_full(WorldModel world)
	{
		Miner new_entity = new MinerNotFull(this.get_name(), this.get_resource_limit(), 
				this.get_position(), this.get_rate(), 
				this.get_images(),
				this.get_animation_rate());
		return new_entity;
	}
	
}
