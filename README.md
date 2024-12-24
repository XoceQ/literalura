LiterAlura - Book Catalog
Description
Welcome to the LiterAlura challenge! In this project, you'll build a book catalog by consuming the Gutendex API, which provides data on over 70,000 books from the Project Gutenberg. The API includes information such as book title, authors, languages, formats, and download counts. Best of all, it's free, easy to use, and doesn’t require an API key!

Tools and Technologies Used:
Spring Boot: For building and running the backend application.
PostgreSQL: For storing book and author data.
Gutendex API: Provides data for books and authors.
JSON: For parsing and manipulating API responses.
Steps
Java Environment Setup

Install Java Development Kit (JDK).
Set up your IDE (e.g., IntelliJ IDEA, Eclipse).
Project Creation

Create a new Java project and organize the folders.
API Consumption

Learn how to consume the Gutendex API and parse JSON responses.
JSON Response Analysis

Analyze the response structure to extract required data (e.g., book title, author, etc.).
Database Insertion and Querying

Insert data into PostgreSQL and query for specific books and authors.
Displaying Results

Display filtered results to users in a user-friendly format.
Key Classes and Interfaces
HttpClient: Responsible for making HTTP requests to the book API.
HttpRequest: Defines the structure for making requests.
HttpResponse: Handles API responses, including error handling and data extraction.
Scanner: Used for reading input from the user for queries.

Here are the functionalities implemented in the LiterAlura project:

Search Books: Allows the user to search books by title using the Gutendex API and store selected books in the database.
Show Saved Books: Displays a list of all books saved in the database.
Delete Book: Deletes a book from the database using its ID.
Show Authors by Book: Displays authors of a specific book based on the book's ID.
Show All Authors: Lists all authors available in the database.
Show Books by Language: Displays the number of books available in specific languages.
Show Authors Alive in a Specific Year: Lists authors who were alive during a specific year.
