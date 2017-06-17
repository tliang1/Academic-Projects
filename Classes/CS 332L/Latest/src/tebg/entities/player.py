"""
Created on May 6, 2015

@author: Tony Liang

Source:
Event Timer by furas: https://stackoverflow.com/questions/40205000/pygame-datet
                      ime-troubles
                      
"""

import pygame
from tebg.entities.bullets.bullet import Bullet

class Player(pygame.sprite.DirtySprite):
    """This class represents the player.
    
    The player's movement is controlled by keyboard or mouse. The player's 
    health and armor are updated whenever the player receives damage or recovers 
    health or armor. The player shoots bullets automatically. When the player is 
    shooting and/or receiving damage, the player is animated. The player's audio 
    for when the player gets hurt, shoots bullets, or is killed are initialized 
    and are played when appropriate.

    Attributes:
        image (pygame.Surface): The sprite's image.
        rect (pygame.Rect): The sprite's image's rectangle.
        mouse_movement (bool): Toggle using the mouse for player movement.
        keyboard_movement (bool): Toggle using the keyboard for player movement.
        move_left (bool): When using the keyboard, toggle moving the player left 
            or right.
        damage (int): The player's damage.
        dirty (int): Determines whether to repaint the sprite every frame.
    
    """
    
    def __init__(self, images_storage, audio_storage, layer):
        """Sets up the player on creation. 
        
        Args:
            images_storage (ImagesStorage): ImagesStorage object that stores all 
                game images.
            audio_storage (AudioStorage): AudioStorage object that stores all 
                game audio.
            layer (int): The layer the player is to be drawn in.
        
        """
        
        # Calls the parent class (DirtySprite) constructor.
        super().__init__()
        
        self._imgs_storage = images_storage
        
        # Images for player shooting and hurt animation.
        self._shoot_frames = []
        
        self._screen_res = pygame.display.get_surface().get_size()
        min_w_or_h = self._screen_res[0]
        
        if (min_w_or_h > self._screen_res[1]):
            min_w_or_h = self._screen_res[1]
        
        for frame in range(1, 5):
            img = self._imgs_storage.get_image("../../Sprites/Player/player_" 
                                           + str(frame) + ".png")
            screen_w_or_h_percentage = 1 / 450
            scaled_img = (pygame.transform
                          .smoothscale(img, (round(img.get_rect().width 
                                                   * (screen_w_or_h_percentage 
                                                      * min_w_or_h)), 
                                             round(img.get_rect().height 
                                                   * (screen_w_or_h_percentage 
                                                      * min_w_or_h)))))
            self._shoot_frames.append(scaled_img.convert_alpha())
            
        self.image = self._shoot_frames[0]
        self.rect = self.image.get_rect()
        
        # Player stays at the center bottom of the screen.
        self.rect.x = int(self._screen_res[0] / 2)
        self.rect.y = self._screen_res[1] - self.rect.height
        
        self._frame_index = 0
        self._animation_change = 0
        
        self.mouse_movement = False
        self.keyboard_movement = False
        self.move_left = False
        
        self._hurt = False
        self._hurt_sound = audio_storage.get_audio('../../Sounds/hurt.ogg')
        self._death_sound = audio_storage.get_audio('../../Sounds/death.ogg')
        self._shoot_sound = audio_storage.get_audio('../../Sounds/shooting.ogg')
        self._shoot_sound.set_volume(0.25)
        self._previous_time = pygame.time.get_ticks()
        self._bullets_list = pygame.sprite.Group()
        
        self._health = 100
        self._armor = 0
        self.damage = 25
        
        self.dirty = 2
        self._layer = layer
 
    def update(self, elapsed_seconds_since_last_frame):
        """
        Updates the player's movement, animation, shooting, and sound effects.
        
        Args:
            elapsed_seconds_since_last_frame (float): The number of seconds 
                since the last frame.
        
        """
        
        pos = pygame.mouse.get_pos()
        
        if self.keyboard_movement:
            if (self.rect.x <= (self._screen_res[0] - self.rect.width)):
                if (self.move_left):
                    self._go_left(elapsed_seconds_since_last_frame)
                else:
                    self._go_right(elapsed_seconds_since_last_frame)
            else:
                self.rect.x = self._screen_res[0] - self.rect.width
        elif self.mouse_movement:
            # Sets the player x position to the mouse x position
            if (pos[0] < (self._screen_res[0] - self.rect.width)):
                self.rect.x = pos[0]
            else:
                self.rect.x = self._screen_res[0] - self.rect.width
        
        # Animates player movement.
        if self._hurt:   
            if ((self._animation_change % 2) == 0):
                self.image = self._shoot_frames[3]
            else:
                self.image = self._shoot_frames[2]
             
            self._hurt = False
        else:   
            if ((self._animation_change % 2) == 0):
                self.image = self._shoot_frames[1]
            else:
                self.image = self._shoot_frames[0]
            
        self.rect = self.image.get_rect(x = self.rect.x, 
                                        y = (self._screen_res[1] 
                                             - self.image.get_height()))
        
        ticks = pygame.time.get_ticks()
        
        # Player has infinite bullets that spawn around every 250 ms.
        bullet_delay_in_millis = 250
        
        if ((ticks - self._previous_time) > bullet_delay_in_millis):
            while (pygame.mixer.find_channel() is None):
                pygame.mixer.set_num_channels(pygame.mixer.get_num_channels() 
                                              + 1)
                
            pygame.mixer.find_channel().play(self._shoot_sound)
            
            bullet = Bullet(self._imgs_storage, 2)
            bullet.damage = self.damage
            
            # Sets the bullet so it is where the player is.
            player_rect_w_percentage = 3 / 4
            bullet.rect.x = int(self.rect.x + (player_rect_w_percentage 
                                               * self.rect.width))
            bullet.rect.y = self.rect.y
            
            self._bullets_list.empty()
            self._bullets_list.add(bullet)
                    
            self._previous_time = ticks
            
            self._animation_change += 1
    
    def _go_left(self, elapsed_seconds_since_last_frame):
        """Moves the player left.
        
        Args:
            elapsed_seconds_since_last_frame (float): The number of seconds 
                since the last frame.
        
        """
        
        if (self.rect.x > 0):
            screen_w_percentage_for_movement = 1 / 2
            
            self.rect.x -= int(screen_w_percentage_for_movement 
                               * self._screen_res[0] 
                               * elapsed_seconds_since_last_frame)
        else:
            self.rect.x = 0

    def _go_right(self, elapsed_seconds_since_last_frame):
        """Moves the player right.
        
        Args:
            elapsed_seconds_since_last_frame (float): The number of seconds 
                since the last frame.
        
        """
        
        if (self.rect.x < (self._screen_res[0] - self.rect.width)):
            screen_w_percentage_for_movement = 1 / 2
            
            self.rect.x += int(screen_w_percentage_for_movement 
                               * self._screen_res[0] 
                               * elapsed_seconds_since_last_frame)
        else:
            self.rect.x = self._screen_res[0] - self.rect.width
        
    def reset_position(self):
        self.rect.x = int(self._screen_res[0] / 2)
        self.rect.y = self._screen_res[1] - self.rect.height
    
    @property
    def health(self):
        return self._health
            
    def change_health(self, health):
        """Updates the player's health and plays sound effects.
        
        Args:
            health (int): The amount of health to add to or subtract from the 
                player.
        
        """
        
        if (health < 0):
            self._hurt = True
            
            while (pygame.mixer.find_channel() is None):
                pygame.mixer.set_num_channels(pygame.mixer.get_num_channels() 
                                              + 1)
                
            pygame.mixer.find_channel().play(self._hurt_sound)
        
        self._health += health
        
        if (self._health > 100):
            self._health = 100
        elif (self._health < 0):
            self._health = 0
            
            while (pygame.mixer.find_channel() is None):
                pygame.mixer.set_num_channels(pygame.mixer.get_num_channels() 
                                              + 1)
                
            pygame.mixer.find_channel().play(self._death_sound)
            
    def reset_health(self):
        self._health = 100
        
    @property
    def armor(self):
        return self._armor
            
    def change_armor(self, armor):
        """Updates the player's armor and plays sound effect.
        
        Args:
            armor (int): The amount of armor to add to or subtract from the 
                player.
        
        """
        
        if (armor < 0):
            self._hurt = True
            
            while (pygame.mixer.find_channel() is None):
                pygame.mixer.set_num_channels(pygame.mixer.get_num_channels() 
                                              + 1)
                
            pygame.mixer.find_channel().play(self._hurt_sound)
        
        self._armor += armor
        
        if (self._armor > 100):
            self._armor = 100
        elif (self._armor < 0):
            self._armor = 0
            
    def reset_armor(self):
        self._armor = 0
        
    @property
    def bullets_list(self):
        return self._bullets_list
    
    def reset_damage(self):
        self.damage = 25