import java.io.*;
import java.util.Scanner;
import processing.core.*;
import java.util.*;


public class Image_store
{
   //private static final String DEFAULT_IMAGE_NAME = 'background_default';
   //private static final DEFAULT_IMAGE_COLOR = (128, 128, 128, 0);
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

   
   public static PImage load_images(Scanner in, int tile_width, int tile_height):
   {  PImage [] image;

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

   public static void process_image_line(List<PImage> images, String line)
   {
      List<String> attrs = new ArrayList<String> line.split("//s");
      if (attrs.size() >= 2)
      {
         String key = attrs.get(0);
         PImage img = pygame.image.load(attrs[1]).convert();     

         if (img)
         {
            List<PImage> imgs = get_images_internal(images, key);
            imgs.add(img);
            images[key] = imgs;
         
            if (attrs.size() == 6)
            {
               int r = String.valueOf(attrs[2]);
               int g = String.valueOf(attrs[3]);
               int b = String.valueOf(attrs[4]);
               int a = String.valueOf(attrs[5]);
               img.set_colorkey(pygame.Color(r, g, b, a));
            }
         }
      }
   }


   public static List<PImage> get_images_internal(List<PImage> images, String key)
   {
      if (key : images)
      {
         return images[key];
      }
      else
      {
         List<PImage> empty = new ArrayList<PImage>();
         return empty;
      }
   }


   public static List<PImage> get_images(List<PImage> images, String key)
   {
      if (key : images)
      {
         return images[key];
      }
      else
      {
         return images[DEFAULT_IMAGE_NAME];
      }
}

