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
import org.springframework.security.access.prepost.PreAuthorize;
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
        try {
            return hskLevelRepository.findAll().stream()
                    .map(hskLevelMapper::toHskLevelResponse).toList();
        } catch (Exception e) {
            log.error("Error fetching all HSK levels: {}", e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public HskLevelResponse getById(UUID id){
        try {
            return hskLevelRepository.findById(id)
                    .map(hskLevelMapper::toHskLevelResponse)
                    .orElseThrow(() -> new AppException(ErrorCode.HSK_LEVEL_NOT_FOUND));
        } catch (Exception e) {
            log.error("Error fetching HSK level by id {}: {}", id, e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public HskLevelResponse create(HskLevelRequest request) {
        try {
            return hskLevelMapper
                    .toHskLevelResponse(hskLevelRepository
                            .save(hskLevelMapper.toHskLevel(request)));
        } catch (Exception e) {
            log.error("Error creating HSK level: {}", e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(UUID id) {
        try {
            var hskLevel = hskLevelRepository.findById(id)
                    .orElseThrow(() -> new AppException(ErrorCode.HSK_LEVEL_NOT_FOUND));
            hskLevelRepository.delete(hskLevel);
        } catch (Exception e) {
            log.error("Error deleting HSK level with id {}: {}", id, e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }
}
