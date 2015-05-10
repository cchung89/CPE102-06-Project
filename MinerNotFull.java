import java.util.*;
import processing.core.*;
import static java.util.Arrays.asList;

public class MinerNotFull 
	extends Miner
{
	public MinerNotFull(String name, List<PImage> imgs, int resource_limit, Point position, int rate, int animation_rate)
	{
		super(name, imgs, position, rate, resource_limit, 0, animation_rate);
		//this.resource_count = 0 ;
	}

	public String entity_string()
	{
		List<String> strings = new ArrayList<String>(asList(
				"miner", this.get_name(), String.valueOf(this.get_position().x),
		         String.valueOf(this.get_position().y), String.valueOf(this.get_resource_limit()),
		         String.valueOf(this.get_rate()), String.valueOf(get_animation_rate())));
		String result = String.join(" ", strings);
		return result;
	}
	/*
	public Action create_miner_not_full_action(WorldModel world, PImage i_store)
	
	public MinerFull try_transform_miner_not_full(WorldModel world)
	
	protected Action create_miner_action(WorldModel world, PImage image_store)
	
	public Pair<List<Point>, Boolean> miner_to_ore(WorldModel world, Ore ore)
	
	public void schedule_miner(WorldModel world, int ticks, PImage i_store)	
	*/
}
