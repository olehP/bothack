package com.botscrew.bothack.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.botscrew.bothack.messages.Postback;
import com.botscrew.bothack.model.Button;
import com.botscrew.bothack.model.outgoing.generic.MessageElement;

public class ServiceProvider {
	public static List<MessageElement> getMusicServices() {
		List<MessageElement> elements = new ArrayList<>();
		MessageElement spotify = new MessageElement();
		spotify.setImageUrl("http://d2c87l0yth4zbw.global.ssl.fastly.net/i/_global/open-graph-default.png");
		spotify.setTitle("Spotify");
		spotify.setSubtitle("Some subtitle");
		List<Button> buttons = Arrays.asList(Button.createPostbackButton("Connect", Postback.MUSIC_CONNECT.payload()));
		spotify.setButtons(buttons);
		MessageElement appleMusic = new MessageElement();
		appleMusic.setImageUrl(
				"https://d35fkdjhhgt99.cloudfront.net/static/use-media-items/31/30549/full-1024x575/56701aec/CG_--bDUQAE_z6P.jpeg");
		appleMusic.setTitle("Apple Music");
		appleMusic.setSubtitle("Some subtitle");
		appleMusic.setButtons(buttons);
		MessageElement deezer = new MessageElement();
		deezer.setImageUrl("http://millionmedia.com/wp-content/uploads/2014/11/deezer-logo-circle.png");
		deezer.setTitle("Deezer");
		deezer.setSubtitle("Some subtitle");
		deezer.setButtons(buttons);
		MessageElement googleMusic = new MessageElement();
		googleMusic.setImageUrl("http://aumnia.com/wp-content/uploads/2015/10/google-play-music-logo-1.png");
		googleMusic.setTitle("Google Play Music");
		googleMusic.setSubtitle("Some subtitle");
		googleMusic.setButtons(buttons);
		elements.add(spotify);
		elements.add(appleMusic);
		elements.add(deezer);
		elements.add(googleMusic);
		return elements;

	}

	public static List<MessageElement> getLoyaltyServices() {
		List<MessageElement> elements = new ArrayList<>();
		MessageElement aadvantage = new MessageElement();
		aadvantage.setImageUrl("http://mommypoints.boardingarea.com/wp-content/uploads/2015/03/Merger-Logo.jpg");
		aadvantage.setTitle("AAdvantage");
		aadvantage.setSubtitle("Some subtitle");
		List<Button> buttons = Arrays
				.asList(Button.createPostbackButton("Connect", Postback.LOYALTY_CONNECT.payload()));
		aadvantage.setButtons(buttons);
		MessageElement lufthansa = new MessageElement();
		lufthansa.setImageUrl(
				"https://www.netzsieger.de/sites/default/files/img/produkt/kreditkarten-miles_and_more-logo.png");
		lufthansa.setTitle("Lufthansa Miles&More");
		lufthansa.setSubtitle("Some subtitle");
		lufthansa.setButtons(buttons);
		MessageElement flyingBlue = new MessageElement();
		flyingBlue.setImageUrl("http://www.pasazer.com/img/images/news-facebook/flying,blue,logo,af,klm,media.jpg");
		flyingBlue.setTitle("Flying Blue");
		flyingBlue.setSubtitle("Some subtitle");
		flyingBlue.setButtons(buttons);
		elements.add(aadvantage);
		elements.add(lufthansa);
		elements.add(flyingBlue);
		return elements;
	}
}
