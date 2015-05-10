import java.util.*;
import processing.core.*;
import static java.util.Arrays.asList;

public class Vein
	extends Mineral
{
	private int resource_distance;

	public Vein(String name, List<PImage> imgs, int rate, Point position, int resource_distance)
	{
		super(name, imgs, position, rate);
		this.resource_distance = resource_distance;	
	}
	
	//method overloading 
	public Vein(String name, List<PImage> imgs, int rate, Point position)
	{
		super(name, imgs, position, rate);
		this.resource_distance = 1 ;
	}

	public int get_resource_distance()
	{
		return resource_distance;
	}

	public String entity_string()
	{
		List<String> strings = new ArrayList<String>(asList(
				"vein", this.get_name(), String.valueOf(this.get_position().x),
		         String.valueOf(this.get_position().y),
		         String.valueOf(this.get_rate()), String.valueOf(get_resource_distance())));
		String result = String.join(" ", strings);
		return result;
	}
	/*
	public void schedule_vein(WorldModel world, int ticks, PImage i_store)
	
	public Action create_vein_action(WorldModel world, PImage i_store)
	*/
}