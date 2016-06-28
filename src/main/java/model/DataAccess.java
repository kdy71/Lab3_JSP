package model;

import java.util.Date;
import java.util.List;

/**
 * Created by Dmitry Khoruzhenko on 14.04.2016.
 * Interface for working with data base.
 */
public interface DataAccess {

    List<Employee> getAllEmployees();

    List<Employee> getEmployeesFiltered(String pName, String pJobName, Float pSalaryFrom, Float pSalaryTo,
                                        Integer pDepartmentId, Integer pManagerId, Date pDateInFrom, Date pDateInTo,
                                        String pManagerName, String pDepartmentName);

    List<Employee> getEmployeesFiltered(String pName, String pJobName, Float pSalaryFrom, Float pSalaryTo,
                                        Integer pDepartmentId, Integer pManagerId, Date pDateInFrom, Date pDateInTo,
                                        String pManagerName, String pDepartmentName, int page, int range);

    Employee getEmployeeById(Integer id);

    void insertEmployee(Employee employee);

    void updateEmployee(Employee employee);

    void deleteEmployee(Integer employeeId);

    List<Department> getAllDepartments();

    Department getDepartmentById(Integer id);

    void insertDepartment(Department department);

    void updateDepartment(Department department);

    void deleteDepartment(Integer departmentId);

    int getTotalCountOfEmployees();

    List<Employee> getAllEmployeesByPage(int page, int range);
}
