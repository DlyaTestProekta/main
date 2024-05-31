package ru.pachan.main.service.writer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.pachan.main.dto.writer.WriterDto;

@RequiredArgsConstructor
@Service
public class IncreaseCount {

    private final KafkaTemplate<String, WriterDto> kafkaTemplate;
    private final String topicName;

    @Scheduled(initialDelay = 2000, fixedDelay = 1000)
    public void increaseCount() {
        kafkaTemplate.send(topicName, new WriterDto(1L, 1));
    }
}
