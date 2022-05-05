package com.aderenchuk.enter.controller;

import com.aderenchuk.enter.entity.Tour;
import com.aderenchuk.enter.entity.dto.TourDto;
import com.aderenchuk.enter.repo.TourDaoJPA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

@Controller
@RequestMapping("/tours")
public class TourController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TourController.class);

//    @Autowired
//    private final TourDtoService tourDtoService;

    public TourController(TourDaoJPA tourDaoJPA) {
        this.tourDaoJPA = tourDaoJPA;
    }

    @Autowired
    private final TourDaoJPA tourDaoJPA;



    /**
     * Goto tours list page.
     *
     * @return view name
     */
    @GetMapping
    public final String tours(@RequestParam(value = "dateFrom", required = false)
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                              @RequestParam(value = "dateTo", required = false)
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
                              Model model) {

//        if (dateFrom != null && dateTo != null) {
//            LOGGER.debug("Find tours between dates. dateFrom = {}, dateTo = {}", dateFrom, dateTo);
//            List<TourDto> tourDtoList = tourDaoJPA.findAllQuantityClientsAndDateFilter(dateFrom, dateTo);
//            model.addAttribute("listTour", tourDtoList);
//        } else  {
            LOGGER.debug("Find all with quantity clients");
            List<TourDto> tourDtoList = tourDaoJPA.findAllQuantityClients();
            model.addAttribute("listTour", tourDtoList);
//        }
//        List<Tour> tourList = tourDaoJPA.findAll();
//        model.addAttribute("listTour", tourList);
        return "tours";
    }

//    @GetMapping
//    public final String tours(Model model) {
//        LOGGER.debug("gotoTourPage({},{})", model);
//        List<Tour> tourList = tourDaoJPA.findAll();
//        model.addAttribute("tours", tourList);
//            return "tours";
//    }
    /**
     * Goto edit tour page.
     *
     * @return view name
     */
    @GetMapping(value = "/{tourId}")
    public final String gotoEditTourPage(@PathVariable Integer tourId, Model model) {
        LOGGER.debug("gotoEditTourPage({},{})", tourId, model);
        Optional<Tour> optionalTour = tourDaoJPA.findById(tourId);
        if (optionalTour.isPresent()) {
            model.addAttribute("isNew", false);
            model.addAttribute("tour", optionalTour.get());
            return "tour";
        } else {
            return "redirect:tours";
        }
    }

    /**
     * Update tour.
     *
     * @param tour tour with filled data.
     * @return view name
     */
    @PostMapping(value = "/{id}")
    public String updateTour(@ModelAttribute("tour")@Valid Tour tour, BindingResult result, Model model) {
        LOGGER.debug("updateTour({}, {})", tour, result);
        model.addAttribute("tourEntity", tour);
        if(result.hasErrors()) {
            return "tour";
        } else {
            tourDaoJPA.save(tour);
            return "redirect:/tours";
        }
    }

    /**
     * Goto new tour page.
     *
     * @return view name
     */
    @GetMapping(value = "/add")
    public final String gotoAddTourPage(Model model) {
        LOGGER.debug("gotoAddTourPage({})", model);
        model.addAttribute("isNew", true);
        model.addAttribute("tour", new Tour());
        return "tour";
    }

    /**
     * Persist new tour into persistence storage.
     *
     * @param tour new tour with filled data.
     * @return view name
     */
    @PostMapping(value = "/add")
    public String addTour(@Valid Tour tour, BindingResult result) {
        LOGGER.debug("addTour({}, {})", tour, result);
        if (result.hasErrors()) {
            return "tour";
        } else {
            this.tourDaoJPA.save(tour);
            return "redirect:/tours";
        }

    }

    /**
     * Delete tour.
     *
     * @return view name
     */
    @GetMapping(value = "/{tourId}/delete")
    public String deleteTour(@Valid Tour tour, Model model){
        LOGGER.debug("deleteTour({}, {})", tour, model);
        tourDaoJPA.delete(tour);
        return "redirect:/tours";
    }
}
