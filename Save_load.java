public class Save_load
{
	private static final int PROPERTY_KEY = 0;

	private static final String BGND_KEY = 'background';
	private static final int BGND_NUM_PROPERTIES = 4;
	private static final int BGND_NAME = 1;
	private static final int BGND_COL = 2;
	private static final int BGND_ROW = 3;

	private static final String MINER_KEY = 'miner';
	private static final int MINER_NUM_PROPERTIES = 7;
	private static final int MINER_NAME = 1;
	private static final int MINER_LIMIT = 4;
	private static final int MINER_COL = 2;
	private static final int MINER_ROW = 3;
	private static final int MINER_RATE = 5;
	private static final int MINER_ANIMATION_RATE = 6;

	private static final String OBSTACLE_KEY = 'obstacle';
	private static final int OBSTACLE_NUM_PROPERTIES = 4;
	private static final int OBSTACLE_NAME = 1;
	private static final int OBSTACLE_COL = 2;
	private static final int OBSTACLE_ROW = 3;

	private static final String ORE_KEY = 'ore';
	private static final int ORE_NUM_PROPERTIES = 5;
	private static final int ORE_NAME = 1;
	private static final int ORE_COL = 2;
	private static final int ORE_ROW = 3;
	private static final int ORE_RATE = 4;

	private static final String SMITH_KEY = 'blacksmith';
	private static final int SMITH_NUM_PROPERTIES = 7;
	private static final int SMITH_NAME = 1;
	private static final int SMITH_COL = 2;
	private static final int SMITH_ROW = 3;
	private static final int SMITH_LIMIT = 4;
	private static final int SMITH_RATE = 5;
	private static final int SMITH_REACH = 6;

	private static final String VEIN_KEY = 'vein';
	private static final int VEIN_NUM_PROPERTIES = 6;
	private static final int VEIN_NAME = 1;
	private static final int VEIN_RATE = 4;
	private static final int VEIN_COL = 2;
	private static final int VEIN_ROW = 3;
	private static final int VEIN_REACH = 5;


	public static void save_world(WorldModel world, File file)
   	{
   		save_entities(world, file);
   		save_background(world, file);
   	}

	public static void save_entities(WorldModel world, File file)
   	{
   		for (Entity entity : world.get_entities())
      	{
      		file.write(entity.entity_string() + 'n');
      	}
    }


	public static void save_background(WorldModel world, File file)
   	{
   		for (int row = 0; row < world.num_rows; row++)
      	{
      		for (int col = 0; col < world.num_cols; col++)
         	{
         		Point pt = new Point(col, row);
         		file.write('background ' +
           				entities.get_name(
               			world.get_background(pt)) +
            			' ' + str(col) + ' ' + str(row) + 'n');
         	}
        }
    }


	public static void load_world(WorldModel world, List<PImage> images, File file, boolean run)
   	{
   		boolean run = false;
   		for (line : file)
      	{
      		List<String> properties = new ArrayList<String> line.split();
      		if (properties)
         	{
         		if (properties[PROPERTY_KEY] == BGND_KEY)
            	{
            		add_background(world, properties, images);
            	}
         		else:
            	{
            		add_entity(world, properties, images, run);
            	}
            }
        }
    }

	public static void add_background(WorldModel world, List<String> properties, HashMap<String, List<PImage>> i_store)
   	{
   		if (properties.size() >= BGND_NUM_PROPERTIES)
      	{
      		Point pt = new Point(Integer.toString(properties[BGND_COL]), Interger.toString(properties[BGND_ROW]));
      		String name = properties[BGND_NAME];
      		world.set_background(pt, Background(name, image_store.get_images(i_store, name)));
      	}
    }


	public static void add_entity(WorldModel world, List<String> properties, HashMap<String, List<PImage>> i_store, boolean run)
   	{
   		Entity new_entity = create_from_properties(properties, i_store)
   		if (new_entity)
      	{
      		world.add_entity(new_entity);
      		if (run)
         	{
         		schedule_entity(world, new_entity, i_store);
         	}
        }
    }


	public static Entity create_from_properties(List<String> properties, HashMap<String, List<PImage>> i_store)
   	{
   		String key = properties[PROPERTY_KEY];
   		if (properties)
      		if (key == MINER_KEY)
         	{
         		return create_miner(properties, i_store);
         	}
      		else if (key == VEIN_KEY)
         	{
         		return create_vein(properties, i_store);
         	}
      		else if (key == ORE_KEY)
         	{
         		return create_ore(properties, i_store);
         	}
      		else if (key == SMITH_KEY)
         	{
         		return create_blacksmith(properties, i_store);
         	}
      		else if (key == OBSTACLE_KEY)
         	{
         		return create_obstacle(properties, i_store);
         	}
   		return null;
   	}


	public static Miner create_miner(List<String> properties, HashMap<String, List<PImage>>i_store):
   	{
   		if (properties.size() == MINER_NUM_PROPERTIES)
      	{
      		Point miner_pt = new Point(String.valueOf(properties[MINER_COL]), String.valueOf(properties[MINER_ROW]));
      		Entity miner = new MinerNotFull(properties[MINER_NAME],
         				String.valueOf(properties[MINER_LIMIT]),
         				miner_pt,
         				String.valueOf(properties[MINER_RATE]),
         				image_store.get_images(i_store, properties[PROPERTY_KEY]),
         				String.valueOf(properties[MINER_ANIMATION_RATE]));
      		return miner
      	}
   		else
      	{
      		return null;
      	}
    }


	public static Vein create_vein(List<String> properties, HashMap<String, List<PImage>> i_store)
   	{
   		if (properties.size() == VEIN_NUM_PROPERTIES)
      	{
      		Point vein_pt = new Point(String.valueOf(properties[VEIN_COL]), String.valueOf(properties[VEIN_ROW]));
      		Entity vein = new Vein(properties[VEIN_NAME], String.valueOf(properties[VEIN_RATE]),
         				vein_pt,
         				image_store.get_images(i_store, properties[PROPERTY_KEY]),
         				String.valueOf(properties[VEIN_REACH]));
      		return vein;
      	}
   		else
      	{
      		return null;
      	}
    }


	public static Ore create_ore(List<String> properties, HashMap<String, List<PImage>> i_store)
   	{
   		if (properties.size() == ORE_NUM_PROPERTIES)
      	{
      		Point ore_pt = new Point(String.valueOf(properties[ORE_COL]), String.valueOf(properties[ORE_ROW]));
      		Entity ore = new Ore(properties[ORE_NAME],
         				ore_pt,
         				image_store.get_images(i_store, properties[PROPERTY_KEY]),
         				String.valueOf(properties[ORE_RATE]));
      		return ore;
      	}
      	else
      	{
      		return null;
      	}
    }


	public static Blacksmith create_blacksmith(List<String> properties, HashMap<String, List<PImage>> i_store)
   	{
   		if (properties.size() == SMITH_NUM_PROPERTIES)
      	{
      		Point smith_pt = new Point(String.valueOf(properties[ORE_COL]), String.valueOf(properties[ORE_ROW]));
      		Entity smith = new Blacksmith(properties[SMITH_NAME],
         				smith_pt,
         				image_store.get_images(i_store, properties[PROPERTY_KEY]),
         				String.valueOf(properties[SMITH_LIMIT]), String.valueOf(properties[SMITH_RATE]),
         				String.valueOf(properties[SMITH_REACH]));
      		return smith;
      	}
   		else
      	{
      		return null;
      	}
    }

	public static Obstacle create_obstacle(List<String> properties, HashMap<String, List<PImage>> i_store)
   	{
   		if (properties.size() == OBSTACLE_NUM_PROPERTIES)
      	{
      		Point obstacle_pt = new Point(String.valueOf(properties[OBSTACLE_COL]), String.valueOf(properties[OBSTACLE_ROW]));
      		Entity obstacle = new Obstacle(properties[OBSTACLE_NAME],
         					obstacle_pt,
         					image_store.get_images(i_store, properties[PROPERTY_KEY]));
        }
   		else
      	{
      		return null;
      	}
    }

	public static void schedule_entity(WorldModel world, Entity entity, HashMap<String, List<PImage>> i_store)
   	{
   		if (MinerNotFull.isInstance(entity))
      	{
      		entity.schedule_miner(world, 0, i_store);
      	}
   		else if (Vein.isInstance(entity))
     	{
     		entity.schedule_vein(world,  0, i_store);
     	}
   		else if (Ore.isInstance(entity))
      	{
      		entity.schedule_ore(world, 0, i_store);
      	}
    }
}