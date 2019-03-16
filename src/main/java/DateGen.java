import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class DateGen {

    public LocalDate randomBirthday(int yearMin, int yearMax) {
        Random random = new Random();
        int minDay = (int) LocalDate.of(yearMin, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(yearMax, 1, 1).toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);

        return LocalDate.ofEpochDay(randomDay);
    }

    public int getAge(LocalDate birthDate){
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public String formatDate(LocalDate localDate){
        return localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

}
