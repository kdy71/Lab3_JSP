package model;

import oracle.jdbc.driver.OracleDriver;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.Date;


/**
 * Created by khoruzh on 15.04.2016.
 * Реализация интерфейса работы с базой данных  для БД Oracle
 */
public class OracleDataAccess implements DataAccess {

    private static final OracleDataAccess instance = new OracleDataAccess();

    private DataSource ds;
    private Context ctx;
    private Hashtable ht = new Hashtable();
    Driver driver = new OracleDriver();  // for jdbc connection


    private OracleDataAccess() {
    /*
        ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        ht.put(Context.PROVIDER_URL, "t3://localhost:7001");
        try {
            ctx = new InitialContext(ht);
            ds = (javax.sql.DataSource) ctx.lookup("DataBase");
        } catch (NamingException e) {
            System.err.println (e.getMessage());
        }
    */
    }



    public static OracleDataAccess getInstance() {
        return instance;
    }


    /**
     * Возвращает коннект к базе данных через JDBC
     * @return - JDBC connection
     */
    public Connection connect_JDBC() {
        Locale.setDefault(Locale.ENGLISH);
        Connection connection = null;
        try {
            DriverManager.registerDriver(driver);
//            connection = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XE", "Alef", "student");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XE", "dima", "student");
            } catch (SQLException e) {
            e.printStackTrace();
            // log4j
        }
        return connection;
    }


    /**
     * Возвращает коннект к базе данных через WebLogic DataSource
     * @return - WebLogic DataSource  connection
     */
    public Connection connect_WebLogic_ds() {
        ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        ht.put(Context.PROVIDER_URL, "t3://localhost:7001");
        try {
            ctx = new InitialContext(ht);
            ds = (javax.sql.DataSource) ctx.lookup("DataBase");
        } catch (NamingException e) {
            System.err.println (e.getMessage());
        }

        Connection connection = null;
        try {
            connection = ds.getConnection();
        } catch (SQLException e) {
            System.err.println (e.getMessage());
        }
        return connection;
    }



    public void disconnect(Connection connection, ResultSet result, Statement statement) {
        try {
            if(statement != null)
                statement.close();
            if(connection != null)
                connection.close();
            if(result != null)
                result.close();
        } catch (SQLException e) {
            System.err.println (e.getMessage());
        }
    }


    public void disconnect(Connection connection, Statement statement) {
        try {
            if(statement != null)
                statement.close();
            if(connection != null)
                connection.close();
        } catch (SQLException e) {
            System.err.println (e.getMessage());
        }
    }



    //    public List<EmployeeImpl> getAllEmployees() {
    public List<Employee> getAllEmployees() {
        Connection connection = connect_JDBC();
        ResultSet result = null;
        PreparedStatement statement = null;
//        ArrayList<EmployeeImpl> listEmpl = new ArrayList<EmployeeImpl>();
        ArrayList<Employee> listEmpl = new ArrayList<Employee>();
        EmployeeImpl employee;
        try {
//          statement = connection.prepareStatement("SELECT * FROM lab3_Employees ORDER BY EMP_NAME");
            statement = connection.prepareStatement("" +
                    "select  emp.*, man.EMP_NAME  as manName, dep.DEPARTMENT_NAME as depName " +
                    "from lab3_Employees emp  " +
                    "left join LAB3_EMPLOYEES   man on man.EMP_ID = emp.MANAGER_ID " +
                    "left join LAB3_DEPARTMENTS dep on dep.DEPARTMENT_ID = emp.DEPARTMENT_ID " +
                    "ORDER BY emp.EMP_NAME ");
            result = statement.executeQuery();
            while(result.next()){
                Integer employeeId   = Integer.parseInt(result.getString("EMP_ID"));
                String  name         = result.getString("EMP_NAME");
                String  jobName      = result.getString("JOB_NAME");
                Float   salary       = result.getFloat("SALARY");
                Integer departmentId = Integer.parseInt(result.getString("DEPARTMENT_ID"));
                Integer managerId    = result.getInt("MANAGER_ID");
                Date    date_in      = result.getDate("DATE_IN");
                String  managerName  = result.getString("manName");
                String  depName      = result.getString("depName");

//              employee = new EmployeeImpl(employeeId, name, jobName, salary, departmentId, managerId, date_in);
//                System.out.println("--- 1 -----");
                employee = new EmployeeImpl(employeeId, name, jobName, salary,
                        departmentId, managerId, date_in, managerName, depName);
//                System.out.println("--- 2 -----");
                listEmpl.add(employee);
//                System.out.println("--- 3 -----");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            disconnect(connection, result, statement);
        }
        return listEmpl;
    }

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
    public List<Employee> getEmployeesFiltered(String pName, String pJobName, Float pSalaryFrom, Float pSalaryTo,
                                               Integer pDepartmentId, Integer pManagerId, Date pDateInFrom, Date pDateInTo,
                                               String pManagerName, String pDepartmentName) {
        Connection connection = connect_JDBC();
        ResultSet result = null;
        PreparedStatement statement = null;
        ArrayList<Employee> listEmpl = new ArrayList<Employee>();
        EmployeeImpl employee;
        try {
            statement = connection.prepareStatement("" +
                    "select  emp.*, man.EMP_NAME  as manName, dep.DEPARTMENT_NAME as depName " +
                    "from lab3_Employees emp  " +
                    "left join LAB3_EMPLOYEES   man on man.EMP_ID = emp.MANAGER_ID " +
                    "left join LAB3_DEPARTMENTS dep on dep.DEPARTMENT_ID = emp.DEPARTMENT_ID " +
                    "where  ( :1  is null  or  emp.EMP_NAME     like  :1%    ) " +
                    "   and ( :2  is null  or  emp.JOB_NAME     like  :2%    ) " +
                    "   and ( :3  is null  or  emp.SALARY          >= :3     ) " +
                    "   and ( :4  is null  or  emp.SALARY          <= :4     ) " +
                    "   and ( :5  is null  or  emp.DEPARTMENT_ID    = :5     ) " +
                    "   and ( :6  is null  or  emp.MANAGER_ID       = :6     ) " +
                    "   and ( :7  is null  or  emp.DATE_IN         >= :7     ) " +
                    "   and ( :8  is null  or  emp.DATE_IN         <= :8     ) " +
                    "   and ( :9  is null  or  man.EMP_NAME         like :9% ) " +
                    "   and ( :10 is null  or  dep.DEPARTMENT_NAME  like :10%) " +
                    "ORDER BY emp.EMP_NAME ");
            statement.setString(1, pName);
            result = statement.executeQuery();
            while(result.next()){
                Integer employeeId   = Integer.parseInt(result.getString("EMP_ID"));
                String  name         = result.getString("EMP_NAME");
                String  jobName      = result.getString("JOB_NAME");
                Float   salary       = result.getFloat("SALARY");
                Integer departmentId = Integer.parseInt(result.getString("DEPARTMENT_ID"));
                Integer managerId    = result.getInt("MANAGER_ID");
                Date    date_in      = result.getDate("DATE_IN");
                String  managerName  = result.getString("manName");
                String  depName      = result.getString("depName");

                employee = new EmployeeImpl(employeeId, name, jobName, salary,
                        departmentId, managerId, date_in, managerName, depName);
                listEmpl.add(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            disconnect(connection, result, statement);
        }
        return listEmpl;
    }



    public Employee getEmployeeById(Integer id) {
        Connection connection = connect_JDBC();
        ResultSet result = null;
        PreparedStatement statement = null;
        EmployeeImpl employee = null;
        try {
            statement = connection.prepareStatement("" +
                    "select  emp.*, man.EMP_NAME  as manName, dep.DEPARTMENT_NAME as depName " +
                    "from lab3_Employees emp  " +
                    "left join LAB3_EMPLOYEES   man on man.EMP_ID = emp.MANAGER_ID " +
                    "left join LAB3_DEPARTMENTS dep on dep.DEPARTMENT_ID = emp.DEPARTMENT_ID " +
                    "where emp.EMP_ID = ? ");
            statement.setInt(1, id);
            result = statement.executeQuery();
            while(result.next()){
                Integer employeeId   = Integer.parseInt(result.getString("EMP_ID"));
                String  name         = result.getString("EMP_NAME");
                String  jobName      = result.getString("JOB_NAME");
                Float   salary       = result.getFloat("SALARY");
                Integer departmentId = Integer.parseInt(result.getString("DEPARTMENT_ID"));
                Integer managerId    = result.getInt("MANAGER_ID");
                Date    date_in      = result.getDate("DATE_IN");
                String  managerName  = result.getString("manName");
                String  depName      = result.getString("depName");
                employee = new EmployeeImpl(employeeId, name, jobName, salary,
                        departmentId, managerId, date_in, managerName, depName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // log4j
        }
        finally {
            disconnect(connection, result, statement);
        }
        return employee;
    }


    @Override
    public List<Department> getAllDepartments() {
        Connection connection = connect_JDBC();
        ResultSet result = null;
        PreparedStatement statement = null;
        ArrayList<Department> listDep = new ArrayList<Department>();
        DepartmentImpl department;
        try {
            statement = connection.prepareStatement("SELECT * FROM LAB3_DEPARTMENTS ORDER BY DEPARTMENT_NAME");
            result = statement.executeQuery();
            while(result.next()){
                Integer id   = Integer.parseInt(result.getString("DEPARTMENT_ID"));
                String  name         = result.getString("DEPARTMENT_NAME");
                String  description  = result.getString("DESCRIPTION");

                department = new DepartmentImpl(id, name, description);
                listDep.add(department);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            disconnect(connection, result, statement);
        }
        return listDep;
    }


    @Override
    public Department getDepartmentById(Integer id) {
        Connection connection = connect_JDBC();
        ResultSet result = null;
        PreparedStatement statement = null;
        DepartmentImpl department = null;
        try {
            statement = connection.prepareStatement("" +
                    "select  * " +
                    "from LAB3_DEPARTMENTS   " +
                    "where DEPARTMENT_ID = :1 ");
            statement.setInt(1, id);
            result = statement.executeQuery();
            while(result.next()){
                Integer departmentId   = Integer.parseInt(result.getString("DEPARTMENT_ID"));
                String  name         = result.getString("DEPARTMENT_NAME");
                String  description  = result.getString("DESCRIPTION");
                department = new DepartmentImpl(departmentId, name, description);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // log4j
        }
        finally {
            disconnect(connection, result, statement);
        }
        return department;
    }


    @Override
    public void insertEmployee(Employee employee) {
        Connection connection = connect_JDBC();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("" +
                    "insert into LAB3_EMPLOYEES " +
                    "   (MANAGER_ID, EMP_NAME, DEPARTMENT_ID, JOB_NAME, SALARY, DATE_IN )  " +
                    "values ( :1, :2, :3, :4, :5, :6) ");
            statement.setInt   (1, employee.getManagerId());
            statement.setString(2, employee.getName());
            statement.setInt   (3, employee.getDepartmentId());
            statement.setString(4, employee.getJobName());
            statement.setFloat (5, employee.getSalary());
            statement.setDate  (6, (java.sql.Date)employee.getDateIn());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            // log4j
        }
        finally {
            disconnect(connection, statement);
        }
    }


    @Override
    public void updateEmployee(Employee employee) {
        Connection connection = connect_JDBC();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(" " +
                    " update LAB3_EMPLOYEES " +
                    " set MANAGER_ID    = ?, " +
                    "     EMP_NAME      = ?, " +
                    "     DEPARTMENT_ID = ?, " +
                    "     JOB_NAME      = ?, " +
                    "     SALARY        = ?, " +
                    "     DATE_IN       = ?  " +
                    " where  EMP_ID = ? ");
            statement.setInt   (1, employee.getManagerId());
            statement.setString(2, employee.getName());
            statement.setInt   (3, employee.getDepartmentId());
            statement.setString(4, employee.getJobName());
            statement.setFloat (5, employee.getSalary());
            statement.setDate  (6, (java.sql.Date)employee.getDateIn());
            statement.setInt   (7, employee.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            // log4j
        }
        finally {
            disconnect(connection, statement);
        }
    }


    @Override
    public void deleteEmployee(Integer employeeId) {
        Connection connection = connect_JDBC();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("delete from  LAB3_EMPLOYEES where  EMP_ID = :1 ");
            statement.setInt   (1, employeeId);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            // log4j
        }
        finally {
            disconnect(connection, statement);
        }
    }


    @Override
    public void insertDepartment(Department department) {
        Connection connection = connect_JDBC();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("" +
                    "insert into LAB3_DEPARTMENTS " +
                    "   ( DEPARTMENT_NAME, DESCRIPTION )  " +
                    "values ( :1, :2 ) ");
            statement.setString(1, department.getName());
            statement.setString(2, department.getDescription());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            // log4j
        }
        finally {
            disconnect(connection, statement);
        }
    }


    @Override
    public void updateDepartment(Department department) {
        Connection connection = connect_JDBC();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(" " +
                    " update LAB3_DEPARTMENTS " +
                    " set DEPARTMENT_NAME  = ?, " +
                    "     DESCRIPTION      = ?, " +
                    " where  DEPARTMENT_ID = ? ");
            statement.setString(1, department.getName());
            statement.setString(2, department.getDescription());
            statement.setInt   (3, department.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            // log4j
        }
        finally {
            disconnect(connection, statement);
        }
    }


    @Override
    public void deleteDepartment(Integer departmentId) {
        Connection connection = connect_JDBC();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("delete from  LAB3_DEPARTMENTS where  DEPARTMENT_ID = :1 ");
            statement.setInt   (1, departmentId);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            // log4j
        }
        finally {
            disconnect(connection, statement);
        }
    }
}
