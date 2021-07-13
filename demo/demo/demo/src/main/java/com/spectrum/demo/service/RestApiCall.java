package com.spectrum.demo.service;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.spectrum.demo.model.Details;
import com.spectrum.demo.model.Orgarnizations;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

@Service
public class RestApiCall {

	private RestTemplate restTemplate;

	@PostConstruct
	private void init() {
		restTemplate = new RestTemplate();
	}

	private <T> T performResolverServiceCall(HttpEntity<?> httpEntity, Class<T> responseClassType, String apiEndPoint,
			HttpMethod methodType) throws Exception {
		try {
			ResponseEntity<T> theResponse = restTemplate.exchange(apiEndPoint, methodType, httpEntity,
					responseClassType);
			if (theResponse.getStatusCode() == HttpStatus.OK) {
				return theResponse.getBody();
			} else {
				throw new Exception(
						"The server encountered an unexpected condition which prevented it from fulfilling the request.");
			}
		} catch (HttpClientErrorException exception) {
			throw new Exception(exception.getLocalizedMessage());
		} catch (Exception exception) {
			throw new Exception();
		}
	}

	public Object getOrganizations() throws Exception {
		return performResolverServiceCall(new HttpEntity<>(null), Object.class,
				"https://www.energy.gov/sites/prod/files/2020/12/f81/code-12-15-2020.json", HttpMethod.GET);
	}

	public Orgarnizations getJsonResponse() throws Exception {
		String url = "https://www.energy.gov/sites/prod/files/2020/12/f81/code-12-15-2020.json";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");
		// add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print in String

		// Read JSON response and print
		JSONObject myResponse = new JSONObject(response.toString());
		JSONArray releases = myResponse.getJSONArray("releases");

		List<Details> details = new ArrayList<>();
		for (int i = 0; i < releases.length(); i++) {
			JSONObject jsonObject = releases.getJSONObject(i);
			
			Details detail = new Details();
			detail.setOrganization(jsonObject.getString("organization"));
			detail.setTotal_labor_hours(jsonObject.getLong("laborHours"));
			details.add(detail);
		}
		Orgarnizations org = new Orgarnizations();
		org.setDetails(details);
		return org;
	}
}
