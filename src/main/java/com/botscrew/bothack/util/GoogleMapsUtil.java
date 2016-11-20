package com.botscrew.bothack.util;

public class GoogleMapsUtil {
	public static String generateDestinationUrl(double latitude, double longitude) {
		return "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude;
	}

}
