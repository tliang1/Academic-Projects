"""
Created on May 13, 2017

@author: Tony Liang

Source:
Working with Images by Blake O'Hare: http://www.nerdparadise.com/programming/py
                                     game/part2

"""

import os
import pygame

class ImagesStorage(object):
    """This class represents storing and retrieving game images."""
    
    def __init__(self):
        """Sets up the dictionary for storing images on creation."""
        
        self._images = {}
    
    def get_image(self, image_path):
        """Returns a Surface object of the image data.
        
        A new game image will be stored before returning a Surface object of it. 
        If the game image is already in the storage, storing it isn't necessary. 
        The Surface object will be retrieved from storage and be returned.
        
        Args:
            image_path (str): The image file path.
        
        Returns:
            Surface: Surface object of the image data.
        
        """
        
        last_slash_occurrence = image_path.rfind('\\')
        
        if (last_slash_occurrence == -1):
            last_slash_occurrence = image_path.rfind('/')
            
        image_file_name = image_path[(last_slash_occurrence + 1):]
        image = self._images.get(image_file_name)
        
        if (image is None):
            image = pygame.image.load(os.path.normpath(image_path))
            self._images[image_file_name] = image
            
        return image