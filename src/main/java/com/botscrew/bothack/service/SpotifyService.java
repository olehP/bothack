package com.botscrew.bothack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.botscrew.bothack.model.PlaylistResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class SpotifyService {
	@Autowired
	private RestTemplate restTemplate;

	private final static String TOKEN = "BQBnDPMh7RRSJxIJhXn1jdztNZwSYeSC8qscIDdwiTxYl_V0nGw5wy96gTUMw4nbOThcF3WPHCNwBpurqQy2mrovVPp_2yuWZ1Sh02sPRWlxcTkT0g9qQKlCsqUHj3vaGQ48AzmR7vo2yS1QjT2Qz7VBF499waICmn2eF95rJEvHmHVAJsQ";
    private String PLAYLISTS_URL = "https://api.spotify.com/v1/users/";
	
	
	public PlaylistResponse  getPlaylistsPage(String userName) {
		try {
		String url = PLAYLISTS_URL + userName +"/playlists";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.set("Authorization", " Bearer "+TOKEN);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<PlaylistResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity,
				PlaylistResponse.class);
		PlaylistResponse responseBody = response.getBody();
		ObjectMapper mapper = new ObjectMapper();
		return responseBody;	
		} catch (HttpClientErrorException e) {
			System.out.println(e.getResponseBodyAsString());
		}
		
		return null;
	}
	
	
	
	
}
