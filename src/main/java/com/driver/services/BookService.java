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


    public void createBook(Book book){
        bookRepository.save(book);

    }

    public List<Book> getBooks(String genre, boolean available, String author){

        //find the elements of the list by yourself
        if(genre!=null && author!=null){
            List<Book> bookList= bookRepository.findBooksByGenreAuthor(genre,author,available);
            if(bookList==null){
                bookList=new ArrayList<>();
            }
            return bookList;
        } else if(genre!=null){
            List<Book> bookList= bookRepository.findBooksByGenre(genre,available);
            if(bookList==null){
                bookList=new ArrayList<>();
            }
            return bookList;
        }
        else if(author!=null){
            List<Book> bookList=  bookRepository.findBooksByAuthor(author,available);
            if(bookList==null){
                bookList=new ArrayList<>();
            }
            return bookList;
        }
        else {
            List<Book> bookList= bookRepository.findByAvailability(available);
            if(bookList==null){
                bookList=new ArrayList<>();
            }
            return bookList;
        }
    }

}
