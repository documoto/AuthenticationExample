import com.squareup.okhttp.*;

import java.io.IOException;

public class AuthenticationExample {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static Response authenticate(String endpoint, String configuration, String apiKey) throws IOException {
        OkHttpClient client = new OkHttpClient();  // Set up client
        RequestBody body = RequestBody.create(JSON, configuration);  // Set up our request and converting our body to JSON

        // Put together request object
        Request request = new Request.Builder()
                .header("Authorization", apiKey)
                .post(body)
                .url(endpoint).build();
        // Send our request
        Response response = client.newCall(request).execute();
        return response;
    }

    public static void main(String[] args) {
        String endpoint = "https://documoto.digabit.com/api/ext/authorization/widget/token/v1";  // Documoto Production Endpoint
        String apiKey = "API-KEY-HERE";  // Widget API Key
        // Authentication request body example
        String configuration = "{\n" +
                "    \"bindToElementById\": \"documoto-container\",\n" +  // This is the HTML element the Widget will bind to.
                "    \"widgetType\": \"media\",\n" +  // This determines if we are opening a media or the library
                "    \"mediaIdentifier\": \"123456789\",\n" +  // Opens a specific Media Identifier
                "    \"documotoDomain\": \"https://documoto.digabit.com\",\n" +  // This should remain static unless using the Integration environment
                "    \"locale\": \"en-US\",\n" +  // Language the Widget should bind to.
                "    \"enablePartTags\": true,\n" +  // Determines if Part Tags are displayed or hidden.
                "    \"enablePartComments\": true,\n" +  // Determines if Part Comments are displayed or hidden.
                "    \"theme\": {\n" +
                "        \"brand_primary\": \"#d4dfe7\"\n" +  // Determines primary color Widget should be bound to.
                "    }\n" +
                "}";

        // Try to call authentication endpoint
        try {
            Response response = AuthenticationExample.authenticate(endpoint, configuration, apiKey);
            String responseBody = response.body().string();
            System.out.println(responseBody);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
