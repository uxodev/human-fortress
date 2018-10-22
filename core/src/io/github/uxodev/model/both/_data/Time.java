package io.github.uxodev.model.both._data;

public class Time {
    // internal yearly time
    private static final int SEASON_ORD = 3;
    private static final int GDAY_PER_GYEAR = 9;
    private static final int GDAY_PER_GSEASON = GDAY_PER_GYEAR / SEASON_ORD;

    // internal movement time
    private static final int METERS_PER_VOXEL = 2;
    private static final int GSEC_PER_STEP = 12;
    public static final int MOVE_SPEED_MOD = 100;
    public static final int WORK_SPEED_MOD = 100;

    // internal bucket time
    public static final int STEP_PER_MIN = 60 / GSEC_PER_STEP;
    public static final int STEP_PER_GDAY = 86400 / GSEC_PER_STEP;
    public static final int STEP_PER_GYEAR = GDAY_PER_GYEAR * STEP_PER_GDAY;

    // external step time and real time
    private static final int RMIN_PER_GDAY = 2;
    public static final int STEP_PER_RSEC = 60;
//    public static final int STEP_PER_RSEC = 1440 / (RMIN_PER_GDAY * GSEC_PER_STEP);

    //    private int epoch;
    private int second;
    private int minute;
    private int hour;
    private int day;
    private int season;
    private int year;

    private SeasonPhase seasonPhase;
    private AnimalPhase animalPhase;
    private SentientPhase sentientPhase;

    public Time(int second, int minute, int hour, int day, int season, int year) {
        this.second = second;
        this.minute = minute;
        this.hour = hour;
        this.day = day;
        this.season = season;
        this.year = year;
        updateSeason();
        updateDayPhases();
    }

    public void step() {
//        epoch++;
        second += GSEC_PER_STEP;
//        if (second >= 60) {
//            second = 0;
//            incMinute();
//        }
        if (second >= 0) {
            second = 0;
            incMinute();
        }
    }

    private void incMinute() {
        minute++;
        if (minute >= 60) {
            minute = 0;
            incHour();
        }
    }

    private void incHour() {
        hour++;
        if (hour >= 24) {
            hour = 0;
            incDay();
        }
        updateDayPhases();
    }

    private void incDay() {
        day++;
        if (day >= GDAY_PER_GSEASON) {
            day = 0;
            incSeason();
        }
    }

    private void incSeason() {
        season++;
        if (season >= SEASON_ORD) {
            season = 0;
            incYear();
        }
        updateSeason();
    }

    private void incYear() {
        year++;
    }

    private void updateSeason() {
        seasonPhase = SeasonPhase.values()[season];
    }

    private void updateDayPhases() {
        if (hour >= 3 && hour < 7) animalPhase = AnimalPhase.DAWN;
        else if (hour >= 7 && hour < 17) animalPhase = AnimalPhase.LIGHT;
        else if (hour >= 17 && hour < 21) animalPhase = AnimalPhase.DUSK;
        else animalPhase = AnimalPhase.DARK;

        if (hour >= 4 && hour < 6) sentientPhase = SentientPhase.MORNING;
        else if (hour >= 6 && hour < 12) sentientPhase = SentientPhase.DAYTIME;
        else if (hour >= 12 && hour < 16) sentientPhase = SentientPhase.AFTERNOON;
        else if (hour >= 16 && hour < 20) sentientPhase = SentientPhase.EVENING;
        else sentientPhase = SentientPhase.NIGHT;
    }

    public int getSecond() {
        return second;
    }

    public int getMinute() {
        return minute;
    }

    public int getHour() {
        return hour;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }

    public SeasonPhase getSeasonPhase() {
        return seasonPhase;
    }

    public AnimalPhase getAnimalPhase() {
        return animalPhase;
    }

    public SentientPhase getSentientPhase() {
        return sentientPhase;
    }

    public enum SeasonPhase {
        SPRING, SUMMER, AUTUMN, WINTER,
    }

    // light phase
    public enum AnimalPhase {
        DAWN, LIGHT, DUSK, DARK,
    }

    public enum SentientPhase {
        MORNING, DAYTIME, AFTERNOON, EVENING, NIGHT,
    }

    @Override
    public String toString() {
        return "Time:" + "\n" +
                "seasonPhase=" + seasonPhase + "\n" +
                "animalPhase=" + animalPhase + "\n" +
                "sentientPhase=" + sentientPhase + "\n" +
                "year=" + year + "\n" +
                "season=" + season + "\n" +
                "day=" + day + "\n" +
                "hour=" + hour + "\n" +
                "minute=" + minute +
//                "second=" + second +
//                "epoch=" + epoch +
                "";
    }
}
