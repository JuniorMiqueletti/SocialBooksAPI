package br.com.juniormiqueletti.socialbooks.repository;

import br.com.juniormiqueletti.socialbooks.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {

}
