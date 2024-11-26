import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// Клас для літери
class Letter {
    private final char character;

    public Letter(char character) {
        this.character = character;
    }


    @Override
    public String toString() {
        return Character.toString(character);
    }
}

// Клас для слова
class Word {
    private final List<Letter> letters;

    public Word(String word) {
        letters = new ArrayList<>();
        for (char ch : word.toCharArray()) {
            letters.add(new Letter(ch));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Letter letter : letters) {
            sb.append(letter);
        }
        return sb.toString();
    }
}

// Клас для розділового знака
class Punctuation {
    private final char symbol;

    public Punctuation(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return Character.toString(symbol);
    }
}

// Клас для речення
class Sentence {
    private final List<Object> elements; // Слова і розділові знаки

    public Sentence(String sentence) {
        elements = new ArrayList<>();
        String[] parts = sentence.split("(?=[.,!?])|\\s+");
        for (String part : parts) {
            if (part.matches("[.,!?]")) {
                elements.add(new Punctuation(part.charAt(0)));
            } else if (!part.isBlank()) {
                elements.add(new Word(part));
            }
        }
    }

    public int getWordCount() {
        return (int) elements.stream().filter(e -> e instanceof Word).count();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Object element : elements) {
            sb.append(element);
            if (element instanceof Word) {
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }
}

// Клас для тексту
class Text {
    private final List<Sentence> sentences;

    public Text(String text) {
        sentences = new ArrayList<>();
        String[] sentenceArray = text.split("(?<=[.?!])");
        for (String sentence : sentenceArray) {
            if (!sentence.isBlank()) {
                sentences.add(new Sentence(sentence.trim()));
            }
        }
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Sentence sentence : sentences) {
            sb.append(sentence).append(" ");
        }
        return sb.toString().trim();
    }
}

// Основний клас з виконавчим методом
public class Lab4 {

    public static void main(String[] args) {
        try {
            // Вхідний текст
            String inputText = "First day at first school. Second lesson of the week of the month. " +
                    "Third hour of school.";

            // Очищення тексту: заміна табуляцій і пробілів одним пробілом
            inputText = inputText.replaceAll("\\s+", " ");

            // Створення тексту
            Text text = new Text(inputText);

            // Сортування речень за кількістю слів
            List<Sentence> sortedSentences = text.getSentences();
            sortedSentences.sort(Comparator.comparingInt(Sentence::getWordCount));

            // Вивід результату
            System.out.println("Речення в порядку зростання кількості слів:");
            for (Sentence sentence : sortedSentences) {
                System.out.println(sentence );
            }

        } catch (Exception e) {
            System.out.println("Виникла помилка: " + e.getMessage());
        }
    }
}
