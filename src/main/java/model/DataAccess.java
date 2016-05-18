package model;

import java.util.Date;
import java.util.List;

/**
 * Created by Dmitry Khoruzhenko on 14.04.2016.
 */
public interface DataAccess {

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Integer id);


    /**
     * Возвращает список работников, отфильтрованный по заданным параметрам.
     * Если параметр не должен участвовать в фильтрации - присвоить ему null
     * Строковые значения ищутся по начальным символам, дата приёма и зарплата - по диапазону значений.
     * @param pName          - имя работника
     * @param pJobName       - должность
     * @param pSalaryFrom    - зарплата " с "
     * @param pSalaryTo      - зарплата " по "
     * @param pDepartmentId  - id подразделения
     * @param pManagerId     - id менеджера
     * @param pDateInFrom    - дата приёма " с "
     * @param pDateInTo      - дата приёма " по "
     * @param pManagerName   - имя менеджера
     * @param pDepartmentName - наименование подразделения
     * @return  - возвращает список работников, для которых выполняются все условия фильтрации
     */
    List<Employee> getEmployeesFiltered(String pName, String pJobName, Float pSalaryFrom, Float pSalaryTo,
                                        Integer pDepartmentId, Integer pManagerId, Date pDateInFrom, Date pDateInTo,
                                        String pManagerName, String pDepartmentName);

    void insertEmployee(Employee employee);

    void updateEmployee (Employee employee);

    void deleteEmployee (Integer employeeId);

//    List<Employee> getPossibleManagers(Integer employeeId);


    List<Department> getAllDepartments();

    Department getDepartmentById(Integer id);


    void insertDepartment (Department department);

    void updateDepartment (Department department);

    void deleteDepartment (Integer departmentId);

}
