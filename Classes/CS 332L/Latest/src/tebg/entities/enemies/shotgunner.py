"""
Created on May 11, 2015

@author: Tony Liang

Source:
Event Timer by furas: https://stackoverflow.com/questions/40205000/pygame-datet
                      ime-troubles

"""

import pygame
from tebg.entities.enemies.enemy import Enemy
from tebg.entities.bullets.enemy_bullet import EnemyBullet

class Shotgunner(Enemy):
    """This class represents the shotgunner.
    
    The shotgunner moves down the screen. When the shotgunner is shooting and/or 
    receiving damage, the shotgunner is animated. The shotgunner shoots bullets 
    when it spotted the player. The shotgunner's audio for when the shotgunner 
    shoots bullets is initialized and played when appropriate.
    
    Attributes:
        image (pygame.Surface): The sprite's image.
        rect (pygame.Rect): The sprite's image's rectangle.
        dirty (int): Determines whether to repaint the sprite every frame.
    
    """
    
    def __init__(self, images_storage, audio_storage, difficulty, layer):
        """Sets up the shotgunner on creation.
        
        Args:
            images_storage (ImagesStorage): ImagesStorage object that stores all 
                game images.
            audio_storage (AudioStorage): AudioStorage object that stores all 
                game audio.
            difficulty (int): The difficulty value.
            layer (int): The layer the shotgunner is to be drawn in.
        
        """
        
        # Calls the parent class (Enemy) constructor.
        super().__init__(images_storage, audio_storage)
        
        self._imgs_storage = images_storage
        
        # Images for shotgunner shooting and hurt animation.
        for frame in range(1, 5):
            img = (self._imgs_storage
                   .get_image("../../Sprites/Enemies/shotgunner_" + str(frame) 
                              + ".png"))
            screen_w_or_h_percentage = 1 / 450
            scaled_img = (pygame.transform
                          .smoothscale(img, (round(img.get_rect().width 
                                                   * (screen_w_or_h_percentage 
                                                      * self._min_w_or_h)), 
                                             round(img.get_rect().height 
                                                   * (screen_w_or_h_percentage 
                                                      * self._min_w_or_h)))))
            self._shoot_frames.append(scaled_img.convert_alpha())
            
        self.image = self._shoot_frames[0]
        self.rect = self.image.get_rect()
        
        self._shotgun_sound = (audio_storage
                               .get_audio('../../Sounds/shotgun.ogg'))
        self._shotgun_sound.set_volume(0.25)
        
        self._health = 150
        self._damage = 10 * difficulty
        
        self.dirty = 2
        self._layer = layer
 
    def update(self, elapsed_seconds_since_last_frame):
        """
        Updates the movement, animation, shooting, and sound effect.
        
        Args:
            elapsed_seconds_since_last_frame (float): The number of seconds 
                since the last frame.
        
        """
        
        self.rect = self.image.get_rect(x = self.rect.x, y = self.rect.y)
        
        screen_h_percentage_for_movement = 1 / 3
        
        self.rect.y += int(screen_h_percentage_for_movement 
                           * self._screen_res[1] 
                           * elapsed_seconds_since_last_frame)
        
        if not self._death:
            if self.spotted:
                if self._hurt:
                    if ((self._animation_change % 2) == 0):
                        self.image = self._shoot_frames[3]
                    else:
                        self.image = self._shoot_frames[2]
                else:
                    if ((self._animation_change % 2) == 0):
                        self.image = self._shoot_frames[1]
                    else:
                        self.image = self._shoot_frames[0]
            
                ticks = pygame.time.get_ticks()
                
                bullet_delay_in_millis = 250
                
                if ((ticks - self._previous_time) > bullet_delay_in_millis):
                    while (pygame.mixer.find_channel() is None):
                        pygame.mixer.set_num_channels(pygame.mixer
                                                      .get_num_channels() + 1)
                        
                    pygame.mixer.find_channel().play(self._shotgun_sound)
                    
                    bullet = EnemyBullet(self._imgs_storage, 4)
                    bullet_l = EnemyBullet(self._imgs_storage, 4)
                    bullet_r = EnemyBullet(self._imgs_storage, 4)
                    bullet.damage = self._damage
                    bullet_l.damage = self._damage
                    bullet_r.damage = self._damage
                    
                    shotgunner_rect_w_percentage = 1 / 4
                    shotgunner_rect_w_2_percentage = 1 / 21
                    bullet.rect.x = int(self.rect.x 
                                        + (shotgunner_rect_w_percentage 
                                           * self.rect.width))
                    bullet_l.rect.x = int(bullet.rect.x 
                                          - (shotgunner_rect_w_2_percentage 
                                             * self.rect.width))
                    bullet_r.rect.x = int(bullet.rect.x 
                                          + (shotgunner_rect_w_2_percentage 
                                             * self.rect.width))
                    bullet.rect.y = bullet_l.rect.y = bullet_r.rect.y = (self
                                                                         .rect.y 
                                                                         + self
                                                                         .rect
                                                                         .height
                                                                         )
                    
                    self._bullets_list.empty()
                    self._bullets_list.add(bullet)
                    self._bullets_list.add(bullet_l)
                    self._bullets_list.add(bullet_r)
                    
                    self._previous_time = ticks
                    
                    self._animation_change += 1
            else:
                self._previous_time = pygame.time.get_ticks()
                
                if self._hurt:
                    if ((self._animation_change % 2) == 0):
                        self.image = self._shoot_frames[2]
                    else:
                        self.image = self._shoot_frames[0]
                        
                    self._animation_change += 1
                else:
                    self.image = self._shoot_frames[0]
        else:
            self.image = self._death_image
        
        self.spotted = False
        self.reset_hurt()