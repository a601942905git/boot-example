package com.boot.example.core.controller;

import com.boot.example.core.model.Book;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * com.boot.example.core.controller.BookController
 *
 * @author lipeng
 * @dateTime 2018/12/14 下午3:16
 */
@Controller
public class BookController {

    @PreAuthorize("hasAuthority('/books')")
    @RequestMapping("/books")
    public String books(Model model) {
        Book book1 = Book.builder().id(10001).name("射雕英雄传").publishDate("2018-12-14 09:00:00").build();
        Book book2 = Book.builder().id(10002).name("天龙八部").publishDate("2018-12-15 15:20:00").build();
        Book book3 = Book.builder().id(10003).name("倚天屠龙记").publishDate("2018-12-16 18:05:00").build();
        Book book4 = Book.builder().id(10004).name("神雕侠侣").publishDate("2018-12-17 13:00:00").build();
        Book book5 = Book.builder().id(10005).name("笑傲江湖").publishDate("2018-12-18 19:00:00").build();

        List<Book> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);
        bookList.add(book4);
        bookList.add(book5);

        model.addAttribute("bookList", bookList);
        return "book";
    }

    @PreAuthorize("hasAuthority('/books/add')")
    @RequestMapping("/books/add")
    @ResponseBody
    public String add() {
        return "新增图书";
    }

    @PreAuthorize("hasAuthority('/books/update')")
    @RequestMapping("/books/update")
    @ResponseBody
    public String update() {
        return "修改图书";
    }

    @PreAuthorize("hasAuthority('/books/delete')")
    @RequestMapping("/books/delete")
    @ResponseBody
    public String delete() {
        return "删除图书";
    }
}
