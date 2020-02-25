package com.example.dto;

import com.example.entity.Type;
import lombok.Data;

@Data
public class FieldDto {
    private Type type;
    private String name;
    private boolean active;
    private boolean required;
    private String text;
    private boolean delete;

    public Type[] getTypes() {
        return Type.values();
    }
}
