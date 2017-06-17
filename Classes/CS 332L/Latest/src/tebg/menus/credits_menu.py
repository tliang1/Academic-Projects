"""
Created on May 27, 2015

@author: Tony Liang

"""

import pygame
import sys
import tebg.constants as constants
from pygame.locals import *

class CreditsMenu():
    """This class represents the Credits Menu."""
    
    def __init__(self, audio_storage):
        """Sets up the credits menu on creation."""
        
        self._menu_sound = audio_storage.get_audio('../../Sounds/menu.ogg')
        
        self._screen = pygame.display.get_surface()
        
        self._screen_res = self._screen.get_size()
        min_w_or_h = self._screen_res[0]
        
        if (min_w_or_h > self._screen_res[1]):
            min_w_or_h = self._screen_res[1]
        
        screen_w_or_h_percentage_for_font = 1 / 40
        percentage_for_credits_list_font = 1 / 60
        
        # Initializes all text objects.
        font = pygame.font.SysFont('Consolas', 
                                   int(screen_w_or_h_percentage_for_font 
                                       * min_w_or_h), False, False)
        credits_list_font = (pygame.font
                             .SysFont('Consolas', 
                                      int(percentage_for_credits_list_font 
                                          * min_w_or_h), False, False))
        
        self._credits_title_text = font.render("Credits", True, constants.WHITE)
        self._created_by_text = credits_list_font.render("Created By", True, 
                                                         constants.WHITE)
        self._creator_text = credits_list_font.render("Tony Liang", True, 
                                                      constants.WHITE)
        self._design_by_text = credits_list_font.render("Design By", True, 
                                                        constants.WHITE)
        self._designer_text = credits_list_font.render("Tony Liang", True, 
                                                       constants.WHITE)
        self._graphics_by_text = credits_list_font.render("Graphics By", True, 
                                                          constants.WHITE)
        self._artists_text = credits_list_font.render("Tony Liang", True, 
                                                      constants.WHITE)
        self._images_from_text = credits_list_font.render("Images from Kenney.n" 
                                                          + "l:", True, 
                                                          constants.WHITE)
        kenney_license_url = ("https://creativecommons.org/publicdomain/zero/1." 
                              + "0/")
        self._license_type_text = credits_list_font.render("Under Creative Comm" 
                                                           + "ons 0 License (" 
                                                           + kenney_license_url 
                                                           + "):", True, 
                                                           constants.WHITE)
        kenney_assets_url = "https://kenney.nl/assets/topdown-shooter/"
        self._artists_2_text = credits_list_font.render("Topdown Shooter (" 
                                                        + kenney_assets_url 
                                                        + ") by Kenney", True, 
                                                        constants.WHITE)
        self._images_from_2_text = credits_list_font.render("Images from Opencl" 
                                                            + "ipart.org:", 
                                                            True, 
                                                            constants.WHITE)
        open_clip_art_license_url = ("https://creativecommons.org/publicdomain/" 
                                     + "zero/1.0/")
        self._license_type_2_text = (credits_list_font
                                     .render("Under Creative Commons 0 License " 
                                             + "(" + open_clip_art_license_url 
                                             + "):", True, constants.WHITE))
        easter_crown_of_thorns_url = ("https://openclipart.org/detail/216675/ea" 
                                      + "ster-crown-of-thorns/")
        self._artists_3_text = (credits_list_font
                                .render("Easter - Crown of Thorns (" 
                                        + easter_crown_of_thorns_url 
                                        + ") by logoscambodia", True, 
                                        constants.WHITE))
        face_mask_icon_url = ("https://openclipart.org/detail/85579/face-mask-i" 
                              + "con/")
        self._artists_4_text = credits_list_font.render("face mask icon (" 
                                                        + face_mask_icon_url 
                                                        + ") by netalloy", True, 
                                                        constants.WHITE)
        glossy_shield_3_url = ("https://openclipart.org/detail/78511/glossy-shi" 
                               + "elds-3/")
        self._artists_5_text = credits_list_font.render("Glossy shield 3 (" 
                                                        + glossy_shield_3_url 
                                                        + ") by inky2010", True, 
                                                        constants.WHITE)
        mouse_icon_url = "https://openclipart.org/detail/24771/mouse-icon/"
        self._artists_6_text = credits_list_font.render("Mouse icon (" 
                                                        + mouse_icon_url 
                                                        + ") by Anonymous", 
                                                        True, constants.WHITE)
        pow_url = "https://openclipart.org/detail/122959/pow/"
        self._artists_7_text = credits_list_font.render("Pow (" + pow_url + ") " 
                                                        + "by Viscious-Speed", 
                                                        True, constants.WHITE)
        left_security_url = "https://openclipart.org/detail/92167/security/"
        self._artists_8_text = credits_list_font.render("security (" 
                                                        + left_security_url 
                                                        + ") by yves_guillou", 
                                                        True, constants.WHITE)
        right_security_url = "https://openclipart.org/detail/92161/security/"
        self._artists_9_text = credits_list_font.render("security (" 
                                                        + right_security_url 
                                                        + ") by yves_guillou", 
                                                        True, constants.WHITE)
        skull_and_crossbones_url = ("https://openclipart.org/detail/1448/skull-" 
                                    + "and-crossbones/")
        self._artists_10_text = (credits_list_font
                                 .render("skull and crossbones (" 
                                         + skull_and_crossbones_url + ") by rya" 
                                         + "nlerch", True, constants.WHITE))
        self._images_from_3_text = credits_list_font.render("Images from OpenGa" 
                                                            + "meArt.org:", 
                                                            True, 
                                                            constants.WHITE)
        open_game_art_license_url = ("https://creativecommons.org/publicdomain/" 
                                     + "zero/1.0/")
        self._license_type_3_text = (credits_list_font
                                     .render("Under Creative Commons 0 License " 
                                             + "(" + open_game_art_license_url 
                                             + "):", True, constants.WHITE))
        explosions_url = "https://opengameart.org/content/explosions/"
        self._artists_11_text = credits_list_font.render("Explosions (" 
                                                         + explosions_url + ") " 
                                                         + "by GameProgrammingS" 
                                                         + "lave", True, 
                                                         constants.WHITE)
        grass_texture_seamless_2d_url = ("https://opengameart.org/content/grass" 
                                         + "-textureseamless-2d/")
        self._artists_12_text = (credits_list_font
                                 .render("Grass Texture, Seamless 2D (" 
                                         + grass_texture_seamless_2d_url + ") b" 
                                         + "y surfacecurve", True, 
                                         constants.WHITE))
        powerup_animated_orb_url = ("https://opengameart.org/content/powerup-an" 
                                    + "imated-orb/")
        self._artists_13_text = (credits_list_font
                                 .render("Powerup (Animated Orb) (" 
                                         + powerup_animated_orb_url + ") by Jos" 
                                         + "eph Crown", True, constants.WHITE))
        self._music_and_sounds_from_text = (credits_list_font
                                            .render("Music and Sounds from Free" 
                                                    + "sound.org", True, 
                                                    constants.WHITE))
        freesound_license_url = ("https://creativecommons.org/publicdomain/zero" 
                                 + "/1.0/")
        self._license_type_4_text = (credits_list_font
                                     .render("Under Creative Commons 0 License " 
                                             + "(" + freesound_license_url 
                                             + "):", True, constants.WHITE))
        agony_url = "https://freesound.org/people/Mediapaja2009/sounds/162157/"
        self._musicians_text = credits_list_font.render("Agony (" + agony_url 
                                                        + ") by  Mediapaja2009", 
                                                        True, constants.WHITE)
        bear_trap_url = ("https://freesound.org/people/ThePriest909/sounds/2782" 
                         + "03/")
        self._musicians_2_text = credits_list_font.render("Bear Trap (" 
                                                          + bear_trap_url + ") " 
                                                          + "by ThePriest909", 
                                                          True, constants.WHITE)
        cloth_ripping_url = ("https://freesound.org/people/reg7783/sounds/13344" 
                             + "0/")
        self._musicians_3_text = credits_list_font.render("cloth_ripping (" 
                                                          + cloth_ripping_url 
                                                          + ") by reg7783", 
                                                          True, constants.WHITE)
        crushing_leaves_url = ("https://freesound.org/people/hmoosher/sounds/38" 
                               + "1195/")
        self._musicians_4_text = credits_list_font.render("crushing leaves (" 
                                                          + crushing_leaves_url 
                                                          + ") by hmoosher", 
                                                          True, constants.WHITE)
        grunts_of_pain_by_military_soldiers_url = ("https://freesound.org/peopl" 
                                                   + "e/qubodup/sounds/166944/")
        gopbms_text = ("Grunts of pain by military soldiers (" 
                       + grunts_of_pain_by_military_soldiers_url 
                       + ") by qubodup")
        self._musicians_5_text = credits_list_font.render(gopbms_text, True, 
                                                          constants.WHITE)
        happy_8bit_pixel_adventure_url = ("https://freesound.org/people/edtijo/" 
                                          + "sounds/240376/")
        h_8_pa_text = ("Happy 8bit Pixel Adventure (" 
                       + happy_8bit_pixel_adventure_url + ") by edtijo")
        self._musicians_6_text = credits_list_font.render(h_8_pa_text, True, 
                                                          constants.WHITE)
        idunnometloop_url = ("https://freesound.org/people/furbyguy/sounds/3318" 
                             + "76/")
        self._musicians_7_text = credits_list_font.render("iDunnoMetLoop (" 
                                                          + idunnometloop_url 
                                                          + ") by furbyguy", 
                                                          True, constants.WHITE)
        menu_button_url = "https://freesound.org/people/fins/sounds/191592/"
        self._musicians_8_text = credits_list_font.render("menu button (" 
                                                          + menu_button_url 
                                                          + ") by fins", True, 
                                                          constants.WHITE)
        retro_accomplished_sfx_url = ("https://freesound.org/people/suntemple/s" 
                                      + "ounds/253177/")
        self._musicians_9_text = (credits_list_font
                                  .render("Retro \"Accomplished\" SFX (" 
                                          + retro_accomplished_sfx_url 
                                          + ") by suntemple", True, 
                                          constants.WHITE))
        rifle_shooting_url = ("https://freesound.org/people/qubodup/sounds/2389" 
                              + "16/")
        self._musicians_10_text = credits_list_font.render("Rifle Shooting (" 
                                                           + rifle_shooting_url 
                                                           + ") by qubodup", 
                                                           True, 
                                                           constants.WHITE)
        shotgun_url = "https://freesound.org/people/marregheriti/sounds/266105/"
        self._musicians_11_text = credits_list_font.render("shotgun (" 
                                                           + shotgun_url 
                                                           + ") by Marregherit" 
                                                           + "i", True, 
                                                           constants.WHITE)
        small_explosion_url = ("https://freesound.org/people/Polytest/sounds/27" 
                               + "1979/")
        self._musicians_12_text = (credits_list_font
                                   .render("Small Explosion (" 
                                           + small_explosion_url 
                                           + ") by Polytest", True, 
                                           constants.WHITE))
        smgshoot2_url = ("https://freesound.org/people/tehlordoswag420/sounds/2" 
                         + "49779/")
        self._musicians_13_text = credits_list_font.render("smgshoot2 (" 
                                                           + smgshoot2_url 
                                                           + ") by tehlordoswag" 
                                                           + "420", True, 
                                                           constants.WHITE)
        self._back_text = font.render("Back", True, constants.WHITE)
        
        self._credits_title_text_rect = self._credits_title_text.get_rect()
        self._created_by_text_rect = self._created_by_text.get_rect()
        self._creator_text_rect = self._creator_text.get_rect()
        self._design_by_text_rect = self._design_by_text.get_rect()
        self._designer_text_rect = self._designer_text.get_rect()
        self._graphics_by_text_rect = self._graphics_by_text.get_rect()
        self._artists_text_rect = self._artists_text.get_rect()
        self._images_from_text_rect = self._images_from_text.get_rect()
        self._license_type_text_rect = self._license_type_text.get_rect()
        self._artists_2_text_rect = self._artists_2_text.get_rect()
        self._images_from_2_text_rect = self._images_from_2_text.get_rect()
        self._license_type_2_text_rect = self._license_type_2_text.get_rect()
        self._artists_3_text_rect = self._artists_3_text.get_rect()
        self._artists_4_text_rect = self._artists_4_text.get_rect()
        self._artists_5_text_rect = self._artists_5_text.get_rect()
        self._artists_6_text_rect = self._artists_6_text.get_rect()
        self._artists_7_text_rect = self._artists_7_text.get_rect()
        self._artists_8_text_rect = self._artists_8_text.get_rect()
        self._artists_9_text_rect = self._artists_9_text.get_rect()
        self._artists_10_text_rect = self._artists_10_text.get_rect()
        self._images_from_3_text_rect = self._images_from_3_text.get_rect()
        self._license_type_3_text_rect = self._license_type_3_text.get_rect()
        self._artists_11_text_rect = self._artists_11_text.get_rect()
        self._artists_12_text_rect = self._artists_12_text.get_rect()
        self._artists_13_text_rect = self._artists_13_text.get_rect()
        self._music_and_sounds_from_text_rect = (self
                                                 ._music_and_sounds_from_text
                                                 .get_rect())
        self._license_type_4_text_rect = self._license_type_4_text.get_rect()
        self._musicians_text_rect = self._musicians_text.get_rect()
        self._musicians_2_text_rect = self._musicians_2_text.get_rect()
        self._musicians_3_text_rect = self._musicians_3_text.get_rect()
        self._musicians_4_text_rect = self._musicians_4_text.get_rect()
        self._musicians_5_text_rect = self._musicians_5_text.get_rect()
        self._musicians_6_text_rect = self._musicians_6_text.get_rect()
        self._musicians_7_text_rect = self._musicians_7_text.get_rect()
        self._musicians_8_text_rect = self._musicians_8_text.get_rect()
        self._musicians_9_text_rect = self._musicians_9_text.get_rect()
        self._musicians_10_text_rect = self._musicians_10_text.get_rect()
        self._musicians_11_text_rect = self._musicians_11_text.get_rect()
        self._musicians_12_text_rect = self._musicians_12_text.get_rect()
        self._musicians_13_text_rect = self._musicians_13_text.get_rect()
        self._back_text_rect = self._back_text.get_rect()
        
    def draw_credits_menu(self, menu_choice):
        """Displays the credits menu.
        
        Args:
            menu_choice (int): Current menu option selected.

        Returns:
            int: The menu choice is one of two values: DIFFICULTY_MENU (1) or 
                CREDITS_MENU_BACK_BUTTON (10).
        
        """
        
        credits_centerx = int(self._screen_res[0] / 2)
        credits_centery_starting_point = self._screen_res[1] * (3 / 16)
        credits_centery_change = self._screen_res[1] * (3 / 188)
        screen_h_percentage = 1 / 8
        
        # Centers title text.
        self._credits_title_text_rect.centerx = credits_centerx
        self._credits_title_text_rect.centery = int(self._screen_res[1] 
                                                    * screen_h_percentage)
        self._screen.blit(self._credits_title_text, 
                          self._credits_title_text_rect)
        
        # Displays credits.
        self._created_by_text_rect.centerx = credits_centerx
        self._created_by_text_rect.centery = int(credits_centery_starting_point)
        self._screen.blit(self._created_by_text, self._created_by_text_rect)
        
        self._creator_text_rect.centerx = credits_centerx
        self._creator_text_rect.centery = int(credits_centery_starting_point 
                                              + (2 * credits_centery_change))
        self._screen.blit(self._creator_text, self._creator_text_rect)
        
        self._design_by_text_rect.centerx = credits_centerx
        self._design_by_text_rect.centery = int(credits_centery_starting_point 
                                                + (4 * credits_centery_change))
        self._screen.blit(self._design_by_text, self._design_by_text_rect)
        
        self._designer_text_rect.centerx = credits_centerx
        self._designer_text_rect.centery = int(credits_centery_starting_point 
                                               + (6 * credits_centery_change))
        self._screen.blit(self._designer_text, self._designer_text_rect)
        
        self._graphics_by_text_rect.centerx = credits_centerx
        self._graphics_by_text_rect.centery = int(credits_centery_starting_point 
                                                  + (8 
                                                     * credits_centery_change))
        self._screen.blit(self._graphics_by_text, self._graphics_by_text_rect)
        
        self._artists_text_rect.centerx = credits_centerx
        self._artists_text_rect.centery = int(credits_centery_starting_point 
                                              + (10 * credits_centery_change))
        self._screen.blit(self._artists_text, self._artists_text_rect)
        
        self._images_from_text_rect.centerx = credits_centerx
        self._images_from_text_rect.centery = int(credits_centery_starting_point 
                                                  + (12 
                                                     * credits_centery_change))
        self._screen.blit(self._images_from_text, self._images_from_text_rect)
        
        self._license_type_text_rect.centerx = credits_centerx
        (self._license_type_text_rect
         .centery) = int(credits_centery_starting_point 
                         + (13 * credits_centery_change))
        self._screen.blit(self._license_type_text, self._license_type_text_rect)
        
        self._artists_2_text_rect.centerx = credits_centerx
        self._artists_2_text_rect.centery = int(credits_centery_starting_point 
                                                + (14 * credits_centery_change))
        self._screen.blit(self._artists_2_text, self._artists_2_text_rect)
        
        self._images_from_2_text_rect.centerx = credits_centerx
        (self._images_from_2_text_rect
         .centery) = int(credits_centery_starting_point 
                         + (16 * credits_centery_change))
        self._screen.blit(self._images_from_2_text, 
                          self._images_from_2_text_rect)
        
        self._license_type_2_text_rect.centerx = credits_centerx
        (self._license_type_2_text_rect
         .centery) = int(credits_centery_starting_point 
                         + (17 * credits_centery_change))
        self._screen.blit(self._license_type_2_text, 
                          self._license_type_2_text_rect)
        
        self._artists_3_text_rect.centerx = credits_centerx
        self._artists_3_text_rect.centery = int(credits_centery_starting_point 
                                                + (18 * credits_centery_change))
        self._screen.blit(self._artists_3_text, self._artists_3_text_rect)
        
        self._artists_4_text_rect.centerx = credits_centerx
        self._artists_4_text_rect.centery = int(credits_centery_starting_point 
                                                + (19 * credits_centery_change))
        self._screen.blit(self._artists_4_text, self._artists_4_text_rect)
        
        self._artists_5_text_rect.centerx = credits_centerx
        self._artists_5_text_rect.centery = int(credits_centery_starting_point 
                                                + (20 * credits_centery_change))
        self._screen.blit(self._artists_5_text, self._artists_5_text_rect)
        
        self._artists_6_text_rect.centerx = credits_centerx
        self._artists_6_text_rect.centery = int(credits_centery_starting_point 
                                                + (21 * credits_centery_change))
        self._screen.blit(self._artists_6_text, self._artists_6_text_rect)
        
        self._artists_7_text_rect.centerx = credits_centerx
        self._artists_7_text_rect.centery = int(credits_centery_starting_point 
                                                + (22 * credits_centery_change))
        self._screen.blit(self._artists_7_text, self._artists_7_text_rect)
        
        self._artists_8_text_rect.centerx = credits_centerx
        self._artists_8_text_rect.centery = int(credits_centery_starting_point 
                                                + (23 * credits_centery_change))
        self._screen.blit(self._artists_8_text, self._artists_8_text_rect)
        
        self._artists_9_text_rect.centerx = credits_centerx
        self._artists_9_text_rect.centery = int(credits_centery_starting_point 
                                                + (24 * credits_centery_change))
        self._screen.blit(self._artists_9_text, self._artists_9_text_rect)
        
        self._artists_10_text_rect.centerx = credits_centerx
        (self._artists_10_text_rect
         .centery) = int(credits_centery_starting_point 
                         + (25 * credits_centery_change))
        self._screen.blit(self._artists_10_text, self._artists_10_text_rect)
        
        self._images_from_3_text_rect.centerx = credits_centerx
        (self._images_from_3_text_rect
         .centery) = int(credits_centery_starting_point 
                         + (27 * credits_centery_change))
        self._screen.blit(self._images_from_3_text, 
                          self._images_from_3_text_rect)
        
        self._license_type_3_text_rect.centerx = credits_centerx
        (self._license_type_3_text_rect
         .centery) = int(credits_centery_starting_point 
                         + (28 * credits_centery_change))
        self._screen.blit(self._license_type_3_text, 
                          self._license_type_3_text_rect)
        
        self._artists_11_text_rect.centerx = credits_centerx
        (self._artists_11_text_rect
         .centery) = int(credits_centery_starting_point 
                         + (29 * credits_centery_change))
        self._screen.blit(self._artists_11_text, self._artists_11_text_rect)
        
        self._artists_12_text_rect.centerx = credits_centerx
        (self._artists_12_text_rect
         .centery) = int(credits_centery_starting_point 
                         + (30 * credits_centery_change))
        self._screen.blit(self._artists_12_text, self._artists_12_text_rect)
        
        self._artists_13_text_rect.centerx = credits_centerx
        (self._artists_13_text_rect
         .centery) = int(credits_centery_starting_point 
                         + (31 * credits_centery_change))
        self._screen.blit(self._artists_13_text, self._artists_13_text_rect)
        
        self._music_and_sounds_from_text_rect.centerx = credits_centerx
        (self._music_and_sounds_from_text_rect
         .centery) = int(credits_centery_starting_point 
                         + (33 * credits_centery_change))
        self._screen.blit(self._music_and_sounds_from_text, 
                          self._music_and_sounds_from_text_rect)
        
        self._license_type_4_text_rect.centerx = credits_centerx
        (self._license_type_4_text_rect
         .centery) = int(credits_centery_starting_point 
                         + (34 * credits_centery_change))
        self._screen.blit(self._license_type_4_text, 
                          self._license_type_4_text_rect)
        
        self._musicians_text_rect.centerx = credits_centerx
        self._musicians_text_rect.centery = int(credits_centery_starting_point 
                                                + (35 * credits_centery_change))
        self._screen.blit(self._musicians_text, self._musicians_text_rect)
        
        self._musicians_2_text_rect.centerx = credits_centerx
        (self._musicians_2_text_rect
         .centery) = int(credits_centery_starting_point 
                         + (36 * credits_centery_change))
        self._screen.blit(self._musicians_2_text, self._musicians_2_text_rect)
        
        self._musicians_3_text_rect.centerx = credits_centerx
        (self._musicians_3_text_rect
         .centery) = int(credits_centery_starting_point 
                         + (37 * credits_centery_change))
        self._screen.blit(self._musicians_3_text, self._musicians_3_text_rect)
        
        self._musicians_4_text_rect.centerx = credits_centerx
        (self._musicians_4_text_rect
         .centery) = int(credits_centery_starting_point 
                         + (38 * credits_centery_change))
        self._screen.blit(self._musicians_4_text, self._musicians_4_text_rect)
        
        self._musicians_5_text_rect.centerx = credits_centerx
        (self._musicians_5_text_rect
         .centery) = int(credits_centery_starting_point 
                         + (39 * credits_centery_change))
        self._screen.blit(self._musicians_5_text, self._musicians_5_text_rect)
        
        self._musicians_6_text_rect.centerx = credits_centerx
        (self._musicians_6_text_rect
         .centery) = int(credits_centery_starting_point 
                         + (40 * credits_centery_change))
        self._screen.blit(self._musicians_6_text, self._musicians_6_text_rect)
        
        self._musicians_7_text_rect.centerx = credits_centerx
        (self._musicians_7_text_rect
         .centery) = int(credits_centery_starting_point 
                         + (41 * credits_centery_change))
        self._screen.blit(self._musicians_7_text, self._musicians_7_text_rect)
        
        self._musicians_8_text_rect.centerx = credits_centerx
        (self._musicians_8_text_rect
         .centery) = int(credits_centery_starting_point 
                         + (42 * credits_centery_change))
        self._screen.blit(self._musicians_8_text, self._musicians_8_text_rect)
        
        self._musicians_9_text_rect.centerx = credits_centerx
        (self._musicians_9_text_rect
         .centery) = int(credits_centery_starting_point 
                         + (43 * credits_centery_change))
        self._screen.blit(self._musicians_9_text, self._musicians_9_text_rect)
        
        self._musicians_10_text_rect.centerx = credits_centerx
        (self._musicians_10_text_rect
         .centery) = int(credits_centery_starting_point 
                         + (44 * credits_centery_change))
        self._screen.blit(self._musicians_10_text, self._musicians_10_text_rect)
        
        self._musicians_11_text_rect.centerx = credits_centerx
        (self._musicians_11_text_rect
         .centery) = int(credits_centery_starting_point 
                         + (45 * credits_centery_change))
        self._screen.blit(self._musicians_11_text, self._musicians_11_text_rect)
        
        self._musicians_12_text_rect.centerx = credits_centerx
        (self._musicians_12_text_rect
         .centery) = int(credits_centery_starting_point 
                         + (46 * credits_centery_change))
        self._screen.blit(self._musicians_12_text, self._musicians_12_text_rect)
        
        screen_h_percentage = 15 / 16
        
        self._musicians_13_text_rect.centerx = credits_centerx
        self._musicians_13_text_rect.centery = int(self._screen_res[1] 
                                                   * screen_h_percentage)
        self._screen.blit(self._musicians_13_text, self._musicians_13_text_rect)
        
        # Displays the Back button.
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
                    menu_choice = constants.CREDITS_MENU_BACK_BUTTON
            elif (event.type == MOUSEMOTION):
                mouse_coords = pygame.mouse.get_pos()
                
                if (self._back_text_rect.y <= mouse_coords[1] 
                    <= (self._back_text_rect.y + self._back_text_rect.height)):
                    self._play_menu_sound()
                    menu_choice = constants.CREDITS_MENU_BACK_BUTTON
            elif (event.type == MOUSEBUTTONDOWN):
                buttons = pygame.mouse.get_pressed()

                if buttons[0]:
                    if (menu_choice == constants.CREDITS_MENU_BACK_BUTTON):
                        # Back to the main menu.
                        return constants.DIFFICULTY_MENU
        
        return menu_choice
    
    def _play_menu_sound(self):
        while (pygame.mixer.find_channel() is None):
            pygame.mixer.set_num_channels(pygame.mixer.get_num_channels() + 1)
            
        pygame.mixer.find_channel().play(self._menu_sound)