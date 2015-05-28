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
   
   public static Node[][] node_grid;
   
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
   
   public static int heuristic_cost(Point start, Point goal) // Manhattan distance
   {	   
	   int distance = Math.abs(goal.x - start.x) + Math.abs(goal.y-start.y);
	   return distance;
   }
   
   public static Node lowest_f(List<Node> open_set)
   {
	  Node lowest = open_set.get(0);
	  
	  for (int i = 1; i < open_set.size(); i++)
	  {
		  Node current = open_set.get(i);
		  if (lowest.get_f_score() > current.get_f_score())
		  {
			  lowest = current;
		  }
	  }
	  return lowest;
   }
   
   public static List<Node> neighbor_nodes(Node current, Node[][] node_grid, WorldModel world, Class dest_entity)
   {
	   List<Node> neighbors = new ArrayList<Node>();
	   
	   Point current_position = current.get_point();
	   int current_x = current_position.x;
	   int current_y = current_position.y;
	   
	   Point up = new Point(current_x, current_y - 1);
	   Point right = new Point(current_x + 1, current_y);
	   Point down = new Point(current_x, current_y + 1);
	   Point left = new Point(current_x - 1, current_y);
	   
	   
	   //if (world.within_bounds(up) && (world.get_tile_occupant(up) == null || dest_entity.isInstance(world.get_tile_occupant(up))))
	   if (neighbor_check(up, world, dest_entity, world.get_tile_occupant(up)))
	   {
		   neighbors.add(node_grid[up.y][up.x]);
	   }
		
	   //if (world.within_bounds(right) && (world.get_tile_occupant(right) == null || dest_entity.isInstance(world.get_tile_occupant(right))))
	   if (neighbor_check(right, world, dest_entity, world.get_tile_occupant(right)))
	   {
		   neighbors.add(node_grid[right.y][right.x]);
	   }
		
	   //if (world.within_bounds(down) && (world.get_tile_occupant(down) == null || dest_entity.isInstance(world.get_tile_occupant(down))))
	   if (neighbor_check(down, world, dest_entity, world.get_tile_occupant(down)))
	   {
		   neighbors.add(node_grid[down.y][down.x]);
	   }
	   
	   //if (world.within_bounds(left) && (world.get_tile_occupant(left) == null || dest_entity.isInstance(world.get_tile_occupant(left))))
	   if (neighbor_check(left, world, dest_entity, world.get_tile_occupant(left)))
	   {
		   neighbors.add(node_grid[left.y][left.x]);
	   }
	   
	   return neighbors;
   }
   
   public static boolean neighbor_check(Point pt, WorldModel world, Class dest_entity, Location entity)
   {
	   Location neighbor = world.get_tile_occupant(pt);
	   if (entity instanceof OreBlob)
	   {
		   return world.within_bounds(pt) && (neighbor == null || neighbor instanceof Ore || dest_entity.isInstance(neighbor));
	   }
	   return world.within_bounds(pt) && (neighbor == null || dest_entity.isInstance(neighbor));
   }
   
   public static List<Point> a_star(Point start, Point goal, WorldModel world, Class dest_entity)
   {
	   List<Node> closed_set = new LinkedList<Node>();
	   List<Node> open_set = new ArrayList<Node>();
	   node_grid = new Node[world.num_rows][world.num_cols];
	   
	   for (int col = 0; col < world.num_cols; col++)
	   {
		   for (int row = 0; row < world.num_rows; row++)
		   {
			   Point pt = new Point(col, row);
			   node_grid[row][col] = new Node(pt);
		   }
	   }
	   
	   node_grid[start.y][start.x].set_g_score(0);
	   node_grid[start.y][start.x].set_f_score(node_grid[start.y][start.x].get_g_score() + heuristic_cost(start, goal));
	   open_set.add(node_grid[start.y][start.x]);
	   
	   while (open_set.size() > 0)
	   {
		   Node current = lowest_f(open_set);
		   if (current.get_point().x == goal.x && current.get_point().y == goal.y)
		   {
			   return reconstruct_path(current);
		   }
		   
		   open_set.remove(current);
		   closed_set.add(current);
		   
		   for (Node neighbor : neighbor_nodes(current, node_grid, world, dest_entity))
		   {
			   if (closed_set.contains(neighbor))
			   {
				   continue;
			   }
			   int tentative_g_score = current.get_g_score() + 1;
			   
			   if (!(open_set.contains(neighbor)) || tentative_g_score < neighbor.get_g_score())			   {
				   neighbor.set_came_from(current);
				   neighbor.set_g_score(tentative_g_score);
				   neighbor.set_f_score(neighbor.get_g_score() + heuristic_cost(neighbor.get_point(), goal));
				   if (!(open_set.contains(neighbor)))
				   {
					   open_set.add(neighbor);
				   }
			   }
		   }
	   }
	   return null;
   }
   
   public static List<Point> reconstruct_path(Node current)
   {
	   List<Point> total_path = new ArrayList<Point>();
	   total_path.add(current.get_point());
	   while (current.get_came_from() != null)
	   {
		   total_path.add(current.get_came_from().get_point());
		   current = current.get_came_from();
	   }
	   
	   return total_path;
   }
   
   public static OreBlob create_blob(WorldModel world, String name, Point pt, int rate, long ticks, HashMap<String, List<PImage>> i_store)
   {
      OreBlob blob = new OreBlob(name, pt, rate, new Image_store().get_images(i_store, "blob"), 
    		  (int)(Math.random() * BLOB_ANIMATION_MAX + BLOB_ANIMATION_MIN)
    		  * BLOB_ANIMATION_RATE_SCALE);
      blob.schedule_blob(world, ticks, i_store);
      return blob;
   }


   public static Ore create_ore(WorldModel world, String name, Point pt, long ticks, HashMap<String, List<PImage>> i_store)
   {
      Ore ore = new Ore(name, pt, new Image_store().get_images(i_store, "ore"), 
         (int)(Math.random() * (ORE_CORRUPT_MAX + 1 - ORE_CORRUPT_MIN) + ORE_CORRUPT_MIN));
      ore.schedule_ore(world,  ticks, i_store);

      return ore;
   }

   public static Quake create_quake(WorldModel world, Point pt, long ticks, HashMap<String, List<PImage>> i_store)
   {
      Quake quake = new Quake("quake", pt, new Image_store().get_images(i_store, "quake"), 
          QUAKE_ANIMATION_RATE);
      quake.schedule_quake(world,  ticks);
      return quake;
   }

   public static Vein create_vein(WorldModel world, String name, Point pt, long ticks, HashMap<String, List<PImage>> i_store)
   {
      Vein vein = new Vein("vein" + name, 
    		  		(int)(Math.random() * (VEIN_RATE_MAX + 1 - VEIN_RATE_MIN) + VEIN_RATE_MIN),
    		  		pt, new Image_store().get_images(i_store, "vein"));
      return vein;
   }
}