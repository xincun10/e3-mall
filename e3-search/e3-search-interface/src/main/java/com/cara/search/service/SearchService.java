package com.cara.search.service;

import com.cara.common.pojo.SearchResult;

public interface SearchService {

	SearchResult search(String keyword, int page, int rows) throws Exception;
}
