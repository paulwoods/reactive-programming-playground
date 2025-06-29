package org.mrpaulwoods.sec10.assignment.buffer;

import java.time.OffsetDateTime;
import java.util.Map;

public record RevenueReport(
        OffsetDateTime timestamp,
        Map<String, Integer> categoryRevenue
) {
}
