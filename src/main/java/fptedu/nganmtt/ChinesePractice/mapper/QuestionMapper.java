package fptedu.nganmtt.ChinesePractice.mapper;

import fptedu.nganmtt.ChinesePractice.dto.request.QuestionDetailRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.QuestionDetailResponse;
import fptedu.nganmtt.ChinesePractice.dto.response.QuestionResponse;
import fptedu.nganmtt.ChinesePractice.model.Question;
import org.mapstruct.*;
import org.springframework.util.StringUtils;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    @Mapping(target = "options", expression = "java(listToString(request.getOptions()))")
    Question toQuestionByDetail(QuestionDetailRequest request);

    @Mapping(target = "options", expression = "java(stringToList(question.getOptions()))")
    QuestionResponse toQuestionResponse(Question question);

    @Mapping(target = "options", expression = "java(stringToList(question.getOptions()))")
    QuestionDetailResponse toQuestionDetailResponse(Question question);

    @Mapping(target = "options", expression = "java(listToString(request.getOptions()))")
    void updateQuestion(@MappingTarget Question question, QuestionDetailRequest request);

    default String listToString(List<String> options) {
        return (options == null || options.isEmpty()) ? null : String.join(",", options);
    }

    default List<String> stringToList(String options) {
        return (StringUtils.hasText(options)) ? List.of(options.split(",")) : List.of();
    }
}
