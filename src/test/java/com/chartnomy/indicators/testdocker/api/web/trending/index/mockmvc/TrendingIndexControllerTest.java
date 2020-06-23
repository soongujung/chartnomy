package com.chartnomy.indicators.testdocker.api.web.trending.index.mockmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.chartnomy.indicators.api.web.trending.index.TrendingIndexController;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
//@WebMvcTest(TrendingIndexController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TrendingIndexControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@DisplayName("/api/web/trending/index/DATE")
	@Test
	public void test_get_date_series() throws Exception {
		String from = "20200101000000";
		String to 	= "20201231235959";

		mockMvc.perform(
					get("/api/web/trending/index/DATE")
						.param("from", from)
						.param("to", to)
					.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
//				.andExpect(content().string())
				.andDo(print());

	}

	private LocalDateTime processDateParam(String strDate){
		return LocalDateTime.parse(strDate, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
	}
}
