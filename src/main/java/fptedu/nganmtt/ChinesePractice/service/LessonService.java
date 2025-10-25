package fptedu.nganmtt.ChinesePractice.service;

import fptedu.nganmtt.ChinesePractice.dto.request.LessonRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.LessonResponse;
import fptedu.nganmtt.ChinesePractice.exception.AppException;
import fptedu.nganmtt.ChinesePractice.exception.ErrorCode;
import fptedu.nganmtt.ChinesePractice.mapper.LessonMapper;
import fptedu.nganmtt.ChinesePractice.repository.LessonRepository;
import fptedu.nganmtt.ChinesePractice.repository.UnitsRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LessonService {
    LessonRepository lessonRepository;
    LessonMapper lessonMapper;
    UnitsRepository unitsRepository;

    public LessonResponse getLessonById(String id) {
        try {
            return lessonRepository.findById(java.util.UUID.fromString(id))
                    .map(lessonMapper::toLessonResponse)
                    .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND));
        } catch (Exception e) {
            log.error("Error fetching lesson by id {}: {}", id, e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @PreAuthorize("hasAuthority('LESSON_MANAGE') or hasRole('ADMIN')")
    public LessonResponse create(LessonRequest request) {
        try {
            var lesson = lessonMapper.toLesson(request);
            lesson.setUnit(unitsRepository
                    .findById(java.util.UUID.fromString(request.getUnitId()))
                    .orElseThrow(() -> new AppException(ErrorCode.UNIT_NOT_FOUND)));
            return lessonMapper.toLessonResponse(lessonRepository.save(lesson));
        } catch (Exception e) {
            log.error("Error creating lesson: {}", e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @PreAuthorize("hasAuthority('LESSON_MANAGE') or hasRole('ADMIN')")
    public void update(String id, LessonRequest request) {
        try {
            var existingLesson = lessonRepository.findById(java.util.UUID.fromString(id))
                    .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND));
            existingLesson.setUnit(unitsRepository
                    .findById(java.util.UUID.fromString(request.getUnitId()))
                    .orElseThrow(() -> new AppException(ErrorCode.UNIT_NOT_FOUND)));
            lessonMapper.updateLesson(existingLesson, request);
            lessonRepository.save(existingLesson);
        } catch (Exception e) {
            log.error("Error updating lesson with id {}: {}", id, e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @PreAuthorize("hasAuthority('LESSON_MANAGE') or hasRole('ADMIN')")
    public void delete(String id) {
        try {
            var existingLesson = lessonRepository.findById(java.util.UUID.fromString(id))
                    .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND));
            lessonRepository.delete(existingLesson);
        } catch (Exception e) {
            log.error("Error deleting lesson with id {}: {}", id, e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public List<LessonResponse> getAllLessons() {
        try {
            return lessonRepository.findAll()
                    .stream()
                    .map(lessonMapper::toLessonResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching all lessons: {}", e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }
}
