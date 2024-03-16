package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;


@Controller
public class BidListController {
    @Autowired
    private BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("username", username);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
            String oauthUsername = oauth2User.getAttribute("login"); // "login" est l'attribut qui contient le nom d'utilisateur OAuth2
            model.addAttribute("oauthUsername", oauthUsername);
        }

        model.addAttribute("bidLists", bidListService.findAll());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(Model model)
    {
        model.addAttribute("bidList", new BidList());
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidlist, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/add";
        }

        bidListService.save(bidlist);
        model.addAttribute("bidList",bidListService.findAll());
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidList bidList = bidListService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/update";
        }
        bidListService.save(bidList);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        BidList bidListToDelete = bidListService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
        bidListService.deleteById(id);
        return "redirect:/bidList/list";
    }
}