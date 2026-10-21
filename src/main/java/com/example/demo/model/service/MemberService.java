package com.example.demo.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*; // User, UserDetails 등
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.model.domain.Member;
import com.example.demo.model.dto.MemberForm;
import com.example.demo.model.repository.MemberRepository;

@Service // 서비스 등록
public class MemberService implements UserDetailsService { // 로그인을 위한 인터페이스 제공

    // 관리자가 부여할 수 있는 권한 (화면에서 넘어온 값을 그대로 믿지 않는다)
    // 6주차 연습문제 ② : MANAGER 추가
    private static final List<String> ROLES = List.of("USER", "MANAGER", "ADMIN");

    @Autowired
    private MemberRepository memberRepository;

    @Autowired // SecurityConfig 에 등록한 BCryptPasswordEncoder 주입
    private PasswordEncoder passwordEncoder;

    public Member signup(MemberForm form) { // 회원가입
        // [7주차] JSON 요청에는 HTML 의 required 검사가 없다 → 서버에서 직접 검사
        if (isBlank(form.getUsername()) || isBlank(form.getPassword()) || isBlank(form.getName())) {
            throw new IllegalArgumentException("아이디, 비밀번호, 이름은 필수입니다.");
        }
        // 5주차 연습문제 ② : 비밀번호 확인 검증 (DB 저장 전에 막는다)
        if (form.getPassword() == null || !form.getPassword().equals(form.getPasswordConfirm())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        if (memberRepository.existsByUsername(form.getUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        Member member = new Member();
        member.setUsername(form.getUsername());
        member.setPassword(passwordEncoder.encode(form.getPassword())); // ★ 암호화
        member.setName(form.getName());
        member.setRole("USER"); // 기본 권한
        return memberRepository.save(member); // INSERT
    }

    @Override // 로그인 시 시큐리티가 자동 호출
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("회원 없음 : " + username));

        return User.builder() // 시큐리티용 사용자 객체로 변환
                .username(member.getUsername())
                .password(member.getPassword()) // 암호화 값 → matches()로 비교
                .roles(member.getRole())        // USER → ROLE_USER
                .build();
    }

    // [6주차] 로그인한 아이디로 회원 조회 (내 정보)
    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(
                        "회원이 존재하지 않습니다 : " + username));
    }

    // [6주차] 전체 회원 (id 순서)
    public List<Member> findAll() {
        return memberRepository.findAll(Sort.by("id"));
    }

    // [7주차] 회원 1명 조회 : 없을 수도 있으므로 Optional
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    // [7주차 연습문제 ①] 아이디·이름 검색 (검색어가 없으면 전체)
    public List<Member> search(String keyword) {
        if (isBlank(keyword)) {
            return findAll();
        }
        return memberRepository.findByUsernameContainingOrNameContaining(keyword, keyword);
    }

    @PreAuthorize("hasRole('ADMIN')") // 관리자만 실행 가능 (2차 잠금)
    public Member changeRole(Long id, String role) { // 권한 변경 (바뀐 회원을 돌려준다)
        if (!ROLES.contains(role)) { // 허용된 권한만
            throw new IllegalArgumentException("허용되지 않는 권한입니다 : " + role);
        }
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        checkNotSelf(member); // 6주차 연습문제 ① : 관리자 본인 보호
        member.setRole(role);
        return memberRepository.save(member); // UPDATE (id 가 있으면 수정)
    }

    @PreAuthorize("hasRole('ADMIN')") // 관리자만 실행 가능 (2차 잠금)
    public void deleteMember(Long id) { // 회원 삭제
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        checkNotSelf(member); // 6주차 연습문제 ① : 관리자 본인 보호
        memberRepository.delete(member); // DELETE
    }

    private boolean isBlank(String value) { return value == null || value.isBlank(); }

    // 6주차 연습문제 ① : 대상이 로그인한 본인이면 거부 (관리자 0명 방지)
    private void checkNotSelf(Member target) {
        String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
        if (target.getUsername().equals(loginId)) {
            throw new IllegalArgumentException("자기 자신의 권한 변경·삭제는 할 수 없습니다.");
        }
    }
}
