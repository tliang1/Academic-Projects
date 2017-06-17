"""
Created on May 27, 2015

@author: Tony Liang

"""

import pygame
import sys
import tebg.constants as constants
from pygame.locals import *

class DifficultyMenu():
    """This class represents the Difficulty Menu."""
    
    def __init__(self, audio_storage):
        """Sets up the difficulty menu on creation."""
        
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
        
        self._game_title_text = font.render("The Endless Battle", True, 
                                            constants.WHITE)
        self._easy_text = font.render("Easy", True, constants.WHITE)
        self._medium_text = font.render("Medium", True, constants.WHITE)
        self._hard_text = font.render("Hard", True, constants.WHITE)
        self._back_text = font.render("Back", True, constants.WHITE)
        
        self._game_title_text_rect = self._game_title_text.get_rect()
        self._easy_text_rect = self._easy_text.get_rect()
        self._medium_text_rect = self._medium_text.get_rect()
        self._hard_text_rect = self._hard_text.get_rect()
        self._back_text_rect = self._back_text.get_rect()
        
    def draw_difficulty_menu(self, menu_choice):
        """Displays the difficulty menu.
        
        Args:
            menu_choice (int): Current menu option selected.

        Returns:
            int: The menu choice is one of the eight integer values:
                EASY_DIFFICULTY (-1), MEDIUM_DIFFICULTY (-2), 
                HARD_DIFFICULTY (-3), DIFFICULTY_MENU (1),
                HIGHLIGHT_EASY_DIFFICULTY (5), HIGHLIGHT_MEDIUM_DIFFICULTY (6), 
                HIGHLIGHT_HARD_DIFFICULTY (7), 
                HIGHLIGHT_DIFFICULTY_MENU_BACK_BUTTON (8).
        
        """
        
        credits_centerx = int(self._screen_res[0] / 2)
        credits_centery_starting_point = self._screen_res[1] / 4
        credits_centery_change = self._screen_res[1] / 8
        screen_h_percentage = 1 / 8
        
        # Centers title text.
        self._game_title_text_rect.centerx = credits_centerx
        self._game_title_text_rect.centery = int(self._screen_res[1] 
                                                 * screen_h_percentage)
        self._screen.blit(self._game_title_text, self._game_title_text_rect)
            
        # Displays menu options.
        self._easy_text_rect.centerx = credits_centerx
        self._easy_text_rect.centery = int(credits_centery_starting_point 
                                           + credits_centery_change)
        
        if (menu_choice == constants.HIGHLIGHT_EASY_DIFFICULTY):
            self._screen.fill(constants.MEDIUMGREEN, self._easy_text_rect)
        else:
            self._screen.fill(constants.GREEN, self._easy_text_rect)
            
        self._screen.blit(self._easy_text, self._easy_text_rect)
        
        self._medium_text_rect.centerx = credits_centerx
        self._medium_text_rect.centery = int(credits_centery_starting_point 
                                             + (2 * credits_centery_change))
        
        if (menu_choice == constants.HIGHLIGHT_MEDIUM_DIFFICULTY):
            self._screen.fill(constants.MEDIUMGREEN, self._medium_text_rect)
        else:
            self._screen.fill(constants.GREEN, self._medium_text_rect)
            
        self._screen.blit(self._medium_text, self._medium_text_rect)
        
        self._hard_text_rect.centerx = credits_centerx
        self._hard_text_rect.centery = int(credits_centery_starting_point 
                                           + (3 * credits_centery_change))
        
        if (menu_choice == constants.HIGHLIGHT_HARD_DIFFICULTY):
            self._screen.fill(constants.MEDIUMGREEN, self._hard_text_rect)
        else:
            self._screen.fill(constants.GREEN, self._hard_text_rect)
            
        self._screen.blit(self._hard_text, self._hard_text_rect)
        
        self._back_text_rect.centerx = credits_centerx
        self._back_text_rect.centery = int(credits_centery_starting_point 
                                           + (4 * credits_centery_change))
        
        if (menu_choice == constants.HIGHLIGHT_DIFFICULTY_MENU_BACK_BUTTON):
            self._screen.fill(constants.MEDIUMGREEN, self._back_text_rect)
        else:
            self._screen.fill(constants.GREEN, self._back_text_rect)
            
        self._screen.blit(self._back_text, self._back_text_rect)
        
        for event in pygame.event.get():
            if (event.type == QUIT):  # QUIT event to exit the game.
                pygame.quit()
                sys.exit()
            elif (event.type == pygame.KEYDOWN):
                if (event.key == K_ESCAPE):
                    return constants.DIFFICULTY_MENU
                elif (event.key == K_UP):
                    self._play_menu_sound()
                    
                    if (menu_choice == constants.HIGHLIGHT_EASY_DIFFICULTY):
                        menu_choice = (constants
                                       .HIGHLIGHT_DIFFICULTY_MENU_BACK_BUTTON)
                    else:
                        menu_choice -= 1
                elif (event.key == K_DOWN):
                    self._play_menu_sound()
                    
                    if (menu_choice 
                        == constants.HIGHLIGHT_DIFFICULTY_MENU_BACK_BUTTON):
                        menu_choice = constants.HIGHLIGHT_EASY_DIFFICULTY
                    else:
                        menu_choice += 1
                elif ((event.key == K_RETURN) or (event.key == K_SPACE)):
                    if (menu_choice == constants.HIGHLIGHT_EASY_DIFFICULTY):
                        return constants.EASY_DIFFICULTY # Starts game on Easy.
                    elif (menu_choice == constants.HIGHLIGHT_MEDIUM_DIFFICULTY):
                        # Starts game on Medium.
                        return constants.MEDIUM_DIFFICULTY
                    elif (menu_choice == constants.HIGHLIGHT_HARD_DIFFICULTY):
                        return constants.HARD_DIFFICULTY # Starts game on Hard.
                    else:
                        # Back to the main menu.
                        return constants.DIFFICULTY_MENU
            elif (event.type == MOUSEMOTION):
                mouse_coords = pygame.mouse.get_pos()
                
                if (self._easy_text_rect.y <= mouse_coords[1] 
                    <= (self._easy_text_rect.y + self._easy_text_rect.height)):
                    self._play_menu_sound()
                    menu_choice = constants.HIGHLIGHT_EASY_DIFFICULTY
                elif (self._medium_text_rect.y <= mouse_coords[1] 
                      <= (self._medium_text_rect.y 
                          + self._medium_text_rect.height)):
                    self._play_menu_sound()
                    menu_choice = constants.HIGHLIGHT_MEDIUM_DIFFICULTY
                elif (self._hard_text_rect.y <= mouse_coords[1] 
                      <= (self._hard_text_rect.y 
                          + self._hard_text_rect.height)):
                    self._play_menu_sound()
                    menu_choice = constants.HIGHLIGHT_HARD_DIFFICULTY
                elif (self._back_text_rect.y <= mouse_coords[1] 
                      <= (self._back_text_rect.y 
                          + self._back_text_rect.height)):
                    self._play_menu_sound()
                    menu_choice = (constants
                                   .HIGHLIGHT_DIFFICULTY_MENU_BACK_BUTTON)
            elif (event.type == MOUSEBUTTONDOWN):
                buttons = pygame.mouse.get_pressed()

                if buttons[0]:
                    if (menu_choice == constants.HIGHLIGHT_EASY_DIFFICULTY):
                        return constants.EASY_DIFFICULTY # Starts game on Easy.
                    elif (menu_choice == constants.HIGHLIGHT_MEDIUM_DIFFICULTY):
                        # Starts game on Medium.
                        return constants.MEDIUM_DIFFICULTY
                    elif (menu_choice == constants.HIGHLIGHT_HARD_DIFFICULTY):
                        return constants.HARD_DIFFICULTY # Starts game on Hard.
                    else:
                        # Back to the main menu.
                        return constants.DIFFICULTY_MENU
        
        return menu_choice
    
    def _play_menu_sound(self):
        while (pygame.mixer.find_channel() is None):
            pygame.mixer.set_num_channels(pygame.mixer.get_num_channels() + 1)
            
        pygame.mixer.find_channel().play(self._menu_sound)