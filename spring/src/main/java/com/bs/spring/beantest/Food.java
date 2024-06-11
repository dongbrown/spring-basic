package com.bs.spring.beantest;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Food {

	private String name;
	private int price;
	private List<Person> likePerson;
	
}
