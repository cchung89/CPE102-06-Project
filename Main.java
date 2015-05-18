import processing.core.*;

import java.util.*;
import java.util.function.*;
import java.io.File;
import java.io.IOException;


public class Main 
	extends PApplet
{	
	private long next_time;
	private static final int ANIMATION_TIME = 100;
	
	private static final boolean RUN_AFTER_LOAD = true;
	private static String SOURCE_PATH = "images/";
	private static final String IMAGE_LIST_FILE_NAME = "imagelist";
	private static final String WORLD_FILE = "gaia.sav";

	private static final int WORLD_WIDTH_SCALE = 2;
	private static final int WORLD_HEIGHT_SCALE = 2;

	private static final int SCREEN_WIDTH = 640;
	private static final int SCREEN_HEIGHT = 480;
	private static final int TILE_WIDTH = 32;
	private static final int TILE_HEIGHT = 32;
	
	private WorldModel world;
	private WorldView view;
	private Image_store image_store = new Image_store(this, SOURCE_PATH, IMAGE_LIST_FILE_NAME);
	
	
	private Background create_default_background(List<PImage> imgs)
	{
		return new Background(Image_store.DEFAULT_IMAGE_NAME, imgs);
	}
	
	public void setup()
	{
		new Random();
		size(SCREEN_WIDTH, SCREEN_HEIGHT);
		
		image_store.load_images(TILE_WIDTH, TILE_HEIGHT);
		
		int num_cols = SCREEN_WIDTH / TILE_WIDTH * WORLD_WIDTH_SCALE; // TILE_WIDTH * WORLD_WIDTH_SCALE
		int num_rows = SCREEN_HEIGHT / TILE_HEIGHT * WORLD_WIDTH_SCALE; // TILE_HEIGHT * WORLD_HEIGHT_SCALE
		
		Background default_background = create_default_background(image_store.get_images(Image_store.DEFAULT_IMAGE_NAME));
		
		world = new WorldModel(num_rows, num_cols, default_background);
		view = new WorldView(this, SCREEN_WIDTH / TILE_WIDTH, SCREEN_HEIGHT / TILE_HEIGHT, world, TILE_WIDTH, TILE_HEIGHT);
		
		Save_load.load_world(world, image_store, WORLD_FILE, RUN_AFTER_LOAD);
		
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
	        try {
				moves();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		this.view.draw_viewport();
	}
	
	private void moves() throws IOException
	{
		
		for (int x = 0; x < world.num_cols; x++)
		{
			for (int y = 0; y < world.num_rows; y++)
			{
				Point tile_location = new Point(x, y);
				if (world.is_occupied(tile_location))
				{
					Job entity = (Job) world.get_tile_occupant(tile_location);
					if (entity.get_pending_actions().size() != 0)
					{
						LongConsumer action = entity.get_pending_actions().get(0);
						action.accept(System.currentTimeMillis());
						break;
					}
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
		PApplet.main("Main");
	}
	

}
