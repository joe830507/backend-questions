package com.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.common.ApiResult;

@RestController
@RequestMapping("/members")
public class MemberController {

	@Autowired
	private IMemberService memberService;

	@GetMapping("/{id}")
	public ResponseEntity<ApiResult> getOneMemeber(@PathVariable("id") Long id) {
		try {
			MemberDisplayDto dto = memberService.getOneMemeber(id);
			return ResponseEntity.ok(ApiResult.ok(dto));
		} catch (MemberNotFoundException e) {
			return ResponseEntity.badRequest().body(ApiResult.error(e.getMessage()));
		}
	}

	@GetMapping
	public ResponseEntity<ApiResult> getMembers(MemberQueryDto queryDto) {
		PageRequest pageRequest = PageRequest.of(queryDto.getPage(), queryDto.getSize());
		try {
			Page<MemberDisplayDto> result = memberService.getMembers(pageRequest);
			return ResponseEntity.ok(ApiResult.ok(result));
		} catch (MemberNotFoundException e) {
			return ResponseEntity.badRequest().body(ApiResult.error(e.getMessage()));
		}
	}

	@PostMapping
	public ResponseEntity<ApiResult> registerMember(@RequestBody MemberCreationDto creationDto) {
		memberService.registerMember(creationDto);
		return ResponseEntity.ok(ApiResult.ok("new member created"));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResult> unregisterOneMember(@PathVariable("id") Long id) {
		try {
			memberService.unregisterOneMember(id);
			return ResponseEntity.ok(ApiResult.ok("the memeber was unregistered"));
		} catch (MemberNotFoundException e) {
			return ResponseEntity.badRequest().body(ApiResult.error(e.getMessage()));
		}
	}

	@PutMapping
	public ResponseEntity<ApiResult> modifyOneMember(@RequestBody MemberModifiedDto modifiedDto) {
		try {
			memberService.modifyOneMember(modifiedDto);
			return ResponseEntity.ok(ApiResult.ok("the member information updated"));
		} catch (MemberNotFoundException e) {
			return ResponseEntity.badRequest().body(ApiResult.error(e.getMessage()));
		}
	}
}
