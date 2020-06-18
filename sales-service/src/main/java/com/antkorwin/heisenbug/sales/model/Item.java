package com.antkorwin.heisenbug.sales.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

	@Id
	private String id;
	private int price;
	private Car car;
	private Date createTime;
	private Date startSalesTime;
}
