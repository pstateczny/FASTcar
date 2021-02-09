package org.carRental.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.carRental.dto.TransferData;
import org.carRental.dto.UserContext;
import org.carRental.dto.UserData;
import org.carRental.model.Osoby;
import org.carRental.model.Pojazdy;
import org.carRental.model.Rents;
import org.carRental.service.CarService;
import org.carRental.service.RentsService;
import org.carRental.service.UserService;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class RentController {

    private final CarService carService;
    private final UserService userService;
    private final RentsService rentsService;
    private final TransferData transferData;
    private final UserContext userContext;


    @GetMapping(value = "wynajmij/{id}")
    public String wynajmijPojazd(@PathVariable("id") long id, Model model) {
        model.addAttribute("userData", new UserData());
        transferData.setIdPojazdu(id);
        if (userContext.getPoswiadczeniaUzytkownika().isEmpty()) {
            return "zaloguj";
        }
        model.addAttribute("transferData", transferData);
        model.addAttribute("pojazdDoWynajecia", carService.getOneCar(transferData.getIdPojazdu()));
        return "formularzNajmow";
    }


    @RequestMapping(value = "potwierdz/{id}", produces = "application/json",
            method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST})
    public String potwierdz(
            @PathVariable("id") long idPojazdu,
            @Valid TransferData transferData,
            Model model) {

        model.addAttribute("pojazdy", carService.getAllCars());
        model.addAttribute("errorMessage", "Login failed");
        Osoby customer = userService.getOneUser(userContext.getUserId());
        Pojazdy pojazd = carService.getOnePojazd(idPojazdu);
        rentsService.saveRent(new Rents(pojazd, customer, LocalDate.now().plusDays(transferData.getIloscDni())));
        return "redirect:/pojazdy";
    }
}
