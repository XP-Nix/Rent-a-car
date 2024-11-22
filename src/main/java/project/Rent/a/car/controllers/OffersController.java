package project.Rent.a.car.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.Rent.a.car.http.AppResponse;
import project.Rent.a.car.models.CarModel;
import project.Rent.a.car.models.OfferModel;
import project.Rent.a.car.service.OfferService;

import java.util.List;

@Controller
public class OffersController {

    private OfferService offerService;

    public OffersController(OfferService offerService) {
        this.offerService = offerService;
    }


    @PostMapping("/offer")
    public ResponseEntity<?> createNewOffer(@RequestBody OfferModel offer){





        if (this.offerService.createOffer(offer)){
            return AppResponse.success().withMessage("car created successfully").build();
        }

        return AppResponse.error().withMessage("car cant be createddd").build();
    }


    @GetMapping("/offer-user/{id}")
    public ResponseEntity<?> getAllOffersByUser(@PathVariable("id") int id) {

        List<OfferModel> offers = this.offerService.getAllOffersByUser(id);

        if (offers.isEmpty()) {
            return AppResponse.error().withMessage("no id found").build();
        }

        return AppResponse.success().withData(offers).build();
    }


    @DeleteMapping("/offer-delete/{id}")
    public ResponseEntity<?> deleteOffer(@PathVariable int id) {

        OfferModel offer = this.offerService.getOfferByIdd(id);
        offer.setDeleted(true);
        offerService.updateOffer(id,offer);



        return AppResponse.success().withMessage("done").build();
    }


    @PutMapping("offer-edit/{id}")
    public ResponseEntity<?> updateOffer(@PathVariable("id") int id) {
        OfferModel offer = this.offerService.getOfferByIdd(id);
        offer.setAccepted(true);
        offerService.updateOffer(id,offer);

        return AppResponse.success().withMessage("done").build();

    }


}
