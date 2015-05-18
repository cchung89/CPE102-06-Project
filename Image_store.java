import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.io.FileInputStream;
import processing.core.PConstants;

import processing.core.*;

import java.util.*;


public class Image_store
	extends PApplet
{
	private static final int COLOR_MASK = 0xffffff;
	public static final String DEFAULT_IMAGE_NAME = "background_default";
	public final String image_list_file_name;
	public final String source_path;
	private static Scanner imageFile;
	private HashMap<String, List<PImage>> images = new HashMap<String, List<PImage>>();
	PApplet processor;
   
	public Image_store(Main processor, String source_path, String image_list_file_name)
	{
		this.processor = processor;
		this.source_path = source_path;
		this.image_list_file_name = image_list_file_name;
	}
   
	private PImage create_default_image(int tile_width, int tile_height)
	{
		return processor.loadImage(source_path + "none.bmp");
	}
	
	public HashMap<String, List<PImage>> load_images(int tile_width, int tile_height)
	{
		
		HashMap<String, List<PImage>> images = new HashMap<String, List<PImage>>();
		try
		{
			imageFile = new Scanner(new FileInputStream(image_list_file_name));
			while(imageFile.hasNextLine())	  
			{
				String line = imageFile.nextLine();
				process_image_line(line);
			}
		}
		catch (FileNotFoundException e)
		{
			System.err.println(e.getMessage());
		}
		
        if (!(images.containsKey(DEFAULT_IMAGE_NAME)))
        {
        	List<PImage> default_list = new ArrayList<PImage>();
        	default_list.add(create_default_image(tile_width, tile_height));
         	images.put(DEFAULT_IMAGE_NAME, default_list);
        }
        return images;
	}

	private static PImage setAlpha(PImage img, int maskColor, int alpha)
	{
		int alphaValue = alpha << 24;
	    img.format = PConstants.ARGB;
	    img.loadPixels();
	    for (int i = 0; i < img.pixels.length; i++)
	    {
	       if ((img.pixels[i] & COLOR_MASK) == maskColor)
	       {
	          img.pixels[i] = alphaValue | maskColor;
	       }
	    }
	    img.updatePixels();
	    return img;
	}
	
	public void process_image_line(String line)
	{
		String[] attrs = line.split(" ");
		if (attrs.length >= 2)
		{
			String key = attrs[0];
			PImage img = setAlpha(processor.loadImage(attrs[1]), processor.color(252, 252, 252), 0);
			img = setAlpha(img, processor.color(201, 26, 26), 0);
			if (key.compareTo("blob") == 0 || key.compareTo("quake") == 0)
			{
				img = setAlpha(img, processor.color(255, 255, 255), 0);
			}
			if (img != null)
			{
				List<PImage> imgs = get_images_internal(key);
				imgs.add(img);
				images.put(key, imgs);
         
				if (attrs.length == 6)
				{
					String r = attrs[2];
					String g = attrs[3];
					String b = attrs[4];
					String a = attrs[5];
				}
			}
		}
	}


   public List<PImage> get_images_internal(String key)
   {
      if (images.containsKey(key))
      {
         return images.get(key);
      }
      else
      {
         List<PImage> empty = new ArrayList<PImage>();
         return empty;
      }
   }


   public List<PImage> get_images(String key)
   {
      if (images.containsKey(key))
      {
         return images.get(key);
      }
      else
      {
         return images.get(DEFAULT_IMAGE_NAME);
      }
   }
}

