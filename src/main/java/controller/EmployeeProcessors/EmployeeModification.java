package controller.EmployeeProcessors;

import controller.Processor;
import model.Employee;
import model.EmployeeImpl;
import model.Util_dates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by Oleksandr Dudkin.
 * Абстрактрый класс, который модифицирует данные работника - создает нового или менеят существующего.
 * Наследники CreateEmployee и UpdateEmployee
 */
public abstract class EmployeeModification implements Processor {

    //все значения атрибутов сессии связанные с работником. выносим их в виде констант в поля классов и потом к ним обращаемся
    public static final String EMP_ID = "emp_id";
    public static final String EMP_NAME = "emp_name";
    public static final String DEPARTMENT_ID = "department_id";
    public static final String MANAGER_ID = "manager_id";
    public static final String JOB_NAME = "job_name";
    public static final String SALARY = "salary";
    public static final String DATE_IN = "date_in";

    public static final String EMPLOYEE_LIST = "employeeList";
    public static final String EMPLOYEE = "employee";


    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // TODO: 20.05.2016 Добавить проверку введённый параметров на null
        System.out.println("--- entering EmployeeModification.java ---");  // debug
        //получаем данные работника
        Integer empId = null; // пока пишем ноль
//        Integer empId = (Integer) request.getSession().getAttribute(EmployeeModification.EMP_ID);
        String empIdAsString = request.getParameter(EmployeeModification.EMP_ID);
        if (empIdAsString != null) { empId = Integer.parseInt(empIdAsString);};
        System.out.println("  empId= "+empId);  // debug
        String empName = request.getParameter(EMP_NAME);
        System.out.println("  empName= "+empName);  // debug
        Integer departmentId = Integer.parseInt(request.getParameter(DEPARTMENT_ID));
        System.out.println("  departmentId= "+departmentId);  // debug
        System.out.println("  RequestmanagerId= "+request.getParameter(MANAGER_ID));  // debug
//        Integer managerId = Integer.parseInt(request.getParameter(MANAGER_ID));
        Integer managerId;
//        if (request.getParameter(MANAGER_ID) == null) { System.out.println("man_id == null !! ");    }
        if (request.getParameter(MANAGER_ID) == null) {
            managerId = null; //  MANAGER_ID - необязательное поле
        } else {
            managerId = Integer.parseInt(request.getParameter(MANAGER_ID));
        }
        //Integer managerId = Integer.parseInt(request.getParameter(MANAGER_ID));
        System.out.println("  managerId= "+managerId);  // debug
        String jobName = request.getParameter(JOB_NAME);
        System.out.println("  jobName= "+jobName);  // debug
        Float salary = Float.parseFloat(request.getParameter(SALARY));
        System.out.println("  salary= "+salary);  // debug
        Date dateIn = null;
        try {
//            dateIn = new SimpleDateFormat("MM/dd/yyyy").parse(request.getParameter(DATE_IN));
            dateIn = Util_dates.str2Date(request.getParameter(DATE_IN));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("  request.getParameter(DATE_IN)= "+request.getParameter(DATE_IN));  // debug
        System.out.println("  dateIn= "+dateIn);  // debug

        //создаем работника

        Employee employee = new EmployeeImpl(empId, empName, jobName, salary, departmentId, managerId, dateIn);

        System.out.println("---  EmployeeModification.java ---   new EmployeeImpl= "+employee);  // debug

        //вызываем форвард c тремя параметрами, в том числе работником
        forwardForEmployee(request, response, employee);
    }

    //абстрактный метод для действий по добавлению нового работника или обновлению данных работника
    protected abstract void forwardForEmployee(HttpServletRequest request, HttpServletResponse response, Employee employee);
}
