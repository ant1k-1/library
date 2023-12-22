package com.example.library.service;

import com.example.library.model.*;
import com.example.library.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class LibraryService {
    private AuthorRepository authorRepository;
    private BookLogRepository bookLogRepository;
    private BookRepository bookRepository;
    private CountryRepository countryRepository;
    private CustomerRepository customerRepository;
    private GenreRepository genreRepository;
    private LibStaffRepository libStaffRepository;
    private PublishmentRepository publishmentRepository;
    private UserRepository userRepository;

    public LibraryService(AuthorRepository authorRepository,
                          BookLogRepository bookLogRepository,
                          BookRepository bookRepository,
                          CountryRepository countryRepository,
                          CustomerRepository customerRepository,
                          GenreRepository genreRepository,
                          LibStaffRepository libStaffRepository,
                          PublishmentRepository publishmentRepository,
                          UserRepository userRepository
    ) {
        this.authorRepository = authorRepository;
        this.bookLogRepository = bookLogRepository;
        this.bookRepository = bookRepository;
        this.countryRepository = countryRepository;
        this.customerRepository = customerRepository;
        this.genreRepository = genreRepository;
        this.libStaffRepository = libStaffRepository;
        this.publishmentRepository = publishmentRepository;
        this.userRepository = userRepository;
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public void deleteAuthor(Long id) {
        bookRepository.deleteAll(bookRepository.findAllByAuthorId(id));
        authorRepository.deleteById(id);
    }

    public void deletePublishment(Long id) {
        bookRepository.deleteAll(bookRepository.findAllByPublishmentId(id));
        publishmentRepository.deleteById(id);
    }

    public void deleteCountry(Long id) {
        return;
    }

    public void takeBook(Long id, String username){
        User user = userRepository.findByLogin(username).get();
        Book book = bookRepository.findById(id).get();
        if (book.getAmount()==0) {
            return;
        }
        book.setAmount(book.getAmount() - 1);
        bookRepository.save(book);
        BookLog bookLog = new BookLog();
        bookLog.setStatus(0);
        bookLog.setBookId(id);
        bookLog.setCardId(user.getId());
        bookLog.setLibstaffId(-1L);
        bookLog.setDateFrom(Date.valueOf(LocalDate.now()));
        bookLog.setDateUntil(Date.valueOf(LocalDate.now().plusMonths(6)));
        bookLogRepository.save(bookLog);
    }

    public void addBook(Map<String, String> form){
        Author author; Country country;  Publishment publishment;
        if (form.get("authorId").isEmpty()) {
            author = new Author();
            author.setBookAmount(1);
            author.setPseudonym(form.get("authorPseudonym"));
            author.setFirstName(form.get("authorFirstName"));
            author.setLastName(form.get("authorLastName"));
            author.setPatronymic(form.get("authorPatronymic"));
            author = authorRepository.save(author);
        } else if (!authorRepository.existsById(Long.parseLong(form.get("authorId")))) {
            return;
        }
        if (form.get("countryID").isEmpty()) {
             country = new Country();
            country.setName("countryName");
            country = countryRepository.save(country);
        } else if (!countryRepository.existsById(Long.parseLong(form.get("countryID")))) {
            return;
        }
        if (form.get("publishmentId").isEmpty()) {
             publishment = new Publishment();
            publishment.setName(form.get("publishmentName1"));
            publishment.setBookAmount(1);
            publishment = publishmentRepository.save(publishment);
        } else if (!publishmentRepository.existsById(Long.parseLong(form.get("publishmentId")))) {
            return;
        }
        author = authorRepository.findById(Long.parseLong(form.get("authorId"))).get();
        author.setBookAmount(author.getBookAmount() + 1);
        authorRepository.save(author);
        publishment = publishmentRepository.findById(Long.parseLong(form.get("publishmentId"))).get();
        publishment.setBookAmount(publishment.getBookAmount()+1);
        publishmentRepository.save(publishment);
        Book book = new Book();
        book.setTitle(form.get("bookTitle"));
        book.setYear(Integer.parseInt(form.get("bookYear")));
        book.setAmount(Integer.parseInt(form.get("bookAmount")));
        book.setAuthorId(Long.parseLong(form.get("authorId")));
        book.setPublishmentId(Long.parseLong(form.get("publishmentId")));
        bookRepository.save(book);
        System.out.println(form);
    }

    public void addAuthor(Map<String, String> form){
        if (form.values().stream().anyMatch(String::isEmpty)) {
            return;
        } else {
            Author author = new Author();
            author.setBookAmount(0);
            author.setPseudonym(form.get("authorPseudonym1"));
            author.setFirstName(form.get("authorFirstName1"));
            author.setLastName(form.get("authorLastName1"));
            author.setPatronymic(form.get("authorPatronymic1"));
            authorRepository.save(author);
        }
    }
    public void addPublishment(Map<String, String> form){
        if (form.values().stream().anyMatch(String::isEmpty)) {
            return;
        } else {
            Publishment publishment = new Publishment();
            publishment.setName(form.get("publishmentName1"));
            publishment.setBookAmount(0);
            publishmentRepository.save(publishment);
        }
        System.out.println(form);
    }
    public void addCountry(Map<String, String> form){
        if (form.values().stream().anyMatch(String::isEmpty)) {
            return;
        } else {
            Country country = new Country();
            country.setName(form.get("countryName1"));
            countryRepository.save(country);
        }
        System.out.println(form);
    }

    public Page<BookLog> getAllPaginatedBookLogs(Pageable pageable, String username) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<BookLog> bookLogs;

        if (username.isEmpty()) {
             bookLogs = bookLogRepository.findAll()
                    .stream().sorted(Comparator.comparingLong(BookLog::getStatus)).toList();
        } else {
            User user = userRepository.findByLogin(username).get();
            bookLogs = bookLogRepository.findAllByCardId(user.getId())
                    .stream().sorted(Comparator.comparingLong(BookLog::getStatus)).toList();
        }

        List<BookLog> list;
        if (bookLogs.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, bookLogs.size());
            list = bookLogs.subList(startItem, toIndex);
        }
        return new PageImpl<BookLog>(list, PageRequest.of(currentPage, pageSize), bookLogs.size());
    }


    public Page<Book> getAllPaginatedBooks(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Book> books = bookRepository.findAll()
                .stream().sorted(Comparator.comparingLong(Book::getId)).toList();
        List<Book> list;
        if (books.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, books.size());
            list = books.subList(startItem, toIndex);
        }
        return new PageImpl<Book>(list, PageRequest.of(currentPage, pageSize), books.size());
    }

    public Page<Author> getAllPaginatedAuthors(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Author> authors;

        authors = authorRepository.findAll()
                .stream().sorted(Comparator.comparingLong(Author::getId)).toList();

        List<Author> list;
        if (authors.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, authors.size());
            list = authors.subList(startItem, toIndex);
        }
        return new PageImpl<Author>(list, PageRequest.of(currentPage, pageSize), authors.size());
    }

    public Page<Publishment> getAllPaginatedPublishments(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Publishment> publishments = publishmentRepository.findAll()
                .stream().sorted(Comparator.comparingLong(Publishment::getId)).toList();
        List<Publishment> list;
        if (publishments.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, publishments.size());
            list = publishments.subList(startItem, toIndex);
        }
        return new PageImpl<Publishment>(list, PageRequest.of(currentPage, pageSize), publishments.size());
    }

    public Page<Country> getAllPaginatedCountries(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Country> countries = countryRepository.findAll()
                .stream().sorted(Comparator.comparingLong(Country::getId)).toList();
        List<Country> list;
        if (countries.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, countries.size());
            list = countries.subList(startItem, toIndex);
        }
        return new PageImpl<Country>(list, PageRequest.of(currentPage, pageSize), countries.size());
    }

    public void takeback(Long id){
        BookLog bookLog = bookLogRepository.findById(id).get();
        bookLog.setStatus(2);
        Book book = bookRepository.findById(bookLog.getBookId()).get();
        book.setAmount(book.getAmount() + 1);
        bookRepository.save(book);
        bookLogRepository.save(bookLog);
    }

    public void giveBook(Long id){
        BookLog bookLog = bookLogRepository.findById(id).get();
        bookLog.setStatus(1);
        bookLogRepository.save(bookLog);
    }
}
