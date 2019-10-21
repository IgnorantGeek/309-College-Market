package org.campusmarket.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.persistence.Table;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.core.style.ToStringCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity
@Table(name = "items")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})


public class Item {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refnum")
    private int refnum;
    @Column(name = "name")
    private String  name;
    @Column(name = "price")
    private double price;
    @Column (name="category")
    private String category;
    @Column (name="cond")
    private String cond;
    
    


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seller", referencedColumnName = "username", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

  
    
    
   public Item() {
	   
   }
   
   public Item(int refnum, String name, double price, String category,String condition, User user) {
	   this.refnum=refnum;
	   this.name=name;
	   this.price=price;
	   this.category=category;
	   this.cond=condition;
	   this.user=user;
	   
   }
   
   public int getRefnum() {
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
   public User getUser() {
	   return this.user;
   }
   
   public void setRefnum(int refnum) {
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
   public void setUser(User user) {
	   this.user=user;
   }
   
   @Override
   public String toString()
   {
	   return new ToStringCreator(this)
			   .append("Refnum",this.getRefnum())
			   .append("Name",this.getName())
			   .append("Price",this.getPrice())
			   .append("Category",this.getCategory())
			   .append("Condition",this.getCondition()).append(System.lineSeparator())
			   .append ("Seller",this.getUser().getUsername()).toString(); 
   }
}
