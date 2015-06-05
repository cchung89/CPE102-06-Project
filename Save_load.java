import java.util.*;
import java.io.*;

import processing.core.*;

import java.util.Scanner;


public class Save_load
{
	private static final int PROPERTY_KEY = 0;

	private static final String BGND_KEY = "background";
	private static final int BGND_NUM_PROPERTIES = 4;
	private static final int BGND_NAME = 1;
	private static final int BGND_COL = 2;
	private static final int BGND_ROW = 3;

	private static final String MINER_KEY = "miner";
	private static final int MINER_NUM_PROPERTIES = 7;
	private static final int MINER_NAME = 1;
	private static final int MINER_LIMIT = 4;
	private static final int MINER_COL = 2;
	private static final int MINER_ROW = 3;
	private static final int MINER_RATE = 5;
	private static final int MINER_ANIMATION_RATE = 6;

	private static final String OBSTACLE_KEY = "obstacle";
	private static final int OBSTACLE_NUM_PROPERTIES = 4;
	private static final int OBSTACLE_NAME = 1;
	private static final int OBSTACLE_COL = 2;
	private static final int OBSTACLE_ROW = 3;

	private static final String ORE_KEY = "ore";
	private static final int ORE_NUM_PROPERTIES = 5;
	private static final int ORE_NAME = 1;
	private static final int ORE_COL = 2;
	private static final int ORE_ROW = 3;
	private static final int ORE_RATE = 4;

	private static final String SMITH_KEY = "blacksmith";
	private static final int SMITH_NUM_PROPERTIES = 7;
	private static final int SMITH_NAME = 1;
	private static final int SMITH_COL = 2;
	private static final int SMITH_ROW = 3;
	private static final int SMITH_LIMIT = 4;
	private static final int SMITH_RATE = 5;
	private static final int SMITH_REACH = 6;

	private static final String VEIN_KEY = "vein";
	private static final int VEIN_NUM_PROPERTIES = 6;
	private static final int VEIN_NAME = 1;
	private static final int VEIN_RATE = 4;
	private static final int VEIN_COL = 2;
	private static final int VEIN_ROW = 3;
	private static final int VEIN_REACH = 5;
	
	private static final String TENT_KEY = "vein";
	private static final int TENT_NUM_PROPERTIES = 6;
	private static final int TENT_NAME = 1;
	private static final int TENT_RATE = 4;
	private static final int TENT_COL = 2;
	private static final int TENT_ROW = 3;
	private static final int TENT_REACH = 5;

	private static Scanner in;

	public static void load_world(WorldModel world, HashMap<String, List<PImage>> images, String filename, boolean run)
   	{
		try 
		{
			in = new Scanner(new File(filename));
			while (in.hasNextLine())
			{
				String [] properties = in.nextLine().split("\\s");
				if (properties != null && properties.length > 0)
				{
					if (properties[PROPERTY_KEY].equals(BGND_KEY))
					{
						add_background(world, properties, images);
					}
					else
					{
						add_entity(world, properties, images, run);
					}
				}
			}
		}
		catch (FileNotFoundException e)
		{
			System.err.println(e.getMessage());
			System.out.println("Error loading file in loadWorld in SaveLoad.java");
			return;
		}
    }

	public static void add_background(WorldModel world, String[] properties, HashMap<String, List<PImage>> i_store)
   	{
   		if (properties.length >= BGND_NUM_PROPERTIES)
      	{
      		Point pt = new Point(Integer.parseInt(properties[BGND_COL]), Integer.parseInt(properties[BGND_ROW]));
      		String name = properties[BGND_NAME];
      		world.set_background(pt, new Background(name, new Image_store().get_images(i_store, name)));
      	}
    }


	public static void add_entity(WorldModel world, String[] properties, HashMap<String, List<PImage>> i_store, boolean run)
   	{
   		Location new_entity = create_from_properties(properties, i_store);
   		if (new_entity != null)
      	{
      		world.add_entity(new_entity);
      		if (true)
         	{
         		schedule_entity(world, new_entity, i_store);
         	}
        }
    }


	public static Location create_from_properties(String[] properties, HashMap<String, List<PImage>> i_store)
   	{
   		String key = properties[PROPERTY_KEY];
   		if (properties != null)
      		if (key.equals(MINER_KEY))
         	{
         		return create_miner(properties, i_store);
         	}
      		else if (key.equals(VEIN_KEY))
         	{
         		return create_vein(properties, i_store);
         	}
      		else if (key.equals(ORE_KEY))
         	{
         		return create_ore(properties, i_store);
         	}
      		else if (key.equals(SMITH_KEY))
         	{
         		return create_blacksmith(properties, i_store);
         	}
      		else if (key.equals(OBSTACLE_KEY))
         	{
         		return create_obstacle(properties, i_store);
         	}
   		return null;
   	}


	public static Miner create_miner(String[] properties, HashMap<String, List<PImage>> i_store)
   	{
   		if (properties.length == MINER_NUM_PROPERTIES)
      	{
      		Point miner_pt = new Point(Integer.parseInt(properties[MINER_COL]), Integer.parseInt(properties[MINER_ROW]));
      		Miner miner = new MinerNotFull(properties[MINER_NAME],
      					Integer.parseInt(properties[MINER_LIMIT]),
      					miner_pt,
      					Integer.parseInt(properties[MINER_RATE]),
      					new Image_store().get_images(i_store, properties[PROPERTY_KEY]),
         				Integer.parseInt(properties[MINER_ANIMATION_RATE]) * 2);
      		return miner;
      	}
   		return null;
    }


	public static Vein create_vein(String[] properties, HashMap<String, List<PImage>> i_store)
   	{
   		if (properties.length == VEIN_NUM_PROPERTIES)
      	{
      		Point vein_pt = new Point(Integer.parseInt(properties[VEIN_COL]), Integer.parseInt(properties[VEIN_ROW]));
      		Vein vein = new Vein(properties[VEIN_NAME],
      					Integer.parseInt(properties[VEIN_RATE]),
      					vein_pt,
      					new Image_store().get_images(i_store, properties[PROPERTY_KEY]),
         				Integer.parseInt(properties[VEIN_REACH]));
      		return vein;
      	}
      	return null;
    }


	public static Ore create_ore(String[] properties, HashMap<String, List<PImage>> i_store)
   	{
   		if (properties.length == ORE_NUM_PROPERTIES)
      	{
      		Point ore_pt = new Point(Integer.parseInt(properties[ORE_COL]), Integer.parseInt(properties[ORE_ROW]));
      		Ore ore = new Ore(properties[ORE_NAME],
      					ore_pt,
      					new Image_store().get_images(i_store, properties[PROPERTY_KEY]),
         				Integer.parseInt(properties[ORE_RATE]));
      		return ore;
      	}
      	return null;
    }


	public static Blacksmith create_blacksmith(String[] properties, HashMap<String, List<PImage>> i_store)
   	{
   		if (properties.length == SMITH_NUM_PROPERTIES)
      	{
      		Point smith_pt = new Point(Integer.parseInt(properties[SMITH_COL]), Integer.parseInt(properties[SMITH_ROW]));
      		Blacksmith smith = new Blacksmith(properties[SMITH_NAME],
      					smith_pt,
      					new Image_store().get_images(i_store, properties[PROPERTY_KEY]),
         				Integer.parseInt(properties[SMITH_LIMIT]), 
         				Integer.parseInt(properties[SMITH_RATE]),
         				Integer.parseInt(properties[SMITH_REACH]));
      		return smith;
      	}
      	return null;	
    }

	public static Obstacle create_obstacle(String[] properties, HashMap<String, List<PImage>> i_store)
   	{
   		if (properties.length == OBSTACLE_NUM_PROPERTIES)
      	{
      		Point obstacle_pt = new Point(Integer.parseInt(properties[OBSTACLE_COL]), Integer.parseInt(properties[OBSTACLE_ROW]));
      		Obstacle obstacle = new Obstacle(properties[OBSTACLE_NAME],
      						obstacle_pt,
      						new Image_store().get_images(i_store, properties[PROPERTY_KEY]));
      		return obstacle;
        }
		return null;
    }
	
	public static void schedule_entity(WorldModel world, Location entity, HashMap<String, List<PImage>> i_store)
   	{
   		if (entity instanceof MinerNotFull)
      	{
      		((MinerNotFull) entity).schedule_miner(world, System.currentTimeMillis(), i_store);
      	}
   		else if (entity instanceof Vein)
     	{
     		((Vein) entity).schedule_vein(world,  System.currentTimeMillis(), i_store);
     	}
   		else if (entity instanceof Ore)
      	{
      		((Ore) entity).schedule_ore(world, System.currentTimeMillis(), i_store);
      	}
    }
}