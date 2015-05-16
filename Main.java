import processing.core.*;

import java.util.*;


public class Main extends PApplet {
	
	private static final boolean RUN_AFTER_LOAD = true;

	private static final String IMAGE_LIST_FILE_NAME = "imagelist";
	private static final String WORLD_FILE = "gaia.sav";

	private static final int WORLD_WIDTH_SCALE = 2;
	private static final int WORLD_HEIGHT_SCALE = 2;

	private static final int SCREEN_WIDTH = 640;
	private static final int SCREEN_HEIGHT = 480;
	private static final int TILE_WIDTH = 32;
	private static final int TILE_HEIGHT = 32;
	
	public void setup()
	{
	
	}
	
	public void draw()
	{
		
	}
	
	public void load_world(WorldModel world, HashMap<String, List<PImage>> i_store, File filename)
	{
		
	}
	
	public static void main(String[] args)
	{
		Math.random();
		//screen = pygame.display.set_mode((SCREEN_WIDTH, SCREEN_HEIGHT))
		HashMap<String, List<PImage>> i_store = image_store.load_images(IMAGE_LIST_FILE_NAME,
		      TILE_WIDTH, TILE_HEIGHT);

		int num_cols = SCREEN_WIDTH; // TILE_WIDTH * WORLD_WIDTH_SCALE
		int num_rows = SCREEN_HEIGHT; // TILE_HEIGHT * WORLD_HEIGHT_SCALE

		WorldModel world = new WorldModel(num_rows, num_cols);
		WorldView view = new WorldView(floor(SCREEN_WIDTH / TILE_WIDTH),
		      floor(SCREEN_HEIGHT / TILE_HEIGHT), screen, world, TILE_WIDTH, TILE_HEIGHT);

		load_world(world, i_store, WORLD_FILE);

		view.update_view();

		controller.activity_loop(view, world);
	}
	

}
