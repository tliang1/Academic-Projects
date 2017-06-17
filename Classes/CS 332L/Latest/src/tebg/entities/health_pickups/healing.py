"""
Created on May 14, 2015

@author: Tony Liang

"""

import pygame

class Healing(pygame.sprite.DirtySprite):
    """This class represents the health pickup.
    
    Attributes:
        image (pygame.Surface): The sprite's image.
        rect (pygame.Rect): The sprite's image's rectangle.
        is_picked_up (bool): Toggle if the health pickup is picked up.
    
    """
    
    def __init__(self, images_storage, audio_storage):
        """Sets up the health pickup on creation.
        
        Args:
            images_storage (ImagesStorage): ImagesStorage object that stores all 
                game images.
            audio_storage (AudioStorage): AudioStorage object that stores all 
                game audio.
        
        """
        
        # Calls the parent class (DirtySprite) constructor.
        super().__init__()
        
        self._screen_res = pygame.display.get_surface().get_size()
        self._min_w_or_h = self._screen_res[0]
        
        if (self._min_w_or_h > self._screen_res[1]):
            self._min_w_or_h = self._screen_res[1]
            
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
        
        self.is_picked_up = False
        self._health_pickup_sound = (audio_storage
                                     .get_audio('../../Sounds/healing.ogg'))
        self._played_once = False
        
        self._heal_amount = 100
        
    def update(self, elapsed_seconds_since_last_frame):
        """Updates the health pickup's movement and plays sound effect.
        
        Args:
            elapsed_seconds_since_last_frame (float): The number of seconds 
                since the last frame.
        
        """
        if not self.is_picked_up:
            screen_h_percentage_for_movement = 1 / 3
            self.rect.y += int(screen_h_percentage_for_movement 
                               * self._screen_res[1]  
                               * elapsed_seconds_since_last_frame)
        else:
            if not self._played_once:
                while (pygame.mixer.find_channel() is None):
                    pygame.mixer.set_num_channels(pygame.mixer
                                                  .get_num_channels() + 1)
                    
                pygame.mixer.find_channel().play(self._health_pickup_sound)
                self._played_once = True
                
                self.rect.y = self._screen_res[1] + self.rect.height
    
    @property
    def heal_amount(self):
        return self._heal_amount