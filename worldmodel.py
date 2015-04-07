import entities
import pygame
import ordered_list
import actions
import occ_grid
import point

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
         old_entity = occ_grid.get_cell(self.occupancy, pt)
         if old_entity != None:
            entities.clear_pending_actions(old_entity)
         occ_grid.set_cell(self.occupancy, pt, entity)
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

   #save_load.py
   def save_world(self, file):
      self.save_entities(file)
      self.save_background(file)

   def save_entities(self, file):
      for entity in self.get_entities():
         file.write(entities.entity_string(entity) + '\n')

   def save_background(self, file):
      for row in range(0, self.num_rows):
         for col in range(0, self.num_cols):
            file.write('background ' +
               entities.get_name(
                  self.get_background(point.Point(col, row))) +
               ' ' + str(col) + ' ' + str(row) + '\n')

   def load_world(self, images, file, run=False):
      for line in file:
         properties = line.split()
         if properties:
            if properties[PROPERTY_KEY] == BGND_KEY:
               self.add_background(properties, images)
            else:
               self.add_entity(properties, images, run)

   def add_background(self, properties, i_store):
      if len(properties) >= BGND_NUM_PROPERTIES:
         pt = point.Point(int(properties[BGND_COL]), int(properties[BGND_ROW]))
         name = properties[BGND_NAME]
         self.set_background(pt,
            entities.Background(name, image_store.get_images(i_store, name)))

   def load_add_entity(self, properties, i_store, run):
      new_entity = create_from_properties(properties, i_store)
      if new_entity:
         self.add_entity(new_entity)
         if run:
            self.schedule_entity(new_entity, i_store)

   #builder_controller.py
   def save_world(self, filename):
      with open(filename, 'w') as file:
         self.save_world(file)

   def load_world(self, i_store, filename):
      with open(filename, 'r') as file:
         self.load_world(i_store, file)

   def on_keydown(self, event, entity_select, i_store):
      x_delta = 0
      y_delta = 0
      if event.key == pygame.K_UP: y_delta -= 1
      if event.key == pygame.K_DOWN: y_delta += 1
      if event.key == pygame.K_LEFT: x_delta -= 1
      if event.key == pygame.K_RIGHT: x_delta += 1
      elif event.key in keys.ENTITY_KEYS:
         entity_select = keys.ENTITY_KEYS[event.key]
      elif event.key == keys.SAVE_KEY: self.save_world(WORLD_FILE_NAME)
      elif event.key == keys.LOAD_KEY: self.load_world(i_store, WORLD_FILE_NAME)
        
   #ACTIONS.PY

   def next_position(self, entity_pt, dest_pt):
       horiz = sign(dest_pt.x - entity_pt.x)
       new_pt = point.Point(entity_pt.x + horiz, entity_pt.y)

       if horiz == 0 or self.is_occupied(new_pt):
          vert = sign(dest_pt.y - entity_pt.y)
          new_pt = point.Point(entity_pt.x, entity_pt.y + vert)

          if vert == 0 or self.is_occupied(new_pt):
             new_pt = point.Point(entity_pt.x, entity_pt.y)

       return new_pt


   def blob_next_position(self, entity_pt, dest_pt):
       horiz = sign(dest_pt.x - entity_pt.x)
       new_pt = point.Point(entity_pt.x + horiz, entity_pt.y)

       if horiz == 0 or (self.is_occupied(new_pt) and
          not isinstance(self.get_tile_occupant(new_pt),
          entities.Ore)):
          vert = sign(dest_pt.y - entity_pt.y)
          new_pt = point.Point(entity_pt.x, entity_pt.y + vert)

          if vert == 0 or (self.is_occupied(new_pt) and
             not isinstance(self.get_tile_occupant(new_pt),
             entities.Ore)):
             new_pt = point.Point(entity_pt.x, entity_pt.y)

       return new_pt

   def find_open_around(self, pt, distance):
       for dy in range(-distance, distance + 1):
          for dx in range(-distance, distance + 1):
             new_pt = point.Point(pt.x + dx, pt.y + dy)

             if (self.within_bounds(new_pt) and
                (not self.is_occupied(new_pt))):
                return new_pt

       return None

   def create_animation_action(self, entity, repeat_count):
       def action(current_ticks):
          entities.remove_pending_action(entity, action)

          entities.next_image(entity)

          if repeat_count != 1:
             self.schedule_action(entity,
                self.create_animation_action( entity, max(repeat_count - 1, 0)),
                current_ticks + entities.get_animation_rate(entity))

          return [entities.get_position(entity)]
       return action


   def create_entity_death_action(self, entity):
       def action(current_ticks):
          entities.remove_pending_action(entity, action)
          pt = entities.get_position(entity)
          self.remove_entity(entity)
          return [pt]
       return action

   def create_blob(self, name, pt, rate, ticks, i_store):
       blob = entities.OreBlob(name, pt, rate,
          image_store.get_images(i_store, 'blob'),
          random.randint(BLOB_ANIMATION_MIN, BLOB_ANIMATION_MAX)
          * BLOB_ANIMATION_RATE_SCALE)
       self.schedule_blob(blob, ticks, i_store)
       return blob


   def schedule_blob(self, blob, ticks, i_store):
       self.schedule_action(blob, create_ore_blob_action(world, blob, i_store),
          ticks + entities.get_rate(blob))
       self.schedule_animation( blob)


   def schedule_miner(self, miner, ticks, i_store):
       self.schedule_action( miner, create_miner_action(world, miner, i_store),
          ticks + entities.get_rate(miner))
       self.schedule_animation(miner)


   def create_ore(self, name, pt, ticks, i_store):
       ore = entities.Ore(name, pt, image_store.get_images(i_store, 'ore'),
          random.randint(ORE_CORRUPT_MIN, ORE_CORRUPT_MAX))
       self.schedule_ore( ore, ticks, i_store)

       return ore
   def create_ore_transform_action(self, entity, i_store):
       def action(current_ticks):
          entities.remove_pending_action(entity, action)
          blob =self.create_blob(entities.get_name(entity) + " -- blob",
             entities.get_position(entity),
             entities.get_rate(entity) // BLOB_RATE_SCALE,
             current_ticks, i_store)

          self.remove_entity(entity)
          self.add_entity(blob)

          return [entities.get_position(blob)]
       return action    


   def schedule_ore(self, ore, ticks, i_store):
       self.schedule_action( ore,
          self.create_ore_transform_action(ore, i_store),
          ticks + entities.get_rate(ore))


   def create_quake(self, pt, ticks, i_store):
       quake = entities.Quake("quake", pt,
          image_store.get_images(i_store, 'quake'), QUAKE_ANIMATION_RATE)
       self.schedule_quake( quake, ticks)
       return quake


   def schedule_quake(self, quake, ticks):
       self.schedule_animation( quake, QUAKE_STEPS) 
       self.schedule_action( quake, create_entity_death_action(world, quake),
          ticks + QUAKE_DURATION)
      
    


   def create_vein(self, name, pt, ticks, i_store):
       vein = entities.Vein("vein" + name,
          random.randint(VEIN_RATE_MIN, VEIN_RATE_MAX),
          pt, image_store.get_images(i_store, 'vein'))
       return vein


   def schedule_vein(self, vein, ticks, i_store):
       self.schedule_action( vein, create_vein_action(world, vein, i_store),
          ticks + entities.get_rate(vein))


   def schedule_action(self, entity, action, time):
       entities.add_pending_action(entity, action)
       self.schedule_action( action, time)


   def schedule_animation(self, entity, repeat_count=0):
       self.schedule_action(self, entity,
          self.create_animation_action( entity, repeat_count),
          entities.get_animation_rate(entity))
     


   def clear_pending_actions(self, entity):
       for action in entities.get_pending_actions(entity):
          self.unschedule_action( action)
       entities.clear_pending_actions(entity)
    
   def try_transform_miner(self, entity, transform):
       new_entity = self.transform(entity)
       if entity != new_entity:
          self.clear_pending_actions(entity)
          self.remove_entity_at(entities.get_position(entity))
          self.add_entity(new_entity)
          self.schedule_animation(new_entity)

       return new_entity


   def create_miner_action(self, entity, image_store):
       if isinstance(entity, entities.MinerNotFull):
          return self.create_miner_not_full_action(entity, image_store)
       else:
          return self.create_miner_full_action(entity, image_store)  
        
   def blob_to_vein(self, entity, vein):
       entity_pt = entities.get_position(entity)
       if not vein:
          return ([entity_pt], False)
       vein_pt = entities.get_position(vein)
       if adjacent(entity_pt, vein_pt):
          remove_entity(world, vein)
          return ([vein_pt], True)
       else:
          new_pt = blob_next_position(world, entity_pt, vein_pt)
          old_entity = worldmodel.get_tile_occupant(world, new_pt)
          if isinstance(old_entity, entities.Ore):
             remove_entity(world, old_entity)
          return (self.move_entity(entity, new_pt), False)



   def create_ore_blob_action(self, entity, i_store):
       def action(current_ticks):
          entities.remove_pending_action(entity, action)

          entity_pt = entities.get_position(entity)
          vein = worldmodel.find_nearest(world, entity_pt, entities.Vein)
          (tiles, found) = blob_to_vein(world, entity, vein)

          next_time = current_ticks + entities.get_rate(entity)
          if found:
             quake = create_quake(world, tiles[0], current_ticks, i_store)
             self.add_entity(quake)
             next_time = current_ticks + entities.get_rate(entity) * 2

          self.schedule_action( entity,
             self.create_ore_blob_action(entity, i_store),
            next_time)

          return tiles
       return action

   def create_vein_action(self, entity, i_store):
       def action(current_ticks):
          entities.remove_pending_action(entity, action)

          open_pt = self.find_open_around(entities.get_position(entity),
             entities.get_r.add_entity(world,ore)
             tiles = [open_pt]
          else:
             tiles = []

          self.schedule_action( entity,
             self.create_vein_action(entity, i_store),
             current_ticks + entities.get_rate(entity))
          return tiles
       return action




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
   oftype = [(e, distance_sq(pt, entities.get_position(e)))
      for e in world.entities if isinstance(e, type)]

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
