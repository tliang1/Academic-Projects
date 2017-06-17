"""
Created on May 14, 2015

@author: Tony Liang

Source:
Event Timer by furas: https://stackoverflow.com/questions/40205000/pygame-datet
                      ime-troubles
                      
"""

import random
import pygame

class Spawning():
    """This class represents a system for spawning sprites.
    
    Each instance of this class is meant to only be used for spawning new 
    instances of the same sprite object. The sprite objects are spawned 
    indefinitely with a set spawn delay.
    
    """
    
    def __init__(self, spawn_delay):
        """Sets up the system for spawning sprites on creation.

        Args:
            spawn_delay (int): The number of seconds between each spawn.
        
        """
        
        millis_per_sec = 1000
        
        self._spawn_delay = spawn_delay * millis_per_sec
        self._start_time = pygame.time.get_ticks()
        
    def spawn(self, spawn_object, similar_objects_list, 
              spawn_collide_sprites_list, all_sprites_list):
        """Spawns a sprite object.
        
        When the spawn delay is reached, the sprite object is spawned and 
        placed on a valid starting position above the screen.
        
        Args:
            spawn_object (pygame.sprite): The sprite to use for spawning.
            similar_objects_list (pygame.sprite.Group): List that the spawn 
                object belongs to.
            spawn_collide_sprites_list (pygame.sprite.Group): List of sprites to 
                check collision with the spawn object.
            all_sprites_list (pygame.sprite.Group): List of all sprites.
        
        """
        
        spawn_object.rect.y = -spawn_object.rect.height
        
        new_start_time = pygame.time.get_ticks()
        
        notOverlapped = False
        
        if ((new_start_time - self._start_time) >= self._spawn_delay):
            # Sets a random location for the spawn object above the screen.
            total_x_positions = (pygame.display.get_surface().get_rect().width 
                                 - spawn_object.rect.width + 1)
            randomXList = random.sample(range(total_x_positions), 
                                        total_x_positions)
            
            for x in randomXList:
                spawn_object.rect.x = x
                overlap_sprites = (pygame.sprite
                                   .spritecollide(spawn_object, 
                                                  spawn_collide_sprites_list, 
                                                  False))
                
                if (not overlap_sprites):
                    self._start_time = new_start_time
                    
                    similar_objects_list.add(spawn_object)
                    spawn_collide_sprites_list.add(spawn_object)
                    all_sprites_list.add(spawn_object)
                    
                    notOverlapped = True
                    break
        
        if not notOverlapped:
            spawn_object.kill()
        
    def reset_start_time(self):
        self._start_time = pygame.time.get_ticks()