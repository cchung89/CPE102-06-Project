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
	
	public HashMap<String, List<PImage>> load_images(String filename, int tile_width, int tile_height)
	{
		Scanner imageFile;
		try
		{
			imageFile = new Scanner(new File(filename));
			int linecount = count_lines(new File(filename));
		    HashMap<String, List<PImage>> images = new HashMap<String, List<PImage>>(linecount);
			while(imageFile.hasNextLine())	  
			{
				String line = imageFile.nextLine();
				process_image_line(images, line);
			}
			return images;
		}
		catch (FileNotFoundException e)
		{
			System.err.println(e.getMessage());
		}
		return null;
	}
	
	public int count_lines(File file)
	   {
	      int count = 0;
	      Scanner lines;
	      try
	      {
	         lines = new Scanner(file);
	      }
	      catch(FileNotFoundException e)
	      {
	         System.out.println(e);
	         return -1;
	      }
	      while(lines.hasNextLine())
	      {
	         count++;
	         lines.nextLine();
	      }
	      return count;
	   }

	private PImage setAlpha(PImage img, int maskColor, int alpha)
	{
		int alphaValue = alpha << 24;
		int non_alpha = maskColor & COLOR_MASK;
	    img.format = PApplet.ARGB;
	    img.loadPixels();
	    for (int i = 0; i < img.pixels.length; i++)
	    {
	       if ((img.pixels[i] & COLOR_MASK) == non_alpha)
	       {
	          img.pixels[i] = alphaValue | non_alpha;
	       }
	    }
	    img.updatePixels();
	    return img;
	}
	
	public void process_image_line(HashMap<String, List<PImage>> images, String line)
	{
		String[] attrs = line.split(" ");
		if (attrs.length >= 2)
		{
			String key = attrs[0];
			PImage img;
			if (attrs.length == 2)
			{
				img = loadImage(attrs[1]);
			}
			else
			{
				img = setAlpha(loadImage(attrs[1]), color(Integer.parseInt(attrs[2]),Integer.parseInt(attrs[3]),
			               Integer.parseInt(attrs[4])), Integer.parseInt(attrs[5]));
			}
			if (img != null)
			{
				List<PImage> imgs = get_images_internal(images, key);
				imgs.add(img);
				images.put(key, imgs);
			}
		}
	}


   public List<PImage> get_images_internal(HashMap<String, List<PImage>> images, String key)
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


   public List<PImage> get_images(HashMap<String, List<PImage>> images, String key)
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

