package project.Rent.a.car.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import project.Rent.a.car.models.OfferModel;
import project.Rent.a.car.models.UserModel;

import java.util.List;

@Service    //sql goes here
public class UserService {

    private JdbcTemplate db;

    public UserService(JdbcTemplate jdbc) {
        this.db = jdbc;
    }

    public boolean createUser(UserModel user){
        StringBuilder quarry = new StringBuilder();
        quarry.append("INSERT INTO users")
                .append("(name)")
                .append("VALUES")
                .append("('")
                .append(user.getName())
                .append("')");


        this.db.execute(quarry.toString());
        return true;
    }

    //wtf
    public List<UserModel> getAllUsers(){
        StringBuilder quarry = new StringBuilder();
        quarry.append("SELECT * FROM users");
        return this.db.query(quarry.toString(), (RowMapper<UserModel>)(rs, rowNum)->{//get object of the table row
            UserModel user = new UserModel();
            user.setName(rs.getString("name"));//get the name from the row we have
            user.setId(rs.getInt("id"));
            //adddd more stuff as needed
            return user;
        });
    }


    public UserModel getUserByIdd(int userId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM users WHERE id = ?");

        return this.db.queryForObject(query.toString(), new Object[]{userId}, (rs, rowNum) -> {
            UserModel userModel = new UserModel();
            userModel.setId(rs.getInt("id"));
            userModel.setName(rs.getString("name"));
            userModel.setAddress(rs.getString("address"));
            userModel.setPhone(rs.getString("phone"));
            userModel.setAge(rs.getInt("age"));
            userModel.setHasAccidents(rs.getBoolean("has_accidents"));
            return userModel;
        });
    }


}
