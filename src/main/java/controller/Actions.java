package controller;

import controller.DepartmentProcessors.*;
import controller.EmployeeProcessors.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oleksandr Dudkin, Dmitry Khoruzhenko.
 * Class contains map of actions for method process() in class ServletStart.
 */
public class Actions {

    //описываем все поля, которые могут быть параметрами действия в сессии
    public static final String START_PAGE = "startPage";
    public static final String CREATE_EMPLOYEE = "createEmployee";
    public static final String CREATE_DEPARTMENT = "createDepartment";
    public static final String UPDATE_EMPLOYEE = "updateEmployee";
    public static final String UPDATE_DEPARTMENT = "updateDepartment";
    public static final String DELETE_EMPLOYEE = "deleteEmployee";
    public static final String DELETE_DEPARTMENT = "deleteDepartment";
    public static final String EDIT_EMPLOYEE = "editEmployee";
    public static final String EDIT_DEPARTMENT = "editDepartment";
    public static final String LIST_EMPLOYEES = "listEmployees";

    public static final Actions INSTANCE = new Actions();

    private Map<String, Object> mapOfActions;

    /**
     * Constructor that creates map and puts all the actions into it.
     */
    private Actions() {
        mapOfActions = new HashMap<>();

        mapOfActions.put(START_PAGE, new StartPage());
        mapOfActions.put(CREATE_EMPLOYEE, new CreateEmployee());
        mapOfActions.put(CREATE_DEPARTMENT, new CreateDepartment());
        mapOfActions.put(UPDATE_EMPLOYEE, new UpdateEmployee());
        mapOfActions.put(UPDATE_DEPARTMENT, new UpdateDepartment());
        mapOfActions.put(DELETE_EMPLOYEE, new DeleteEmployee());
        mapOfActions.put(DELETE_DEPARTMENT, new DeleteDepartment());

        mapOfActions.put(EDIT_EMPLOYEE, new EditEmployee());
        mapOfActions.put(EDIT_DEPARTMENT, new EditDepartment());
        mapOfActions.put(LIST_EMPLOYEES, new EmpListProcessor());
    }

    /**
     * Method for singleton-pattern.
     *
     * @return instance of class Actions
     */
    public static Actions getInstance() {
        return Actions.INSTANCE;
    }

    /**
     * Getter for map of actions.
     *
     * @return map of actions
     */
    public Map<String, Object> getMapOfActions() {
        return mapOfActions;
    }

}
