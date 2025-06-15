package org.mrpaulwoods.sec06.assignment;

public record Order(
        String item,
        String category,
        Integer price,
        Integer quantity
) {
    static Order create(String item) {
        String[] fields = item.split(":");
        if (4 != fields.length) {
            throw new RuntimeException("Invalid input: " + item);
        }

        return new Order(
                fields[0],
                fields[1],
                Integer.parseInt(fields[2], 10),
                Integer.parseInt(fields[3], 10)
        );

    }

}
