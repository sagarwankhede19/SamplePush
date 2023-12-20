package com.elasticsearch.elasticsearchPractice;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories
public interface TimeSheetRepo extends ElasticsearchRepository<TimeSheetEntity, Integer> {

	TimeSheetEntity findByEmployeeName(String employeeName);

	TimeSheetEntity findByEmployeeNumber(String employeeNumber);

	TimeSheetEntity deleteByEmployeeNumber(String employeeNumber);
	
	TimeSheetEntity findByEmployeeNumberAndSelectedMonthAndSelectedYear(String number, String month,
			String year);


}
