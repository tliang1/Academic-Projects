"""
Created on Apr 26, 2015

@author: Tony Liang
        
The Endless Battle

This module is the main module that controls the game.

Examples:
    $ python game.py

"""

import doctest
import pygame
import sys
import tebg.constants as constants
from pygame.locals import *
from tebg.storage.images_storage import ImagesStorage
from tebg.storage.audio_storage import AudioStorage
from tebg.menus.main_menu import MainMenu
from tebg.menus.how_to_play_menu import HowToPlayMenu
from tebg.menus.credits_menu import CreditsMenu
from tebg.menus.difficulty_menu import DifficultyMenu
from tebg.menus.game_over_menu import GameOverMenu
from tebg.entities.player import Player
from tebg.hud.health import Health
from tebg.hud.armor import Armor
from tebg.entities.enemies.gunner import Gunner
from tebg.entities.enemies.shotgunner import Shotgunner
from tebg.entities.enemies.dual_wielding_gunner import DualWieldingGunner
from tebg.entities.health_pickups.medkit import Medkit
from tebg.entities.health_pickups.bandages import Bandages
from tebg.entities.threats.thorns import Thorns
from tebg.entities.threats.bear_trap import BearTrap
from tebg.entities.threats.land_mine import LandMine
from tebg.entities.powerups.protection import Protection
from tebg.entities.powerups.instant_kill import InstantKill
from tebg.entities.powerups.life_steal import LifeSteal
from tebg.system.spawning import Spawning

def main():
    """Sets up and starts the game.
    
    The game runs at most 60 frames per second (FPS) and full screen. The main 
    menu displays the options for playing the game, how to play the game, the 
    credits, and quitting the game. Background music is played. While playing, 
    left and right arrow keys or the mouse for player movement. Press the Escape 
    key to quit game or go back to the main menu if in-game. The player 
    indefinitely shoots bullets up. All enemies, pickups, power-ups, and threats 
    are spawned indefinitely at set intervals. Enemies shoots at the player when 
    detected. They get hurt and/or killed when shot. The player's health 
    decreases when shot by enemies. The player can pick up health pickups to 
    fill the player's health. The player can pick up power-ups to gain an 
    advantage for a set time. The player can be injured or killed from stepping 
    on threats. When the player dies, the game goes to the game over menu. Click 
    or press any key to return to the main menu. Everything resets when the user 
    goes back to the main menu.
    
    """
    
    # Always be sure to call the init() function before doing anything else.
    pygame.init()

    # Creates a images storage.
    imgs_storage = ImagesStorage()
    
    # Creates a audio storage.
    audio_storage = AudioStorage()

    # Sets the FPS for this game.
    FPS = constants.FPS
    fpsClock = pygame.time.Clock()
    elapsed_seconds_since_last_frame = 0 # Uses for frame independent actions.

    # Creates the initial window.
    SCREEN = pygame.display.set_mode((0, 0), FULLSCREEN | HWSURFACE | DOUBLEBUF)
    window_size = SCREEN.get_size()
    
    pygame.display.set_caption("The Endless Battle")
    
    # Sets the background color.
    SCREEN.fill(constants.MEDIUMBLUE)
    
    # Sets the background image.
    BACKGROUND = imgs_storage.get_image("../../Textures/grass.png").convert()
    
    # Initializes every menu.
    main_menu = MainMenu(audio_storage)
    how_to_play = HowToPlayMenu(imgs_storage, audio_storage)
    game_credits = CreditsMenu(audio_storage)
    difficulty_menu = DifficultyMenu(audio_storage)
    game_over_menu = GameOverMenu()
    menu_choice = constants.DIFFICULTY_MENU
    
    # This is a list of every sprite.
    all_sprites_list = pygame.sprite.LayeredDirty()
    
    # Creates a player.
    player = Player(imgs_storage, audio_storage, 3)
    all_sprites_list.add(player)
    
    # Player's health and armor.
    health = Health(imgs_storage, player.health, 6)
    armor = Armor(imgs_storage, player.armor, 6)
    all_sprites_list.add(health)
    all_sprites_list.add(armor)
    
    # List of each bullet.
    bullet_list = pygame.sprite.Group()
    enemy_bullet_list = pygame.sprite.Group()
    
    # List of each enemy.
    enemy_list = pygame.sprite.Group()
    
    # List of each health pickup.
    health_pickups_list = pygame.sprite.Group()
    
    # List of each threat.
    threats_list = pygame.sprite.Group()
    
    # List of each power-up.
    power_ups_list = pygame.sprite.Group()
    
    # Initializes all background music.
    pygame.mixer.set_reserved(2)
    menu_music = pygame.mixer.Sound('../../Sounds/background_music.ogg')
    game_music = pygame.mixer.Sound('../../Sounds/background_music_2.ogg')
    game_music.set_volume(0.25)
    
    channels_list = []
    
    for channel in range(2):
        channels_list.append(pygame.mixer.Channel(channel))
    
    channels_list[0].play(menu_music, -1)
    channels_list[1].play(game_music, -1)
    
    for channel in channels_list:
        channel.pause()
    
    # Spawning enemies, health pickups, threats, power-ups, etc.
    gunner_spawning = Spawning(3)
    shotgunner_spawning = Spawning(5)
    dual_wielding_gunner_spawning = Spawning(9)
    bandages_spawning = Spawning(8)
    bandages_spawning_medium = Spawning(18)
    bandages_spawning_hard = Spawning(28)
    medkit_spawning = Spawning(17)
    medkit_spawning_medium = Spawning(27)
    medkit_spawning_hard = Spawning(37)
    thorns_spawning = Spawning(13)
    bear_trap_spawning = Spawning(4)
    land_mine_spawning = Spawning(6)
    protection_spawning = Spawning(11)
    protection_spawning_medium = Spawning(22)
    protection_spawning_hard = Spawning(33)
    instant_kill_spawning = Spawning(29)
    instant_kill_spawning_medium = Spawning(39)
    instant_kill_spawning_hard = Spawning(49)
    life_steal_spawning = Spawning(47)
    life_steal_spawning_medium = Spawning(57)
    life_steal_spawning_hard = Spawning(67)
    
    while True:  # <--- main game loop
        update_sprites = None
        
        # Draws background.
        for x in range(0, window_size[0], BACKGROUND.get_rect().width):
            for y in range(0, window_size[1], BACKGROUND.get_rect().height):
                SCREEN.blit(BACKGROUND, (x, y))
        
        if (constants.HARD_DIFFICULTY <= menu_choice 
            <= constants.EASY_DIFFICULTY):
            #===================================================================
            # Pauses the main menu music and plays the game music when playing 
            # the game.
            #===================================================================
            channels_list[0].pause()
            channels_list[1].unpause()
            
            pygame.mouse.set_visible(False)
            
            for event in pygame.event.get():
                if (event.type == QUIT):  # QUIT event to exit the game.
                    pygame.quit()
                    sys.exit()
                elif (event.type == pygame.KEYDOWN):
                    player.mouse_movement = False
                    
                    if (event.key == K_ESCAPE):
                        # Go back to the main menu.
                        update_sprites = None
                        menu_choice = constants.DIFFICULTY_MENU
                    if (event.key == K_LEFT):
                        player.keyboard_movement = True
                        player.move_left = True
                    elif (event.key == K_RIGHT):
                        player.keyboard_movement = True
                        player.move_left = False
                elif (event.type == KEYUP):
                    player.keyboard_movement = False
                elif (event.type == MOUSEMOTION):
                    player.mouse_movement = True
                    player.keyboard_movement = False  
            
            # List of sprites to check collision with when spawning new sprites.
            spawn_collide_sprites_list = pygame.sprite.Group()
            spawn_collide_sprites_list.add(enemy_list)
            spawn_collide_sprites_list.add(health_pickups_list)
            spawn_collide_sprites_list.add(threats_list)
            spawn_collide_sprites_list.add(power_ups_list)
            
            # Spawns the gunner enemy.
            gunner_spawning.spawn(Gunner(imgs_storage, audio_storage, 
                                         -menu_choice, 1), enemy_list, 
                                  spawn_collide_sprites_list, all_sprites_list)
               
            # Spawns the shotgunner enemy.
            shotgunner_spawning.spawn(Shotgunner(imgs_storage, audio_storage, 
                                                 -menu_choice, 1), enemy_list, 
                                      spawn_collide_sprites_list, 
                                      all_sprites_list)
              
            # Spawns the dual wielding gunner enemy.
            (dual_wielding_gunner_spawning
             .spawn(DualWieldingGunner(imgs_storage, audio_storage, 
                                       -menu_choice, 1), enemy_list, 
                    spawn_collide_sprites_list, all_sprites_list))
             
            # Spawns the thorns.
            thorns_spawning.spawn(Thorns(imgs_storage, audio_storage, 
                                         -menu_choice, 0), threats_list, 
                                  spawn_collide_sprites_list, all_sprites_list)
               
            # Spawns the bear trap.
            bear_trap_spawning.spawn(BearTrap(imgs_storage, audio_storage, 
                                              -menu_choice, 0), threats_list, 
                                     spawn_collide_sprites_list, 
                                     all_sprites_list)
            
            # Spawns the land mine.
            land_mine_spawning.spawn(LandMine(imgs_storage, audio_storage, 
                                              -menu_choice, 0), threats_list, 
                                     spawn_collide_sprites_list, 
                                     all_sprites_list)
            
            if (menu_choice == constants.EASY_DIFFICULTY):
                bandages_spawning.spawn(Bandages(imgs_storage, audio_storage, 
                                                 0), health_pickups_list, 
                                        spawn_collide_sprites_list, 
                                        all_sprites_list)
                medkit_spawning.spawn(Medkit(imgs_storage, audio_storage, 0), 
                                      health_pickups_list, 
                                      spawn_collide_sprites_list, 
                                      all_sprites_list)
                protection_spawning.spawn(Protection(imgs_storage, 
                                                     audio_storage, 1, 0), 
                                          power_ups_list, 
                                          spawn_collide_sprites_list, 
                                          all_sprites_list)
                instant_kill_spawning.spawn(InstantKill(imgs_storage, 
                                                        audio_storage, 20, 0), 
                                            power_ups_list, 
                                            spawn_collide_sprites_list, 
                                            all_sprites_list)
                life_steal_spawning.spawn(LifeSteal(imgs_storage, audio_storage, 
                                                    15, 0), power_ups_list, 
                                          spawn_collide_sprites_list, 
                                          all_sprites_list)
            elif (menu_choice == constants.MEDIUM_DIFFICULTY):
                bandages_spawning_medium.spawn(Bandages(imgs_storage, 
                                                        audio_storage, 0), 
                                               health_pickups_list, 
                                               spawn_collide_sprites_list, 
                                               all_sprites_list)
                medkit_spawning_medium.spawn(Medkit(imgs_storage, audio_storage, 
                                                    0), health_pickups_list, 
                                             spawn_collide_sprites_list, 
                                             all_sprites_list)
                protection_spawning_medium.spawn(Protection(imgs_storage, 
                                                            audio_storage, 1, 
                                                            0), power_ups_list, 
                                                 spawn_collide_sprites_list, 
                                                 all_sprites_list)
                instant_kill_spawning_medium.spawn(InstantKill(imgs_storage, 
                                                               audio_storage, 
                                                               20, 0), 
                                                   power_ups_list, 
                                                   spawn_collide_sprites_list, 
                                                   all_sprites_list)
                life_steal_spawning_medium.spawn(LifeSteal(imgs_storage, 
                                                           audio_storage, 15, 
                                                           0), power_ups_list, 
                                                 spawn_collide_sprites_list, 
                                                 all_sprites_list)
            else:
                bandages_spawning_hard.spawn(Bandages(imgs_storage, 
                                                      audio_storage, 0), 
                                             health_pickups_list, 
                                             spawn_collide_sprites_list, 
                                             all_sprites_list)
                medkit_spawning_hard.spawn(Medkit(imgs_storage, audio_storage, 
                                                  0), health_pickups_list, 
                                           spawn_collide_sprites_list, 
                                           all_sprites_list)
                protection_spawning_hard.spawn(Protection(imgs_storage, 
                                                          audio_storage, 1, 0), 
                                               power_ups_list, 
                                               spawn_collide_sprites_list, 
                                               all_sprites_list)
                instant_kill_spawning_hard.spawn(InstantKill(imgs_storage, 
                                                             audio_storage, 20, 
                                                             0), power_ups_list, 
                                                 spawn_collide_sprites_list, 
                                                 all_sprites_list)
                life_steal_spawning_hard.spawn(LifeSteal(imgs_storage, 
                                                         audio_storage, 15, 0), 
                                               power_ups_list, 
                                               spawn_collide_sprites_list, 
                                               all_sprites_list)
            
            #===================================================================
            # Enemies will fire at the player if the player is in front of and 
            # below the enemy.
            #===================================================================
            for enemy in enemy_list:
                if (player.rect.y > enemy.rect.bottom):
                    if ((enemy.rect.x <= player.rect.x <= (enemy.rect.x 
                                                           + enemy.rect.width)) 
                        or (enemy.rect.x <= (player.rect.x + player.rect.width) 
                            <= (enemy.rect.x + enemy.rect.width))):
                        enemy.spotted = True
            
            all_sprites_list.clear(SCREEN, BACKGROUND)
            
            # Calls the update() method on all the sprites.
            all_sprites_list.update(elapsed_seconds_since_last_frame)
            
            # Adds bullets fired from the player to the lists.
            all_sprites_list.add(player.bullets_list)
            bullet_list.add(player.bullets_list)
            
            # Adds bullets fired from enemies to the lists.
            for enemy in enemy_list:
                all_sprites_list.add(enemy.bullets_list)
                enemy_bullet_list.add(enemy.bullets_list)
            
            enemy_bullets = pygame.sprite.spritecollide(player, 
                                                        enemy_bullet_list, True)
            
            #===================================================================
            # Decreases the player's health or armor if a bullet hits the 
            # player.
            #===================================================================
            if (enemy_bullets):
                if (player.armor > 0):
                    player.change_armor(-enemy_bullets[0].damage)
                    armor.set_armor(player.armor)
                else:
                    player.change_health(-enemy_bullets[0].damage)
                    health.set_health(player.health)
                    
                all_sprites_list.remove(enemy_bullets)
            
            # Removes the enemies' bullets if they go below the screen.
            for enemy_bullet in enemy_bullet_list:
                if (enemy_bullet.rect.y > window_size[1]):
                    enemy_bullet_list.remove(enemy_bullet)
                    all_sprites_list.remove(enemy_bullet)
            
            # Calculates mechanics for each bullet the player fires.    
            for bullet in bullet_list:
                collided = False
                alreadyDead = False
                
                # Checks if any bullets hit any enemies.
                enemy_hit_list = pygame.sprite.spritecollide(bullet, enemy_list, 
                                                             False)
                
                # Decreases the enemy's health if the bullet hits him.
                if (enemy_hit_list):
                    collided = True
                    
                    enemy = enemy_hit_list[0]
                    enemy.change_health(-player.damage)
                    
                    if (enemy.health < 1):
                        alreadyDead = True
                        enemy_list.remove(enemy)
                        
                    for power_up in power_ups_list:
                        #=======================================================
                        # Heals the player when the life steal power-up is 
                        # active.
                        #=======================================================
                        if ((power_up.heal > 0) and power_up.is_picked_up):
                            player.change_health(power_up.heal)
                            health.set_health(player.health)
                            break
                
                #===============================================================
                # Removes the player's bullet if it goes above the screen or 
                # hits an enemy.
                #===============================================================
                if ((bullet.rect.y < -bullet.rect.height) 
                    or (collided and not alreadyDead)):
                    bullet_list.remove(bullet)
                    all_sprites_list.remove(bullet)
            
            # Removes the enemies if they go below the screen.
            for enemy in enemy_list:
                if (enemy.rect.y > window_size[1]):
                    enemy_list.remove(enemy)
                    all_sprites_list.remove(enemy)
                    
            health_pickups = pygame.sprite.spritecollide(player, 
                                                         health_pickups_list, 
                                                         False)
            
            # Heals the player when the player picks up a health pickup.
            if (health_pickups):
                health_pickups[0].is_picked_up = True
                player.change_health(health_pickups[0].heal_amount)
                health.set_health(player.health)
            
            # Removes the health pickups if they go below the screen.    
            for health_pickup in health_pickups_list:
                if (health_pickup.rect.y > window_size[1]):
                    health_pickups_list.remove(health_pickup)
                    all_sprites_list.remove(health_pickup)
                    
            threats = pygame.sprite.spritecollide(player, threats_list, False)
            
            # Damages the player if the player collides with a threat.
            if (threats):
                if (player.armor > 0):
                    player.change_armor(-threats[0].damage)
                    armor.set_armor(player.armor)
                else:
                    player.change_health(-threats[0].damage)
                    health.set_health(player.health)
                    
                threats[0].collided = True
                threats[0].reset_damage()
                
                if threats[0].explosive:
                    all_sprites_list.change_layer(threats[0], 4)
            
            # Removes the threats if they go below the screen.    
            for threat in threats_list:
                if (threat.rect.y > window_size[1]):
                    threats_list.remove(threat)
                    all_sprites_list.remove(threat)
                    
            power_ups = pygame.sprite.spritecollide(player, power_ups_list, 
                                                    False)
            
            # Activates any power-ups that collided with the player.
            if (power_ups):
                power_ups[0].is_picked_up = True
                
                if (power_ups[0].armor_amount > 0):
                    player.change_armor(power_ups[0].armor_amount)
                    armor.set_armor(player.armor)
                elif (power_ups[0].damage > 0):
                    player.damage = power_ups[0].damage
            
            #===================================================================
            # Removes the power-ups if they go below the screen or their 
            # durations are over.
            #===================================================================
            for power_up in power_ups_list:
                if (power_up.is_over or (power_up.rect.y > window_size[1])):
                    player.reset_damage()
                        
                    power_ups_list.remove(power_up)
                    all_sprites_list.remove(power_up)
            
            # Removes leftover sprites.
            for left_over in all_sprites_list:
                if (left_over.rect.y > window_size[1]):
                    all_sprites_list.remove(left_over)
            
            # Draws all the sprites.
            update_sprites = all_sprites_list.draw(SCREEN)
            
            # When the player dies, the game over menu is displayed.
            if (player.health < 1):
                menu_choice = constants.GAME_OVER_MENU
        elif (menu_choice == constants.GAME_OVER_MENU):
            pygame.mouse.set_visible(True)
            
            menu_choice = game_over_menu.draw_game_over_menu(menu_choice)
        else:
            # Pauses the game music and plays the main menu music.
            channels_list[1].pause()
            channels_list[0].unpause()
            
            pygame.mouse.set_visible(True)
            
            if (constants.DIFFICULTY_MENU <= menu_choice 
                <= constants.QUIT_BUTTON):
                menu_choice = main_menu.draw_main_menu(menu_choice)
            elif (constants.HIGHLIGHT_EASY_DIFFICULTY <= menu_choice 
                  <= constants.HIGHLIGHT_DIFFICULTY_MENU_BACK_BUTTON):
                menu_choice = difficulty_menu.draw_difficulty_menu(menu_choice)
            elif (menu_choice == constants.HOW_TO_PLAY_MENU_BACK_BUTTON):
                menu_choice = how_to_play.draw_how_to_play_menu(menu_choice)
            elif (menu_choice == constants.CREDITS_MENU_BACK_BUTTON):
                menu_choice = game_credits.draw_credits_menu(menu_choice)
                
            # Resets all game variables.
            all_sprites_list.empty()
            bullet_list.empty()
            enemy_bullet_list.empty()
            enemy_list.empty()
            health_pickups_list.empty()
            threats_list.empty()
            power_ups_list.empty()
                
            all_sprites_list = pygame.sprite.LayeredDirty()
            bullet_list = pygame.sprite.Group()
            enemy_bullet_list = pygame.sprite.Group()
            enemy_list = pygame.sprite.Group()
            health_pickups_list = pygame.sprite.Group()
            threats_list = pygame.sprite.Group()
            power_ups_list = pygame.sprite.Group()
            
            player.reset_position()
            player.reset_damage()
            player.reset_health()
            player.reset_armor()
                
            all_sprites_list.add(player)
                
            health.set_health(player.health)
            armor.set_armor(player.armor)
            all_sprites_list.add(health)
            all_sprites_list.add(armor)
            
            # Reset spawning systems' timers.
            gunner_spawning.reset_start_time()
            shotgunner_spawning.reset_start_time()
            dual_wielding_gunner_spawning.reset_start_time()
            medkit_spawning.reset_start_time()
            medkit_spawning_medium.reset_start_time()
            medkit_spawning_hard.reset_start_time()
            bandages_spawning.reset_start_time()
            bandages_spawning_medium.reset_start_time()
            bandages_spawning_hard.reset_start_time()
            thorns_spawning.reset_start_time()
            bear_trap_spawning.reset_start_time()
            land_mine_spawning.reset_start_time()
            protection_spawning.reset_start_time()
            protection_spawning_medium.reset_start_time()
            protection_spawning_hard.reset_start_time()
            instant_kill_spawning.reset_start_time()
            instant_kill_spawning_medium.reset_start_time()
            instant_kill_spawning_hard.reset_start_time()
            life_steal_spawning.reset_start_time()
            life_steal_spawning_medium.reset_start_time()
            life_steal_spawning_hard.reset_start_time()
        
        # Updates the display when all events have been processed.
        if update_sprites is not None:
            pygame.display.update(update_sprites)
        else:
            pygame.display.update()
            
        elapsed_seconds_since_last_frame = fpsClock.tick(FPS) / 1000

if (__name__ == "__main__"):
    main();
    doctest.testmod()