import axios from "../custom-axios/axios";

const ELibraryRepository = {

    fetchAuthors: () => {
        return axios.get("/authors");
    },

    fetchCategories: () => {
        return axios.get("/categories");
    },

    fetchBooks: () => {
        return axios.get("/books");
    },

    takeBook: (id) => {
        return axios.get(`/take/${id}`);
    },

    deleteBook: (id) => {
        return axios.delete(`/delete/${id}`);
    },

    addBook: (name, category, author, availableCopies) => {
        return axios.post('/add', {
            'name': name,
            'author': author,
            'category': category,
            'availableCopies': availableCopies
        });
    },

    getBook: (id) => {
        return axios.get(`/books/${id}`);
    },

    editBook: (id, name, author, category, availableCopies) => {
        return axios.put(`/edit/${id}`, {
            'name': name,
            'author': author,
            'category': category,
            'availableCopies': availableCopies
        });
    }


};

export default ELibraryRepository;