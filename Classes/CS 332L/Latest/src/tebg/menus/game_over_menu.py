"""
Created on May 28, 2017

@author: Tony Liang

"""

import pygame
import sys
import tebg.constants as constants
from pygame.locals import *

class GameOverMenu(object):
    """This class represents the Game Over Menu."""

    def __init__(self):
        """Sets up the game over menu on creation."""
        
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
        
        self._game_over_title_text = font.render("Game Over", True, 
                                                 constants.WHITE)
        self._return_text = font.render("Click or press any key to return to " 
                                        + "the main menu.", True, 
                                        constants.WHITE)
        
        self._game_over_title_text_rect = self._game_over_title_text.get_rect()
        self._return_text_rect = self._return_text.get_rect()
        
    def draw_game_over_menu(self, menu_choice):
        """Displays the game over menu.
        
        Args:
            menu_choice (int): Current menu option selected.

        Returns:
            int: The menu choice is one of two values: DIFFICULTY_MENU (1) or 
                GAME_OVER_MENU (11).
        
        """
        
        credits_centerx = int(self._screen_res[0] / 2)
        screen_h_percentage = 1 / 8
        
        # Centers title text.
        self._game_over_title_text_rect.centerx = credits_centerx
        self._game_over_title_text_rect.centery = int(self._screen_res[1] 
                                                      * screen_h_percentage)
        self._screen.blit(self._game_over_title_text, 
                          self._game_over_title_text_rect)
        
        # Displays game over.
        self._return_text_rect.centerx = credits_centerx
        self._return_text_rect.centery = int(self._screen_res[1] 
                                             - (self._screen_res[1] 
                                                * screen_h_percentage))
        self._screen.blit(self._return_text, self._return_text_rect)
        
        for event in pygame.event.get():
            if (event.type == QUIT):  # QUIT event to exit the game.
                pygame.quit()
                sys.exit()
            elif (event.type == pygame.KEYDOWN):
                return constants.DIFFICULTY_MENU # Back to the main menu.
            elif (event.type == MOUSEBUTTONDOWN):
                buttons = pygame.mouse.get_pressed()

                if buttons[0]:
                    if (menu_choice == constants.GAME_OVER_MENU):
                        # Back to the main menu.
                        return constants.DIFFICULTY_MENU
        
        return menu_choice