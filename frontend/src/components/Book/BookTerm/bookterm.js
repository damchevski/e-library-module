import React from "react";
import { Link } from "react-router-dom";

const bookTerm = (props) => {
    return (
        <tr class="book">
            <td class="bookName">{props.term.name}</td>
            <td class="bookCategory">{props.term.category}</td>
            <td class="bookAuthor">{props.term.author.name + " " + props.term.author.surname}</td>
            <td class="bookNCopies">{props.term.availableCopies}</td>
            <td>
                 <a title={"Take Book"} className={"btn btn-success"}
                   onClick={() => props.takeBook(props.term.id)}>
                    Take Book
                </a>

                <Link className={"btn btn-info ml-2 mr-2"}
                      onClick={() => props.editBook(props.term.id)}
                      to={`/books/edit/${props.term.id}`}>
                    Edit
                </Link>

                <a title={"Delete"} className={"btn btn-danger"}
                   onClick={() => props.deleteBook(props.term.id)}>
                    Delete
                </a>

            </td>
        </tr>
    );
};

export default bookTerm;