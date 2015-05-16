import java.util.*;

import processing.core.*;
import static java.util.Arrays.asList;

public class Blacksmith 
	extends Character
{
	private int resource_distance;
	
	public Blacksmith(String name, Point position, List<PImage> imgs, int resource_limit, int rate, int resource_distance)
	{
		super(name, imgs, position, rate, resource_limit, 0);
		this.resource_distance = 1;
	}
	
	protected int get_resource_distance()
	{
		return resource_distance;
	}

	public String entity_string()
	{
		List<String> strings = new ArrayList<String>(asList(
				"blacksmith", this.get_name(), String.valueOf(this.get_position().x),
		         String.valueOf(this.get_position().y), String.valueOf(this.get_resource_limit()),
		         String.valueOf(this.get_rate()), String.valueOf(get_resource_distance())));
		String result = String.join(" ", strings);
		return result;
	}
}