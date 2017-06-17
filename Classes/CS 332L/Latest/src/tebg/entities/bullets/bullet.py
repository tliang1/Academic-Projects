"""
Created on May 2, 2015

@author: Tony Liang

"""

import pygame

class Bullet(pygame.sprite.DirtySprite):
    """This class represents the player's bullet.
    
    Attributes:
        image (pygame.Surface): The sprite's image.
        rect (pygame.Rect): The sprite's image's rectangle.
        damage (int): The bullet's damage.
        dirty (int): Determines whether to repaint the sprite every frame.
    
    """
    
    def __init__(self, images_storage, layer):
        """Sets up the bullet on creation.
        
        Args:
            images_storage (ImagesStorage): ImagesStorage object that stores all 
                game images.
            layer (int): The layer the bullet is to be drawn in.
        
        """
        
        # Calls the parent class (DirtySprite) constructor.
        super().__init__()
        
        self._screen_res = pygame.display.get_surface().get_size()
        
        min_w_or_h = self._screen_res[0]
        
        if (min_w_or_h > self._screen_res[1]):
            min_w_or_h = self._screen_res[1]
            
        img = images_storage.get_image("../../Sprites/Bullets/bullet.png")
        screen_w_or_h_percentage = 1 / 7200
        scaled_img = (pygame.transform
                      .smoothscale(img, (round(img.get_rect().width 
                                               * (screen_w_or_h_percentage 
                                                  * min_w_or_h)), 
                                         (round(img.get_rect().height 
                                                * (screen_w_or_h_percentage 
                                                   * min_w_or_h))))))
        self.image = scaled_img.convert_alpha()

        self.rect = self.image.get_rect()
        
        self.damage = 0
        
        self.dirty = 2
        self._layer = layer

    def update(self, elapsed_seconds_since_last_frame):
        """Moves the bullet up the screen.
        
        Args:
            elapsed_seconds_since_last_frame (float): The number of seconds 
                since the last frame.
        
        """
        
        screen_h_percentage_for_movement = 2 / 3
        
        self.rect.y -= int(screen_h_percentage_for_movement 
                           * self._screen_res[1] 
                           * elapsed_seconds_since_last_frame)