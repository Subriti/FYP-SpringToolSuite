package com.example.project.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonPropertyOrder({"cloth_id, clothes_category_id, item_category_id, cloth_size, cloth_condition, cloth_season"})

@Entity
@Table(name = "Clothes")
public class Clothes implements Serializable{
	private static final long serialVersionUID = -4132868616593747054L;

	@Id
	@SequenceGenerator(
            name = "clothes_sequence",
            sequenceName = "clothes_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "clothes_sequence"
    )
	
    @Column(name = "cloth_id")
    private int clothId;

	@OneToOne
    @JoinColumn(name = "clothes_category_id", referencedColumnName = "category_id")
    private Category clothesCategoryId;
	
	@OneToOne
    @JoinColumn(name = "item_category_id", referencedColumnName = "category_id")
    private Category itemCategoryId;
    
	
    @Column(name = "cloth_size")
    private String clothSize;
    
    @Column(name = "cloth_condition")
    private String clothCondition;
    
    @Column(name = "cloth_season")
    private String clothSeason;
    
  
	public Clothes() {
		super();
	}

    public Clothes(int clothId, Category clothesCategoryId, Category itemCategoryId, String clothSize,
                String clothCondition, String clothSeason) {
            super();
            this.clothId = clothId;
            this.clothesCategoryId = clothesCategoryId;
            this.itemCategoryId = itemCategoryId;
            this.clothSize = clothSize;
            this.clothCondition = clothCondition;
            this.clothSeason = clothSeason;
        }

    public Clothes(Category clothesCategoryId, Category itemCategoryId, String clothSize, String clothCondition,
            String clothSeason) {
        super();
        this.clothesCategoryId = clothesCategoryId;
        this.itemCategoryId = itemCategoryId;
        this.clothSize = clothSize;
        this.clothCondition = clothCondition;
        this.clothSeason = clothSeason;
    }

    @JsonGetter("cloth_id")
	public int getClothId() {
		return clothId;
	}

    @JsonSetter("cloth_id")
	public void setClothId(int clothId) {
		this.clothId = clothId;
	}

    @JsonGetter("clothes_category_id")
    public Category getClothesCategoryId() {
        return clothesCategoryId;
    }

    @JsonSetter("clothes_category_id")
    public void setClothesCategoryId(Category clothesCategoryId) {
        this.clothesCategoryId = clothesCategoryId;
    }

    @JsonGetter("item_category_id")
    public Category getItemCategoryId() {
        return itemCategoryId;
    }

    @JsonSetter("item_category_id")
    public void setItemCategoryId(Category itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }

    @JsonGetter("cloth_size")
    public String getClothSize() {
        return clothSize;
    }

    @JsonSetter("cloth_size")
    public void setClothSize(String clothSize) {
        this.clothSize = clothSize;
    }

    @JsonGetter("cloth_condition")
    public String getClothCondition() {
        return clothCondition;
    }

    @JsonSetter("cloth_condition")
    public void setClothCondition(String clothCondition) {
        this.clothCondition = clothCondition;
    }

    @JsonGetter("cloth_season")
    public String getClothSeason() {
        return clothSeason;
    }

    @JsonSetter("cloth_season")
    public void setClothSeason(String clothSeason) {
        this.clothSeason = clothSeason;
    }
}
