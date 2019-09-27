package org.campusmarket.app.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
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
    @Column (name="cond")
    private String cond;
    
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
	   return this.cond;
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
	   this.cond=condition;
   }
   @Override
   public String toString()
   {
       String ret = new String();
       ret = String.format("{Refnum:%1$s}\n{Name:%2$s}\n{Price:%3$d}\n{Category:%4$s}\n{Condition:%5$s}\n\n",
                           this.refnum,
                           this.name,
                           this.price,
                           this.category,
                           this.cond
                           );
       return ret;
   }
}
