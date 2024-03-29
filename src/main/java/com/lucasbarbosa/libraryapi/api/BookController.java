package com.lucasbarbosa.libraryapi.api;

import com.lucasbarbosa.libraryapi.model.dto.BookListRequest;
import com.lucasbarbosa.libraryapi.model.dto.BookRequest;
import com.lucasbarbosa.libraryapi.model.dto.BookResponse;
import com.lucasbarbosa.libraryapi.model.enums.BookGenreEnum;
import com.lucasbarbosa.libraryapi.service.BookService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/** @author Lucas Barbosa on 27/06/2021 */
@RestController
@RequestMapping("/books")
@Api(tags = "Book")
public class BookController {

  private final BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Resource] responsible for fetching all registered books")
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Fetched all books sucessfully",
            response = BookResponse.class)
      })
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "isbn",
        value = "International Standard Book Number",
        dataType = "string",
        paramType = "query"),
    @ApiImplicitParam(
        name = "title",
        value = "Book Title",
        dataType = "string",
        paramType = "query"),
    @ApiImplicitParam(
        name = "author",
        value = "Book Author",
        dataType = "string",
        paramType = "query"),
    @ApiImplicitParam(
        name = "bookGenre",
        value = "Book Genre",
        allowableValues =
            "ACTION,"
                + "COMEDY,"
                + "DRAMA,"
                + "FANTASY,"
                + "HORROR,"
                + "MYSTERY,"
                + "ROMANCE,"
                + "THRILLER",
        dataTypeClass = BookGenreEnum.class,
        paramType = "query"),
    @ApiImplicitParam(
        name = "initialDate",
        value = "Initial Creation Date",
        dataType = "string",
        paramType = "query"),
    @ApiImplicitParam(
        name = "finalDate",
        value = "Final Creation Date",
        dataType = "string",
        paramType = "query"),
    @ApiImplicitParam(
        name = "bookMaxPages",
        value = "Max Book Number of Pages",
        dataType = "string",
        paramType = "query")
  })
  public ResponseEntity<List<BookResponse>> fetchBooks(
      @RequestParam(name = "isbn", required = false) String isbn,
      @RequestParam(name = "title", required = false) String title,
      @RequestParam(name = "author", required = false) String author,
      @RequestParam(name = "bookGenre", required = false) String bookGenre,
      @RequestParam(name = "initialDate", required = false) String initialDate,
      @RequestParam(name = "finalDate", required = false) String finalDate,
      @RequestParam(name = "bookMaxPages", required = false) String bookMaxPages) {

    var books =
        bookService.fetchBooks(
            isbn, title, author, bookGenre, initialDate, finalDate, bookMaxPages);

    return ResponseEntity.ok(books);
  }

  @PostMapping(
      path = "/register",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Resource responsible for registering books")
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 201,
            message = "Book sucessfully registered",
            response = BookResponse.class),
        @ApiResponse(code = 400, message = "Error due to incorrect request contract")
      })
  public ResponseEntity<BookResponse> createBook(
      @Validated @RequestBody BookRequest bookRequestDTO) {
    var book = bookService.createBook(bookRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(BookResponse.of(book));
  }

  @PostMapping(
      path = "/register/list",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Resource responsible for registering a list of books")
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 201,
            message = "Book sucessfully registered",
            response = BookResponse.class),
        @ApiResponse(code = 400, message = "Error due to incorrect request contract")
      })
  public ResponseEntity<List<BookResponse>> createListOfBooks(
      @Validated @RequestBody BookListRequest bookListRequest) {
    var book = bookService.registerBookList(bookListRequest);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(book.stream().map(BookResponse::of).collect(Collectors.toList()));
  }
}
