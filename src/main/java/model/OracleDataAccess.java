package model;

// import oracle.jdbc.driver.OracleDriver;

import com.sun.xml.internal.bind.v2.TODO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.Date;


/**
 * Created by khoruzh on 15.04.2016.
 * Realization of interface for working with data base.
 */
public class OracleDataAccess implements DataAccess {
    public static final Logger LOG = LogManager.getLogger(OracleDataAccess.class);

    private static final String SQL_SELECT_ALL_EMPLOYEES =  "" +
            "select  emp.*, man.EMP_NAME  as manName, dep.DEPARTMENT_NAME as depName " +
            "from lab3_Employees emp  " +
            "   left join LAB3_EMPLOYEES   man on man.EMP_ID = emp.MANAGER_ID " +
            "   left join LAB3_DEPARTMENTS dep on dep.DEPARTMENT_ID = emp.DEPARTMENT_ID " +
            "ORDER BY emp.EMP_NAME ";
    private static final String SQL_SELECT_FILTERED_EMLOYEES_RANGE = "" +
            "SELECT *" +
            "FROM (SELECT page.*,ROWNUM rn" +
            "      FROM (select  emp.*, nvl(to_char(man.EMP_NAME), ' ')  as manName, dep.DEPARTMENT_NAME as depName " +
            "from lab3_Employees emp  " +
            "left join LAB3_EMPLOYEES   man on man.EMP_ID = emp.MANAGER_ID " +
            "left join LAB3_DEPARTMENTS dep on dep.DEPARTMENT_ID = emp.DEPARTMENT_ID " +
            "where  ( :1  is null  or  lower(emp.EMP_NAME)    like  :2 ) " +
            "   and ( :3  is null  or  lower(emp.JOB_NAME)    like  :4 ) " +
            "   and ( :5  is null  or  emp.SALARY          >= :6     ) " +
            "   and ( :7  is null  or  emp.SALARY          <= :8     ) " +
            "   and ( :9  is null  or  emp.DEPARTMENT_ID    = :10    ) " +
            "   and ( :11 is null  or  emp.MANAGER_ID       = :12    ) " +
            "   and ( :13 is null  or  emp.DATE_IN         >= :14    ) " +
            "   and ( :15 is null  or  emp.DATE_IN         <= :16     ) " +
            "   and ( :17 is null  or  lower(man.EMP_NAME)         like :18  ) " +
            "   and ( :19 is null  or  lower(dep.DEPARTMENT_NAME)  like :20 ) " +
            "ORDER BY emp.EMP_NAME) page)" +
            "WHERE rn BETWEEN :21 AND :22 ";

    private static final String SQL_SELECT_FILTERED_EMLOYEES = "" +
            "select  emp.*, nvl(to_char(man.EMP_NAME), ' ')  as manName, dep.DEPARTMENT_NAME as depName " +
            "from lab3_Employees emp  " +
            "left join LAB3_EMPLOYEES   man on man.EMP_ID = emp.MANAGER_ID " +
            "left join LAB3_DEPARTMENTS dep on dep.DEPARTMENT_ID = emp.DEPARTMENT_ID " +
            "where  ( :1  is null  or  lower(emp.EMP_NAME)    like  :2 ) " +
            "   and ( :3  is null  or  lower(emp.JOB_NAME)    like  :4 ) " +
            "   and ( :5  is null  or  emp.SALARY          >= :6     ) " +
            "   and ( :7  is null  or  emp.SALARY          <= :8     ) " +
            "   and ( :9  is null  or  emp.DEPARTMENT_ID    = :10    ) " +
            "   and ( :11 is null  or  emp.MANAGER_ID       = :12    ) " +
            "   and ( :13 is null  or  emp.DATE_IN         >= :14    ) " +
            "   and ( :15 is null  or  emp.DATE_IN         <= :16     ) " +
            "   and ( :17 is null  or  lower(man.EMP_NAME)         like :18  ) " +
            "   and ( :19 is null  or  lower(dep.DEPARTMENT_NAME)  like :20 ) " +
            "ORDER BY emp.EMP_NAME ";

    private static final String SQL_SELECT_EMPLOYEE_BY_ID = "" +
            "select  emp.*, man.EMP_NAME  as manName, dep.DEPARTMENT_NAME as depName " +
            "from lab3_Employees emp  " +
            "   left join LAB3_EMPLOYEES   man on man.EMP_ID = emp.MANAGER_ID " +
            "   left join LAB3_DEPARTMENTS dep on dep.DEPARTMENT_ID = emp.DEPARTMENT_ID " +
            "where emp.EMP_ID = ? ";
    private static final String SQL_SELECT_ALL_DEPARTMENTS = "SELECT * FROM LAB3_DEPARTMENTS ORDER BY DEPARTMENT_NAME";
    private static final String SQL_SELECT_DEPARTMENT_BY_ID = "" +
            "select  * " +
            "from LAB3_DEPARTMENTS   " +
            "where DEPARTMENT_ID = :1 ";
    private static final String SQL_INSERT_EMPLOYEE = "" +
            "insert into LAB3_EMPLOYEES " +
            "   (MANAGER_ID, EMP_NAME, DEPARTMENT_ID, JOB_NAME, SALARY, DATE_IN )  " +
            "values ( :1, :2, :3, :4, :5, :6) ";
    private static final String SQL_UPDATE_EMPLOYEE = " " +
            " update LAB3_EMPLOYEES " +
            " set MANAGER_ID    = ?, " +
            "     EMP_NAME      = ?, " +
            "     DEPARTMENT_ID = ?, " +
            "     JOB_NAME      = ?, " +
            "     SALARY        = ?, " +
            "     DATE_IN       = ?  " +
            " where  EMP_ID = ? ";
    private static final String SQL_DELETE_EMPLOYEE = "delete from  LAB3_EMPLOYEES where  EMP_ID = :1 ";
    private static final String SQL_INSERT_DEPARTMENT = "" +
            "insert into LAB3_DEPARTMENTS " +
            "   ( DEPARTMENT_NAME, DESCRIPTION )  " +
            "values ( :1, :2 ) ";
    private static final String SQL_UPDATE_DEPARTMENT = " " +
            " update LAB3_DEPARTMENTS " +
            " set DEPARTMENT_NAME  = ?, " +
            "     DESCRIPTION      = ? " +
            " where  DEPARTMENT_ID = ? ";
    private static final String SQL_DELETE_DEPARTMENT = "delete from  LAB3_DEPARTMENTS where  DEPARTMENT_ID = :1 ";

    private static final OracleDataAccess INSTANCE = new OracleDataAccess();
    private DataSource ds;
    private Context ctx;
    private Hashtable ht = new Hashtable();

    private OracleDataAccess() {
        super();
    }

    /**
     * Getter for singleton.
     *
     * @return instance of class.
     */
    public static OracleDataAccess getInstance() {
        return INSTANCE;
    }

    /**
     * Возвращает коннект к базе данных через WebLogic DataSource
     *
     * @return - WebLogic DataSource  connection
     */
    private Connection connectWebLogicDs() {
        ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        ht.put(Context.PROVIDER_URL, "t3://localhost:7001");
        try {
            ctx = new InitialContext(ht);
            ds = (javax.sql.DataSource) ctx.lookup("DataBase");
        } catch (NamingException e) {
            //System.err.println(e.getMessage());
            LOG.error("Error while looking up data source: " + e);
        }
        Connection connection = null;
        try {
            connection = ds.getConnection();
        } catch (SQLException e) {
            //System.err.println(e.getMessage());
            LOG.error("Error while connecting to data base: " + e);
        }
        return connection;
    }

    /**
     * Возвращает коннект к базе данных
     * @return
     */
    private Connection getConnection() {
        return connectWebLogicDs();
//        return connectJDBC();
    }

    /**
     * Disconnect with ResultSet
     * @param connection - Connection
     * @param result - ResultSet
     * @param statement - Statement
     */
    private void disconnect(Connection connection, ResultSet result, Statement statement) {
        try {
            if(statement != null)
                statement.close();
            if(connection != null)
                connection.close();
            if(result != null)
                result.close();
        } catch (SQLException e) {
            //System.err.println(e.getMessage());
            LOG.error("Error while disconnecting from data base: " + e);
        }
    }

    /**
     * Disconnect without ResultSet
     * @param connection - Connection
     * @param statement - Statement
     */
    private void disconnect(Connection connection, Statement statement) {
        try {
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            //System.err.println(e.getMessage());
            LOG.error("Error while disconnecting from data base: " + e);
        }
    }

    /**
     * Возвращает список всех работников (из базы данных).
     * @return List<Employee> - список всех работников.
     */
    @Override
    public List<Employee> getAllEmployees() {
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        ArrayList<Employee> listEmpl = new ArrayList<Employee>();
        Employee employee;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ALL_EMPLOYEES);
            result = statement.executeQuery();
            while (result.next()) {
                employee = getEmployeeFromResultSet(result);
                listEmpl.add(employee);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            LOG.error("Error while query SQL_SELECT_ALL_EMPLOYEES: " + e);
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
     */
    @Override
    public List<Employee> getEmployeesFiltered(String pName, String pJobName, Float pSalaryFrom, Float pSalaryTo,
                                               Integer pDepartmentId, Integer pManagerId, Date pDateInFrom, Date pDateInTo,
                                               String pManagerName, String pDepartmentName, int page, int range) {
        LOG.info("Search parameters: \n Name: " + pName + ". Job " + pJobName + ". Salary from " + pSalaryFrom + ". Salary to " + pSalaryTo +
                ". Department Id " + pDepartmentId + ". Manager Id " + pManagerId + ". Date from " + pDateInFrom + ". Date to " + pDateInTo +
                ". Manager name " + pManagerName + ". Department name " + pDepartmentName);
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        ArrayList<Employee> listEmpl = new ArrayList<>();
        Employee employee;
        try {
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

            statement = connection.prepareStatement(SQL_SELECT_FILTERED_EMLOYEES_RANGE);
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

/*            statement.setInt(21, page);
            statement.setInt(22, range);*/
            statement.setInt(21, ((page - 1) * range + 1));
            statement.setInt(22, (page * range));



            result = statement.executeQuery();
            while (result.next()) {
                employee = getEmployeeFromResultSet(result);
                listEmpl.add(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Error while query SQL_SELECT_FILTERED_EMLOYEES_RANGE: " + e);
        } finally {
            disconnect(connection, result, statement);
        }
        return listEmpl;
    }

    public List<Employee> getEmployeesFiltered(String pName, String pJobName, Float pSalaryFrom, Float pSalaryTo,
                                               Integer pDepartmentId, Integer pManagerId, Date pDateInFrom, Date pDateInTo,
                                               String pManagerName, String pDepartmentName) {
        LOG.info("Search parameters: \n Name: " + pName + ". Job " + pJobName + ". Salary from " + pSalaryFrom + ". Salary to " + pSalaryTo +
                ". Department Id " + pDepartmentId + ". Manager Id " + pManagerId + ". Date from " + pDateInFrom + ". Date to " + pDateInTo +
                ". Manager name " + pManagerName + ". Department name " + pDepartmentName);
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        ArrayList<Employee> listEmpl = new ArrayList<>();
        Employee employee;
        try {
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

            statement = connection.prepareStatement(SQL_SELECT_FILTERED_EMLOYEES);
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
            LOG.error("Error while query SQL_SELECT_FILTERED_EMLOYEES: " + e);
        } finally {
            disconnect(connection, result, statement);
        }
        return listEmpl;
    }

    private String convertToQueryFormat(String st) {
        if (st == null) {return null;}
        String result =  st.trim();
        if (st.equals("")) {return null;}
        return "%"+result.toLowerCase()+"%";
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

    /**
     * Возвращает работника по заданному ID
     * @param id - id искомого работника
     * @return Employee - найденный работник (или null - если не найден)
     */
    @Override
    public Employee getEmployeeById(Integer id) {
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        Employee employee = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_EMPLOYEE_BY_ID);
            statement.setInt(1, id);
            result = statement.executeQuery();
            while (result.next()) {
                employee = getEmployeeFromResultSet(result);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            LOG.error("Error while query SQL_SELECT_EMPLOYEE_BY_ID: " + e);
        } finally {
            disconnect(connection, result, statement);
        }
        return employee;
    }

    /**
     * Возвращает список всех отделов (из базы данных).
     * @return - List<Department> - список всех отделов.
     */
    @Override
    public List<Department> getAllDepartments() {
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        ArrayList<Department> listDep = new ArrayList<Department>();
        DepartmentImpl department;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ALL_DEPARTMENTS);
            result = statement.executeQuery();
            while (result.next()) {
                Integer id = Integer.parseInt(result.getString("DEPARTMENT_ID"));
                String name = result.getString("DEPARTMENT_NAME");
                String description = result.getString("DESCRIPTION");

                department = new DepartmentImpl(id, name, description);
                listDep.add(department);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            LOG.error("Error while query SQL_SELECT_ALL_DEPARTMENTS: " + e);
        } finally {
            disconnect(connection, result, statement);
        }
        return listDep;
    }

    /**
     * Возвращает отдел по заданному ID
     * @param id - id искомого отдела
     * @return - Department - найденный отдел (или null - если не найден).
     */
    @Override
    public Department getDepartmentById(Integer id) {
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        DepartmentImpl department = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_DEPARTMENT_BY_ID);
            statement.setInt(1, id);
            result = statement.executeQuery();
            while (result.next()) {
                Integer departmentId = Integer.parseInt(result.getString("DEPARTMENT_ID"));
                String name = result.getString("DEPARTMENT_NAME");
                String description = result.getString("DESCRIPTION");
                department = new DepartmentImpl(departmentId, name, description);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            LOG.error("Error while query SQL_SELECT_DEPARTMENT_BY_ID: " + e);
        } finally {
            disconnect(connection, result, statement);
        }
        return department;
    }

    /**
     * Вставляет запись о работнике в таблицу базы данных
     * @param employee - работник, которого надо записать в базу данных
     */
    @Override
    public void insertEmployee(Employee employee) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_EMPLOYEE);
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
            LOG.info("Insert employee " + employee);
            statement.executeUpdate();
        } catch (Exception e) {
            //e.printStackTrace();
            LOG.error("Error while query SQL_INSERT_EMPLOYEE: " + e);
        } finally {
            disconnect(connection, statement);
        }
    }

    /**
     * Изменяет запись о работнике в базе данных.
     * @param employee - работник, которого будем сохранять в базе.
     */
    @Override
    public void updateEmployee(Employee employee) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_EMPLOYEE);

            if (employee.getManagerId() != null) {  statement.setInt  (1, employee.getManagerId());  }
                                            else {  statement.setNull (1, Types.INTEGER);  }
            statement.setString(2, employee.getName());
//            statement.setInt   (3, employee.getDepartmentId());
            if (employee.getDepartmentId() != null) {  statement.setInt  (3, employee.getDepartmentId());  }
                                            else    {  statement.setNull (3, Types.INTEGER);  }
            statement.setString(4, employee.getJobName());
            statement.setFloat (5, employee.getSalary());
            if (employee.getDateIn() != null) {
                java.sql.Date sqlDate = new java.sql.Date(employee.getDateIn().getTime());
                statement.setDate  (6, sqlDate);
            }
            else { statement.setNull(6, Types.DATE); }
            statement.setInt   (7, employee.getId());
            LOG.info("Update employee.");
            LOG.info("Employee before changes " + getEmployeeById(employee.getId()));
            LOG.info("Employee after changes " + employee);
            statement.executeUpdate();
        } catch (Exception e) {
            //e.printStackTrace();
            LOG.error("Error while query SQL_UPDATE_EMPLOYEE: " + e);
        }
        finally {
            disconnect(connection, statement);
        }
    }

    /**
     * Удаляет работника из базы данных
     * @param employeeId - id удаляемого работника.
     */
    @Override
    public void deleteEmployee(Integer employeeId) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_EMPLOYEE);
            statement.setInt(1, employeeId);
            LOG.info("Delete employee " + getEmployeeById(employeeId));
            statement.executeUpdate();
        } catch (Exception e) {
            //e.printStackTrace();
            LOG.error("Error while query SQL_DELETE_EMPLOYEE: " + e);
        } finally {
            disconnect(connection, statement);
        }
    }

    /**
     * Вставляет запись об отделе в базу данных
     * @param department - отдел, который будем записывать в базу
     */
    @Override
    public void insertDepartment(Department department) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_DEPARTMENT);
            statement.setString(1, department.getName());
            statement.setString(2, department.getDescription());
            LOG.info("Insert department " + department);
            statement.executeUpdate();
        } catch (Exception e) {
            //e.printStackTrace();
            LOG.error("Error while query SQL_INSERT_DEPARTMENT: " + e);
        } finally {
            disconnect(connection, statement);
        }
    }

    /**
     * Изменяет запись об отделе в базе данных.
     * @param department - отдел, который должен быть сохранён в базе.
     */
    @Override
    public void updateDepartment(Department department) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_DEPARTMENT);
            statement.setString(1, department.getName());
            statement.setString(2, department.getDescription());
            statement.setInt   (3, department.getId());
            LOG.info("Update department.");
            LOG.info("Department before changes " + getDepartmentById(department.getId()));
            LOG.info("Department after changes " + department);
            statement.executeUpdate();
        } catch (Exception e) {
            //e.printStackTrace();
            LOG.error("Error while query SQL_UPDATE_DEPARTMENT: " + e);
        }
        finally {
            disconnect(connection, statement);
        }
    }

    /**
     * Удаляет отдел из базы данных.
     * @param departmentId - id удаляемого отдела.
     */
    @Override
    public void deleteDepartment(Integer departmentId) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_DEPARTMENT);
            statement.setInt(1, departmentId);
            LOG.info("Delete department " + getDepartmentById(departmentId));
            statement.executeUpdate();
        } catch (Exception e) {
            //e.printStackTrace();
            LOG.error("Error while query SQL_UPDATE_DEPARTMENT: " + e);
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
        System.out.println(employee);
        return employee;
    }

    @Override
    public int getTotalCountOfEmployees() {
            Connection connection = getConnection();
            ResultSet resultSet = null;
            PreparedStatement statement = null;

            int number = 0;

            try {
                // TODO: 26.06.2016 вынести на уровень класса
                statement = connection.prepareStatement("SELECT count(EMP_ID) AS COUNT FROM LAB3_EMPLOYEES");
                resultSet = statement.executeQuery();
                resultSet.next();
                number = resultSet.getInt("COUNT");
                System.out.println("TotalCountOfEmployees " + number);
            } catch (Exception e) {
                e.printStackTrace();
                LOG.error("Error while query count of employees: " + e);
            }
            finally {
                disconnect(connection, resultSet, statement);
            }
            return number;
    }

    @Override
    public List<Employee> getAllEmployeesByPage(int page, int range) {

        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;

        Employee employee;
        List<Employee> employeeList = new ArrayList<Employee>();

        try {
            // TODO: 26.06.2016 вынести на уровень класса
            statement = connection.prepareStatement("SELECT *" +
                    "FROM (SELECT page.*,ROWNUM rn" +
                    "      FROM (select  emp.*, man.EMP_NAME  as manName, dep.DEPARTMENT_NAME as depName" +
                    "            from lab3_Employees emp" +
                    "            left join LAB3_EMPLOYEES   man on man.EMP_ID = emp.MANAGER_ID" +
                    "            left join LAB3_DEPARTMENTS dep on dep.DEPARTMENT_ID = emp.DEPARTMENT_ID" +
                    "            ORDER BY emp.EMP_NAME) page)" +
                    "WHERE rn BETWEEN ? AND ?");

            statement.setInt(1, ((page - 1) * range + 1));
            statement.setInt(2, (page * range));

            LOG.info("Page from " + ((page - 1) * range + 1) + " to " + (page * range));
            result = statement.executeQuery();

            while(result.next()){
                employee = getEmployeeFromResultSet(result);
                employeeList.add(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Error while query set of employees for page: " + e);
        }
        finally {
            disconnect(connection, result, statement);
        }
        return employeeList;
    }
}
