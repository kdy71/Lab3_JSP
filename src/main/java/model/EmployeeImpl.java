package model;

import java.util.Date;

/**
 * Created by itps13 on 14.04.2016.
 */
public class EmployeeImpl implements Employee {
    private Integer id;
    private Integer managerId;
    private Integer departmentId;
    private Float salary;
    private String name;
    private String jobName;
    private Date dateIn;

    public EmployeeImpl() {
        super();
    }


    public EmployeeImpl(Integer id, String name, String jobName, Float salary,
                        Integer departmentId, Integer managerId, Date dateIn) {
        this.id = id;
        this.managerId = managerId;
        this.departmentId = departmentId;
        this.salary = salary;
        this.name = name;
        this.jobName = jobName;
        this.dateIn = dateIn;
    }


    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {

    }

    @Override
    public Integer getManagerId() {
        return managerId;
    }

    @Override
    public void setManagerId(Integer ManagerId) {

    }

    @Override
    public Integer getDepartmentId() {
        return departmentId;
    }

    @Override
    public void setDepartmentId(Integer DepartmentId) {

    }

    @Override
    public Float getSalary() {
        return salary;
    }

    @Override
    public void setSalary(Float salary) {

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getJobName() {
        return jobName;
    }

    @Override
    public void setJobName(String jobName) {

    }

    @Override
    public Date getDateIn() {
        return dateIn;
    }

    @Override
    public void setDateIn(Date dateIn) {

    }
}
