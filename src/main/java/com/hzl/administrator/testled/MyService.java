package com.hzl.administrator.testled;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {
    public MyService() {
    }
    static {
        System.loadLibrary("hue_core");
    }
    private native int sendLightColor(String key, int color, boolean forcesend,
                                      boolean isSmartmod);

    public native int sendLightColorAll(String key, int color, boolean forcesend,
                                        boolean isSmartmod);

    public static native int getCpuFamily();

    public static native int getLampCount();

    private static native int getAllLampID(String ids[]);

    private native void updateLamp(int action, int order);

    private static native int sendLampTiming(String key, int time, int action,
                                             boolean send);

    public native boolean isRunning();

    // public static native void loadLampId(int prefix,int sufix);
    public static native void initLampId(String devid);

    public native int isLightValid(String key);

    public native void setController(String mac);

    private native static void exeCommand(int command, int flag);
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        exeCommand(7,0);
        super.onCreate();
    }
}
