package ua.dargunovskiy.util;

import lombok.experimental.UtilityClass;
import ua.dargunovskiy.entity.Founder;

import java.security.SecureRandom;


@UtilityClass
public class FounderSecretCodeGenerator {

    public String secretCodeGenerator(Founder founder) {
        return founder.hashCode() + founder.getUserId().toString();
    }
}
