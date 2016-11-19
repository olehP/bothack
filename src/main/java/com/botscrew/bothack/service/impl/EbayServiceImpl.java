package com.botscrew.bothack.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.botscrew.bothack.exception.SystemException;
import com.botscrew.bothack.model.ebay.EbayProduct;
import com.botscrew.bothack.service.EbayService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EbayServiceImpl implements EbayService {

	@Value("${ebay.basic.token}")
	private String basicToken;
	@Value("${ebay.ads.url}")
	private String adsUrl;
	@Value("${ebay.distance}")
	private Integer distance;
	@Autowired
	private RestTemplate restTemplate;

	public List<EbayProduct> getProducts(String query, double latitude, double longitude, Integer minPrice,
			Integer maxPrice, int page, int size) {
		try {
			StringBuilder searchUrl = new StringBuilder(adsUrl);
			searchUrl.append(".json?query=");
			searchUrl.append(query);
			searchUrl.append("&latitude=");
			searchUrl.append(latitude);
			searchUrl.append("&longitude=");
			searchUrl.append(longitude);
			searchUrl.append("&distance=");
			searchUrl.append(distance);
			searchUrl.append("&distanceUnit=KM");
			if (minPrice != null) {
				searchUrl.append("&minPrice=");
				searchUrl.append(minPrice);
			}
			if (maxPrice != null) {
				searchUrl.append("&maxPrice=");
				searchUrl.append(maxPrice);
			}
			searchUrl.append("&size=");
			searchUrl.append(size);
			searchUrl.append("&page=");
			searchUrl.append(page);
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
			headers.add("Authorization", "Basic " + basicToken);
			HttpEntity<String> entity = new HttpEntity<>(headers);
			String response = restTemplate.exchange(searchUrl.toString(), HttpMethod.GET, entity, String.class)
					.getBody();

			ObjectMapper objectMapper = new ObjectMapper();

			JsonNode root = objectMapper.readTree(response);
			Iterator<JsonNode> ads = root.get("{http://www.ebayclassifiedsgroup.com/schema/ad/v1}ads").get("value")
					.get("ad").elements();
			List<EbayProduct> result = new ArrayList<>();
			while (ads.hasNext()) {
				String id = ads.next().get("id").asText();
				result.add(getProduct(id));
			}
			return result;
		} catch (IOException e) {
			throw new SystemException("Ebay ", e);
		}

	}

	private EbayProduct getProduct(String id) throws IOException {
		StringBuilder requestUrl = new StringBuilder(adsUrl);
		requestUrl.append("/");
		requestUrl.append(id);
		requestUrl.append(".json");
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", "Basic " + basicToken);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		String response = restTemplate.exchange(requestUrl.toString(), HttpMethod.GET, entity, String.class).getBody();
		ObjectMapper objectMapper = new ObjectMapper();

		JsonNode root = objectMapper.readTree(response);
		JsonNode ad = root.get("{http://www.ebayclassifiedsgroup.com/schema/ad/v1}ad").get("value");
		EbayProduct result = new EbayProduct();
		result.setTitle(ad.get("title").get("value").asText());
		result.setDescription(ad.get("description").get("value").asText());
		result.setPrice(ad.get("price").get("amount").get("value").asDouble());
		if (ad.get("phone").get("value") != null) {
			result.setPhone(ad.get("phone").get("value").asText());
		}
		result.setLatitude(
				ad.get("locations").get("location").elements().next().get("latitude").get("value").asDouble());
		result.setLongitude(
				ad.get("locations").get("location").elements().next().get("longitude").get("value").asDouble());
		Iterator<JsonNode> linkIterator = ad.get("link").elements();
		while (linkIterator.hasNext()) {
			JsonNode link = linkIterator.next();
			if ("self-public-website".equals(link.get("rel").asText())) {
				result.setLink(link.get("href").asText());
			}
		}

		Iterator<JsonNode> pictureIterator = ad.get("pictures").get("picture").elements();
		while (pictureIterator.hasNext()) {
			JsonNode picture = pictureIterator.next();
			linkIterator = picture.get("link").elements();
			while (linkIterator.hasNext()) {
				JsonNode link = linkIterator.next();
				if ("large".equals(link.get("rel").asText())) {
					result.setPhotoUrl(link.get("href").asText());
				}
			}
		}

		return result;
	}

}
