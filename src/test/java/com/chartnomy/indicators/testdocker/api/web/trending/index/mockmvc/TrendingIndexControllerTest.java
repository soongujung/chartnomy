package com.chartnomy.indicators.testdocker.api.web.trending.index.mockmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

	@DisplayName("/api/web/trending/index/DATE")
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

	private LocalDateTime processDateParam(String strDate){
		return LocalDateTime.parse(strDate, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
	}
}
