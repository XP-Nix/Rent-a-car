package project.Rent.a.car.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import project.Rent.a.car.models.CarModel;
import project.Rent.a.car.models.OfferModel;
import project.Rent.a.car.util.Utility;

import java.util.List;

@Service
public class OfferService {

    private JdbcTemplate db;
    private CarService carService;
    private UserService userService;

    public OfferService(JdbcTemplate jdbc, CarService carService, UserService userService) {
        this.db = jdbc;
        this.carService = carService;
        this.userService = userService;
    }

    public boolean createOffer(OfferModel offer) {

        //calculate
        double carPrice      = this.carService.getCarByCarId(offer.getCarId()).getDailyRate();
        double rentDays      = offer.getRentalDays();
        boolean isWeekend    = false; //todo  rework rent days: use date format with checker in utils
        boolean hasAccidents = this.userService.getUserByIdd(offer.getUserId()).isHasAccidents();//rename


        offer.setTotalPrice(Utility.calcPrice(carPrice,rentDays,isWeekend,hasAccidents));

        //create the offer
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO offers ")
                .append("(user_id, car_id, rental_days, total_price, accepted, deleted) ")
                .append("VALUES (")
                .append(offer.getUserId())
                .append(", ")
                .append(offer.getCarId())
                .append(", ")
                .append(offer.getRentalDays())
                .append(", ")
                .append(offer.getTotalPrice())
                .append(", ")
                .append(offer.isAccepted())
                .append(", ")
                .append(offer.isDeleted())
                .append(")");

        this.db.execute(query.toString());
        return true;

    }




    public List<OfferModel> getAllOffersByUser(int userId) {

        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM offers WHERE user_id = ?");

        return this.db.query(query.toString(), new Object[]{userId}, (RowMapper<OfferModel>) (rs, rowNum) -> {
            OfferModel offerModel = new OfferModel();
            offerModel.setId(rs.getInt("id"));
            offerModel.setUserId(rs.getInt("user_id"));
            offerModel.setCarId(rs.getInt("car_id"));
            offerModel.setRentalDays(rs.getInt("rental_days"));
            offerModel.setTotalPrice(rs.getInt("total_price"));
            offerModel.setAccepted(rs.getBoolean("accepted"));
            offerModel.setDeleted(rs.getBoolean("deleted"));
            return offerModel;
        });


    }

    public OfferModel getOfferByIdd(int offerId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM offers WHERE id = ?");

        return this.db.queryForObject(query.toString(), new Object[]{offerId}, (rs, rowNum) -> {
            OfferModel offerModel = new OfferModel();
            offerModel.setId(rs.getInt("id"));
            offerModel.setUserId(rs.getInt("user_id"));
            offerModel.setCarId(rs.getInt("car_id"));
            offerModel.setRentalDays(rs.getInt("rental_days"));
            offerModel.setTotalPrice(rs.getInt("total_price"));
            offerModel.setAccepted(rs.getBoolean("accepted"));
            offerModel.setDeleted(rs.getBoolean("deleted"));
            return offerModel;
        });
    }

    public boolean updateOffer(int id, OfferModel newOffer) {

        StringBuilder query = new StringBuilder();

        query.append("UPDATE offers ")
                .append("SET user_id = '").append(newOffer.getUserId())    .append("', ")
                .append("car_id = ")      .append(newOffer.getCarId())     .append(", ")
                .append("rental_days = ") .append(newOffer.getRentalDays()).append(", ")
                .append("total_price = ") .append(newOffer.getTotalPrice()).append(", ")
                .append("accepted = ")    .append(newOffer.isAccepted())   .append(", ")
                .append("deleted = ") .append(newOffer.isDeleted())
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
