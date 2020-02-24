package com.example.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "fields")
@NoArgsConstructor
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(mappedBy = "field")
    private List<Option> options;

    private boolean active;
    private boolean required;

    @Transient
    private String optionsText;

    @Column(name = "is_delete")
    private boolean delete;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    public Field(Field field) {
        this.id = field.getId();
        this.name = field.getName();
        this.type = field.getType();
        this.options = field.getOptions();
        this.active = field.isActive();
        this.required = field.isRequired();
        this.delete = field.isDelete();
        this.author = field.getAuthor();
        this.optionsText = field.getOptionsText();
    }
}
