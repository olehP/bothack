package com.botscrew.bothack.service;

import java.util.List;

import com.botscrew.bothack.model.ebay.EbayProduct;

public interface EbayService {

	List<EbayProduct> getProducts(String query, Double latitude, Double longitude, Integer minPrice, Integer maxPrice,
			int page, int size);

}
