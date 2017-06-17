"""
Created on May 15, 2017

@author: Tony Liang

Source:
Working with Images by Blake O'Hare: http://www.nerdparadise.com/programming/py
                                     game/part2
                                     
"""

import os
import pygame

class AudioStorage(object):
    """This class represents storing and retrieving game audio."""
    
    def __init__(self):
        """Sets up the dictionary for storing audio on creation."""
        
        self._audio = {}
    
    def get_audio(self, audio_path):
        """Returns a Sound object of the audio data.
        
        New game audio will be stored before returning a Sound object. If the 
        game audio is already in the storage, storing it isn't necessary. The 
        Sound object will be retrieved from storage and be returned.
        
        Args:
            audio_path (str): The audio file path.
        
        Returns:
            Sound: Sound object of the audio data.
        
        """
        
        last_slash_occurrence = audio_path.rfind('\\')
        
        if (last_slash_occurrence == -1):
            last_slash_occurrence = audio_path.rfind('/')
            
        audio_file_name = audio_path[(last_slash_occurrence + 1):]
        audio_file = self._audio.get(audio_file_name)
        
        if (audio_file is None):
            audio_file = pygame.mixer.Sound(os.path.normpath(audio_path))
            self._audio[audio_file_name] = audio_file
            
        return audio_file