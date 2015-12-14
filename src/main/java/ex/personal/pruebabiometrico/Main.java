package ex.personal.pruebabiometrico;

import com.sun.jna.ptr.IntByReference;
import ex.personal.pruebabiometrico.fs88lib.FTRAnsiSDK;
import ex.personal.pruebabiometrico.fs88lib.FTRScanAPI;
import java.io.File;

public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        FTRAnsiSDK ftrAnsiSDK = FTRAnsiSDK.INSTANCE;
        FTRScanAPI ftrScanAPI = FTRScanAPI.INSTANCE;
        IntByReference equipo = ftrScanAPI.ftrScanOpenDevice();
        FTRScanAPI.FTRSCAN_IMAGE_SIZE.ByReference imageSize = new FTRScanAPI.FTRSCAN_IMAGE_SIZE.ByReference();
        ftrScanAPI.ftrScanGetImageSize(equipo, imageSize);
        System.out.println("Equipo: "+equipo.getValue());
        System.out.println(String.format("ancho: %s alto: %s tamaño: %s", imageSize.nWidth, imageSize.nHeight, imageSize.nImageSize));
        byte[] imagen = new byte[imageSize.nImageSize];
        boolean captura = ftrAnsiSDK.ftrAnsiSdkCaptureImage(equipo, imagen);
        if(captura){
            System.out.println("Huella en ansi: "+imagen.length);
        }else{
            System.out.println("No se pudo capturar huella");
        }        
    }
}
