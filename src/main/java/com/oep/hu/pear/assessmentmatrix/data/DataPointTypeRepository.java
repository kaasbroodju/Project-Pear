package com.oep.hu.pear.assessmentmatrix.data;

import com.oep.hu.pear.assessmentmatrix.domain.criteria.DataPointType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataPointTypeRepository extends JpaRepository<DataPointType, String> {
}
