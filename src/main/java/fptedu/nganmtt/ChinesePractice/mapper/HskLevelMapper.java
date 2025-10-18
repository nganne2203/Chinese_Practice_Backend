package fptedu.nganmtt.ChinesePractice.mapper;

import fptedu.nganmtt.ChinesePractice.dto.request.HskLevelRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.HskLevelResponse;
import fptedu.nganmtt.ChinesePractice.model.HSKLevel;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface HskLevelMapper {
    HSKLevel toHskLevel(HskLevelRequest hskLevelRequest);
    HskLevelResponse toHskLevelResponse(HSKLevel hskLevel);
}
