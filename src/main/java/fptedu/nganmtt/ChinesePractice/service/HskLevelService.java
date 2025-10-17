package fptedu.nganmtt.ChinesePractice.service;

import fptedu.nganmtt.ChinesePractice.dto.request.HskLevelRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.HskLevelResponse;
import fptedu.nganmtt.ChinesePractice.exception.AppException;
import fptedu.nganmtt.ChinesePractice.exception.ErrorCode;
import fptedu.nganmtt.ChinesePractice.mapper.HskLevelMapper;
import fptedu.nganmtt.ChinesePractice.repository.HskLevelRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HskLevelService {
    HskLevelRepository hskLevelRepository;
    HskLevelMapper hskLevelMapper;

    public List<HskLevelResponse> getAll() {
        return hskLevelRepository.findAll().stream()
                .map(hskLevelMapper::toHskLevelResponse).toList();
    }

    public HskLevelResponse getById(UUID id){
        return hskLevelRepository.findById(id)
                .map(hskLevelMapper::toHskLevelResponse)
                .orElseThrow(() -> new AppException(ErrorCode.HSK_LEVEL_NOT_FOUND));
    }

    public HskLevelResponse create(HskLevelRequest request) {
        return hskLevelMapper
                .toHskLevelResponse(hskLevelRepository
                        .save(hskLevelMapper.toHskLevel(request)));
    }

    public void delete(UUID id) {
        var hskLevel = hskLevelRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.HSK_LEVEL_NOT_FOUND));
        hskLevelRepository.delete(hskLevel);
    }
}
