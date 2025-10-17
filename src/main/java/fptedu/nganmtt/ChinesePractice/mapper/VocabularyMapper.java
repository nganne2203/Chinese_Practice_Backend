package fptedu.nganmtt.ChinesePractice.mapper;

import fptedu.nganmtt.ChinesePractice.dto.request.VocabularyRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.VocabularyDetailResponse;
import fptedu.nganmtt.ChinesePractice.dto.response.VocabularyResponse;
import fptedu.nganmtt.ChinesePractice.model.Vocabulary;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {LessonMapper.class})
public interface VocabularyMapper {
    Vocabulary toVocabulary(VocabularyRequest vocabularyRequest);

    VocabularyResponse toVocabularyResponse(Vocabulary vocabulary);

    void updateVocabularyFromRequest(VocabularyRequest vocabularyRequest, @MappingTarget Vocabulary vocabulary);

    VocabularyDetailResponse toVocabularyDetailResponse(Vocabulary vocabulary);
}
