package project.Rent.a.car.models;

import org.springframework.data.annotation.Id;

public class CityModel {
    private int id;
    private String name;




    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
