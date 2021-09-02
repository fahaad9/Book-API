package com.example.book.controller;


import com.example.book.exception.ResourceNotFoundException;
import com.example.book.model.Book;
import com.example.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    //get all books
    @GetMapping("/books")
    public List<Book> getAllBooks(){
        return  bookRepository.findAll();
    }

    //create a book
    @PostMapping("/books")
    public Book addBook(@RequestBody Book book){
        return bookRepository.save(book);
    }

    //get book by ID
    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable(value = "id") long bookId ) throws ResourceNotFoundException{
       Book book = bookRepository.findById(bookId)
               .orElseThrow(() -> new ResourceNotFoundException("Book not found :: " + bookId));
        return ResponseEntity.ok().body(book);
    }

    //update book
    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable(value = "id")long bookId , @RequestBody Book bookDetails) throws ResourceNotFoundException{
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found :: " + bookId));
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        final Book updatedBook = bookRepository.save(book);
        return ResponseEntity.ok(updatedBook);
    }

    //delete book
    @DeleteMapping("/book/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable(value = "id")long bookId) throws ResourceNotFoundException {
        bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found :: " + bookId));
        bookRepository.deleteById(bookId);
        return ResponseEntity.ok().body(bookId);
    }

}
