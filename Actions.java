public class Actions
{
   private static final int BLOB_RATE_SCALE = 4;
   private static final int BLOB_ANIMATION_RATE_SCALE = 50;
   private static final int BLOB_ANIMATION_MIN = 1;
   private static final int BLOB_ANIMATION_MAX = 3;

   private static final int ORE_CORRUPT_MIN = 20000;
   private static final int ORE_CORRUPT_MAX = 30000;

   private static final int QUAKE_STEPS = 10;
   private static final int QUAKE_DURATION = 1100;
   private static final int QUAKE_ANIMATION_RATE = 100;

   private static final int VEIN_SPAWN_DELAY = 500;
   private static final int VEIN_RATE_MIN = 8000;
   private static final int VEIN_RATE_MAX = 17000;


   public static int sign(int x)
   {
      if (x < 0)
      {
         return -1;
      }
      else if (x > 0)
      {
         return 1;
      }
      else
      {
         return 0;
      }
   }

   public static boolean adjacent(Point pt1, Point pt2)
   {
      return (pt1.x == pt2.x && abs(pt1.y - pt2.y) == 1) ||
            (pt1.y == pt2.y && abs(pt1.x - pt2.x) == 1);
   }

   public static Point find_open_around(WorldModel world, Point pt, int distance)
   {
      for (int dx = -distance; dx < distance + 1; x++)
      {
         for (int dy = -distance; dy < distance + 1; y++)
         {
            Point new_pt = new Point(pt.x + dx, pt.y + dy);

            if (within_bounds(new_pt) && !(is_occupied(new_pt)))
            {
               return new_pt;
            }
         }
      }
      return null;
   }

   public static OreBlob create_blob(WorldModel world, String name, Point pt, int rate, int ticks, HashMap<String, List<PImage>> i_store)
   {
      Entity blob = new OreBlob(name, pt, rate,
         image_store.get_images(i_store, 'blob'),
         random(BLOB_ANIMATION_MIN, BLOB_ANIMATION_MAX)
         * BLOB_ANIMATION_RATE_SCALE);
      blob.schedule_blob(world, ticks, i_store);
      return blob;
   }


   public static Ore create_ore(WorldModel world, String name, Point pt, int ticks, HashMap<String, List<PImage>> i_store)
   {
      entity ore = new Ore(name, pt, image_store.get_images(i_store, 'ore'),
         random(ORE_CORRUPT_MIN, ORE_CORRUPT_MAX));
      ore.schedule_ore(world,  ticks, i_store);

      return ore;
   }

   public static Quake create_quake(WorldModel world, Point pt, int ticks, HashMap<String, List<PImage>> i_store)
   {
      Entity quake = new Quake("quake", pt,
         image_store.get_images(i_store, 'quake'), QUAKE_ANIMATION_RATE);
      quake.schedule_quake(world,  ticks);
      return quake;
   }

   public static Vein create_vein(WorldModel world, String name, Point pt, int ticks, HashMap<String, List<PImage>> i_store)
   {
      Entity vein = new Vein("vein" + name,
         random(VEIN_RATE_MIN, VEIN_RATE_MAX),
         pt, image_store.get_images(i_store, 'vein'));
      return vein;
   }
}