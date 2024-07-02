package io.bootify.health_hive.service;

import io.bootify.health_hive.domain.Faq;
import io.bootify.health_hive.model.FaqDTO;
import io.bootify.health_hive.repos.FaqRepository;
import io.bootify.health_hive.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class FaqService {

    private final FaqRepository faqRepository;

    public FaqService(final FaqRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    public List<FaqDTO> findAll() {
        final List<Faq> faqs = faqRepository.findAll(Sort.by("id"));
        return faqs.stream()
                .map(faq -> mapToDTO(faq, new FaqDTO()))
                .toList();
    }

    public FaqDTO get(final Long id) {
        return faqRepository.findById(id)
                .map(faq -> mapToDTO(faq, new FaqDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final FaqDTO faqDTO) {
        final Faq faq = new Faq();
        mapToEntity(faqDTO, faq);
        return faqRepository.save(faq).getId();
    }

    public void update(final Long id, final FaqDTO faqDTO) {
        final Faq faq = faqRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(faqDTO, faq);
        faqRepository.save(faq);
    }

    public void delete(final Long id) {
        faqRepository.deleteById(id);
    }

    private FaqDTO mapToDTO(final Faq faq, final FaqDTO faqDTO) {
        faqDTO.setId(faq.getId());
        faqDTO.setQuestion(faq.getQuestion());
        faqDTO.setAnswer(faq.getAnswer());
        return faqDTO;
    }

    private Faq mapToEntity(final FaqDTO faqDTO, final Faq faq) {

        faq.setQuestion(faqDTO.getQuestion());
        faq.setAnswer(faqDTO.getAnswer());
        return faq;
    }

}

