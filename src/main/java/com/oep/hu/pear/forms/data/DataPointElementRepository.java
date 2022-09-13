package com.oep.hu.pear.forms.data;

import com.oep.hu.pear.assessmentmatrix.domain.criteria.DataPointType;
import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.domain.element.FormElementId;
import com.oep.hu.pear.forms.domain.element.impl.datapointelement.DataPointElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface DataPointElementRepository extends JpaRepository<DataPointElement, FormElementId> {

	List<DataPointElement> findByFormInAndDataPointTypeOrderByForm_FinishedAtDesc(Collection<Form> forms, DataPointType dataPointType);

}
