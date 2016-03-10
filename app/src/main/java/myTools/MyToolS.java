package myTools;

import android.app.Activity;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import cqhh.ibeacon.R;

/**
 * Created by cooper on 2016.3.7.
 */
public class MyToolS {

    private static final String TAG = "MyToolS";
    static double currentTime, oldTime = 0;
    public static String getTime() {

        currentTime = System.currentTimeMillis();
        //Log.i(TAG,"getTime()");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年mm月dd日 HH:MM:SS");
        Date date = new Date();
        String string = formatter.format(date);
        if (currentTime - oldTime > 10000) {
            oldTime = currentTime;
            return string;
        }
        return "";
    }

    public static String getRandomWelcomeTips(Activity activity) {
        String contentStr;
        String welcomeStrs[];
        String welcomStr;
        welcomeStrs = activity.getResources().getStringArray(R.array.welconmeTips);
        int index = (int) (Math.random() * (welcomeStrs.length - 1));
        welcomStr = welcomeStrs[index];
        return welcomStr;
    }
}
