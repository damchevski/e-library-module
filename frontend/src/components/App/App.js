import { Component } from 'react';
import { BrowserRouter as Router, Redirect, Route } from 'react-router-dom';

import Categories from "../Categories/categories";
import BookList from "../Book/BookList/booklist";
import Header from "../Header/header";

import ELibraryRepository from "../../repository/eLibraryRepository";
import BookAdd from '../Book/BookAdd/bookadd';
import BookEdit from '../Book/BookEdit/bookedit';

class App extends Component {

    constructor(props) {
        super(props);

        this.state = {
            categories: [],
            authors: [],
            books: [],
            selectedBook: {}
        };
    };

    render() {
    return (
        <Router>
          <Header/>
          <main>
                <div className="container">
                    
                    <Route path={"/books"} exact render={() =>
                        <BookList books={this.state.books}
                            takeBook={this.takeBook}
                            deleteBook={this.deleteBook}
                            editBook={this.getBook}/>} />
                    
                    <Route path={"/categories"} exact render={() =>
                        <Categories categories={this.state.categories} />} />
                    
                    <Route path={"/books/add"} exact render={() => 
                        <BookAdd onAddBook={this.addBook}
                            categories={this.state.categories}
                            authors={this.state.authors} />} />
                    
                    <Route path={"/books/edit/:id"} exact render={() => 
                        <BookEdit onEditBook={this.editBook}
                            categories={this.state.categories}
                            authors={this.state.authors}
                            book={this.state.selectedBook}/>} />
                    
                    <Redirect to={"/books"}/>
                 </div>
          </main>
        </Router>
    );
    };

    componentDidMount() {
        this.loadBooks();
        this.loadAuthors();
        this.loadCategories();
    };

    loadBooks= () => {
        ELibraryRepository.fetchBooks()
            .then((data) => {
                this.setState({
                    books: data.data
                });
            });
    };

    loadAuthors= () =>{
        ELibraryRepository.fetchAuthors()
            .then((data) => {
                this.setState({
                    authors: data.data
                });
            });
    };

    loadCategories = () => {
        ELibraryRepository.fetchCategories()
            .then((data) => {
                this.setState({
                    categories: data.data
                });
            });
    };

    takeBook = (id) => {
        ELibraryRepository.takeBook(id)
            .then(() => {
                this.loadBooks();
            });
    };

    deleteBook = (id) => {
        ELibraryRepository.deleteBook(id)
            .then(() => {
                this.loadBooks();
            });
    };

    addBook = (name, category, author, availableCopies) => {
        ELibraryRepository.addBook(name, category, author, availableCopies)
            .then(() => {
                this.loadBooks();
            });
    };

    getBook = (id) => {
        ELibraryRepository.getBook(id)
            .then((data) => {
                this.setState({
                    selectedBook: data.data
                });
            });
    };

    
    editBook = (id, name, category, author, availableCopies) => {
        ELibraryRepository.editBook(id, name, author, category, availableCopies)
            .then(() => {
                this.loadBooks();
            });
    };

    
}

export default App;
