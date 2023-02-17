package com.member;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MemberService implements IMemberService {

	@Autowired
	private MemberRepository memberRepository;

	@Override
	public MemberDisplayDto getOneMemeber(Long id) {
		Optional<Member> optMember = memberRepository.findById(id);
		if (optMember.isEmpty())
			throw new MemberNotFoundException("Could not find the member.");
		return optMember.map(mem -> mem.toMeMemberDisplayDto()).get();
	}

	@Override
	public Page<MemberDisplayDto> getMembers(Pageable pageable) throws MemberNotFoundException {
		Page<Member> pageMembers = memberRepository.findAll(pageable);
		if (pageMembers.isEmpty())
			throw new MemberNotFoundException("Could not find the member.");
		return pageMembers.map(mem -> mem.toMeMemberDisplayDto());
	}

	@Override
	public void registerMember(MemberCreationDto creationDto) {
		memberRepository.save(creationDto.toMember());
	}

	@Override
	public void unregisterOneMember(Long id) {
		boolean isExisted = memberRepository.existsById(id);
		if (isExisted)
			memberRepository.deleteById(id);
		else
			throw new MemberNotFoundException("Could not find the member.");
	}

	@Override
	public void modifyOneMember(MemberModifiedDto dto) {
		Optional<Member> optMember = memberRepository.findById(dto.getId());
		if (optMember.isEmpty())
			throw new MemberNotFoundException("Could not find the member.");
		Member member = optMember.get();
		if (!member.getPassword().equals(dto.getPassword())) {
			member.setPassword(dto.getPassword());
		}
		memberRepository.save(member);
	}

}
