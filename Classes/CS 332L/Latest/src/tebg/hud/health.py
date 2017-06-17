"""
Created on May 14, 2015

@author: Tony Liang

"""

import pygame
import tebg.constants as constants

class Health(pygame.sprite.DirtySprite):
    """
    This class represents the heads-up display (HUD) for the player's health. 
    
    The player's health is displayed as a heart and a bar on the top of the 
    screen. When the player has health, the bar will be filled red with the 
    amount of health. Otherwise, the bar stays empty.
    
    Attributes:
        image (pygame.Surface): The sprite's image.
        rect (pygame.Rect): The sprite's image's rectangle.
        dirty (int): Determines whether to repaint the sprite every frame.
    
    """
    
    def __init__(self, images_storage, value, layer):
        """Sets up the HUD for the player's health on creation.
        
        Args:
            images_storage (ImagesStorage): ImagesStorage object that stores all 
                game images.
            value (int): The amount of health the player starts with.
            layer (int): The layer the player's health is to be drawn in.
        
        """
        
        # Calls the parent class (DirtySprite) constructor.
        super().__init__()
        
        screen_res = pygame.display.get_surface().get_size()
        self._min_w_or_h = screen_res[0]
        
        if (self._min_w_or_h > screen_res[1]):
            self._min_w_or_h = screen_res[1]
        
        self._imgs_storage = images_storage
        
        img = self._imgs_storage.get_image("../../Sprites/HUD/health_bar.png")
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
        x_and_y_percentage = 1 / 90
        self.rect.x = self.rect.y = int(x_and_y_percentage * self._min_w_or_h)
        
        self._val = value
        
        self.dirty = 2
        self._layer = layer

    def update(self, elapsed_seconds_since_last_frame):
        """Updates the health bar.
        
        Args:
            elapsed_seconds_since_last_frame (float): The number of seconds 
                since the last frame.
        
        """
        
        if (self._val >= 0):
            img = (self._imgs_storage
                   .get_image("../../Sprites/HUD/health_bar.png"))
            screen_w_or_h_percentage = 1 / 900
            scaled_img = (pygame.transform
                          .smoothscale(img, (round(img.get_rect().width 
                                                   * (screen_w_or_h_percentage 
                                                      * self._min_w_or_h)), 
                                             round(img.get_rect().height 
                                                   * (screen_w_or_h_percentage 
                                                      * self._min_w_or_h)))))
            self.image = scaled_img.convert_alpha()
            left_percentage = 41 / 900
            top_percentage = 1 / 900
            width_percentage = 32 / 225
            height_percentage = 8 / 225
            self.image.fill(constants.RED, [left_percentage * self._min_w_or_h, 
                                            top_percentage * self._min_w_or_h, 
                                            round(width_percentage 
                                                  * self._min_w_or_h 
                                                  * (self._val / 100)), 
                                            round(height_percentage 
                                                  * self._min_w_or_h)])
        
    def set_health(self, health):
        """Updates the player's health.
        
        Args:
            health (int): The amount of health the player has.
        
        """
        
        self._val = health
        
        if (self._val > 100):
            self._val = 100
        elif (self._val < 0):
            self._val = 0