package model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import oracle.jdbc.driver.OracleDriver;


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


    public List<EmployeeImpl> getAllEmployees() {
//    public List<Employee> getAllEmployees() {
        Connection connection = connect_JDBC();
        ResultSet result = null;
        PreparedStatement statement = null;
        ArrayList<EmployeeImpl> listEmpl = new ArrayList<EmployeeImpl>();
        EmployeeImpl employee;
        System.out.println("dfh");  // debug
        try {
            statement = connection.prepareStatement("SELECT * FROM lab3_Employees ORDER BY EMP_NAME");
            result = statement.executeQuery();
            while(result.next()){
                Integer employeeId =  Integer.parseInt(result.getString("EMP_ID"));
                String name = result.getString("EMP_NAME");
                Integer departmentId = Integer.parseInt(result.getString("DEPARTMENT_ID"));
                System.out.println(name);
                employee = new EmployeeImpl(employeeId, name, null, null, departmentId, null, null);
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


    @Override
    public List<Department> getAllDepartments() {
        return null;
    }


}
