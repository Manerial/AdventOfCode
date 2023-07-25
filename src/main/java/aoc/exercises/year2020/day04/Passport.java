package aoc.exercises.year2020.day04;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Passport {
    private String id;
    private Integer issueYear;
    private Integer expirationYear;
    private Integer birthYear;
    private String height;
    private String hairColor;
    private String eyesColor;
    private static final List<String> possibleEyesColor = List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
    private static final Pattern ID_PATTERN = Pattern.compile("^\\d{9}$");
    private static final Pattern HEXADECIMAL_PATTERN = Pattern.compile("#\\p{XDigit}+");

    public Passport(String passportStr) {
        // "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd"
        String[] zones = passportStr.split(" ");
        // ["ecl:gry", "pid:860033327", "eyr:2020", "hcl:#fffffd"]
        for (String zone : zones) {
            String[] zoneSplit = zone.split(":");
            String field = zoneSplit[0];
            String value = zoneSplit[1];

            switch (field) {
                case "pid" -> this.id = value;
                case "iyr" -> this.issueYear = Integer.parseInt(value);
                case "eyr" -> this.expirationYear = Integer.parseInt(value);
                case "byr" -> this.birthYear = Integer.parseInt(value);
                case "hgt" -> this.height = value;
                case "hcl" -> this.hairColor = value;
                case "ecl" -> this.eyesColor = value;
                default -> {
                    // Nothing
                }
            }
        }
    }

    public boolean isValid(boolean strongCheck) {
        boolean hasEmptyField = id == null ||
                issueYear == null ||
                expirationYear == null ||
                birthYear == null ||
                height == null ||
                hairColor == null ||
                eyesColor == null;

        if (hasEmptyField) {
            return false;
        } else if (strongCheck) {
            return checkPassportId() &&
                    checkIssueYear() &&
                    checkExpirationYear() &&
                    checkBirthYear() &&
                    checkHeight() &&
                    checkHairColor() &&
                    checkEyesColor();
        }
        return true;
    }

    private boolean checkPassportId() {
        // pid (Passport ID) - a nine-digit number, including leading zeroes.
        Matcher passportIdMatcher = ID_PATTERN.matcher(id);
        return passportIdMatcher.matches();
    }

    private boolean checkIssueYear() {
        // iyr (Issue Year) - four digits; at least 2010 and at most 2020
        return issueYear >= 2010 && issueYear <= 2020;
    }

    private boolean checkExpirationYear() {
        // eyr (Expiration Year) - four digits; at least 2020 and at most 2030
        return expirationYear >= 2020 && expirationYear <= 2030;
    }

    private boolean checkBirthYear() {
        // byr (Birth Year) - four digits; at least 1920 and at most 2002
        return birthYear >= 1920 && birthYear <= 2002;
    }

    private boolean checkHeight() {
        // hgt (Height) - a number followed by either cm or in:
        String heightUnit = height.substring(height.length() - 2);
        String heightValueStr = StringUtils.removeEnd(height, heightUnit);
        int heightValue = NumberUtils.isCreatable(heightValueStr) ? Integer.parseInt(heightValueStr) : 0;
        // If cm, the number must be at least 150 and at most 193. ||
        boolean checkCM = (heightUnit.equals("cm") && (heightValue >= 150 && heightValue <= 193));
        // If in, the number must be at least 59 and at most 76
        boolean checkIN = (heightUnit.equals("in") && (heightValue >= 59 && heightValue <= 76));
        return checkCM || checkIN;
    }

    private boolean checkHairColor() {
        // hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f
        Matcher hairColorMatcher = HEXADECIMAL_PATTERN.matcher(hairColor);
        return hairColorMatcher.matches();
    }

    private boolean checkEyesColor() {
        // ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth
        return possibleEyesColor.contains(eyesColor);
    }
}
