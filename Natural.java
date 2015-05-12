import java.util.*;
import processing.core.*;

public abstract class Natural 
	extends Job
	
{   
	public Natural(String name, List<PImage> imgs, Point position)
	{
	  super(name, imgs, position);
	}

	
	protected void schedule_action(WorldModel world, Job action, long time)
	{
		add_pending_action(action);
		world.schedule_action(action,time);
	}
	
	protected void remove_entity(WorldModel world)
	{
		for( action : get_pending_actions())
		{
			world.unschedule_action(action,time);
			
		}
		clear_pending_actions();
		world.remove_entity(entity);
	}
	
}

