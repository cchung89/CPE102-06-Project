import java.util.*;
import processing.core.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.function.*;


public class OreBlob 
	extends Destroyer
{
	private int rate;
	
	public OreBlob(String name, Point position, int rate, List<PImage> imgs, int animation_rate)
		{
			super(name, imgs, position, animation_rate);
			this.rate = rate;
		}

	public int get_rate()
		{
		  return rate;
		}
	
	public LongConsumer create_ore_blob_action(WorldModel world, Image_store i_store)
	{
		LongConsumer [] action = { null };
		action[0] = (long current_ticks) -> {
			this.remove_pending_action(action[0]);
          	
          	Point entity_pt = this.get_position();
          	Vein vein = (Vein) world.find_nearest(entity_pt, Vein.class);
          	SimpleEntry<List<Point>, Boolean> tiles_found = this.blob_to_vein(world, vein);
          	List<Point> tiles = tiles_found.getKey();

          	long next_time = current_ticks + this.get_rate();
          	if (tiles_found.getValue())
            {
            	Quake quake = Actions.create_quake(world, tiles.get(0), current_ticks, i_store);
            	world.add_entity(quake);
             	next_time = current_ticks + this.get_rate() * 2;
            }

          	this.schedule_action(world, 
             				this.create_ore_blob_action(world, i_store),
             				next_time);
		};
       	return action[0];	
	}
	
	public Point blob_next_position(WorldModel world, Point dest_pt)
	{
		int horiz = Actions.sign(dest_pt.x - this.get_position().x);
       	Point new_pt = new Point(this.get_position().x + horiz, this.get_position().y);

       	if (horiz == 0 || (world.is_occupied(new_pt) &&
          	!(world.get_tile_occupant(new_pt) instanceof Ore)))
        {
        	int vert = Actions.sign(dest_pt.y - this.get_position().y);
          	new_pt = new Point(this.get_position().x, this.get_position().y + vert);

          	if (vert == 0 || (world.is_occupied(new_pt) &&
             	!(world.get_tile_occupant(new_pt) instanceof Ore)))
            {
            	new_pt = new Point(this.get_position().x, this.get_position().y);
            }
        }

       	return new_pt;
	}
	
	public SimpleEntry<List<Point>, Boolean> blob_to_vein(WorldModel world, Vein vein)
	{
		Point entity_pt = this.get_position();
		SimpleEntry<List<Point>, Boolean> tiles_boolean;
       	if (!(vein != null))
       	{
       		List<Point> not_vein = new ArrayList<Point>();
       		not_vein.add(entity_pt);
       		tiles_boolean = new SimpleEntry<List<Point>, Boolean>(not_vein, false);
          	return tiles_boolean;
        }
       	Point vein_pt = vein.get_position();
       	if (Actions.adjacent(entity_pt, vein_pt))
       	{
          	vein.remove_entity(world);
          	List<Point> adjacent_vein = new ArrayList<Point>();
          	adjacent_vein.add(vein_pt);
          	tiles_boolean = new SimpleEntry<List<Point>, Boolean>(adjacent_vein, true);
          	return tiles_boolean;
        }
       	else
        {
        	Point new_pt = this.blob_next_position(world, vein_pt);
        	if (world.get_tile_occupant(new_pt) instanceof Ore)
            {
        		Ore old_entity = (Ore) world.get_tile_occupant(new_pt);
            	old_entity.remove_entity(world);
            }
        	tiles_boolean = new SimpleEntry<List<Point>, Boolean>(world.move_entity(this, new_pt), false);
          	return tiles_boolean;
        }
	}
	
	public void schedule_blob(WorldModel world, long ticks, Image_store i_store)
	{
		this.schedule_action(world, this.create_ore_blob_action(world, i_store),
          ticks + this.get_rate());
       	this.schedule_animation(world, 0);
	}
	
}