import java.util.*;
import processing.core.*;

public class MinerFull 
	extends Miner 
{
	public MinerFull(String name, List<PImage> imgs, int resource_limit, int resource_count, Point position, int rate, int animation_rate)
	{
		super(name, imgs, position, rate, resource_limit, resource_limit, animation_rate);
	}
	
	//methods implemented for next assignment
	
	public Action create_miner_full_action(WorldModel world, PImage i_store)
	
	public Action create_miner_action(WorldModel world, PImage image_store)
	{
		return this.create_miner_action(world, image_store);
	}
	public List<Pair<Point>, Boolean> miner_to_smith(WorldModel world,Blacksmith smith)
	
	public MinerNotFull try_transform_minr_full(WorldModel world)
	{
		new_entity = new MinerNotFull(this.get_name(),this.get_images(), this.get_resource_limit(), 
				this.get_position(), this.get_rate() , 
				this.get_animation_rate());
		return new_entity;
	}
	
}
