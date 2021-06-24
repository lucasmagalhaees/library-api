package com.lucasbarbosa.libraryapi.model.dto;

import com.lucasbarbosa.libraryapi.model.entity.Book;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Function;

import static com.lucasbarbosa.libraryapi.driver.utils.DateUtils.formatDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDTO {

  private String isbn;

  private String title;

  private String author;

  private int numberPages;

  private String genre;

  private String creationDate;

  private String updateDate;

  public static BookResponseDTO of(Book book) {
    return disassemble.apply(book);
  }

  private static Function<Book, BookResponseDTO> disassemble =
      book ->
          BookResponseDTO.builder()
              .isbn(book.getIsbn())
              .title(book.getTitle())
              .author(book.getAuthor())
              .genre(StringUtils.capitalize(book.getBookGenre().toString().toLowerCase()))
              .numberPages(book.getNumberPages())
              .creationDate(formatDateTime(book.getCreationDate()))
              .updateDate(formatDateTime(book.getUpdateDate()))
              .build();
}