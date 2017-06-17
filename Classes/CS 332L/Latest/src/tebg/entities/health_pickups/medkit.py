"""
Created on May 14, 2015

@author: Tony Liang

"""

import pygame
from tebg.entities.health_pickups.healing import Healing

class Medkit(Healing):
    """This class represents the medkit pickup.
    
    Attributes:
        image (pygame.Surface): The sprite's image.
        rect (pygame.Rect): The sprite's image's rectangle.
        dirty (int): Determines whether to repaint the sprite every frame.
    
    """
    
    def __init__(self, images_storage, audio_storage, layer):
        """Sets up the medkit pickup on creation.
        
        Args:
            images_storage (ImagesStorage): ImagesStorage object that stores all 
                game images.
            audio_storage (AudioStorage): AudioStorage object that stores all 
                game audio.
            layer (int): The layer the medkit pickup is to be drawn in.
        
        """
        
        # Calls the parent class (Healing) constructor.
        super().__init__(images_storage, audio_storage)
        
        img = images_storage.get_image("../../Sprites/Health Items/medkit.png")
        screen_w_or_h_percentage = 1 / 900
        scaled_img = (pygame.transform
                      .smoothscale(img, (round(img.get_rect().width 
                                               * (screen_w_or_h_percentage 
                                                  * self._min_w_or_h)), 
                                         round(img.get_rect().height 
                                               * (screen_w_or_h_percentage 
                                                  * self._min_w_or_h)))))
        self.image = scaled_img.convert_alpha()

        self.rect = self.image.get_rect()
        
        self._heal_amount = 50
        
        self.dirty = 2
        self._layer = layer