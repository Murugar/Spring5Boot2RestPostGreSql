package com.iqmsoft.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.iqmsoft.domain.Book;
import com.iqmsoft.domain.BookRepository;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private BookRepository repository;

    @Inject
    public void setRepository(BookRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(
            method = RequestMethod.POST)
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        return new ResponseEntity<>(repository.save(book), HttpStatus.CREATED);
    }

    @RequestMapping(
            method = RequestMethod.GET)
    public ResponseEntity<Collection<Book>> getAllBooks() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET)
    public ResponseEntity<Book> getBookWithId(@PathVariable Long id) {
    	
    	Optional<Book> b = repository.findById(id);
    	
        return new ResponseEntity<>(b.get(), HttpStatus.OK);
    }

    @RequestMapping(
            params = {"name"},
            method = RequestMethod.GET)
    public ResponseEntity<Collection<Book>> findBookWithName(@RequestParam(value = "name") String name) {
        return new ResponseEntity<>(repository.findByName(name), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT)
    public ResponseEntity<Book> updateBookFromDB(@PathVariable("id") long id, @RequestBody Book book) {

        Optional<Book> currentBook = repository.findById(id);
        currentBook.get().setName(book.getName());
        currentBook.get().setDescription(book.getDescription());
        currentBook.get().setTags(book.getTags());

        return new ResponseEntity<>(repository.save(currentBook.get()), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE)
    public void deleteBookWithId(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @RequestMapping(
            method = RequestMethod.DELETE)
    public void deleteAllBooks() {
        repository.deleteAll();
    }
}
