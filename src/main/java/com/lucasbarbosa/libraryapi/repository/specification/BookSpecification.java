package com.lucasbarbosa.libraryapi.repository.specification;

import com.lucasbarbosa.libraryapi.driver.utils.DateUtils;
import com.lucasbarbosa.libraryapi.model.entity.Book;
import com.lucasbarbosa.libraryapi.model.enums.BookGenreEnum;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static com.lucasbarbosa.libraryapi.driver.utils.LibraryUtils.*;
/** @author Lucas Barbosa on 27/06/2021 */
public class BookSpecification extends BookSpecificationSupport {

  public static Specification<Book> byTitle(String title) {
    return Optional.ofNullable(title).map(BookSpecificationSupport::setUpTitle).orElse(null);
  }

  public static Specification<Book> byAuthor(String author) {
    return Optional.ofNullable(author).map(BookSpecificationSupport::setUpAuthor).orElse(null);
  }

  public static Specification<Book> byBookGenre(String bookGenre) {
    return Optional.ofNullable(BookGenreEnum.findByLiteral(bookGenre))
        .map(BookSpecificationSupport::setUpBookGenre)
        .orElse(null);
  }

  public static Specification<Book> byISBN(String isbn) {
    return Optional.ofNullable(isbn).map(BookSpecificationSupport::setUpISBN).orElse(null);
  }

  public static Specification<Book> byCreationDate(String initialDate, String finalDate) {
    LocalDate startDate = Optional.ofNullable(initialDate).map(LocalDate::parse).orElse(null);
    LocalDate endDate = Optional.ofNullable(finalDate).map(LocalDate::parse).orElse(null);

    LocalDateTime startDateTime =
        Optional.ofNullable(startDate)
            .map(presentInitialDateTime -> LocalDateTime.of(presentInitialDateTime, LocalTime.MIN))
            .orElse(DateUtils.buildMinLocalDateTime());
    LocalDateTime endDateTime =
        Optional.ofNullable(endDate)
            .map(presentFinalDateTime -> LocalDateTime.of(presentFinalDateTime, LocalTime.MAX))
            .orElse(DateUtils.buildMaxLocalDateTime());

    return BookSpecificationSupport.setUpDateCreation(startDateTime, endDateTime);
  }

  public static Specification<Book> byNumberPages(String maxNumberPages) {
    int maxPages = Optional.ofNullable(maxNumberPages).map(Integer::parseInt).orElse(ONE_THOUSAND);

    return BookSpecificationSupport.setUpNumberPages(maxPages);
  }
}
