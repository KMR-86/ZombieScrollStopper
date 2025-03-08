package com.kmr.ZombieScrollStopper.Service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Pattern;

@Service
public class OllamaPostGeneratorService {
    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";
    private static final HttpClient client = HttpClient.newHttpClient();

    public String queryOllama(String countryName) {
        try {
            JSONObject payload = new JSONObject()
                    .put("model", "deepseek-r1:14b")
                    .put("prompt", createPrompt(countryName))
                    .put("stream", false);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(OLLAMA_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonResponse = new JSONObject(response.body());
            String fullResponse = jsonResponse.getString("response");
            return Pattern.compile("<think>.*?</think>", Pattern.DOTALL)
                    .matcher(fullResponse)
                    .replaceAll("")
                    .trim()
                    // Replace ### with newlines
                    .replace("###", "\n")
                    // Replace all **text** with <strong>text</strong>
                    .replaceAll("\\*\\*(.*?)\\*\\*", "<strong>$1</strong>")
                    // Ensure proper line breaks
                    .replace("\n", "<br/>");
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private String createPrompt(String countryName) {
        return "write a detail post about a interesting place in the " + countryName +
                ". Choose a place that is either historical or interesting or natural." +
                "write it in markdown, use bold and new lines using html tags,make sure that the post is at least 1000 words long." +
                "use this post as an example:" +
                "<strong>Discover the Blue Grotto, Malta – A Natural Wonder</strong><br/>" +
                "Hidden along the southern coast of Malta lies one of the island's most breathtaking natural wonders—the Blue Grotto." +
                "This series of sea caves, shaped by centuries of waves, is famous for its dazzling blue waters that reflect the sunlight, creating an incredible glow.<br/>" +
                "<strong>Why is it special?</strong><br/> The name “Blue Grotto” comes from the intense shades of blue created by underwater light reflection.<br/> The cave system consists of six stunning caves, with the main arch towering over 30 meters high.<br/> Divers and snorkelers love this spot for its crystal-clear waters and rich marine life, including colorful coral and fish.<br/><strong>Interesting Facts</strong><br/>" +
                "The cave’s glowing effect is best seen in the morning, between 9 AM and noon, when the sun perfectly illuminates the water.<br/> The site was featured in movies like *Troy* (2004), starring Brad Pitt.<br/> The Phoenicians, one of the ancient seafaring civilizations, are believed to have used these caves for rituals over 2,000 years ago.<br/><br/>Have you been to the Blue Grotto? It is one of Malta’s must-see locations for anyone who loves nature and adventure.";
    }
}
