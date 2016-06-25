package model;

import java.util.Date;

/**
 * Created by khoruzh on 15.04.2016.
 */
public interface Employee {
    public Integer getId();

    public void setId(Integer id);

    public Integer getManagerId();

    public void setManagerId(Integer ManagerId);

    public Integer getDepartmentId();

    public void setDepartmentId(Integer DepartmentId);

    public Float getSalary();

    public void setSalary(Float salary);

    public String getName();

    public void setName(String name);

    public String getJobName();

    public void setJobName(String jobName);

    public Date getDateIn();

    public void setDateIn(Date dateIn);

    public String getManagerName();

    public void setManagerName(String managerName);

    public String getDepartmentName();

    public void setDepartmentName(String departmentName);

    public boolean equals(Object o);

    public String getDateInAsString();

    public String getSalaryAsString();

}
