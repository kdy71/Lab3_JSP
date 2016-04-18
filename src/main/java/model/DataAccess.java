package model;

import java.util.List;

/**
 * Created by Dmitry Khoruzhenko on 14.04.2016.
 */
public interface DataAccess {

    List<EmployeeImpl> getAllEmployees();

    List<Department> getAllDepartments();

}
