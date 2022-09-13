package com.oep.hu.pear.assessmentmatrix.domain.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DataPointType implements Serializable {

	@Id
	private String type;

	private String description;
}
