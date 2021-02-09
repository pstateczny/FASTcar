package org.carRental.controller;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.carRental.dto.CarData;
import org.carRental.dto.UserContext;
import org.carRental.model.Konta;
import org.carRental.model.Pojazdy;
import org.carRental.model.Rents;
import org.carRental.service.AccountService;
import org.carRental.service.CarService;
import org.carRental.service.RentsService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final RentsService rentsService;
    private final UserContext userContext;
    private final AccountService accountService;




    @RequestMapping(value = "dodajPojazd", produces = "application/json",
            method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST})
    public String dodajPojazd(
            @Valid CarData carData,
            Model model) {

        if (carData.getBrand() != null) {
            carService.saveCar(new Pojazdy(carData.getBrand(), carData.getModel(), carData.getPower(), carData.getAvergeConsumption(), carData.getDailyAmount(), carData.getImageUrl()));
            return "redirect:/boss";
        }
        model.addAttribute("carData", new CarData());
        return "dodajPojazd";

    }

    @GetMapping(value = "edytujPojazd/{id}")
    public String edytujPojazd(@PathVariable("id") long id, Model model) {
        Pojazdy pojazd = carService.getOneCar(id);
        model.addAttribute("pojazd", pojazd);
        return "edytujPojazd";
    }


    @GetMapping(value = "usunPojazd/{id}")
    public String usunPojazd(@PathVariable("id") long id, Model model) {
        zrezygnuj(id);
        carService.deleteCarById(id);
        return "redirect:/boss";
    }



    @GetMapping(value = "pojazdy")
    public String pokazPojazdy(Model model) {
        List<Rents> rents = rentsService.getAllRents();
        model.addAttribute("pojazdy", carService.getAllCars());
        model.addAttribute("wynajetePojazdy", rents.stream().map(Rents::getPojazd).collect(Collectors.toList()));
        if (userContext.getUserId() != null) {
            Konta konto = accountService.getAccountByCredentials(userContext.getPoswiadczeniaUzytkownika());
            List<Pojazdy> pojazdyWynajetePrzezUzytkownika = new ArrayList<>();
            rents.forEach(rent -> {
                if (konto.getOsoba() == rent.getKlient()){
                    pojazdyWynajetePrzezUzytkownika.add(rent.getPojazd());
                }
            });
            model.addAttribute("pojazdyWynajetePrzezUzytkownika", pojazdyWynajetePrzezUzytkownika);

        }
        return "dostepnePojazdy";
    }


    @RequestMapping(value = "wyslijDoEdycji/{id}", produces = "application/json",
            method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST})
    public String wyslijDoEdycji(@PathVariable("id") long id,
                                 @Valid Pojazdy pojazd) {

        carService.updateCar(id, pojazd.getMarka(), pojazd.getModel(), pojazd.getMoc(), pojazd.getSrednieSpalanie(), pojazd.getCenaZaDobe(), pojazd.getMiniaturka());

        return "redirect:/boss";

    }


    @GetMapping(value = "/zrezygnuj/{id}")
    public String zrezygnuj(@PathVariable("id") long id) {
        Pojazdy pojazd = carService.getCarById(id).get();
        rentsService.deleteRentBasedOnCar(pojazd);

        return "redirect:/pojazdy";

    }

}
