import entities
import worldmodel
import pygame
import math
import random
import point
import image_store

BLOB_RATE_SCALE = 4
BLOB_ANIMATION_RATE_SCALE = 50
BLOB_ANIMATION_MIN = 1
BLOB_ANIMATION_MAX = 3

ORE_CORRUPT_MIN = 20000
ORE_CORRUPT_MAX = 30000

QUAKE_STEPS = 10
QUAKE_DURATION = 1100
QUAKE_ANIMATION_RATE = 100

VEIN_SPAWN_DELAY = 500
VEIN_RATE_MIN = 8000
VEIN_RATE_MAX = 17000


def sign(x):
   if x < 0:
      return -1
   elif x > 0:
      return 1
   else:
      return 0


def adjacent(pt1, pt2):
   return ((pt1.x == pt2.x and abs(pt1.y - pt2.y) == 1) or
      (pt1.y == pt2.y and abs(pt1.x - pt2.x) == 1))

   


def find_open_around(world, pt, distance):
   for dy in range(-distance, distance + 1):
      for dx in range(-distance, distance + 1):
         new_pt = point.Point(pt.x + dx, pt.y + dy)

         if (world.within_bounds( new_pt) and
            (not world.is_occupied( new_pt))):
            return new_pt

   return None





def create_blob(world, name, pt, rate, ticks, i_store):
   blob = entities.OreBlob(name, pt, rate,
      image_store.get_images(i_store, 'blob'),
      random.randint(BLOB_ANIMATION_MIN, BLOB_ANIMATION_MAX)
      * BLOB_ANIMATION_RATE_SCALE)
   blob.schedule_blob(world, ticks, i_store)
   return blob


def create_ore(world, name, pt, ticks, i_store):
   ore = entities.Ore(name, pt, image_store.get_images(i_store, 'ore'),
      random.randint(ORE_CORRUPT_MIN, ORE_CORRUPT_MAX))
   ore.schedule_ore(world,  ticks, i_store)

   return ore



def create_quake(world, pt, ticks, i_store):
   quake = entities.Quake("quake", pt,
      image_store.get_images(i_store, 'quake'), QUAKE_ANIMATION_RATE)
   quake.schedule_quake(world,  ticks)
   return quake

    


def create_vein(world, name, pt, ticks, i_store):
   vein = entities.Vein("vein" + name,
      random.randint(VEIN_RATE_MIN, VEIN_RATE_MAX),
      pt, image_store.get_images(i_store, 'vein'))
   return vein


  


