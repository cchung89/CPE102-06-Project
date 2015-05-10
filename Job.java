import java.util.*;
import processing.core.*;

public abstract class Job 
	extends Location
{
	private List<Job> pending_actions;
	
	public Job(String name, List<PImage> imgs, Point position)
	{
		super(name, imgs, position);
		this.pending_actions = new ArrayList<Job>();
	}
	
	public void remove_pending_actions(Job action)
	{
		pending_actions.remove(action);
	}

	public void add_pending_action(Job action)
	{
		pending_actions.add(action);
	}
	
	public List<Job> get_pending_actions()
	{
		return pending_actions;
	}

	public void clear_pending_actions()
	{
		pending_actions = new ArrayList<Job>();
	}
	
}

