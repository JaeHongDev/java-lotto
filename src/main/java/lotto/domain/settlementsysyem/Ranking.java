package lotto.domain.settlementsysyem;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lotto.domain.money.Money;

public enum Ranking {
    NOT_WINNING(0L, List.of(), ""),
    FIFTH_WINNING(5000L, List.of(new Score(2, 1), new Score(3, 0)), "3개 일치"),
    FOURTH_WINNING(50000L, List.of(new Score(3, 1), new Score(4, 0)), "4개 일치"),
    THIRD_WINNING(1_500_000L, List.of(new Score(4, 1), new Score(5, 0)), "5개 일치"),
    SECOND_WINNING(30_000_000L, List.of(new Score(5, 1)), "5개 일치, 보너스 볼 일치"),
    FIRST_WINNING(2_000_000_000L, List.of(new Score(6, 0)), "6개 일치");

    private final Long prize;
    private final List<Score> score;
    private final String result;

    Ranking(final Long rank, final List<Score> score, final String result) {
        this.prize = rank;
        this.score = score;
        this.result = result;
    }

    public static Ranking calculateScore(Score score) {
        return Arrays.stream(Ranking.values())
                .filter(ranking -> ranking.score.contains(score))
                .findAny()
                .orElse(NOT_WINNING);
    }

    public static Map<Ranking, Integer> generateList() {
        Map<Ranking, Integer> list = new LinkedHashMap<>();
        for (var ranking : Ranking.values()) {
            if (ranking.prize != 0) {
                list.put(ranking, 0);
            }
        }
        return list;
    }

    public String result() {
        String prize = String.valueOf(this.prize)
                .replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");
        return this.result + " (" + prize + "원) ";
    }

    public Money calculateProfits(Integer value) {
        return new Money(String.valueOf(prize * value));
    }
}
