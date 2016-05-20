package model;

/**
 * Created by itps13 on 14.04.2016.
 */
public class DepartmentImpl implements Department {

    private Integer id;
    private String name;
    private String description;

    public DepartmentImpl(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String name) {

    }
}
