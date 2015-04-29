
public abstract class Job extends Location:
	//private action actions = pending_actions;

	public Job(name,imgs,position)
	{

		super();
		this.pending_actions = [];

	}

	public void remove_pending_actions(action)
	{
		pending_actions.remove(action);

	}

	public void add_pending_action(action)
	{
		pending_actions.append(action);
	}

	public actions get_pending_actions()
	{
		return pending_actions;
	}
xs
	public void clear_pending_actions()
	{
		pending_actions = [] ;

	}