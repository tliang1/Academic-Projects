"""
Created on May 12, 2017

@author: Tony Liang

"""

import pygame
from tebg.entities.threats.threat import Threat

class Thorns(Threat):
    """This class represents thorns.
    
    Attributes:
        image (pygame.Surface): The sprite's image.
        rect (pygame.Rect): The sprite's image's rectangle.
        dirty (int): Determines whether to repaint the sprite every frame.
    
    """
    
    def __init__(self, images_storage, audio_storage, difficulty, layer):
        """Sets up the thorns on creation.
        
        Args:
            images_storage (ImagesStorage): ImagesStorage object that stores all 
                game images.
            audio_storage (AudioStorage): AudioStorage object that stores all 
                game audio.
            difficulty (int): The difficulty value.
            layer (int): The layer the thorns are to be drawn in.
        
        """
        
        # Calls the parent class (Threat) constructor.
        super().__init__(images_storage, audio_storage)
        
        img = images_storage.get_image("../../Sprites/Threats/thorns.png")
        screen_w_or_h_percentage = 1 / 2700
        scaled_img = (pygame.transform
                      .smoothscale(img, (round(img.get_rect().width 
                                               * (screen_w_or_h_percentage 
                                                  * self._min_w_or_h)), 
                                         round(img.get_rect().height 
                                               * (screen_w_or_h_percentage 
                                                  * self._min_w_or_h)))))
        self.image = scaled_img.convert_alpha()

        self.rect = self.image.get_rect()
        
        self._threat_sound = audio_storage.get_audio('../../Sounds/thorns.ogg')
        
        self._damage = 15 * difficulty
        
        self.dirty = 2
        self._layer = layer