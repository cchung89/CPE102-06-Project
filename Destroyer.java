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
	
	
	protected void schedule_animation(WorldModel world, int repeat_count)
	{
		
	}
	//default 
	protected void schedule_animation(WorldModel world)
	{
		this.schedule_action(world,this.create_animation_action(world, 0),
				this.get_animation_rate());
	}
	
	protected Action create_animation_action(WorldModel world, int repeat_count)
	{
		protected Action action(current_ticks)
		{
			this.remove_pending_action(action);
			this.next_image();
			
			if (repeat_count != 1)
			{
				this.schedule_action(world, this.create_animation_action(world, 
						Math.max(repeat_count - 1, 0)),
						current_ticks + this.get_animation_rate()) ;
			}
			return [this.get_position];
		}
		
		return action;
	}
	
	
	protected int get_animation_rate()
	{
		return animation_rate;
	}
}