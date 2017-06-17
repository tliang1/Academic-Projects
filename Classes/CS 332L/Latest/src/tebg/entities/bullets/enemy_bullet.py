"""
Created on May 11, 2015

@author: Tony Liang

"""

import pygame
from tebg.entities.bullets.bullet import Bullet

class EnemyBullet(Bullet):
    """This class represents the enemy's bullet."""
    
    def __init__(self, images_storage, layer):
        """Sets up the enemy's bullet on creation.
        
        Args:
            images_storage (ImagesStorage): ImagesStorage object that stores all 
                game images.
            layer (int): The layer the enemy bullet is to be drawn in.
        
        """
        
        # Calls the parent class (Bullet) constructor.
        super().__init__(images_storage, layer)
        
        self.image = pygame.transform.rotate(self.image, 180)

    def update(self, elapsed_seconds_since_last_frame):
        """Moves the enemy's bullet down the screen.
        
        Args:
            elapsed_seconds_since_last_frame (float): The number of seconds 
                since the last frame.
        
        """
        
        screen_h_percentage_for_movement = 2 / 3
        
        self.rect.y += int(screen_h_percentage_for_movement 
                           * self._screen_res[1] 
                           * elapsed_seconds_since_last_frame)