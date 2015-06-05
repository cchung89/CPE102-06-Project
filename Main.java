import processing.core.*;
import processing.event.MouseEvent;

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
	
	private static final int SUPER_VEIN_RATE_MIN = 5000;
	private static final int SUPER_VEIN_RATE_MAX = 10000;
	
	private static final int GUARDIAN_KNIGHT_RATE = 4000;
	private static final int SPAWNED_BLOB_RATE = 5000;
	
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
		draw_path();
	}
	
	public void draw_path() // draw the entity's path
	{
		int x = mouseX / TILE_WIDTH;
		int y = mouseY / TILE_HEIGHT;
		
		Point world_pt = view.viewport_to_world(new Point(x, y));
		
		if (world.is_occupied(world_pt))
		{
				List<Point> path = new ArrayList<Point>();
				Location entity = world.get_tile_occupant(world_pt);
				if (entity instanceof Miner)
				{
					path = ((Miner) entity).get_path();
				}
				else if (entity instanceof OreBlob)
				{
					path = ((OreBlob) entity).get_path();
				}
				else if (entity instanceof Knight)
				{
					path = ((Knight) entity).get_path();
				}
				if (path != null && path.size() > 1)
				{
					for (int i = 0; i < path.size() - 1; i++)
					{
						Point view_pt = view.world_to_viewport(path.get(i));
						fill(200, 100, 100);
						ellipse(view_pt.x * TILE_WIDTH + 16, 
								view_pt.y * TILE_HEIGHT + 16, 
								TILE_WIDTH / 2, TILE_HEIGHT / 2);
					}
				}
		}
	}
	
	public void keyPressed() //move the current viewing rectangle
	{
		int dy = 0;
		int dx = 0;
		
		int x = mouseX / TILE_WIDTH;
		int y = mouseY / TILE_HEIGHT;
		HashMap<String, List<PImage>> images = new Image_store().load_images(IMAGE_LIST_FILE_NAME, TILE_WIDTH, TILE_HEIGHT);
		Point world_pt = view.viewport_to_world(new Point(x, y));
		
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
			case '1':
			{
				if (!(world.is_occupied(world_pt)))
				{
					OreBlob blob = Actions.create_blob(world,  "blob", world_pt, SPAWNED_BLOB_RATE, System.currentTimeMillis(), images);
					world.add_entity(blob);
				}
			}
			case '2':
			{
				if (!(world.is_occupied(world_pt)))
				{
					Vein vein = Actions.create_vein(world, "vein", world_pt, System.currentTimeMillis(), images);
					vein.schedule_vein(world, System.currentTimeMillis(), images);
					world.add_entity(vein);
				}
			}
			case '3':
			{
				if (!(world.is_occupied(world_pt)))
				{
					Ore ore = Actions.create_ore(world, "ore", world_pt, System.currentTimeMillis(), images);
					world.add_entity(ore);
				}
			}
			case '4':
			{
				if (!(world.is_occupied(world_pt)))
				{
					Miner miner = Actions.create_miner(world, "miner", world_pt,
							Tent.SUPER_MINER_RATE, System.currentTimeMillis(), images);
					world.add_entity(miner);
				}
			}
			case '5':
			{
				if (!(world.is_occupied(world_pt)))
				{
					Obstacle obstacle = new Obstacle("obstacle", world_pt, new Image_store().get_images(images, "obstacle"));
					world.add_entity(obstacle);
				}
			}
		}
		view.update_view(dx, dy);
	}
	
	public void mouseClicked()
	{
		int x = mouseX / TILE_WIDTH;
		int y = mouseY / TILE_HEIGHT;
		HashMap<String, List<PImage>> images = new Image_store().load_images(IMAGE_LIST_FILE_NAME, TILE_WIDTH, TILE_HEIGHT);
		
		Point world_pt = view.viewport_to_world(new Point(x, y));
		switch(mouseButton)
		{
			case LEFT:
			{
				if (!(world.is_occupied(world_pt)))
				{
					Tent tent = Actions.create_tent(world, "tent", world_pt, System.currentTimeMillis(), images);
					tent.schedule_tent(world, System.currentTimeMillis(), images);
					world.add_entity(tent);
					
					for (int dy = -2; dy < 3; dy+=4)
					{
						for (int dx = -2; dx < 3; dx+=4)
						{
							Point new_pt = new Point(world_pt.x + dx, world_pt.y + dy);
							if (!(world.is_occupied(new_pt)))
							{
								Knight knight = Actions.create_knight(world, "knight", new_pt, GUARDIAN_KNIGHT_RATE, System.currentTimeMillis(), images);
								world.add_entity(knight);
							}
						}
					}
				}
				for (int dy = -2; dy < 3; dy++)
				{
					for (int dx = -2; dx < 3; dx++)
					{
						Point region_pt = new Point(world_pt.x + dx, world_pt.y + dy);
						Location entity = world.get_tile_occupant(region_pt);
						world.set_background(region_pt, new Background("earth", new Image_store().get_images(images, "earth")));
						if (entity instanceof Vein)
						{
							((Vein) entity).remove_entity(world);
							Vein super_vein = new Vein("vein2",
									(int)(Math.random() * (SUPER_VEIN_RATE_MAX + 1 - SUPER_VEIN_RATE_MIN) + SUPER_VEIN_RATE_MIN),
									region_pt, new Image_store().get_images(images, "vein2"), 2);
							super_vein.schedule_vein(world, System.currentTimeMillis(), images);
							world.add_entity(super_vein);
						}
					}
				}
				break;
			}
			case RIGHT:
			{
				for (int dy = -1; dy < 2; dy++)
				{
					for (int dx = -1; dx < 2; dx++)
					{
						Point region_pt = new Point(world_pt.x + dx, world_pt.y + dy);
						world.set_background(region_pt, new Background("lava", new Image_store().get_images(images, "lava")));
						if (world.is_occupied(region_pt))
						{
							Location entity = world.get_tile_occupant(region_pt);
							if (entity instanceof Miner)
							{
								((Miner) entity).remove_entity(world);
							}
							if (entity instanceof Natural)
							{
								((Natural) entity).remove_entity(world);
							}
							else
							{
								world.remove_entity(entity);
							}
						}
					}
				}
				break;
			}
		}
	}
	
	public static void main(String[] args)
	{
		PApplet.main("Main");
	}
	

}
