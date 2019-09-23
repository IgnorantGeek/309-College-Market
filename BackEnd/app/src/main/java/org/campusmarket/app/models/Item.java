package org.campusmarket.app.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "items")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})


public class Item {
	@Id
    @Column(name = "refnum")
    private String refnum;
    @Column(name = "name")
    private String  name;
    @Column(name = "price")
    private double price;
    @Column (name="category")
    private String category;
    @Column (name="condition")
    private String condition;
    
   public Item() {
	   
   }
    
   public String getRefnum() {
	   return this.refnum;
   }
    
   public String getName() {
	   return this.name;
   }
   public double getPrice() {
	   return this.price;
   }
   
   public String getCategory() {
	   return this.category;
   }
   public String getCondition() {
	   return this.condition;
   }
   
   public void setRefnum(String refnum) {
	   this.refnum=refnum;
   }
   public void setName(String name) {
	   this.name=name;
   }
   
   public void setPrice(double price) {
	   this.price=price;
   }
   
   public void setCategory(String category) {
	   this.category=category;
   }
   public void setCondition(String condition) {
	   this.condition=condition;
   }
   
}
