package com.oep.hu.pear.assessmentmatrix.data;

import com.oep.hu.pear.assessmentmatrix.domain.criteria.DataPoint;
import com.oep.hu.pear.assessmentmatrix.domain.criteria.DataPointId;
import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.domain.FormStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface DataPointRepository extends JpaRepository<DataPoint, DataPointId> {

	// todo new way to get data points
//	List<DataPoint> findByFormInAndForm_FormStatusEquals(Collection<ReviewableForm> forms, FormStatus formStatus);

	List<DataPoint> findByFormInAndForm_FormStatusEquals(Collection<Form> forms, FormStatus formStatus);


//	List<DataPoint> findById_FormInAndId_Form_FormStatusAndId_Skill(Collection<ReviewableForm> forms, FormStatus formStatus, Skill skill);


}
