package com.example.library.controller;

import com.example.library.service.LibraryService;
import com.example.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@PreAuthorize("hasRole('ROLE_STAFF')")
@RequestMapping("/lib")
@Controller
public class LibraryController {
    private final UserService userService;
    private final LibraryService libraryService;

    @Autowired
    public LibraryController(UserService userService, LibraryService libraryService) {
        this.userService = userService;
        this.libraryService = libraryService;
    }

    @GetMapping("/users")
    public String viewUsers() {
        return "users";
    }

    @GetMapping("/add")
    public String addPage() {
        return "adder";
    }

    @PostMapping("/addBook")
    public String addBook(
            @RequestParam Map<String, String> bookData
    ) {
        libraryService.addBook(bookData);
        return "redirect:/books";
    }

    @PostMapping("/addAuthor")
    public String addAuthor(
            @RequestParam Map<String, String> authorData
    ) {
        libraryService.addAuthor(authorData);
        return "redirect:/authors";
    }
    // bvg1996@mail.ru
    @PostMapping("/addPublishment")
    public String addPublishment(
            @RequestParam Map<String, String> publishmentData
    ) {
        libraryService.addPublishment(publishmentData);
        return "redirect:/publishments";
    }

    @PostMapping("/addCountry")
    public String addCountry(
            @RequestParam Map<String, String> countryData
    ) {
        libraryService.addCountry(countryData);
        return "redirect:/countries";
    }
}
