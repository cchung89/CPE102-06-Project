import processing.core.*;

import java.util.*;
import java.io.File;
import java.io.IOException;


public class Main 
	extends PApplet
{	
	private WorldModel world;
	private WorldView view;
	private long next_time;
	private static final int ANIMATION_TIME = 100;
	
	private static final boolean RUN_AFTER_LOAD = true;

	private static final String IMAGE_LIST_FILE_NAME = "imagelist";
	private static final String WORLD_FILE = "gaia.sav";

	private static final int WORLD_WIDTH_SCALE = 2;
	private static final int WORLD_HEIGHT_SCALE = 2;

	private static final int SCREEN_WIDTH = 640;
	private static final int SCREEN_HEIGHT = 480;
	private static final int TILE_WIDTH = 32;
	private static final int TILE_HEIGHT = 32;
	
	
	private Background create_default_background(List<PImage> imgs)
	{
		return new Background(Image_store.DEFAULT_IMAGE_NAME, imgs);
	}
	
	public void setup()
	{
		new Random();
		size(SCREEN_WIDTH, SCREEN_HEIGHT);
		
		HashMap<String, List<PImage>> i_store = Image_store.load_images(TILE_WIDTH, TILE_HEIGHT);

		int num_cols = SCREEN_WIDTH; // TILE_WIDTH * WORLD_WIDTH_SCALE
		int num_rows = SCREEN_HEIGHT; // TILE_HEIGHT * WORLD_HEIGHT_SCALE
		
		Background default_background = create_default_background(Image_store.get_images(i_store, Image_store.DEFAULT_IMAGE_NAME));
		
		world = new WorldModel(num_rows, num_cols);
		view = new WorldView(SCREEN_WIDTH / TILE_WIDTH, SCREEN_HEIGHT / TILE_HEIGHT, this, world, TILE_WIDTH, TILE_HEIGHT);
		
		Save_load.load_world(world, i_store, WORLD_FILE, RUN_AFTER_LOAD);
		view.update_view(0, 0);
		
		next_time = System.currentTimeMillis() + ANIMATION_TIME;
	}
	
	private void next_images()
	{
		for (Entity entity : this.world.get_entities())
		{
			entity.next_image();
		}
	}
	
	public void draw()
	{
		long time = System.currentTimeMillis();
		if (time >= next_time)
	    {
			next_images();
	        next_time = time + ANIMATION_TIME;
	        moves();
	    }
		this.view.draw_viewport();
	}
	
	public void load_world(WorldModel world, HashMap<String, List<PImage>> i_store, File filename)
	{
		
	}
	
	private void moves()
	{
		for (int x = 0; x < world.num_cols; x++)
		{
			for (int y = 0; y < world.num_rows; y++)
			{
				Point tile_location = new Point(x, y);
				if (world.is_occupied(tile_location))
				{
					Location entity = world.get_tile_occupant(tile_location);
					if (entity.get_pending_action())
				}
			}
		}
	}
	
	public void keyPressed()
	{
		int dy = 0;
		int dx = 0;
		switch(key)
		{
			case UP:
			{
				dy = -1;
				break;
			}
			case DOWN:
			{
				dy = 1;
				break;
			}
			case RIGHT:
			{
				dx = 1;
				break;
			}
			case LEFT:
			{
				dx = -1;
				break;
			}
		}
		view.update_view(dx, dy);
	}
	
	public static void main(String[] args)
	{
		
	}
	

}
