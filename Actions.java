import java.util.*;
import processing.core.*;

public class Actions
{
   public static final int BLOB_RATE_SCALE = 4;
   public static final int BLOB_ANIMATION_RATE_SCALE = 50;
   public static final int BLOB_ANIMATION_MIN = 1;
   public static final int BLOB_ANIMATION_MAX = 3;

   public static final int ORE_CORRUPT_MIN = 20000;
   public static final int ORE_CORRUPT_MAX = 30000;

   public static final int QUAKE_STEPS = 10;
   public static final int QUAKE_DURATION = 1100;
   public static final int QUAKE_ANIMATION_RATE = 100;

   public static final int VEIN_SPAWN_DELAY = 500;
   public static final int VEIN_RATE_MIN = 8000;
   public static final int VEIN_RATE_MAX = 17000;


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
      return (pt1.x == pt2.x && Math.abs(pt1.y - pt2.y) == 1) ||
            (pt1.y == pt2.y && Math.abs(pt1.x - pt2.x) == 1);
   }

   public static Point find_open_around(WorldModel world, Point pt, int distance)
   {
      for (int dx = -distance; dx < distance + 1; dx++)
      {
         for (int dy = -distance; dy < distance + 1; dy++)
         {
            Point new_pt = new Point(pt.x + dx, pt.y + dy);

            if (world.within_bounds(new_pt) && !(world.is_occupied(new_pt)))
            {
               return new_pt;
            }
         }
      }
      return null;
   }

   public static OreBlob create_blob(WorldModel world, String name, Point pt, int rate, long ticks, HashMap<String, List<PImage>> i_store)
   {
      OreBlob blob = new OreBlob(name, pt, rate,
         image_store.get_images(i_store, "blob"),
         ((int)Math.random() * BLOB_ANIMATION_MAX + BLOB_ANIMATION_MIN)
         * BLOB_ANIMATION_RATE_SCALE);
      blob.schedule_blob(world, ticks, i_store);
      return blob;
   }


   public static Ore create_ore(WorldModel world, String name, Point pt, long ticks, HashMap<String, List<PImage>> i_store)
   {
      Ore ore = new Ore(name, pt, image_store.get_images(i_store, "ore"),
         (int)Math.random() * (ORE_CORRUPT_MAX + 1 - ORE_CORRUPT_MIN) + ORE_CORRUPT_MIN);
      ore.schedule_ore(world,  ticks, i_store);

      return ore;
   }

   public static Quake create_quake(WorldModel world, Point pt, long ticks, HashMap<String, List<PImage>> i_store)
   {
      Quake quake = new Quake("quake", pt,
         image_store.get_images(i_store, "quake"), QUAKE_ANIMATION_RATE);
      quake.schedule_quake(world,  ticks);
      return quake;
   }

   public static Vein create_vein(WorldModel world, String name, Point pt, long ticks, HashMap<String, List<PImage>> i_store)
   {
      Vein vein = new Vein("vein" + name,
         (int)Math.random() * (VEIN_RATE_MAX + 1 - VEIN_RATE_MIN) + VEIN_RATE_MIN,
         pt, image_store.get_images(i_store, "vein"));
      return vein;
   }
}