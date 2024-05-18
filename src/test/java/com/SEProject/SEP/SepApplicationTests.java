package com.SEProject.SEP;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class SepApplicationTests {

	@Autowired //의존성 주입으로 테스트
	private IssueRepository issueRepository;

	@Test
	void testJpa() {

		Issue q1 = new Issue();
		q1.setSubject("sdfsdf");
		q1.setContent("sdfsdfsd.");
		q1.setCreateDate(LocalDateTime.now());
		this.issueRepository.save(q1);
	}

}
