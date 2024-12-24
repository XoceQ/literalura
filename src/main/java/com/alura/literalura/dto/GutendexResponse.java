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

        @JsonProperty("download_count")  // Assuming the API returns this field
        private Integer downloadCount;

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

        public Integer getDownloadCount() {
            return downloadCount;
        }

        public void setDownloadCount(Integer downloadCount) {
            this.downloadCount = downloadCount;
        }
    }

    public static class Author {
        private String name;
        private Integer birth_year;
        private Integer death_year;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getBirth_year() {
            return birth_year;
        }

        public void setBirth_year(Integer birth_year) {
            this.birth_year = birth_year;
        }

        public Integer getDeath_year() {
            return death_year;
        }

        public void setDeath_year(Integer death_year) {
            this.death_year = death_year;
        }
    }

}