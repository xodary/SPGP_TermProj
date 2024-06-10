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
        cardSupplier.put("stone", () -> new SilverCard(R.mipmap.silver_stone, 1, "stone"));
        cardSupplier.put("wood", () -> new SilverCard(R.mipmap.silver_wood, 1, "wood"));
        cardSupplier.put("rock", () -> new BlackCard(R.mipmap.black_rock, 1, "rock"));
        cardSupplier.put("flint", () -> new SilverCard(R.mipmap.silver_flint, 2, "flint"));
        cardSupplier.put("stick", () -> new SilverCard(R.mipmap.silver_stick, 2, "stick"));
        cardSupplier.put("villager", () -> new YellowCard(R.mipmap.yellow_villager, 15, "villager"));
        cardSupplier.put("coin", () -> new CoinCard(R.mipmap.gold_coin, "coin"));
        cardSupplier.put("berry", () -> new OrangeCard(R.mipmap.orange_berry, 1, 1, "berry"));
        cardSupplier.put("berry_bush", () -> new BlackCard(R.mipmap.black_berry_bush, 1, "berry_bush"));
        cardSupplier.put("boosterPack_ANewWorld", () -> new BoosterPack(R.mipmap.pack_a_new_world, 0, "boosterPack_ANewWorld"));
        cardSupplier.put("boosterPack_CuriousCuisine", () -> new BoosterPack(R.mipmap.pack_curious_cuisine, 3, "boosterPack_ANewWorld"));
    }

    public Card CreateCard(String str) {
        Supplier<Card> supplier = () -> new Card(0, "NoNameCard", "NoColor");
        return cardSupplier.getOrDefault(str, supplier).get();
    }

    public void collide(Card collidedCard, int n) {
    }
}