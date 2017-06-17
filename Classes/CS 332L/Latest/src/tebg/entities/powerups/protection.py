"""
Created on May 21, 2015

@author: Tony Liang

"""

import pygame
from tebg.entities.powerups.power_ups import PowerUp

class Protection(PowerUp):
    """This class represents a protection power-up.
    
    Attributes:
        image (pygame.Surface): The sprite's image.
        rect (pygame.Rect): The sprite's image's rectangle.
        dirty (int): Determines whether to repaint the sprite every frame.
    
    """
    
    def __init__(self, images_storage, audio_storage, duration, layer):
        """Sets up the protection power-up on creation.
        
        Args:
            images_storage (ImagesStorage): ImagesStorage object that stores all 
                game images.
            audio_storage (AudioStorage): AudioStorage object that stores all 
                game audio.
            duration (int): The power-up's duration in seconds.
            layer (int): The layer the protection power-up is to be drawn in.
        
        """
        
        # Calls the parent class (PowerUp) constructor.
        super().__init__(images_storage, audio_storage, duration)
        
        img = images_storage.get_image("../../Sprites/Power Ups/armor.png")
        screen_w_or_h_percentage = 1 / 4444
        scaled_img = (pygame.transform
                      .smoothscale(img, (round(img.get_rect().width 
                                               * (screen_w_or_h_percentage 
                                                  * self._min_w_or_h)), 
                                         round(img.get_rect().height 
                                               * (screen_w_or_h_percentage 
                                                  * self._min_w_or_h)))))
        self.image = scaled_img.convert_alpha()

        self.rect = self.image.get_rect()
        
        self._armor_amount = 25
        
        self.dirty = 2
        self._layer = layer