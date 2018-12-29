package io.murrer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class User {
    private String name;
    private String home;
}
