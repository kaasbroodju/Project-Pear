package com.oep.hu.pear.users.application;

import com.oep.hu.pear.users.data.ApprenticeRepository;
import com.oep.hu.pear.users.domain.Apprentice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ApprenticeService {

	private ApprenticeRepository apprenticeRepository;

	public List<Apprentice> getAll() {
		return this.apprenticeRepository.findAll();
	}
}
