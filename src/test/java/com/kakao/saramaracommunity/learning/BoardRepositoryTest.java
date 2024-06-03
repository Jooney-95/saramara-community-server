package com.kakao.saramaracommunity.learning;

import com.kakao.saramaracommunity.board.entity.Board;
import com.kakao.saramaracommunity.board.repository.BoardRepository;
import com.kakao.saramaracommunity.member.entity.Member;
import com.kakao.saramaracommunity.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.List;

import static com.kakao.saramaracommunity.fixture.BoardFixtures.게시글_생성_1;
import static com.kakao.saramaracommunity.fixture.MemberFixture.NORMAL_MEMBER_LANGO;
import static com.kakao.saramaracommunity.fixture.MemberFixture.NORMAL_MEMBER_SONNY;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BoardRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardRepository boardRepository;

    private Member 사용자_랑고;
    private Member 사용자_소니;

    @BeforeEach
    void setUp() {
        사용자_랑고 = createMember(NORMAL_MEMBER_LANGO.createMember());
        사용자_소니 = createMember(NORMAL_MEMBER_SONNY.createMember());
        for(int i=0; i<20; i++) {
            boardRepository.saveAll(List.of(
                    게시글_생성_1(사용자_랑고),
                    게시글_생성_1(사용자_소니)
            ));
        }
    }

    @Test
    @DisplayName("Pageable을 통해 0페이지부터 10개 단위로 게시글 목록을 조회한다.")
    void requestPageableTest() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        // when
        Page<Board> result = boardRepository.findAll(pageable);
        // then
        assertThat(result.getTotalPages()).isEqualTo(4);
        assertThat(result.getTotalElements()).isEqualTo(40);
    }

    @Test
    @DisplayName("Pageable을 통해 0페이지부터 10개 단위로 생성일시 순으로 정렬된 게시글 목록을 조회할 수 있다.")
    void requestPageableSortTest() {
        // given
        Sort sortCondition1 = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(0, 10, sortCondition1);
        // when
        Page<Board> boardPageList = boardRepository.findAll(pageable);
        List<Board> result = boardPageList.getContent();
        // then
        assertThat(result).isSortedAccordingTo(Comparator.comparing(Board::getCreatedAt).reversed());
    }

    private Member createMember(Member member) {
        return memberRepository.save(member);
    }
}
