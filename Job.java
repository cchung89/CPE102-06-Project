import java.util.*;

import processing.core.*;

import java.util.function.*;

public abstract class Job 
	extends Location
{
	private List<LongConsumer> pending_actions;
	
	public Job(String name, List<PImage> imgs, Point position)
	{
		super(name, imgs, position);
		this.pending_actions = new ArrayList<LongConsumer>();
	}
	
	public void remove_pending_action(LongConsumer action)
	{
		pending_actions.remove(action);
	}

	public void add_pending_action(LongConsumer action)
	{
		pending_actions.add(action);
	}
	
	public List<LongConsumer> get_pending_actions()
	{
		return pending_actions;
	}

	public void clear_pending_actions()
	{
		pending_actions = new ArrayList<LongConsumer>();
	}
	
}

