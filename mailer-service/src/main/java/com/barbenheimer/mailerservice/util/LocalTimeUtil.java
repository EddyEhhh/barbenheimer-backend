package com.barbenheimer.mailerservice.util;

import java.time.LocalTime;

public final class LocalTimeUtil {



    public static LocalTime parseTime(String timeString){
        return LocalTime.parse(timeString);
    }



}
