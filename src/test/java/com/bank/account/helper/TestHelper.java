package com.bank.account.helper;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestHelper {
	/**
	 * Convert an object to JSON string.
	 *
	 * @param object
	 *            the object to convert
	 * @return the JSON string
	 * @throws IOException
	 */
	public static String convertObjectToJsonStrings(Object object) throws IOException {
		 ObjectMapper mapper = new ObjectMapper();
		 mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		 mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
		 mapper.configure(
				    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		 String json = mapper.writeValueAsString(object);

		return json;
	}
}
