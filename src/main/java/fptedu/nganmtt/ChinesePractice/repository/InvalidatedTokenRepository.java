package fptedu.nganmtt.ChinesePractice.repository;

import fptedu.nganmtt.ChinesePractice.model.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
}
