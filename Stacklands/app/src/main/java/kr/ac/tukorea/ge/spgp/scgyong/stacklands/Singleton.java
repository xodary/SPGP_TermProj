package kr.ac.tukorea.ge.spgp.scgyong.stacklands;

public class Singleton {
    private static Singleton instance;

    // 생성자를 private으로 선언하여 외부에서 인스턴스를 생성하지 못하도록 막습니다.
    private Singleton() {
    }

    // 유일한 인스턴스를 반환하는 정적 메서드입니다.
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public void showMessage() {
        System.out.println("안녕하세요! 저는 싱글톤 객체입니다.");
    }
}
