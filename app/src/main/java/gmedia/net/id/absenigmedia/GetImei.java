package gmedia.net.id.absenigmedia;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class GetImei {
    public static ArrayList<String> getIMEI(Context context) {

        ArrayList<String> imeiList = new ArrayList<>();
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            if (Build.VERSION.SDK_INT >= 21) {
                Class<?> telephonyClass = Class.forName(telephony.getClass().getName());
                Class<?>[] parameter = new Class[1];
                parameter[0] = int.class;
                Method getFirstMethod = telephonyClass.getMethod("getDeviceId", parameter);
                //Log.d("SimData", getFirstMethod.toString());
                Object[] obParameter = new Object[1];
                obParameter[0] = 0;
                TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                String first = (String) getFirstMethod.invoke(telephony, obParameter);
                if (first != null && !first.equals("")) imeiList.add(first);
                //Log.d("SimData", "first :" + first);
                obParameter[0] = 1;
                String second = (String) getFirstMethod.invoke(telephony, obParameter);
                if (second != null && !second.equals("")) imeiList.add(second);
                //Log.d("SimData", "Second :" + second);
            } else {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                }
                String first = telephony.getDeviceId();
                imeiList.add(first);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return imeiList;
    }
}
