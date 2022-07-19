import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

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
    @ValueSource(strings = {"", " ", "   ", " \n"})
    void constructor_WhenHorseNameBlank_ThenThrowException(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, 10, 10));
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
        Horse horse = new Horse(name, 10, 10);

        String actualName = horse.getName();

        assertEquals(name, actualName);
    }

    @ParameterizedTest
    @ValueSource(doubles = {100, 200})
    void getSpeed_WhenNewInstance_ThenReturnSpeedFromConstructor(double speed) {
        Horse horse = new Horse("HorseName", speed, 10);

        double actualSpeed = horse.getSpeed();

        assertEquals(speed, actualSpeed);
    }

    @ParameterizedTest
    @ValueSource(doubles = {100, 200})
    void getDistance_WhenNewInstance_ThenReturnDistanceFromConstructor(double distance) {
        Horse horse = new Horse("HorseName", 10, distance);

        double actualDistance = horse.getDistance();

        assertEquals(distance, actualDistance);
    }

    @Test
    void getDistance_WhenConstructorWithTwoArguments_ThenReturnZeroDistance() {
        Horse horse = new Horse("HorseName", 10);

        double actualDistance = horse.getDistance();

        assertEquals(0, actualDistance);
    }

    @Test
    void move_VerifyInvokeMethod_GetRandomDouble() {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            new Horse("HorseName", 1).move();

            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "10, 5, 0.5",
            "15, 10, 0.8"})
    void move_ChangeDistance(double speed, double distance, double random) {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);
            Horse horse = new Horse("HorseName", speed, distance);
            double expected = distance + speed * random;

            horse.move();
            double actual = horse.getDistance();

            assertEquals(expected, actual);
        }
    }

    @Disabled
    @Test
    void checkLogger() {
        final Logger logger = LogManager.getLogger(HorseTest.class);
        for (int i = 0; i < 1000; i++) {
            logger.debug("Создание Hippodrome, лошадей [{}]", 7);
            logger.info("Начало скачек. Количество участников {}", 6);
            logger.error("Horses list is null");
            logger.fatal("fatal");

        }
    }

}