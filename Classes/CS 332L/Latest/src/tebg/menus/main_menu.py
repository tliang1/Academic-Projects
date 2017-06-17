"""
Created on Apr 25, 2015

@author: Tony Liang

"""

import pygame
import sys
import tebg.constants as constants
from pygame.locals import *

class MainMenu():
    """This class represents the Main Menu"""
    
    def __init__(self, audio_storage):
        """Sets up the main menu on creation."""
        
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
        self._play_game_text = font.render("Play Game", True, constants.WHITE)
        self._how_to_play_text = font.render("How To Play", True, 
                                             constants.WHITE)
        self._credits_text = font.render("Credits", True, constants.WHITE)
        self._quit_text = font.render("Quit", True, constants.WHITE)
        
        self._game_title_text_rect = self._game_title_text.get_rect()
        self._play_game_text_rect = self._play_game_text.get_rect()
        self._how_to_play_text_rect = self._how_to_play_text.get_rect()
        self._credits_text_rect = self._credits_text.get_rect()
        self._quit_text_rect = self._quit_text.get_rect()
    
    def draw_main_menu(self, menu_choice):
        """Displays the main menu with options.
        
        Args:
            menu_choice (int): Current menu option selected.

        Returns:
            int: The menu choice is one of the four integer values: 
                DIFFICULTY_MENU (1), HOW_TO_PLAY_MENU (2), CREDITS_MENU (3), 
                QUIT_BUTTON (4), HIGHLIGHT_EASY_DIFFICULTY (5), 
                HOW_TO_PLAY_MENU_BACK_BUTTON (9), CREDITS_MENU_BACK_BUTTON (10).
        
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
        self._play_game_text_rect.centerx = credits_centerx
        self._play_game_text_rect.centery = int(credits_centery_starting_point 
                                                + credits_centery_change)
        
        if (menu_choice == constants.DIFFICULTY_MENU):
            self._screen.fill(constants.MEDIUMGREEN, self._play_game_text_rect)
        else:
            self._screen.fill(constants.GREEN, self._play_game_text_rect)
            
        self._screen.blit(self._play_game_text, self._play_game_text_rect)
        
        self._how_to_play_text_rect.centerx = credits_centerx
        (self._how_to_play_text_rect
         .centery) = int(credits_centery_starting_point 
                         + (2 * credits_centery_change))
        
        if (menu_choice == constants.HOW_TO_PLAY_MENU):
            self._screen.fill(constants.MEDIUMGREEN, 
                              self._how_to_play_text_rect)
        else:
            self._screen.fill(constants.GREEN, self._how_to_play_text_rect)
            
        self._screen.blit(self._how_to_play_text, self._how_to_play_text_rect)
        
        self._credits_text_rect.centerx = credits_centerx
        self._credits_text_rect.centery = int(credits_centery_starting_point 
                                              + (3 * credits_centery_change))
        
        if (menu_choice == constants.CREDITS_MENU):
            self._screen.fill(constants.MEDIUMGREEN, self._credits_text_rect)
        else:
            self._screen.fill(constants.GREEN, self._credits_text_rect)
            
        self._screen.blit(self._credits_text, self._credits_text_rect)
        
        self._quit_text_rect.centerx = credits_centerx
        self._quit_text_rect.centery = int(credits_centery_starting_point 
                                           + (4 * credits_centery_change))
        
        if (menu_choice == constants.QUIT_BUTTON):
            self._screen.fill(constants.MEDIUMGREEN, self._quit_text_rect)
        else:
            self._screen.fill(constants.GREEN, self._quit_text_rect)
            
        self._screen.blit(self._quit_text, self._quit_text_rect)
        
        for event in pygame.event.get():
            if (event.type == QUIT):  # QUIT event to exit the game.
                pygame.quit()
                sys.exit()
            elif (event.type == pygame.KEYDOWN):
                if (event.key == K_ESCAPE):
                    pygame.quit()
                    sys.exit()
                elif (event.key == K_UP):
                    self._play_menu_sound()
                    
                    if (menu_choice == constants.DIFFICULTY_MENU):
                        menu_choice = constants.QUIT_BUTTON
                    else:
                        menu_choice -= 1
                elif (event.key == K_DOWN):
                    self._play_menu_sound()
                    
                    if (menu_choice == constants.QUIT_BUTTON):
                        menu_choice = constants.DIFFICULTY_MENU
                    else:
                        menu_choice += 1
                elif ((event.key == K_RETURN) or (event.key == K_SPACE)):
                    if (menu_choice == constants.DIFFICULTY_MENU):
                        # Selects difficulty.
                        return constants.HIGHLIGHT_EASY_DIFFICULTY
                    elif (menu_choice == constants.HOW_TO_PLAY_MENU):
                        # How To Play.
                        return constants.HOW_TO_PLAY_MENU_BACK_BUTTON
                    elif (menu_choice == constants.CREDITS_MENU):
                        return constants.CREDITS_MENU_BACK_BUTTON # Credits.
                    else:
                        pygame.quit()
                        sys.exit()
            elif (event.type == MOUSEMOTION):
                mouse_coords = pygame.mouse.get_pos()
                
                if (self._play_game_text_rect.y <= mouse_coords[1] 
                    <= (self._play_game_text_rect.y 
                        + self._play_game_text_rect.height)):
                    self._play_menu_sound()
                    menu_choice = constants.DIFFICULTY_MENU
                elif (self._how_to_play_text_rect.y <= mouse_coords[1] 
                      <= (self._how_to_play_text_rect.y 
                          + self._how_to_play_text_rect.height)):
                    self._play_menu_sound()
                    menu_choice = constants.HOW_TO_PLAY_MENU
                elif (self._credits_text_rect.y <= mouse_coords[1] 
                      <= (self._credits_text_rect.y 
                          + self._credits_text_rect.height)):
                    self._play_menu_sound()
                    menu_choice = constants.CREDITS_MENU
                elif (self._quit_text_rect.y <= mouse_coords[1] 
                      <= (self._quit_text_rect.y 
                          + self._quit_text_rect.height)):
                    self._play_menu_sound()
                    menu_choice = constants.QUIT_BUTTON
            elif (event.type == MOUSEBUTTONDOWN):
                buttons = pygame.mouse.get_pressed()

                if buttons[0]:
                    if (menu_choice == constants.DIFFICULTY_MENU):
                        # Selects difficulty.
                        return constants.HIGHLIGHT_EASY_DIFFICULTY 
                    elif (menu_choice == constants.HOW_TO_PLAY_MENU):
                        # How To Play.
                        return constants.HOW_TO_PLAY_MENU_BACK_BUTTON
                    elif (menu_choice == constants.CREDITS_MENU):
                        return constants.CREDITS_MENU_BACK_BUTTON # Credits.
                    elif (menu_choice == constants.QUIT_BUTTON):
                        pygame.quit()
                        sys.exit()
        
        return menu_choice
    
    def _play_menu_sound(self):
        while (pygame.mixer.find_channel() is None):
            pygame.mixer.set_num_channels(pygame.mixer.get_num_channels() + 1)
            
        pygame.mixer.find_channel().play(self._menu_sound)