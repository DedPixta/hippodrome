import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(MockitoExtension.class)
class HippodromeTest {

    @Test
    void constructor_WhenInputNull_ThenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    void constructor_WhenInputNull_ThenMessage() {
        String expected = "Horses cannot be null.";
        String actual = "";

        try {
            new Hippodrome(null);
        } catch (IllegalArgumentException e) {
            actual = e.getMessage();
        }

        assertEquals(expected, actual);
    }

    @Test
    void constructor_WhenInputEmptyList_ThenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
    }

    @Test
    void constructor__WhenInputEmptyList_ThenMessage() {
        String expected = "Horses cannot be empty.";
        String actual = "";

        try {
            new Hippodrome(new ArrayList<>());
        } catch (IllegalArgumentException e) {
            actual = e.getMessage();
        }

        assertEquals(expected, actual);
    }

    @Test
    void getHorses_EqualsWithList_FromConstructor() {
        List<Horse> expected = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            expected.add(new Horse("HorseName", i));
        }
        Hippodrome hippodrome = new Hippodrome(expected);

        List<Horse> actual = hippodrome.getHorses();

        assertEquals(expected, actual);
    }

    @Test
    void move_MovedEachHorse() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Horse horse = Mockito.mock(Horse.class);
            horses.add(horse);
        }
        Hippodrome hippodrome = new Hippodrome(horses);

        hippodrome.move();

        for (Horse horse : horses) {
            Mockito.verify(horse).move();
        }
    }

    @ParameterizedTest
    @CsvSource({
            "10, 20, 30",
            "15, 5, 2",
            "45, 50, 32"})
    void getWinner(double distance1, double distance2, double distance3) {
        List<Horse> horses = new ArrayList<>();
        double speed = 5;
        String name = "HorseName";
        horses.add(new Horse(name, speed, distance1));
        horses.add(new Horse(name, speed, distance2));
        horses.add(new Horse(name, speed, distance3));
        Hippodrome hippodrome = new Hippodrome(horses);

        Horse winner = hippodrome.getWinner();
        double actual = winner.getDistance();
        double expected = Math.max(distance1, Math.max(distance2, distance3));

        assertEquals(expected, actual);
    }
}