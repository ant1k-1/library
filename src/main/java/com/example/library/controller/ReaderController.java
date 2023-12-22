package com.example.library.controller;

import com.example.library.enums.Role;
import com.example.library.model.Book;
import com.example.library.model.BookLog;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
public class ReaderController {
    private final UserService userService;
    private final LibraryService libraryService;

    @Autowired
    public ReaderController(UserService userService, LibraryService libraryService) {
        this.userService = userService;
        this.libraryService = libraryService;
    }

    @GetMapping("/booklogs")
    public String bookLogsView(
            @CurrentSecurityContext(expression = "authentication") Authentication auth,
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size
    ) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);
        Page<BookLog> bookLogPage;
        if (auth.getAuthorities().contains(Role.ROLE_CUSTOMER)) {
            bookLogPage = libraryService.getAllPaginatedBookLogs(PageRequest.of(currentPage - 1, pageSize), auth.getName());
        } else {
            bookLogPage = libraryService.getAllPaginatedBookLogs(PageRequest.of(currentPage - 1, pageSize), "");
        }

        model.addAttribute("bookLogPage", bookLogPage);
        int totalPages = bookLogPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "books";
    }

    @PreAuthorize("hasRole('ROLE_STAFF')")
    @GetMapping("/book/back/{id}")
    public String takeback(@PathVariable String id) {
        libraryService.takeback(Long.parseLong(id));
        return "redirect:/booklogs";
    }

    @PreAuthorize("hasRole('ROLE_STAFF')")
    @GetMapping("/book/give/{id}")
    public String giveBook(@PathVariable String id) {
        libraryService.giveBook(Long.parseLong(id));
        return "redirect:/booklogs";
    }
}
