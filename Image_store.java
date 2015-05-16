import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import processing.core.*;

import java.util.*;


public class Image_store
	extends PApplet
{
	private static final int COLOR_MASK = 0xffffff;
	public static final String DEFAULT_IMAGE_NAME = "background_default";
	private final int DEFAULT_IMAGE_COLOR = color(128, 128, 128);
	private static final int FILE_IDX = 0;
	private static final int MIN_ARGS = 1;
	private Scanner imageFile;
   
	private static boolean verifyArguments(String [] args)
	{
		return args.length >= MIN_ARGS;
	}
   
	public static void readFile(Scanner in)
	{
	   
	   
		while(in.hasNextLine())
		{
			String [ ] words = in.nextLine().split("//s");
			String type = words[0];
			String
		   
		}
	}

   
	public static PImage load_images(Scanner in, int tile_width, int tile_height)
			throws IOException
	{
		PImage [] image;

		while(in.hasNextLine())
    	  
		{ String [ ] words = in.nextLine().split("//s");
         	for (line : fstr)
         	{
         		process_image_line(images, line);
         	}

         	if (DEFAULT_IMAGE_NAME not in images)
         	{
         		PImage default_image = create_default_image(tile_width, tile_height);
         		images[DEFAULT_IMAGE_NAME] = [default_image];
         	}

         	return images;
		}
	}

	private static PImage setAlpha(PImage img, int maskColor, int alpha)
	{
		int alphaValue = alpha << 24;
	    int nonAlpha = maskColor & COLOR_MASK;
	    img.format = PApplet.ARGB;
	    img.loadPixels();
	    for (int i = 0; i < img.pixels.length; i++)
	    {
	       if ((img.pixels[i] & COLOR_MASK) == nonAlpha)
	       {
	          img.pixels[i] = alphaValue | nonAlpha;
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
			PImage img = setAlpha(loadImage(attrs[1]), color(252, 252, 252), 0);

         if (img != null)
         {
            List<PImage> imgs = get_images_internal(images, key);
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


   public static List<PImage> get_images_internal(HashMap<String, List<PImage>> images, String key)
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


   public static List<PImage> get_images(HashMap<String, List<PImage>> images, String key)
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

