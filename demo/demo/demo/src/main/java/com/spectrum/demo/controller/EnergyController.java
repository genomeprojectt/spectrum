package com.spectrum.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spectrum.demo.model.Details;
import com.spectrum.demo.model.Orgarnizations;
import com.spectrum.demo.service.RestApiCall;

@RestController
@RequestMapping(value = "/spectrum")
public class EnergyController {

	@Autowired
	RestApiCall apiCall;

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Orgarnizations getResponse() throws Exception {
		return apiCall.getJsonResponse();
	}

	@GetMapping(value = "/exportCSV", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Resource> exportCSV() throws Exception {
		String[] csvHeader = { "organization", "release_count", "total_labor_hours", "all_in_production", "licenses",
				"most_active_months" };

		List<List<String>> csvBody = new ArrayList<>();

		Orgarnizations org = apiCall.getJsonResponse();
		for (Details detail : org.getDetails()) {
			csvBody.add((List<String>) detail);
		}
		ByteArrayInputStream byteArrayOutputStream;

		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				// defining the CSV printer
				CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out),
						// withHeader is optional
						CSVFormat.DEFAULT.withHeader(csvHeader));) {
			// populating the CSV content
			for (List<String> record : csvBody)
				csvPrinter.printRecord(record);

			// writing the underlying stream
			csvPrinter.flush();

			byteArrayOutputStream = new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}

		InputStreamResource fileInputStream = new InputStreamResource(byteArrayOutputStream);

		String csvFileName = "organizationDetails.csv";

		// setting HTTP headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + csvFileName);
		// defining the custom Content-Type
		headers.set(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE);

		return new ResponseEntity<>(fileInputStream, headers, HttpStatus.OK);
	}
}
