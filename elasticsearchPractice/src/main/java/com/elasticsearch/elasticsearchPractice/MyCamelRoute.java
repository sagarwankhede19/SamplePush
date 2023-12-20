package com.elasticsearch.elasticsearchPractice;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

@Component
@CrossOrigin
public class MyCamelRoute extends RouteBuilder {

	int id;
	String nameString;

	@Autowired
	TimeSheetRepo repo;

	@Override
	public void configure() throws Exception {
		restConfiguration().component("servlet").port(8081).host("localhost").bindingMode(RestBindingMode.json);

		// Save --------------------------------------------------------
		rest().post("/saveTimeSheet").type(TimeSheetEntity.class).route().to("direct:processMessage");
		from("direct:processMessage").log("Received message: ${body}").process(exchange -> {
			TimeSheetEntity message = exchange.getIn().getBody(TimeSheetEntity.class);
			repo.save(message);
		}).end();

		// DELETE
		rest().delete("/deleteByEmployeeNumber").param().name("employeeNumber").type(RestParamType.query).endParam()
		.to("direct:start");

		from("direct:start").process(exchange -> {
			String empNumber = exchange.getIn().getHeader("employeeNumber", String.class);
			TimeSheetEntity employee = repo.findByEmployeeNumber(empNumber);
			if (employee == null) {
				exchange.getMessage().setBody("Employee Number Not Found");
				exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 404);

			} else {
				repo.deleteByEmployeeNumber(empNumber);
				exchange.getMessage().setBody("Employee Details have been deleted");
				exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
			}

		});

		// GET

		rest().get("/getTimeSheetByName").param().name("name").type(RestParamType.query).endParam()
		.to("direct:processName");
		from("direct:processName").process(exchange -> {
			String name = exchange.getIn().getHeader("name", String.class);
			if (isValidName(name)) {
				TimeSheetEntity timeSheetEntity = repo.findByEmployeeName(name);
				if (timeSheetEntity != null) {
					exchange.getMessage().setBody(timeSheetEntity);
				} else {
					exchange.getMessage().setBody("Error: TimeSheetEntity with name '" + name + "' not found");
					exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 404);
				}
			} else {
				exchange.getMessage().setBody("Error: Invalid name provided");
				exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
			}
		});

		//Update

		rest().put("/updateTimeSheet").param().name("number").type(RestParamType.query).endParam()
		.param().name("month").type(RestParamType.query).endParam()
		.param().name("year").type(RestParamType.query).endParam()
		.param().name("clientName").type(RestParamType.query).endParam()
		.param().name("assignmentName").type(RestParamType.query).endParam()
		.param().name("holidaysInput").type(RestParamType.query).endParam()

		.to("direct:updateTimeSheet");
		from("direct:updateTimeSheet").process(exchange -> {

			String number = exchange.getIn().getHeader("number", String.class);
			String month = exchange.getIn().getHeader("month", String.class);
			String year = exchange.getIn().getHeader("year", String.class);
			String clientName = exchange.getIn().getHeader("clientName", String.class);
			String assignmentName = exchange.getIn().getHeader("assignmentName", String.class);
			String holidaysInput = exchange.getIn().getHeader("holidaysInput", String.class);

			if (isValidNumber(number) && isValidMonth(month) && isValidYear(year)) {
				TimeSheetEntity existingEntity = repo.findByEmployeeNumberAndSelectedMonthAndSelectedYear(number, month,
						year);
				if (existingEntity != null) {

					if (clientName != null) {
						existingEntity.setClientName(clientName);
					}
					if (assignmentName != null) {
						existingEntity.setAssignmentName(assignmentName);
					}
					if (holidaysInput != null) {
						existingEntity.setHolidaysInput(holidaysInput);
					}
					repo.save(existingEntity);
					exchange.getMessage().setBody(existingEntity);
					exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
				} else {
					exchange.getMessage().setBody("Error: TimeSheetEntity with number '" + number + "' not found");
					exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 404);
				}
			} else {
				exchange.getMessage().setBody("Error: Invalid field provided");
				exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
			}

		});

	}

	private boolean isValidName(String name) {

		String nameregex = "^[a-zA-Z'` -]+$";
		return name != null && name.matches(nameregex);
	}
	private boolean isValidYear(String year) {
		return year != null && year.matches("^20[2-9]\\d|2[1-9]\\d{2}|3\\d{3}");
	}

	private boolean isValidMonth(String month) {
		return month != null && month.matches(
				"^(January|February|March|April|May|June|July|August|September|October|November|December)|(0?[1-9"
						+ "]|1[0-2])$");
	}

	private boolean isValidNumber(String number) {
		return number != null && number.matches("^VKSS\\d{3,4}$");
	}

}
