package com.botscrew.bothack.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.botscrew.bothack.exception.SystemException;
import com.botscrew.bothack.model.lufthansa.EarnRequest;
import com.botscrew.bothack.service.LufthansaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LufthansaServiceImpl implements LufthansaService {
	@Autowired
	private RestTemplate restTemplate;
	private String token;
	private static final String URL = "https://api-test.lufthansa.com/v1/";

	@Override
	public int getBalance(String userAlias) {
		try {
			StringBuilder urlBuilder = new StringBuilder(URL);
			urlBuilder.append("profiles/customers/accountbalance/");
			urlBuilder.append(userAlias);
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
			headers.add("Authorization", "Bearer " + token);
			HttpEntity<String> request = new HttpEntity<>(headers);
			String response = restTemplate.exchange(urlBuilder.toString(), HttpMethod.GET, request, String.class)
					.getBody();
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readTree(response).get("remainingTotalPoints").asInt(0);

		} catch (IOException e) {
			throw new SystemException("Lufthansa", e);
		}

	}

	@Override
	public void earnMiles(String userAlias, int miles) {
		StringBuilder urlBuilder = new StringBuilder(URL);
		urlBuilder.append("profiles/customers/earnrequest");
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", "Bearer " + token);
		EarnRequest earnRequest = new EarnRequest();
		earnRequest.setCardNo(userAlias);
		earnRequest.setMiles(miles);
		HttpEntity<EarnRequest> request = new HttpEntity<>(earnRequest, headers);
		restTemplate.exchange(urlBuilder.toString(), HttpMethod.GET, request, String.class);
	}
}
