import java.util.*;

import processing.core.*;
import static java.util.Arrays.asList;

public class Ore 
	extends Mineral
{
	public Ore (String name, List<PImage> imgs, Point position, int rate)
	{
		super(name, imgs, position, rate);
	}
	
	//method overloading
	public Ore(String name, List<PImage> imgs, Point position)
	{
		super(name, imgs, position, 5000);
	}
	
	public String entity_string()
	{
		List<String> strings = new ArrayList<String>(asList(
				"ore", this.get_name(), String.valueOf(this.get_position().x),
		         String.valueOf(this.get_position().y),
		         String.valueOf(this.get_rate())));
		String result = String.join(" ", strings);
		return result;
	}
	
	/*
	public void schedule_ore(WorldModel world, int ticks, PImage i_store)
	
	public Action create_ore_action(WorldModel world, PImage i_store)
	*/
}