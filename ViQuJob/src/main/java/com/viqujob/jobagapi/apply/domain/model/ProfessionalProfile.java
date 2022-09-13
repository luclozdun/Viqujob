package com.viqujob.jobagapi.apply.domain.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.viqujob.jobagapi.ability.domain.model.Languages;
import com.viqujob.jobagapi.ability.domain.model.Skill;
import com.viqujob.jobagapi.ability.domain.model.Studies;
import com.viqujob.jobagapi.security.domain.model.Postulant;

import java.util.List;

@Entity
@Table(name = "professional_profiles")
public class ProfessionalProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotNull
    @Length(max = 100)
    private String ocupation;

    private String video;

    @NotNull
    @Length(max = 100)
    private String description;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "postulant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Postulant postulant;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "professionalprofile_skills", joinColumns = {
            @JoinColumn(name = "professionalprofile_id") }, inverseJoinColumns = { @JoinColumn(name = "skills_id") })
    private List<Skill> skills;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "professionalprofile_languages", joinColumns = {
            @JoinColumn(name = "professionalprofile_id") }, inverseJoinColumns = { @JoinColumn(name = "languages_id") })
    private List<Languages> languages;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "professionalprofile_studies", joinColumns = {
            @JoinColumn(name = "professionalprofile_id") }, inverseJoinColumns = { @JoinColumn(name = "studies_id") })
    private List<Studies> studies;

    public ProfessionalProfile(Long id, String ocupation, String video, String description, Postulant postulant,
            Studies studies, Skill skill, Languages languages) {
        this.id = id;
        this.ocupation = ocupation;
        this.video = video;
        this.description = description;
        this.postulant = postulant;
    }

    public ProfessionalProfile() {

    }

    public Long getId() {
        return id;
    }

    public ProfessionalProfile setId(Long id) {
        this.id = id;
        return this;
    }

    public String getOcupation() {
        return ocupation;
    }

    public ProfessionalProfile setOcupation(String ocupation) {
        this.ocupation = ocupation;
        return this;
    }

    public String getVideo() {
        return video;
    }

    public ProfessionalProfile setVideo(String video) {
        this.video = video;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProfessionalProfile setDescription(String description) {
        this.description = description;
        return this;
    }

    public Postulant getPostulant() {
        return postulant;
    }

    public ProfessionalProfile setPostulant(Postulant postulant) {
        this.postulant = postulant;
        return this;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public ProfessionalProfile setSkills(List<Skill> skills) {
        this.skills = skills;
        return this;
    }

    public boolean hasSkill(Skill skill) {
        return this.getSkills().contains(skill);
    }

    public ProfessionalProfile addSkill(Skill skill) {
        if (!hasSkill(skill)) {
            this.getSkills().add(skill);
        }
        return this;
    }

    public ProfessionalProfile removeSkill(Skill skill) {
        if (this.hasSkill(skill))
            this.getSkills().remove(skill);
        return this;
    }

    public boolean hasStudies(Studies studies) {
        return this.getStudies().contains(studies);
    }

    public ProfessionalProfile addStudies(Studies studies) {
        if (!hasStudies(studies)) {
            this.getStudies().add(studies);
        }
        return this;
    }

    public ProfessionalProfile removeStudies(Studies studies) {
        if (this.hasStudies(studies))
            this.getStudies().remove(studies);
        return this;
    }

    public boolean hasLanguages(Languages languages) {
        return this.getLanguages().contains(languages);
    }

    public ProfessionalProfile addLanguages(Languages languages) {
        if (!hasLanguages(languages)) {
            this.getLanguages().add(languages);
        }
        return this;
    }

    public ProfessionalProfile removeLanguages(Languages languages) {
        if (this.hasLanguages(languages))
            this.getLanguages().remove(languages);
        return this;
    }

    public List<Languages> getLanguages() {
        return languages;
    }

    public ProfessionalProfile setLanguages(List<Languages> languages) {
        this.languages = languages;
        return this;
    }

    public List<Studies> getStudies() {
        return studies;
    }

    public ProfessionalProfile setStudies(List<Studies> studies) {
        this.studies = studies;
        return this;
    }
}
