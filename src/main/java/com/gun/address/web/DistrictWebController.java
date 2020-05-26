package com.gun.address.web;

import com.gun.address.dto.DistrictDto;
import com.gun.address.services.CityService;
import com.gun.address.services.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class DistrictWebController {
    private CityService cityService;
    private DistrictService districtService;

    @Autowired
    public DistrictWebController(CityService cityService, DistrictService districtService) {
        this.cityService = cityService;
        this.districtService = districtService;
    }

    @GetMapping
    @RequestMapping("/districts/{cityId}")
    public String getDistricts(@PathVariable("cityId") int cityId, Model model) {
        findAll(cityId, model);
        return "districts";
    }

    @GetMapping
    @RequestMapping("/districts/{cityId}/search")
    public String getDistricts(@PathVariable("cityId") int cityId,
                               @RequestParam("searchDistrictName") String searchDistrictName, Model model) {
        findAllByName(cityId, searchDistrictName, model);
        return "districts";
    }



    @GetMapping
    @RequestMapping("/district/{cityId}")
    public String newDistrict(@PathVariable("cityId") int cityId, Model model) {
        model.addAttribute("districtDto", new DistrictDto());
        model.addAttribute("action", String.format("/district/%d/save", cityId));
        model.addAttribute("cityId", cityId);
        return "district";
    }

    @GetMapping
    @RequestMapping("/district/{cityId}/{districtId}")
    public String editDistrict(@PathVariable("cityId") int cityId, @PathVariable("districtId") int districtId,
                              Model model) {
        model.addAttribute("districtDto", districtService.findByIdAndCityId(districtId, cityId));
        model.addAttribute("action", String.format("/district/%d/update", cityId));
        model.addAttribute("cityId", cityId);
        return "district";
    }

    @PostMapping
    @RequestMapping("/district/{cityId}/save")
    public String saveDistrict(@PathVariable("cityId") int cityId, @Valid @ModelAttribute DistrictDto districtDto, BindingResult bindingResult,
                               Model model) {
        return insertOrUpdate(cityId, districtDto, bindingResult, model, true);
    }


    @PostMapping
    @RequestMapping("/district/{cityId}/update")
    public String updateDistrict(@PathVariable("cityId") int cityId, @Valid @ModelAttribute DistrictDto districtDto, BindingResult bindingResult,
                                 Model model) {
        return insertOrUpdate(cityId, districtDto, bindingResult, model, false);
    }

    private String insertOrUpdate(int cityId, DistrictDto districtDto, BindingResult bindingResult, Model model,
                                  boolean isInsert) {
        if (bindingResult.hasErrors()) {
            return "district";
        }
        try {
            if (isInsert) {
                districtService.save(cityId, districtDto);
            } else {
                districtService.update(cityId, districtDto);
            }
        } catch (RuntimeException e) {
            model.addAttribute("errorData", e.getMessage());
            return "district";
        }


        findAll(cityId, model);
        model.addAttribute("cityId", cityId);
        return "redirect:/districts/{cityId}";
    }

    private void findAll(int cityId, Model model) {
        model.addAttribute("city", cityService.findById(cityId));
        model.addAttribute("districts", districtService.findByCityId(cityId));
    }

    private void findAllByName(int cityId, String searchDistrictName, Model model) {
        model.addAttribute("city", cityService.findById(cityId));
        model.addAttribute("districts", districtService.findByCityIdAndNameLike(cityId, searchDistrictName));
    }
}
