package adventofcode.aoc_2021_04;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class Bingo {
    private final List<Map<String, Boolean>> grid = new ArrayList<>();
    private boolean win = false;
    private String winPlay = "";

    public Bingo(List<String> lines) {
        for (String line : lines) {
            Map<String, Boolean> mapLine = new LinkedHashMap<>();
            Arrays.stream(line.split(" ")).filter(s -> !s.isBlank()).forEach(s -> mapLine.put(s, false));
            grid.add(mapLine);
        }
    }

    public void play(String play) {
        for (Map<String, Boolean> line : grid) {
            if (line.containsKey(play)) {
                line.put(play, true);
                win = hasWin(line, play);
                if(win) {
                    winPlay = play;
                }
            }
        }
    }

    private boolean hasWin(Map<String, Boolean> line, String play) {
        if (!line.containsValue(false)) {
            return true;
        } else {
            List<String> keys = new ArrayList<>(line.keySet());
            int index = keys.indexOf(play);
            return !grid.stream().map(stringBooleanMap -> {
                String key = (new ArrayList<>(stringBooleanMap.keySet())).get(index);
                return stringBooleanMap.get(key);
            }).collect(Collectors.toList()).contains(false);
        }
    }

    public List<String> getGridContentByCheck(boolean check) {
        List<String> content = new ArrayList<>();
        grid.forEach(stringBooleanMap ->
                stringBooleanMap.forEach((s, aBoolean) -> {
                    if (check == aBoolean) {
                        content.add(s);
                    }
                })
        );
        return content;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        grid.forEach(stringBooleanMap -> stringBuilder.append(stringBooleanMap).append("\r\n"));
        return stringBuilder.toString();
    }
}
