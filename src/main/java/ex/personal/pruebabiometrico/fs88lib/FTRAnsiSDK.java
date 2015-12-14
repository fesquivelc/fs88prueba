/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ex.personal.pruebabiometrico.fs88lib;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.IntByReference;

/**
 *
 * @author Francis
 */
public interface FTRAnsiSDK extends Library {
    public static final int FTR_ANSISDK_ERROR_IMAGE_SIZE_NOT_SUP = 0x30000001;
    public static final int FTR_ANSISDK_ERROR_EXTRACTION_UNSPEC = 0x30000002;
    public static final int FTR_ANSISDK_ERROR_EXTRACTION_BAD_IMP = 0x30000003;
    public static final int FTR_ANSISDK_ERROR_MATCH_NULL = 0x30000004;
    public static final int FTR_ANSISDK_ERROR_MATCH_PARSE_PROBE = 0x30000005;
    public static final int FTR_ANSISDK_ERROR_MATCH_PARSE_GALLERY = 0x30000006;
    public static final int FTR_ANSISDK_ERROR_MORE_DATA = 0x30000007;

    FTRAnsiSDK INSTANCE = (FTRAnsiSDK) Native.loadLibrary("ftrAnsiSdk", FTRAnsiSDK.class);

    public boolean ftrAnsiSdkCaptureImage(IntByReference hDevice, byte[] pBuffer);

    public int ftrAnsiSdkGetMaxTemplateSize();

    public boolean ftrAnsiSdkCreateTemplate(IntByReference hDevice, byte byFingerPosition, byte[] pOutImageBuffer, byte[] pOutTemplate, IntByReference pnOutTemplateSize);
    
    public boolean ftrAnsiSdkCreateTemplateFromBuffer(IntByReference hDevice, byte byFingerPosition, byte[] pImageBuffer, int nWidth, int nHeight, byte[] pOutTemplate, IntByReference pnOutTemplateSize);
    
    public boolean ftrAnsiSdkVerifyTemplate(IntByReference hDevice, byte byFingerPosition, byte[] pInTemplate, byte[] pOutImageBuffer, FloatByReference pfOutResult);
    
    public boolean ftrAnsiSdkMatchTemplates(byte[] pProbeTemplate, byte[] pGaleryTemplate, FloatByReference pfOutResult);
    
    public boolean ftrAnsiSdkConvertAnsiTemplateToIso(byte[] pTemplateANSI, byte[] pTemplateIso, IntByReference pnInOutTemplateSize);
    
    
}
