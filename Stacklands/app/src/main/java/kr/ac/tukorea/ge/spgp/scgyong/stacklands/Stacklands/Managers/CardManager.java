package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.Card;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.BoosterPack;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.R;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.SilverCard;

public class CardManager {
    private static CardManager instance;


    public static CardManager getInstance() {
        if (instance == null) {
            instance = new CardManager();
        }
        return instance;
    }
    public HashMap<String, Supplier<Card>> cardSupplier = new HashMap<>();
    public CardManager() {
        cardSupplier.put("stone", () -> new SilverCard(R.mipmap.silver_stone, 1));
        cardSupplier.put("wood", () -> new SilverCard(R.mipmap.silver_wood, 1));
        cardSupplier.put("flint", () -> new SilverCard(R.mipmap.silver_flint, 2));
        cardSupplier.put("stick", () -> new SilverCard(R.mipmap.silver_stick, 2));
        cardSupplier.put("boosterPack_ANewWorld", () -> new BoosterPack(R.mipmap.pack_a_new_world, 0));
        cardSupplier.put("boosterPack_CuriousCuisine", () -> new BoosterPack(R.mipmap.pack_curious_cuisine, 3));
    }

    public Card CreateCard(String str) {
        Supplier<Card> supplier = () -> new Card(0);
        return cardSupplier.getOrDefault(str, supplier).get();
    }
}

class Pair {
    private String name;
    private Float rate;

    public Pair(String first, Float second) {
        this.name = first;
        this.rate = second;
    }

    public String getName() {
        return name;
    }

    public Float getRate() {
        return rate;
    }
}

class BoosterPackManager {
    protected final HashMap<String, ArrayList<ArrayList<Pair>>> boosterpacks = new HashMap<>();
    public BoosterPackManager() {
        addCardPack("boosterPack_ANewWorld",
                CreatePairArrayList(new Pair("wood", 1.0f)),
                CreatePairArrayList(new Pair("rock", 1.0f)),
                CreatePairArrayList(new Pair("berry_bush", 1.0f)),
                CreatePairArrayList(new Pair("villager", 1.0f)),
                CreatePairArrayList(new Pair("coin", 1.0f)));

        for (Map.Entry<String, ArrayList<ArrayList<Pair>>> entry : boosterpacks.entrySet()) {
            String key = entry.getKey();
            ArrayList<ArrayList<Pair>> values = entry.getValue();

            BoosterPack card = (BoosterPack) CardManager.getInstance().CreateCard(key);
            for(ArrayList<Pair> node : values){
                card.PushCard(CardManager.getInstance().CreateCard(selectItemWithProbability(node)));
            }
        }
    }

    private void addCardPack(String key, ArrayList<Pair>... images) {
        boosterpacks.put(key, (ArrayList<ArrayList<Pair>>) Arrays.asList(images));
    }

    private ArrayList<Pair> CreatePairArrayList(Pair... datas) {
        return (ArrayList<Pair>) Arrays.asList(datas);
    }

    public static String selectItemWithProbability(ArrayList<Pair> probabilities) {
        // 각 항목의 누적 확률을 계산합니다.
        ArrayList<Float> cumulativeProbabilities = new ArrayList<>();
        float cumulativeProbability = 0;
        for (Pair probability : probabilities) {
            cumulativeProbability += probability.getRate();
            cumulativeProbabilities.add(cumulativeProbability);
        }

        // 0과 1 사이의 임의의 숫자를 생성합니다.
        Random rand = new Random();
        float randomValue = rand.nextFloat();

        // 임의의 숫자가 누적 확률에 속하는 항목을 찾습니다.
        for (int i = 0; i < cumulativeProbabilities.size(); i++) {
            if (randomValue < cumulativeProbabilities.get(i)) {
                return probabilities.get(i).getName();
            }
        }
        return "Error";
    }
}