import point
import image_store
import random
import worldmodel

PROPERTY_KEY = 0

class Background:
   def __init__(self, name, imgs):
      self.name = name
      self.imgs = imgs
      self.current_img = 0

   def get_images(self):
      return self.imgs

   def get_image(self):
      return self.imgs[self.current_img]

   def get_name(self):
      return self.name

   def next_image(self):
      self.current_img = (self.current_img + 1) % len(self.imgs)


class MinerNotFull:
   def __init__(self, name, resource_limit, position, rate, imgs,
      animation_rate):
      self.name = name
      self.position = position
      self.rate = rate
      self.imgs = imgs
      self.current_img = 0
      self.resource_limit = resource_limit
      self.resource_count = 0
      self.animation_rate = animation_rate
      self.pending_actions = []

   def set_position(self, point):
      self.position = point

   def get_position(self):
      return self.position

   def get_images(self):
      return self.imgs

   def get_image(self):
      return self.imgs[self.current_img]

   def get_rate(self):
      return self.rate

   def set_resource_count(self, n):
      self.resource_count = n

   def get_resource_count(self):
      return self.resource_count

   def get_resource_limit(self):
      return self.resource_limit

   def get_name(self):
      return self.name

   def get_animation_rate(self):
      return self.animation_rate

   def remove_pending_action(self, action):
      self.pending_actions.remove(action)

   def add_pending_action(self, action):
      self.pending_actions.append(action)

   def get_pending_actions(self):
      return self.pending_actions

   def clear_pending_actions(self):
      self.pending_actions = []

   def next_image(self):
      self.current_img = (self.current_img + 1) % len(self.imgs)

   def entity_string(self):
      return ' '.join(['miner', self.name, str(self.position.x),
         str(self.position.y), str(self.resource_limit),
         str(self.rate), str(self.animation_rate)])

   #Actions.py
   def schedule_miner(world, miner, ticks, i_store):
       schedule_action(world, miner, create_miner_action(world, miner, i_store),
          ticks + entities.get_rate(miner))
       schedule_animation(world, miner)
   
   def remove_entity(self, world):
       for action in self.get_pending_actions():
          worldmodel.unschedule_action(world, action)
       self.clear_pending_actions(self)
       worldmodel.self.remove_entity(world)
        
   def create_miner_not_full_action(self, world, i_store):
       def action(current_ticks):
          self.remove_pending_action(action)

          entity_pt = self.get_position()
          ore = worldmodel.find_nearest(world, entity_pt, Ore)
          (tiles, found) = self.miner_to_ore(world, ore)

          new_entity = entity
          if found:
             new_entity = self.try_transform_miner(world,
                try_transform_miner_not_full)

          schedule_action(world, new_entity,
             create_miner_action(world, new_entity, i_store),
             current_ticks + entities.get_rate(new_entity))
          return tiles
       return action

   def miner_to_ore(self,world,ore):
       entity_pt = self.get_position()
       if not ore:
          return ([entity_pt], False)
       ore_pt = get_position(ore)
       if adjacent(entity_pt, ore_pt):
          self.set_resource_count(
             1 + self.get_resource_count())
          remove_entity(world, ore)
          return ([ore_pt], True)
       else:
          new_pt = next_position(world, entity_pt, ore_pt)
          return (worldmodel.move_entity(world, entity, new_pt), False)


   def miner_to_smith(self, smith):
       entity_pt = self.get_position()
       if not smith:
          return ([entity_pt], False)
       smith_pt = get_position(smith)
       if adjacent(entity_pt, smith_pt):
          set_resource_count(smith,
            get_resource_count(smith) +
             self.get_resource_count())
          self.set_resource_count(0)
          return ([], True)
       else:
          new_pt = next_position(world, entity_pt, smith_pt)
          return (worldmodel.move_entity(world, entity, new_pt), False)
        
   def try_transform_miner_full(self,world):
       new_entity = MinerNotFull(
          self.get_name(), self.get_resource_limit(entity),
          self.get_position(), self.get_rate(),
         self.get_images(), self.get_animation_rate())

       return new_entity



   
  
   


class MinerFull:
   def __init__(self, name, resource_limit, position, rate, imgs,
      animation_rate):
      self.name = name
      self.position = position
      self.rate = rate
      self.imgs = imgs
      self.current_img = 0
      self.resource_limit = resource_limit
      self.resource_count = resource_limit
      self.animation_rate = animation_rate
      self.pending_actions = []

   def set_position(self, point):
      self.position = point

   def get_position(self):
      return self.position

   def get_images(self):
      return self.imgs

   def get_image(self):
      return self.imgs[self.current_img]

   def get_rate(self):
      return self.rate

   def get_resource_count(self):
      return self.resource_count

   def get_resource_limit(self):
      return self.resource_limit

   def get_name(self):
      return self.name

   def get_animation_rate(self):
      return self.animation_rate

   def remove_pending_action(self, action):
      self.pending_actions.remove(action)

   def add_pending_action(self, action):
      self.pending_actions.append(action)

   def get_pending_actions(self):
      return self.pending_actions

   def clear_pending_actions(self):
      self.pending_actions = []

   def next_image(self):
      self.current_img = (self.current_img + 1) % len(self.imgs)
        
   #Actions.py
  


   def remove_entity(self, world):
       for action in self.get_pending_actions():
          worldmodel.unschedule_action(world, action)
       self.clear_pending_actions(self)
       worldmodel.self.remove_entity(world)
        
   def create_miner_full_action(self, world, i_store):
       def action(current_ticks):
          self.remove_pending_action(action)

          entity_pt = self.get_position()
          smith = worldmodel.find_nearest(world, entity_pt, Blacksmith)
          (tiles, found) = self.miner_to_smith(world, smith)

          new_entity = entity
          if found:
              new_entity = self.try_transform_miner(world,
                try_transform_miner_full)

          schedule_action(world, new_entity,
             create_miner_action(world, new_entity, i_store),
             current_ticks + entities.get_rate(new_entity))
          return tiles
       return action
   def miner_to_ore(self,world,ore):
       entity_pt = self.get_position()
       if not ore:
          return ([entity_pt], False)
       ore_pt = get_position(ore)
       if adjacent(entity_pt, ore_pt):
          self.set_resource_count(
             1 + self.get_resource_count())
          remove_entity(world, ore)
          return ([ore_pt], True)
       else:
          new_pt = next_position(world, entity_pt, ore_pt)
          return (worldmodel.move_entity(world, entity, new_pt), False)


   def miner_to_smith(self, smith):
       entity_pt = self.get_position()
       if not smith:
          return ([entity_pt], False)
       smith_pt = get_position(smith)
       if adjacent(entity_pt, smith_pt):
          set_resource_count(smith,
            get_resource_count(smith) +
             self.get_resource_count())
          self.set_resource_count(0)
          return ([], True)
       else:
          new_pt = next_position(world, entity_pt, smith_pt)
          return (worldmodel.move_entity(world, entity, new_pt), False)
        
   def try_transform_miner_not_full(world, entity):
       if entity.resource_count < entity.resource_limit:
          return entity
       else:
          new_entity = MinerFull(
             self.get_name(), self.get_resource_limit(),
             self.get_position(), self.get_rate(),
             self.get_images(), self.get_animation_rate())
          return new_entity

        
   
   
  

class Vein:
   def __init__(self, name, rate, position, imgs, resource_distance=1):
      self.name = name
      self.position = position
      self.rate = rate
      self.imgs = imgs
      self.current_img = 0
      self.resource_distance = resource_distance
      self.pending_actions = []

   def set_position(self, point):
      self.position = point

   def get_position(self):
      return self.position

   def get_images(self):
      return self.imgs

   def get_image(self):
      return self.imgs[self.current_img]

   def get_rate(self):
      return self.rate

   def get_resource_distance(self):
      return self.resource_distance

   def get_name(self):
      return self.name

   def remove_pending_action(self, action):
      self.pending_actions.remove(action)

   def add_pending_action(self, action):
      self.pending_actions.append(action)

   def get_pending_actions(self):
      return self.pending_actions

   def clear_pending_actions(self):
      self.pending_actions = []

   def next_image(self):
      self.current_img = (self.current_img + 1) % len(self.imgs)

   def entity_string(self):
      return ' '.join(['vein', self.name, str(self.position.x),
         str(self.position.y), str(self.rate),
         str(self.resource_distance)])

   #Actions.py 
     
   def schedule_vein(world, vein, ticks, i_store):
       schedule_action(world, vein, create_vein_action(world, vein, i_store),
          ticks + entities.get_rate(vein))


   def remove_entity(self, world):
       for action in self.get_pending_actions():
          worldmodel.unschedule_action(world, action)
       self.clear_pending_actions(self)
       worldmodel.self.remove_entity(world)


class Ore:
   def __init__(self, name, position, imgs, rate=5000):
      self.name = name
      self.position = position
      self.imgs = imgs
      self.current_img = 0
      self.rate = rate
      self.pending_actions = []

   def set_position(self, point):
      self.position = point

   def get_position(self):
      return self.position

   def get_images(self):
      return self.imgs

   def get_image(self):
      return self.imgs[self.current_img]

   def get_name(self):
      return self.name

   def remove_pending_action(self, action):
      self.pending_actions.remove(action)

   def add_pending_action(self, action):
      self.pending_actions.append(action)

   def get_pending_actions(self):
      return self.pending_actions

   def clear_pending_actions(self):
      self.pending_actions = []

   def next_image(self):
      self.current_img = (self.current_img + 1) % len(self.imgs)

   def entity_string(self):
      return ' '.join(['ore', self.name, str(self.position.x),
         str(self.position.y), str(self.rate)])

   #actions.py
   def schedule_ore(world, ore, ticks, i_store):
       schedule_action(world, ore,
          create_ore_transform_action(world, ore, i_store),
          ticks + entities.get_rate(ore))

   def remove_entity(self, world):
       for action in self.get_pending_actions():
          worldmodel.unschedule_action(world, action)
       self.clear_pending_actions(self)
       worldmodel.self.remove_entity(world)
        
  
   


class Blacksmith:
   def __init__(self, name, position, imgs, resource_limit, rate,
      resource_distance=1):
      self.name = name
      self.position = position
      self.imgs = imgs
      self.current_img = 0
      self.resource_limit = resource_limit
      self.resource_count = 0
      self.rate = rate
      self.resource_distance = resource_distance
      self.pending_actions = []

   def set_position(self, point):
      self.position = point

   def get_position(self):
      return self.position

   def get_images(self):
      return self.imgs

   def get_image(self):
      return self.imgs[self.current_img]

   def get_rate(self):
      return self.rate

   def set_resource_count(self, n):
      self.resource_count = n

   def get_resource_count(self):
      return self.resource_count

   def get_resource_limit(self):
      return self.resource_limit

   def get_resource_distance(self):
      return self.resource_distance

   def get_name(self):
      return self.name

   def remove_pending_action(self, action):
      self.pending_actions.remove(action)

   def add_pending_action(self, action):
      self.pending_actions.append(action)

   def get_pending_actions(self):
      return self.pending_actions

   def clear_pending_actions(self):
      self.pending_actions = []

   def next_image(self):
      self.current_img = (self.current_img + 1) % len(self.imgs)

   def entity_string(self):
      return ' '.join(['blacksmith', self.name, str(self.position.x),
         str(self.position.y), str(self.resource_limit),
         str(self.rate), str(self.resource_distance)])

   #actions.py
      

   def remove_entity(self, world):
       for action in self.get_pending_actions():
          worldmodel.unschedule_action(world, action)
       self.clear_pending_actions(self)
       worldmodel.self.remove_entity(world)


class Obstacle:
   def __init__(self, name, position, imgs):
      self.name = name
      self.position = position
      self.imgs = imgs
      self.current_img = 0

   def set_position(self, point):
      self.position = point

   def get_position(self):
      return self.position

   def get_images(self):
      return self.imgs

   def get_image(self):
      return self.imgs[self.current_img]

   def get_name(self):
      return self.name

   def next_image(self):
      self.current_img = (self.current_img + 1) % len(self.imgs)

   def entity_string(self):
      return ' '.join(['obstacle', self.name, str(self.position.x),
         str(self.position.y)])

   #Actions.py


   def remove_entity(self, world):
       for action in self.get_pending_actions():
          worldmodel.unschedule_action(world, action)
       self.clear_pending_actions(self)
       worldmodel.self.remove_entity(world)

class OreBlob:
   def __init__(self, name, position, rate, imgs, animation_rate):
      self.name = name
      self.position = position
      self.rate = rate
      self.imgs = imgs
      self.current_img = 0
      self.animation_rate = animation_rate
      self.pending_actions = []

   def set_position(self, point):
      self.position = point

   def get_position(self):
      return self.position

   def get_images(self):
      return self.imgs

   def get_image(self):
      return self.imgs[self.current_img]

   def get_rate(self):
      return self.rate

   def get_name(self):
      return self.name

   def get_animation_rate(self):
      return self.animation_rate

   def remove_pending_action(self, action):
      self.pending_actions.remove(action)

   def add_pending_action(self, action):
      self.pending_actions.append(action)

   def get_pending_actions(self):
      return self.pending_actions

   def clear_pending_actions(self):
      self.pending_actions = []

   def next_image(self):
      self.current_img = (self.current_img + 1) % len(self.imgs)
        
   #actions.py

   def remove_entity(self, world):
       for action in self.get_pending_actions():
          worldmodel.unschedule_action(world, action)
       self.clear_pending_actions(self)
       worldmodel.self.remove_entity(world)
        
   

class Quake:
   def __init__(self, name, position, imgs, animation_rate):
      self.name = name
      self.position = position
      self.imgs = imgs
      self.current_img = 0
      self.animation_rate = animation_rate
      self.pending_actions = []

   def set_position(self, point):
      self.position = point

   def get_position(self):
      return self.position

   def get_images(self):
      return self.imgs

   def get_image(self):
      return self.imgs[self.current_img]

   def get_name(self):
      return self.name

   def get_animation_rate(self):
      return self.animation_rate

   def remove_pending_action(self, action):
      self.pending_actions.remove(action)

   def add_pending_action(self, action):
      self.pending_actions.append(action)

   def get_pending_actions(self):
      return self.pending_actions

   def clear_pending_actions(self):
      self.pending_actions = []

   def next_image(self):
      self.current_img = (self.current_img + 1) % len(self.imgs)
        
   #actions.py 


   def remove_entity(self, world):
       for action in self.get_pending_actions():
          worldmodel.unschedule_action(world, action)
       self.clear_pending_actions(self)
       worldmodel.self.remove_entity(world)
        
 
   


"""def set_position(entity, point):
   entity.position = point"""

def get_position(entity):
   return entity.position


"""def get_images(entity):
   return entity.imgs

def get_image(entity):
   return entity.imgs[entity.current_img]


def get_rate(entity):
   return entity.rate


def set_resource_count(entity, n):
   entity.resource_count = n

def get_resource_count(entity):
   return entity.resource_count


def get_resource_limit(entity):
   return entity.resource_limit


def get_resource_distance(entity):
   return entity.resource_distance


def get_name(entity):
   return entity.name


def get_animation_rate(entity):
   return entity.animation_rate


def remove_pending_action(entity, action):
   if hasattr(entity, "pending_actions"):
      entity.pending_actions.remove(action)

def add_pending_action(entity, action):
   if hasattr(entity, "pending_actions"):
      entity.pending_actions.append(action)


def get_pending_actions(entity):
   if hasattr(entity, "pending_actions"):
      return entity.pending_actions
   else:
      return []

def clear_pending_actions(entity):
   if hasattr(entity, "pending_actions"):
      entity.pending_actions = []


def next_image(entity):
   entity.current_img = (entity.current_img + 1) '% 'len(entity.imgs)"""


# This is a less than pleasant file format, but structured based on
# material covered in course.  Something like JSON would be a
# significant improvement.
"""def entity_string(entity):
   if isinstance(entity, MinerNotFull):
      return ' '.join(['miner', entity.name, str(entity.position.x),
         str(entity.position.y), str(entity.resource_limit),
         str(entity.rate), str(entity.animation_rate)])
   elif isinstance(entity, Vein):
      return ' '.join(['vein', entity.name, str(entity.position.x),
         str(entity.position.y), str(entity.rate),
         str(entity.resource_distance)])
   elif isinstance(entity, Ore):
      return ' '.join(['ore', entity.name, str(entity.position.x),
         str(entity.position.y), str(entity.rate)])
   elif isinstance(entity, Blacksmith):
      return ' '.join(['blacksmith', entity.name, str(entity.position.x),
         str(entity.position.y), str(entity.resource_limit),
         str(entity.rate), str(entity.resource_distance)])
   elif isinstance(entity, Obstacle):
      return ' '.join(['obstacle', entity.name, str(entity.position.x),
         str(entity.position.y)])
   else:
      return 'unknown'"""

