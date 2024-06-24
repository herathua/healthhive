package io.bootify.health_hive.service;

import io.bootify.health_hive.domain.DailyTips;
import io.bootify.health_hive.model.DailyTipsDTO;
import io.bootify.health_hive.repos.DailyTipsRepository;
import io.bootify.health_hive.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DailyTipsService {

    private final DailyTipsRepository dailyTipsRepository;

    public DailyTipsService(final DailyTipsRepository dailyTipsRepository) {
        this.dailyTipsRepository = dailyTipsRepository;
    }

    public List<DailyTipsDTO> findAll() {
        final List<DailyTips> dailyTipses = dailyTipsRepository.findAll(Sort.by("id"));
        return dailyTipses.stream()
                .map(dailyTips -> mapToDTO(dailyTips, new DailyTipsDTO()))
                .toList();
    }

    public DailyTipsDTO get(final Long id) {
        return dailyTipsRepository.findById(id)
                .map(dailyTips -> mapToDTO(dailyTips, new DailyTipsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DailyTipsDTO dailyTipsDTO) {
        final DailyTips dailyTips = new DailyTips();
        mapToEntity(dailyTipsDTO, dailyTips);
        return dailyTipsRepository.save(dailyTips).getId();
    }

    public void update(final Long id, final DailyTipsDTO dailyTipsDTO) {
        final DailyTips dailyTips = dailyTipsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(dailyTipsDTO, dailyTips);
        dailyTipsRepository.save(dailyTips);
    }

    public void delete(final Long id) {
        dailyTipsRepository.deleteById(id);
    }

    private DailyTipsDTO mapToDTO(final DailyTips dailyTips, final DailyTipsDTO dailyTipsDTO) {
        dailyTipsDTO.setId(dailyTips.getId());
        dailyTipsDTO.setTip(dailyTips.getTip());
        dailyTipsDTO.setHeading(dailyTips.getHeading());
        dailyTipsDTO.setType(dailyTips.getType());
        return dailyTipsDTO;
    }

    private DailyTips mapToEntity(final DailyTipsDTO dailyTipsDTO, final DailyTips dailyTips) {
        dailyTips.setTip(dailyTipsDTO.getTip());
        dailyTips.setHeading(dailyTipsDTO.getHeading());
        dailyTips.setType(dailyTipsDTO.getType());
        return dailyTips;
    }

}
