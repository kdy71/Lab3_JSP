package model;

import oracle.jdbc.OraclePreparedStatement;
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
     *
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
     *
     * @return - WebLogic DataSource  connection
     */
    public Connection connect_WebLogic_ds() {
        ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        ht.put(Context.PROVIDER_URL, "t3://localhost:7001");
        try {
            ctx = new InitialContext(ht);
            ds = (javax.sql.DataSource) ctx.lookup("DataBase");
        } catch (NamingException e) {
            System.err.println(e.getMessage());
        }

        Connection connection = null;
        try {
            connection = ds.getConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
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
            System.err.println(e.getMessage());
        }
    }


    public void disconnect(Connection connection, Statement statement) {
        try {
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    //    public List<EmployeeImpl> getAllEmployees() {
    public List<Employee> getAllEmployees() {
        Connection connection = connect_JDBC();
        ResultSet result = null;
        PreparedStatement statement = null;
//        ArrayList<EmployeeImpl> listEmpl = new ArrayList<EmployeeImpl>();
        ArrayList<Employee> listEmpl = new ArrayList<Employee>();
//        EmployeeImpl employee;
        Employee employee;
        try {
//          statement = connection.prepareStatement("SELECT * FROM lab3_Employees ORDER BY EMP_NAME");
            statement = connection.prepareStatement("" +
                    "select  emp.*, man.EMP_NAME  as manName, dep.DEPARTMENT_NAME as depName " +
                    "from lab3_Employees emp  " +
                    "left join LAB3_EMPLOYEES   man on man.EMP_ID = emp.MANAGER_ID " +
                    "left join LAB3_DEPARTMENTS dep on dep.DEPARTMENT_ID = emp.DEPARTMENT_ID " +
                    "ORDER BY emp.EMP_NAME ");
            result = statement.executeQuery();
            while (result.next()) {
                employee = getEmployeeFromResultSet(result);
                listEmpl.add(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect(connection, result, statement);
        }
        return listEmpl;
    }



    /**
     * Возвращает список работников, отфильтрованный по заданным параметрам.
     * Если параметр не должен участвовать в фильтрации - присвоить ему null
     * Строковые значения ищутся по начальным символам, дата приёма и зарплата - по диапазону значений.
     *
     * @param pName           - имя работника
     * @param pJobName        - должность
     * @param pSalaryFrom     - зарплата " с "
     * @param pSalaryTo       - зарплата " по "
     * @param pDepartmentId   - id подразделения
     * @param pManagerId      - id менеджера
     * @param pDateInFrom     - дата приёма " с "
     * @param pDateInTo       - дата приёма " по "
     * @param pManagerName    - имя менеджера
     * @param pDepartmentName - наименование подразделения
     * @return - возвращает список работников, для которых выполняются все условия фильтрации

    public List<Employee> findEmployees(String name, String job, Integer salMin, Integer salMax, String department,
    String manager, Date dateMin, Date dateMax) {
     */
    public List<Employee> getEmployeesFiltered(String pName, String pJobName, Float pSalaryFrom, Float pSalaryTo,
                                               Integer pDepartmentId, Integer pManagerId, Date pDateInFrom, Date pDateInTo,
                                               String pManagerName, String pDepartmentName) {
        System.out.println("   --- getEmployeesFiltered(String pName, String pJobName,... ---");
        Connection connection = connect_JDBC();
        ResultSet result = null;
        PreparedStatement statement = null;
//        OraclePreparedStatement statement = null;
        ArrayList<Employee> listEmpl = new ArrayList<Employee>();
        Employee employee;
        try {
/*            System.out.println("    getEmployeesFiltered - try 1");
            System.out.println("pName= <<"+pName+">>"); // debug
            pName = convertToQueryFormat(pName);
            System.out.println("pName= <<"+pName+">>"); // debug
            System.out.println("pManagerName= <<"+pManagerName+">>"); // debug
            pManagerName = convertToQueryFormat(pManagerName);
            System.out.println("pManagerName= <<"+pManagerName+">>"); // debug
            System.out.println("pSalaryFrom1= <<"+pSalaryFrom+">>"); // debug
            pSalaryFrom = convertToQueryFormat(pSalaryFrom);
            System.out.println("pSalaryFrom2= <<"+pSalaryFrom+">>"); // debug
            System.out.println("pDateInFrom1= <<"+pDateInFrom+">>"); // debug
            pDateInFrom = convertToQueryFormat(pDateInFrom);
            System.out.println("pDateInFrom2= <<"+pDateInFrom+">>"); // debug  */

            pName           = convertToQueryFormat(pName          );
            pJobName        = convertToQueryFormat(pJobName       );
            pSalaryFrom     = convertToQueryFormat(pSalaryFrom    );
            pSalaryTo       = convertToQueryFormat(pSalaryTo      );
            pDepartmentId   = convertToQueryFormat(pDepartmentId  );
            pManagerId      = convertToQueryFormat(pManagerId     );
            pDateInFrom     = convertToQueryFormat(pDateInFrom    );
            pDateInTo       = convertToQueryFormat(pDateInTo      );
            pManagerName    = convertToQueryFormat(pManagerName   );
            pDepartmentName = convertToQueryFormat(pDepartmentName);

            String stSQL = "" +
                    "select  emp.*, nvl(to_char(man.EMP_NAME), ' ')  as manName, dep.DEPARTMENT_NAME as depName " +
                    "from lab3_Employees emp  " +
                    "left join LAB3_EMPLOYEES   man on man.EMP_ID = emp.MANAGER_ID " +
                    "left join LAB3_DEPARTMENTS dep on dep.DEPARTMENT_ID = emp.DEPARTMENT_ID " +
                    "where  ( :1  is null  or  lower(emp.EMP_NAME)    like  :2  /*  or  1=2 */ ) " +
                    "   and ( :3  is null  or  lower(emp.JOB_NAME)    like  :4  /*or (1=2) */ ) " +
//                    "   and ( :3  is null  or  emp.JOB_NAME     = like :4        ) " +
                    "   and ( :5  is null  or  emp.SALARY          >= :6     ) " +
                    "   and ( :7  is null  or  emp.SALARY          <= :8     ) " +
                    "   and ( :9  is null  or  emp.DEPARTMENT_ID    = :10    ) " +
                    "   and ( :11 is null  or  emp.MANAGER_ID       = :12    ) " +
                    "   and ( :13 is null  or  emp.DATE_IN         >= :14    ) " +
                    "   and ( :15 is null  or  emp.DATE_IN         <= :16     ) " +
                    "   and ( :17 is null  or  lower(man.EMP_NAME)         like :18  ) " +
                    "   and ( :19 is null  or  lower(dep.DEPARTMENT_NAME)  like :20 ) " +
                    "ORDER BY emp.EMP_NAME ";
//            System.out.println("stSQL= "+stSQL); // debug
            statement = connection.prepareStatement(stSQL);
//            ((OraclePreparedStatement)statement).setStringAtName("p1", pName);
            statement.setString(1, pName); // :1
            statement.setString(2, pName); // :1
            statement.setString(3, pJobName); // :2
            statement.setString(4, pJobName); // :2
            if (pSalaryFrom != null) {
                statement.setFloat(5, pSalaryFrom); // :3
                statement.setFloat(6, pSalaryFrom); // :3
            }
            else {
                statement.setNull(5, Types.FLOAT); // :3
                statement.setNull(6, Types.FLOAT); // :3
            }
            if (pSalaryTo != null) {
                statement.setFloat(7, pSalaryTo); // :4
                statement.setFloat(8, pSalaryTo); // :4
            }
            else {
                statement.setNull(7, Types.FLOAT); // :4
                statement.setNull(8, Types.FLOAT); // :4
            }

            if (pDepartmentId != null) {
                statement.setInt(9, pDepartmentId);  // :5
                statement.setInt(10, pDepartmentId); // :5
            }
            else {
                statement.setNull(9, Types.INTEGER);  // :5
                statement.setNull(10, Types.INTEGER); // :5
            }

            if (pManagerId != null) {
                statement.setInt(11, pManagerId);  // :6
                statement.setInt(12, pManagerId);  // :6
            }
            else {
                statement.setNull(11, Types.INTEGER); // :6
                statement.setNull(12, Types.INTEGER); // :6
            }

            if (pDateInFrom != null) {
                java.sql.Date sqlDate = new java.sql.Date(pDateInFrom.getTime());
                statement.setDate(13, sqlDate);  // :7
                statement.setDate(14, sqlDate);  // :7
            }
            else {
                statement.setNull(13, Types.DATE); // :7
                statement.setNull(14, Types.DATE); // :7
            }

            if (pDateInTo != null) {
                java.sql.Date sqlDate = new java.sql.Date(pDateInTo.getTime());
                statement.setDate(15, sqlDate);  // :8
                statement.setDate(16, sqlDate);  // :8
            }
            else {
                statement.setNull(15, Types.DATE); // :8
                statement.setNull(16, Types.DATE); // :8
            }

            statement.setString(17, pManagerName); // :9
            statement.setString(18, pManagerName); // :9
            statement.setString(19, pDepartmentName); // :10
            statement.setString(20, pDepartmentName); // :10


            result = statement.executeQuery();
            while (result.next()) {
                employee = getEmployeeFromResultSet(result);
                listEmpl.add(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect(connection, result, statement);
        }
        return listEmpl;
    }



    private String convertToQueryFormat(String st) {
        if (st == null) {return null;}
        String result =  st.trim();
        if (st.equals("")) {return null;}
//        return "%"+result.toLowerCase(Locale.ENGLISH)+"%";
//        return result.toLowerCase()+"%";
        return "%"+result.toLowerCase()+"%";
//        return "'"+result.toLowerCase()+"%'";
//        return st;
    }

    private Float convertToQueryFormat(Float var) {
        return var;
    }

    private Integer convertToQueryFormat(Integer var) {
        return var;
    }

    private Date convertToQueryFormat(Date datArg) {
        if (datArg == null) {return null;}
        Date dat1 = new Date(1);
        if (datArg.before(dat1)) {return null;}
        return datArg;
    }




    public List<Employee> findEmployees(String name, String job, Integer salMin, Integer salMax, String department,
                                        String manager, Date dateMin, Date dateMax) {
        String namePattern = "%" + name.toLowerCase() + "%";
        String jobPattern = "%" + job.toLowerCase() + "%";
        String salMinPattern = salMin.toString();
        String salMaxPattern = salMax.toString();
        String depPattern = "%" + department.toLowerCase() + "%";
        String manPattern = "%" + manager.toLowerCase() + "%";
        String dateMinPattern = Util_dates.dateToDBString(dateMin);
        String dateMaxPattern = Util_dates.dateToDBString(dateMax);

        System.out.println(dateMinPattern); //debug
        System.out.println(dateMaxPattern); //debug

        Connection connection = connect_JDBC();
        ResultSet result = null;
        PreparedStatement statement = null;
        ArrayList<Employee> employeeList = new ArrayList<Employee>();
        Employee employee;

        try {
            statement = connection.prepareStatement("SELECT  emp.*, man.EMP_NAME AS manName, dep.DEPARTMENT_NAME AS depName\n" +
                    "FROM LAB3_EMPLOYEES emp\n" +
                    "  LEFT JOIN LAB3_EMPLOYEES man ON man.EMP_ID = emp.MANAGER_ID\n" +
                    "  LEFT JOIN LAB3_DEPARTMENTS dep ON dep.DEPARTMENT_ID = emp.DEPARTMENT_ID\n" +
                    "WHERE (LOWER(emp.EMP_NAME) LIKE :1) " +
                    " AND (LOWER(emp.JOB_NAME) LIKE :2)" +
                    " AND (emp.SALARY >= :3) AND (emp.SALARY <= :4)" +
                    " AND (LOWER(dep.DEPARTMENT_NAME) LIKE :5)" +
                    " AND (LOWER(man.EMP_NAME) LIKE :6)" +              // TODO Не выводит начальника с null-полем менеджера
                    " AND (emp.DATE_IN >= to_date(:7, 'yyyy-MM-dd'))" +
                    " AND (emp.DATE_IN <= to_date(:8, 'yyyy-MM-dd'))"
            );
            statement.setString(1, namePattern);
            statement.setString(2, jobPattern);
            statement.setString(3, salMinPattern);
            statement.setString(4, salMaxPattern);
            statement.setString(5, depPattern);
            statement.setString(6, manPattern);
            statement.setString(7, dateMinPattern);
            statement.setString(8, dateMaxPattern);

            result = statement.executeQuery();
            while (result.next()) {
                employee = getEmployeeFromResultSet(result);
                employeeList.add(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect(connection, result, statement);
        }
        return employeeList;
    }

    public List<Department> findDepartmentsByName(String name) {
        String pattern = "%" + name + "%";
        Connection connection = connect_JDBC();
        ResultSet result = null;
        PreparedStatement statement = null;
        ArrayList<Department> listDepart = new ArrayList<Department>();
        Department department;

        try {
            statement = connection.prepareStatement("SELECT dep.*\n" +
                    "FROM LAB3_DEPARTMENTS dep\n" +
                    "WHERE dep.DEPARTMENT_NAME LIKE ? ");
            statement.setString(1, pattern);
            result = statement.executeQuery();
            while (result.next()) {
                department = getDepartmentFromResultSet(result);
                listDepart.add(department);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect(connection, result, statement);
        }
        return listDepart;
    }


    public Employee getEmployeeById(Integer id) {
        Connection connection = connect_JDBC();
        ResultSet result = null;
        PreparedStatement statement = null;
        Employee employee = null;
        try {
            statement = connection.prepareStatement("" +
                    "select  emp.*, man.EMP_NAME  as manName, dep.DEPARTMENT_NAME as depName " +
                    "from lab3_Employees emp  " +
                    "left join LAB3_EMPLOYEES   man on man.EMP_ID = emp.MANAGER_ID " +
                    "left join LAB3_DEPARTMENTS dep on dep.DEPARTMENT_ID = emp.DEPARTMENT_ID " +
                    "where emp.EMP_ID = ? ");
            statement.setInt(1, id);
            result = statement.executeQuery();
            while (result.next()) {
                employee = getEmployeeFromResultSet(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // log4j
        } finally {
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
            while (result.next()) {
                Integer id = Integer.parseInt(result.getString("DEPARTMENT_ID"));
                String name = result.getString("DEPARTMENT_NAME");
                String description = result.getString("DESCRIPTION");

                department = new DepartmentImpl(id, name, description);
                listDep.add(department);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
            while (result.next()) {
                Integer departmentId = Integer.parseInt(result.getString("DEPARTMENT_ID"));
                String name = result.getString("DEPARTMENT_NAME");
                String description = result.getString("DESCRIPTION");
                department = new DepartmentImpl(departmentId, name, description);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // log4j
        } finally {
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
            if (employee.getManagerId() == null) {
                statement.setNull(1, Types.INTEGER);
            } else {
                statement.setInt(1, employee.getManagerId());
            }
            statement.setString(2, employee.getName());
            statement.setInt(3, employee.getDepartmentId());
            statement.setString(4, employee.getJobName());
            statement.setFloat(5, employee.getSalary());
//            statement.setDate  (6, (java.sql.Date)employee.getDateIn());
            if (employee.getDateIn() != null) {
                java.sql.Date sqlDate = new java.sql.Date(employee.getDateIn().getTime());
                statement.setDate(6, sqlDate);
            }
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            // log4j
        } finally {
            disconnect(connection, statement);
        }
    }


    @Override
    public void updateEmployee(Employee employee) {
        System.out.println("We are into ODA - updateEmployee(Employee employee). Begin.");  // debug
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
//            statement.setInt   (1, employee.getManagerId());
            if (employee.getManagerId() != null ) { statement.setInt   (1, employee.getManagerId()); }
            statement.setString(2, employee.getName());
            statement.setInt   (3, employee.getDepartmentId());
            statement.setString(4, employee.getJobName());
            statement.setFloat (5, employee.getSalary());
//            statement.setDate  (6, (java.sql.Date)employee.getDateIn());
            if (employee.getDateIn() != null) {
                java.sql.Date sqlDate = new java.sql.Date(employee.getDateIn().getTime());
                statement.setDate  (6, sqlDate);
            }
            statement.setInt   (7, employee.getId());
            System.out.println(" ODA - updateEmployee. -- 2 -- before execute sql.");  // debug
            statement.executeUpdate();
            System.out.println(" ODA - updateEmployee. -- 3 -- after execute sql.");  // debug
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
            statement.setInt(1, employeeId);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            // log4j
        } finally {
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
        } finally {
            disconnect(connection, statement);
        }
    }


    @Override
    public void updateDepartment(Department department) {
//        System.out.println("We are into ODA - updateDepartment(Department department). Begin.");  // debug
//        System.out.println("  department= "+department); // debug
        Connection connection = connect_JDBC();
        PreparedStatement statement = null;
        try {

            statement = connection.prepareStatement(" " +
                    " update LAB3_DEPARTMENTS " +
                    " set DEPARTMENT_NAME  = ?, " +
                    "     DESCRIPTION      = ? " +
                    " where  DEPARTMENT_ID = ? ");
            //     System.out.println("  stSql= "+stSql);
            statement.setString(1, department.getName());
            statement.setString(2, department.getDescription());
            statement.setInt   (3, department.getId());
//            System.out.println("  try to executeUpdate...");  // debug
            System.out.println(department);
            statement.executeUpdate();
//            System.out.println("  executeUpdate ok.");  // debug
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
            statement.setInt(1, departmentId);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            // log4j
        } finally {
            disconnect(connection, statement);
        }
    }


    /**
     * Возвращает экземпляр Employee (на самом деле EmployeeImpl) на основе данных из базы (ResultSet)
     *
     * @param result - ResultSet - запись из базы данных
     * @return - экземпляр Employee (на самом деле EmployeeImpl) на основе данных из базы (ResultSet)
     * @throws SQLException
     */
    private Employee getEmployeeFromResultSet (ResultSet result) throws SQLException {
        Integer employeeId   = Integer.parseInt(result.getString("EMP_ID"));
        String  name         = result.getString("EMP_NAME");
        String  jobName      = result.getString("JOB_NAME");
        Float   salary       = result.getFloat("SALARY");
        Integer departmentId = Integer.parseInt(result.getString("DEPARTMENT_ID"));
        Integer managerId    = result.getInt("MANAGER_ID");
        Date    date_in      = result.getDate("DATE_IN");
        String  managerName  = result.getString("manName");
        String  depName      = result.getString("depName");

        Employee employee = new EmployeeImpl(employeeId, name, jobName, salary,
                departmentId, managerId, date_in, managerName, depName);
        return employee;
    }

    private Department getDepartmentFromResultSet(ResultSet result) throws SQLException {
        Integer departmentId = Integer.parseInt(result.getString(1));
        String  departmentName = result.getString(2);
        String  departmentDescription = result.getString(3);

        return new DepartmentImpl(departmentId, departmentName, departmentDescription);
    }
}
