import java.util.*;

import processing.core.*;
import static java.util.Arrays.asList;

public class Obstacle 
	extends Location
{
	public Obstacle(String name, Point position, List<PImage> imgs)
		{
			super(name, imgs, position);
		}

	public String entity_string()
	{
		List<String> strings = new ArrayList<String>(asList(
				"obstacle", this.get_name(), String.valueOf(this.get_position().x),
		         String.valueOf(this.get_position().y)));
		String result = String.join(" ", strings);
		return result;
	}
}