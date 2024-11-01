package com.joshuadias.moneyplannerapi.domains.gemini.models;

import com.joshuadias.moneyplannerapi.domains.shared.base.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(schema = "gemini", name = "context_message")
public class ContextMessage extends BaseModel {

    @Column(name = "text", nullable = false)
    private String text;
}
