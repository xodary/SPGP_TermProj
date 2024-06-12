package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers;

import java.util.HashMap;
import java.util.function.Supplier;

import kr.ac.tukorea.ge.spgp.scgyong.stacklands.R;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.BlackCard;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.BoosterPack;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.Card;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.CoinCard;
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
        cardSupplier.put("silver_stone", () -> new SilverCard(R.mipmap.silver_stone, 1, "silver_stone"));
        cardSupplier.put("silver_wood", () -> new SilverCard(R.mipmap.silver_wood, 1, "silver_wood"));
        cardSupplier.put("black_rock", () -> new BlackCard(R.mipmap.black_rock, 1, "black_rock"));
        cardSupplier.put("black_tree", () -> new BlackCard(R.mipmap.black_tree, 1, "black_tree"));
        cardSupplier.put("silver_flint", () -> new SilverCard(R.mipmap.silver_flint, 2, "silver_flint"));
        cardSupplier.put("silver_stick", () -> new SilverCard(R.mipmap.silver_stick, 2, "silver_stick"));
        cardSupplier.put("yellow_villager", () -> new YellowCard(R.mipmap.yellow_villager, 15, "yellow_villager"));
        cardSupplier.put("gold_coin", () -> new CoinCard(R.mipmap.gold_coin, "gold_coin"));
        cardSupplier.put("orange_berry", () -> new OrangeCard(R.mipmap.orange_berry, 1, 1, "orange_berry"));
        cardSupplier.put("orange_apple", () -> new OrangeCard(R.mipmap.orange_apple, 2, 2, "orange_apple"));
        cardSupplier.put("black_berry_bush", () -> new BlackCard(R.mipmap.black_berry_bush, 1, "black_berry_bush"));
        cardSupplier.put("boosterPack_ANewWorld", () -> new BoosterPack(R.mipmap.pack_a_new_world, 0, "boosterPack_ANewWorld"));
        cardSupplier.put("boosterPack_CuriousCuisine", () -> new BoosterPack(R.mipmap.pack_curious_cuisine, 3, "boosterPack_CuriousCuisine"));
        cardSupplier.put("boosterPack_humble_beginnings", () -> new BoosterPack(R.mipmap.pack_humble_beginnnings, 3, "boosterPack_humble_beginnings"));
        cardSupplier.put("boosterPack_seeking_wisdom", () -> new BoosterPack(R.mipmap.pack_seeking_wisdom, 4, "boosterPack_seeking_wisdom"));
        cardSupplier.put("boosterPack_reap_n_sow", () -> new BoosterPack(R.mipmap.pack_curious_cuisine, 10, "boosterPack_reap_n_sow"));
    }

    public Card CreateCard(String str) {
        Supplier<Card> supplier = () -> new Card(0, "NoNameCard", "NoColor");
        return cardSupplier.getOrDefault(str, supplier).get();
    }
}