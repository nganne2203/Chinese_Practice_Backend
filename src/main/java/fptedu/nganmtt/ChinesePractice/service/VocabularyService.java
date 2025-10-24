package fptedu.nganmtt.ChinesePractice.service;

import fptedu.nganmtt.ChinesePractice.dto.request.VocabularyRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.VocabularyDetailResponse;
import fptedu.nganmtt.ChinesePractice.dto.response.VocabularyResponse;
import fptedu.nganmtt.ChinesePractice.exception.AppException;
import fptedu.nganmtt.ChinesePractice.exception.ErrorCode;
import fptedu.nganmtt.ChinesePractice.mapper.VocabularyMapper;
import fptedu.nganmtt.ChinesePractice.repository.LessonRepository;
import fptedu.nganmtt.ChinesePractice.repository.VocabularyRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VocabularyService {
    VocabularyRepository vocabularyRepository;
    VocabularyMapper vocabularyMapper;
    LessonRepository lessonRepository;

    public List<VocabularyResponse> getVocabByUnitAndHsk(String unitId, String hskLevelId) {
        try {
            UUID unitUUID = (unitId != null && !unitId.isBlank()) ? UUID.fromString(unitId) : null;
            UUID levelUUID = (hskLevelId != null && !hskLevelId.isBlank()) ? UUID.fromString(hskLevelId) : null;

            return vocabularyRepository
                    .findByOptionalUnitAndHSKLevel(unitUUID, levelUUID)
                    .stream()
                    .map(vocabularyMapper::toVocabularyResponse)
                    .toList();

        } catch (Exception e) {
            log.error("Error fetching vocabularies by unit {} and HSK level {}: {}", unitId, hskLevelId, e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }


    public VocabularyResponse getVocabularyById(String id) {
        try {
            return vocabularyRepository.findById(java.util.UUID.fromString(id))
                    .map(vocabularyMapper::toVocabularyResponse)
                    .orElseThrow(() -> new AppException(ErrorCode.VOCABULARY_NOT_FOUND));
        } catch (Exception e) {
            log.error("Error fetching vocabularies by id {}: {}", id, e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @PreAuthorize("hasAuthority('VOCABULARY_MANAGE') or hasAuthority('ADMIN')")
    public VocabularyResponse createVocabulary(VocabularyRequest request) {
        try {
            var vocabulary = vocabularyMapper.toVocabulary(request);
            vocabulary.setLesson(lessonRepository
                    .findById(java.util.UUID.fromString(request.getLessonId()))
                    .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND)));
            return vocabularyMapper.toVocabularyResponse(vocabularyRepository.save(vocabulary));
        } catch (Exception e) {
            log.error("Error creating vocabulary: {}", e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @PreAuthorize("hasAuthority('VOCABULARY_MANAGE') or hasAuthority('ADMIN')")
    public void updateVocabulary(String id, VocabularyRequest request) {
        try{
            var existingVocabulary = vocabularyRepository
                    .findById(java.util.UUID.fromString(id))
                    .orElseThrow(() -> new AppException(ErrorCode.VOCABULARY_NOT_FOUND));
            existingVocabulary.setLesson(lessonRepository
                    .findById(java.util.UUID.fromString(request.getLessonId()))
                    .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND)));
            vocabularyMapper.updateVocabularyFromRequest(request, existingVocabulary);
            vocabularyRepository.save(existingVocabulary);
        } catch (Exception e) {
            log.error("Error fetching vocabulary by id {}: {}", id, e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @PreAuthorize("hasAuthority('VOCABULARY_MANAGE') or hasAuthority('ADMIN')")
    public void deleteVocabulary(String id) {
        try {
            var existingVocabulary = vocabularyRepository
                    .findById(java.util.UUID.fromString(id))
                    .orElseThrow(() -> new AppException(ErrorCode.VOCABULARY_NOT_FOUND));
            vocabularyRepository.delete(existingVocabulary);
        } catch (Exception e) {
            log.error("Error deleting vocabulary by id {}: {}", id, e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public List<VocabularyResponse> getAllVocabularies() {
        try {
            return vocabularyRepository.findAll()
                    .stream()
                    .map(vocabularyMapper::toVocabularyResponse)
                    .toList();
        } catch (Exception e) {
            log.error("Error fetching all vocabularies: {}", e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public List<VocabularyDetailResponse> searchVocabularyDetailByKeyword(String keyword) {
        try {
            return vocabularyRepository.findByChineseWord(keyword)
                    .stream()
                    .map(vocabularyMapper::toVocabularyDetailResponse)
                    .toList();
        } catch (Exception e) {
            log.error("Error searching vocabulary detail by keyword {}: {}", keyword, e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }
}
