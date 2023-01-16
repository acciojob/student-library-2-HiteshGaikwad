package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.BookRepository;
import com.driver.repositories.CardRepository;
import com.driver.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    BookRepository bookRepository5;

    @Autowired
    CardRepository cardRepository5;

    @Autowired
    TransactionRepository transactionRepository5;

    @Value("${books.max_allowed}")
    public int max_allowed_books;

    @Value("${books.max_allowed_days}")
    public int getMax_allowed_days;

    @Value("${books.fine.per_day}")
    public int fine_per_day;

    public String issueBook(int cardId, int bookId) throws Exception {
        //check whether bookId and cardId already exist
        //conditions required for successful transaction of issue book:
        //1. book is present and available
        // If it fails: throw new Exception("Book is either unavailable or not present");
        //2. card is present and activated
        // If it fails: throw new Exception("Card is invalid");
        //3. number of books issued against the card is strictly less than max_allowed_books
        // If it fails: throw new Exception("Book limit has reached for this card");
        //If the transaction is successful, save the transaction to the list of transactions and return the id

        //Note that the error message should match exactly in all cases

        try{
        if(cardRepository5.existsById(cardId)){
        Card card= cardRepository5.findById(cardId).get();
       if(card.getCardStatus() == CardStatus.ACTIVATED) {
       }
       }
        }
        catch(Exception e){
            return "Card is invalid";
        }
        try {
            if(bookRepository5.existsById(bookId)) {
                Book book = bookRepository5.findById(bookId).get();
                if (book.isAvailable()){

                }
            }
        }catch(Exception e) {
            return "Book is either unavailable or not present";
        }
            Card card = cardRepository5.findById(cardId).get();
        //card.setCardStatus(CardStatus.ACTIVATED);
            int total=card.getBooks().size();
            int max=max_allowed_books;
             try{
                 if(total<max){
                 }
        } catch(Exception e){
                 return "Book limit has reached for this card";
             }
             Transaction transaction=new Transaction();
        Book book= bookRepository5.findById(bookId).get();
        List<Book> list= new ArrayList<>();
        list.add(book);
        transaction.setBook(book);
        card.setBooks(list);
        book.setAvailable(false);

        transaction.setCard(card);
        transaction.setFineAmount(100);
        transaction.setIssueOperation(true);
        transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
             transactionRepository5.save(transaction);
       return transaction.getTransactionId();//return transactionId instead
    }

    public Transaction returnBook(int cardId, int bookId) throws Exception{

        List<Transaction> transactions = transactionRepository5.find(cardId, bookId, TransactionStatus.SUCCESSFUL, true);
        Book book= bookRepository5.findById(bookId).get();

        Transaction transaction = book.getTransactions().get(bookId);
                //transactions.get(transactions.size() - 1);

        //for the given transaction calculate the fine amount considering the book has been returned exactly when this function is called
        //make the book available for other users
        //make a new transaction for return book which contains the fine amount as well


        book.setAvailable(true);

        Card card= cardRepository5.findById(cardId).get();

        transactions.remove(transaction);
        transactionRepository5.delete(transaction);

        transaction.getFineAmount();
        transaction.setIssueOperation(false);
        transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);

        Transaction returnBookTransaction  = transaction;
        return returnBookTransaction; //return the transaction after updating all details
    }
}
