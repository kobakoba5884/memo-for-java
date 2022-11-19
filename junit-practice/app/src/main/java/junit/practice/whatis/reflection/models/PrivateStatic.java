package junit.practice.whatis.reflection.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PrivateStatic {
    @SuppressWarnings("unused")
    private static String privateStaticField;

    public static String publicStaticField;
}
