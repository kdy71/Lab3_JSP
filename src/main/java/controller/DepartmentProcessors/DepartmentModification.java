package controller.DepartmentProcessors;

import controller.Processor;
import model.Department;
import model.DepartmentImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Oleksandr Dudkin.
 * Абстрактрый класс, который модифицирует данные работника - создает нового или менеят существующего.
 * Наследники CreateDepartment и UpdateDepartment.
 */
public abstract class DepartmentModification implements Processor {

    //все значения атрибутов сессии связанные с работником. выносим их в виде констант в поля классов и потом к ним обращаемся
    public static final String DEPARTMENT_ID = "department_id";
    public static final String DEPARTMENT_NAME = "department_name";
    public static final String DESCRIPTION = "description";

    public static final String DEPARTMENT_LIST = "departmentList";
    public static final String DEPARTMENT = "department";

    /**
     * Метод берет из реквеста параметры отдела, создает его объект и дальше выполняет требемое действие -
     * добавление нового или обновление информации о существующем отделе в БД.
     * @param request
     * @param response
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {
        //получаем данные отдела
        int departmentId = 0; // пока пишем ноль
        String departmentName = request.getParameter(DEPARTMENT_NAME);
        String description = request.getParameter(DESCRIPTION);
        System.out.println(departmentId + description);

        //создаем отдел
        Department department = new DepartmentImpl(departmentId, departmentName, description);
        System.out.println(department);

        //вызываем форвард c тремя параметрами, в том числе отделом
        forwardForDepartment(request, response, department);
    }

    //абстрактный метод для действий по добавлению нового отдела или обновлению данных об отделе
    protected abstract void forwardForDepartment(HttpServletRequest request, HttpServletResponse response, Department department);
}
