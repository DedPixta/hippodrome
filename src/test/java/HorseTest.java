import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

class HorseTest {

    @Test
    void constructor_WhenHorseNameNull_ThenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 10, 10));
    }

    @Test
    void constructor_WhenHorseNameNull_ThenMessage() {
        String expected = "Name cannot be null.";
        String actual = "";

        try {
            new Horse(null, 10, 10);
        } catch (IllegalArgumentException e) {
            actual = e.getMessage();
        }

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void constructor_WhenHorseNameBlank_ThenThrowException(String text) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(text.trim(), 10, 10));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void constructor_WhenHorseNameBlank_ThenMessage(String text) {
        String expected = "Name cannot be blank.";
        String actual = "";

        try {
            new Horse(text.trim(), 10, 10);
        } catch (IllegalArgumentException e) {
            actual = e.getMessage();
        }

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, -10, -100})
    void constructor_WhenHorseSpeedNegative_ThenThrowException(double speed) {
        assertThrows(IllegalArgumentException.class, () -> new Horse("HorseName", speed, 10));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, -10, -100})
    void constructor_WhenHorseSpeedNegative_ThenMessage(double speed) {
        String expected = "Speed cannot be negative.";
        String actual = "";

        try {
            new Horse("HorseName", speed, 10);
        } catch (IllegalArgumentException e) {
            actual = e.getMessage();
        }

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, -10, -100})
    void constructor_WhenHorseDistanceNegative_ThenThrowException(double distance) {
        assertThrows(IllegalArgumentException.class, () -> new Horse("HorseName", 10, distance));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, -10, -100})
    void constructor_WhenHorseDistanceNegative_ThenMessage(double distance) {
        String expected = "Distance cannot be negative.";
        String actual = "";

        try {
            new Horse("HorseName", 10, distance);
        } catch (IllegalArgumentException e) {
            actual = e.getMessage();
        }

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Maria, Vanya"})
    void getName_WhenNewInstance_ThenReturnNameFromConstructor(String name) {
        String actual;
        try {
            Horse horse = new Horse(name, 10, 10);
            actual = horse.getName();

        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }

        assertEquals(name, actual);
    }

    @ParameterizedTest
    @ValueSource(doubles = {100, 200})
    void getSpeed_WhenNewInstance_ThenReturnSpeedFromConstructor(double speed) {
        double actual;
        try {
            Horse horse = new Horse("HorseName", speed, 10);
            actual = horse.getSpeed();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }

        assertEquals(speed, actual);
    }

    @ParameterizedTest
    @ValueSource(doubles = {100, 200})
    void getDistance_WhenNewInstance_ThenReturnDistanceFromConstructor(double distance) {
        double actual;
        try {
            Horse horse = new Horse("HorseName", 10, distance);
            actual = horse.getDistance();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }

        assertEquals(distance, actual);
    }

    @Test
    void getDistance_WhenConstructorWithTwoArguments_ThenReturnZeroDistance() {
        double actual;
        try {
            Horse horse = new Horse("HorseName", 10);
            actual = horse.getDistance();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }

        assertEquals(0, actual);
    }

    @ParameterizedTest
    @CsvSource({
            "10, 5, 0.5",
            "15, 10, 0.8"})
    void move_VerifyInvokeMethod_GetRandomDouble(double speed, double distance, double value) {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(value);
            Horse horse = new Horse("HorseName", speed, distance);
            double expected = distance + speed * value;

            horse.move();
            double actual = horse.getDistance();

            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
            assertEquals(expected, actual);
        }
    }

    @Test
    void checkLogger(){
        final Logger logger = LogManager.getLogger(HorseTest.class);
        for (int i = 0; i < 1000; i++) {
        logger.debug("Создание Hippodrome, лошадей [{}]", 7);
        logger.info("Начало скачек. Количество участников {}",6);
        logger.error("Horses list is null");
        logger.fatal("fatal");

        }
    }

}