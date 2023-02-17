package com.member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMemberService {

	public MemberDisplayDto getOneMemeber(Long id) throws MemberNotFoundException;

	public Page<MemberDisplayDto> getMembers(Pageable pageable) throws MemberNotFoundException;

	public void registerMember(MemberCreationDto creationDto);

	public void unregisterOneMember(Long id);

	public void modifyOneMember(MemberModifiedDto dto);
}
