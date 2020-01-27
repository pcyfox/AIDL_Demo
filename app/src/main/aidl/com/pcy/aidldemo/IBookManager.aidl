package com.pcy.aidldemo;

import com.pcy.aidldemo.model.Book;


interface IBookManager {
  List<Book> getBookList();
  void addBook(in Book book);
}