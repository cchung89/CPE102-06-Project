public abstract class Miner extends Character

{
	//private int animation_rate;

	public Miner(String name, Point position , int rate, int resource_limit)
	{	super(name, position,rate,resource_limit);
			

	}
    /*
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
		for (actions pending_action : get_pending_actions())
			{unschedule_action(action);}
		clear_pending_actions();
		remove_entity(this);
	}
	*/

	public Point next_position(WorldModel world,Point dest_pt)
	{

		horiz = sign(dest_pt.x - this.position.x);
		Point new_pt = new Point(this.position.x + horiz,this.position.y);

		if (horiz == 0 || world.is_occupied(new_pt) )
			{
				vert = sign(dest_pt.y - this.position.y);
				Point new_pt = Point(this.position.x , this.position.y + vert);

				if (vert == 0 || is_occupied(new_pt))
					{	Point new_pt =  Point(this.position.x , this.position.y);

					} 
				}
		return new_pt;		
		
		//try_transform_miner, animations
	}
	
}
