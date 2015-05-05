public abstract class Job 
	extends Location
{
	//private Array<action> actions = pending_actions;
	
	public Job(String name, Point position)
	{
		super(name, position);
		//this.pending_actions = [];
	}
	
	//pending action methods for next assignment
	/*
	public void remove_pending_actions(action)
	{
		pending_actions.remove(action);
	}

	public void add_pending_action(action)
	{
		pending_actions.add(action);
	}

	public actions get_pending_actions()
	{
		return pending_actions;
	}

	public void clear_pending_actions()
	{
		pending_actions = [] ;
	}
	*/
}

