package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm bookForm) {
        Book book = new Book();
        book.setName(bookForm.getName());
        book.setPrice(bookForm.getPrice());
        book.setStockQuantity(bookForm.getStockQuantity());
        book.setAuthor(bookForm.getAuthor());
        book.setIsbn(bookForm.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String itemList(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "items/items";
    }


    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable Long itemId, Model model) {
        Book editBook = (Book) itemService.findItem(itemId);
        BookForm bookForm = new BookForm();
        bookForm.setId(editBook.getId());
        bookForm.setName(editBook.getName());
        bookForm.setPrice(editBook.getPrice());
        bookForm.setStockQuantity(editBook.getStockQuantity());
        bookForm.setAuthor(editBook.getAuthor());
        bookForm.setIsbn(editBook.getIsbn());

        model.addAttribute("form", bookForm);
        //modelmapper 사용하면 편할수있는데...찾아보던지...
        //multi line select intellij...
        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") BookForm form) {
        /*Book book = new Book();
        book.setId(form.getId());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        itemService.saveItem(book);*/
        itemService.updateItem(itemId, form.getPrice(), form.getStockQuantity());
        return "redirect:/items";
    }
}
