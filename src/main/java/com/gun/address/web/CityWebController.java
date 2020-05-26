package com.gun.address.web;

import com.gun.address.dto.CityDto;
import com.gun.address.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class CityWebController {

    private final String CITIES_PATH = "cities";
    private final String CITY_PATH = "city";

    private CityService cityService;

    @Autowired
    public CityWebController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    @RequestMapping("/cities")
    public String getCities(Model model) {
        findAll(model);
        return CITIES_PATH;
    }

    @GetMapping
    @RequestMapping("/cities/search")
    public String search(Model model, @RequestParam("searchCityName") String searchCityName) {
        findAllByNameLike(model, searchCityName);
        return CITIES_PATH;
    }

    @GetMapping
    @RequestMapping("/city")
    public String addOrUpdateCity(Model model) {
        model.addAttribute("cityDto", new CityDto());
        model.addAttribute("action", "/addCity");
        return CITY_PATH;
    }

    @GetMapping
    @RequestMapping("/editCity/{cityId}")
    public String addOrUpdateCity(@PathVariable("cityId") int cityId, Model model) {
        CityDto cityDto = cityService.findById(cityId);
        model.addAttribute("cityDto", cityDto);
        model.addAttribute("action", "/updateCity");
        return CITY_PATH;
    }

    @PostMapping
    @RequestMapping("/addCity")
    public String addCity(@Valid CityDto cityDto, BindingResult result, Model model) {
        return saveOrUpdate(cityDto, result, model, true);
    }


    @PostMapping
    @RequestMapping("/updateCity")
    public String updateCity(@Valid CityDto cityDto, BindingResult result, Model model) {
        return saveOrUpdate(cityDto, result, model, false);
    }

    private String saveOrUpdate(CityDto cityDto, BindingResult result, Model model, boolean isInsert) {
        if (result.hasErrors()) {
            return CITY_PATH;
        }

        try {
            if (isInsert) {
                cityService.save(cityDto);
            } else {
                cityService.update(cityDto);
            }
        } catch (RuntimeException e) {
            model.addAttribute("errorData", e.getMessage());
            return CITY_PATH;
        }
        findAll(model);
        return "redirect:" + CITIES_PATH;
    }



    private void findAll(Model model) {
        model.addAttribute("cities", cityService.findAll());
    }

    private void findAllByNameLike(Model model, String name) {
        model.addAttribute("cities", cityService.findAllByNameLike(name));
    }
}
