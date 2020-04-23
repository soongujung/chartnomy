package com.chartnomy.indicators.config;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("local")
@Component
@RequiredArgsConstructor
public class SampleDataComponent {

	private final InitSampleData initSampleData;

	@PostConstruct
	public void init(){
		initSampleData.init();
	}

	@Component
	static class InitSampleData{

		//		@PersistenceContext
		@Autowired
		EntityManager entityManager;

		@Transactional
		public void init(){
			// ...
		}
	}
}
