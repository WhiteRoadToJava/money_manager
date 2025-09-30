package dev.mohammad.mymonymanager.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "tbl_categories")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;

        @Column(name = "created_at", nullable = false, updatable = false)
        @CreationTimestamp
        private LocalDateTime createdAt;

        @UpdateTimestamp
        private LocalDateTime updatedAt;

        private String type;

        private String icon;

        @ManyToOne
        @JoinColumn(name = "profile_id", nullable = false)
        private ProfileEntity profile;


       

}