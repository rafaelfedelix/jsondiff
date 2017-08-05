package br.com.fedelix.jsondiff.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "json_right")
public class JsonRightDomain {

    @Id
    public Long id;

    @Column(nullable = false)
    public String encodedJson;
}
