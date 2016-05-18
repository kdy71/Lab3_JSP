package controller.EmployeeProcessors;

import controller.Processor;
import model.Employee;
import model.EmployeeImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

        //получаем данные работника
        int empId = 0; // пока пишем ноль
        String empName = request.getParameter(EMP_NAME);
        int departmentId = Integer.parseInt(request.getParameter(DEPARTMENT_ID));
        int managerId = Integer.parseInt(request.getParameter(MANAGER_ID));
        String jobName = request.getParameter(JOB_NAME);
        Float salary = Float.parseFloat(request.getParameter(SALARY));
        Date dateIn = null;
        try {
            // TODO: согласовать формат даты c html-формой и уже введенными инсертом даннными
            dateIn = new SimpleDateFormat("dd.MM.yyyy").parse(request.getParameter(DATE_IN));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //создаем работника
        Employee employee = new EmployeeImpl(empId, empName, jobName, salary, departmentId, managerId, dateIn);

        //вызываем форвард c тремя параметрами, в том числе работником
        forwardForEmployee(request, response, employee);
    }

    //абстрактный метод для действий по добавлению нового работника или обновлению данных работника
    protected abstract void forwardForEmployee(HttpServletRequest request, HttpServletResponse response, Employee employee);
}
