package org.carRental;

import org.carRental.dto.CarData;
import org.carRental.dto.TransferData;
import org.carRental.dto.UserContext;
import org.carRental.dto.UserData;
import org.carRental.model.Konta;
import org.carRental.model.Osoby;
import org.carRental.model.Pojazdy;
import org.carRental.model.Rents;
import org.carRental.repository.KontaRepository;
import org.carRental.repository.PojazdRepository;
import org.carRental.repository.RentsRepository;
import org.carRental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Scope("prototype")
@org.springframework.stereotype.Controller
public class Controller {


    private PojazdRepository pojazdRepository;
    private KontaRepository kontaRepository;
    private RentsRepository rentsRepository;
    private UserRepository userRepository;
    private UserContext userContext;
    private TransferData transferData;

    @Autowired
    public Controller(PojazdRepository pojazdRepository, UserContext userContext, KontaRepository kontaRepository, TransferData transferData, RentsRepository rentsRepository, UserRepository userRepository) {
        this.pojazdRepository = pojazdRepository;
        this.userContext = userContext;
        this.kontaRepository = kontaRepository;
        this.transferData = transferData;
        this.rentsRepository = rentsRepository;
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/")
    public String start(Model model) {
        model.addAttribute("userContext", userContext);
        String role = "";
        if (userContext.getUserId() != null) {
            Osoby osoba = userRepository.getOne(userContext.getUserId());
            role = kontaRepository.findByUser(osoba).getRola();
        }
        model.addAttribute("rola", role);


        return "index";
    }

    @RequestMapping(value = "dodajPojazd", produces = "application/json",
            method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST})
    public String dodajPojazd(
            @Valid CarData carData,
            Model model) {

        if (carData.getBrand() != null) {
            pojazdRepository.save(new Pojazdy(carData.getBrand(), carData.getModel(), carData.getPower(), carData.getAvergeConsumption(), carData.getDailyAmount(), carData.getImageUrl()));
            return "redirect:/boss";
        }
        model.addAttribute("carData", new CarData());
        return "dodajPojazd";

    }

    @RequestMapping(value = "wyslijDoEdycji/{id}", produces = "application/json",
            method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST})
    public String wyslijDoEdycji(@PathVariable("id") long id,
                              @Valid Pojazdy pojazd) {

        pojazdRepository.updateCar(id, pojazd.getMarka(), pojazd.getModel(), pojazd.getMoc(), pojazd.getSrednieSpalanie(), pojazd.getCenaZaDobe(), pojazd.getMiniaturka());

        return "redirect:/boss";

    }


    @GetMapping(value = "edytujPojazd/{id}")
    public String edytujPojazd(@PathVariable("id") long id, Model model) {
        Pojazdy pojazd = pojazdRepository.getOne(id);
        model.addAttribute("pojazd", pojazd);
        return "edytujPojazd";
    }


    @GetMapping(value = "usunPojazd/{id}")
    public String usunPojazd(@PathVariable("id") long id, Model model) {
        zrezygnuj(id);
        pojazdRepository.deleteById(id);
        return "redirect:/boss";
    }

    @GetMapping(value = "/wyloguj")
    public String wyloguj() {
        userContext.setUserId(null);
        userContext.setPoswiadczeniaUzytkownika("");
        return "redirect:/";

    }

    @GetMapping(value = "/zrezygnuj/{id}")
    public String zrezygnuj(@PathVariable("id") long id) {
        Pojazdy pojazd = pojazdRepository.findById(id).get();
        rentsRepository.deleteRentBasedOnCar(pojazd);

        return "redirect:/pojazdy";

    }



    @GetMapping(value = "/boss")
    public String panelSzefa(Model model) {
        if (userContext.getPoswiadczeniaUzytkownika().isEmpty()) {
            return "redirect:/";
        }
        model.addAttribute("pojazdy", pojazdRepository.findAll());
        return "carDetails";

    }

    @GetMapping(value = "pojazdy")
    public String pokazPojazdy(Model model) {
        List<Rents> rents = rentsRepository.findAll();
        model.addAttribute("pojazdy", pojazdRepository.findAll());
        model.addAttribute("wynajetePojazdy", rents.stream().map(Rents::getPojazd).collect(Collectors.toList()));
        if (userContext.getUserId() != null) {
            Konta konto = kontaRepository.findByCredentials(userContext.getPoswiadczeniaUzytkownika());
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

    @GetMapping(value = "wynajmij/{id}")
    public String wynajmijPojazd(@PathVariable("id") long id, Model model) {
        model.addAttribute("userData", new UserData());
        transferData.setIdPojazdu(id);
        if (userContext.getPoswiadczeniaUzytkownika().isEmpty()) {
            return "zaloguj";
        }
        model.addAttribute("transferData", transferData);
        model.addAttribute("pojazdDoWynajecia", pojazdRepository.getOne(transferData.getIdPojazdu()));
        return "formularzNajmow";
    }

    @RequestMapping(value = "zaloguj", produces = "application/json",
            method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST})
    public String zaloguj(
            @Valid UserData userData,
            Model model) {


        String encodedUserDetails = AppUtils.encodeToBase64(userData);

        Konta konto = kontaRepository.findByCredentials(encodedUserDetails);
        if (konto==null) {
            return "zaloguj";
        } else {
            userContext.setPoswiadczeniaUzytkownika(konto.getDaneLogowania());
            userContext.setUserId(konto.getOsoba().getId());
        }
        Pojazdy one = pojazdRepository.getOne(transferData.getIdPojazdu());
        model.addAttribute("pojazdDoWynajecia", one);
        transferData.setIdPojazdu(one.getId());
        model.addAttribute("transferData", transferData);
        return "redirect:/";
    }


    @RequestMapping(value = "zarejestruj", produces = "application/json",
            method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST})
    public String zarejestruj(
            @Valid UserData userData,
            Model model) {
        if (userData.getLogin() != null) {
            userRepository.save(new Osoby(userData.getImie(), userData.getNazwisko(), userData.getNumerDowodu(), userData.getNumerTelefonu()));
            Osoby byNumerDowodu = userRepository.findByNumerDowodu(userData.getNumerDowodu());
            kontaRepository.save(new Konta(byNumerDowodu, AppUtils.encodeToBase64(userData), "CLIENT"));
            return "redirect:/";
        }
        model.addAttribute("userData", new UserData());

        return "zarejestruj";
    }

    @RequestMapping(value = "potwierdz/{id}", produces = "application/json",
            method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST})
    public String potwierdz(
            @PathVariable("id") long idPojazdu,
            @Valid TransferData transferData,
            Model model) {

        model.addAttribute("pojazdy", pojazdRepository.findAll());
        model.addAttribute("errorMessage", "Login failed");
        Osoby customer = userRepository.getOne(userContext.getUserId());
        Pojazdy pojazd = pojazdRepository.getOne(idPojazdu);
        rentsRepository.save(new Rents(pojazd, customer, LocalDate.now().plusDays(transferData.getIloscDni())));
        return "redirect:/pojazdy";
    }

    @GetMapping("/contact")
    public String contactPage() {
        return "contact";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }
}
