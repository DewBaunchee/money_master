package by.varyvoda;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        var gson = new Gson();
        var result = new JsonObject();

        var jsonObject = gson.fromJson(new FileReader("assets/raw_google_icons_names.json"), JsonObject.class);
        jsonObject.keySet().forEach(categoryName -> {
            var resultCategory = new JsonArray();

            jsonObject.getAsJsonArray(categoryName).asList().stream()
                .skip(1)
                .forEach(iconName -> {
                    var rawIconName = iconName.getAsString();

                    String[] words = rawIconName.split("_");
                    String programmaticName = Arrays.stream(words)
                        .map(Main::firstCapitalized)
                        .collect(Collectors.joining());
                    String uiName = firstCapitalized(String.join(" ", words));

                    var categoryContent = new JsonArray();
                    categoryContent.add(programmaticName);
                    categoryContent.add(uiName);

                    resultCategory.add(categoryContent);
                });

            result.add(categoryName, resultCategory);
        });

        Files.writeString(Path.of("assets/google_icons_names.json"), gson.toJson(result));
    }

    private static String firstCapitalized(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
}