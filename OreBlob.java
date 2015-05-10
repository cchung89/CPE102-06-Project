import java.util.*;
import processing.core.*;

public class OreBlob 
	extends Destroyer
{
	private int rate;
	
	public OreBlob(String name, List<PImage> imgs, Point position, int rate, int animation_rate)
		{
			super(name, imgs, position, animation_rate);
			this.rate = rate;
		}

	public int get_rate()
		{
		  return rate;
		}
	/*
	public Action create_ore_blob_action(WorldModel world, PImage i_store)
	
	public Point blob_next_position(Worldmodel world, Point dest_pt)
	
	public Pair<List<Point>, Boolean> blob_to_vein(WorldModel world, Vein vein)
	
	public void schedule_blob(WorldModel world int ticks, PImage i_store)
	*/
}