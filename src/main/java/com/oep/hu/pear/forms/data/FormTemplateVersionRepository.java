package com.oep.hu.pear.forms.data;

import com.oep.hu.pear.forms.domain.template.FormTemplateLayout;
import com.oep.hu.pear.forms.domain.template.FormTemplateLayoutId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormTemplateVersionRepository extends JpaRepository<FormTemplateLayout, FormTemplateLayoutId> {
}
