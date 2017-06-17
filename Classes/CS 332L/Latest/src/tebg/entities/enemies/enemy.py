"""
Created on May 14, 2015

@author: Tony Liang

"""

import pygame

class Enemy(pygame.sprite.DirtySprite):
    """This class represents the enemy.
    
    The enemy's health is updated whenever the enemy receives damage. The 
    enemy's audio for when the enemy gets hurt is initialized and played when 
    appropriate.
    
    Attributes:
        spotted (bool): Toggle detected the player.
    
    """
    
    def __init__(self, images_storage, audio_storage):
        """Sets up the enemy on creation.
        
        Args:
            images_storage (ImagesStorage): ImagesStorage object that stores all 
                game images.
            audio_storage (AudioStorage): AudioStorage object that stores all 
                game audio.
        
        """
        
        # Calls the parent class (DirtySprite) constructor.
        super().__init__()
        
        self._shoot_frames = []
        
        self._screen_res = pygame.display.get_surface().get_size()
        self._min_w_or_h = self._screen_res[0]
        
        if (self._min_w_or_h > self._screen_res[1]):
            self._min_w_or_h = self._screen_res[1]
            
        img = images_storage.get_image("../../Sprites/Enemies/Death.png")
        screen_w_or_h_percentage = 1 / 450
        scaled_img = (pygame.transform
                      .smoothscale(img, (round(img.get_rect().width 
                                               * (screen_w_or_h_percentage 
                                                  * self._min_w_or_h)), 
                                         round(img.get_rect().height 
                                               * (screen_w_or_h_percentage 
                                                  * self._min_w_or_h)))))
        self._death_image = scaled_img.convert_alpha()
        
        self._frame_index = 0
        self._animation_change = 0
        
        self._hurt = False
        self._hurt_sound = audio_storage.get_audio('../../Sounds/hurt.ogg')
        
        self._death = False
        
        self.spotted = False
        self._previous_time = pygame.time.get_ticks()
        self._bullets_list = pygame.sprite.Group()
        
        self._health = 100
        self._damage = 0
 
    def update(self, elapsed_seconds_since_last_frame):
        """Nothing.
        
        Args:
            elapsed_seconds_since_last_frame (float): The number of seconds 
                since the last frame.
        
        """
        
        pass
    
    @property
    def health(self):
        return self._health
    
    def change_health(self, health):
        """Updates the enemy's health and plays sound effect.
        
        Args:
            health (int): The amount of health to add or subtract from the 
                enemy.
        
        """
        
        if (health < 0):
            self._hurt = True
            
            while (pygame.mixer.find_channel() is None):
                pygame.mixer.set_num_channels(pygame.mixer.get_num_channels() 
                                              + 1)
                
            pygame.mixer.find_channel().play(self._hurt_sound)
            
        self._health += health
        
        if (self._health < 1):
            self._death = True
    
    @property
    def hurt(self):
        return self._hurt
    
    def reset_hurt(self):
        self._hurt = False
    
    @property
    def death_image(self):
        return self._death_image
    
    @property
    def death(self):
        return self._death
    
    @property
    def bullets_list(self):
        return self._bullets_list