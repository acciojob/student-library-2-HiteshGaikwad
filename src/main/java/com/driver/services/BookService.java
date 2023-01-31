package com.driver.services;

import com.driver.models.Author;
import com.driver.models.Book;
import com.driver.repositories.AuthorRepository;
import com.driver.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {


    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;


    public void createBook(Book book){
        try
        {
            int authorId = book.getAuthor().getId();
            Author author = authorRepository.findById(authorId).get();
            List<Book> bookList = author.getBooksWritten();
            if(bookList==null) {
                bookList = new ArrayList<>();
            }
            bookList.add(book);
            book.setAuthor(author);
            author.setBooksWritten(bookList);
            authorRepository.save(author);
        }
        catch(Exception e) {
            bookRepository.save(book);
        }
    }

    public List<Book> getBooks(String genre, boolean available, String author){
        List<Book> books;

        if(genre != null && author != null){
            books = bookRepository.findBooksByGenreAuthor(genre, author, available);
        }else if(genre != null){
            books = bookRepository.findBooksByGenre(genre, available);
        }else if(author != null){
            books = bookRepository.findBooksByAuthor(author, available);
        }else{
            books = bookRepository.findByAvailability(available);
        }
        return books;
    }

}
