package com.matheus.DigitalAgriculture.dto.response;

import java.util.List;

public record PaginationResponseDTO<T>(List<T> elements, long totalElements, int totalPages) {
}
