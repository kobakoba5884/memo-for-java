package pure.java.faker;

import java.util.Locale;
import java.util.stream.IntStream;

import com.github.javafaker.Faker;

public class UserData {

    public static void main(String[] args) {

        Faker faker = new Faker(Locale.getDefault());

        IntStream.range(0, 10).forEach(i -> {
            System.out.println(faker.name().fullName());
        });
    }
}
