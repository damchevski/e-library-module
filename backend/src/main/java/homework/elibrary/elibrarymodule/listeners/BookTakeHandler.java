package homework.elibrary.elibrarymodule.listeners;

import homework.elibrary.elibrarymodule.model.Book;
import homework.elibrary.elibrarymodule.model.events.BookTakeEvent;
import homework.elibrary.elibrarymodule.service.BookService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BookTakeHandler {

    private final BookService bookService;

    public BookTakeHandler(BookService bookService) {
        this.bookService = bookService;
    }

    @EventListener
    public void onBookTake(BookTakeEvent bookTakeEvent) {
        this.bookService.minusBook((Book) bookTakeEvent.getSource());
    }
}
