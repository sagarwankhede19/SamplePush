package com.elasticsearch.elasticsearchPractice;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;



@Document(indexName = "new_time_sheet")
public class TimeSheetEntity {

	@Id
    private String id;
   
    private String employeeName;
    private String employeeNumber;
    private String selectedMonth;
    private String selectedYear;
    private String clientName;
    private String assignmentName;
    private String holidaysInput;
    
    
    private List<TimeSheetEntry> timesheetData;
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getSelectedMonth() {
		return selectedMonth;
	}

	public void setSelectedMonth(String selectedMonth) {
		this.selectedMonth = selectedMonth;
	}

	public String getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(String selectedYear) {
		this.selectedYear = selectedYear;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getAssignmentName() {
		return assignmentName;
	}

	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}

	public String getHolidaysInput() {
		return holidaysInput;
	}

	public void setHolidaysInput(String holidaysInput) {
		this.holidaysInput = holidaysInput;
	}


    public TimeSheetEntity() {
    }

    public TimeSheetEntity(String employeeName, String employeeNumber, String selectedMonth, String selectedYear,
                           String clientName, String assignmentName, String holidaysInput, List<TimeSheetEntry> timesheetData) {
        this.employeeName = employeeName;
        this.employeeNumber = employeeNumber;
        this.selectedMonth = selectedMonth;
        this.selectedYear = selectedYear;
        this.clientName = clientName;
        this.assignmentName = assignmentName;
        this.holidaysInput = holidaysInput;
        this.timesheetData = timesheetData;
    }

    

    public List<TimeSheetEntry> getTimesheetData() {
        return timesheetData;
    }

    public void setTimesheetData(List<TimeSheetEntry> timesheetData) {
        this.timesheetData = timesheetData;
    }

	@Override
	public String toString() {
		return "TimeSheetEntity [id=" + id + ", employeeName=" + employeeName + ", employeeNumber=" + employeeNumber
				+ ", selectedMonth=" + selectedMonth + ", selectedYear=" + selectedYear + ", clientName=" + clientName
				+ ", assignmentName=" + assignmentName + ", holidaysInput=" + holidaysInput + ", timesheetData="
				+ timesheetData + "]";
	}

	
   
}

class TimeSheetEntry {
	
    private int date;
    private String leaveNonWorkingDays;
    private String column1;
    private String  clientName;
    private String assignmentName;
    private String workingHours;

    public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getAssignmentName() {
		return assignmentName;
	}

	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}

	public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getLeaveNonWorkingDays() {
        return leaveNonWorkingDays;
    }

    public void setLeaveNonWorkingDays(String leaveNonWorkingDays) {
        this.leaveNonWorkingDays = leaveNonWorkingDays;
    }

    public String getColumn1() {
        return column1;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

	@Override
	public String toString() {
		return "TimeSheetEntry [date=" + date + ", leaveNonWorkingDays=" + leaveNonWorkingDays + ", column1=" + column1
				+ ", clientName=" + clientName + ", assignmentName=" + assignmentName + ", workingHours=" + workingHours
				+ "]";
	}

}
