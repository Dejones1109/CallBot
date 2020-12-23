package com.its.sanve.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author quangdt
 */

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionToken {
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name ="token")
    @JsonProperty("tokenKey")
    String token;
    @Column(name ="created_at")
    LocalDate createdAt;
    @Column(name = "updated_at")
    LocalDate updatedAt;

    public TransactionToken(String token, LocalDate createdAt, LocalDate updatedAt) {
        this.token = token;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}