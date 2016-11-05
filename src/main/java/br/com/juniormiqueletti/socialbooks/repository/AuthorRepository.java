package br.com.juniormiqueletti.socialbooks.repository;

import br.com.juniormiqueletti.socialbooks.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long>{

}
