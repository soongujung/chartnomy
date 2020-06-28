package com.chartnomy.indicators.testdocker.api.web.trending.index.mockmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("testdocker")
public class TrendingIndexControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@DisplayName("날짜축 API - /api/web/trending/index/DATE")
	@Test
	public void test_get_date_series() throws Exception {
		String from = "20200101000000";
		String to 	= "20201231235959";

		MvcResult mvcResult = mockMvc.perform(
			get("/api/web/trending/index/DATE")
				.param("from", from)
				.param("to", to)
				.accept(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString();
		System.out.println(responseBody);

//		ObjectMapper mapper = new ObjectMapper();
//		mapper.readValue(responseBody, new TypeReference<List<IndexDateDto>>(){});

//		verify(trendingIndexRepository, times(1))
//			.getDateSeries(processDateParam(from), processDateParam(to));

//		stubbing

//		BDD)

	}

	@DisplayName("KOSPI API - /api/web/trending/index/KOSPI")
	@Test
	public void test_get_kospi_series() throws Exception {
		String from = "20200101000000";
		String to 	= "20201231235959";

		MvcResult mvcResult = mockMvc.perform(
			get("/api/web/trending/index/KOSPI")
				.param("from", from)
				.param("to", to)
				.accept(MediaType.APPLICATION_JSON)
		)
		.andDo(print())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString();
		System.out.println(responseBody);
	}

	@DisplayName("USD API - /api/web/trending/index/exchange/USD")
	@Test
	public void test_usd_series() throws Exception {
		String from = "20200101000000";
		String to 	= "20201231235959";

		MvcResult mvcResult = mockMvc.perform(
			get("/api/web/trending/index/exchange/USD")
				.param("from", from)
				.param("to", to)
				.accept(MediaType.APPLICATION_JSON)
		)
		.andDo(print())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString();
		System.out.println(responseBody);
	}

	@DisplayName("LOAN_KR - /api/web/trending/index/loan/LOAN_KR")
	@Test
	public void test_loan_kr_series() throws Exception {
		String from = "20200101000000";
		String to 	= "20201231235959";

		MvcResult mvcResult = mockMvc.perform(
			get("/api/web/trending/index/loan/LOAN_KR")
				.param("from", from)
				.param("to", to)
				.accept(MediaType.APPLICATION_JSON)
		)
		.andDo(print())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString();
		System.out.println(responseBody);
	}

	@DisplayName("LOAN_US - /api/web/trending/index/loan/LOAN_US")
	@Test
	public void test_loan_us_series() throws Exception {
		String from = "20200101000000";
		String to 	= "20201231235959";

		MvcResult mvcResult = mockMvc.perform(
			get("/api/web/trending/index/LOAN_US")
				.param("from", from)
				.param("to", to)
				.accept(MediaType.APPLICATION_JSON)
		)
		.andDo(print())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString();
		System.out.println(responseBody);
	}
}
