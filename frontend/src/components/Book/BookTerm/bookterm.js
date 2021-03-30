import React from "react";
import { Link } from "react-router-dom";

const bookTerm = (props) => {
    return (
        <tr>
            <td>{props.term.name}</td>
            <td>{props.term.category}</td>
            <td>{props.term.author.name + " " + props.term.author.surname}</td>
            <td>{props.term.availableCopies}</td>
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