import java.util.*;

import processing.core.*;


public class WorldView 

	
{
	private int view_cols;
	private int view_rows;
	private PApplet screen;
	private WorldModel world;
	private int tile_width;
	private int tile_height;
	private int num_rows;
	private int num_cols;
	private Rectangle viewport;
	
	

    public WorldView(int view_cols, int view_rows, PApplet screen, WorldModel world , int tile_width, int tile_height)
    {
    	this.view_cols = view_cols;
    	this.view_rows = view_rows;
    	this.screen = screen;
    	this.world = world;
    	this.tile_width = tile_width;
    	this.tile_height = tile_height;
    	this.num_cols = world.get_num_cols();
    	this.num_rows = world.get_num_rows();
    	this.viewport = new Rectangle(0,0,view_cols,view_rows);
    }
    
    public void draw_background()
    {
    	for (int y = 0; y < this.viewport.get_height(); y++)
    	{
    		for(int x = 0; x < this.viewport.get_width(); x++)
    		{
    			Point w_pt = viewport_to_world(this.viewport, new Point(x,y));
    			PImage img = this.world.get_background_image(w_pt);
    			screen.image(img,x * this.tile_width , y * this.tile_height);
    					
    		}
    			
    	}
    }
    
    public void draw_entities()
    {
    	for (Location entity: this.world.get_entities() )
    	{
    		if (this.viewport.collidepoint(entity.get_position().x, entity.get_position().y ))
    		{
    			Point v_pt = world_to_viewport(this.viewport, entity.get_position());
    			this.screen.image(entity.get_image(), v_pt.x * this.tile_width, v_pt.y * this.tile_height);
    		}
    	}
    }
    
    public void draw_viewport()
    {
    	this.draw_background();
    	this.draw_entities();
    }
//default????
public void update_view( view_delta)
{
	Rectangle this.viewport = create_shifted_viewport(this.viewport , view_delta, this.num_rows , this.num_cols);
	this.draw_viewport();
	//display.update draw??
	
	
}

public void update_view_tiles( List<Tile> tiles)
{   
	List<Rectangle> rects = new ArrayList<Rectangle>();
	for(Tile tile : tiles)
	{
		if (this.viewport.collidepoint(tile.get_x() , tile.get_y()))
		{
			Point v_pt = world_to_viewport(this.viewport, new Point(tile.get_x(), tile.get_y()));
		            PImage img = this.get_tile_image(v_pt);
		            rects.add(this.update_tile(v_pt, img));
		            //if this.mouse_pt.x == v_pt.x and self.mouse_pt.y == v_pt.y:
		             //  rects.append(self.update_mouse_cursor())

		     // pygame.display.update(rects) draw
		}
	}
}

public Rectangle update_tile(Point view_tile_pt,PImage surface )
{
	int abs_x = view_tile_pt.x * this.tile_width;
	int abs_y = view_tile_pt.y * this.tile_height;

	this.screen.image(surface, abs_x, abs_y);

	return new Rectangle(abs_x, abs_y, this.tile_width, this.tile_height);
	
}

public PImage get_tile_image(Point view_tile_pt)
{
	Point pt = viewport_to_world(this.viewport, view_tile_pt);
	PImage bgnd = this.world.get_background_image(pt);
    Location occupant = this.world.get_tile_occupant(pt);
    if (occupant)
    {
		 PImage img = pygame.Surface((self.tile_width, self.tile_height));
		        img.blit(bgnd, (0, 0))
		        img.blit(occupant.get_image(), (0,0))
		         return img
		        		 }
		      else
		         {
		    	  return bgnd
		         }
}






//Functions
public static Point  viewport_to_world(Rectangle viewport, Point pt)
{

      return new Point(pt.x + viewport.get_left(), pt.y + viewport.get_top()) ;
}

public static Point world_to_viewport(Rectangle viewport, Point pt)
{
      return new Point(pt.x - viewport.get_left(), pt.y - viewport.get_top());
}
public static int  clamp(int v, int low, int high)
{
      return Math.min(high, Math.max(v, low)) ;
}

public static Rectangle create_shifted_viewport(Rectangle viewport, delta, int num_rows, int num_cols)
{
      int new_x = clamp(viewport.get_left() + delta[0], 0, num_cols - viewport.get_width());
      int new_y = clamp(viewport.get_top() + delta[1], 0, num_rows - viewport.get_height());

      return new Rectangle(new_x, new_y, viewport.get_width(), viewport.get_height());


}












}
