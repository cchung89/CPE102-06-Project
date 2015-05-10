import java.util.*;
import processing.core.*;

public abstract class Destroyer 
	extends Natural
{
	private int animation_rate;
	
	public Destroyer(String name, List<PImage> imgs, Point position, int animation_rate)
	{
		super(name, imgs, position);
		this.animation_rate = animation_rate;
	}
	
	/*
	protected void schedule_animation(WorldModel world, int repeat_count)
	
	protected Action create_animation_action(WorldModel world, int repeat_count)
	*/
	
	protected int get_animation_rate()
	{
		return animation_rate;
	}
}