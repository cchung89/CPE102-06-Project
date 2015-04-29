public abstract class Miner extends Character:
	private int animation_rate;

	public Miner(String name, imgs, Point position , int rate, int resource_limit, int animation_rate)
	{	super();
		this.animation_rate = animation_rate;	

	}

	public int get_animation_rate()
	{	return animation_rate;
	}

	public void schedule_action(world,action,time)
	{
		add_pending_action(action);
		schedule_action(action,time);
	}

	public void remove_entity(world)
	{
		for (actions pending_action : get_pending_actions();)
			{unschedule_action(action);}
		clear_pending_actions();
		remove_entity(this);
	}

	public Point next_position(world,dest_pt)
	{

		horiz = sign(dest_pt.x - position.x);
		new_pt = Point(position.x,position.y);

		if (horiz == 0; || is_occupied(new_pt);)
			{
				vert = sign(dest_pt.y - position.y);
				new_pt = Point(position.x , position.y + vert);

				if (vert == 0 || is_occupied(new_pt))
					{	new_pt = Point(position.x , position.y)

					} 
				}
		return new_pt;		
	}