package project.Rent.a.car.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import project.Rent.a.car.models.CarModel;
import project.Rent.a.car.models.UserModel;

import java.util.List;

@Service
public class CarService {

    private JdbcTemplate db;

    public CarService(JdbcTemplate jdbc) {
        this.db = jdbc;
    }


    //this should return all the cars with the correct city/id
    public List<CarModel> getAllCarsByCityId(int cityId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM cars WHERE city_id = ?");

        return this.db.query(query.toString(), new Object[]{cityId}, (RowMapper<CarModel>) (rs, rowNum) -> {
            CarModel car = new CarModel();
            car.setId(rs.getInt("id"));
            car.setModel(rs.getString("model"));
            car.setDailyRate(rs.getDouble("daily_rate"));
            car.setCityId(rs.getInt("city_id"));
            return car;
        });
    }

    public CarModel getCarByCarId(int carId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM cars WHERE id = ?");

        return this.db.queryForObject(query.toString(), new Object[]{carId}, (rs, rowNum) -> {
            CarModel car = new CarModel();
            car.setId(rs.getInt("id"));
            car.setModel(rs.getString("model"));
            car.setDailyRate(rs.getDouble("daily_rate"));
            car.setCityId(rs.getInt("city_id"));
            return car;
        });
    }

//    public List<CarModel> getCarByCarId(int carId) {
//        StringBuilder query = new StringBuilder();
//        query.append("SELECT * FROM cars WHERE id = ?");
//
//        return this.db.query(query.toString(), new Object[]{carId}, (RowMapper<CarModel>) (rs, rowNum) -> {
//            CarModel car = new CarModel();
//            car.setId(rs.getInt("id"));
//            car.setModel(rs.getString("model"));
//            car.setDailyRate(rs.getDouble("daily_rate"));
//            car.setCityId(rs.getInt("city_id"));
//            return car;
//        });
//
//    }




    public boolean createCar(CarModel car) {
        System.out.println("City ID received: " + car.getCityId());  // Log the city_id
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO cars ")
                .append("(model, daily_rate, city_id) ")
                .append("VALUES ('")
                .append(car.getModel())
                .append("', ")
                .append(car.getDailyRate())
                .append(", ")
                .append(car.getCityId())
                .append(")");

        this.db.execute(query.toString());
        return true;
    }

    public boolean updateCar(int id, CarModel newcar) {
        StringBuilder query = new StringBuilder();

        query.append("UPDATE cars ")
                .append("SET model = '").append(newcar.getModel()).append("', ")
                .append("daily_rate = ").append(newcar.getDailyRate()).append(", ")
                .append("city_id = ").append(newcar.getCityId()).append(", ")
                .append("deleted = ").append(newcar.isDeleted())
                .append(" WHERE id = ").append(id);

        try {
            int rowsUpdated = this.db.update(query.toString());
            return rowsUpdated > 0;
        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }



}
