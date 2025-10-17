package fptedu.nganmtt.ChinesePractice.repository;

import fptedu.nganmtt.ChinesePractice.model.HSKLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HskLevelRepository extends JpaRepository<HSKLevel, UUID> {
}
