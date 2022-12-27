package com.example.project.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonPropertyOrder({"type_id, type_name"})

@Entity
@Table(name = "Type")
public class Type implements Serializable{
	
	private static final long serialVersionUID = -1244383128993878686L;

	@Id
	@SequenceGenerator(
            name = "type_sequence",
            sequenceName = "type_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "type_sequence"
    )
   
	
	@Column(name = "type_id")
	private int typeId;

	@Column(name = "type_name")
	private String typeName;

	public Type() {

	}

	public Type(int typeId, String typeName) {
		super();
		this.typeId = typeId;
		this.typeName = typeName;
	}

	public Type(String typeName) {
		super();
		this.typeName = typeName;
	}

	@JsonGetter("type_id")
	public int getTypeId() {
		return typeId;
	}

	@JsonSetter("type_id")
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	@JsonGetter("type_name")
	public String getTypeName() {
		return typeName;
	}

	@JsonSetter("type_name")
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


}
