package fptedu.nganmtt.ChinesePractice.mapper;

import fptedu.nganmtt.ChinesePractice.dto.request.LessonRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.LessonResponse;
import fptedu.nganmtt.ChinesePractice.model.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LessonMapper {
    Lesson toLesson(LessonRequest lesson);

    LessonResponse toLessonResponse(Lesson lesson);

    void updateLesson(@MappingTarget Lesson existingLesson, LessonRequest lessonRequest);
}
