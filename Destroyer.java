import java.util.*;
import processing.core.*;
import java.util.function.*;

public abstract class Destroyer 
	extends Natural
{
	private int animation_rate;
	
	public Destroyer(String name, List<PImage> imgs, Point position, int animation_rate)
	{
		super(name, imgs, position);
		this.animation_rate = animation_rate;
	}
	
	
	protected void schedule_animation(WorldModel world, int repeat_count)
	{
		this.schedule_action(world, 
          				this.create_animation_action(world, repeat_count),
          				this.get_animation_rate());
	}
	
	protected LongConsumer create_animation_action(WorldModel world, int repeat_count)
	{
		LongConsumer[] action = { null };
		action[0] = (long current_ticks) -> {
			this.remove_pending_action(action[0]);
            this.next_image();
            if (repeat_count != 1)
            {
                this.schedule_action(world, 
                    this.create_animation_action(world, Math.max(repeat_count - 1, 0)),
                current_ticks + this.get_animation_rate());
            }
		};
        return action[0];
	}
	
	
	protected int get_animation_rate()
	{
		return animation_rate;
	}
}