import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.io.FileInputStream;

import processing.core.*;

import java.util.*;


public class Image_store
	extends PApplet
{
	private static final int COLOR_MASK = 0xffffff;
	public static final String DEFAULT_IMAGE_NAME = "background_default";
	private static final String IMAGE_LIST_FILE_NAME = "imagelist";
	private final int DEFAULT_IMAGE_COLOR = color(128, 128, 128);
	private static final int FILE_IDX = 0;
	private static final int MIN_ARGS = 1;
	private static PApplet processor = new PApplet();
	private static final String source_path = "images";
   
	private static boolean verifyArguments(String [] args)
	{
		return args.length >= MIN_ARGS;
	}
   
	private static PImage create_default_image(int tile_width, int tile_height)
	{
		return processor.loadImage(source_path + "/" + "none.bmp");
	}
	
	public static HashMap<String, List<PImage>> load_images(int tile_width, int tile_height)
	{
		HashMap<String, List<PImage>> images = new HashMap<String, List<PImage>>();
		try
		{
			Scanner imageFile = new Scanner(new FileInputStream(IMAGE_LIST_FILE_NAME));
			while(imageFile.hasNextLine())	  
			{ 
				String[] line = imageFile.nextLine().split("\\s");
				if (line.length >= 2)
				{
					if (!(images.containsKey(line[0])))
					{
						images.put(line[0], new ArrayList<PImage>());
					}
					images.get(line[0]).add(processor.loadImage(line[1]));
				}
			}
		}
		catch (FileNotFoundException e)
		{
			System.err.println(e.getMessage());
		}
		
        if (!(images.containsKey(DEFAULT_IMAGE_NAME)))
        {
         	images.put(DEFAULT_IMAGE_NAME, new ArrayList<PImage>());
         	images.get(DEFAULT_IMAGE_NAME).add(create_default_image(tile_width, tile_height));
        }
        return images;
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

