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

        Author author=book.getAuthor();

        List<Book> list=author.getBooksWritten();

        if(list==null) {
         list=new ArrayList<>();
        }

        list.add(book);

        author.setBooksWritten(list);

        book.setAuthor(author);

        //authorRepository.save(author);

        bookRepository2.save(book);

    }

    public List<Book> getBooks(String genre, boolean available, String author){

        //find the elements of the list by yourself

        List<Book> bookList=new ArrayList<>();

        if(genre != null && author != null){
            bookList= bookRepository2.findBooksByGenreAuthor(genre, author, available);
            return bookList;
        }else if(author==null){
            bookList= bookRepository2.findBooksByGenre(genre, available);
            return bookList;
        }else if(genre==null){
            bookList= bookRepository2.findBooksByAuthor(author, available);
            return bookList;
        }else{
            bookList= bookRepository2.findByAvailability(available);
            return bookList;
        }
    }

}
