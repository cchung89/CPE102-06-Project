import point
import image_store
import random
#import worldmodel.py 

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

   #builder_controller.py
   def create_new_entity(pt, entity_select, i_store):
      MINER_LIMIT = 2
      MINER_RATE_MIN = 600
      MINER_RATE_MAX = 1000
      MINER_ANIMATION_RATE = 100

      name = entity_select + '_' + str(pt.x) + '_' + str(pt.y)
      images = image_store.get_images(i_store, entity_select)

      return MinerNotFull(name, MINER_LIMIT, pt,
         random.randint(MINER_RATE_MIN, MINER_RATE_MAX),
         images, MINER_ANIMATION_RATE)
      return self

   #Actions.py
   
   
   def remove_entity(self, world):
       for action in self.get_pending_actions():
          worldmodel.unschedule_action(world, action)
       self.clear_pending_actions(self)
       worldmodel.self.remove_entity(world)
        
  
   


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

   #builder_controller.py
   def create_new_entity(pt, entity_select, i_store):
      VEIN_RATE_MIN = 8000
      VEIN_RATE_MAX = 17000

      name = entity_select + '_' + str(pt.x) + '_' + str(pt.y)
      images = image_store.get_images(i_store, entity_select)

      return Vein(name,
         random.randint(VEIN_RATE_MIN, VEIN_RATE_MAX), pt, images)

   #Actions.py 
     
   


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

   #save_load.py
   def create_ore(self, properties, i_store):
      ORE_KEY = 'ore'
      ORE_NUM_PROPERTIES = 5
      ORE_NAME = 1
      ORE_COL = 2
      ORE_ROW = 3
      ORE_RATE = 4

      if len(properties) == ORE_NUM_PROPERTIES:
         self.name = properties[ORE_NAME]
         self.position = point.Point(int(properties[ORE_COL]), int(properties[ORE_ROW]))
         self.imgs = image_store.get_images(i_store, properties[PROPERTY_KEY])
         self.current_img = 0
         self.rate = int(properties[ORE_RATE])
         self.pending_actions = []
         return self
      else:
         return None

   #builder_controller.py
   def create_new_entity(pt, entity_select, i_store):
      ORE_RATE_MIN = 20000
      ORE_RATE_MAX = 30000

      name = entity_select + '_' + str(pt.x) + '_' + str(pt.y)
      images = image_store.get_images(i_store, entity_select)

      return Ore(name, pt, images,
         random.randint(ORE_RATE_MIN, ORE_RATE_MAX))

   #actions.py


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

   #save_load.py
   def create_blacksmith(self, properties, i_store):
      SMITH_KEY = 'blacksmith'
      SMITH_NUM_PROPERTIES = 7
      SMITH_NAME = 1
      SMITH_COL = 2
      SMITH_ROW = 3
      SMITH_LIMIT = 4
      SMITH_RATE = 5
      SMITH_REACH = 6

      if len(properties) == SMITH_NUM_PROPERTIES:
         self.name = properties[SMITH_NAME]
         self.position = point.Point(int(properties[SMITH_COL]), int(properties[SMITH_ROW]))
         self.imgs = image_store.get_images(i_store, properties[PROPERTY_KEY])
         self.current_img = 0
         self.resource_limit = int(properties[SMITH_LIMIT])
         self.resource_count = 0
         self.rate = int(properties[SMITH_RATE])
         self.resource_distance = int(properties[SMITH_REACH])
         self.pending_actions = []
         return self
      else:
         return None

   #builder_controller.py
   def create_new_entity(pt, entity_select, i_store):
      SMITH_LIMIT_MIN = 10
      SMITH_LIMIT_MAX = 15
      SMITH_RATE_MIN = 2000
      SMITH_RATE_MAX = 4000

      name = entity_select + '_' + str(pt.x) + '_' + str(pt.y)
      images = image_store.get_images(i_store, entity_select)

      return entities.Blacksmith(name, pt, images,
         random.randint(SMITH_LIMIT_MIN, SMITH_LIMIT_MAX),
      randintandom.randint(SMITH_RATE_MIN, SMITH_RATE_MAX))

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

   #save_load.py
   def create_obstacle(self, properties, i_store):
      OBSTACLE_KEY = 'obstacle'
      OBSTACLE_NUM_PROPERTIES = 4
      OBSTACLE_NAME = 1
      OBSTACLE_COL = 2
      OBSTACLE_ROW = 3

      if len(properties) == OBSTACLE_NUM_PROPERTIES:
         self.name = properties[OBSTACLE_NAME]
         self.position = point.Point(int(properties[OBSTACLE_COL]), int(properties[OBSTACLE_ROW]))
         self.imgs = image_store.get_images(i_store, properties[PROPERTY_KEY])
         self.current_img = 0
         return self
      else:
         return None

   #builder_controller.py
   def create_new_entity(pt, entity_select, i_store):
      name = entity_select + '_' + str(pt.x) + '_' + str(pt.y)
      images = image_store.get_images(i_store, entity_select)

      return Obstacle(name, pt, images)

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
   entity.position = point

def get_position(entity):
   return entity.position


def get_images(entity):
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

