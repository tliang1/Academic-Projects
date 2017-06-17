"""
Created on May 27, 2015

@author: Tony Liang

"""

import pygame
import sys
import tebg.constants as constants
from pygame.locals import *

class HowToPlayMenu():
    """This class represents the How To Play Menu."""
    
    def __init__(self, images_storage, audio_storage):
        """Sets up the how to play menu on creation."""
        
        self._menu_sound = audio_storage.get_audio('../../Sounds/menu.ogg')
        
        self._screen = pygame.display.get_surface()
        
        self._screen_res = self._screen.get_size()
        min_w_or_h = self._screen_res[0]
        
        if (min_w_or_h > self._screen_res[1]):
            min_w_or_h = self._screen_res[1]
        
        screen_w_or_h_percentage_for_font = 1 / 40
        
        # Initializes all text objects.
        font = pygame.font.SysFont('Consolas', 
                                   int(screen_w_or_h_percentage_for_font 
                                       * min_w_or_h), False, False)
        
        self._how_to_play_title_text = font.render("How To Play", True, 
                                                   constants.WHITE)
        
        img = images_storage.get_image("../../Sprites/HUD/how_to_play.png")
        screen_w_or_h_percentage = 1 / 1200
        scaled_img = (pygame.transform
                      .smoothscale(img, (round(img.get_rect().width 
                                               * (screen_w_or_h_percentage 
                                                  * min_w_or_h)), 
                                         round(img.get_rect().height 
                                               * (screen_w_or_h_percentage 
                                                  * min_w_or_h)))))
        self._how_to_play_description = scaled_img.convert_alpha()
        
        self._back_text = font.render("Back", True, constants.WHITE)
        
        self._how_to_play_title_text_rect = (self._how_to_play_title_text
                                             .get_rect())
        self._how_to_play_description_rect = (self._how_to_play_description
                                              .get_rect())
        self._back_text_rect = self._back_text.get_rect()
        
    def draw_how_to_play_menu(self, menu_choice):
        """Displays the how to play menu.
        
        Args:
            menu_choice (int): Current menu option selected.

        Returns:
            int: The menu choice is one of two values: DIFFICULTY_MENU (1) or 
                HOW_TO_PLAY_MENU_BACK_BUTTON (9).
        
        """
        
        credits_centerx = int(self._screen_res[0] / 2)
        screen_h_percentage = 1 / 8
        
        # Centers title text.
        self._how_to_play_title_text_rect.centerx = credits_centerx
        self._how_to_play_title_text_rect.centery = int(self._screen_res[1] 
                                                        * screen_h_percentage)
        self._screen.blit(self._how_to_play_title_text, 
                          self._how_to_play_title_text_rect)
        
        screen_h_percentage = 1 / 32
        
        # Displays how to play.
        self._how_to_play_description_rect.centerx = credits_centerx
        (self._how_to_play_description_rect
         .y) = int(self._how_to_play_title_text_rect.bottom 
                   + (self._screen_res[1] * screen_h_percentage))
        self._screen.blit(self._how_to_play_description, 
                          self._how_to_play_description_rect)
        
        self._back_text_rect.centerx = int(self._screen_res[0] 
                                           - self._back_text_rect.width)
        self._back_text_rect.centery = int(self._screen_res[1] 
                                           - self._back_text_rect.height)
        self._screen.fill(constants.MEDIUMGREEN, self._back_text_rect)
        self._screen.blit(self._back_text, self._back_text_rect)
        
        for event in pygame.event.get():
            if (event.type == QUIT):  # QUIT event to exit the game.
                pygame.quit()
                sys.exit()
            elif (event.type == pygame.KEYDOWN):
                if ((event.key == K_ESCAPE) or (event.key == K_RETURN) 
                    or (event.key == K_SPACE)):
                    return constants.DIFFICULTY_MENU # Back to the main menu.
                elif ((event.key == K_UP) or (event.key == K_DOWN)):
                    self._play_menu_sound()
                    menu_choice = constants.HOW_TO_PLAY_MENU_BACK_BUTTON
            elif (event.type == MOUSEMOTION):
                mouse_coords = pygame.mouse.get_pos()
                
                if (self._back_text_rect.y <= mouse_coords[1] 
                    <= (self._back_text_rect.y + self._back_text_rect.height)):
                    self._play_menu_sound()
                    menu_choice = constants.HOW_TO_PLAY_MENU_BACK_BUTTON
            elif (event.type == MOUSEBUTTONDOWN):
                buttons = pygame.mouse.get_pressed()

                if buttons[0]:
                    if (menu_choice == constants.HOW_TO_PLAY_MENU_BACK_BUTTON):
                        # Back to the main menu.
                        return constants.DIFFICULTY_MENU
        
        return menu_choice
    
    def _play_menu_sound(self):
        while (pygame.mixer.find_channel() is None):
            pygame.mixer.set_num_channels(pygame.mixer.get_num_channels() + 1)
            
        pygame.mixer.find_channel().play(self._menu_sound)