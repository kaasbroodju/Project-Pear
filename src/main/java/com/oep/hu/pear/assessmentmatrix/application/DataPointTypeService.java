package com.oep.hu.pear.assessmentmatrix.application;

import com.oep.hu.pear.assessmentmatrix.data.DataPointTypeRepository;
import com.oep.hu.pear.assessmentmatrix.domain.criteria.DataPointType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DataPointTypeService {

	private DataPointTypeRepository repository;

	public DataPointType create(String type, String description) {
		return this.repository.save(new DataPointType(type, description));
	}

	public List<String> getAllTypes() {
		return this.repository.findAll().stream().map(DataPointType::getType).toList();
	}

	public DataPointType getDataPointType(String type) {
		return this.repository.getReferenceById(type);
	}

	public boolean doesDataPointTypeExists(String type) {
		return this.repository.existsById(type);
	}
}
