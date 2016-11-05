package br.com.juniormiqueletti.socialbooks.repository;

import br.com.juniormiqueletti.socialbooks.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository  extends JpaRepository<Comment,Long>{
}
