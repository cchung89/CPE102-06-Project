import java.util.*;
import processing.core.*;
import java.util.function.*;

public abstract class Natural 
	extends Job
	
{   
	public Natural(String name, List<PImage> imgs, Point position)
	{
	  super(name, imgs, position);
	}

	
	protected void schedule_action(WorldModel world, LongConsumer action, long time)
	{
		this.add_pending_action(action);
		world.schedule_action(action,time);
	}
	
	protected void remove_entity(WorldModel world)
	{
		for(LongConsumer action : this.get_pending_actions())
		{
			world.unschedule_action(action);
		}
		clear_pending_actions();
		world.remove_entity(this);
	}
	
}

