package fptedu.nganmtt.ChinesePractice.mapper;

import fptedu.nganmtt.ChinesePractice.dto.request.QuizDetailRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.QuizDetailResponse;
import fptedu.nganmtt.ChinesePractice.dto.response.QuizResponse;
import fptedu.nganmtt.ChinesePractice.model.Quiz;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {UserMapper.class, LessonMapper.class, QuestionMapper.class})
public interface QuizMapper {
    QuizResponse toQuizResponse(Quiz quiz);

    QuizDetailResponse toQuizDetailResponse(Quiz quiz);

    Quiz toQuizDetail(QuizDetailRequest quizDetailRequest);

    void updateQuizDetail(QuizDetailRequest quizDetailRequest, @MappingTarget Quiz quiz);
}
