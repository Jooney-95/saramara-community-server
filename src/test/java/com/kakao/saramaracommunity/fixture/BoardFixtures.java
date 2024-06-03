package com.kakao.saramaracommunity.fixture;

import com.kakao.saramaracommunity.board.entity.Board;
import com.kakao.saramaracommunity.member.entity.Member;

import java.util.List;

import static com.kakao.saramaracommunity.board.entity.CategoryBoard.VOTE;
import static java.time.LocalDateTime.now;

public class BoardFixtures {

    private static final String 게시글_제목_1 = "집에서 입을 잠옷 어떤 것이 좋을까요?";
    private static final String 게시글_제목_2 = "겨울 다 지났는데 발마칸 코트를 살까요? 말까요?";

    private static final String 게시글_내용_1 = "잠옷 후보 3개 정도를 추려봤는데 골라주세요!";
    private static final String 게시글_내용_2 = "겨울 내내 고민이네요. 여러분들의 귀한 조언을 얻고 싶습니다.";

    private static final List<String> 게시글_사진_목록_1 = List.of("image-1", "image-2", "image-3");

    public static Board 게시글_생성_1(Member member) {
        return Board.builder()
                .title(게시글_제목_1)
                .content(게시글_내용_1)
                .member(member)
                .categoryBoard(VOTE)
                .deadLine(now())
                .images(게시글_사진_목록_1)
                .build();
    }

}
