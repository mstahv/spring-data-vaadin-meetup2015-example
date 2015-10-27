package hello;

import javax.persistence.Entity;

@Entity
public class Company extends AbstractEntity {

    private String name;

    protected Company() {}

    public Company(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Companty[id=%d, name='%s']",
                id, name);
    }

}
