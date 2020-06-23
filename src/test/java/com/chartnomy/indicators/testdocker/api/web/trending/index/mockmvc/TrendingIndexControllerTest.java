package com.chartnomy.indicators.testdocker.api.web.trending.index.mockmvc;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.chartnomy.indicators.api.web.trending.index.QTrendingIndexRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
//@WebMvcTest(TrendingIndexController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TrendingIndexControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private QTrendingIndexRepository trendingIndexRepository;

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
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect((ResultMatcher) jsonPath("$.status", is(400)));
//				.andExpect(content().string())

		verify(trendingIndexRepository, times(1))
			.getDateSeries(processDateParam(from), processDateParam(to));

//		stubbing

//		BDD)

	}

	private LocalDateTime processDateParam(String strDate){
		return LocalDateTime.parse(strDate, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
	}
}
