package com.botscrew.bothack.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botscrew.bothack.exception.SystemException;
import com.botscrew.bothack.service.ApiAiService;

import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

@Service
public class ApiAiServiceImpl implements ApiAiService {
	@Autowired
	private AIDataService dataService;

	public void processMessage(String message) {
		try {
			AIRequest request = new AIRequest(message);

			AIResponse response = dataService.request(request);
			if (response.getStatus().getCode() != 200) {
				throw new SystemException(response.getStatus().getErrorDetails());
			}
			System.out.println(response.getResult().getFulfillment().getSpeech());
		} catch (AIServiceException e) {
			e.printStackTrace();
		}
	}
}
