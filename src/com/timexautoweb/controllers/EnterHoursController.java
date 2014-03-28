package com.timexautoweb.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.timexautoweb.domain.DepartmentHome;
import com.timexautoweb.domain.Employee;
import com.timexautoweb.domain.EmployeeHome;
import com.timexautoweb.domain.Timesheet;
import com.timexautoweb.domain.TimesheetHome;
import com.timexautoweb.util.DateUtil;

/**
 * Controller class for the "Enter Hours" screen
 * 
 * @author Anil Hemrajani
 * @author Alvaro E. Escobar
 */
public class EnterHoursController extends SimpleFormController {

	private TimesheetHome timesheetManager;
	private DepartmentHome departmentManager;
	private EmployeeHome employeeManager;
	public static final String TID = "tid";

	/**
	 * Returns a new instance of Timesheet object if "tid" HTTP parameter is not
	 * specified; otherwise returns instance of object in database matching the
	 * value of the "tid" parameter.
	 * 
	 * @see Timesheet
	 */
	protected Object formBackingObject(HttpServletRequest request) {
		Timesheet timesheet;
		Employee employee;
		if (request.getParameter(TID) != null && request.getParameter(TID).trim().length() > 0) {
			timesheet = timesheetManager.findById(Integer.parseInt(request.getParameter(TID)));
		} else {
			// We are hard-coding for now the employee whose timesheet belongs to.
			timesheet = new Timesheet();
			timesheet.setEmployee_id(1);
			timesheet.setPeriodEndingDate(DateUtil.getCurrentPeriodEndingDate());
		}
		timesheet.setStatusCode(Timesheet.PENDING);
		return timesheet;
	}

	/**
	 * Registers the MinutesPropertyEditor.
	 * 
	 * @see org.springframework.web.servlet.mvc.BaseCommandController#initBinder(javax.servlet.http.HttpServletRequest,
	 *      org.springframework.web.bind.ServletRequestDataBinder)
	 */
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Integer.class, new MinutesPropertyEditor());
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
	}

	/**
	 * Returns Hashmap containing a list of all Department database records.
	 * 
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest)
	 */
	@SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request) throws Exception {
		HashMap model = new HashMap();
		model.put("departments", departmentManager.getDepartments());
		return model;
	}

	/**
	 * Saves or submits timesheet.
	 */
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) {
		Timesheet timesheet = (Timesheet) command;
		try {
			if (request.getParameter("sendEmail").equalsIgnoreCase("no")) {
				timesheet.setStatusCode(Timesheet.PENDING);
			} else {
				timesheet.setStatusCode(Timesheet.SUBMITTED);
			}
			timesheetManager.attachDirty(timesheet);

			request.getSession().setAttribute("message",
					getMessageSourceAccessor().getMessage("message.enterhours.savesuccess"));
		} catch (Exception e) {
			if (e instanceof org.hibernate.exception.ConstraintViolationException) {
				request.getSession().setAttribute("error",
						getMessageSourceAccessor().getMessage("error.enterhours.preexistingerror"));
				request.getSession().removeAttribute("message");
			}
		}
		return new ModelAndView(getSuccessView());
	}

	public void setTimesheetManager(TimesheetHome timesheetManager) {
		this.timesheetManager = timesheetManager;
	}

	public void setDepartmentManager(DepartmentHome departmentManager) {
		this.departmentManager = departmentManager;
	}

	public void setEmployeeManager(EmployeeHome employeeManager) {
		this.employeeManager = employeeManager;
	}

}