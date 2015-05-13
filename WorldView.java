import processing.core.*;


public class WorldView 


extends PApplet
	
{
	private int view_cols;
	private int view_rows;
	private screen;
	private WorldModel world;
	private int tile_width;
	private int tile_height;
	private int num_rows;
	private int num_cols;
	private PApplet viewport;
	

    public WorldView(int view_cols, int view_rows, screen, WorldModel world , int tile_width, int tile_height)
    {
    	this.view_cols = view_cols;
    	this.view_rows = view_rows;
    	this.world = world;
    	this.tile_width = tile_width;
    	this.tile_height = tile_height;
    	this.num_cols = world.num_cols;
    	this.num_rows = world.num_rows;
    	this.viewport = viewport;
    }
    
    public void draw_background()
    {
    	for (int y = 0; y < this.view)
    }
}
