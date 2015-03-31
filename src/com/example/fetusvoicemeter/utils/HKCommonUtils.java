package com.example.fetusvoicemeter.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HKCommonUtils {
	
	private static SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss",Locale.getDefault());

	public static String getTimeString() {
		return localSimpleDateFormat.format(new Date());
	}

}
