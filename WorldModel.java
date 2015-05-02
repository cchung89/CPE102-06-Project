import java.util.*;

import javax.lang.model.type.NullType;

import javafx.util.Pair;

public class WorldModel
	extends Point
{
	private Background[][] background;
	private int num_rows;
	private int num_cols;
	private Entity[][] occupancy;
	private List<Entity> entities = new ArrayList<Entity>();
	//private action_queue;

	

	public WorldModel(int num_rows, int num_cols, Entity background, List<Entity> entities)
	{
		super(x, y);
		this.background = new Background[num_cols][num_rows];
		this.num_rows = num_rows;
		this.num_cols = num_cols;
		this.occupancy = new Entity[num_cols][num_rows];
		this.entities = entities;
		//this.action_queue = new OrderedList();
	}

	private boolean within_bounds(Point pt)
	{
		return pt.x >= 0 && pt.x < num_cols && pt.y >= 0 && pt.y < num_rows;
	}

	private boolean is_occupied(Point pt)
	{
		return within_bounds(pt) && occupancy[pt.y][pt.x] != null;
	}

	public double find_nearest(Point pt, Class type)
	{
		List<Pair<Entity, Double>> oftype = new ArrayList<Pair<Entity, Double>>();
		//Pair<Entity, Double> pair = Pair.with();
		for (Entity e : entities)
		{
			if (e instanceof type)
			{
				oftype.add(Pair.with(e, distance_sq(pt, e.get_position())));
			}
		}
		
      	return nearest_entity(oftype);
	}

	private void add_entity(Entity entity)
	{
		Point pt = entity.get_position();
      	if (within_bounds(pt))
      	{
        	Entity old_entity = occupancy[pt.y][pt.x]
      	}
        if (old_entity != null)
        {
            //entities.clear_pending_actions(old_entity);
        }
        occupancy[pt.y][pt.x] = entity;
        entities.add(entity);
	}

	public List<Point> move_entity(Entity entity, Point pt)
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

	public void remove_entity(Entity entity)
	{
		remove_entity_at(entity.get_position());
	}

	private void remove_entity_at(Point pt)
	{
		if (within_bounds(pt) && occupancy[pt.y][pt.x] != null)
		{
         	Entity entity = occupancy[pt.y][pt.x];
         	Point set_point = new Point(-1, -1);
         	entity.set_position(set_point);
         	entities.remove(entity);
         	occupancy[pt.y][pt.x] = null;
		}
	}

	public Entity get_background_image(Point pt)
	{
		if (within_bounds(pt))
		{
         	return Background.get_image(background[pt.y][pt.x]);
		}
	}

	public int get_background(Point pt)
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
	}

	public Entity get_tile_occupant(Point pt)
	{
		if (within_bounds(pt))
		{
         	return occupancy[pt.y][pt.x];
		}
	}

	public Entity get_entities()
	{
		return this.entities;
	}

	public double nearest_entity(List<Pair<Entity, Double>> entity_dists)
	{
		if (entity_dists.size() > 0)
		{
      		Pair<Entity, Double> pair = entity_dists.get(0);
      		for (Pair<Entity, Double> other : entity_dists)
      		{
         		if (other.getValue() < pair.getValue())
         		{
            		pair = other;
         		}
      		}
      		Entity nearest = pair.getKey();
		}
   		else
   		{
      		NullType nearest = null;
   		}
   		return nearest;
	}

	private double distance_sq(Point p1, Point p2)
	{
		return (p1.x - p2.x)*(p1.x - p2.x) + (p1.y - p2.y)*(p1.y - p2.y);
	}
}