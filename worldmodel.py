import entities
import pygame
import ordered_list
import actions
import occ_grid
import point
import save_load

class WorldModel:
   def __init__(self, num_rows, num_cols, background):
      self.background = occ_grid.Grid(num_cols, num_rows, background)
      self.num_rows = num_rows
      self.num_cols = num_cols
      self.occupancy = occ_grid.Grid(num_cols, num_rows, None)
      self.entities = []
      self.action_queue = ordered_list.OrderedList()

   def within_bounds(self, pt):
      return (pt.x >= 0 and pt.x < self.num_cols and
         pt.y >= 0 and pt.y < self.num_rows)

   def is_occupied(self, pt):
      return (self.within_bounds(pt) and
         occ_grid.get_cell(self.occupancy, pt) != None)

   def find_nearest(self, pt, type):
      oftype = [(e, distance_sq(pt, entities.get_position(e)))
         for e in self.entities if isinstance(e, type)]

      return nearest_entity(oftype)

   def add_entity(self, entity):
      pt = entities.get_position(entity)
      if self.within_bounds(pt):
         old_entity = occ_grid.Grid.get_cell(self.occupancy, pt)
         if old_entity != None:
            entities.clear_pending_actions(old_entity)
         occ_grid.Grid.set_cell(self.occupancy, pt, entity)
         self.entities.append(entity)

   def move_entity(self, entity, pt):
      tiles = []
      if self.within_bounds(pt):
         old_pt = entities.get_position(entity)
         occ_grid.set_cell(self.occupancy, old_pt, None)
         tiles.append(old_pt)
         occ_grid.set_cell(self.occupancy, pt, entity)
         tiles.append(pt)
         entities.set_position(entity, pt)

      return tiles

   def remove_entity(self, entity):
      self.remove_entity_at(entities.get_position(entity))

   def remove_entity_at(self, pt):
      if (self.within_bounds(pt) and
         occ_grid.get_cell(self.occupancy, pt) != None):
         entity = occ_grid.get_cell(elf.occupancy, pt)
         entities.set_position(entity, point.Point(-1, -1))
         self.entities.remove(entity)
         occ_grid.set_cell(self.occupancy, pt, None)

   def schedule_action(self, action, time):
      self.action_queue.insert(action, time)

   def unschedule_action(self, action):
      self.action_queue.remove(action)

   def update_on_time(self, ticks):
      tiles = []

      next = self.action_queue.head()
      while next and next.ord < ticks:
         self.action_queue.pop()
         tiles.extend(next.item(ticks))  # invoke action function
         next = self.action_queue.head()

      return tiles

   def get_background_image(self, pt):
      if self.within_bounds(pt):
         return entities.get_image(occ_grid.get_cell(self.background, pt))


   def get_background(self, pt):
      if self.within_bounds(pt):
         return occ_grid.get_cell(self.background, pt)


   def set_background(self, pt, bgnd):
      if self.within_bounds(pt):
         occ_grid.set_cell(self.background, pt, bgnd)


   def get_tile_occupant(self, pt):
      if self.within_bounds(pt):
         return occ_grid.get_cell(self.occupancy, pt)

   def get_entities(self):
      return self.entities

   """#save_load.py
   def add_background(self, properties, i_store):
      BGND_NUM_PROPERTIES = 4
      BGND_NAME = 1
      BGND_COL = 2
      BGND_ROW = 3
      if len(properties) >= BGND_NUM_PROPERTIES:
         pt = point.Point(int(properties[BGND_COL]), int(properties[BGND_ROW]))
         name = properties[BGND_NAME]
         self.set_background(pt,
            entities.Background(name, image_store.get_images(i_store, name)))

   def load_add_entity(self, properties, i_store, run):
      new_entity = save_load.create_from_properties(properties, i_store)
      if new_entity:
         self.add_entity(new_entity)
         if run:
            self.schedule_entity(new_entity, i_store)

   def schedule_entity(self, entity, i_store):
      if isinstance(entity, entities.MinerNotFull):
         self.schedule_miner(entity, 0, i_store)
      elif isinstance(entity, entities.Vein):
         self.schedule_vein(entity, 0, i_store)
      elif isinstance(entity, entities.Ore):
         self.schedule_ore(entity, 0, i_store) """
        
   #ACTIONS.PY

   

#def within_bounds(world, pt):
#   return (pt.x >= 0 and pt.x < world.num_cols and
#      pt.y >= 0 and pt.y < world.num_rows)


#def is_occupied(world, pt):
#   return (within_bounds(world, pt) and
#      occ_grid.get_cell(world.occupancy, pt) != None)


def nearest_entity(entity_dists):
   if len(entity_dists) > 0:
      pair = entity_dists[0]
      for other in entity_dists:
         if other[1] < pair[1]:
            pair = other
      nearest = pair[0]
   else:
      nearest = None

   return nearest


def distance_sq(p1, p2):
   return (p1.x - p2.x)**2 + (p1.y - p2.y)**2


"""def find_nearest(world, pt, type):
   oftype = [(e, distance_sq(pt, entities.get_positio(e)))
      for e in world.entities if isinstance(e, type)]n

   return nearest_entity(oftype)


def add_entity(world, entity):
   pt = entities.get_position(entity)
   if within_bounds(world, pt):
      old_entity = occ_grid.get_cell(world.occupancy, pt)
      if old_entity != None:
         entities.clear_pending_actions(old_entity)
      occ_grid.set_cell(world.occupancy, pt, entity)
      world.entities.append(entity)


def move_entity(world, entity, pt):
   tiles = []
   if within_bounds(world, pt):
      old_pt = entities.get_position(entity)
      occ_grid.set_cell(world.occupancy, old_pt, None)
      tiles.append(old_pt)
      occ_grid.set_cell(world.occupancy, pt, entity)
      tiles.append(pt)
      entities.set_position(entity, pt)

   return tiles


def remove_entity(world, entity):
   remove_entity_at(world, entities.get_position(entity))


def remove_entity_at(world, pt):
   if (within_bounds(world, pt) and
      occ_grid.get_cell(world.occupancy, pt) != None):
      entity = occ_grid.get_cell(world.occupancy, pt)
      entities.set_position(entity, point.Point(-1, -1))
      world.entities.remove(entity)
      occ_grid.set_cell(world.occupancy, pt, None)


def schedule_action(world, action, time):
   world.action_queue.insert(action, time)


def unschedule_action(world, action):
   world.action_queue.remove(action)


def update_on_time(world, ticks):
   tiles = []

   next = world.action_queue.head()
   while next and next.ord < ticks:
      world.action_queue.pop()
      tiles.extend(next.item(ticks))  # invoke action function
      next = world.action_queue.head()

   return tiles


def get_background_image(world, pt):
   if within_bounds(world, pt):
      return entities.get_image(occ_grid.get_cell(world.background, pt))


def get_background(world, pt):
   if within_bounds(world, pt):
      return occ_grid.get_cell(world.background, pt)


def set_background(world, pt, bgnd):
   if within_bounds(world, pt):
      occ_grid.set_cell(world.background, pt, bgnd)


def get_tile_occupant(world, pt):
   if within_bounds(world, pt):
      return occ_grid.get_cell(world.occupancy, pt)


def get_entities(world):
   return world.entities"""


