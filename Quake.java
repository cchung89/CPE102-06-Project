import java.util.*;
import java.util.function.*;

import processing.core.*;

public class Quake
	extends Destroyer
{
		public Quake (String name, List<PImage> imgs, Point position, int animation_rate)
		{
			super(name, imgs, position, animation_rate);
		}
		
		public LongConsumer create_entity_death_action(WorldModel world)
		{
			LongConsumer [] action = {null};
			action[0] = (long current_ticks) -> {
				this.remove_pending_action(action[0]);
          		this.remove_entity(world);
			};
       		return action[0];
		}
		
		public void schedule_quake(WorldModel world, long ticks)
		{
			this.schedule_animation(world, Actions.QUAKE_STEPS); 
			this.schedule_action(world, this.create_entity_death_action(world),
          					ticks + Actions.QUAKE_DURATION);
		}
}
