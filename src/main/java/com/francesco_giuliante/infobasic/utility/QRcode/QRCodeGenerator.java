package com.francesco_giuliante.infobasic.utility.QRcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class QRCodeGenerator {

    // Crea la stringa che rappresenta l'URL del QR code (solo con l'ID della lezione)
    public static String generateQRCodeData(int lessonID) {
        return "http://localhost:8080/attendance/register/" + lessonID;  // URL con solo l'ID della lezione
    }

    // Genera il QR code in formato Base64 per poterlo inviare alla UI
    public static String generateQRCodeBase64(int lessonID) throws WriterException, IOException {
        String data = generateQRCodeData(lessonID);  // Ottieni l'URL con l'ID della lezione

        // Generazione del QR code
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);

        // Creazione di un'immagine BufferedImage dalla matrice di bit
        BufferedImage bufferedImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < 200; x++) {
            for (int y = 0; y < 200; y++) {
                // Imposta il colore in base al valore del bit (nero o bianco)
                bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
            }
        }

        // Converti l'immagine in formato Base64
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "PNG", byteArrayOutputStream);  // Scrivi l'immagine PNG nel flusso di byte
        byte[] imageBytes = byteArrayOutputStream.toByteArray();  // Ottieni i byte dell'immagine
        return Base64.getEncoder().encodeToString(imageBytes);  // Restituisci l'immagine codificata in Base64
    }
}
