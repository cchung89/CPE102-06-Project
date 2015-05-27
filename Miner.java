import java.util.*;

import processing.core.*;

import java.util.function.*;

public abstract class Miner
	extends Character
{
	private int animation_rate;
	private List<Point> path;

	public Miner(String name, List<PImage> imgs, Point position , int rate, int resource_limit, int resource_count, int animation_rate)
	{	
		super(name, imgs, position, rate, resource_limit, resource_count);
		this.animation_rate = animation_rate;
		this.path = path;
	}
	
	protected int get_animation_rate()
	{
		return animation_rate;
	}
	
	
	protected void schedule_action(WorldModel world, LongConsumer action, long time)
	{
		this.add_pending_action(action);
		world.schedule_action(action, time);
	}
	
	protected void remove_entity(WorldModel world)
	{
		for (LongConsumer action : this.get_pending_actions())
		{
			world.unschedule_action(action);
		}
		this.clear_pending_actions();
		world.remove_entity(this);
	}
	
	/*public Point next_position(WorldModel world, Point dest_pt)
	{
		int horiz = Actions.sign(dest_pt.x - this.get_position().x);
       	Point new_pt = new Point(this.get_position().x + horiz, this.get_position().y);

       	if (horiz == 0 || world.is_occupied(new_pt))
        {
        	int vert = Actions.sign(dest_pt.y - this.get_position().y);
          	new_pt = new Point(this.get_position().x, this.get_position().y + vert);

          	if (vert == 0 || world.is_occupied(new_pt))
            {
            	new_pt = new Point(this.get_position().x, this.get_position().y);
            }
        }

       	return new_pt;
	}*/
	
	protected Point next_position(WorldModel world, Point goal, Class dest_entity)
	{
		Point start = this.get_position();
		List<Point> path = create_path(world, start, goal, dest_entity);
		if (path.size() > 1)
		{
			return path.get(path.size() - 2);
		}
		else
		{
			return start;
		}
	}
	
	public List<Point> create_path(WorldModel world, Point start, Point goal, Class dest_entity)
	{
		path = Actions.a_star(start, goal, world, dest_entity);
		return path;
	}
	
	public List<Point> get_path()
	{
		return this.path;
	}
	
	public Miner try_transform_miner(WorldModel world, Function<WorldModel, Miner> transform)
	{
		Miner new_entity = transform.apply(world);
       	if (this != new_entity)
       	{
          	for (LongConsumer action : this.get_pending_actions())
            {
            	world.unschedule_action(action);
            }
          	this.clear_pending_actions();
          	world.remove_entity_at(this.get_position());
          	world.add_entity(new_entity);
          	new_entity.schedule_animation(world, 0);
        }
       	return new_entity; 
	}
	
	protected void schedule_animation(WorldModel world, int repeat_count)
	{
		repeat_count = 0;
		this.schedule_action(world, 
          				this.create_animation_action(world, repeat_count),
          				this.get_animation_rate());
	}
	
	protected LongConsumer create_animation_action(WorldModel world, int repeat_count)
	{
		LongConsumer[] action = { null };
		action[0] = (long current_ticks) -> {
			this.remove_pending_action(action[0]);
            this.next_image();
            if (repeat_count != 1)
            {
                this.schedule_action(world, 
                    this.create_animation_action(world, Math.max(repeat_count - 1, 0)),
                current_ticks + this.get_animation_rate());
            }
		};
        return action[0];
	}
	
	protected abstract LongConsumer create_miner_action(WorldModel world, HashMap<String, List<PImage>> image_store);
	
}
