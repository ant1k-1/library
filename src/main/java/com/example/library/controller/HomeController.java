package com.example.library.controller;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.Country;
import com.example.library.model.Publishment;
import com.example.library.service.LibraryService;
import com.example.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
public class HomeController {
    private final UserService userService;
    private final LibraryService libraryService;

    @Autowired
    public HomeController(UserService userService, LibraryService libraryService) {
        this.userService = userService;
        this.libraryService = libraryService;
    }

    @GetMapping("/search")
    public String search(
            @RequestParam("searchType") String searchType
    ) {
        return "redirect:/" + searchType;
    }

    @GetMapping("/home")
    public String redir() {
        return "redirect:/";
    }


    @GetMapping({"/{type}", "/"})
    public String welcome(
            @CurrentSecurityContext(expression = "authentication") Authentication auth,
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @PathVariable("type") Optional<String> type
    ) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);
        switch (type.orElse("books")) {
            case "books" -> {
                Page<Book> bookPage = libraryService.getAllPaginatedBooks(PageRequest.of(currentPage - 1, pageSize));
                model.addAttribute("bookPage", bookPage);
                int totalPages = bookPage.getTotalPages();
                if (totalPages > 0) {
                    List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                            .boxed()
                            .toList();
                    model.addAttribute("pageNumbers", pageNumbers);
                }
                model.addAttribute("searchType", "books");
            }
            case "authors" -> {
                Page<Author> authorPage = libraryService.getAllPaginatedAuthors(PageRequest.of(currentPage - 1, pageSize));
                model.addAttribute("authorPage", authorPage);
                int totalPages = authorPage.getTotalPages();
                if (totalPages > 0) {
                    List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                            .boxed()
                            .toList();
                    model.addAttribute("pageNumbers", pageNumbers);
                }
                model.addAttribute("searchType", "authors");
            }
            case "publishments" -> {
                Page<Publishment> publishmentPage = libraryService.getAllPaginatedPublishments(PageRequest.of(currentPage - 1, pageSize));
                model.addAttribute("publishmentPage", publishmentPage);
                int totalPages = publishmentPage.getTotalPages();
                if (totalPages > 0) {
                    List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                            .boxed()
                            .toList();
                    model.addAttribute("pageNumbers", pageNumbers);
                }
                model.addAttribute("searchType", "publishments");
            }
            case "countries" -> {
                Page<Country> countryPage = libraryService.getAllPaginatedCountries(PageRequest.of(currentPage - 1, pageSize));
                model.addAttribute("countryPage", countryPage);
                int totalPages = countryPage.getTotalPages();
                if (totalPages > 0) {
                    List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                            .boxed()
                            .toList();
                    model.addAttribute("pageNumbers", pageNumbers);
                }
                model.addAttribute("searchType", "countries");
            }
        }
        model.addAttribute("user", userService.makeDTO(auth.getName()));
        return "home";
    }

    @PreAuthorize("hasRole('ROLE_STAFF')")
    @GetMapping("/book/delete/{id}")
    public String deleteBook(@PathVariable String id) {
        libraryService.deleteBook(Long.parseLong(id));
        return "redirect:/books";
    }

    @PreAuthorize("hasRole('ROLE_STAFF')")
    @GetMapping("/author/delete/{id}")
    public String deleteAuthor(@PathVariable String id) {
        libraryService.deleteAuthor(Long.parseLong(id));
        return "redirect:/authors";
    }

    @PreAuthorize("hasRole('ROLE_STAFF')")
    @GetMapping("/publishment/delete/{id}")
    public String deletePublishment(@PathVariable String id) {
        libraryService.deletePublishment(Long.parseLong(id));
        return "redirect:/publishments";
    }

    @PreAuthorize("hasRole('ROLE_STAFF')")
    @GetMapping("/country/delete/{id}")
    public String deleteCountry(@PathVariable String id) {
        libraryService.deleteCountry(Long.parseLong(id));
        return "redirect:/countries";
    }

    @GetMapping("/book/take/{id}")
    public String takeBook(
            @CurrentSecurityContext(expression = "authentication") Authentication auth,
            @PathVariable String id) {
        libraryService.takeBook(Long.parseLong(id), auth.getName());
        return "redirect:/booklogs";
    }
}
