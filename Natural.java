public abstract class Natural extends Job:
	

	public void schedule_action(world,action,time)
	{	add_pending_action(action);
		world.schedule_action(action,time)
	}

	public void remove_entity(world)
	{	for ( actions pending_Actions : get_pending_actions();)
			{unschedule_action(action);
			}
			
		clear_pending_actions();
		remove_entity(this);	

	}