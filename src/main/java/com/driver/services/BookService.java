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
    BookRepository bookRepository2;

    @Autowired
    AuthorRepository authorRepository;

    public void createBook(Book book){
       // int authorId=book.getAuthor().getId();

       // Author author= authorRepository.findById(authorId).get();

//        Author author=book.getAuthor();
//
//        List<Book> list=author.getBooksWritten();
//
//        if(list==null) {
//         list=new ArrayList<>();
//        }
//
//        list.add(book);
//
//        author.setBooksWritten(list);
//
//        book.setAuthor(author);
//
//        //authorRepository.save(author);
//
//        bookRepository2.save(book);

        int authorId = book.getAuthor().getId();

        Author author =  authorRepository.findById(authorId).get();

        //Update the bookList written by Author
        author.getBooksWritten().add(book);

        //Updated the book
        book.setAuthor(author);
        //bookRepository2.save(book);
        bookRepository2.save(book);

        authorRepository.save(author);
    }

    public List<Book> getBooks(String genre, boolean available, String author){

        //find the elements of the list by yourself
//        if(genre!=null && author!=null) {
//           return bookRepository2.findBooksByGenreAuthor(genre, author, available);
//        }else if(author!=null){
//            return bookRepository2.findBooksByAuthor(author,available);
//        }else if(genre!=null){
//            return bookRepository2.findBooksByGenre(genre,available);
//        }else{
//            return bookRepository2.findByAvailability(available);
//        }

        if(genre != null && author != null){
            return bookRepository2.findBooksByGenreAuthor(genre, author, available);
        }else if(genre != null){
            return bookRepository2.findBooksByGenre(genre, available);
        }else if(author != null){
            return bookRepository2.findBooksByAuthor(author, available);
        }else{
            return bookRepository2.findByAvailability(available);
        }
    }

}
