package com.example.datajpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item implements Persistable<String> {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String  id;

    public Item(String id) {
        this.id = id;
    }

    @CreatedDate
    private LocalDateTime createDate;

    @Override
    public boolean isNew() {
        return createDate == null;
    }
}
