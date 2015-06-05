import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.function.LongConsumer;

import processing.core.PImage;


public class Knight 
	extends Destroyer 
{
	private int rate;
	private List<Point> path;
	
	public Knight(String name, Point position, int rate, List<PImage> imgs, int animation_rate)
	{
		super(name, imgs, position, animation_rate);
		this.rate = rate;
	}

	public int get_rate()
	{
		 return rate;
	}
	
	public LongConsumer create_knight_action(WorldModel world, HashMap<String, List<PImage>> i_store)
	{
		LongConsumer [] action = { null };
		action[0] = (long current_ticks) -> {
			this.remove_pending_action(action[0]);
          	
          	Point entity_pt = this.get_position();
          	OreBlob blob = (OreBlob) world.find_nearest(entity_pt, OreBlob.class);
          	SimpleEntry<List<Point>, Boolean> tiles_found = this.knight_to_blob(world, blob);
          	List<Point> tiles = tiles_found.getKey();

          	long next_time = current_ticks + this.get_rate();
          	if (tiles_found.getValue())
            {
            	Quake quake = Actions.create_quake(world, tiles.get(0), current_ticks, i_store);
            	world.add_entity(quake);
             	next_time = current_ticks + this.get_rate() * 2;   	
            }
          	
          	this.schedule_action(world, 
             				this.create_knight_action(world, i_store),
             				next_time);
		};
       	return action[0];	
	}
	
	protected Point knight_next_position(WorldModel world, Point goal, Class dest_entity)
	{
		Point start = this.get_position();
		List<Point> path = create_path(world, start, goal, dest_entity);
		if(path != null)
		{
			if (path.size() > 1)
			{
				return path.get(path.size() - 2);
			}
			else
			{
				return start;
			}
		}
		return start;
	}
	
	private List<Point> create_path(WorldModel world, Point start, Point goal, Class dest_entity)
	{
		path = Actions.a_star(start, goal, world, dest_entity);
		return path;
	}
	
	public List<Point> get_path()
	{
		return this.path;
	}
	
	public SimpleEntry<List<Point>, Boolean> knight_to_blob(WorldModel world, OreBlob blob)
	{
		Point entity_pt = this.get_position();
		SimpleEntry<List<Point>, Boolean> tiles_boolean;
       	if (!(blob != null))
       	{
       		List<Point> not_blob = new ArrayList<Point>();
       		not_blob.add(entity_pt);
       		tiles_boolean = new SimpleEntry<List<Point>, Boolean>(not_blob, false);
          	return tiles_boolean;
        }
       	Point blob_pt = blob.get_position();
       	if (Actions.adjacent(entity_pt, blob_pt))
       	{
          	blob.remove_entity(world);
          	List<Point> adjacent_blob = new ArrayList<Point>();
          	adjacent_blob.add(blob_pt);
          	tiles_boolean = new SimpleEntry<List<Point>, Boolean>(adjacent_blob, true);
          	return tiles_boolean;
        }
       	else
        {
        	Point new_pt = this.knight_next_position(world, blob_pt, OreBlob.class);
        	tiles_boolean = new SimpleEntry<List<Point>, Boolean>(world.move_entity(this, new_pt), false);
          	return tiles_boolean;
        }
	}
	
	public void schedule_knight(WorldModel world, long ticks, HashMap<String, List<PImage>> i_store)
	{
		this.schedule_action(world, this.create_knight_action(world, i_store),
          ticks + this.get_rate());
       	this.schedule_animation(world);
	}
}
