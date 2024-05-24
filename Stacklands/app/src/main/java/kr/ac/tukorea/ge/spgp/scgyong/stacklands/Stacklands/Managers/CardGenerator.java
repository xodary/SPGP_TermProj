package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

import kr.ac.tukorea.ge.spgp.scgyong.stacklands.R;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.BlackCard;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.BoosterPack;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.Card;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.OrangeCard;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.SilverCard;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.YellowCard;

public class CardGenerator {
    private static CardGenerator instance;

    public static CardGenerator getInstance() {
        if (instance == null) {
            instance = new CardGenerator();
        }
        return instance;
    }
    public HashMap<String, Supplier<Card>> cardSupplier = new HashMap<>();


    public CardGenerator() {
        cardSupplier.put("stone", () -> new SilverCard(R.mipmap.silver_stone, 1, "stone"));
        cardSupplier.put("wood", () -> new SilverCard(R.mipmap.silver_wood, 1, "wood"));
        cardSupplier.put("flint", () -> new SilverCard(R.mipmap.silver_flint, 2, "flint"));
        cardSupplier.put("stick", () -> new SilverCard(R.mipmap.silver_stick, 2, "stick"));
        cardSupplier.put("villager", () -> new YellowCard(R.mipmap.yellow_villager, 15, "villager"));
        cardSupplier.put("berry", () -> new OrangeCard(R.mipmap.orange_berry, 1, 1, "berry"));
        cardSupplier.put("berry_bush", () -> new BlackCard(R.mipmap.black_berry_bush, 1, "berry_bush"));
        cardSupplier.put("boosterPack_ANewWorld", () -> new BoosterPack(R.mipmap.pack_a_new_world, 0, "boosterPack_ANewWorld"));
        cardSupplier.put("boosterPack_CuriousCuisine", () -> new BoosterPack(R.mipmap.pack_curious_cuisine, 3, "boosterPack_ANewWorld"));
    }

    public Card CreateCard(String str) {
        Supplier<Card> supplier = () -> new Card(0, "NoNameCard", "NoColor");
        return cardSupplier.getOrDefault(str, supplier).get();
    }
}

class BoosterPackGenerator {
    protected final HashMap<String, ArrayList<ArrayList<AbstractMap.SimpleEntry<String, Float>>>> boosterpacks = new HashMap<>();
    public BoosterPackGenerator() {
        addCardPack("boosterPack_ANewWorld",
                CreatePairArrayList(new AbstractMap.SimpleEntry<>("wood", 1.0f)),
                CreatePairArrayList(new AbstractMap.SimpleEntry<>("rock", 1.0f)),
                CreatePairArrayList(new AbstractMap.SimpleEntry<>("berry_bush", 1.0f)),
                CreatePairArrayList(new AbstractMap.SimpleEntry<>("villager", 1.0f)),
                CreatePairArrayList(new AbstractMap.SimpleEntry<>("coin", 1.0f)),
                CreatePairArrayList(new AbstractMap.SimpleEntry<>("berry", 1.0f)));

        for (Map.Entry<String, ArrayList<ArrayList<AbstractMap.SimpleEntry<String, Float>>>> entry : boosterpacks.entrySet()) {
            String key = entry.getKey();
            ArrayList<ArrayList<AbstractMap.SimpleEntry<String, Float>>> values = entry.getValue();

            BoosterPack card = (BoosterPack) CardGenerator.getInstance().CreateCard(key);
            for(ArrayList<AbstractMap.SimpleEntry<String, Float>> node : values){
                card.PushCard(CardGenerator.getInstance().CreateCard(selectItemWithProbability(node)));
            }
        }
    }


    private void addCardPack(String key, ArrayList<AbstractMap.SimpleEntry<String, Float>>... images) {
        boosterpacks.put(key, (ArrayList<ArrayList<AbstractMap.SimpleEntry<String, Float>>>) Arrays.asList(images));
    }

    private ArrayList<AbstractMap.SimpleEntry<String, Float>> CreatePairArrayList(AbstractMap.SimpleEntry<String, Float>... datas) {
        return (ArrayList<AbstractMap.SimpleEntry<String, Float>>) Arrays.asList(datas);
    }

    public static String selectItemWithProbability(ArrayList<AbstractMap.SimpleEntry<String, Float>> probabilities) {
        // 각 항목의 누적 확률을 계산합니다.
        ArrayList<Float> cumulativeProbabilities = new ArrayList<>();
        float cumulativeProbability = 0;
        for (AbstractMap.SimpleEntry<String, Float> probability : probabilities) {
            cumulativeProbability += probability.getValue();
            cumulativeProbabilities.add(cumulativeProbability);
        }

        // 0과 1 사이의 임의의 숫자를 생성합니다.
        Random rand = new Random();
        float randomValue = rand.nextFloat();

        // 임의의 숫자가 누적 확률에 속하는 항목을 찾습니다.
        for (int i = 0; i < cumulativeProbabilities.size(); i++) {
            if (randomValue < cumulativeProbabilities.get(i)) {
                return probabilities.get(i).getKey();
            }
        }
        return "Error";
    }
}