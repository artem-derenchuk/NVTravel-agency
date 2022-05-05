package com.aderenchuk.enter.controller;


import com.aderenchuk.enter.entity.Client;
import com.aderenchuk.enter.repo.ClientDaoJPA;
import com.aderenchuk.enter.repo.TourDaoJPA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;
import javax.validation.Valid;

@Controller
public class ClientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private final ClientDaoJPA clientDaoJPA;

    @Autowired
    private final TourDaoJPA tourDaoJPA;

    public ClientController(ClientDaoJPA clientDaoJPA, TourDaoJPA tourDaoJPA) {
        this.clientDaoJPA = clientDaoJPA;
        this.tourDaoJPA = tourDaoJPA;
    }

    /**
     * Goto client page.
     *
     * @return view name
     */
    @GetMapping(value = "/clients")
    public final String clients(Model model) {
        LOGGER.debug("clients()");
        model.addAttribute("clients", clientDaoJPA.findAll());
        return "clients";
    }

    /**
     * Goto edit client page.
     *
     * @return view name
     */
    @GetMapping(value = "/client/{id}")
    public final String gotoEditClientPage(@PathVariable Integer id, Model model) {
        LOGGER.debug("gotoEditClientPage({}, {})", id, model);
        Optional<Client> optionalClient = clientDaoJPA.findById(id);
        if (optionalClient.isPresent()) {
            model.addAttribute("isNew", false);
            model.addAttribute("client", optionalClient.get());
            model.addAttribute("tours", tourDaoJPA.findAll());
            return "client";
        } else  {
            return "redirect:clients";
        }
    }

    /**
     * Update client.
     *
     * @param client client with filled data.
     * @param result binding result
     * @return       view name
     */
    @PostMapping(value = "/client/{id}")
    public String updateClient(@Valid Client client, BindingResult result) {
        LOGGER.debug("updateClient({}, {})", client, result);
        if (result.hasErrors()) {
            return "tour";
        } else  {
            this.clientDaoJPA.save(client);
            return "redirect:/clients";
        }
    }

    /**
     * Goto add client page.
     *
     * @return view name
     */
    @GetMapping(value = "/client")
    public final String gotoAddClientPage(Model model) {
        LOGGER.debug("gotoAddClientPage({})", model);
        model.addAttribute("isNew", true);
        model.addAttribute("client", new Client(1));
        model.addAttribute("tours", tourDaoJPA.findAll());
        return "client";
    }

    /**
     * Persist new client into persistence storage.
     *
     * @param client new client with filled data.
     * @param result binding result.
     * @return view name
     */
    @PostMapping(value = "/client")
    public String addClient(@Valid Client client, BindingResult result) {
        LOGGER.debug("addClient({}, {})", client, result);
        if(result.hasErrors()) {
            return "client";
        } else {
            this.clientDaoJPA.save(client);
            return "redirect:/clients";
        }
    }

    /**
     * Delete client.
     *
     * @return view name
     */
    @GetMapping(value = "/client/{clientId}/delete")
    public final String deleteClientById(@Valid Client client, Model model) {
        LOGGER.debug("delete({}, {})", client, model);
        clientDaoJPA.delete(client);
        return "redirect:/clients";
    }
}
