import java.util.*;

//import javafx.util.Pair;

public class WorldModel
{
	private Background[][] background;
	private int num_rows;
	private int num_cols;
	private Location[][] occupancy;
	private List<Location> entities = new ArrayList<Location>();
	//private action_queue;


	public WorldModel(int num_rows, int num_cols, Location[][] occupancy, Background[][] background, List<Location> entities)
	{
		this.background = background;
		this.num_rows = num_rows;
		this.num_cols = num_cols;
		this.occupancy = occupancy;
		this.entities = entities;
		//this.action_queue = new OrderedList();
	}

	private boolean within_bounds(Point pt)
	{
		return pt.x >= 0 && pt.x < num_cols && pt.y >= 0 && pt.y < num_rows;
	}

	public boolean is_occupied(Point pt)
	{
		return within_bounds(pt) && occupancy[pt.y][pt.x] != null;
	}

	public Location find_nearest(Point pt, Class type)
	{
		List<Location> oftype = new ArrayList<Location>();
		for (Location e : entities)
		{
			if (type.isInstance(e))
			{
				//Pair<Location, Double> position = new Pair<Location, Double>(e, distance_sq(pt, e.get_position()));
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
        	Entity old_entity = occupancy[pt.y][pt.x];
        	if (old_entity != null)
        	{
            //entities.clear_pending_actions(old_entity);
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

	private void remove_entity_at(Point pt)
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

	/*public Entity get_background_image(Point pt)
	{
		if (within_bounds(pt))
		{
         	return background[pt.y][pt.x].get_image();
		}
	}

	public Entity get_background(Point pt)
	{
		if (within_bounds(pt))
		{
         	return background[pt.y][pt.x];
		}
	}

	public void set_background(Point pt, Background bgnd)
	{
		if (within_bounds(pt))
		{
         	background[pt.y][pt.x] = bgnd;
		}
	}*/

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
        	boolean first = true;
        	double shortest = 0;
        	Location closest_entity = null;
            for(Location entity : entity_list)
            {
                if(first)
                {
                    shortest = distance_sq(pt, entity.get_position());
                    closest_entity = entity;
                    first = false;
                }
                else
                {
                	double distance = distance_sq(pt, entity.get_position());
                    if(distance < shortest)
                    {
                        closest_entity = entity;
                        shortest = distance;
                    }
                }
            }
            return closest_entity;
        }
        return null;
    }
	
	private double distance_sq(Point p1, Point p2)
	{
		return (p1.x - p2.x)*(p1.x - p2.x) + (p1.y - p2.y)*(p1.y - p2.y);
	}
}