package com.timexautoweb.domain;
// Generated Feb 13, 2013 6:38:57 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
* Timesheet generated by hbm2java
*/
public class Timesheet implements java.io.Serializable {

	public static final Character SUBMITTED = 'S';
	public static final Character PENDING = 'P';
	public static final Character APPROVED = 'A';
	public static final Character DISAPPROVED = 'D';
	public static final Character PAID = 'C';

	private Integer id;
	private Employee employee;
	private Department department;
	private int department_id;
	private int employee_id;
	private char statusCode;
	private Date periodEndingDate;
	private Integer minutesMon = 0;
	private Integer minutesTue = 0;
	private Integer minutesWed = 0;
	private Integer minutesThu = 0;
	private Integer minutesFri = 0;
	private Integer minutesSat = 0;
	private Integer minutesSun = 0;
	private Set payments = new HashSet(0);

	public Timesheet() {
	}

	public Timesheet(Employee employee, Department department, char statusCode, Date periodEndingDate) {
		this.employee = employee;
		this.department = department;
		this.statusCode = statusCode;
		this.periodEndingDate = periodEndingDate;
	}

	public Timesheet(Employee employee, Department department, char statusCode, Date periodEndingDate,
			Integer minutesMon, Integer minutesTue, Integer minutesWed, Integer minutesThu, Integer minutesFri,
			Integer minutesSat, Integer minutesSun, Set payments) {
		this.employee = employee;
		this.department = department;
		this.statusCode = statusCode;
		this.periodEndingDate = periodEndingDate;
		this.minutesMon = minutesMon;
		this.minutesTue = minutesTue;
		this.minutesWed = minutesWed;
		this.minutesThu = minutesThu;
		this.minutesFri = minutesFri;
		this.minutesSat = minutesSat;
		this.minutesSun = minutesSun;
		this.payments = payments;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	public int getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}

	public int getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}

	/**
	 * Possible values:
	 * <ul>
	 * <li />PENDING: "P"
	 * <li />APPROVED: "A"
	 * <li />PAID: "C"
	 * <li />SUBMITTED: "S"
	 * <li />DISAPPROVED: "D"
	 * </ul>
	 * 
	 * @param statusCode
	 */

	public char getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(char statusCode) {
		this.statusCode = statusCode;
	}

	public Date getPeriodEndingDate() {
		return this.periodEndingDate;
	}

	public void setPeriodEndingDate(Date periodEndingDate) {
		this.periodEndingDate = periodEndingDate;
	}

	public Integer getMinutesMon() {
		return this.minutesMon;
	}

	public void setMinutesMon(Integer minutesMon) {
		this.minutesMon = minutesMon;
	}

	public Integer getMinutesTue() {
		return this.minutesTue;
	}

	public void setMinutesTue(Integer minutesTue) {
		this.minutesTue = minutesTue;
	}

	public Integer getMinutesWed() {
		return this.minutesWed;
	}

	public void setMinutesWed(Integer minutesWed) {
		this.minutesWed = minutesWed;
	}

	public Integer getMinutesThu() {
		return this.minutesThu;
	}

	public void setMinutesThu(Integer minutesThu) {
		this.minutesThu = minutesThu;
	}

	public Integer getMinutesFri() {
		return this.minutesFri;
	}

	public void setMinutesFri(Integer minutesFri) {
		this.minutesFri = minutesFri;
	}

	public Integer getMinutesSat() {
		return this.minutesSat;
	}

	public void setMinutesSat(Integer minutesSat) {
		this.minutesSat = minutesSat;
	}

	public Integer getMinutesSun() {
		return this.minutesSun;
	}

	public void setMinutesSun(Integer minutesSun) {
		this.minutesSun = minutesSun;
	}

	public Set getPayments() {
		return this.payments;
	}

	public void setPayments(Set payments) {
		this.payments = payments;
	}

	/**
	 * @return total minutes for the week
	 */
	public Integer getTotalMinutes() {
		return (minutesMon.intValue() + minutesTue.intValue() + minutesWed.intValue() + minutesThu.intValue() + minutesFri.intValue() + minutesSat.intValue() + minutesSun.intValue());
	}

	public boolean isApproved() {
		return APPROVED.equals(this.statusCode);
	}

	public boolean isPending() {
		return PENDING.equals(this.statusCode) || DISAPPROVED.equals(this.statusCode);
	}

}