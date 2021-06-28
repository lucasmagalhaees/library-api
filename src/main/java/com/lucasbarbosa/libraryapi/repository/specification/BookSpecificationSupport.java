package com.lucasbarbosa.libraryapi.repository.specification;

import com.lucasbarbosa.libraryapi.model.entity.Book;
import com.lucasbarbosa.libraryapi.model.entity.Book_;
import com.lucasbarbosa.libraryapi.model.enums.BookGenreEnum;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

import static com.lucasbarbosa.libraryapi.driver.utils.LibraryUtils.generateSpecificationQueryPattern;
/** @author Lucas Barbosa on 27/06/2021 */
public class BookSpecificationSupport {

  protected static Specification<Book> setUpTitle(String title) {
    return (root, query, cb) ->
        cb.like(cb.upper(root.get(Book_.TITLE)), generateSpecificationQueryPattern(title));
  }

  protected static Specification<Book> setUpAuthor(String author) {
    return (root, query, cb) ->
        cb.like(cb.upper(root.get(Book_.AUTHOR)), generateSpecificationQueryPattern(author));
  }

  protected static Specification<Book> setUpISBN(String isbn) {
    return (root, query, cb) -> (cb.equal(root.get(Book_.ISBN), isbn));
  }

  protected static Specification<Book> setUpBookGenre(BookGenreEnum bookGenreEnum) {
    return (root, query, cb) -> (cb.equal(root.get(Book_.BOOK_GENRE), bookGenreEnum));
  }

  public static Specification<Book> setUpDateCreation(
      LocalDateTime initialDate, LocalDateTime finalDate) {
    return (root, query, cb) -> cb.between(root.get(Book_.CREATION_DATE), initialDate, finalDate);
  }

  public static Specification<Book> setUpNumberPages(int minPagesNumber, int maxNumberPages) {
    return (root, query, cb) ->
        cb.between(root.get(Book_.NUMBER_PAGES), minPagesNumber, maxNumberPages);
  }
}
