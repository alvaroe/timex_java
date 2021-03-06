package com.timexautoweb.test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.timexautoweb.controllers.TimesheetListController;
import com.timexautoweb.domain.Department;
import com.timexautoweb.domain.Employee;
import com.timexautoweb.domain.Timesheet;
import com.timexautoweb.domain.TimesheetHome;
import com.timexautoweb.util.ApplicationSecurityManager;
import com.timexautoweb.util.TimexJmxBean;
import com.timexautoweb.view.XMLView;

/**
 * Test class for TimeListController
 */
public class TimesheetListControllerTest extends TestCase {
	private MockHttpServletRequest mockHttpServletRequest = null;
	private TimesheetListController timesheetListController = null;
	private TimesheetHome timesheetManager = null;
	private ApplicationSecurityManager applicationSecurityManager = null;
	private XMLView xmlView;
	private TimexJmxBean timexJmxBean = null;
	private final int EMPLOYEE_ID = 1;
	Timesheet timesheet1 = null;
	Timesheet timesheet2 = null;

	public static void main(String args[]) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(TimesheetListControllerTest.class);
	}

	/**
	 * Test GET of TimesheetList screen for an employee
	 */
	public void testHandleRequest() throws Exception {
		mockHttpServletRequest.setMethod("GET");
		mockHttpServletRequest.setQueryString("timesheetlist.htm");

		// This is the real test
		ModelAndView modelAndView = timesheetListController.handleRequest(mockHttpServletRequest, null);

		// Make sure model and view from handleRequest is not null
		assertNotNull(modelAndView);
		assertNotNull(modelAndView.getModel());

		// Make sure timesheets passed on to the view are not null
		List timesheets = (List) modelAndView.getModel().get(TimesheetListController.MAP_KEY);
		assertNotNull(timesheets);

		Timesheet timesheet;
		for (int i = 0; i < timesheets.size(); i++) {
			timesheet = (Timesheet) timesheets.get(i);
			// Make sure all timesheets belong to EMPLOYEE_ID
			assertEquals(EMPLOYEE_ID, timesheet.getEmployee().getId().intValue());
		}
	}

	/**
	 * Create two test Timesheet objects in DB
	 */
	protected void setUp() throws Exception {
		Employee employee = new Employee();
		employee.setId(EMPLOYEE_ID);
		mockHttpServletRequest = new MockHttpServletRequest();
		timesheetManager = new TimesheetHome();
		applicationSecurityManager = new ApplicationSecurityManager();
		applicationSecurityManager.setEmployee(mockHttpServletRequest, employee);
		xmlView = new XMLView();

		timexJmxBean = new TimexJmxBean();
		// Inject objects Spring normally would
		timesheetListController = new TimesheetListController();
		timesheetListController.setTimesheetManager(timesheetManager);
		timesheetListController.setApplicationSecurityManager(applicationSecurityManager);
		timesheetListController.setXmlView(xmlView);
		timesheetListController.setTimexJmxBean(timexJmxBean);
	}

	/**
	 * Delete test Timesheet objects from DB.
	 */
	protected void tearDown() throws Exception {
	}
}
