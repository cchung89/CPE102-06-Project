import java.util.*;
import processing.core.*;

public abstract class Miner
	extends Character
{
	private int animation_rate;

	public Miner(String name, List<PImage> imgs, Point position , int rate, int resource_limit, int resource_count, int animation_rate)
	{	
		super(name, imgs, position, rate, resource_limit, resource_count);
		this.animation_rate = animation_rate;
	}
	
	protected int get_animation_rate()
	{
		return animation_rate;
	}
	
	
	protected void schedule_action(WorldModel world,Job action, long time)
	{
		add_pending_action(action);
	}
	protected void remove_entity(WorldModel world)
	{
		for (action : get_pending_actions())
		{
			world.unschedule_action(action);
		}
		clear_pending_actions();
		world.remove_entity(entity);
	}

	public Point next_position(WorldModel world,Point dest_pt)
	{
		//lamda expresssions for sign function ??
	}
	
	public Miner try_transform_miner(WorldModel world, Function transform)
	{
		
	}
	
	protected void schedule_animation(WorldModel world, int repeat_count)
	
	protected Action create_animation_action(WorldModel world, int repeat_count)
	*/
	
}
