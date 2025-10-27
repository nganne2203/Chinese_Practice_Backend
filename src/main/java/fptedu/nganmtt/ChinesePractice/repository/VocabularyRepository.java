package fptedu.nganmtt.ChinesePractice.repository;

import fptedu.nganmtt.ChinesePractice.dto.response.VocabularyResponse;
import fptedu.nganmtt.ChinesePractice.model.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VocabularyRepository extends JpaRepository<Vocabulary, UUID> {
    @Query("""
    SELECT v 
    FROM Vocabulary v
    JOIN v.lesson l
    JOIN l.unit u
    JOIN u.level h
    WHERE (:unitId IS NULL OR u.id = :unitId)
      AND (:hskLevelId IS NULL OR h.id = :hskLevelId)
""")
    List<Vocabulary> findByOptionalUnitAndHSKLevel(
            @Param("unitId") UUID unitId,
            @Param("hskLevelId") UUID hskLevelId);



    @Query("""
    SELECT v
    FROM Vocabulary v
    WHERE 
        (:chineseWord IS NULL OR :chineseWord = '' 
         OR v.hanzi LIKE CONCAT('%', :chineseWord, '%') 
         OR v.pinyin LIKE CONCAT('%', :chineseWord, '%'))
""")
    List<Vocabulary> findByChineseWord(@Param("chineseWord") String chineseWord);

    List<Vocabulary> findAllByLesson_Id(UUID lessonId);

}
