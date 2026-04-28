package com.dgnl.ex01.controller;

import com.dgnl.ex01.model.Artwork;
import com.dgnl.ex01.service.ArtworkService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
public class ArtworkController {
    private final ArtworkService service;
    @Autowired
    public ArtworkController(ArtworkService service) {
        this.service = service;
    }

    @GetMapping({"/", "/artworks"})
    public String list(@RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "size", defaultValue = "5") int size,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       Model model) {

        Pageable pageable = PageRequest.of(Math.max(0, page), size);
        Page<Artwork> pageData = service.listAll(keyword, pageable);

        model.addAttribute("artworks", pageData.getContent());
        model.addAttribute("pageData", pageData);
        model.addAttribute("currentPage", pageData.getNumber());
        model.addAttribute("totalPages", pageData.getTotalPages());
        model.addAttribute("keyword", keyword == null ? "" : keyword);

        return "artworks/list";
    }

    @GetMapping("/artworks/new")
    public String createForm(Model model) {
        if (!model.containsAttribute("artwork")) {
            Artwork artwork = new Artwork();
            artwork.setDate(LocalDate.now());
            model.addAttribute("artwork", artwork);
        }
        return "artworks/form";
    }

    @PostMapping("/artworks")
    public String create(@Valid @ModelAttribute("artwork") Artwork artwork,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.artwork", bindingResult);
            redirectAttributes.addFlashAttribute("artwork", artwork);
            return "redirect:/artworks/new";
        }

        service.save(artwork);
        return "redirect:/artworks";
    }

    @GetMapping("/artworks/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Artwork artwork = service.findById(id).orElse(null);
        if (artwork == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Artwork not found");
            return "redirect:/artworks";
        }
        if (!model.containsAttribute("artwork")) {
            model.addAttribute("artwork", artwork);
        }
        return "artworks/form";
    }

    @PostMapping("/artworks/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("artwork") Artwork artwork,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.artwork", bindingResult);
            redirectAttributes.addFlashAttribute("artwork", artwork);
            return "redirect:/artworks/" + id + "/edit";
        }

        artwork.setId(id);
        service.save(artwork);
        return "redirect:/artworks";
    }

    @PostMapping("/artworks/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        service.deleteById(id);
        return "redirect:/artworks";
    }

}
