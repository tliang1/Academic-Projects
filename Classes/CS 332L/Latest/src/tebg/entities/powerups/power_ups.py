"""
Created on Apr 27, 2015

@author: Tony Liang

Source:
Event Timer by furas: https://stackoverflow.com/questions/40205000/pygame-datet
                      ime-troubles

"""

import pygame

class PowerUp(pygame.sprite.DirtySprite):
    """This class represents a power-up.
    
    Attributes:
        image (pygame.Surface): The sprite's image.
        rect (pygame.Rect): The sprite's image's rectangle.
        is_picked_up (bool): Toggle if the power-up is picked up.
        is_over (bool): Toggle if the power-up wears off.
    
    """
    
    def __init__(self, images_storage, audio_storage, duration):
        """Sets up the power-up on creation.
        
        Args:
            images_storage (ImagesStorage): ImagesStorage object that stores all 
                game images.
            audio_storage (AudioStorage): AudioStorage object that stores all 
                game audio.
            duration (int): The power-up's duration in seconds.
        
        """
        
        # Calls the parent class (DirtySprite) constructor.
        super().__init__()
        
        self._screen_res = pygame.display.get_surface().get_size()
        self._min_w_or_h = self._screen_res[0]
        
        if (self._min_w_or_h > self._screen_res[1]):
            self._min_w_or_h = self._screen_res[1]
        
        img = images_storage.get_image("../../Sprites/Power Ups/power_up.png")
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
        
        self.is_picked_up = False
        self._power_up_sound = (audio_storage
                                .get_audio('../../Sounds/power_up.ogg'))
        self._played_once = False
        
        self._start_time = pygame.time.get_ticks()
        millis_per_sec = 1000
        self._duration = duration * millis_per_sec
        self.is_over = False
        
        self._armor_amount = -1
        self._damage = -1
        self._heal = 0
        
    def update(self, elapsed_seconds_since_last_frame):
        """
        Updates the power-up's movement, usage duration, and sound effect.
        
        Args:
            elapsed_seconds_since_last_frame (float): The number of seconds 
                since the last frame.
        
        """
        
        if not self.is_picked_up:
            screen_h_percentage_for_movement = 1 / 3
            self.rect.y += int(screen_h_percentage_for_movement 
                               * self._screen_res[1] 
                               * elapsed_seconds_since_last_frame)
            self._start_time = pygame.time.get_ticks()
        else:
            if not self._played_once:
                while (pygame.mixer.find_channel() is None):
                    pygame.mixer.set_num_channels(pygame.mixer
                                                  .get_num_channels() + 1)
            
                pygame.mixer.find_channel().play(self._power_up_sound)
                self._played_once = True
                
            self.rect.x = -self._screen_res[0]
            
            if ((pygame.time.get_ticks() - self._start_time) > self._duration):
                self.is_over = True
                self.rect.y = self._screen_res[1] + self.rect.height
    
    @property
    def armor_amount(self):
        return self._armor_amount
    
    @property
    def damage(self):
        return self._damage
    
    @property
    def heal(self):
        return self._heal