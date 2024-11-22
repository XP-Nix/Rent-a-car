package project.Rent.a.car.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.Rent.a.car.http.AppResponse;
import project.Rent.a.car.models.CarModel;
import project.Rent.a.car.models.UserModel;
import project.Rent.a.car.service.CarService;
import project.Rent.a.car.util.Utility;

import java.util.List;

@Controller
public class CarController {

    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }







    @PostMapping("/car")
    public ResponseEntity<?> createNewCar(@RequestBody CarModel car){
        System.out.println(car.getCityId());

        if (!Utility.isCarInTheCity(car.getCityId())){
            return AppResponse.success().withMessage("city is invalid").build();
        }


        if (this.carService.createCar(car)){
            return AppResponse.success().withMessage("car created successfully").build();
        }

        return AppResponse.error().withMessage("car cant be createddd").build();
    }


    //list cars based on the users addres
    @GetMapping("/car-city/{cityid}")
    public ResponseEntity<?> getCarsByCityId(@PathVariable("cityid") int cityid) {

        List<CarModel> cars = this.carService.getAllCarsByCityId(cityid);

        if (cars.isEmpty()) {
            return AppResponse.error().withMessage("no id found").build();
        }

        return AppResponse.success().withData(cars).build();
    }


    @GetMapping("/car/{carid}")
    public ResponseEntity<?> getCarsByCarId(@PathVariable("carid") int carid) {

        CarModel cars = this.carService.getCarByCarId(carid);

        return AppResponse.success().withData(cars).build();

    }


    @PutMapping("car-edit/{id}")
    public ResponseEntity<?> updateCar(@PathVariable("id") int id, @RequestBody CarModel newcar) {

        carService.updateCar(id,newcar);

        return AppResponse.success().withMessage("done").build();

    }



    @DeleteMapping("/car-delete/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable int id) {

        CarModel car = this.carService.getCarByCarId(id);
        car.setDeleted(true);
        carService.updateCar(id,car);



        return AppResponse.success().withMessage("done").build();
    }



}
