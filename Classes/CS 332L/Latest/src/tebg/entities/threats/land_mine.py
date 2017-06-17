"""
Created on May 14, 2015

@author: Tony Liang

"""

import pygame
from tebg.entities.threats.threat import Threat

class LandMine(Threat):
    """This class represents a land mine.
    
    Attributes:
        image (pygame.Surface): The sprite's image.
        rect (pygame.Rect): The sprite's image's rectangle.
        dirty (int): Determines whether to repaint the sprite every frame.
    
    """
    
    def __init__(self, images_storage, audio_storage, difficulty, layer):
        """Sets up the land mine on creation.
        
        Args:
            images_storage (ImagesStorage): ImagesStorage object that stores all 
                game images.
            audio_storage (AudioStorage): AudioStorage object that stores all 
                game audio.
            difficulty (int): The difficulty value.
            layer (int): The layer the land mine is to be drawn in.
        
        """
        
        # Calls the parent class (Threat) constructor.
        super().__init__(images_storage, audio_storage)
        
        img = images_storage.get_image("../../Sprites/Threats/land_mine.png")
        screen_w_or_h_percentage = 1 / 900
        scaled_img = (pygame.transform
                      .smoothscale(img, (round(img.get_rect().width 
                                               * (screen_w_or_h_percentage 
                                                  * self._min_w_or_h)), 
                                         round(img.get_rect().height 
                                               * (screen_w_or_h_percentage 
                                                  * self._min_w_or_h)))))
        self.image = scaled_img.convert_alpha()
        
        img = images_storage.get_image("../../Sprites/Threats/explosion.png")
        screen_w_or_h_percentage = 1 / 1800
        scaled_img = (pygame.transform
                      .smoothscale(img, (round(img.get_rect().width 
                                               * (screen_w_or_h_percentage 
                                                  * self._min_w_or_h)), 
                                         round(img.get_rect().height 
                                               * (screen_w_or_h_percentage 
                                                  * self._min_w_or_h)))))
        self._explosion_image = scaled_img.convert_alpha()

        self.rect = self.image.get_rect()
        
        self._threat_sound = (audio_storage
                              .get_audio('../../Sounds/explosion.ogg'))
        
        self._damage = 30 * difficulty
        
        self.dirty = 2
        self._layer = layer
        
    def update(self, elapsed_seconds_since_last_frame):
        """
        Updates the land mine's movement, explosion status, and sound effect.
        
        Args:
            elapsed_seconds_since_last_frame (float): The number of seconds 
                since the last frame.
        
        """
        
        screen_h_percentage_for_movement = 1 / 3
        
        self.rect.y += int(screen_h_percentage_for_movement 
                           * self._screen_res[1] 
                           * elapsed_seconds_since_last_frame)
        
        if self.collided:
            if not self._played_once:
                while (pygame.mixer.find_channel() is None):
                    pygame.mixer.set_num_channels(pygame.mixer
                                                  .get_num_channels() + 1)
                
                pygame.mixer.find_channel().play(self._threat_sound)
                self._played_once = True
                
            self.image = self._explosion_image
            self.explosive = True