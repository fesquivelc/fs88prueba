/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ex.personal.pruebabiometrico.fs88lib;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Francis
 */
public interface FTRScanAPI extends Library {

    FTRScanAPI INSTANCE = (FTRScanAPI) Native.loadLibrary("ftrScanAPI", FTRScanAPI.class);

    public static class FTRSCAN_IMAGE_SIZE extends Structure {

        @Override
        protected List getFieldOrder() {
            List<String> fields = new ArrayList<>();
            fields.add("nWidth");
            fields.add("nHeight");
            fields.add("nImageSize");
            return fields;
        }

        public static class ByReference extends FTRSCAN_IMAGE_SIZE implements Structure.ByReference {
        }

        public int nWidth;
        public int nHeight;
        public int nImageSize;
    }

    public static final int FTR_ERROR_SUCCESS = 0;
    public static final int FTR_ERROR_EMPTY_FRAME = 4306;
    public static final int FTR_ERROR_MOVABLE_FINGER = 0x20000001;
    public static final int FTR_ERROR_NO_FRAME = 0x20000002;
    public static final int FTR_ERROR_USER_CANCELED = 0x20000003;
    public static final int FTR_ERROR_HARDWARE_INCOMPATIBLE = 0x20000004;
    public static final int FTR_ERROR_FIRMWARE_INCOMPATIBLE = 0x20000005;
    public static final int FTR_ERROR_INVALID_AUTHORIZATION_CODE = 0x20000006;
    public static final int FTR_ERROR_ROLL_NOT_STARTED = 0x20000007;
    public static final int FTR_ERROR_ROLL_PROGRESS_DATA = 0x20000008;
    public static final int FTR_ERROR_ROLL_TIMEOUT = 0x20000009;
    public static final int FTR_ERROR_ROLL_ABORTED = 0x2000000A;
    public static final int FTR_ERROR_ROLL_ALREADY_STARTED = 0x2000000B;
    public static final int FTR_ERROR_NO_MORE_ITEMS = 259;
    public static final int FTR_ERROR_NOT_ENOUGH_MEMORY = 8;
    public static final int FTR_ERROR_NO_SYSTEM_RESOURCES = 1450;
    public static final int FTR_ERROR_TIMEOUT = 1460;
    public static final int FTR_ERROR_NOT_READY = 21;
    public static final int FTR_ERROR_BAD_CONFIGURATION = 1610;
    public static final int FTR_ERROR_INVALID_PARAMETER = 87;
    public static final int FTR_ERROR_CALL_NOT_IMPLEMENTED = 120;
    public static final int FTR_ERROR_NOT_SUPPORTED = 50;
    public static final int FTR_ERROR_WRITE_PROTECT = 19;
    public static final int FTR_ERROR_MESSAGE_EXCEEDS_MAX_SIZE = 4336;

    public IntByReference ftrScanOpenDevice();

    public void ftrScanCloseDevice(IntByReference hDevice);
    
    public boolean ftrScanGetImageSize(IntByReference hDevice, FTRSCAN_IMAGE_SIZE.ByReference pImageSize);
}
