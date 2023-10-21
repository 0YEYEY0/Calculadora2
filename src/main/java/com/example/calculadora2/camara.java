package com.example.calculadora2;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class camara {
    public static void main(String[] args) {
        // Inicializa Tesseract
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:\\Users\\jacna\\Downloads"); // Ruta a los archivos de entrenamiento

        // Inicializa la cámara
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.open();

        while (true) {
            try {
                // Captura una imagen desde la cámara
                BufferedImage image = webcam.getImage();

                // Realiza el reconocimiento de texto en la imagen
                String result = tesseract.doOCR(image);

                // Imprime el texto reconocido
                System.out.println("Texto reconocido:\n" + result);

                // Guarda la imagen capturada (opcional)
                File outputImage = new File("captura.jpg");
                ImageIO.write(image, "jpg", outputImage);

                // Espera un tiempo antes de capturar otra imagen
                Thread.sleep(1000);
            } catch (TesseractException | InterruptedException | IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}
