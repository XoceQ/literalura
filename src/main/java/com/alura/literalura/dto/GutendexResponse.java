package com.alura.literalura.dto;


import com.alura.literalura.service.GutendexService;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import java.util.List;
import java.util.Map;

public class GutendexResponse {

    @JsonProperty("results")
    private List<Book> results;

    public List<Book> getResults() {
        return results;
    }

    public void setResults(List<Book> results) {
        this.results = results;
    }

    public static class Book {
        private String title;

        @JsonProperty("authors")
        private List<Author> authors;

        @JsonProperty("languages")
        private List<String> languages;

        @JsonProperty("formats")
        private Map<String, String> formats;

        // Getters y Setters
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<Author> getAuthors() {
            return authors;
        }

        public void setAuthors(List<Author> authors) {
            this.authors = authors;
        }

        public List<String> getLanguages() {
            return languages;
        }

        public void setLanguages(List<String> languages) {
            this.languages = languages;
        }

        public Map<String, String> getFormats() {
            return formats;
        }

        public void setFormats(Map<String, String> formats) {
            this.formats = formats;
        }
    }

    public static class Author {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}