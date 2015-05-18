import java.util.*;
import java.util.function.*;

import processing.core.*;

public class WorldModel extends PApplet
{
	private Background[][] background;
	public final int num_rows;
	public final int num_cols;
	private Location[][] occupancy;
	private List<Location> entities;
	private OrderedList action_queue;


	public WorldModel(int num_rows, int num_cols, Background background)
	{
		this.background = new Background[num_rows][num_cols];
		this.num_rows = num_rows;
		this.num_cols = num_cols;
		this.occupancy = new Location[num_rows][num_cols];
		this.entities = new ArrayList<Location>();
		this.action_queue = new OrderedList();
		for (int x = 0; x < num_cols; x++)
		{
			for (int y = 0; y < num_rows; y++)
			{
				this.background[y][x] = background;
			}
		}
	}

	public boolean within_bounds(Point pt)
	{
		return pt.x >= 0 && pt.x < num_cols && pt.y >= 0 && pt.y < num_rows;
	}

	public boolean is_occupied(Point pt)
	{
		return within_bounds(pt) && (occupancy[pt.y][pt.x] != null);
	}

	public Location find_nearest(Point pt, Class type)
	{
		List<Location> oftype = new ArrayList<Location>();
		for (Location e : entities)
		{
			if (type.isInstance(e))
			{
				oftype.add(e);
			}
		}
		
      	return nearest_entity(oftype, pt);
	}

	public void add_entity(Location entity)
	{
		Point pt = entity.get_position();
      	if (within_bounds(pt))
      	{
        	Job old_entity = (Job) occupancy[pt.y][pt.x];
        	if (old_entity != null)
        	{
            	old_entity.clear_pending_actions();
        	}
        	occupancy[pt.y][pt.x] = entity;
        	entities.add(entity);
      	}
	}

	public List<Point> move_entity(Location entity, Point pt)
	{
		List<Point> tiles = new ArrayList<Point>();

		if (within_bounds(pt))
		{
         	Point old_pt = entity.get_position();
         	occupancy[old_pt.y][old_pt.x] = null;
         	tiles.add(old_pt);
         	occupancy[pt.y][pt.x] = entity;
         	tiles.add(pt);
         	entity.set_position(pt);
		}

      return tiles;
	}

	public void remove_entity(Location entity)
	{
		remove_entity_at(entity.get_position());
	}

	public void remove_entity_at(Point pt)
	{
		if (within_bounds(pt) && occupancy[pt.y][pt.x] != null)
		{
         	Location entity = occupancy[pt.y][pt.x];
         	Point set_point = new Point(-1, -1);
         	entity.set_position(set_point);
         	entities.remove(entity);
         	occupancy[pt.y][pt.x] = null;
		}
	}

	public void schedule_action(LongConsumer action, long time)
    {
    	this.action_queue.insert(action, time);
    }

    public void unschedule_action(LongConsumer action)
    {
    	this.action_queue.remove(action);
    }

    public void update_on_time(long ticks)
    {
    	List<Rectangle> tiles = new ArrayList<Rectangle>();

      	ListItem next = this.action_queue.head();
      	while (next != null && next.get_ord() < ticks)
        {
        	this.action_queue.pop();
         	//tiles.add(next.get_item().accept(ticks));
         	next = this.action_queue.head();
        }

      	//return tiles;
    }

	public PImage get_background_image(Point pt)
	{
		if (within_bounds(pt))
		{
    		return background[pt.y][pt.x].get_image();
		}
		return null;
	}
	
	public Background get_background(Point pt)
	{
		if (within_bounds(pt))
		{
    		return background[pt.y][pt.x];
    	}
		return null;
	}

	public void set_background(Point pt, Background bgnd)
	{
		if (within_bounds(pt))
		{
         	background[pt.y][pt.x] = bgnd;
		}
	}

	public Location get_tile_occupant(Point pt)
	{
		if (within_bounds(pt))
		{
         	return occupancy[pt.y][pt.x];
		}
		return null;
	}

	public List<Location> get_entities()
	{
		return entities;
	}
	
	private Location nearest_entity(List<Location> entity_list, Point pt)
    {
        if(entity_list.size() > 0)
        {
        	Location closest = entity_list.get(0);
            for(Location entity : entity_list)
            {
            	if(distance_sq(pt, entity.get_position()) < distance_sq(pt, closest.get_position()))
            	{
            		closest = entity;
            	}
            }
            return closest;
        }
        return null;
    }
	
	private double distance_sq(Point p1, Point p2)
	{
		return (p1.x - p2.x)*(p1.x - p2.x) + (p1.y - p2.y)*(p1.y - p2.y);
	}
}