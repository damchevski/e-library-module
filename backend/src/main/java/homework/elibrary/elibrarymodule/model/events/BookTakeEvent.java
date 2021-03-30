package homework.elibrary.elibrarymodule.model.events;

import homework.elibrary.elibrarymodule.model.Book;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class BookTakeEvent extends ApplicationEvent {
    public BookTakeEvent(Book source) {
        super(source);
    }
}
