package com.viqujob.jobagapi.result.domain.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.viqujob.jobagapi.general.domain.model.AuditModel;
import com.viqujob.jobagapi.security.domain.model.Employer;
import com.viqujob.jobagapi.security.domain.model.Postulant;

@Entity
@Table(name = "mailmessages")
public class MailMessage extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Relacion con postulante
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_postulant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Postulant postulant;

    // Relacion con el empleador
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_employeer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Employer employeer;

    @NotNull
    private String message;

    @NotNull
    @Size(max = 40)
    private String document_Link;

    public MailMessage() {
    }

    public MailMessage(Long id, String message, String document_Link) {
        this.id = id;
        this.message = message;
        this.document_Link = document_Link;
    }

    public Long getId() {
        return id;
    }

    public MailMessage setId(Long id) {
        this.id = id;
        return this;
    }

    public Postulant getPostulant() {
        return postulant;
    }

    public MailMessage setPostulant(Postulant postulant) {
        this.postulant = postulant;
        return this;
    }

    public Employer getEmployeer() {
        return employeer;
    }

    public MailMessage setEmployeer(Employer employeer) {
        this.employeer = employeer;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public MailMessage setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getDocument_Link() {
        return document_Link;
    }

    public MailMessage setDocument_Link(String document_Link) {
        this.document_Link = document_Link;
        return this;
    }
}
