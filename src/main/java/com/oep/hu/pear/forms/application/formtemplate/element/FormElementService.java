package com.oep.hu.pear.forms.application.formtemplate.element;

import com.oep.hu.pear.forms.application.formtemplate.element.impl.*;
import com.oep.hu.pear.forms.domain.element.FormElement;
import com.oep.hu.pear.forms.domain.element.impl.competenceelement.CompetenceElement;
import com.oep.hu.pear.forms.domain.element.impl.datapointelement.DataPointElement;
import com.oep.hu.pear.forms.domain.element.impl.datapointelement.NotApplicableDataPointElement;
import com.oep.hu.pear.forms.domain.element.impl.datapointelement.SelfPickDataPointTypeDataPointElement;
import com.oep.hu.pear.forms.domain.element.impl.datapointtypeelement.DevelopmentActionElement;
import com.oep.hu.pear.forms.domain.element.impl.datapointtypeelement.DevelopmentGoalOnDataPointTypeElement;
import com.oep.hu.pear.forms.domain.element.impl.datapointtypeelement.ReviewableDataPointTypeSelectorElement;
import com.oep.hu.pear.forms.domain.element.impl.durationelement.DurationElement;
import com.oep.hu.pear.forms.domain.element.impl.emotionelement.EmotionElement;
import com.oep.hu.pear.forms.domain.element.impl.fileelement.FileElement;
import com.oep.hu.pear.forms.domain.element.impl.textelement.ReviewableTextElement;
import com.oep.hu.pear.forms.domain.element.impl.textelement.TextElement;
import com.oep.hu.pear.forms.task.exception.ElementNotSupportedException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class FormElementService {

	private TextElementService textElementService;
	private EmotionElementService emotionElementService;
	private DurationElementService durationElementService;
	private DataPointElementService dataPointElementService;
	private SelfPickDataPointTypeDataPointElementService selfPickDataPointTypeDataPointElementService;
	private ReviewableTextElementService reviewableTextElementService;
	private ReviewableDataPointTypeSelectorElementService reviewableDataPointTypeSelectorElementService;
	private CompetenceElementService competenceElementService;
	private NotApplicableDataPointElementService notApplicableDataPointElementService;
	private DevelopmentGoalOnDataPointTypeElementService developmentGoalOnDataPointTypeElementService;
	private DevelopmentActionElementService developmentActionElementService;
	private FileElementService fileElementService;


	private ElementService getCorrespondingServiceOfFormElement(FormElement element) throws ElementNotSupportedException {
		return switch (element) {
			case ReviewableTextElement ignored-> reviewableTextElementService;
			case TextElement ignored -> textElementService;
			case EmotionElement ignored -> emotionElementService;
			case DurationElement ignored-> durationElementService;
			case SelfPickDataPointTypeDataPointElement ignored-> selfPickDataPointTypeDataPointElementService;
			case NotApplicableDataPointElement ignored-> notApplicableDataPointElementService;
			case DataPointElement ignored-> dataPointElementService;
			case ReviewableDataPointTypeSelectorElement ignored-> reviewableDataPointTypeSelectorElementService;
			case CompetenceElement ignored-> competenceElementService;
			case DevelopmentGoalOnDataPointTypeElement ignored-> developmentGoalOnDataPointTypeElementService;
			case DevelopmentActionElement ignored-> developmentActionElementService;
			case FileElement ignored-> fileElementService;

			default -> throw new ElementNotSupportedException(element);
		};
	}

	
	public void setInnerValue(Map<String, Object> dto, FormElement element) throws ElementNotSupportedException {
		this.getCorrespondingServiceOfFormElement(element).setInnerValue(dto, element);
	}

	
	public void setInnerValueReviewer(Map<String, Object> dto, FormElement element) throws ElementNotSupportedException {
		this.getCorrespondingServiceOfFormElement(element).setInnerValueReviewer(dto, element);
	}

	
	public void getInnerValue(Map<String, Object> values, FormElement element) throws ElementNotSupportedException {
		this.getCorrespondingServiceOfFormElement(element).getInnerValue(values, element);

	}

	
	public void getInnerValueReviewer(Map<String, Object> values, FormElement element) throws ElementNotSupportedException {
		this.getCorrespondingServiceOfFormElement(element).getInnerValueReviewer(values, element);

	}
}
