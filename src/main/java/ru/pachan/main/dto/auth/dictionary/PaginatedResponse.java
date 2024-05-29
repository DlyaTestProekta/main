package ru.pachan.main.dto.auth.dictionary;

import java.util.List;

public record PaginatedResponse<T>(
        long total,
        List<T> result
) {
}
