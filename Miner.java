import java.util.*;
import processing.core.*;

public abstract class Miner
	extends Character
{
	private int animation_rate;

	public Miner(String name, List<PImage> imgs, Point position , int rate, int resource_limit, int resource_count, int animation_rate)
	{	
		super(name, imgs, position, rate, resource_limit, resource_count);
	}
	
	protected int get_animation_rate()
	{
		return animation_rate;
	}
	
	/*
	protected void schedule_action(world,action,time)

	protected void remove_entity(world)

	public Point next_position(WorldModel world,Point dest_pt)
	
	public Miner try_transform_miner(WorldModel world, Function transform)
	
	protected void schedule_animation(WorldModel world, int repeat_count)
	
	protected Action create_animation_action(WorldModel world, int repeat_count)
	*/
	
}
