package fptedu.nganmtt.ChinesePractice.repository;

import fptedu.nganmtt.ChinesePractice.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UnitsRepository extends JpaRepository<Unit, UUID> {
    List<Unit> findAllByLevel_Id(UUID level);

}
