import processing.core.*;

import java.util.*;


public class Main 
	extends PApplet
{	
	private long next_time;
	private static final int ANIMATION_TIME = 100;
	private Background default_background;
	private final int BACKGROUND_COLOR = color(100, 100, 100);
	
	private static final boolean RUN_AFTER_LOAD = true;
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
	
	
	private Background create_default_background(List<PImage> imgs)
	{
		return new Background(Image_store.DEFAULT_IMAGE_NAME, imgs);
	}
	
	public void setup()
	{
		size(SCREEN_WIDTH, SCREEN_HEIGHT);
		background(BACKGROUND_COLOR);
		
		HashMap<String, List<PImage>> images = new Image_store().load_images(IMAGE_LIST_FILE_NAME, TILE_WIDTH, TILE_HEIGHT);
		
		int num_cols = SCREEN_WIDTH / TILE_WIDTH * WORLD_WIDTH_SCALE; // TILE_WIDTH * WORLD_WIDTH_SCALE
		int num_rows = SCREEN_HEIGHT / TILE_HEIGHT * WORLD_HEIGHT_SCALE; // TILE_HEIGHT * WORLD_HEIGHT_SCALE
		
		default_background = create_default_background(new Image_store().get_images(images, Image_store.DEFAULT_IMAGE_NAME));
		
		world = new WorldModel(num_rows, num_cols, default_background);
		view = new WorldView(this, SCREEN_WIDTH / TILE_WIDTH, SCREEN_HEIGHT / TILE_HEIGHT, world, TILE_WIDTH, TILE_HEIGHT);
		
		Save_load.load_world(world, images, WORLD_FILE, RUN_AFTER_LOAD);
		
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
	        this.world.update_on_time(time); //update the action_queue
	    }
		this.view.draw_viewport();
	}
	
	public void keyPressed() //move the current viewing rectangle
	{
		int dy = 0;
		int dx = 0;
		switch(keyCode)
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
