/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ex.personal.pruebabiometrico.gui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;

/**
 *
 * @author Francis
 */
public class IconoHuella implements Icon {

    private Image imagen;
    private final int ancho;
    private final int altura;

    public boolean LoadImage(String path) {
        boolean bRetCode = false;
        Image newImg;
        try {
            File f = new File(path);
            newImg = ImageIO.read(f);
            bRetCode = true;
            setImage(newImg);
        } catch (IOException e) {
        }

        return bRetCode;
    }

    public void setImage(Image Img) {
        if (Img != null) {
            imagen = Img.getScaledInstance(getIconWidth(), getIconHeight(), Image.SCALE_FAST);
        } else {
            imagen = null;
        }
    }

    public IconoHuella(int ancho, int altura) {
        this.ancho = ancho;
        this.altura = altura;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        if (imagen != null) {
            g.drawImage(imagen, x, y, getIconWidth(), getIconHeight(), null);
        } else {
            g.fillRect(x, y, getIconWidth(), getIconHeight());
        }
    }

    @Override
    public int getIconWidth() {
        return this.ancho;
    }

    @Override
    public int getIconHeight() {
        return this.altura;
    }

}
