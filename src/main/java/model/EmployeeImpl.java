package model;

//import java.text.SimpleDateFormat;

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
    private String managerName;
    private String departmentName;

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


    public EmployeeImpl(Integer id, String name, String jobName, Float salary, Integer departmentId, Integer managerId,
                        Date dateIn, String managerName, String departmentName) {
        this.id = id;
        this.managerId = managerId;
        this.departmentId = departmentId;
        this.salary = salary;
        this.name = name;
        this.jobName = jobName;
        this.dateIn = dateIn;
        this.managerName = managerName;
        this.departmentName = departmentName;
    }


    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getManagerId() {
        return managerId;
    }

    @Override
    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    @Override
    public Integer getDepartmentId() {
        return departmentId;
    }

    @Override
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public Float getSalary() {
        return salary;
    }

    @Override
    public void setSalary(Float salary) {
        this.salary = salary;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getJobName() {
        return jobName;
    }

    @Override
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    @Override
    public Date getDateIn() {
        return dateIn;
    }

    @Override
    public void setDateIn(Date dateIn) {
        this.dateIn = dateIn;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeImpl)) return false;

        EmployeeImpl employee = (EmployeeImpl) o;

        if (getId() != null ? !getId().equals(employee.getId()) : employee.getId() != null) return false;
        if (getManagerId() != null ? !getManagerId().equals(employee.getManagerId()) : employee.getManagerId() != null)
            return false;
        if (getDepartmentId() != null ? !getDepartmentId().equals(employee.getDepartmentId()) : employee.getDepartmentId() != null)
            return false;
        if (getSalary() != null ? !getSalary().equals(employee.getSalary()) : employee.getSalary() != null)
            return false;
        if (getName() != null ? !getName().equals(employee.getName()) : employee.getName() != null) return false;
        if (getJobName() != null ? !getJobName().equals(employee.getJobName()) : employee.getJobName() != null)
            return false;
        return getDateIn() != null ? getDateIn().equals(employee.getDateIn()) : employee.getDateIn() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getManagerId() != null ? getManagerId().hashCode() : 0);
        result = 31 * result + (getDepartmentId() != null ? getDepartmentId().hashCode() : 0);
        result = 31 * result + (getSalary() != null ? getSalary().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getJobName() != null ? getJobName().hashCode() : 0);
        result = 31 * result + (getDateIn() != null ? getDateIn().hashCode() : 0);
        return result;
    }

    /**
     * Возвращает дату приёма в строковом формате
     *
     * @return
     */
    @Override
    public String getDateInAsString() {
        if (dateIn == null)
            return "";
        else
            return UtilDates.dateToString(dateIn);
    }

    /**
     * Возвращает зврплату в строковом формате
     *
     * @return
     */
    @Override
    public String getSalaryAsString() {
        return salary.toString();
    }

    @Override
    public String toString() {
        return "EmployeeImpl{" +
                " id=" + id +
                ", name='" + name + '\'' +
                ", jobName='" + jobName + '\'' +
                ", salary=" + salary +
                ", departmentName='" + departmentName + '\'' +
                ", managerId=" + managerId +
                ", departmentId=" + departmentId +
                ", dateIn=" + dateIn +
                ", managerName='" + managerName + '\'' +
                '}';
    }
}