package com.devNNP.Helpers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Loader {

    private Loader() {
    }

    public static BufferedImage loadImage(String path) {
        try {
            BufferedImage loadedImage = ImageIO.read(Loader.class.getResource(path));
            BufferedImage formattedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            formattedImage.getGraphics().drawImage(loadedImage, 0, 0, null);
            return formattedImage;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File loadFile(String path) {
        try {
            File loadedFile = new File(Loader.class.getResource(path).toURI());
            return loadedFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
