package org.example.bt_nangcapungdungquanlycauthubongda_springdatarepository.controller;


import org.example.bt_nangcapungdungquanlycauthubongda_springdatarepository.model.Player;
import org.example.bt_nangcapungdungquanlycauthubongda_springdatarepository.service.IPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.beans.PropertyEditorSupport;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

@Controller
@RequestMapping("/player")
public class PlayerController {
    @Autowired
    private IPlayerService playerService;

    @GetMapping("")
    public String showList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Date dobMin,
            @RequestParam(required = false) Date dobMax,
            Model model
            ) {
//        Page<Player> players = playerService.findAll(page, size, sort, name, dobMin,dobMax);
        Page<Player> players = playerService.getPlayer(page, size, name, dobMin, dobMax);
        model.addAttribute("players", players);
        model.addAttribute("page", page);
        model.addAttribute("size",size);
        model.addAttribute("name",name);
        model.addAttribute("dobMin",dobMin);
        model.addAttribute("dobMax",dobMax);
        return "/views/list";
    }


    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        Optional<Player> player = playerService.findById(id);
        if (player.isPresent()) {
            model.addAttribute("player", player.get());
            return "/views/detail";
        }
        return "redirect:/player";
    }

    @GetMapping("/create")
    public String showFormCreate(Model model) {
        model.addAttribute("player", new Player());
        return "/views/create";
    }

    @PostMapping("/create")
    public String save(@ModelAttribute("player") Player player, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasFieldErrors()) {
            return "/views/create";
        }
        redirectAttributes.addFlashAttribute("message", "Thêm thành công");
        playerService.save(player);
        return "redirect:/player";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Optional<Player> player = playerService.findById(id);
        if (player.isPresent()) {
            model.addAttribute("player", player.get());
            return "/views/edit";
        }
        return "redirect:/list";
    }

    @PostMapping("/edit")
    public String saveEdit(@ModelAttribute Player player) {
        playerService.save(player);
        return "redirect:/player";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id")Long id){
        playerService.remove(id);
        return "redirect:/player";
    }
    @GetMapping("/search")
    public String search(@RequestParam("search") String search,@PageableDefault(value = 6) Pageable pageable, Model model){
        if (search.isEmpty()){
            return "redirect:/player";
        }
       Iterable<Player> players = playerService.findAllByNameContaining(search,pageable);
        model.addAttribute("players",players);
        return "/views/list";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(java.sql.Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                try {
                    setValue(new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(text).getTime()));
                } catch (ParseException e) {
                    setValue(null);
                }
            }
        });
    }
}


